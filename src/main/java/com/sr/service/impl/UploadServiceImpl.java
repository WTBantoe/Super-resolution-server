package com.sr.service.impl;

import com.sr.common.FileNameUtils;
import com.sr.common.ReturnCodeBuilder;
import com.sr.common.StringUtil;
import com.sr.entity.History;
import com.sr.entity.UserInfo;
import com.sr.entity.builder.HistoryBuilder;
import com.sr.entity.builder.UserInfoBuilder;
import com.sr.enunn.MediaTypeEnum;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.manager.RedisManager;
import com.sr.service.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    UserService userService;

    public static String RAW_PICTURE_FOLDER;

    public static String RAW_VIDEO_FOLDER;

    public static String PROCESSED_PICTURE_FOLDER;

    public static String PROCESSED_VIDEO_FOLDER;

    public static String AVATAR_PATH;

    public static String COMMENT_PATH;

    @Value("${comment.path}")
    public void setCommentPath(String commentPath)
    {
        COMMENT_PATH = commentPath;
    }

    @Value("${picture.path.raw}")
    public void setRawPicturePath(String rawPicturePath)
    {
        RAW_PICTURE_FOLDER = rawPicturePath;
    }

    @Value("${video.path.raw}")
    public void setRawVideoPath(String rawVideoPath)
    {
        RAW_VIDEO_FOLDER = rawVideoPath;
    }

    @Value("${picture.path.processed}")
    public void setProcessedPicturePath(String processedPicturePath)
    {
        PROCESSED_PICTURE_FOLDER = processedPicturePath;
    }

    @Value("${video.path.processed}")
    public void setProcessedVideoPath(String processedVideoPath)
    {
        PROCESSED_VIDEO_FOLDER = processedVideoPath;
    }

    @Value("${avatar.path}")
    public void setAvatarPath(String avatarPath)
    {
        AVATAR_PATH = avatarPath;
    }

    private File getSavePath(String fileName, MediaTypeEnum mediaTypeEnum)
    {
        String rawFilePath = "/";

        switch (mediaTypeEnum)
        {
            case PICTURE:
                rawFilePath = RAW_PICTURE_FOLDER;
                break;
            case VIDEO:
                rawFilePath = RAW_VIDEO_FOLDER;
                break;
            case AVATAR:
                rawFilePath = AVATAR_PATH;
                break;
            case COMMENT:
                rawFilePath = COMMENT_PATH;
                break;
        }

        return new File(rawFilePath + fileName);
    }

    private File getProcessedPath(String fileName, MediaTypeEnum mediaTypeEnum)
    {
        String rawFilePath = "/";

        switch (mediaTypeEnum)
        {
            case PICTURE:
                rawFilePath = PROCESSED_PICTURE_FOLDER;
                break;
            case VIDEO:
                rawFilePath = PROCESSED_VIDEO_FOLDER;
                break;
        }

        return new File(rawFilePath + fileName);
    }

    private long getUid(String token)
    {
        long uid;
        try
        {
            uid = Long.parseLong((String) redisManager.hGet(UserServiceImpl.REDIS_TOKEN_KEY, token));
        }
        catch (Exception e)
        {
            throw new StatusException(StatusEnum.TOKEN_EXPIRE);
        }
        return uid;
    }

    private void saveFile(MultipartFile file, File destination)
    {
        transferService.uploadFile(file, destination);
    }

    @NotNull
    private String[] handleSinglePicture(MultipartFile file)
    {
        String fileName = FileNameUtils.processFileName(file);

        File rawPicturePath = getSavePath(fileName, MediaTypeEnum.PICTURE);
        File processedPicturePath = getProcessedPath(fileName,MediaTypeEnum.PICTURE);

        new Thread(()->{
            saveFile(file, rawPicturePath);
            System.out.println("Image Upload Success! Saved to " + rawPicturePath.getAbsolutePath());
            srService.imageSuperResolution(rawPicturePath, processedPicturePath);
        }).start();

//  TODO:放到下载
//        File processed = new File((PROCESSED_PICTURE_FOLDER + fileName).trim());
//
//        if (!processed.exists())
//        {
//            throw new StatusException(StatusEnum.COULD_NOT_FIND_PROCESSED_PICTURE);
//        }

        return new String[]{rawPicturePath.getAbsolutePath(), processedPicturePath.getAbsolutePath()};
    }

    @NotNull
    private String[] handleSingleVideo(MultipartFile file)
    {
        String fileName = FileNameUtils.processFileName(file);

        File rawVideoPath = getSavePath(fileName, MediaTypeEnum.VIDEO);
        File processedVideoPath = getProcessedPath(fileName, MediaTypeEnum.VIDEO);

        new Thread(() ->{
            saveFile(file, rawVideoPath);
            System.out.println("Video Upload Success! Saved to " + rawVideoPath.getAbsolutePath());
            srService.videoSuperResolution(rawVideoPath, processedVideoPath);
        }).start();

//  TODO:放到下载
//        File processed = new File((PROCESSED_VIDEO_FOLDER + fileName).trim());
//
//        if (!processed.exists())
//        {
//            throw new StatusException(StatusEnum.COULD_NOT_FIND_PROCESSED_VIDEO);
//        }

        return new String[]{rawVideoPath.getAbsolutePath(),processedVideoPath.getAbsolutePath()};
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processSinglePicture(MultipartFile file, HttpServletResponse response, String tag, String token)
    {
        Long startTime = System.currentTimeMillis();
        long uid = getUid(token);

        if (file.isEmpty())
        {
            throw new StatusException(StatusEnum.PICTURE_NOT_UPLOAD);
        }

        String[] raw_and_processed = handleSinglePicture(file);

        Long endTime = System.currentTimeMillis();

        History history = HistoryBuilder.aHistory()
                .withUid(uid)
                .withType(MediaTypeEnum.PICTURE.getCode())
                .withTag(tag)
                .withRawMaterial(raw_and_processed[0])
                .withResult(raw_and_processed[1])
                .withSpan(endTime - startTime)
                .build();

        historyService.post(history);

        return ReturnCodeBuilder.successBuilder().addDataValue(raw_and_processed).buildMap();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processSingleVideo(MultipartFile file, HttpServletResponse response, String tag, String token)
    {
        Long startTime = System.currentTimeMillis();
        long uid = getUid(token);

        if (file.isEmpty())
        {
            throw new StatusException(StatusEnum.VIDEO_NOT_UPLOAD);
        }

        String[] raw_and_processed = handleSingleVideo(file);

        Long endTime = System.currentTimeMillis();

        History history = HistoryBuilder.aHistory()
                .withUid(uid)
                .withType(MediaTypeEnum.VIDEO.getCode())
                .withTag(tag)
                .withRawMaterial(raw_and_processed[0])
                .withResult(raw_and_processed[1])
                .withSpan(endTime - startTime)
                .build();

        historyService.post(history);

        return ReturnCodeBuilder.successBuilder().addDataValue(raw_and_processed).buildMap();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processMultiPicture(MultipartFile[] files, HttpServletResponse response, String tag, String token)
    {
        Long startTime = System.currentTimeMillis();
        long uid = getUid(token);

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

        ArrayList<String[]> raw_and_processed_paths = new ArrayList<>();

        for (MultipartFile file : files)
        {
            String[] raw_and_processed = handleSinglePicture(file);
            raw_and_processed_paths.add(raw_and_processed);

            Long endTime = System.currentTimeMillis();
            History history = HistoryBuilder.aHistory()
                    .withUid(uid)
                    .withType(MediaTypeEnum.PICTURE.getCode())
                    .withTag(tag)
                    .withRawMaterial(raw_and_processed[0])
                    .withResult(raw_and_processed[1])
                    .withSpan(endTime - startTime)
                    .build();

            historyService.post(history);
            startTime = System.currentTimeMillis();
        }

        return ReturnCodeBuilder.successBuilder().addDataValue(raw_and_processed_paths).buildMap();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processMultiVideo(MultipartFile[] files, HttpServletResponse response, String tag, String token)
    {
        Long startTime = System.currentTimeMillis();
        long uid = getUid(token);

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

        ArrayList<String[]> raw_and_processed_paths = new ArrayList<>();

        for (MultipartFile file : files)
        {
            String[] raw_and_processed = handleSingleVideo(file);
            raw_and_processed_paths.add(raw_and_processed);

            Long endTime = System.currentTimeMillis();
            History history = HistoryBuilder.aHistory()
                    .withUid(uid)
                    .withType(MediaTypeEnum.VIDEO.getCode())
                    .withTag(tag)
                    .withRawMaterial(raw_and_processed[0])
                    .withResult(raw_and_processed[1])
                    .withSpan(endTime - startTime)
                    .build();

            historyService.post(history);
            startTime = System.currentTimeMillis();
        }

        return ReturnCodeBuilder.successBuilder().addDataValue(raw_and_processed_paths).buildMap();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> uploadAvatar(MultipartFile file, Long uid) {
        String fileName = FileNameUtils.processFileName(file);

        File rawPicturePath = getSavePath(fileName, MediaTypeEnum.AVATAR);

        saveFile(file, rawPicturePath);

        System.out.println("Image Upload Success! Saved to " + rawPicturePath.getAbsolutePath());

        UserInfo userInfo = UserInfoBuilder.anUserInfo().withAvatar(rawPicturePath.getAbsolutePath()).build();

        return userService.modifyUserInfo(userInfo, uid);
    }

    @Override
    public String uploadComment(MultipartFile[] files) {
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
        List<String> commentMedias = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = FileNameUtils.processFileName(file);
            File commentPath = getSavePath(fileName, MediaTypeEnum.COMMENT);
            saveFile(file, commentPath);
            commentMedias.add(commentPath.getAbsolutePath());
        }

        return StringUtil.getBindLinkSeparateByComma(commentMedias);
    }
}
