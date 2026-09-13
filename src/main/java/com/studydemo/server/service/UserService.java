package com.studydemo.server.service;

import com.studydemo.server.common.PageResult;
import com.studydemo.server.dto.UserQueryDTO;
import com.studydemo.server.dto.UserSaveDTO;
import com.studydemo.server.dto.UserVO;

/**
 * 用户业务接口。
 */
public interface UserService {

    /**
     * 条件分页查询用户列表，按创建时间倒序。
     *
     * @param query 过滤条件（用户名/手机号模糊、状态精确）+ 分页参数（页码/每页条数）
     * @return 分页结果，records 为当前页的用户视图对象
     */
    PageResult<UserVO> listUsers(UserQueryDTO query);

    /**
     * 查询单个用户详情。
     *
     * @param id 用户主键
     * @return 用户视图对象
     */
    UserVO getUser(Long id);

    /**
     * 新增用户。
     *
     * @param dto 用户名/手机号/状态/备注
     * @return 新增后的用户（含主键与时间）
     */
    UserVO createUser(UserSaveDTO dto);

    /**
     * 修改指定用户。
     *
     * @param id  用户主键
     * @param dto 新的字段值
     * @return 修改后的用户
     */
    UserVO updateUser(Long id, UserSaveDTO dto);

    /**
     * 逻辑删除指定用户。
     *
     * @param id 用户主键
     */
    void deleteUser(Long id);
}
