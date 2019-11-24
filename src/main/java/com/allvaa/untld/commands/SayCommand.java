package com.allvaa.untld.commands;

import com.allvaa.untld.Untld;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class SayCommand extends Command {
    private final Untld untld;

    public SayCommand(Untld untld) {
        this.untld = untld;
        this.name = "say";
        this.aliases = new String[]{"echo"};
        this.help = "i'll say what r u say";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().length() == 0) {
            event.reply("what should i say?");
            return;
        }
        event.reply(event.getArgs());
        try {
            event.getMessage().delete().queue();
        } catch (InsufficientPermissionException ignored) {

        }
    }
}