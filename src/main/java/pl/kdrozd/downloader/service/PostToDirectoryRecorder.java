package pl.kdrozd.downloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kdrozd.downloader.dto.Post;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostToDirectoryRecorder implements Consumer<Post> {

    static final String FILE_EXTENSION = ".json";
    private final ObjectMapper mapper;
    private final OutputFilesProvider directoryProvider;

    @Override
    public void accept(Post post) {

        try {
            Objects.requireNonNull(post);
            writeToFile(post);

        } catch (IOException e) {
            throw new RuntimeException("Unable to save post with id <" + post.getId() + "> to directory <" + directoryProvider.getDirectory() + ">", e);
        }

    }

    private void writeToFile(Post post) throws IOException {
        Path destinationFile = directoryProvider.createDestinationFile(getFileName(post));

        log.info("Saving post with id <{}> to {}", post.getId(), destinationFile.toAbsolutePath());

        mapper.writeValue(destinationFile.toFile(), post);
    }

    private static String getFileName(Post post) {
        return post.getId() + FILE_EXTENSION;
    }

}
