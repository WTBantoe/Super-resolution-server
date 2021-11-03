package com.sr.service;

import java.io.File;

public interface SRService
{
    File imageSuperResolution(File image_path);

    File videoSuperResolution(File video_path);
}
