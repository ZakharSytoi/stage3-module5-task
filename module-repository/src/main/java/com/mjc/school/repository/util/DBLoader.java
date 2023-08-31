package com.mjc.school.repository.util;

import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Component
public class DBLoader {
    @PersistenceContext
    private EntityManager entityManager;
    private static final String AUTHORS_FILE_NAME = "authors";
    private static final String CONTENT_FILE_NAME = "content";
    private static final String COMMENTS_FILE_NAME = "comments";
    private static final String NEWS_FILE_NAME = "news";
    private static final String TAGS_FILE_NAME = "tags";

    @Transactional
    public void writeNews() {
        List<String> authors = DataReader.read(AUTHORS_FILE_NAME);
        List<String> tags = DataReader.read(TAGS_FILE_NAME);
        List<String> comments = DataReader.read(COMMENTS_FILE_NAME);
        Set<Integer> prevs = new HashSet<>();
        List<TagModel> tagModels = new LinkedList<>();
        List<AuthorModel> authorModels = new LinkedList<>();
        List<CommentModel> commentModels = new LinkedList<>();
        Random rnd = new Random();
        for (long i = 1; i <= 20; i++) {
            int index = rnd.nextInt(tags.size());
            if (prevs.contains(index)) {
                i--;
            } else {
                prevs.add(index);
                TagModel tm = new TagModel();
                AuthorModel am = new AuthorModel();
                CommentModel cm = new CommentModel();
                tm.setName(tags.get(index));
                am.setName(authors.get(index));
                cm.setContent(comments.get(index));
                tagModels.add(tm);
                authorModels.add(am);
                commentModels.add(cm);
            }
        }
        List<String> content = DataReader.read(CONTENT_FILE_NAME);
        List<String> titles = DataReader.read(NEWS_FILE_NAME);
        for (long i = 1; i <= 20; i++) {
            NewsModel nm = new NewsModel();
            int tagNumber = rnd.nextInt(2, 6);
            for (int j = 0; j < tagNumber; j++) {
                nm.getTags().add(tagModels.get(rnd.nextInt(20)));
            }
            int commentNumber = rnd.nextInt(4, 10);
            for (int j = 0; j < commentNumber; j++) {
                CommentModel cmm = commentModels.get(rnd.nextInt(20));
                cmm.setNews(nm);
                nm.getComments().add(cmm);
            }
            nm.setTitle(titles.get(rnd.nextInt(titles.size())));
            nm.setContent(content.get(rnd.nextInt(content.size())));
            nm.setAuthor(authorModels.get(rnd.nextInt(authorModels.size())));
            entityManager.persist(nm);
        }
    }
}
