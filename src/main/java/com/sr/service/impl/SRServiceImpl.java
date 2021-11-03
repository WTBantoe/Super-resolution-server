package com.sr.service.impl;

import com.sr.service.SRService;
import com.sr.common.PythonExecutionUtils;
import org.python.core.PyObject;

import java.io.File;

public class SRServiceImpl implements SRService
{

    static File image_python_file = new File("test.py");
    static File video_python_file = new File("test.py");

    @Override
    public File imageSuperResolution(File image_path)
    {
        PyObject py_result = PythonExecutionUtils.executePythonFunction("imageSR", image_python_file, image_path.getAbsolutePath());
        Object result = PythonExecutionUtils.python_class_mapping.inverse().get(py_result.getClass()).cast(py_result);
        return new File((String) result);
    }

    @Override
    public File videoSuperResolution(File video_path)
    {
        PyObject py_result = PythonExecutionUtils.executePythonFunction("videoSR", video_python_file, video_path.getAbsolutePath());
        Object result = PythonExecutionUtils.python_class_mapping.inverse().get(py_result.getClass()).cast(py_result);
        return new File((String) result);
    }
}
