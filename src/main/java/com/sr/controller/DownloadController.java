package com.sr.controller;

import com.sr.common.MediaInfoUtils;
import com.sr.common.ReturnCodeBuilder;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/download")
@Api(tags = {"下载管理"})
public class DownloadController
{
    @Autowired
    TransferService transferService;

    private static final double TIME_PER_IMAGE = 20.0;
    private static final double STANDARD_IMAGE_WIDTH = 125.0;
    private static final double STANDARD_IMAGE_HEIGHT = 120.0;

    private static final double TIME_PER_FRAME = 2.5;
    private static final double STANDARD_FRAME_WIDTH = 180.0;
    private static final double STANDARD_FRAME_HEIGHT = 120.0;

    public static String PROCESSED_PICTURE_PATH;

    public static String PROCESSED_VIDEO_PATH;

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

    @PostMapping("/image/isprocessed")
    @ApiOperation("判断图片是否已经处理完毕")
    public Map<String, Object> imageExist(@RequestParam(value = "rawFilePath") String rawFilePath, @RequestParam(value = "processedFilePath") String processedFilePath)
    {
        HashMap<String, Object> return_message = new HashMap<>();
        File processedFile = new File(processedFilePath);
        if (processedFile.exists())
        {
            return_message.put("time", 0);
            return_message.put("processed", true);
            return ReturnCodeBuilder.successBuilder().addDataValue(return_message).buildMap();
        }
        else
        {
            try
            {
                HashMap<String, Object> imageInfo = MediaInfoUtils.getImageInfo(rawFilePath);
                int image_width = (int) imageInfo.get("width");
                int image_height = (int) imageInfo.get("height");
                double totalTime = TIME_PER_IMAGE * (image_width * image_height) / (STANDARD_IMAGE_WIDTH * STANDARD_IMAGE_HEIGHT);
                return_message.put("time", totalTime);
                return_message.put("processed", false);
                return ReturnCodeBuilder.successBuilder().addDataValue(return_message).buildMap();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return_message.put("time", -1);
                return_message.put("processed", false);
                return_message.put("error", e.getMessage());
                return ReturnCodeBuilder.successBuilder().addDataValue(return_message).buildMap();
            }
        }
    }

    @PostMapping("/video/isprocessed")
    @ApiOperation("判断视频是否已经处理完毕")
    public Map<String, Object> videoExist(@RequestParam(value = "rawFilePath") String rawFilePath, @RequestParam(value = "processedFilePath") String processedFilePath)
    {
        HashMap<String, Object> return_message = new HashMap<>();
        File processedFile = new File(processedFilePath);
        if (processedFile.exists())
        {
            return_message.put("time", 0);
            return_message.put("processed", true);
            return ReturnCodeBuilder.successBuilder().addDataValue(return_message).buildMap();
        }
        else
        {
            try
            {
                HashMap<String, Object> videoInfo = MediaInfoUtils.getVideoInfo(rawFilePath);
                double frame_count = (double) videoInfo.get("frame_count");
                int video_width = (int) videoInfo.get("width");
                int video_height = (int) videoInfo.get("height");
                double totalTime = (frame_count * TIME_PER_FRAME) * (video_width * video_height) / (STANDARD_FRAME_WIDTH * STANDARD_FRAME_HEIGHT);
                return_message.put("time", totalTime);
                return_message.put("processed", false);
                return ReturnCodeBuilder.successBuilder().addDataValue(return_message).buildMap();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return_message.put("time", -1);
                return_message.put("processed", false);
                return_message.put("error", e.getMessage());
                return ReturnCodeBuilder.successBuilder().addDataValue(return_message).buildMap();
            }
        }
    }
}
