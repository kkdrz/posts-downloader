package pl.kdrozd.downloader.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kdrozd.downloader.client.ApiClient;
import pl.kdrozd.downloader.dto.Post;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final ApiClient apiClient;

    public List<Post> fetchAllPosts() {
        return apiClient.fetchAll();
    }
}
