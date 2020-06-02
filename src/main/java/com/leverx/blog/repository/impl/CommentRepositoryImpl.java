package com.leverx.blog.repository.impl;

import com.leverx.blog.model.Comment;
import com.leverx.blog.repository.CommentRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    public static final String SELECT_C_FROM_COMMENT_C_WHERE_C_ARTICLE_ID_ARTICLE_ID_AND_C_ID_COMMENT_ID =
            "SELECT c from Comment c where c.article.id = :articleId AND c.id =:commentId";
    public static final String SELECT_C_FROM_COMMENT_C_WHERE_C_ARTICLE_ID_ARTICLE_ID =
            "SELECT c from Comment c where c.article.id = :articleId";
    public static final String ARTICLE_ID = "articleId";
    public static final String COMMENT_ID = "commentId";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Comment> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public Optional<Comment> findById(Integer articleId, Integer commentId) {
        Query query = entityManager.createQuery(SELECT_C_FROM_COMMENT_C_WHERE_C_ARTICLE_ID_ARTICLE_ID_AND_C_ID_COMMENT_ID);
        query.setParameter(ARTICLE_ID, articleId);
        query.setParameter(COMMENT_ID, commentId);
        return Optional.ofNullable((Comment) query.getSingleResult());
    }

    @Override
    public Integer create(Comment comment) {
        entityManager.persist(comment);
        return comment.getId();
    }


    @Override
    public void delete(Integer id) {
        Comment reference = entityManager.getReference(Comment.class, id);
        entityManager.remove(reference);
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Comment> findAll(Integer id) {
        Query query = entityManager.createQuery(SELECT_C_FROM_COMMENT_C_WHERE_C_ARTICLE_ID_ARTICLE_ID);
        query.setParameter(ARTICLE_ID, id);
        return query.getResultList();
    }
}
