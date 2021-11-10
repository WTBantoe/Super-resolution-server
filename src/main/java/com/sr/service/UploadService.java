package com.sr.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author cyh
 * @Date 2021/11/7 15:53
 */
public interface UploadService {
    void processSinglePicture(MultipartFile file, HttpServletResponse response, String tag, String token);

    void processSingleVideo(MultipartFile file, HttpServletResponse response, String tag, String token);
}
