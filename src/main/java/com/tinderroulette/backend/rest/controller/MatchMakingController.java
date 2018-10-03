/*package com.tinderroulette.backend.rest.controller;


import com.tinderroulette.backend.rest.model.MatchMaking;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MatchMakingController {

    private static final String templateName = "nom = %s!";
    private static final String templateCIP = "cip = %s!";
    private static AtomicLong counter = new AtomicLong();

    @RequestMapping(path = "/rest/v1/matchmaking", method = GET)
    @ResponseBody
    public MatchMaking matchmaking(@RequestParam(value="name", defaultValue="Test") String name,
                                   @RequestParam(value="cip", defaultValue="Ok") String cip) {
        return new MatchMaking(counter.incrementAndGet(),
                String.format(templateName, name), String.format(templateCIP, cip));
    }
}
*/