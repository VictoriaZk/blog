package com.leverx.blog.service.specification.tag;

import com.leverx.blog.model.Tag;
import com.leverx.blog.service.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TagByName implements Specification<Tag> {
    private String tagName;

    public TagByName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public Predicate[] toPredicate(Root<Tag> root, CriteriaQuery<Tag> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return new Predicate[]{criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), tagName))};
    }
}
