package com.mjc.school.repository.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDBRepository extends AbstractDBRepository<TagModel, Long> implements TagRepository {
    @Override
    protected void updateEntity(TagModel dbEntity, TagModel newEntity) {
        dbEntity.setName(newEntity.getName());
    }

    @Override
    public List<TagModel> readByNewsId(Long newsId) {
        NewsModel news = entityManager.find(NewsModel.class, newsId);
        if (news == null) return null;
        return news.getTags();
    }
}
