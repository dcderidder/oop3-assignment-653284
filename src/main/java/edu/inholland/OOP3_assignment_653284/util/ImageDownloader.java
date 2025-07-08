package edu.inholland.OOP3_assignment_653284.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageDownloader {

    private final Path targetDir;

    public ImageDownloader(@Value("${images.path}") String dir) {
        this.targetDir = Path.of(dir);
        targetDir.toFile().mkdirs();
    }

    public List<String> download(List<String> relativePaths) throws Exception {
        List<String> local = new ArrayList<>();
        for (int i = 0; i < Math.min(3, relativePaths.size()); i++) {
            String rel = relativePaths.get(i);
            URL url = new URL("https://image.tmdb.org/t/p/w780" + rel);
            Path file = targetDir.resolve(rel.substring(1)); // strip leading '/'

            file.getParent().toFile().mkdirs();
            try (InputStream in = url.openStream();
                 FileOutputStream out = new FileOutputStream(file.toFile())) {
                StreamUtils.copy(in, out);
            }
            local.add(file.toString());
        }
        return local;
    }
}
