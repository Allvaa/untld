package com.allvaa.untld.modules.commands.owner;

import com.allvaa.untld.Untld;
import com.allvaa.untld.modules.categories.OwnerCategory;
import com.jagrosh.jdautilities.command.CommandEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EvalCommand extends OwnerCategory {
    public EvalCommand(Untld untld) {
        super(untld);
        this.name = "eval";
        this.aliases = new String[]{"evaluate", "ev", "e"};
        this.help = "evaluating";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.reply("what do you want?");
            return;
        }
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            engine.put("event", event);
            engine.put("client", event.getJDA());
            engine.put("cmdClient", commandClient);
            engine.put("untld", untld);
            engine.put("command", this);
            engine.put("message", event.getMessage());
            Object evaled = engine.eval(event.getArgs());
            event.reply("<:yes:523751512720015361> **Output**\n```java\n" + evaled + "\n```");
        } catch (Exception e) {
            event.reply("<:no:523751512434802689> **Error**\n```java\n" + e.getMessage() + "\n```");
        }
    }
}
