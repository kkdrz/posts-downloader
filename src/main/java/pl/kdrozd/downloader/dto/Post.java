package pl.kdrozd.downloader.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Post {

    Integer userId;
    Integer id;
    String title;
    String body;

}
