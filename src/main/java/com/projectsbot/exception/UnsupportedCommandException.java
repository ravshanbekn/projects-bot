package com.projectsbot.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnsupportedCommandException extends IllegalArgumentException {
    public UnsupportedCommandException(String message){
        super(message);
        log.error(message);
    }
}
