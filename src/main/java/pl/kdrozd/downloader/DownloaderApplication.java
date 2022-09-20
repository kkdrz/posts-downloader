package pl.kdrozd.downloader;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.kdrozd.downloader.service.ApiService;
import pl.kdrozd.downloader.service.PostToDirectoryRecorder;

@RequiredArgsConstructor
@SpringBootApplication
public class DownloaderApplication implements CommandLineRunner {

    private final ApiService service;
    private final PostToDirectoryRecorder postRecorder;

    public static void main(String[] args) {
        SpringApplication.run(DownloaderApplication.class, args);
    }

    @Override
    public void run(String... args) {

        service.fetchAllPosts()
                .stream()
                .parallel()
                .forEach(postRecorder);

        System.exit(0);
    }

}
