package com.sr.common;

import io.humble.video.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import static com.sr.common.TimeUtil.formatTimeStamp;
import static org.python.bouncycastle.crypto.tls.CipherType.stream;

public class MediaInfoUtils
{
    public static HashMap<String, Object> getImageInfo(String filePath)
    {
        HashMap<String,Object> media_info = new HashMap<>();

        File image = new File(filePath);

        Dimension result = null;
        String suffix = image.getName().split("\\.")[1];
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        System.out.println(iter);
        if (iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(new File(
                        filePath));
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                result = new Dimension(width, height);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.dispose();
            }
        }
        media_info.put("width",result.width);
        media_info.put("height",result.height);
        return media_info;
    }


    /**
     * Parse information from a file, and also optionally print information about what
     * formats, containers and codecs fit into that file.
     *
     * @param filePath The file to open, or null if we just want generic options.
     * @throws IOException if file cannot be opened.
     * @throws InterruptedException if process is interrupted while querying.
     * @return
     */
    public static HashMap<String, Object> getVideoInfo(String filePath) throws InterruptedException, IOException
    {
        HashMap<String,Object> media_info = new HashMap<>();

        final Demuxer demuxer = Demuxer.make();
        demuxer.open(filePath, null, false, true, null, null);

        final DemuxerFormat format = demuxer.getFormat();
        media_info.put("URL",demuxer.getURL());
        media_info.put("format_long",format.getLongName());
        media_info.put("format",format.getName());

        KeyValueBag metadata_all = demuxer.getMetaData();
        HashMap<String,String> metadata_all_detail = new HashMap<>();
        for(String key: metadata_all.getKeys())
        {
            metadata_all_detail.put(key,metadata_all.getValue(key));
        }
        media_info.put("metadata",metadata_all_detail);

        media_info.put("duration",formatTimeStamp(demuxer.getDuration()));
        media_info.put("start_time",demuxer.getStartTime() == Global.NO_PTS ? 0 : demuxer.getStartTime() / 1000000.0);
        media_info.put("bit_rate",demuxer.getBitRate());


        int numStreams = demuxer.getNumStreams();

        int videoStreamId = -1;
        Decoder videoDecoder = null;
        boolean first = true;

        for (int i = 0; i < numStreams; i++) {
            HashMap<String,Object> stream_info = new HashMap<>();
            DemuxerStream stream = demuxer.getStream(i);

            KeyValueBag metadata_stream = stream.getMetaData();
            HashMap<String,String> metadata_stream_detail = new HashMap<>();
            for(String key: metadata_stream.getKeys())
            {
                metadata_stream_detail.put(key,metadata_stream.getValue(key));
            }
            stream_info.put("metadata",metadata_stream_detail);
            stream_info.put("duration",formatTimeStamp(stream.getDuration()));
            stream_info.put("start_time",stream.getStartTime() == Global.NO_PTS ? 0 : demuxer.getStartTime() / 1000000.0);
            stream_info.put("frame_rate",stream.getFrameRate());
            stream_info.put("frame_count",stream.getNumFrames());

            Decoder decoder = stream.getDecoder();

            stream_info.put("decoder",decoder != null ? decoder.toString() : "unknown coder");

            if (first&&decoder != null && decoder.getCodecType() == MediaDescriptor.Type.MEDIA_VIDEO) {
                videoStreamId = i;
                videoDecoder = decoder;
                first = false;
            }
            media_info.put(String.format("stream_%d_info",i),stream_info);
        }

        if (videoStreamId == -1)
            throw new RuntimeException("could not find video stream in container: "+filePath);

        videoDecoder.open(null, null);

        media_info.put("width",videoDecoder.getWidth());
        media_info.put("height",videoDecoder.getHeight());
        media_info.put("pixel_format",videoDecoder.getPixelFormat());
        media_info.put("frame_count",(1.0/videoDecoder.getTimeBase().getValue())*1.0 * demuxer.getDuration() / Global.DEFAULT_PTS_PER_SECOND);

        demuxer.close();

        return media_info;
    }
}
