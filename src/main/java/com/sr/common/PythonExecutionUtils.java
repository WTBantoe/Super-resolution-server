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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;


public class PythonExecutionUtils
{
    public static BiMap<Class, Class> python_class_mapping = HashBiMap.create();
    public static HashMap<Class, Method> java_class_mapping = new HashMap<>();

    public static File python_path;

    public static File getPython_path()
    {
        return python_path;
    }

    public static void setPython_path(File python_path)
    {
        PythonExecutionUtils.python_path = python_path;
    }

    static
    {
        python_class_mapping.clear();
        python_class_mapping.put(Integer.class, PyInteger.class);
        python_class_mapping.put(Double.class, PyFloat.class);
        python_class_mapping.put(String.class, PyString.class);
        python_class_mapping.put(Arrays.class, PyArray.class);
        python_class_mapping.put(Dictionary.class, PyDictionary.class);
        python_class_mapping.put(File.class, PyFile.class);

        java_class_mapping.clear();
        try
        {
            java_class_mapping.put(PyInteger.class, PyInteger.class.getMethod("getValue"));
            java_class_mapping.put(PyFloat.class, PyFloat.class.getMethod("getValue"));
            java_class_mapping.put(PyString.class, PyString.class.getMethod("getString"));
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
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
            process = Runtime.getRuntime().exec(new String[]{python_path.getAbsolutePath(), python_file.getAbsolutePath()});
            BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
//            while ((line = error.readLine()) != null)
//            {
//                System.out.println(line);
//                result.add(line);
//            }
            while ((line = output.readLine()) != null)
            {
                result.add(line);
            }
            output.close();
            process.waitFor();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        String[] return_result = new String[result.size()];
        result.toArray(return_result);
        return return_result;
    }

    @NotNull
    public static String[] executePythonFileWithOutput(File python_file, File input_file, File output_file)
    {
        Process process;
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> err_result = new ArrayList<>();
        try
        {
            process = Runtime.getRuntime().exec(new String[]{python_path.getAbsolutePath(), python_file.getAbsolutePath(), "--input", input_file.getAbsolutePath(), "--output", output_file.getAbsolutePath()});

//            final InputStream output_stream = process.getInputStream();
//            final InputStream error_stream = process.getErrorStream();
//            new Thread(() ->
//            {
//                BufferedReader output = new BufferedReader(new InputStreamReader(output_stream));
//                try
//                {
//                    String outputLine = null;
//                    while ((outputLine = output.readLine()) != null)
//                    {
//                        System.out.println(outputLine);
//                        result.add(outputLine);
//                    }
//                }
//                catch (IOException e)
//
//                {
//                    e.printStackTrace();
//                }
//                finally
//                {
//                    try
//                    {
//                        output_stream.close();
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//
//            new Thread(() ->
//            {
//                BufferedReader output = new BufferedReader(new InputStreamReader(error_stream));
//                try
//                {
//                    String errorLine = null;
//                    while ((errorLine = output.readLine()) != null)
//                    {
//                        System.out.println(errorLine);
//                        err_result.add(errorLine);
//                    }
//                }
//                catch (IOException e)
//
//                {
//                    e.printStackTrace();
//                }
//                finally
//
//                {
//                    try
//                    {
//                        output_stream.close();
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();

            BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String errorLine;
            String outputLine;

            while ((errorLine = error.readLine()) != null)
            {
                System.out.println(errorLine);
                err_result.add(errorLine);
            }
            while ((outputLine = output.readLine()) != null)
            {
                System.out.println(outputLine);
                result.add(outputLine);
            }
            error.close();
            output.close();
            process.waitFor();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        ArrayList<String> full_result = new ArrayList<>();
        full_result.addAll(result);
        full_result.addAll(err_result);
        String[] return_result = new String[full_result.size()];
        full_result.toArray(return_result);
        return return_result;
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
            try
            {
                python_prams[i] = (PyObject) python_class_mapping.get(params[i].getClass()).getConstructor(params[i].getClass()).newInstance(params[i]);
            }
            catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return function.__call__(python_prams);
    }
}
