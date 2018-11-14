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

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.CAS.PrivilegeValidator;
import com.tinderroulette.backend.rest.CAS.Status;
import com.tinderroulette.backend.rest.dao.ClassesDao;
import com.tinderroulette.backend.rest.exceptions.ClassesIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.Classes;

@RestController
public class ClassesController {
    private ClassesDao classesDao;
    private PrivilegeValidator validator;

    public ClassesController(ClassesDao classesDao, PrivilegeValidator validator) {
        this.classesDao = classesDao;
        this.validator = validator;
    }

    @GetMapping(value = "/classes/")
    public List<Classes> findAll(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        return classesDao.findAll();
    }

    @PostMapping(value = "/classes/")
    public ResponseEntity<Void> addClasses(@Valid @RequestBody Classes classes,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        Classes classesTest = classesDao.findByIdClass(classes.getIdClass());
        if (classesTest != null) {
            throw new ClassesIntrouvableException(Message.CLASS_EXIST.toString());
        } else {
            Classes classesput = classesDao.save(classes);
            if (classesput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/classes/")
    public ResponseEntity<Void> updateClasses(@Valid @RequestBody Classes classes,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        Classes classesTest = classesDao.findByIdClass(classes.getIdClass());
        if (classesTest == null) {
            throw new ClassesIntrouvableException(Message.CLASS_NOT_EXIST.toString());
        } else {
            Classes classesput = classesDao.save(classes);
            if (classesput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/classes/{idClass}/")
    public ResponseEntity<Void> deleteClasses(@PathVariable String idClass, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        Classes classesTest = classesDao.findByIdClass(idClass);
        if (classesTest == null) {
            throw new ClassesIntrouvableException(Message.CLASS_NOT_EXIST.toString());
        } else {
            classesDao.deleteByIdClass(idClass);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
}
