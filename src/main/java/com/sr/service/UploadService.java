package com.sr.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author cyh
 * @Date 2021/11/7 15:53
 */
public interface UploadService {
    public void processSinglePicture(MultipartFile file, HttpServletResponse response, String tag, String token);

    public String processSingleVideo(MultipartFile file);
}
