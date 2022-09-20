package pl.kdrozd.downloader.client;

import pl.kdrozd.downloader.dto.Post;

import java.util.List;

public interface ApiClient {

    List<Post> fetchAll();

}
