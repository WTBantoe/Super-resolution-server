package com.sr.utils;

import com.sr.common.PythonExecutionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

public class PythonExecutionUtilsTest
{

    @BeforeAll
    static void setPython()
    {
        Properties props = new Properties();
        props.put("python.home", "C:\\ProgramFiles\\Python310\\Lib");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
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
        PythonExecutionUtils.executePythonFileWithOutput(test_file);
    }

    @Test
    void functionTest()
    {
        File test_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\test_function.py");
        PyObject result = PythonExecutionUtils.executePythonFunction("test", test_file, "teststring");
        System.out.println(result);
    }

    @Test
    void srTest()
    {
        File sr_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\ESRGAN\\test.py");
        PythonExecutionUtils.executePythonFile(sr_file);
    }

    @Test
    void srOutputTest()
    {
        File sr_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\ESRGAN\\test.py");
        PythonExecutionUtils.executePythonFileWithOutput(sr_file);
    }

    @Test
    void srFunctionTest()
    {
        File test_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\ESRGAN\\test.py");
        PyObject result = PythonExecutionUtils.executePythonFunction("sr", test_file, "LR/*");
        System.out.println(result);
    }

}
