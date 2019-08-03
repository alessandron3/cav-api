package com.volanty.exception;

import org.springframework.web.util.NestedServletException;

public class DateNotFoundException extends NestedServletException {

    public DateNotFoundException() {
        super("This date could not be found in our calendar");
    }
}
