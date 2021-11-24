package com.sr.common;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.python.core.PyObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PythonExecutionUtilsTest
{

    static File python_exe_file;

    public File getPython_exe_file()
    {
        return python_exe_file;
    }

    @Value("${python.path}")
    public void setPython_exe_file(String python_exe_file)
    {
        PythonExecutionUtilsTest.python_exe_file = new File(python_exe_file);
    }

    File test_file = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\test\\testfiles\\test.py");

    @Before
    public void setPython()
    {
        PythonExecutionUtils.setPython_path(python_exe_file);
    }

    @Test
    public void statementTest()
    {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("print('Hello World!')");
        String[] statements = new String[strings.size()];
        strings.toArray(statements);
        PythonExecutionUtils.executePythonStatement(statements);
    }

    @Test
    public void fileTest()
    {
        PythonExecutionUtils.executePythonFile(test_file);
    }

    @Test
    public void fileOutputTest()
    {
        String[] result = PythonExecutionUtils.executePythonFileWithOutput(test_file);
        for (String output : result)
        {
            System.out.println(output);
        }
    }

    @Test
    public void fileOutputParamTest()
    {
        String[] result = PythonExecutionUtils.executePythonFileWithOutput(test_file, new File("first.txt"), new File("second.txt"));
        for (String output : result)
        {
            System.out.println(output);
        }
    }

    @Test
    public void functionTest()
    {
        PyObject result = PythonExecutionUtils.executePythonFunction("testString", test_file, "TEST");
        System.out.println(result);
    }

}
