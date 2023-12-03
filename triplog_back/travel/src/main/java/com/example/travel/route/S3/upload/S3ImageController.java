package com.example.travel.route.S3.upload;

import com.example.travel.route.S3.S3Uploader;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class S3ImageController {

    @Autowired
    private S3ImageService service;

    // S3 서버에 저장
    @PostMapping(value = "/image")
    public String saveImage(HttpServletRequest request,
                            @RequestParam(value = "image")MultipartFile image) throws IOException {
        return service.keepImage(image);
    }
}