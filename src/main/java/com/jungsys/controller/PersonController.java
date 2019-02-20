package com.jungsys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.JsonPath;
import com.jungsys.common.exception.EnumExceptionMessage;
import com.jungsys.dto.PersonDto;
import com.jungsys.service.PersonService;
import com.jungsys.utils.DateUtil;

@RestController
@RequestMapping(value = "/person", produces = "application/json; charset=utf-8")
public class PersonController {

    @Autowired
    private PersonService personService;

    // =========================================== Get All Users ==========================================

    @RequestMapping(value = "/getall")
    public Map<String, Object> getAll(HttpServletRequest req, @RequestParam Map<String, String> param) {
        System.out.println("getting all persons");
        List<PersonDto> persons = personService.getAll();
        Map<String, Object> rtn = new HashMap<String, Object>();
        rtn.put("request_time", DateUtil.getYYYYMMDDhhmmss());
        if (persons == null || persons.isEmpty()){
            System.out.println("no persons found");
            rtn.put("result","9999");
            rtn.put("reason", "Person is not existed");
            return rtn;
        }
        rtn.put("result","0000");
        rtn.put("person",persons);
        return rtn;
    }

    // =========================================== Get PersonDto By ID =========================================

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonDto> get(@PathVariable("id") int id){
        System.out.println("getting person with id: {}"+id);
        PersonDto person = personService.findById(id);

        if (person == null){
            System.out.println("person with id {} not found"+ id);
            return new ResponseEntity<PersonDto>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PersonDto>(person, HttpStatus.OK);
    }

    // =========================================== Create New PersonDto ========================================

    @RequestMapping(value = "/person", params = "method=post", method = RequestMethod.POST)
    public Map<String, Object> create(@RequestBody String payload, HttpServletRequest request,
                                      HttpServletResponse response){

        Map<String, Object> result = new HashMap<String, Object>();

        String id = JsonPath.parse(payload).read("$.id");
        String name = JsonPath.parse(payload).read("$.name");

        result.put("id", id);
        result.put("name", name);
        result.put("result", EnumExceptionMessage.SUCCESS.getCode());
        result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

        return result;
    }

    // =========================================== Update Existing PersonDto ===================================

    @RequestMapping(value = "/person/{id}", params = "method=put", method = RequestMethod.PUT)
    public ResponseEntity<PersonDto> update(@PathVariable int id, @RequestBody PersonDto person){
        System.out.println("updating person: {}"+ person);
        PersonDto currentUser = personService.findById(id);

        if (currentUser == null){
            System.out.println("PersonDto with id {} not found"+ id);
            return new ResponseEntity<PersonDto>(HttpStatus.NOT_FOUND);
        }

        currentUser.setId(person.getId());
        currentUser.setUsername(person.getUsername());

        personService.update(person);
        return new ResponseEntity<PersonDto>(currentUser, HttpStatus.OK);
    }

    // =========================================== Delete PersonDto ============================================

    @RequestMapping(value = "/person/{id}", params = "method=delete", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        System.out.println("deleting person with id: {}"+id);
        PersonDto person = personService.findById(id);

        if (person == null){
            System.out.println("Unable to delete. PersonDto with id {} not found"+ id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        personService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
