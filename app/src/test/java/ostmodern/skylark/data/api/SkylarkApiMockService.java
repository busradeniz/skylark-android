package ostmodern.skylark.data.api;

import com.google.common.collect.ImmutableList;

import org.junit.rules.ExternalResource;

import java.io.IOException;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import ostmodern.skylark.model.Episode;
import ostmodern.skylark.model.Image;
import ostmodern.skylark.model.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class SkylarkApiMockService extends ExternalResource {

    public static final Episode EPISODE = new Episode("mock-episode-1", "test-self-1", "test-content-url-1", "episode");
    public static final Set SET_WITHOUT_IMAGE = new Set("set-without-image", "Popular", 0,
            "test-formatted-body-1", ImmutableList.of(),
            ImmutableList.of());
    public static final Set SET_WITH_IMAGE = new Set("set-with-images", "Home", 2,
            "test-formatted-body-2",
            ImmutableList.of("/api/images/mock-image-1/", "/api/images/mock-image-2/"),
            ImmutableList.of(EPISODE));
    public static final Image MOCK_IMAGE_1 = new Image("mock-image-1", "test-url", "/api/images/mock-image-1/");
    public static final Image MOCK_IMAGE_2 = new Image("mock-image-2", "test-url-2", "/api/images/mock-image-2/");
    public static final Image DEFAULT_IMAGE = new Image("", "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/static/images/dummy/dummy-film.jpg", "");

    private final MockWebServer mockWebServer;
    private String baseUrl;

    public SkylarkApiMockService() {
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(createDispatcher());
    }

    private Dispatcher createDispatcher() {
        return new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {
                    case "/api/sets/":
                        return new MockResponse().setBody(SETS_JSON).setResponseCode(200);
                    case "/api/images/":
                        return new MockResponse().setBody(IMAGES_JSON).setResponseCode(200);
                    default:
                        return new MockResponse().setResponseCode(500);
                }
            }
        };
    }

    @Override
    protected void before() throws Throwable {
        mockWebServer.start();
        baseUrl = mockWebServer.url("").toString();
        assertThat("Base URL should be provided by mock service", baseUrl, notNullValue());
    }

    @Override
    protected void after() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            throw new RuntimeException("Unable to stop mock server");
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    private static final String SETS_JSON = "{\n" +
            "\t\"objects\": [{\n" +
            "\t\t\"uid\": \"set-without-image\",\n" +
            "\t\t\"schedule_urls\": [\"/api/schedules/sche_aeba759af1f44c9ca75564c363c870b6/\"],\n" +
            "\t\t\"publish_on\": \"2014-10-24T00:00:00+00:00\",\n" +
            "\t\t\"quoter\": \"\",\n" +
            "\t\t\"featured\": false,\n" +
            "\t\t\"language_modified_by\": null,\n" +
            "\t\t\"plans\": [],\n" +
            "\t\t\"cached_film_count\": 0,\n" +
            "\t\t\"modified_by\": null,\n" +
            "\t\t\"temp_id\": 91,\n" +
            "\t\t\"title\": \"Popular\",\n" +
            "\t\t\"self\": \"/api/sets/coll_20ce39424470457c925f823fc150b3d4/\",\n" +
            "\t\t\"created_by\": \"admin@example.com\",\n" +
            "\t\t\"language_publish_on\": \"2014-10-24T00:00:00+00:00\",\n" +
            "\t\t\"language_modified\": \"2014-10-25T12:45:54.159000+00:00\",\n" +
            "\t\t\"has_price\": false,\n" +
            "\t\t\"set_type_url\": \"/api/set-types/sett_888fea5fb5474ee9aac9416f4fd6bce6/\",\n" +
            "\t\t\"temp_image\": \"\",\n" +
            "\t\t\"film_count\": 0,\n" +
            "\t\t\"body\": \"test-body-1\",\n" +
            "\t\t\"language_version_url\": \"/api/sets/coll_20ce39424470457c925f823fc150b3d4/language-versions/0/\",\n" +
            "\t\t\"quote\": \"test-quote-1\",\n" +
            "\t\t\"lowest_amount\": null,\n" +
            "\t\t\"formatted_body\": \"test-formatted-body-1\",\n" +
            "\t\t\"image_urls\": [],\n" +
            "\t\t\"hierarchy_url\": null,\n" +
            "\t\t\"schedule_url\": \"/api/schedules/sche_aeba759af1f44c9ca75564c363c870b6/\",\n" +
            "\t\t\"active\": true,\n" +
            "\t\t\"slug\": \"popular\",\n" +
            "\t\t\"version_number\": 0,\n" +
            "\t\t\"language_ends_on\": \"2100-01-01T00:00:00+00:00\",\n" +
            "\t\t\"created\": \"2014-10-25T12:45:54+00:00\",\n" +
            "\t\t\"items\": [],\n" +
            "\t\t\"language_version_number\": 0,\n" +
            "\t\t\"modified\": \"2014-10-25T12:45:54.159000+00:00\",\n" +
            "\t\t\"summary\": \"test-summary-1\",\n" +
            "\t\t\"ends_on\": \"2100-01-01T00:00:00+00:00\",\n" +
            "\t\t\"version_url\": \"/api/sets/coll_20ce39424470457c925f823fc150b3d4/versions/0/\",\n" +
            "\t\t\"set_type_slug\": \"listings\"\n" +
            "\t}, {\n" +
            "\t\t\"uid\": \"set-with-images\",\n" +
            "\t\t\"schedule_urls\": [\"/api/schedules/sche_aeba759af1f44c9ca75564c363c870b6/\"],\n" +
            "\t\t\"publish_on\": \"2014-10-24T00:00:00+00:00\",\n" +
            "\t\t\"quoter\": \"\",\n" +
            "\t\t\"featured\": false,\n" +
            "\t\t\"language_modified_by\": null,\n" +
            "\t\t\"plans\": [],\n" +
            "\t\t\"cached_film_count\": 0,\n" +
            "\t\t\"modified_by\": null,\n" +
            "\t\t\"temp_id\": 89,\n" +
            "\t\t\"title\": \"Home\",\n" +
            "\t\t\"self\": \"/api/sets/coll_e8400ca3aebb4f70baf74a81aefd5a78/\",\n" +
            "\t\t\"created_by\": \"admin@example.com\",\n" +
            "\t\t\"language_publish_on\": \"2014-10-24T00:00:00+00:00\",\n" +
            "\t\t\"language_modified\": \"2014-10-25T12:45:54.131000+00:00\",\n" +
            "\t\t\"has_price\": false,\n" +
            "\t\t\"set_type_url\": \"/api/set-types/sett_febfffc998c74fb0a90e214aa7942da0/\",\n" +
            "\t\t\"temp_image\": \"\",\n" +
            "\t\t\"film_count\": 2,\n" +
            "\t\t\"body\": \"test-body-2\",\n" +
            "\t\t\"language_version_url\": \"/api/sets/coll_e8400ca3aebb4f70baf74a81aefd5a78/language-versions/0/\",\n" +
            "\t\t\"quote\": \"test-quote-2\",\n" +
            "\t\t\"lowest_amount\": null,\n" +
            "\t\t\"formatted_body\": \"test-formatted-body-2\",\n" +
            "\t\t\"image_urls\": [\"/api/images/mock-image-1/\", \"/api/images/mock-image-2/\"],\n" +
            "\t\t\"hierarchy_url\": null,\n" +
            "\t\t\"schedule_url\": \"/api/schedules/sche_aeba759af1f44c9ca75564c363c870b6/\",\n" +
            "\t\t\"active\": true,\n" +
            "\t\t\"slug\": \"home\",\n" +
            "\t\t\"version_number\": 0,\n" +
            "\t\t\"language_ends_on\": \"2100-01-01T00:00:00+00:00\",\n" +
            "\t\t\"created\": \"2014-10-25T12:45:54+00:00\",\n" +
            "\t\t\"items\": [{\n" +
            "\t\t\t\"uid\": \"mock-episode-1\",\n" +
            "\t\t\t\"self\": \"test-self-1\",\n" +
            "\t\t\t\"content_url\": \"test-content-url-1\",\n" +
            "\t\t\t\"schedule_url\": \"/api/schedules/sche_aeba759af1f44c9ca75564c363c870b6/\",\n" +
            "\t\t\t\"set_url\": \"/api/sets/coll_e8400ca3aebb4f70baf74a81aefd5a78/\",\n" +
            "\t\t\t\"content_type\": \"episode\",\n" +
            "\t\t\t\"position\": 210\n" +
            "\t\t}],\n" +
            "\t\t\"language_version_number\": 0,\n" +
            "\t\t\"modified\": \"2014-10-25T12:45:54.131000+00:00\",\n" +
            "\t\t\"summary\": \"test-summary-2\",\n" +
            "\t\t\"ends_on\": \"2100-01-01T00:00:00+00:00\",\n" +
            "\t\t\"version_url\": \"/api/sets/coll_e8400ca3aebb4f70baf74a81aefd5a78/versions/0/\",\n" +
            "\t\t\"set_type_slug\": \"home\"\n" +
            "\t}]\n" +
            "}";

    private static final String IMAGES_JSON = "{\n" +
            "\t\"objects\": [{\n" +
            "\t\"uid\": \"mock-image-1\",\n" +
            "\t\"show\": true,\n" +
            "\t\"schedule_urls\": [\"/api/schedules/sche_aeba759af1f44c9ca75564c363c870b6/\"],\n" +
            "\t\"content_url\": \"/api/sets/coll_0261a4d4a0f347aebdb446c04dc9f56d/\",\n" +
            "\t\"url\": \"test-url\",\n" +
            "\t\"image_type_url\": \"/api/image-types/imag_97107d1bcf0b4d88914450b4524bf919/\",\n" +
            "\t\"upload_image_url\": \"/api/images/imag_fcdf67481d8147e6844d838f4112faaf/upload/\",\n" +
            "\t\"language_modified_by\": null,\n" +
            "\t\"language_version_url\": \"/api/images/imag_fcdf67481d8147e6844d838f4112faaf/language-versions/0/\",\n" +
            "\t\"description\": \"\",\n" +
            "\t\"language_ends_on\": null,\n" +
            "\t\"created\": \"2014-10-27T11:32:12.645000+00:00\",\n" +
            "\t\"image_type\": \"header\",\n" +
            "\t\"self\": \"/api/images/mock-image-1/\",\n" +
            "\t\"title\": \"this and that\",\n" +
            "\t\"created_by\": null,\n" +
            "\t\"language_publish_on\": \"2015-06-23T14:38:52.456000+00:00\",\n" +
            "\t\"language_version_number\": 0,\n" +
            "\t\"language_modified\": \"2015-06-23T14:38:52.456000+00:00\",\n" +
            "\t\"position\": 1,\n" +
            "\t\"align\": \"default\"\n" +
            "\t}, {\n" +
            "\t\"uid\": \"mock-image-2\",\n" +
            "\t\"show\": true,\n" +
            "\t\"schedule_urls\": [\"/api/schedules/sche_aeba759af1f44c9ca75564c363c870b6/\"],\n" +
            "\t\"content_url\": \"/api/sets/coll_14bc183d0dd447d59dfc014ba1e69510/\",\n" +
            "\t\"url\": \"test-url-2\",\n" +
            "\t\"image_type_url\": \"/api/image-types/imag_97107d1bcf0b4d88914450b4524bf919/\",\n" +
            "\t\"upload_image_url\": \"/api/images/imag_fcdf67481d8147e6844d838f4112faac/upload/\",\n" +
            "\t\"language_modified_by\": null,\n" +
            "\t\"language_version_url\": \"/api/images/imag_fcdf67481d8147e6844d838f4112faac/language-versions/0/\",\n" +
            "\t\"description\": \"\",\n" +
            "\t\"language_ends_on\": null,\n" +
            "\t\"created\": \"2014-10-28T17:40:57.229000+00:00\",\n" +
            "\t\"image_type\": \"header\",\n" +
            "\t\"self\": \"/api/images/mock-image-2/\",\n" +
            "\t\"title\": \"Unidentified Sporting Crowd\",\n" +
            "\t\"created_by\": null,\n" +
            "\t\"language_publish_on\": \"2015-06-23T14:38:52.465000+00:00\",\n" +
            "\t\"language_version_number\": 0,\n" +
            "\t\"language_modified\": \"2015-06-23T14:38:52.465000+00:00\",\n" +
            "\t\"position\": 2,\n" +
            "\t\"align\": \"default\"\n" +
            "\t}]\n" +
            "}";

}
