package com.sr.utils;

import com.sr.common.PythonExecutionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.python.core.PyObject;

import java.io.File;
import java.util.ArrayList;

public class PythonExecutionUtilsTest
{

    @BeforeAll
    static void setPython()
    {
        PythonExecutionUtils.setPython_path(new File("C:\\ProgramFiles\\Python37\\python.exe"));
    }

    @Test
    void statementTest()
    {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("print('Hello World!')");
        String[] statements = new String[strings.size()];
        strings.toArray(statements);
        PythonExecutionUtils.executePythonStatement(statements);
    }

    @Test
    void fileTest()
    {
        File test_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\test.py");
        PythonExecutionUtils.executePythonFile(test_file);
    }

    @Test
    void fileOutputTest()
    {
        File test_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\test.py");
        String[] result = PythonExecutionUtils.executePythonFileWithOutput(test_file);
        for (String output : result)
        {
            System.out.println(output);
        }
    }

    @Test
    void functionTest()
    {
        File test_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\test_function.py");
        PyObject result = PythonExecutionUtils.executePythonFunction("test", test_file, "teststring");
        System.out.println(result);
    }

    @Test
    void srFileTest()
    {
        File sr_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\ESRGAN\\test.py");
        PythonExecutionUtils.executePythonFile(sr_file);
    }

    @Test
    void srFileOutputTest()
    {
        File sr_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\ESRGAN\\test.py");
        String[] result = PythonExecutionUtils.executePythonFileWithOutput(sr_file);
        for (String output : result)
        {
            System.out.println(output);
        }
    }

    @Test
    void srFunctionTest()
    {
        File test_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\ESRGAN\\test.py");
        PyObject result = PythonExecutionUtils.executePythonFunction("sr", test_file, "LR/*");
        System.out.println(result);
    }

}
