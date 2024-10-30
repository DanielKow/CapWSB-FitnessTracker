package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.exception.api.BusinessException;

/**
 * Exception indicating that the user with given email already exists.
 */
public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(String email) {
        super("User with email %s already exists".formatted(email));
    }

    public UserAlreadyExistsException(Long id) {
        super("User with ID=%s already exists".formatted(id));
    }
}
