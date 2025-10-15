package com.train.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Pagination result
 */
@Data
public class PageResult<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long total;
    private List<T> list;
    private Integer pageNum;
    private Integer pageSize;
    
    public PageResult() {
    }
    
    public PageResult(Long total, List<T> list, Integer pageNum, Integer pageSize) {
        this.total = total;
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
