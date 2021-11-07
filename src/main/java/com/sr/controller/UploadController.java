package com.sr.controller;

import com.sr.common.HttpUtil;
import com.sr.entity.History;
import com.sr.entity.builder.HistoryBuilder;
import com.sr.enunn.MediaTypeEnum;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.manager.RedisManager;
import com.sr.service.HistoryService;
import com.sr.service.SRService;
import com.sr.service.TransferService;
import com.sr.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.UUID;

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
    HistoryService historyService;

    @Autowired
    HttpUtil httpUtil;

    @Autowired
    SRService srService;

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
    public void uploadImage(@RequestParam(value = "image") MultipartFile file, HttpServletResponse response, @RequestParam(value = "tag", required = false) String tag, HttpServletRequest httpServletRequest)
    {
        if (file.isEmpty())
        {
            throw new StatusException(StatusEnum.PICTURE_NOT_UPLOAD);
        }

        Long startTime = System.currentTimeMillis();

        String picturePath = saveFile(file, MediaTypeEnum.PICTURE);
        System.out.println("Image Upload Success! Saved to " + picturePath);

        File processed = new File(picturePath.trim());

        if (!processed.exists())
        {
            throw new StatusException(StatusEnum.COULD_NOT_FIND_PROCESSED_PICTURE);
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + processed.getName().substring(36));
        transferService.downloadFile(processed, response);
        srService.imageSuperResolution();

        String token = httpUtil.getToken(httpServletRequest);

        long uid;
        try
        {
            uid = Long.parseLong((String) redisManager.hGet(UserServiceImpl.REDIS_TOKEN_KEY, token));
        }
        catch (Exception e)
        {
            throw new StatusException(StatusEnum.TOKEN_EXPIRE);
        }

        Long endTime = System.currentTimeMillis();

        History history = HistoryBuilder.aHistory().withUid(uid).withType(MediaTypeEnum.PICTURE.getCode()).withTag(tag).withRawMaterial(picturePath).withResult(picturePath).withSpan(endTime - startTime).build();

        historyService.post(history);
    }

    @PostMapping("/video/single")
    @ApiOperation("处理视频上传")
    public void uploadVideo(@RequestParam(value = "video") MultipartFile file, @RequestParam(value = "tag") String tag, HttpServletRequest httpServletRequest)
    {
        if (file.isEmpty())
        {
            throw new StatusException(StatusEnum.VIDEO_NOT_UPLOAD);
        }

        String videoPath = saveFile(file, MediaTypeEnum.VIDEO);
        System.out.println("Video Upload Success! Saved to" + videoPath);
        srService.videoSuperResolution();
        File video = new File(videoPath);
    }

    private String saveFile(MultipartFile file, MediaTypeEnum mediaTypeEnum)
    {
        String fileName = file.getOriginalFilename();
        if (fileName == null)
        {
            throw new StatusException(StatusEnum.PICTURE_NOT_UPLOAD);
        }

        if (!fileName.contains("."))
        {
            throw new StatusException((StatusEnum.INVALID_FILE_TYPE));
        }
        File currentFile = new File(fileName);
        fileName = UUID.randomUUID() + currentFile.getName();

        String filePath = "";
        switch (mediaTypeEnum)
        {
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
        File processed = new File(PROCESSED_PICTURE_PATH + fileName);
        transferService.uploadFile(file, processed);

        return processed.getAbsolutePath();
    }

    public String uploadAvatar(){
        // TODO
        return null;
    }
}
