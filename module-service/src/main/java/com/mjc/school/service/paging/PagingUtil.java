package com.mjc.school.service.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PagingUtil {
    public static <T> Page<T> createPageFromList(Pageable pageRequest, List<T> list){
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<T> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, list.size());
    }
}
