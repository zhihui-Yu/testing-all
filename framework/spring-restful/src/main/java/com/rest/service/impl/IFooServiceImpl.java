package com.rest.service.impl;

import com.rest.persistence.model.Foo;
import com.rest.service.IFooService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author simple
 */
@Service
public class IFooServiceImpl implements IFooService {
    @Override
    public List<Foo> findAll() {
        return null;
    }

    @Override
    public Foo findById(Long id) {
        return null;
    }

    @Override
    public Long create(Foo resource) {
        return null;
    }

    @Override
    public Foo getById(String id) {
        return null;
    }

    @Override
    public void update(Foo resource) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
