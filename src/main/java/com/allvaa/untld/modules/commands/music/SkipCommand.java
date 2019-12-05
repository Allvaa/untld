package com.allvaa.untld.modules.commands.music;

import com.allvaa.untld.Untld;
import com.allvaa.untld.handler.music.QueueConstruct;
import com.allvaa.untld.modules.categories.MusicCategory;
import com.jagrosh.jdautilities.command.CommandEvent;

public class SkipCommand extends MusicCategory {
    public SkipCommand(Untld untld) {
        super(untld);
        this.name = "skip";
        this.aliases = new String[]{"sk"};
        this.help = "skip current track";
    }

    @Override
    protected void execute(CommandEvent event) {
        QueueConstruct queue = untld.getGuildQueue(event.getGuild());
        if (queue == null) {
            event.reply("there is no queue on the server");
        }
        if (!event.getSelfMember().getVoiceState().getChannel().equals(event.getMember().getVoiceState().getChannel())) {
            event.reply("you're the on wrong channel");
        }

        queue.getPlayer().stopTrack();
        event.reply("track skipped.");
    }
}
