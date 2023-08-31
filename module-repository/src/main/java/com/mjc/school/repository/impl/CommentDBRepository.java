package com.mjc.school.repository.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.repository.model.impl.NewsModel;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CommentDBRepository extends AbstractDBRepository<CommentModel, Long> implements CommentRepository {
    @Override
    protected void updateEntity(CommentModel dbEntity, CommentModel newEntity) {
        dbEntity.setContent(newEntity.getContent());
    }

    @Override
    public List<CommentModel> readCommentsByNewsId(Long newsId) {
        NewsModel news = entityManager.find(NewsModel.class, newsId);
        if (news == null) return null;
        return news.getComments();
    }
}
