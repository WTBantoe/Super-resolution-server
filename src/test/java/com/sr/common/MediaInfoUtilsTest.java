package com.sr.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaInfoUtilsTest
{
    @Test
    public void testGetVideoInfo()
    {
        try
        {
            System.out.println(MediaInfoUtils.getVideoInfo("C:\\Users\\92887\\Desktop\\Subjects\\大四\\大四上\\大型应用软件课程设计\\video\\video\\city.avi"));

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetImageInfo()
    {
        System.out.println(MediaInfoUtils.getImageInfo("C:\\Users\\92887\\Desktop\\Subjects\\大四\\大四上\\大型应用软件课程设计\\image\\Set14\\baboon.png"));
    }
}