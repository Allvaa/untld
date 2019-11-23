package com.allvaa.untld;

import com.allvaa.untld.handler.CommandHandler;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        JDA client = new JDABuilder(AccountType.BOT)
                .setToken(Config.token)
                .build();
        new CommandHandler(client).load();
    }
}
