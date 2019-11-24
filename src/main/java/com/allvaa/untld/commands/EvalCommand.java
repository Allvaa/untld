package com.allvaa.untld.commands;

import com.allvaa.untld.Untld;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EvalCommand extends Command {
    private final Untld untld;

    public EvalCommand(Untld untld) {
        this.untld = untld;
        this.name = "eval";
        this.aliases = new String[]{"evaluate", "ev", "e"};
        this.help = "evaluating";
        this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().length() == 0) {
            event.reply("what do you want?");
            return;
        }
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            engine.put("event", event);
            engine.put("client", event.getJDA());
            engine.put("untld", this.untld);
            engine.put("command", this);
            engine.put("message", event.getMessage());
            Object evaled = engine.eval(event.getArgs());
            event.reply("<:yes:523751512720015361> **Output**\n```java\n" + evaled + "\n```");
        } catch (Exception e) {
            event.reply("<:no:523751512434802689> **Error**\n```java\n" + e.getMessage() + "\n```");
        }
    }
}
