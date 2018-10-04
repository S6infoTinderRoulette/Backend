package com.tinderroulette.backend.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ActivitiesIntrouvableException extends RuntimeException{

    public ActivitiesIntrouvableException(String s) {
        super(s);
    }

}
