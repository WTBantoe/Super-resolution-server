package com.sr.common;

import io.humble.video.*;

import java.io.IOException;
import java.util.HashMap;

import static com.sr.common.TimeUtil.formatTimeStamp;

public class MediaInfoUtils
{
    /**
     * Parse information from a file, and also optionally print information about what
     * formats, containers and codecs fit into that file.
     *
     * @param fileName The file to open, or null if we just want generic options.
     * @throws IOException if file cannot be opened.
     * @throws InterruptedException if process is interrupted while querying.
     * @return
     */
    private static HashMap<String, Object> getInfo(String fileName) throws InterruptedException, IOException
    {
        HashMap<String,Object> media_info = new HashMap<>();

        final Demuxer demuxer = Demuxer.make();
        demuxer.open(fileName, null, false, true, null, null);

        final DemuxerFormat format = demuxer.getFormat();
        System.out.printf("URL: '%s' (%s: %s)\n", demuxer.getURL(), format.getLongName(), format.getName());
        media_info.put("format_long",format.getLongName());
        media_info.put("format",format.getName());

        KeyValueBag metadata = demuxer.getMetaData();
        System.out.println("MetaData:");
        for(String key: metadata.getKeys())
            System.out.printf("  %s: %s\n", key, metadata.getValue(key));
        media_info.put("meatdata_all",metadata);

        final String formattedDuration = formatTimeStamp(demuxer.getDuration());
        System.out.printf("Duration: %s, start: %f, bitrate: %d kb/s\n",
                formattedDuration,
                demuxer.getStartTime() == Global.NO_PTS ? 0 : demuxer.getStartTime() / 1000000.0,
                demuxer.getBitRate()/1000);
        media_info.put("duration",formattedDuration);
        media_info.put("start_time",demuxer.getStartTime() == Global.NO_PTS ? 0 : demuxer.getStartTime() / 1000000.0);
        media_info.put("bit_rate",demuxer.getBitRate());

        int numStreams = demuxer.getNumStreams();

        for (int i = 0; i < numStreams; i++) {
            DemuxerStream stream = demuxer.getStream(i);

            metadata = stream.getMetaData();
            media_info.put(String.format("metadata_stream_%d",i),metadata);

            final String language = metadata.getValue("language");

            Decoder d = stream.getDecoder();

            System.out.printf(" Stream #0.%1$d (%2$s): %3$s\n", i, language, d != null ? d.toString() : "unknown coder");
            System.out.println("  Metadata:");
            for(String key: metadata.getKeys())
                System.out.printf("    %s: %s\n", key, metadata.getValue(key));
        }

        //TODO:分图片和视频进行处理
        //TODO:获取分辨率有关信息

        return media_info;
    }
}
