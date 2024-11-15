package com.projectsbot.command;

import com.projectsbot.builder.SendMessageBuilder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements Command, SendMessageBuilder {
    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.HELP;
    }

    @Override
    public SendMessage executeCommand(Update userUpdate) {
        return buildMessage(userUpdate.getMessage().getChatId(), """
                Commands provided by bot:
                To view all projects enter command:
                 /viewTree

                To add project enter command:
                 /addElement <Name-Of-Project>

                To add subproject enter command:
                 /addElement <Name-Of-Project> <Name-Of-Subproject>

                To remove project enter command:
                 /removeElement <Name-Of-Project>

                """);
    }
}
