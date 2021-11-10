package com.sr.controller;

import com.sr.common.HttpUtil;
import com.sr.manager.RedisManager;
import com.sr.service.TransferService;
import com.sr.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload")
@Api(tags = {"上传管理"})
public class UploadController
{
    @Autowired
    TransferService transferService;

    @Autowired
    RedisManager redisManager;

    @Autowired
    HttpUtil httpUtil;


    @Autowired
    UploadService uploadService;

    public static String RAW_PICTURE_PATH;

    public static String RAW_VIDEO_PATH;

    public static String PROCESSED_PICTURE_PATH;

    @Value("${picture.path.raw}")
    public void setRawPicturePath(String rawPicturePath)
    {
        RAW_PICTURE_PATH = rawPicturePath;
    }

    @Value("${video.path.raw}")
    public void setRawVideoPath(String rawVideoPath)
    {
        RAW_VIDEO_PATH = rawVideoPath;
    }

    @Value("${picture.path.processed}")
    public void setProcessedPicturePath(String processedPicturePath)
    {
        PROCESSED_PICTURE_PATH = processedPicturePath;
    }


    @PostMapping("/image/single")
    @ApiOperation("处理图片上传")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> uploadImage(@RequestParam(value = "image") MultipartFile file, @RequestParam(value = "tag", required = false) String tag, HttpServletResponse response, HttpServletRequest httpServletRequest)
    {
        String token = httpUtil.getToken(httpServletRequest);
        return uploadService.processSinglePicture(file, response, tag, token);
    }


    @PostMapping("/video/single")
    @ApiOperation("处理视频上传")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> uploadVideo(@RequestParam(value = "video") MultipartFile file, @RequestParam(value = "tag") String tag, HttpServletResponse response, HttpServletRequest httpServletRequest)
    {
        String token = httpUtil.getToken(httpServletRequest);
        return uploadService.processSingleVideo(file, response, tag, token);
    }

    @PostMapping("/image/multi")
    @ApiOperation("处理多图片上传")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> uploadMultiImage(@RequestParam(value = "images") MultipartFile[] files, @RequestParam(value = "tag", required = false) String tag, HttpServletResponse response, HttpServletRequest httpServletRequest)
    {
        String token = httpUtil.getToken(httpServletRequest);
        return uploadService.processMultiPicture(files, response, tag, token);
    }

    @PostMapping("/video/multi")
    @ApiOperation("处理多视频上传")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> uploadMultiVideo(@RequestParam(value = "videos") MultipartFile[] files, @RequestParam(value = "tag") String tag, HttpServletResponse response, HttpServletRequest httpServletRequest)
    {
        String token = httpUtil.getToken(httpServletRequest);
        return uploadService.processMultiVideo(files, response, tag, token);
    }


    public String uploadAvatar()
    {
        // TODO
        return null;
    }
}
