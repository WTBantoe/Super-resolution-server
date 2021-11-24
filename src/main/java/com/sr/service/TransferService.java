package com.sr.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface TransferService
{
    void uploadFile(MultipartFile file, File destination);

    void downloadFile(File source, HttpServletResponse response);
}
