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
            "delete from article_tag where tag_id = :tagId";

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
    public Optional<List<Tag>> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> rootQuery = criteriaQuery.from(Tag.class);
        criteriaQuery.select(rootQuery);
        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().size() == 0 ?
                Optional.empty() :
                Optional.ofNullable(query.getResultList());
    }

    @Override
    public void detachTagFromArticle(Integer tagId) {
        Query query = entityManager.createNativeQuery(DETACH_TAG_FROM_ARTICLE_SQL_BY_TAG_ID);
        query.setParameter("tagId", tagId);
        query.executeUpdate();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Query query = entityManager.createQuery("SELECT t FROM Tag t WHERE t.name =:name");
        query.setParameter("name", name);
        return query.getResultList().size() == 0 ?
                Optional.empty() :
                query.getResultList().stream().findAny();
    }
}
