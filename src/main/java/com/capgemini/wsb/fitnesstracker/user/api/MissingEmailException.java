package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.exception.api.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that the email is missing.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email is required")
public class MissingEmailException extends BusinessException {
    public MissingEmailException() {
        super("Email is required");
    }
}
