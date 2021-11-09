package com.rest.service;

import com.rest.persistence.model.Foo;

import java.util.List;

/**
 * @author simple
 */
public interface IFooService {
    List<Foo> findAll();

    Foo findById(Long id);

    Long create(Foo resource);

    Foo getById(String id);

    void update(Foo resource);

    void deleteById(Long id);
}
