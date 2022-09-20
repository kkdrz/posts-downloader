package pl.kdrozd.downloader.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Slf4j
@Service
public class OutputFilesProvider {
    @Getter
    private final Path directory;
    private boolean directoryCreated;

    public OutputFilesProvider(@Value("${app.output-directory}") Path directory) {
        this.directory = directory
                .normalize()
                .toAbsolutePath();
    }

    public Path createDestinationFile(String fileName) throws IOException {
        if (!directoryCreated) {
            createOutputDirectory();
            directoryCreated = true;
        }

        return Files.createFile(directory.resolve(fileName));
    }

    private void createOutputDirectory() throws IOException {

        if (directoryExistsAndValid()) {
            return;
        }

        log.debug("Creating directory <{}>", directory);
        Files.createDirectories(directory);
    }

    private boolean directoryExistsAndValid() throws IOException {
        if (!directory.toFile().exists()) {
            return false;
        }

        if (!directory.toFile().isDirectory()) {
            throw new IllegalArgumentException("Given path <" + directory + "> is not a directory.");
        }

        if (!isEmpty(directory)) {
            throw new IllegalArgumentException("Given directory <" + directory + "> is not empty!");
        }

        log.debug("Directory <{}> exists and is empty", directory);
        return true;
    }

    private boolean isEmpty(Path directory) throws IOException {
        try (Stream<Path> entries = Files.list(directory)) {
            return entries.findFirst().isEmpty();
        }
    }
}
