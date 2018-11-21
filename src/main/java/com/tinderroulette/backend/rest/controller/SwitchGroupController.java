package com.tinderroulette.backend.rest.controller;

import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.CAS.PrivilegeValidator;
import com.tinderroulette.backend.rest.CAS.Status;
import com.tinderroulette.backend.rest.dao.SwitchGroupDao;
import com.tinderroulette.backend.rest.model.SwitchGroupInfo;
import com.tinderroulette.backend.rest.notification.NotificationData;
import com.tinderroulette.backend.rest.notification.NotificationDispatcher;

@RestController
public class SwitchGroupController {
    private SwitchGroupDao switchGroupDao;
    private PrivilegeValidator validator;

    public SwitchGroupController(SwitchGroupDao switchGroupDao, PrivilegeValidator validator) {
        this.switchGroupDao = switchGroupDao;
        this.validator = validator;
    }

    @GetMapping(value = "/switchgroup/request/")
    public List<SwitchGroupInfo> getSwitchRequest(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        String currUser = validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        return switchGroupDao.findUserClassSwitchGroupRequest(currUser);
    }

    @ResponseBody
    @GetMapping(value = "/switchgroup/request/{idClass}/{tutorat}/")
    public int insertSwitch(@PathVariable String idClass, @PathVariable int tutorat,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        String currUser = validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        return switchGroupDao.insertSwitchRequest(currUser, idClass, tutorat);
    }

    @ResponseBody
    @GetMapping(value = "/switchgroup/accept/{idClass}/{cipRequested}/")
    public boolean acceptSwitch(@PathVariable String idClass, @PathVariable String cipRequested,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        String currUser = validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        NotificationData eventData = new NotificationData("Switchrequest",
                Message.format(Message.SWITCH_SUCCESS, currUser, cipRequested));
        NotificationDispatcher.addEvent(cipRequested, eventData);
        return switchGroupDao.acceptSwitchRequest(cipRequested, currUser, idClass);
    }
}
