package com.projectsbot.command;

public enum CommandIdentifier {
    VIEW_TREE("^\\/viewTree$"),
    ADD_PROJECT("^\\/addElement\\s+([A-Za-zА-Яа-яЁё0-9]+)$"),
    ADD_SUBPROJECT("^\\/addElement\\s+([A-Za-zА-Яа-яЁё0-9]+)\\s+([A-Za-zА-Яа-яЁё0-9]+)$"),
    REMOVE_ELEMENT("^\\/removeElement\\s+([A-Za-zА-Яа-яЁё0-9]+)$"),
    HELP("^\\/help$");

    private final String regex;

    CommandIdentifier(String regex) {
        this.regex = regex;
    }

    public boolean matches(String stringToCheck) {
        return stringToCheck.trim().matches(regex);
    }
}
