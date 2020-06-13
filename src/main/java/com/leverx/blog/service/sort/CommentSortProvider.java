package com.leverx.blog.service.sort;

import com.leverx.blog.model.Comment;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class CommentSortProvider implements SortProvider<Comment> {

    private String sortBy;
    private SortOrder sortOrder;

    public CommentSortProvider(String sortBy, String sortOrder) {
        this.sortBy = sortBy;
        this.sortOrder = sortOrder == null ? null : SortOrder.valueOf(sortOrder.toUpperCase());
    }

    @Override
    public Order[] getSortOrder(Root<Comment> root, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.isEmpty(sortBy) && StringUtils.isEmpty(sortOrder)) {
            return new Order[]{};
        }
        if (sortOrder == SortOrder.DESC) {
            return new Order[]{criteriaBuilder.desc(root.get(sortBy))};
        }
        return new Order[]{criteriaBuilder.asc(root.get(sortBy))};
    }
}
