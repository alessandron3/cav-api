package com.volanty.exception;

import org.springframework.web.util.NestedServletException;

public class TimeAlreadyScheduledException extends NestedServletException {

    public TimeAlreadyScheduledException() {
        super("Time is already scheduled");
    }

    public TimeAlreadyScheduledException(String msg) {
        super(msg);
    }

    public TimeAlreadyScheduledException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
