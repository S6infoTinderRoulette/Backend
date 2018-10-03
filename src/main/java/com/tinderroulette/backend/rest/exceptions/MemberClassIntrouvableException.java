package com.tinderroulette.backend.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberClassIntrouvableException extends RuntimeException{

    public MemberClassIntrouvableException(String s) {
        super(s);
    }

}