package com.allvaa.untld.modules.commands.music;

import com.allvaa.untld.Untld;
import com.allvaa.untld.Utility;
import com.allvaa.untld.handler.music.QueueConstruct;
import com.allvaa.untld.handler.music.QueueHandler;
import com.allvaa.untld.modules.categories.MusicCategory;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.Permission;

public class PlayCommand extends MusicCategory {
    public PlayCommand(Untld untld) {
        super(untld);
        this.name = "play";
        this.help = "plays music";
        this.botPermissions = new Permission[]{Permission.VOICE_CONNECT};
    }

    @Override
    protected void execute(CommandEvent event) {
        QueueConstruct queue = untld.getGuildQueue(event.getGuild());
        if (queue != null && !queue.getVoiceChannel().equals(event.getMember().getVoiceState().getChannel())) {
            event.reply("you're the on wrong channel");
            return;
        }
        if (event.getArgs().isEmpty()) {
            event.reply("please give me the title");
            return;
        }
        String url = Utility.searchYt(event.getArgs());
        PlayerManager playerManager = new PlayerManager();
        playerManager.loadItemOrdered(event.getGuild(), url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                new QueueHandler(untld, event.getMessage(), track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

            }

            @Override
            public void noMatches() {
                event.reply("Couldn't find any results");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                event.reply(exception.getMessage());
            }
        });
    }

    private class PlayerManager extends DefaultAudioPlayerManager {
        public PlayerManager() {
            AudioSourceManagers.registerRemoteSources(this);
            AudioSourceManagers.registerLocalSource(this);
            source(YoutubeAudioSourceManager.class).setPlaylistPageCount(10);
        }
    }
}
