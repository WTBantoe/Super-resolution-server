package com.sr.utils;

import com.sr.common.PythonExecutionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.python.core.PyObject;

import java.io.File;
import java.util.ArrayList;

public class PythonExecutionUtilsTest
{

    File test_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\test.py");
    File test_import_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\test_import.py");

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
        PythonExecutionUtils.executePythonFile(test_file);
    }

    @Test
    void fileOutputTest()
    {
        String[] result = PythonExecutionUtils.executePythonFileWithOutput(test_file);
        for (String output : result)
        {
            System.out.println(output);
        }
    }

    @Test
    void functionTest()
    {
        PyObject result = PythonExecutionUtils.executePythonFunction("testString", test_file, "TEST");
        System.out.println(result);
    }

    @Test
    void importFileTest()
    {
        PythonExecutionUtils.executePythonFile(test_import_file);
    }

    @Test
    void importFileOutputTest()
    {
        String[] result = PythonExecutionUtils.executePythonFileWithOutput(test_import_file);
        for (String output : result)
        {
            System.out.println(output);
        }
    }

    @Test
    void importFunctionTest()
    {
        PyObject result = PythonExecutionUtils.executePythonFunction("sr", test_file, "LR/*");
        System.out.println(result);
    }

}
