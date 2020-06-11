package com.hxzy.crowdfunding.exception;

import org.springframework.security.core.AuthenticationException;

public class AdminNoExistException extends AuthenticationException {

    public AdminNoExistException(String message) {
        super(message);
    }
}
