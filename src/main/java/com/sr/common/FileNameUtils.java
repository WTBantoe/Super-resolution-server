package com.sr.common;

import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class FileNameUtils
{
    public static String processFileName(MultipartFile file)
    {
        String fileName = file.getOriginalFilename();
        if (fileName == null)
        {
            throw new StatusException(StatusEnum.PICTURE_NOT_UPLOAD);
        }

        if (!fileName.contains("."))
        {
            throw new StatusException((StatusEnum.INVALID_FILE_TYPE));
        }
        File currentFile = new File(fileName);
        fileName = UUID.randomUUID() + currentFile.getName();
        return fileName;
    }
}
