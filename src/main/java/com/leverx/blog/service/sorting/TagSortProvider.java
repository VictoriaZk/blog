package com.leverx.blog.service.sorting;

import com.leverx.blog.model.Tag;
import com.leverx.blog.service.specification.SortOrder;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class TagSortProvider implements SortProvider<Tag> {
    private String sortBy;
    private SortOrder sortOrder;

    public TagSortProvider(String sortBy, SortOrder sortOrder) {
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    @Override
    public Order[] getSortOrder(Root<Tag> root, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.isEmpty(sortBy) && StringUtils.isEmpty(sortOrder)) {
            return new Order[]{};
        }
        if (sortOrder == SortOrder.DESC) {
            return new Order[]{criteriaBuilder.desc(root.get(sortBy))};
        }
        return new Order[]{criteriaBuilder.asc(root.get(sortBy))};
    }
}
