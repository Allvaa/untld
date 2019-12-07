package com.allvaa.untld.modules.commands.music;

import com.allvaa.untld.Untld;
import com.allvaa.untld.handler.music.QueueConstruct;
import com.allvaa.untld.modules.categories.MusicCategory;
import com.jagrosh.jdautilities.command.CommandEvent;

public class LoopCommand extends MusicCategory {
    public LoopCommand(Untld untld) {
        super(untld);
        this.name = "loop";
        this.aliases = new String[]{"repeat"};
        this.help = "toggle loop";
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

        queue.setLoop(!queue.isLoop());
        if (queue.isLoop()) {
            event.reply("Loop is on!");
        } else {
            event.reply("Loop is off!");
        }
    }
}
