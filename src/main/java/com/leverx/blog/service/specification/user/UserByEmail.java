package com.leverx.blog.service.specification.user;


import com.leverx.blog.model.User;
import com.leverx.blog.service.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserByEmail implements Specification<User> {

    public static final String EMAIL = "email";
    private String userName;

    public UserByEmail(String userName) {
        this.userName = userName;
    }

    @Override
    public Predicate[] toPredicate(Root<User> root, CriteriaQuery<User> query, CriteriaBuilder criteriaBuilder) {
        return new Predicate[]{criteriaBuilder.and(criteriaBuilder.equal(root.get(EMAIL), userName))};
    }
}
