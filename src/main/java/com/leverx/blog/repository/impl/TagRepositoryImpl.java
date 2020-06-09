package com.leverx.blog.repository.impl;

import com.leverx.blog.model.Tag;
import com.leverx.blog.repository.TagRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private static final String DETACH_TAG_FROM_ARTICLE_SQL_BY_TAG_ID =
            "DELETE FROM article_tag WHERE tag_id = ?";
    private static final String SELECT_T_FROM_TAG_T_WHERE_T_NAME_NAME =
            "SELECT t FROM Tag t WHERE t.name =:name";
    private static final String SELECT_FROM_ARTICLE_TAG_WHERE_TAG_ID =
            "SELECT * FROM article_tag WHERE tag_id = ?";
    private static final String NAME = "name";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Tag> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Integer create(Tag tag) {
        tag.setId(null);
        entityManager.persist(tag);
        return tag.getId();
    }

    @Override
    public void delete(Integer id) {
        Tag reference = entityManager.getReference(Tag.class, id);
        entityManager.remove(reference);
    }

    @Override
    public List<Tag> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> rootQuery = criteriaQuery.from(Tag.class);
        criteriaQuery.select(rootQuery);
        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void detachTagFromArticle(Integer tagId) {
        Query query = entityManager.createNativeQuery(DETACH_TAG_FROM_ARTICLE_SQL_BY_TAG_ID);
        query.setParameter(1, tagId);
        query.executeUpdate();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public Optional<Tag> findByName(String name) {
        Query query = entityManager.createQuery(SELECT_T_FROM_TAG_T_WHERE_T_NAME_NAME);
        query.setParameter(NAME, name);
        return query.getResultList().size() == 0 ?
                Optional.empty() :
                query.getResultList().stream().findAny();
    }

    @Override
    public int amountOfArticlesWithGivenTag(Integer tagId) {
        Query query = entityManager.createNativeQuery(SELECT_FROM_ARTICLE_TAG_WHERE_TAG_ID);
        query.setParameter(1, tagId);
        return query.getResultList().size();
    }
}
