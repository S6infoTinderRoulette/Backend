package com.tinderroulette.backend.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.dao.RequestDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.RequestIntrouvableException;
import com.tinderroulette.backend.rest.model.Request;

@RestController
public class RequestController {

    private RequestDao requestDao;

    public RequestController(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @GetMapping(value = "/request/")
    public List<Request> findAll () {
        return requestDao.findAll();
    }

    @PostMapping(value = "/request/")
    public ResponseEntity<Void> addRequest (@Valid @RequestBody Request Request){
		Request requestTest = requestDao.findByCipRequestedAndCipSeekingAndIdActivity(
				Request.getCipRequested(), Request.getCipSeeking(), Request.getIdActivity());
        if (requestTest != null) {
            throw new RequestIntrouvableException("La Request correspondante est déjà présente dans la base de données");
        } else {
            Request requestput = requestDao.save(Request);
            if (requestput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/request/")
    public ResponseEntity<Void> updateRequest (@Valid @RequestBody Request Request){
		Request requestTest = requestDao.findByCipRequestedAndCipSeekingAndIdActivity(
				Request.getCipRequested(), Request.getCipSeeking(), Request.getIdActivity());
        if (requestTest == null) {
            throw new RequestIntrouvableException("La Request correspondante n'est pas présente dans la base de données");
        } else {
            Request requestput = requestDao.save(Request);
            if (requestput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

	@DeleteMapping(value = "/request/{ciprequested}/{cipseeking}/{idactivity}/")
	public ResponseEntity<Void> deleteRequest(@PathVariable String ciprequested, @PathVariable String cipseeking,
			@PathVariable int idactivity) {
		Request requestTest = requestDao.findByCipRequestedAndCipSeekingAndIdActivity(ciprequested,
				cipseeking, idactivity);
        if (requestTest == null) {
            throw new RequestIntrouvableException("La request correspondante n'est pas présente dans la base de données");
        } else {
			requestDao.deleteByCipRequestedAndCipSeekingAndIdActivity(ciprequested, cipseeking,
					idactivity);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
}
