package com.allvaa.untld;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

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
}
