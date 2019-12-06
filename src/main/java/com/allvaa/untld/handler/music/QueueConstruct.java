package com.allvaa.untld.handler.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueConstruct {
    private final TextChannel textChannel;
    private final VoiceChannel voiceChannel;
    private boolean loop;
    private boolean playing;
    private AudioPlayer player;
    private AudioHandler handler;
    private final LinkedBlockingQueue<AudioTrack> songs;
    private int volume;

    public QueueConstruct(Message message) {
        this.textChannel = message.getTextChannel();
        this.voiceChannel = message.getMember().getVoiceState().getChannel();
        this.songs = new LinkedBlockingQueue<>();
        this.playing = true;
        this.volume = 100;
        this.loop = false;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public VoiceChannel getVoiceChannel() {
        return voiceChannel;
    }

    public AudioPlayer setPlayer(AudioPlayer player) {
        return this.player = player;
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public AudioHandler setHandler(AudioHandler handler) {
        return this.handler = handler;
    }

    public AudioHandler getHandler() {
        return handler;
    }

    public LinkedBlockingQueue<AudioTrack> getSongs() {
        return songs;
    }

    public boolean setPlaying(boolean playing) {
        return this.playing = playing;
    }

    public boolean isPlaying() {
        return playing;
    }

    public int setVolume(int volume) {
        player.setVolume(volume);
        return this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public boolean setLoop(boolean loop) {
        return this.loop = loop;
    }

    public boolean isLoop() {
        return loop;
    }
}
