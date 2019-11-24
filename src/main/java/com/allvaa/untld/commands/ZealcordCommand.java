package com.allvaa.untld.commands;

import com.allvaa.untld.Untld;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public class ZealcordCommand extends Command {
    private final Untld untld;

    public ZealcordCommand(Untld untld) {
        this.untld = untld;
        this.name = "zealcord";
        this.aliases = new String[]{"zc"};
        this.help = "get bot info from zealcord api";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
    }

    @Override
    protected void execute(CommandEvent event) {
        try {
            String user;
            if (event.getArgs().length() != 0) {
                user = FinderUtil.findUsers(event.getArgs(), event.getJDA()).get(0).getId();
            } else {
                user = event.getSelfUser().getId();
            }
            HttpResponse<JsonNode> res = Unirest.get("https://app.zealcord.xyz/api/bots/" + user)
                    .asJson();

            JSONObject data = res.getBody().getObject();
            User owner = FinderUtil.findUsers(data.getString("ownerID"), event.getJDA()).get(0);
            User bot = FinderUtil.findUsers(data.getString("botID"), event.getJDA()).get(0);
            String prefix = data.getString("prefix");
            Boolean accepted = data.getBoolean("accepted");
            String ownerInfo = "```asciidoc\nTag :: " + owner.getAsTag() + "\nID  :: "+ owner.getId() + "\n```";

            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor(bot.getAsTag(), null, bot.getEffectiveAvatarUrl())
                    .setThumbnail("https://cdn.discordapp.com/attachments/615699898020397064/616459734513483805/ZealNation_Logo_Flat.png")
                    .addField("Owner", ownerInfo, false)
                    .addField("Status", accepted ? "The Bot was approved by Zealcord Nation" : "The Bot maybe on queue", false)
                    .setFooter("Prefix: " + prefix)
                    .build();
            event.reply(embed);
        } catch (Exception e) {
            event.reply("The Bot isn't registered on Zealcord Nation.");
        }
    }
}
