package com.rest.controller;

import com.rest.exception.NotFoundException;
import com.rest.persistence.model.Foo;
import com.rest.service.IFooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author simple
 */
@RestController
@RequestMapping("/foos")
public class FooController {

    @Autowired
    private IFooService service;

    // The @ExceptionHandler annotated method is only active for that particular Controller, not globally for the entire application.
//    @ExceptionHandler({BadRequestException.class, NotFoundException.class})
//    public void handleException() {
//    }

    @GetMapping
    public List<Foo> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Foo findById(@PathVariable("id") Long id) {
        try {
            throw new NotFoundException();
//            int a = 1/0;
//            return service.findById(id);
        } catch (Exception exception) {
            // advice
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found", exception);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Foo resource) {
        return service.create(resource);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Foo resource) {
        service.getById(resource.getId());
        service.update(resource);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
