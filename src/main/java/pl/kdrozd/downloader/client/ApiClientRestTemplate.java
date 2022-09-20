package pl.kdrozd.downloader.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.kdrozd.downloader.dto.Post;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiClientRestTemplate implements ApiClient {

    static final String POSTS_ENDPOINT = "/posts";
    private final RestTemplate restTemplate;

    @Override
    public List<Post> fetchAll() {

        ResponseEntity<List<Post>> response = restTemplate.exchange(POSTS_ENDPOINT, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }
}
