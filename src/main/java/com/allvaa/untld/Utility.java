package com.allvaa.untld;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Utility {
    @Nullable
    public static String searchYt(String input) {
        try {
            new URL(input);
            return input;
        } catch (MalformedURLException ignored) {
            try {
                YouTube youTube = new YouTube.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        null
                )
                        .setApplicationName("music bot")
                        .build();
                List<SearchResult> results = youTube.search()
                        .list("id,snippet")
                        .setQ(input)
                        .setMaxResults(1L)
                        .setType("video")
                        .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                        .setKey(new Config().getYouTubeAPI())
                        .execute()
                        .getItems();

                if (!results.isEmpty()) {
                    String videoId = results.get(0).getId().getVideoId();

                    return "https://www.youtube.com/watch?v=" + videoId;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class FormatUtil {

        public static String formatTime(long duration)
        {
            if(duration == Long.MAX_VALUE)
                return "LIVE";
            long seconds = Math.round(duration/1000.0);
            long hours = seconds/(60*60);
            seconds %= 60*60;
            long minutes = seconds/60;
            seconds %= 60;
            return (hours>0 ? hours+":" : "") + (minutes<10 ? "0"+minutes : minutes) + ":" + (seconds<10 ? "0"+seconds : seconds);
        }

        public static String progressBar(double percent)
        {
            String str = "";
            for(int i=0; i<12; i++)
                if(i == (int)(percent*12))
                    str+="\uD83D\uDD18"; // 🔘
                else
                    str+="▬";
            return str;
        }

        public static String volumeIcon(int volume)
        {
            if(volume == 0)
                return "\uD83D\uDD07"; // 🔇
            if(volume < 30)
                return "\uD83D\uDD08"; // 🔈
            if(volume < 70)
                return "\uD83D\uDD09"; // 🔉
            return "\uD83D\uDD0A";     // 🔊
        }

        public static String listOfTChannels(List<TextChannel> list, String query)
        {
            String out = " Multiple text channels found matching \""+query+"\":";
            for(int i=0; i<6 && i<list.size(); i++)
                out+="\n - "+list.get(i).getName()+" (<#"+list.get(i).getId()+">)";
            if(list.size()>6)
                out+="\n**And "+(list.size()-6)+" more...**";
            return out;
        }

        public static String listOfVChannels(List<VoiceChannel> list, String query)
        {
            String out = " Multiple voice channels found matching \""+query+"\":";
            for(int i=0; i<6 && i<list.size(); i++)
                out+="\n - "+list.get(i).getName()+" (ID:"+list.get(i).getId()+")";
            if(list.size()>6)
                out+="\n**And "+(list.size()-6)+" more...**";
            return out;
        }

        public static String listOfRoles(List<Role> list, String query)
        {
            String out = " Multiple text channels found matching \""+query+"\":";
            for(int i=0; i<6 && i<list.size(); i++)
                out+="\n - "+list.get(i).getName()+" (ID:"+list.get(i).getId()+")";
            if(list.size()>6)
                out+="\n**And "+(list.size()-6)+" more...**";
            return out;
        }

        public static String filter(String input)
        {
            return input.replace("@everyone", "@\u0435veryone").replace("@here", "@h\u0435re").trim(); // cyrillic letter e
        }
    }
}
