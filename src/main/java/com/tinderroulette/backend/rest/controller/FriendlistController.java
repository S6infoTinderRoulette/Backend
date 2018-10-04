package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.FriendlistDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.FriendlistIntrouvableException;
import com.tinderroulette.backend.rest.model.Friendlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FriendlistController {

    private FriendlistDao friendlistDao;

    public FriendlistController(FriendlistDao friendlistDao) {
        this.friendlistDao = friendlistDao;
    }

    @GetMapping(value = "/friendlists/")
    public List<Friendlist> findAll () {
        return friendlistDao.findAll();
    }

    @PostMapping(value = "/friendlist/")
    public ResponseEntity<Void> addFriendlist (@Valid @RequestBody Friendlist friendlist){
        Friendlist friendlistTest = friendlistDao.findByCipAndFriendCip(friendlist.getCip(),friendlist.getFriendCip());
        if (friendlistTest != null) {
            throw new FriendlistIntrouvableException("La Friendlist correspondante est déjà présente dans la base de données");
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
    public ResponseEntity<Void> updateFriendList (@Valid @RequestBody Friendlist friendlist){
        Friendlist friendlistTest = friendlistDao.findByCipAndFriendCip(friendlist.getCip(),friendlist.getFriendCip());
        if (friendlistTest == null) {
            throw new FriendlistIntrouvableException("La Friendlist correspondante n'est pas présente dans la base de données");
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
    public ResponseEntity<Void> deleteFriendlist (@PathVariable String cip, @PathVariable String friendCip) {
        Friendlist friendlistTest = friendlistDao.findByCipAndFriendCip(cip,friendCip);
        if (friendlistTest == null) {
            throw new FriendlistIntrouvableException("La Friendlist correspondante n'est pas présente dans la base de données");
        } else {
            friendlistDao.deleteByCipAndAndFriendCip(cip,friendCip);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
}
