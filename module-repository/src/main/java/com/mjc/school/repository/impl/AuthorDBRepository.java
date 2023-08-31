package com.mjc.school.repository.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthorDBRepository extends AbstractDBRepository<AuthorModel, Long> implements AuthorRepository {
    @Override
    public Optional<AuthorModel> readByNewsId(Long newsId) {
        NewsModel news = entityManager.find(NewsModel.class, newsId);
        if (news == null) return Optional.empty();
        return Optional.ofNullable(news.getAuthor());
    }

    @Override
    protected void updateEntity(AuthorModel dbEntity, AuthorModel newEntity) {
        dbEntity.setName(newEntity.getName());
    }
}
