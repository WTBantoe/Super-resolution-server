package com.sr.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SRServiceTest
{
    @Autowired
    SRService srService;

    File test_image = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\main\\resources\\model\\ESRGAN\\raw\\baboon.png");
    File test_image_output = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\main\\resources\\model\\ESRGAN\\results\\baboon.png");

    File test_video = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\main\\resources\\model\\TecoGAN\\raw\\calendar.avi");
    File test_video_output = new File("C:\\Users\\92887\\Desktop\\Projects\\Coding\\Python\\Super-resolution-server\\src\\main\\resources\\model\\TecoGAN\\results\\calendar.avi");

    @Test
    public void imageSRTest()
    {
        srService.imageSuperResolution(test_image, test_image_output);
    }

    @Test
    public void videoSRTest()
    {
        srService.videoSuperResolution(test_video, test_video_output);
    }
}
