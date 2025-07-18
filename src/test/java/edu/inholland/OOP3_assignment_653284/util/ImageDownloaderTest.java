package edu.inholland.OOP3_assignment_653284.util;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ImageDownloaderTest {
    @Test
    void testConstructor() {
        ImageDownloader downloader = new ImageDownloader("test_images");
        assertNotNull(downloader);
    }

    // You can add more tests with mocks or using temporary folders!
}
