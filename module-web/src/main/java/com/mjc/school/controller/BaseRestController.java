package com.mjc.school.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BaseRestController<T, R, K>{
    ResponseEntity<Page<R>> readAll(int pageNo, int pageSize);

    ResponseEntity<R> readById(K id);

    ResponseEntity<R> create(T createRequest);

    ResponseEntity<R> update(Long id, T updateRequest);

    void deleteById(K id);
}
