package com.studydemo.server.common;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 统一分页返回结构（对齐 cdp_service 的 PageVO 风格）。
 *
 * <p>字段语义与 {@link PageResult} 一致，独立存在以便文章模块走 {@code ApiResponseVo<PageVO<T>>}，
 * 不与 user / messages 等仍使用 {@link PageResult} 的模块耦合。</p>
 *
 * @param <T> 列表元素类型
 */
public class PageVO<T> {

    /** 当前页数据 */
    private List<T> records;

    /** 满足条件的总记录数 */
    private long total;

    /** 当前页码，从 1 开始 */
    private long pageNum;

    /** 每页条数 */
    private long pageSize;

    /** 总页数 */
    private long pages;

    /**
     * 由 MyBatis-Plus 的分页对象构造返回结构。
     *
     * @param page    查询使用的分页对象（取其中的 total / current / size）
     * @param records 已转换成视图对象的当前页数据
     * @param <T>     列表元素类型
     * @return 统一分页返回结构
     */
    public static <T> PageVO<T> of(IPage<?> page, List<T> records) {
        PageVO<T> result = new PageVO<>();
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
