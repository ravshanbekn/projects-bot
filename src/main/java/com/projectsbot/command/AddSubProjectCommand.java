package com.projectsbot.commands;

import org.springframework.stereotype.Component;

@Component
public class AddSubProjectCommand implements Command {
    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.ADD_SUBPROJECT;
    }

    @Override
    public String execute(String message) {
        return "addSubProject"; //todo
    }
}
