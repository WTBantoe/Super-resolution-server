package com.sr.service.impl;

import com.sr.common.FileNameUtils;
import com.sr.common.ReturnCodeBuilder;
import com.sr.entity.History;
import com.sr.entity.builder.HistoryBuilder;
import com.sr.enunn.MediaTypeEnum;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.manager.RedisManager;
import com.sr.service.HistoryService;
import com.sr.service.SRService;
import com.sr.service.TransferService;
import com.sr.service.UploadService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/7 15:54
 */
@Service
public class UploadServiceImpl implements UploadService
{

    @Autowired
    TransferService transferService;

    @Autowired
    HistoryService historyService;

    @Autowired
    RedisManager redisManager;

    @Autowired
    SRService srService;

    public static String RAW_PICTURE_PATH;

    public static String RAW_VIDEO_PATH;

    public static String PROCESSED_PICTURE_PATH;

    public static String PROCESSED_VIDEO_PATH;

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

    @Value("${video.path.processed}")
    public void setProcessedVideoPath(String processedVideoPath)
    {
        PROCESSED_VIDEO_PATH = processedVideoPath;
    }

    private String saveFile(MultipartFile file, String fileName, MediaTypeEnum mediaTypeEnum)
    {
        String rawFilePath = "";
        switch (mediaTypeEnum)
        {
            case PICTURE:
                rawFilePath = RAW_PICTURE_PATH;
                break;
            case VIDEO:
                rawFilePath = RAW_VIDEO_PATH;
                break;
        }

        File material = new File(rawFilePath + fileName);
        transferService.uploadFile(file, material);

        return material.getAbsolutePath();
    }

    @NotNull
    private File saveSinglePicture(MultipartFile file, String tag, String token)
    {
        Long startTime = System.currentTimeMillis();

        String fileName = FileNameUtils.processFileName(file);

        String picturePath = saveFile(file, fileName, MediaTypeEnum.PICTURE);
        System.out.println("Image Upload Success! Saved to " + picturePath);

        srService.imageSuperResolution(new File(RAW_PICTURE_PATH + fileName), new File(PROCESSED_PICTURE_PATH + fileName));

        File processed = new File((PROCESSED_PICTURE_PATH + fileName).trim());

        if (!processed.exists())
        {
            throw new StatusException(StatusEnum.COULD_NOT_FIND_PROCESSED_PICTURE);
        }

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
        return processed;
    }

    @NotNull
    private File saveSingleVideo(MultipartFile file, String tag, String token)
    {
        Long startTime = System.currentTimeMillis();

        String fileName = FileNameUtils.processFileName(file);
        String videoPath = saveFile(file, fileName, MediaTypeEnum.VIDEO);
        System.out.println("Video Upload Success! Saved to " + videoPath);

        srService.videoSuperResolution(new File(RAW_VIDEO_PATH + fileName), new File(PROCESSED_VIDEO_PATH + fileName));

        File processed = new File((PROCESSED_VIDEO_PATH + fileName).trim());

        if (!processed.exists())
        {
            throw new StatusException(StatusEnum.COULD_NOT_FIND_PROCESSED_VIDEO);
        }

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

        History history = HistoryBuilder.aHistory().withUid(uid).withType(MediaTypeEnum.PICTURE.getCode()).withTag(tag).withRawMaterial(videoPath).withResult(videoPath).withSpan(endTime - startTime).build();

        historyService.post(history);
        return processed;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processSinglePicture(MultipartFile file, HttpServletResponse response, String tag, String token)
    {
        if (file.isEmpty())
        {
            throw new StatusException(StatusEnum.PICTURE_NOT_UPLOAD);
        }

        File processed = saveSinglePicture(file, tag, token);

        return ReturnCodeBuilder.successBuilder().addDataValue(processed.getAbsolutePath()).buildMap();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processSingleVideo(MultipartFile file, HttpServletResponse response, String tag, String token)
    {
        if (file.isEmpty())
        {
            throw new StatusException(StatusEnum.VIDEO_NOT_UPLOAD);
        }

        File processed = saveSingleVideo(file, tag, token);

        return ReturnCodeBuilder.successBuilder().addDataValue(processed.getAbsolutePath()).buildMap();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processMultiPicture(MultipartFile[] files, HttpServletResponse response, String tag, String token)
    {
        if (files.length <= 0)
        {
            throw new StatusException(StatusEnum.PICTURE_NOT_UPLOAD);
        }
        else
        {
            for (MultipartFile file : files)
            {
                if (file.isEmpty())
                {
                    throw new StatusException(StatusEnum.PICTURE_NOT_UPLOAD);
                }
            }
        }

        ArrayList<String> processed_paths = new ArrayList<>();

        for (MultipartFile file : files)
        {
            File processed = saveSinglePicture(file, tag, token);
            processed_paths.add(processed.getAbsolutePath());
        }

        return ReturnCodeBuilder.successBuilder().addDataValue(processed_paths).buildMap();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processMultiVideo(MultipartFile[] files, HttpServletResponse response, String tag, String token)
    {
        if (files.length <= 0)
        {
            throw new StatusException(StatusEnum.VIDEO_NOT_UPLOAD);
        }
        else
        {
            for (MultipartFile file : files)
            {
                if (file.isEmpty())
                {
                    throw new StatusException(StatusEnum.VIDEO_NOT_UPLOAD);
                }
            }
        }

        ArrayList<String> processed_paths = new ArrayList<>();

        for (MultipartFile file : files)
        {
            File processed = saveSingleVideo(file, tag, token);
            processed_paths.add(processed.getAbsolutePath());
        }

        return ReturnCodeBuilder.successBuilder().addDataValue(processed_paths).buildMap();
    }
}
