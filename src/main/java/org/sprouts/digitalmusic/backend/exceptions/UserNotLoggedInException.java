package org.sprouts.digitalmusic.backend.exceptions;

public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException(String message) {
        super(message);
    }
}
