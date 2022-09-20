package pl.kdrozd.downloader.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OutputFilesProviderTest {

    @TempDir
    Path tempDirectory;

    OutputFilesProvider filesProvider;


    @BeforeEach
    public void beforeEach() {
        filesProvider = new OutputFilesProvider(tempDirectory);
    }

    @Test
    void should_throw_when_directory_not_empty() throws Exception {
        Path someFile = tempDirectory.resolve("someFile.txt");
        Files.createFile(someFile);

        Assertions.assertThrows(IllegalArgumentException.class, () -> filesProvider.createDestinationFile("anotherFile.json"));
    }

    @Test
    void should_throw_when_directory_is_not_directory() throws Exception {
        Path someFile = tempDirectory.resolve("someFile.txt");
        Files.createFile(someFile);

        filesProvider = new OutputFilesProvider(someFile);

        Assertions.assertThrows(IllegalArgumentException.class, () -> filesProvider.createDestinationFile("anotherFile.json"));
    }

    @Test
    void should_create_directory_if_missing() throws Exception {

        Path directoryToCreate = tempDirectory.resolve("directoryToCreate");

        filesProvider = new OutputFilesProvider(directoryToCreate);

        filesProvider.createDestinationFile("someFile.json");

        assertTrue(directoryToCreate.toFile().exists());
        assertTrue(directoryToCreate.toFile().isDirectory());
    }

    @Test
    void should_create_destination_file_in_empty_dir() throws Exception {
        String fileToCreateName = "anotherFile.json";
        Path expectedFile = tempDirectory.resolve(fileToCreateName);

        Path actualFile = filesProvider.createDestinationFile(fileToCreateName);

        assertEquals(expectedFile, actualFile);
        assertTrue(expectedFile.toFile().exists());
        assertTrue(expectedFile.toFile().isFile());
    }

    @Test
    void should_create_directory_and_destination_file() throws Exception {
        // given
        Path directoryToCreate = tempDirectory.resolve("directoryToCreate");

        String fileToCreateName = "anotherFile.json";
        Path expectedFile = directoryToCreate.resolve(fileToCreateName);

        filesProvider = new OutputFilesProvider(directoryToCreate);

        // when
        Path actualFile = filesProvider.createDestinationFile(fileToCreateName);

        // then
        assertEquals(expectedFile, actualFile);
        assertTrue(expectedFile.toFile().exists());
        assertTrue(expectedFile.toFile().isFile());
    }
}