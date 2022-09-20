package pl.kdrozd.downloader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DownloaderApplicationTests.class},
        initializers = ConfigDataApplicationContextInitializer.class)
class DownloaderApplicationTests {

    @Test
    void contextLoads() {
    }

}
