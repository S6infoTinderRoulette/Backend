package com.tinderroulette.backend.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppIntrouvableException extends RuntimeException{

    public AppIntrouvableException(String s) {
        super(s);
    }

}
