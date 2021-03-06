package com.leverx.blog.repository.impl;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.Comment;
import com.leverx.blog.model.Status;
import com.leverx.blog.model.Tag;
import com.leverx.blog.model.metamodel.Article_;
import com.leverx.blog.model.metamodel.Comment_;
import com.leverx.blog.repository.CommentRepository;
import com.leverx.blog.service.pages.Page;
import com.leverx.blog.service.sort.CommentSortProvider;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private static final String SELECT_C_FROM_COMMENT_C_WHERE_C_ARTICLE_ID_ARTICLE_ID_AND_C_ID_COMMENT_ID =
            "SELECT c from Comment c where c.article.id = :articleId AND c.id =:commentId AND c.article.status =:public";
    private static final String SELECT_C_FROM_COMMENT_C_WHERE_C_ARTICLE_ID_ARTICLE_ID =
            "SELECT c from Comment c where c.article.id = :articleId AND c.article.status =:public";
    private static final String DELETE_FROM_COMMENT_C_WHERE_C_USER_ID_ID =
            "DELETE FROM Comment c WHERE c.user.id =:userId";
    private static final String ARTICLE_ID = "articleId";
    private static final String COMMENT_ID = "commentId";
    private static final String PUBLIC = "public";
    private static final String ARTICLE = "article";
    private static final String ID = "userId";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Comment> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public Optional<Comment> findById(Integer articleId, Integer commentId) {
        /*CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> commentRoot = criteriaQuery.from(Comment.class);

        Predicate criteria = criteriaBuilder.conjunction();
        Predicate predicateArticle = criteriaBuilder.equal(
                commentRoot.get(Comment_.ARTICLE), articleId);
        Predicate predicateComment= criteriaBuilder.equal(
                commentRoot.get(Comment_.ID), commentId);
        criteria = criteriaBuilder.and(criteria, predicateArticle, predicateComment);
        criteriaQuery.select(commentRoot).where(criteria);
        TypedQuery<Comment> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().size() == 0 ?
                Optional.empty() :
                Optional.ofNullable(query.getSingleResult());
         */
        Query query = entityManager.createQuery(SELECT_C_FROM_COMMENT_C_WHERE_C_ARTICLE_ID_ARTICLE_ID_AND_C_ID_COMMENT_ID);
        query.setParameter(ARTICLE_ID, articleId);
        query.setParameter(COMMENT_ID, commentId);
        query.setParameter(PUBLIC, PUBLIC);
        return query.getResultList().size() == 0 ?
                Optional.empty() :
                Optional.ofNullable((Comment) query.getSingleResult());

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

    @Override
    public void deleteByUser(Integer id) {
        Query query = entityManager.createQuery(DELETE_FROM_COMMENT_C_WHERE_C_USER_ID_ID);
        query.setParameter(ID, id);
        query.executeUpdate();
    }


    @Override
    public List<Comment> findAll(Integer id) {
        Query query = entityManager.createQuery(SELECT_C_FROM_COMMENT_C_WHERE_C_ARTICLE_ID_ARTICLE_ID);
        query.setParameter(ARTICLE_ID, id);
        query.setParameter(PUBLIC, PUBLIC);
        return query.getResultList();
    }

    @Override
    public List<Comment> findAll(Integer id, Page page, CommentSortProvider commentSortProvider) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);

        criteriaQuery.where(new Predicate[]{criteriaBuilder.and(criteriaBuilder.equal(root.get(ARTICLE), id))});
        criteriaQuery.orderBy(commentSortProvider.getSortOrder(root, criteriaBuilder));
        Integer offset = page.getOffset();
        Integer limit = page.getLimit();
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset * limit - limit)
                .setMaxResults(limit)
                .getResultList();
    }
}
