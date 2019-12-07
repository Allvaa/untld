package com.allvaa.untld.modules.commands.general;

import com.allvaa.untld.Untld;
import com.allvaa.untld.handler.CommandHandler;
import com.allvaa.untld.modules.categories.GeneralCategory;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;

public class HelpCommand extends GeneralCategory {
    public HelpCommand(Untld untld) {
        super(untld);
        this.name = "help";
        this.aliases = new String[]{"h"};
        this.help = "shows this msg";
    }

    @Override
    protected void execute(CommandEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor("My commands.");
        for (com.allvaa.untld.handler.Category category : CommandHandler.categories) {
            if (!category.isHidden() || !category.isOwnerCommand()) {
                ArrayList<String> commands = new ArrayList<>();
                for (Command command: category.commands) {
                    commands.add(command.getName());
                }
                embed.addField(category.getCategory().getName(), String.join(", ", commands), false);
            }
        }
        event.reply(embed.build());
    }
}