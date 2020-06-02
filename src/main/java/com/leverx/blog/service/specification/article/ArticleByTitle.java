package com.leverx.blog.service.specification.article;

import com.leverx.blog.model.Article;
import com.leverx.blog.service.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ArticleByTitle implements Specification<Article> {
    private static final String TITLE = "title";
    private String articleTitle;

    public ArticleByTitle(String title) {
        this.articleTitle = title;
    }

    @Override
    public Predicate[] toPredicate(Root<Article> root, CriteriaQuery<Article> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return new Predicate[]{criteriaBuilder.and(criteriaBuilder.equal(root.get(TITLE), articleTitle))};
    }
}
