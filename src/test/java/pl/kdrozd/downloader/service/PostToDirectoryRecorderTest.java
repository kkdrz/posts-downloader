package pl.kdrozd.downloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kdrozd.downloader.dto.Post;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static pl.kdrozd.downloader.service.PostToDirectoryRecorder.FILE_EXTENSION;

@ExtendWith(MockitoExtension.class)
class PostToDirectoryRecorderTest {

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OutputFilesProvider outputFilesProvider;

    @Captor
    ArgumentCaptor<String> actualFileNameCaptor;

    @InjectMocks
    PostToDirectoryRecorder recorder;

    @Test
    void should_fail_fast_when_null_supplied() {
        assertThrows(NullPointerException.class, () -> recorder.accept(null));

        verifyNoInteractions(outputFilesProvider, objectMapper);
    }

    @Test
    void should_record_post_to_correct_file() throws Exception {
        Post post = Post.builder()
                .id(123)
                .build();

        String expectedOutputFileName = post.getId() + FILE_EXTENSION;
        Path expectedPath = Path.of(expectedOutputFileName);

        when(outputFilesProvider.createDestinationFile(expectedOutputFileName)).thenReturn(expectedPath);

        recorder.accept(post);

        verify(outputFilesProvider).createDestinationFile(actualFileNameCaptor.capture());
        verify(objectMapper, times(1)).writeValue(eq(expectedPath.toFile()), eq(post));
        assertEquals(expectedOutputFileName, actualFileNameCaptor.getValue());
    }
}