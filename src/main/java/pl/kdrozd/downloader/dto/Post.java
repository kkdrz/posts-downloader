package pl.kdrozd.downloader.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Post {

    Integer userId;
    Integer id;
    String title;
    String body;

}
