package com.allvaa.untld.modules.commands.music;

import com.allvaa.untld.Untld;
import com.allvaa.untld.handler.music.QueueConstruct;
import com.allvaa.untld.modules.categories.MusicCategory;
import com.jagrosh.jdautilities.command.CommandEvent;

public class VolumeCommand extends MusicCategory {
    public VolumeCommand(Untld untld) {
        super(untld);
        this.name = "volume";
        this.aliases = new String[]{"vol"};
        this.help = "shows / set volume";
    }

    @Override
    protected void execute(CommandEvent event) {
        QueueConstruct queue = untld.getGuildQueue(event.getGuild());
        if (queue == null) {
            event.reply("there is no queue on the server");
            return;
        }
        if (event.getArgs().isEmpty()) {
            event.reply("Current volume: " + queue.getVolume() + "%");
            return;
        } else {
            if (event.getMember().getVoiceState().getChannel() == null) {
                event.reply("you're not in vc");
                return;
            }
            if (!queue.getVoiceChannel().equals(event.getMember().getVoiceState().getChannel())) {
                event.reply("you're on the wrong channel");
                return;
            }

            try {
                int i = Integer.parseInt(event.getArgs());
                queue.getPlayer().setVolume(i);
                event.reply("Volume was set to " + i + "%");
            } catch (Exception e) {
                event.reply("value must be a number");
            }
        }
    }
}

