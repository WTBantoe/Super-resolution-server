package com.sr.controller;

import com.sr.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
@Api(tags = {"上传管理"})
public class UploadController
{
    @Autowired
    TransferService transferService;

    @PostMapping("/image")
    @ApiOperation("处理图片上传")
    @ResponseBody
    public String uploadImage(@RequestParam(value = "image") MultipartFile file)
    {
        if (file.isEmpty())
        {
            System.out.println("未上传图片！");
        }
        String file_name = file.getOriginalFilename();
        String ext_name = file_name.substring(file_name.lastIndexOf("."));
        String save_path = "/projects/super-resolution/image/";
        file_name = UUID.randomUUID() + ext_name;
        File image = new File(save_path + file_name);
        transferService.uploadFile(file, image);
        return "Image Upload Success!";
    }

    @PostMapping("/video")
    @ApiOperation("处理视频上传")
    @ResponseBody
    public String uploadVideo(@RequestParam(value = "video") MultipartFile file)
    {
        if (file.isEmpty())
        {
            System.out.println("未上传视频！");
        }
        String file_name = file.getOriginalFilename();
        String ext_name = file_name.substring(file_name.lastIndexOf("."));
        String save_path = "/projects/super-resolution/video/";
        file_name = UUID.randomUUID() + ext_name;
        File video = new File(save_path + file_name);
        transferService.uploadFile(file, video);
        return "Video Upload Success!";
    }
}
