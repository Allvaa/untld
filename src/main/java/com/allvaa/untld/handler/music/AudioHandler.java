package com.allvaa.untld.handler.music;

import com.allvaa.untld.Untld;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.nio.ByteBuffer;
import java.util.*;

public class AudioHandler extends AudioEventAdapter implements AudioSendHandler {

    private final Untld untld;
    private final Guild guild;
    private final AudioPlayer player;
    private AudioFrame lastFrame;

    public AudioHandler(Untld untld, Guild guild, AudioPlayer player) {
        this.untld = untld;
        this.guild = guild;
        this.player = player;
    }

    public void queue(AudioTrack track) {
        QueueConstruct queue = untld.getGuildQueue(guild);
        queue.getSongs().add(track);

        if (player.getPlayingTrack() == null) {
            player.playTrack(track);
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        QueueConstruct queue = untld.getGuildQueue(guild);
        VoiceChannel vChan = queue.getVoiceChannel();
        queue.getTextChannel().sendMessage("Now playing: **" + track.getInfo().title + "**.").queue();
        if (vChan == null) { // User has left all voice channels
            player.stopTrack();
        } else {
            queue.getTextChannel().getGuild().getAudioManager().openAudioConnection(vChan);
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        QueueConstruct queue = untld.getGuildQueue(guild);
        Guild g = queue.getTextChannel().getGuild();
        queue.getSongs().remove();
        if (queue.getSongs().isEmpty()) {
            untld.removeGuildQueue(g);
            queue.getTextChannel().sendMessage("Queue is empty!").queue();
        } else {
            player.playTrack(queue.getSongs().element());
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