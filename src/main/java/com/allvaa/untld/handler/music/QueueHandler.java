package com.allvaa.untld.handler.music;

import com.allvaa.untld.Untld;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class QueueHandler extends AudioEventAdapter {
    private final Untld untld;
    private QueueConstruct queue;

    public QueueHandler(Untld untld, Message message, AudioTrack track) {
        this.untld = untld;
        this.queue = untld.getGuildQueue(message.getGuild());

        if (queue == null) {
            queue = new QueueConstruct(message);
            DefaultAudioPlayerManager player = new DefaultAudioPlayerManager();
            queue.setPlayer(player.createPlayer());
            AudioHandler handler = new AudioHandler(untld, message.getGuild(), queue.getPlayer());
            queue.getPlayer().addListener(this);
            queue.setHandler(handler);
            untld.setGuildQueue(message.getGuild(), queue);
            queue.getTextChannel().getGuild().getAudioManager().setSendingHandler(handler);
        } else {
            message.getTextChannel().sendMessage("Added **" + track.getInfo().title + "** to the queue.").queue();
        }
        queue.getHandler().play(track);
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
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
        Guild g = queue.getTextChannel().getGuild();
        AudioTrack prevTrack = queue.getSongs().remove();
        if (queue.isLoop()) {
            queue.getSongs().add(prevTrack.makeClone());
        }
        if (queue.getSongs().isEmpty()) {
            untld.removeGuildQueue(g);
            queue.getTextChannel().sendMessage("Queue is empty!").queue();
        } else {
            player.playTrack(queue.getSongs().element());
        }
    }
}
