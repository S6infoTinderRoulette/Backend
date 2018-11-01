package com.tinderroulette.backend.rest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.CAS.ConfigurationController;
import com.tinderroulette.backend.rest.dao.SwitchGroupDao;

@RestController
public class SwitchGroupController {
    private SwitchGroupDao switchGroupDao;

    public SwitchGroupController(SwitchGroupDao switchGroupDao) {
        this.switchGroupDao = switchGroupDao;
    }

    @ResponseBody
    @PostMapping(value = "/switchgroup/{idClass}/{tutorat}")
    public int insertSwitch(@PathVariable String idClass, @PathVariable int tutorat) {
        String currUser = ConfigurationController.getAuthUser();
        return switchGroupDao.insertSwitchRequest(currUser, idClass, tutorat);
    }

    @ResponseBody
    @PutMapping(value = "/switchgroup/{idClass}/{cipRequested}")
    public boolean acceptSwitch(@PathVariable String idClass, @PathVariable String cipRequested) {
        String currUser = ConfigurationController.getAuthUser();
        return switchGroupDao.acceptSwitchRequest(cipRequested, currUser, idClass);
    }
}