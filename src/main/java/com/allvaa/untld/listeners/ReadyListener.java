package com.allvaa.untld.listeners;

import com.allvaa.untld.Untld;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {
    private final Untld untld;

    public ReadyListener(Untld untld) {
        this.untld = untld;
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Logged in as " + event.getJDA().getSelfUser().getAsTag() + "!");
    }
}
