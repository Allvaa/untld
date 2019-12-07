package com.allvaa.untld.handler.music;

import com.allvaa.untld.Untld;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;

import java.nio.ByteBuffer;
import java.util.*;

public class AudioHandler implements AudioSendHandler {
    private final Untld untld;
    private final Guild guild;
    private final AudioPlayer player;
    private AudioFrame lastFrame;

    public AudioHandler(Untld untld, Guild guild, AudioPlayer player) {
        this.untld = untld;
        this.guild = guild;
        this.player = player;
    }

    public void play(AudioTrack track) {
        QueueConstruct queue = untld.getGuildQueue(guild);
        queue.getSongs().add(track);

        if (player.getPlayingTrack() == null) {
            player.playTrack(track);
        }
    }

    public void shuffleQueue() {
        QueueConstruct queue = untld.getGuildQueue(guild);
        List<AudioTrack> tQueue = new ArrayList<>(getQueuedTracks());
        AudioTrack current = tQueue.get(0);
        tQueue.remove(0);
        Collections.shuffle(tQueue);
        tQueue.add(0, current);
        purgeQueue();
        queue.getSongs().addAll(tQueue);
    }

    public Set<AudioTrack> getQueuedTracks() {
        QueueConstruct queue = untld.getGuildQueue(guild);
        return new LinkedHashSet<>(queue.getSongs());
    }

    public void purgeQueue() {
        untld.getGuildQueue(guild).getSongs().clear();
    }

    public void remove(AudioTrack entry) {
        untld.getGuildQueue(guild).getSongs().remove(entry);
    }

    public AudioTrack getTrackInfo(AudioTrack track) {
        return untld.getGuildQueue(guild).getSongs().stream().filter(audioInfo -> audioInfo.equals(track)).findFirst().orElse(null);
    }

    @Override
    public boolean canProvide() {
        if (lastFrame == null) {
            lastFrame = player.provide();
        }

        return lastFrame != null;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        if (lastFrame == null) {
            lastFrame = player.provide();
        }

        ByteBuffer data = lastFrame != null ? ByteBuffer.wrap(lastFrame.getData()) : null;
        lastFrame = null;

        return data;
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}