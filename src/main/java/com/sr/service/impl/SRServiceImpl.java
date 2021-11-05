package com.sr.service.impl;

import com.sr.common.PythonExecutionUtils;
import com.sr.service.SRService;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SRServiceImpl implements SRService
{
    static File image_python_file = new File("src/test/testfiles/ESRGAN/imageSR.py");
    static File video_python_file = new File("videoSR.py");

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
