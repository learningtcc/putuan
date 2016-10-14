package com.advanpro.putuan.utils.common;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {说明}
 *
 * @author Retina.Ye
 * @since 2014/11/4 14:12
 */
public class Page<T> extends BaseQuery {

    /**
     * 对象总数量
     */
    private Integer totalCount;

    /**
     * 每页的数据列表
     */
    private List<T> result = new ArrayList<T>();

    /**
     * 根据直接提供的记录开始位置创建分页对象
     *
     * @param start
     * @param limit
     * @param <T>
     * @return
     */
    public static <T> Page<T> createFromStart(int start, int limit) {
        return new Page<T>(start / limit + 1, limit);
    }

    public Page(int pageNo, int pageSize) {
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
    }

    public Page(int pageNo) {
        this.setPageNo(pageNo);
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Page() {
        this.pageNo = DEFAULT_PAGE_NO;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public static <T> Page<T> create(List<T> result, Integer totalCount) {
        Page<T> page = new Page<T>();
        if (!CollectionUtils.isEmpty(result)) {
            page.setResult(result);
        }
        else {
            page.setResult(Collections.EMPTY_LIST);
        }
        page.setTotalCount(totalCount == null ? 0 : totalCount);
        return page;
    }

    public static <T> Page<T> create(List<T> result) {
        Page<T> page = new Page<T>(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE);
        if (!CollectionUtils.isEmpty(result)) {
            page.setResult(result);
        }
        else {
            page.setResult(Collections.EMPTY_LIST);
        }
        return page;
    }

    public static <T> Page<T> create(List<T> result, int pageNo, int pageSize) {
        Page<T> page = new Page<T>(pageNo, pageSize);
        if (!CollectionUtils.isEmpty(result)) {
            page.setResult(result);
        }
        else {
            page.setResult(Collections.EMPTY_LIST);
        }
        return page;
    }

    // 总页数，这个是根据totalcount和pageSize计算的
    public int getTotalPage() {
        if (totalCount == 0)
            return 0;

        int count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPage());
    }

    /**
     * 返回下页的页号,序号从1开始.
     */
    public int getNextPage() {
        if (isHasNext()) {
            return ++pageNo;
        }
        else {
            return pageNo;
        }
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 返回上页的页号,序号从1开始.
     */
    public int getPrePage() {
        if (isHasPre())
            return pageNo - 1;
        else
            return pageNo;
    }

    /**
     * @return the totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the result
     */
    public List<T> getResult() {
        return this.result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(List<T> result) {
        if (result == null) {
            this.result = new ArrayList<T>(0);
        }
        else {
            this.result = result;
        }
    }

}
