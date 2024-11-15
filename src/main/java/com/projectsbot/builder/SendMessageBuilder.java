package com.projectsbot.builder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendMessageBuilder {
    default SendMessage buildMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        return sendMessage;
    }
}
