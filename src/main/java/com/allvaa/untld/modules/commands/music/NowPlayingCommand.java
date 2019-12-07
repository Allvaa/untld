package com.allvaa.untld.modules.commands.music;

import com.allvaa.untld.Untld;
import com.allvaa.untld.handler.music.QueueConstruct;
import com.allvaa.untld.modules.categories.MusicCategory;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class NowPlayingCommand extends MusicCategory {
    public NowPlayingCommand(Untld untld) {
        super(untld);
        this.name = "nowplaying";
        this.aliases = new String[]{"np"};
        this.help = "currently playing track";
    }

    @Override
    protected void execute(CommandEvent event) {
        QueueConstruct queue = untld.getGuildQueue(event.getGuild());
        if (queue == null) {
            event.reply("there is no queue on the server");
            return;
        }
        if (!queue.isPlaying()) {
            event.reply("the track maybe is paused.");
            return;
        }
        AudioTrack track = queue.getSongs().element();

        event.reply("Now playing:\n" + track.getInfo().title);
    }
}
