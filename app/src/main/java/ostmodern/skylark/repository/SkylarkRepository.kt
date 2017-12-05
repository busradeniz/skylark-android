package ostmodern.skylark.repository


import android.support.v4.util.Pair

import java.util.ArrayList

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ostmodern.skylark.model.Episode
import ostmodern.skylark.model.Image
import ostmodern.skylark.model.Set
import ostmodern.skylark.model.SetUI
import ostmodern.skylark.repository.local.EpisodeEntity
import ostmodern.skylark.repository.local.FavouriteEntity
import ostmodern.skylark.repository.local.SetEntity
import ostmodern.skylark.repository.local.SkylarkDatabase
import ostmodern.skylark.repository.remote.SkylarkService

class SkylarkRepository(private val skylarkService: SkylarkService, private val skylarkDatabase: SkylarkDatabase) {

    /**
     * Fetches the sets from database and trigger the [SkylarkService] to get the latest
     * sets.
     *
     * @return [Flowable]
     */
    val sets: Flowable<List<SetUI>> get() = Flowable.create({ emitter ->
            object : NetworkBoundSource<List<SetUI>, List<Pair<Set, Image>>>(emitter) {
                override val local: Flowable<List<SetUI>>
                    get() = Flowable.zip(
                            skylarkDatabase.setDao().all,
                            skylarkDatabase.favouriteDao().all,
                            BiFunction{ setEntities, favouriteEntities -> mergeAndConvertSetList(setEntities, favouriteEntities) })

                override val remote: Observable<List<Pair<Set, Image>>>
                    get() = skylarkService.getSetList()

                override fun saveCallResult(data: List<Pair<Set, Image>>) {
                    skylarkDatabase.setDao().insert(convert(data))
                    val episodeEntities = convertEpisodeEntities(data)
                    skylarkDatabase.episodeDao().insertEpisodes(episodeEntities)
                }

            }
        }, BackpressureStrategy.BUFFER)

    /**
     * Insert a new [FavouriteEntity] into database.
     *
     * @param setId id of set.
     */
    fun favorite(setId: String) {
        val favouriteEntity = FavouriteEntity(setId)
        skylarkDatabase.favouriteDao().insertFavourite(favouriteEntity)
    }

    /**
     * Deletes the given [FavouriteEntity] from database.
     *
     * @param setId id of set
     */
    fun unfavorite(setId: String) {
        val favouriteEntity = FavouriteEntity(setId)
        skylarkDatabase.favouriteDao().deleteFavourite(favouriteEntity)
    }

    /**
     * Fetches the set according to given id.
     *
     * @param uid id of set.
     * @return [Flowable]
     */
    fun getSetById(uid: String): Maybe<SetUI> {
        return Maybe.zip(
                skylarkDatabase.setDao().getSetById(uid),
                skylarkDatabase.favouriteDao().getFavouriteById(uid).defaultIfEmpty(FavouriteEntity("")),
                BiFunction{ setEntity, favouriteEntity -> mergeAndConvertSet(setEntity, favouriteEntity) })
    }

    private fun mergeAndConvertSet(setEntity: SetEntity, favouriteEntity: FavouriteEntity?): SetUI {
        var isFavourite = false
        if (favouriteEntity != null && !favouriteEntity.setId.isEmpty()) {
            isFavourite = true
        }
        return SetUI(setEntity, isFavourite)
    }

    private fun mergeAndConvertSetList(setEntities: List<SetEntity>,
                                       favouriteEntities: List<FavouriteEntity>): List<SetUI> {

        val setUIList = ArrayList<SetUI>()
        for (setEntity in setEntities) {
            var isFavourite = false
            for ((setId) in favouriteEntities) {
                if (setId == setEntity.uid) {
                    isFavourite = true
                }
            }
            setUIList.add(SetUI(setEntity, isFavourite))
        }
        return setUIList
    }

    private fun convert(sets: List<Pair<Set, Image>>): ArrayList<SetEntity> {
        val setEntities = ArrayList<SetEntity>()
        for (pair in sets) {
            setEntities.add(setEntity(pair.first, pair.second))
        }

        return setEntities
    }

    private fun convertEpisodeEntities(sets: List<Pair<Set, Image>>): ArrayList<EpisodeEntity> {
        val episodeEntities = ArrayList<EpisodeEntity>()
        for (pair in sets) {
            for (episode in pair.first.items) {
                episodeEntities.add(episodeEntity(pair.first.uid, episode))
            }
        }

        return episodeEntities
    }

    private fun episodeEntity(setId: String, episode: Episode): EpisodeEntity {
        return EpisodeEntity(episode.uid, episode.contentUrl,
                episode.contentType, setId)
    }

    private fun setEntity(entity: Set, image: Image): SetEntity {
        return SetEntity(entity.uid, entity.title, entity.filmCount,
                entity.formattedBody, image.url)
    }
}
