package com.allvaa.untld.modules.commands.owner;

import com.allvaa.untld.Untld;
import com.allvaa.untld.modules.categories.OwnerCategory;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ExecCommand extends OwnerCategory {
    public ExecCommand(Untld untld) {
        super(untld);
        this.name = "exec";
        this.aliases = new String[]{"execute", "ex", "$"};
        this.help = "excuting";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.reply("what do you want?");
            return;
        }
        try {
            Process p = Runtime.getRuntime().exec(event.getArgs());
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            String stdInStr = stdInput.lines().collect(Collectors.joining("\n"));
            String stdErrStr = stdError.lines().collect(Collectors.joining("\n"));

            if (!stdInStr.isEmpty()) {
                event.reply(stdInStr);
            } else if (!stdErrStr.isEmpty()) {
                event.reply(stdErrStr);
            } else {
                event.reply("Completed without result.");
            }
        } catch (IOException e) {
            event.reply(e.getMessage());
        }
    }
}
