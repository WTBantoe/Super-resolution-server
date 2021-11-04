package com.sr.service;

import com.sr.service.impl.SRServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class SRServiceTest
{
    static SRService srService;

    @BeforeAll
    static void startService()
    {
        srService = new SRServiceImpl();
    }

    @Test
    void imageSRTest()
    {
        srService.imageSuperResolution();
    }

    @Test
    void videoSRTest()
    {
        srService.videoSuperResolution();
    }
}
