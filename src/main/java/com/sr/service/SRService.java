package com.sr.service;

import java.io.File;

public interface SRService
{
    String[] imageSuperResolution();

    String[] videoSuperResolution();

    String[] imageSuperResolution(File input_file, File output_file);

    String[] videoSuperResolution(File input_file, File output_file);
}
