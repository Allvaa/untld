package com.allvaa.untld.modules.commands.fun;

import com.allvaa.untld.Untld;
import com.allvaa.untld.modules.categories.FunCategory;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class SayCommand extends FunCategory {
    public SayCommand(Untld untld) {
        super(untld);
        this.name = "say";
        this.aliases = new String[]{"echo"};
        this.help = "i'll say what r u say";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
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