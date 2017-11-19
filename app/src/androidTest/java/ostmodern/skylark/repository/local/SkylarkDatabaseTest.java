package ostmodern.skylark.repository.local;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.internal.deps.guava.collect.ImmutableList;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

@RunWith(AndroidJUnit4.class)
public class SkylarkDatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private SkylarkDatabase database;

    @Before
    public void setUp() throws Exception {
        database = Room
                .inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), SkylarkDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @Test
    public void testReadAfterWriteSets() throws Exception {
        // Given
        SetEntity setEntity = new SetEntity("test-set-id-1", "test-title", 3,
                "test-formatted-body", "test-image-url");

        // When
        database.setDao().insert(ImmutableList.of(setEntity));
        TestSubscriber<List<SetEntity>> result = database.setDao().getAll()
                .test();

        // Then
        result.assertNoErrors();
        result.assertValue(ImmutableList.of(setEntity));
    }

    @Test
    public void testReadAfterWriteEpisode() throws Exception {
        // Given
        String setId = "test-set-id-1";
        SetEntity setEntity = new SetEntity(setId, "test-title", 3,
                "test-formatted-body", "test-image-url");
        EpisodeEntity episodeEntity = new EpisodeEntity("test-id-1", "test-content-url",
                "test-content-type", setId);

        // When
        database.setDao().insert(ImmutableList.of(setEntity));
        database.episodeDao().insertEpisodes(ImmutableList.of(episodeEntity));
        TestSubscriber<List<EpisodeEntity>> result = database.episodeDao().getEpisodesForSet(setId).test();

        // Then
        result.assertNoErrors();
        result.assertValue(ImmutableList.of(episodeEntity));
    }

    @Test
    public void testFavorite() throws Exception {
        // Given
        String setId = "test-set-id-1";
        SetEntity setEntity = new SetEntity(setId, "test-title", 3,
                "test-formatted-body", "test-image-url");
        FavouriteEntity favouriteEntity = new FavouriteEntity(setId);

        // When
        database.setDao().insert(ImmutableList.of(setEntity));
        database.favouriteDao().insertFavourite(favouriteEntity);

        // Then
        database.favouriteDao().getAll().test()
                .assertNoErrors()
                .assertValue(ImmutableList.of(favouriteEntity));
    }

    @Test
    public void testUnfavorite() throws Exception {
        // Given
        String setId = "test-set-id-1";
        SetEntity setEntity = new SetEntity(setId, "test-title", 3,
                "test-formatted-body", "test-image-url");
        FavouriteEntity favouriteEntity = new FavouriteEntity(setId);

        // When
        database.setDao().insert(ImmutableList.of(setEntity));
        database.favouriteDao().insertFavourite(favouriteEntity);
        database.favouriteDao().deleteFavourite(favouriteEntity);

        // Then
        database.favouriteDao().getAll().test()
                .assertNoErrors()
                .assertValue(ImmutableList.of());

    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }
}