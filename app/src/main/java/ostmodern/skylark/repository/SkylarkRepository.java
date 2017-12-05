package ostmodern.skylark.repository;


import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import ostmodern.skylark.model.Episode;
import ostmodern.skylark.model.Image;
import ostmodern.skylark.model.Set;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.repository.local.EpisodeEntity;
import ostmodern.skylark.repository.local.FavouriteEntity;
import ostmodern.skylark.repository.local.SetEntity;
import ostmodern.skylark.repository.local.SkylarkDatabase;
import ostmodern.skylark.repository.remote.SkylarkService;

public class SkylarkRepository {

    private final SkylarkService skylarkService;
    private final SkylarkDatabase skylarkDatabase;

    public SkylarkRepository(SkylarkService skylarkService, SkylarkDatabase skylarkDatabase) {
        this.skylarkService = skylarkService;
        this.skylarkDatabase = skylarkDatabase;
    }

    /**
     * Fetches the sets from database and trigger the {@link SkylarkService} to get the latest
     * sets.
     *
     * @return {@link Flowable}
     */
    public Flowable<List<SetUI>> getSets() {
        return Flowable.create(emitter -> new NetworkBoundSource<List<SetUI>, List<Pair<Set, Image>>>(emitter) {

            @Override
            public Observable<List<Pair<Set, Image>>> getRemote() {
                return skylarkService.getSetList();
            }

            @Override
            public Flowable<List<SetUI>> getLocal() {
                return Flowable.zip(
                        skylarkDatabase.setDao().getAll(),
                        skylarkDatabase.favouriteDao().getAll(),
                        (setEntities, favouriteEntities)
                                -> mergeAndConvertSetList(setEntities, favouriteEntities));
            }

            @Override
            public void saveCallResult(List<Pair<Set, Image>> data) {
                skylarkDatabase.setDao().insert(convert(data));
                ArrayList<EpisodeEntity> episodeEntities = convertEpisodeEntities(data);
                skylarkDatabase.episodeDao().insertEpisodes(episodeEntities);
            }

        }, BackpressureStrategy.BUFFER);
    }

    /**
     * Insert a new {@link FavouriteEntity} into database.
     *
     * @param setId id of set.
     */
    public void favorite(String setId) {
        final FavouriteEntity favouriteEntity = new FavouriteEntity(setId);
        skylarkDatabase.favouriteDao().insertFavourite(favouriteEntity);
    }

    /**
     * Deletes the given {@link FavouriteEntity} from database.
     *
     * @param setId id of set
     */
    public void unfavorite(String setId) {
        final FavouriteEntity favouriteEntity = new FavouriteEntity(setId);
        skylarkDatabase.favouriteDao().deleteFavourite(favouriteEntity);
    }

    /**
     * Fetches the set according to given id.
     *
     * @param uid id of set.
     * @return {@link Flowable}
     */
    public Maybe<SetUI> getSetById(String uid) {
        return Maybe.zip(
                skylarkDatabase.setDao().getSetById(uid),
                skylarkDatabase.favouriteDao().getFavouriteById(uid).defaultIfEmpty(new FavouriteEntity("")),
                (setEntity, favouriteEntity)
                        -> mergeAndConvertSet(setEntity, favouriteEntity));
    }

    private SetUI mergeAndConvertSet(SetEntity setEntity, FavouriteEntity favouriteEntity) {
        boolean isFavourite = false;
        if (favouriteEntity != null && !favouriteEntity.setId.isEmpty()) {
            isFavourite = true;
        }
        return new SetUI(setEntity, isFavourite);
    }

    private List<SetUI> mergeAndConvertSetList(List<SetEntity> setEntities,
                                               List<FavouriteEntity> favouriteEntities) {

        List<SetUI> setUIList = new ArrayList<>();
        for (SetEntity setEntity : setEntities) {
            boolean isFavourite = false;
            for (FavouriteEntity favouriteEntity : favouriteEntities) {
                if (Objects.equals(favouriteEntity.setId, setEntity.getUid())) {
                    isFavourite = true;
                }
            }
            setUIList.add(new SetUI(setEntity, isFavourite));
        }
        return setUIList;
    }

    private ArrayList<SetEntity> convert(List<Pair<Set, Image>> sets) {
        final ArrayList<SetEntity> setEntities = new ArrayList<>();
        for (Pair<Set, Image> pair : sets) {
            setEntities.add(setEntity(pair.first, pair.second));
        }

        return setEntities;
    }

    private ArrayList<EpisodeEntity> convertEpisodeEntities(List<Pair<Set, Image>> sets) {
        final ArrayList<EpisodeEntity> episodeEntities = new ArrayList<>();
        for (Pair<Set, Image> pair : sets) {
            for (Episode episode : pair.first.getItems()) {
                episodeEntities.add(episodeEntity(pair.first.getUid(), episode));
            }
        }

        return episodeEntities;
    }

    private EpisodeEntity episodeEntity(String setId, Episode episode) {
        return new EpisodeEntity(episode.getUid(), episode.getContentUrl(),
                episode.getContentType(), setId);
    }

    private SetEntity setEntity(Set entity, Image image) {
        return new SetEntity(entity.getUid(), entity.getTitle(), entity.getFilmCount(),
                entity.getFormattedBody(), image.getUrl());
    }
}
