package com.projectsbot.commands;

import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.HELP;
    }

    @Override
    public String execute(String message) {
        return "help"; //todo
    }
}
