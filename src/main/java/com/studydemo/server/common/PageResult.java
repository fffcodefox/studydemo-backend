package com.studydemo.server.common;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 统一分页返回结构。
 *
 * <p>不直接把 MyBatis-Plus 的 {@code Page} 返回给前端，原因有两个：</p>
 * <ul>
 *   <li>{@code Page} 里带着 orders、optimizeCountSql、searchCount 等内部字段，
 *       暴露出去等于把 ORM 实现细节和接口契约绑死，换 ORM 就得改前端；</li>
 *   <li>换页逻辑需要的其实只有「当前页数据 + 总数 + 页码 + 每页条数」四项。</li>
 * </ul>
 *
 * @param <T> 列表元素类型
 */
public class PageResult<T> {

    /** 当前页数据 */
    private List<T> records;

    /** 满足条件的总记录数 */
    private long total;

    /** 当前页码，从 1 开始 */
    private long pageNum;

    /** 每页条数 */
    private long pageSize;

    /** 总页数，由 total 与 pageSize 算出，方便前端直接渲染「第 x / y 页」 */
    private long pages;

    /**
     * 由 MyBatis-Plus 的分页对象构造返回结构。
     *
     * @param page    查询使用的分页对象（取其中的 total / current / size）
     * @param records 已转换成视图对象的当前页数据
     * @param <T>     列表元素类型
     * @return 统一分页返回结构
     */
    public static <T> PageResult<T> of(IPage<?> page, List<T> records) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(records);
        result.setTotal(page.getTotal());
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setPages(page.getPages());
        return result;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }
}
