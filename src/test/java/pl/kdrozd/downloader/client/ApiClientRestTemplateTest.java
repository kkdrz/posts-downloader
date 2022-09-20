package pl.kdrozd.downloader.client;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.kdrozd.downloader.dto.Post;

import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.status;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.kdrozd.downloader.client.ApiClientRestTemplate.POSTS_ENDPOINT;


@SpringBootTest(classes = {ApiClientRestTemplate.class, ApiClientRestTemplateTest.RestTemplateTestConfig.class})
class ApiClientRestTemplateTest {

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @TestConfiguration
    public static class RestTemplateTestConfig {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplateBuilder()
                    .rootUri("http://localhost:" + wiremock.getPort())
                    .build();
        }

    }

    @Autowired
    private ApiClient apiClient;

    @Test
    void should_fetch_posts_correctly() {
        List<Post> expectedPosts = createDummyPosts();
        wiremock.stubFor(get(POSTS_ENDPOINT).willReturn(okForJson(expectedPosts)));

        List<Post> posts = apiClient.fetchAll();

        assertEquals(expectedPosts, posts);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            400, 401, 403, 404, 500, 503
    })
    void should_throw_exception_when_response_status_not_ok(int status) {
        wiremock.stubFor(get(POSTS_ENDPOINT).willReturn(status(status)));

        assertThrows(RestClientException.class, () -> apiClient.fetchAll());
    }

    private static List<Post> createDummyPosts() {
        return Arrays.asList(
                Post.builder()
                        .id(1)
                        .body("Some body")
                        .title("Post title")
                        .userId(1)
                        .build(),
                Post.builder()
                        .id(2)
                        .body("Different body")
                        .title("Post title #2")
                        .userId(2)
                        .build());
    }
}