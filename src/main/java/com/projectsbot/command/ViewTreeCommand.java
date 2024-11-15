package com.projectsbot.commands;

import org.springframework.stereotype.Component;

@Component
public class ViewTreeCommand implements Command {
    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.VIEW_TREE;
    }

    @Override
    public String execute(String message) {
        return "viewTree"; // todo
    }
}
