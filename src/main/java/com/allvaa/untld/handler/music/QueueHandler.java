package com.allvaa.untld.handler.music;

import com.allvaa.untld.Untld;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;

public class QueueHandler extends AudioEventAdapter {
    public QueueHandler(Untld untld, Message message, AudioTrack track) {
        QueueConstruct data = untld.getGuildQueue(message.getGuild());
        if (data == null) {
            QueueConstruct queue = new QueueConstruct(message);
            DefaultAudioPlayerManager player = new DefaultAudioPlayerManager();
            queue.setPlayer(player.createPlayer());
            AudioHandler handler = new AudioHandler(untld, message.getGuild(), queue.getPlayer());
            queue.getPlayer().addListener(handler);
            queue.setHandler(handler);
            untld.setGuildQueue(message.getGuild(), queue);
            queue.getTextChannel().getGuild().getAudioManager().setSendingHandler(handler);
            queue.getHandler().queue(track);
        } else {
            data.getSongs().add(track);
            message.getTextChannel().sendMessage("Added **" + track.getInfo().title + "** to the queue.").queue();
        }
    }
}
