package com.example.travel.route.S3.upload;

import com.example.travel.route.S3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ImageService {

    @Autowired
    private S3Uploader s3Uploader;
    private final S3ImageRepository repository;

    @Transactional
    public String keepImage(MultipartFile image) throws IOException {
        S3Image s3Image = new S3Image();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        if(!image.isEmpty()) {
            String url = s3Uploader.uploadFiles(image, "review");
            log.info("File uploaded: " + url);
            s3Image.setS3_url(url);
        }

        try {
            repository.saveS3(s3Image.getS3_url());
        } catch (SQLException e) {
            log.info(e.getMessage());
        }

        stopWatch.stop();
        log.info("{} - total time = {}s", stopWatch.prettyPrint());
        return "uploaded";
    }
}