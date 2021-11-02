package com.sr.service.impl;

import com.sr.service.TransferService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class TransferServiceImpl implements TransferService
{
    @Override
    public void uploadFile(MultipartFile file, File destination)
    {
        if (!destination.getParentFile().exists())
        {
            destination.getParentFile().mkdirs();
        }
        try
        {
            file.transferTo(destination);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("文件保存失败！");
        }
    }

    @Override
    public void downloadFile(File source, HttpServletResponse response)
    {
        byte[] buffer = new byte[1024 * 1024];
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(source);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            OutputStream outputStream = response.getOutputStream();
            int i = bufferedInputStream.read(buffer);
            while (i != -1)
            {
                outputStream.write(buffer, 0, i);
                i = bufferedInputStream.read(buffer);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.print("文件下载失败！");
        }
        finally
        {
            if (bufferedInputStream != null)
            {
                try
                {
                    bufferedInputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
