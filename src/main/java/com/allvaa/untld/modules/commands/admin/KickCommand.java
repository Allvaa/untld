package com.allvaa.untld.modules.commands.admin;

import com.allvaa.untld.Untld;
import com.allvaa.untld.modules.categories.AdminCategory;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class KickCommand extends AdminCategory {
    public KickCommand(Untld untld) {
        super(untld);
        this.name = "kick";
        this.botPermissions = new Permission[]{Permission.KICK_MEMBERS};
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getMessage().getMentionedMembers().isEmpty()) {
            event.reply("please mention a member to kick");
            return;
        }

        Member member = event.getMessage().getMentionedMembers().get(0);
        member.kick(event.getArgs().isEmpty() ? "No reason." : event.getArgs()).queue();
        event.reply("member kicked");
    }
}
