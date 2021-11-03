package com.sr.common;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.NotNull;
import org.python.core.*;
import org.python.jline.internal.InputStreamReader;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;


public class PythonExecutionUtils
{
    public static BiMap<Class, Class> python_class_mapping = HashBiMap.create();

    static
    {
        python_class_mapping.clear();
        python_class_mapping.put(Integer.class, PyInteger.class);
        python_class_mapping.put(Double.class, PyFloat.class);
        python_class_mapping.put(String.class, PyString.class);
        python_class_mapping.put(Arrays.class, PyArray.class);
        python_class_mapping.put(Dictionary.class, PyDictionary.class);
        python_class_mapping.put(File.class, PyFile.class);
    }

    @NotNull
    public static PythonInterpreter executePythonStatement(String[] statements)
    {
        PythonInterpreter interpreter = new PythonInterpreter();
        for (String statement : statements)
        {
            interpreter.exec(statement);
        }
        return interpreter;
    }

    @NotNull
    public static PythonInterpreter executePythonFile(File python_file)
    {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile(python_file.getAbsolutePath());
        return interpreter;
    }

    @NotNull
    public static String[] executePythonFileWithOutput(File python_file)
    {
        Process process;
        ArrayList<String> result = new ArrayList<>();
        try
        {
            process = Runtime.getRuntime().exec("python " + python_file.getAbsolutePath());
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = input.readLine()) != null)
            {
                System.out.println(line);
                result.add(line);
            }
            input.close();
            process.waitFor();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            String[] return_result = new String[result.size()];
            result.toArray(return_result);
            return return_result;
        }
    }

    @NotNull
    public static PyObject executePythonFunction(String function_name, File python_file, Object... params)
    {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile(python_file.getAbsolutePath());
        PyFunction function = interpreter.get(function_name, PyFunction.class);
        PyObject[] python_prams = new PyObject[params.length];
        for (int i = 0; i < params.length; i++)
        {
            python_prams[i] = (PyObject) python_class_mapping.get(params[i].getClass()).cast(params[i]);
        }
        PyObject result = function.__call__(python_prams);
        return result;
    }
}
