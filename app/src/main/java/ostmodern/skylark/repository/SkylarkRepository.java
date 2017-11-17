package ostmodern.skylark.repository;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import ostmodern.skylark.model.Episode;
import ostmodern.skylark.model.Image;
import ostmodern.skylark.model.Set;
import ostmodern.skylark.repository.local.EpisodeEntity;
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
    public Flowable<List<SetEntity>> getSets() {
        return Flowable.create(emitter -> new NetworkBoundSource<List<SetEntity>, List<Pair<Set, Image>>>(emitter) {

            @Override
            public Observable<List<Pair<Set, Image>>> getRemote() {
                return skylarkService.getSetList();
            }

            @Override
            public Flowable<List<SetEntity>> getLocal() {
                return skylarkDatabase.setDao().getAll();
            }

            @Override
            public void saveCallResult(List<Pair<Set, Image>> data) {
                skylarkDatabase.setDao().insert(convert(data));
                List<EpisodeEntity> episodeEntities = convertEpisodeEntities(data);
                skylarkDatabase.episodeDao().insertEpisodes(episodeEntities);
            }

        }, BackpressureStrategy.BUFFER);
    }

    private List<SetEntity> convert(List<Pair<Set, Image>> sets) {
        final List<SetEntity> setEntities = new ArrayList<>();
        for (Pair<Set, Image> pair : sets) {
            setEntities.add(setEntity(pair.first, pair.second));
        }

        return setEntities;
    }

    private List<EpisodeEntity> convertEpisodeEntities(List<Pair<Set, Image>> sets) {
        final List<EpisodeEntity> episodeEntities = new ArrayList<>();
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
                entity.getBody(), entity.getFormattedBody(), entity.getSummary(), image.getUrl());
    }
}
