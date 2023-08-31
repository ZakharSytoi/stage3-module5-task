package com.mjc.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, R, K> {
    Page<R> readAll(Pageable pageRequest);

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    Boolean deleteById(K id);
}
