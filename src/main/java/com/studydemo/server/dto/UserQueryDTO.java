package com.studydemo.server.dto;

/**
 * 用户列表查询入参。
 *
 * <p>过滤条件（username / phone / status）全部可选，由 Service 层按需拼接到查询中；
 * 分页参数（pageNum / pageSize）同样可选，缺省时走下面的默认值。
 * Spring 会自动把 GET 请求参数绑定到该对象上。</p>
 */
public class UserQueryDTO {

    /** 默认页码 */
    private static final int DEFAULT_PAGE_NUM = 1;

    /** 默认每页条数 */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /** 每页条数上限：再大就有被刷全表的风险，超出直接截断 */
    private static final int MAX_PAGE_SIZE = 100;

    /** 用户名模糊匹配 */
    private String username;

    /** 手机号模糊匹配 */
    private String phone;

    /** 状态精确匹配：1-启用 0-禁用 */
    private Integer status;

    /** 页码，从 1 开始；不传按 1 处理 */
    private Integer pageNum;

    /** 每页条数；不传按 10 处理，最大 100 */
    private Integer pageSize;

    /**
     * 取出可用的页码。
     *
     * <p>不写成 {@code @Min(1)} 那类注解校验，是因为分页参数属于「填错就用默认值」
     * 的容错型参数，为它返回 400 打断整个列表查询并不划算。</p>
     *
     * @return 合法页码，至少为 1
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
