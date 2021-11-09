package com.sr.service.impl;

import com.sr.common.PythonExecutionUtils;
import com.sr.service.SRService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SRServiceImpl implements SRService
{
    static File python_exe_file;

    static File image_python_file;

    static File video_to_image_file;
    static File video_python_file;
    static File image_to_video_file;

    static String RAW_VIDEO_FRAME_PATH;
    static String PROCESSED_VIDEO_FRAME_PATH;

    @Value("${python.path}")
    public void setPython_exe_file(String python_exe_file)
    {
        SRServiceImpl.python_exe_file = new File(python_exe_file);
    }

    @Value("${model.path.image}")
    public void setImage_python_file(String image_python_file)
    {
        SRServiceImpl.image_python_file = new File(image_python_file);
    }

    @Value("${model.path.video}")
    public void setVideo_python_file(String video_python_file)
    {
        SRServiceImpl.video_python_file = new File(video_python_file);
    }

    @Value("${video.path.raw_frame}")
    public void setRawVideoFramePath(String rawVideoFramePath)
    {
        RAW_VIDEO_FRAME_PATH = rawVideoFramePath;
    }

    @Value("${video.path.processed_frame}")
    public void setProcessedVideoFramePath(String processedVideoFramePath)
    {
        PROCESSED_VIDEO_FRAME_PATH = processedVideoFramePath;
    }

    @Value("${tool.path.video2image}")
    public void setVideo_to_image_file(String video_to_image_file)
    {
        SRServiceImpl.video_to_image_file = new File(video_to_image_file);
    }

    @Value("${tool.path.image2video}")
    public void setImage_to_video_file(String image_to_video_file)
    {
        SRServiceImpl.image_to_video_file = new File(image_to_video_file);
    }

    @Override
    public String[] imageSuperResolution()
    {
        PythonExecutionUtils.setPython_path(python_exe_file);
        String[] outputs = PythonExecutionUtils.executePythonFileWithOutput(image_python_file);
        for (String output : outputs)
        {
            System.out.println(output);
        }
        return outputs;
    }

    @Override
    public String[] videoSuperResolution()
    {
        PythonExecutionUtils.setPython_path(python_exe_file);
        String[] outputs = PythonExecutionUtils.executePythonFileWithOutput(video_python_file);
        for (String output : outputs)
        {
            System.out.println(output);
        }
        return outputs;
    }

    @Override
    public String[] imageSuperResolution(File input_file, File output_file)
    {
        PythonExecutionUtils.setPython_path(python_exe_file);
        String[] outputs = PythonExecutionUtils.executePythonFileWithOutput(image_python_file, input_file, output_file);
        for (String output : outputs)
        {
            System.out.println(output);
        }
        return outputs;
    }

    @Override
    public String[] videoSuperResolution(File input_file, File output_file)
    {
        PythonExecutionUtils.setPython_path(python_exe_file);
        String folder_name = input_file.getName().split("\\.")[0];
        PythonExecutionUtils.executePythonFileWithOutput(video_to_image_file, input_file, new File(RAW_VIDEO_FRAME_PATH + folder_name));
        String[] outputs = PythonExecutionUtils.executePythonFileWithOutput(video_python_file, new File(RAW_VIDEO_FRAME_PATH + folder_name), new File(PROCESSED_VIDEO_FRAME_PATH + folder_name));
        PythonExecutionUtils.executePythonFileWithOutput(image_to_video_file, new File(PROCESSED_VIDEO_FRAME_PATH + folder_name), output_file);
        for (String output : outputs)
        {
            System.out.println(output);
        }
        return outputs;
    }
}
