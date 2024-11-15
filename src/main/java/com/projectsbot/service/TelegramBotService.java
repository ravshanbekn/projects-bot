package com.projectsbot.services;

import com.projectsbot.commands.Command;
import com.projectsbot.exceptions.UnsupportedCommandException;
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
public class TelegramBotService extends TelegramLongPollingBot {
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
            String userMessage = update.getMessage().getText();
            try {
                String commandExecutionResult = commands.stream()
                        .filter(command -> command.getIdentifier().matches(userMessage))
                        .findFirst()
                        .orElseThrow(() -> new UnsupportedCommandException("Unsupported command entered"))
                        .execute(userMessage);
                sendMessage(chatId, commandExecutionResult);
            } catch (Exception e) {
                sendMessage(chatId, e.getMessage());
            }
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
