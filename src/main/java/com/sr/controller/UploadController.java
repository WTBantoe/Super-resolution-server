package com.sr.controller;

import com.sr.common.ReturnCodeBuilder;
import com.sr.enunn.MediaTypeEnum;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@Api(tags = {"上传管理"})
public class UploadController
{
    @Autowired
    TransferService transferService;


    public static String RAW_PICTURE_PATH;

    public static String RAW_VIDEO_PATH;

    @Value("${picture.path.raw}")
    public void setRawPicturePath(String rawPicturePath) {
        RAW_PICTURE_PATH = rawPicturePath;
    }

    @Value("${video.path.raw}")
    public void setRawVideoPath(String rawVideoPath) {
        RAW_VIDEO_PATH = rawVideoPath;
    }


    @PostMapping("/image/single")
    @ApiOperation("处理图片上传")
    public Map<String, Object> uploadImage(@RequestParam(value = "image") MultipartFile file, HttpServletResponse response)
    {
        if (file.isEmpty())
        {
            throw new StatusException(StatusEnum.PICTURE_NOT_UPLOAD);
        }

        String picturePath = saveFile(file,MediaTypeEnum.PICTURE);
        System.out.println("Image Upload Success! Saved to " + picturePath);

        File processed = new File(picturePath.trim());

        if (!processed.exists())
        {
            throw new StatusException(StatusEnum.COULD_NOT_FIND_PROCESSED_PICTURE);
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + processed.getAbsolutePath());
        transferService.downloadFile(processed, response);

        return ReturnCodeBuilder.successBuilder()
                .buildMap();
    }

    @PostMapping("/video/single")
    @ApiOperation("处理视频上传")
    public Map<String, Object> uploadVideo(@RequestParam(value = "video") MultipartFile file)
    {
        if (file.isEmpty())
        {
            throw new StatusException(StatusEnum.VIDEO_NOT_UPLOAD);
        }

        String videoPath = saveFile(file,MediaTypeEnum.VIDEO);
        System.out.println("Video Upload Success! Saved to" + videoPath);
        File video = new File(videoPath);

        return ReturnCodeBuilder.successBuilder()
                .buildMap();
    }

    private String saveFile(MultipartFile file, MediaTypeEnum mediaTypeEnum){
        String fileName = file.getOriginalFilename();
        if (fileName == null){
            throw new StatusException(StatusEnum.PICTURE_NOT_UPLOAD);
        }

        if (!fileName.contains(".")){
            throw new StatusException((StatusEnum.INVALID_FILE_TYPE));
        }

        String extName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + extName;

        String filePath = "";
        switch (mediaTypeEnum){
            case PICTURE:
                filePath = RAW_PICTURE_PATH;
                break;
            case VIDEO:
                filePath = RAW_VIDEO_PATH;
                break;
        }

//        File material = new File(filePath + fileName);
//        transferService.uploadFile(file, material);

        //TODO
        File processed = new File("/data/pic/processed" + fileName);
        transferService.uploadFile(file, processed);

        return processed.getAbsolutePath();
    }
}
