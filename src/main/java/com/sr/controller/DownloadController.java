package com.sr.controller;

import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("/download")
@Api(tags = {"下载管理"})
public class DownloadController
{
    @Autowired
    TransferService transferService;

    @Value("&{picture.path.processed}")
    public static String PROCESSED_PICTURE_PATH;

    @Value("&{video.path.processed}")
    public static String PROCESSED_VIDEO_PATH;

    @GetMapping("/image/single")
    @ApiOperation("处理图片下载")
    public String downloadImage(@RequestParam(value = "filename") String filename, HttpServletResponse response)
    {
        File image = new File(PROCESSED_PICTURE_PATH + filename);
        if (!image.exists())
        {
            throw new StatusException(StatusEnum.COULD_NOT_FIND_PROCESSED_PICTURE);
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + image.getAbsolutePath());
        transferService.downloadFile(image, response);
        return "Image Download Success!";
    }

    @GetMapping("/video/single")
    @ApiOperation("处理视频下载")
    public String downloadVideo(@RequestParam(value = "filename") String filename, HttpServletResponse response)
    {
        File video = new File(PROCESSED_VIDEO_PATH + filename);
        if (!video.exists())
        {
            throw new StatusException(StatusEnum.COULD_NOT_FIND_PROCESSED_VIDEO);
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + video.getAbsolutePath());
        transferService.downloadFile(video, response);
        return "Video Download Success!";
    }
}
