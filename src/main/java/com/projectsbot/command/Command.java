package com.projectsbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    CommandIdentifier getIdentifier();

    SendMessage executeCommand(Update userUpdate);
}
