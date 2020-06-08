package com.leverx.blog.service.pages;

public class PageImpl implements Page {
    private Integer offset;
    private Integer limit;

    public PageImpl(Integer offset, Integer limit){
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public Long getAllPages(Long countOfElements, Integer limit) {
        return countOfElements % limit == 0 ? countOfElements / limit : countOfElements / limit + 1;
    }
}
