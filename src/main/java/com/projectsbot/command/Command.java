package com.projectsbot.commands;

public interface Command {
    CommandIdentifier getIdentifier();
    String execute(String message);
}
