package com.sr.service.impl;

import com.sr.common.PythonExecutionUtils;
import com.sr.service.SRService;
import org.python.core.PyObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class SRServiceImpl implements SRService
{

    static File image_python_file = new File("test.py");
    static File video_python_file = new File("test.py");

    @Override
    public File imageSuperResolution(File image_path)
    {
        PyObject py_result = PythonExecutionUtils.executePythonFunction("imageSR", image_python_file, image_path.getAbsolutePath());
        Object result = null;
        try
        {
            result = PythonExecutionUtils.java_class_mapping.get(py_result.getClass()).invoke(py_result);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return new File((String) result);
    }

    @Override
    public File videoSuperResolution(File video_path)
    {
        PyObject py_result = PythonExecutionUtils.executePythonFunction("videoSR", video_python_file, video_path.getAbsolutePath());
        Object result = null;
        try
        {
            result = PythonExecutionUtils.java_class_mapping.get(py_result.getClass()).invoke(py_result);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return new File((String) result);
    }
}
