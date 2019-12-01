package com.allvaa.untld.modules.commands.utility;

import com.allvaa.untld.Untld;
import com.allvaa.untld.modules.categories.UtilityCategory;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class UrbanDictionaryCommand extends UtilityCategory {
    public UrbanDictionaryCommand(Untld untld) {
        super(untld);
        this.name = "urban";
        this.aliases = new String[]{"urbandictionary", "ud"};
        this.help = "looking for word definition?";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.reply("give me some word..");
        }
        HttpResponse<JsonNode> res =  Unirest.get("https://allvzx.glitch.me/api/urbandictionary")
                .queryString("define", event.getArgs())
                .asJson();

        JSONArray bodyArr = res.getBody().getArray();
        if (bodyArr.isEmpty()) {
            event.reply("Couldn't find results.");
            return;
        }

        JSONObject def = (JSONObject) bodyArr.get(0);
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(def.getString("title"), def.getString("url"), "https://cdn6.aptoide.com/imgs/9/d/f/9df70e7de56b5dc8d4d6e76c5f1c30f2_screen.png?h=320")
                .setDescription(def.getString("meaning"))
                .addField("Example Usage", def.getString("example"), false)
                .addField("Author", "[" + def.getJSONObject("author").getString("username") + "](" + def.getJSONObject("author").getString("url") + "]", false)
                .build();
        event.reply(embed);
    }
}