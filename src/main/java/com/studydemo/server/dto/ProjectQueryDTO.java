package com.studydemo.server.dto;

/**
 * 项目列表查询入参。
 *
 * <p>过滤条件（projectName 模糊、status 精确）全部可选；分页参数（pageNum/pageSize）可选，缺省走默认值。
 * 与 {@code ArticleQueryDTO} 保持一致的容错策略：分页参数填错就用默认值，不返回 400 打断列表查询。</p>
 */
public class ProjectQueryDTO {

    /** 默认页码 */
    private static final int DEFAULT_PAGE_NUM = 1;

    /** 默认每页条数 */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /** 每页条数上限：再大就有被刷全表的风险，超出直接截断 */
    private static final int MAX_PAGE_SIZE = 100;

    /** 项目名称模糊匹配 */
    private String projectName;

    /** 状态精确匹配：1-进行中 0-已结束 */
    private Integer status;

    /** 页码，从 1 开始；不传按 1 处理 */
    private Integer pageNum;

    /** 每页条数；不传按 10 处理，最大 100 */
    private Integer pageSize;

    /**
     * 取出可用的页码（至少为 1）。
     *
     * @return 合法页码
     */
    public int resolvePageNum() {
        if (pageNum == null || pageNum < DEFAULT_PAGE_NUM) {
            return DEFAULT_PAGE_NUM;
        }
        return pageNum;
    }

    /**
     * 取出可用的每页条数，并夹在 [1, MAX_PAGE_SIZE] 区间内。
     *
     * @return 合法每页条数
     */
    public int resolvePageSize() {
        if (pageSize == null || pageSize < 1) {
            return DEFAULT_PAGE_SIZE;
        }
        return Math.min(pageSize, MAX_PAGE_SIZE);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
