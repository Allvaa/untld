package com.allvaa.untld;

import com.allvaa.untld.handler.CommandHandler;
import com.allvaa.untld.handler.ListenerHandler;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        Untld untld = new Untld(new Config());
        JDA client = new JDABuilder(AccountType.BOT)
                .setToken(untld.getConfig().getToken())
                .build();
        new ListenerHandler(client, untld);
        new CommandHandler(client, untld);
    }
}
