package com.allvaa.untld.modules.commands.music;

import com.allvaa.untld.Untld;
import com.allvaa.untld.Utility;
import com.allvaa.untld.handler.music.PlayerManager;
import com.allvaa.untld.modules.categories.GeneralCategory;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand extends GeneralCategory {
    public PlayCommand(Untld untld) {
        super(untld);
        this.name = "play";
        this.help = "plays music";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.reply("please give me the title.");
            return;
        }
        String searchres = Utility.searchYt(event.getArgs());
        if (searchres == null) {
            event.reply("Couldn't find any results.");
        }

        TextChannel channel = event.getTextChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (audioManager.isConnected()) {
            channel.sendMessage("I'm already connected to a channel").queue();
            return;
        }

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Please join a voice channel first").queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            channel.sendMessageFormat("I am missing permission to join %s", voiceChannel).queue();
            return;
        }

        audioManager.openAudioConnection(voiceChannel);
        channel.sendMessage("Joining your voice channel").queue();

        PlayerManager manager = PlayerManager.getInstance();
        manager.loadAndPlay(event.getTextChannel(), searchres);
    }
}
