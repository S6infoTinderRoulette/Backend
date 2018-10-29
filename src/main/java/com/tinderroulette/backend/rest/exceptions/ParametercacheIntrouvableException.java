package com.tinderroulette.backend.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParametercacheIntrouvableException extends RuntimeException{

    public ParametercacheIntrouvableException(String s) {
        super(s);
    }

}
