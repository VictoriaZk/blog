package com.leverx.blog.service.sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public interface SortProvider<T> {
    static <T> SortProvider<T> unsorted() {
        return (root, criteriaBuilder) -> new Order[]{};
    }

    Order[] getSortOrder(Root<T> root, CriteriaBuilder criteriaBuilder);
}
