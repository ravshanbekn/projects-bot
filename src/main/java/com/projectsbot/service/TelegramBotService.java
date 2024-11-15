package com.projectsbot.service;

import com.projectsbot.command.Command;
import com.projectsbot.builder.SendMessageBuilder;
import com.projectsbot.exception.UnsupportedCommandException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramBotService extends TelegramLongPollingBot implements SendMessageBuilder {
    @Value("${bot.name}")
    private String botName;
    private final List<Command> commands;

    @Autowired
    public TelegramBotService(@Value("${bot.token}") String botToken,
                              List<Command> commands) {
        super(botToken);
        this.commands = commands;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String userMessage = update.getMessage().getText().trim();
            try {
                SendMessage commandResponse = commands.stream()
                        .filter(command -> command.getIdentifier().matches(userMessage))
                        .findFirst()
                        .orElseThrow(() -> new UnsupportedCommandException("Unsupported command entered. " +
                                "Enter command: /help to see more information about commands"))
                        .executeCommand(update);
                sendMessage(commandResponse);
            } catch (Exception e) {
                SendMessage sendMessage = buildMessage(chatId, e.getMessage());
                sendMessage(sendMessage);
            }
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
