package com.sr.controller;

import com.sr.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@RequestMapping("/download")
@Api(tags = {"下载管理"})
public class DownloadController
{
    @Autowired
    TransferService transferService;

    @GetMapping("/image")
    @ApiOperation("处理图片下载")
    @ResponseBody
    public String downloadImage(@RequestParam(value = "filename") String filename, HttpServletResponse response)
    {
        String save_path = "/projects/super-resolution/image/";
        File image = new File(save_path + filename);
        if (!image.exists())
        {
            System.out.println("图片未上传！");
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + image.getAbsolutePath());
        transferService.downloadFile(image, response);
        return "Image Download Success!";
    }

    @GetMapping("/video")
    @ApiOperation("处理视频下载")
    @ResponseBody
    public String downloadVideo(@RequestParam(value = "filename") String filename, HttpServletResponse response)
    {
        String save_path = "/projects/super-resolution/image/";
        File video = new File(save_path + filename);
        if (!video.exists())
        {
            System.out.println("视频未上传！");
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + video.getAbsolutePath());
        transferService.downloadFile(video, response);
        return "Video Download Success!";
    }
}
