package com.mjc.school.repository;

import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.query.NewsSearchQueryParams;

import java.util.List;

public interface NewsRepository extends BaseRepository<NewsModel, Long> {

    List<NewsModel> readBySearchParams(NewsSearchQueryParams searchQueryParams);
}
