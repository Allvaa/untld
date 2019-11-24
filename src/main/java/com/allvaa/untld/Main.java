package com.allvaa.untld;

import com.allvaa.untld.handler.CommandHandler;
import com.allvaa.untld.handler.ListenerHandler;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;


public class Main {
    private static final Untld untld = new Untld(new Config());
    public static void main(String[] args) throws LoginException {
        JDA client = new JDABuilder(AccountType.BOT)
                .setToken(Config.TOKEN)
                .build();
        new ListenerHandler(client, untld);
        new CommandHandler(client, untld);
    }
}
