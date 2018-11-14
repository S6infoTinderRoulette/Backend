package com.tinderroulette.backend.rest.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.CAS.PrivilegeValidator;
import com.tinderroulette.backend.rest.CAS.Status;
import com.tinderroulette.backend.rest.dao.FriendlistDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.FriendlistIntrouvableException;
import com.tinderroulette.backend.rest.model.Friendlist;

@RestController
public class FriendlistController {

    private FriendlistDao friendlistDao;
    private PrivilegeValidator validator;

    public FriendlistController(FriendlistDao friendlistDao, PrivilegeValidator validator) {
        this.friendlistDao = friendlistDao;
        this.validator = validator;
    }

    @GetMapping(value = "/friendlist/")
    public List<Friendlist> findAll(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        return friendlistDao.findAll();
    }

    @PostMapping(value = "/friendlist/")
    public ResponseEntity<Void> addFriendlist(@Valid @RequestBody Friendlist friendlist,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Friendlist friendlistTest = friendlistDao.findByCipAndFriendCip(friendlist.getCip(), friendlist.getFriendCip());
        if (friendlistTest != null) {
            throw new FriendlistIntrouvableException(
                    "La Friendlist correspondante est déjà présente dans la base de données");
        } else {
            Friendlist friendlistput = friendlistDao.save(friendlist);
            if (friendlistput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/friendlist/")
    public ResponseEntity<Void> updateFriendList(@Valid @RequestBody Friendlist friendlist,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Friendlist friendlistTest = friendlistDao.findByCipAndFriendCip(friendlist.getCip(), friendlist.getFriendCip());
        if (friendlistTest == null) {
            throw new FriendlistIntrouvableException(
                    "La Friendlist correspondante n'est pas présente dans la base de données");
        } else {
            Friendlist friendlistput = friendlistDao.save(friendlist);
            if (friendlistput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/friendlist/{cip}/{friendCip}")
    public ResponseEntity<Void> deleteFriendlist(@PathVariable String cip, @PathVariable String friendCip,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Friendlist friendlistTest = friendlistDao.findByCipAndFriendCip(cip, friendCip);
        if (friendlistTest == null) {
            throw new FriendlistIntrouvableException(
                    "La Friendlist correspondante n'est pas présente dans la base de données");
        } else {
            friendlistDao.deleteByCipAndAndFriendCip(cip, friendCip);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
}
