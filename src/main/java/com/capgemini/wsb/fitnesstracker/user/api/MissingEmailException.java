package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.exception.api.BusinessException;

/**
 * Exception indicating that the email is missing.
 */
public class MissingEmailException extends BusinessException {
    public MissingEmailException() {
        super("Email is required");
    }
}
