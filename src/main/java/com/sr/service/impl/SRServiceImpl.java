package com.sr.service.impl;

import com.sr.common.PythonExecutionUtils;
import com.sr.service.SRService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SRServiceImpl implements SRService
{
    static File image_python_file;
    static File video_python_file;

    public File getImage_python_file()
    {
        return image_python_file;
    }

    @Value("${model.path.image}")
    public void setImage_python_file(String image_python_file)
    {
        SRServiceImpl.image_python_file = new File(image_python_file);
    }

    public File getVideo_python_file()
    {
        return video_python_file;
    }

    @Value("${model.path.video}")
    public void setVideo_python_file(String video_python_file)
    {
        SRServiceImpl.video_python_file = new File(video_python_file);
    }

    @Override
    public String[] imageSuperResolution()
    {
        PythonExecutionUtils.setPython_path(new File("C:\\ProgramFiles\\Python37\\python.exe"));
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
        PythonExecutionUtils.setPython_path(new File("C:\\ProgramFiles\\Python37\\python.exe"));
        String[] outputs = PythonExecutionUtils.executePythonFileWithOutput(video_python_file);
        for (String output : outputs)
        {
            System.out.println(output);
        }
        return outputs;
    }
}
