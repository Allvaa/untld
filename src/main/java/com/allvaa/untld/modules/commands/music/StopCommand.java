package com.allvaa.untld.modules.commands.music;

import com.allvaa.untld.Untld;
import com.allvaa.untld.handler.music.QueueConstruct;
import com.allvaa.untld.modules.categories.MusicCategory;
import com.jagrosh.jdautilities.command.CommandEvent;

public class StopCommand extends MusicCategory {
    public StopCommand(Untld untld) {
        super(untld);
        this.name = "stop";
        this.aliases = new String[]{"leave", "dc"};
        this.help = "destroys queue";
    }

    @Override
    protected void execute(CommandEvent event) {
        QueueConstruct queue = untld.getGuildQueue(event.getGuild());
        if (queue == null) {
            event.reply("there is no queue on the server");
            return;
        }
        if (event.getMember().getVoiceState().getChannel() == null) {
            event.reply("you're not in vc");
            return;
        }
        if (!queue.getVoiceChannel().equals(event.getMember().getVoiceState().getChannel())) {
            event.reply("you're on the wrong channel");
            return;
        }

        untld.removeGuildQueue(event.getGuild());
        event.reply("stopped");
    }
}
