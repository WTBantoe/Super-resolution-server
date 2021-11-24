package com.sr.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/7 15:53
 */
public interface UploadService
{
    Map<String, Object> processSinglePicture(MultipartFile file, HttpServletResponse response, String tag, String token);

    Map<String, Object> processSingleVideo(MultipartFile file, HttpServletResponse response, String tag, String token);

    Map<String, Object> processMultiPicture(MultipartFile[] files, HttpServletResponse response, String tag, String token);

    Map<String, Object> processMultiVideo(MultipartFile[] files, HttpServletResponse response, String tag, String token);
}
