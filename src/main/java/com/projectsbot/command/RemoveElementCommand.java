package com.projectsbot.commands;

import org.springframework.stereotype.Component;

@Component
public class RemoveElementCommand implements Command {
    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.REMOVE_ELEMENT;
    }

    @Override
    public String execute(String message) {
        return "removeElement";
    }
}
