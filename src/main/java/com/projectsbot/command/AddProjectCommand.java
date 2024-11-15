package com.projectsbot.commands;

import org.springframework.stereotype.Component;

@Component
public class AddProjectCommand implements Command {
    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.ADD_PROJECT;
    }

    @Override
    public String execute(String message) {
        return "addProject"; //todo
    }
}
