package com.allvaa.untld.modules.commands.admin;

import com.allvaa.untld.Untld;
import com.allvaa.untld.modules.categories.AdminCategory;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

public class PurgeCommand extends AdminCategory {
    public PurgeCommand(Untld untld) {
        super(untld);
        this.name = "purge";
        this.aliases = new String[]{"clear"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_MANAGE};
        this.userPermissions = new Permission[]{Permission.MESSAGE_MANAGE};
    }

    @Override
    protected void execute(CommandEvent event) {
        try {
            int limit = Integer.parseInt(event.getArgs());
            List<Message> messagesToDel = event.getTextChannel().getHistory().retrievePast(limit).complete();
            event.getTextChannel().purgeMessages(messagesToDel);
            event.reply("purged");
        } catch (Exception e) {
            event.reply("that's not a number");
        }
    }
}
