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
//        String save_path = "/data/sr/prod/pic/raw";
        String save_path = "/data/sr/test/pic/raw";
        file_name = UUID.randomUUID() + ext_name;
        File image = new File(save_path + file_name);
        transferService.uploadFile(file, image);
        System.out.println("Image Upload Success! Saved to " + image.getAbsolutePath());
        return file_name;
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
//        String save_path = "/data/sr/prod/vid/raw";
        String save_path = "/data/sr/test/vid/raw";
        file_name = UUID.randomUUID() + ext_name;
        File video = new File(save_path + file_name);
        transferService.uploadFile(file, video);
        System.out.println("Video Upload Success! Saved to" + video.getAbsolutePath());
        return file_name;
    }
}
