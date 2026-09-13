package com.studydemo.server.controller;

import com.studydemo.server.common.PageResult;
import com.studydemo.server.common.Result;
import com.studydemo.server.dto.UserQueryDTO;
import com.studydemo.server.dto.UserSaveDTO;
import com.studydemo.server.dto.UserVO;
import com.studydemo.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户管理接口。
 *
 * <p>列表查询与详情查询分离：GET /api/users 走条件分页列表，GET /api/users/{id} 走单条详情。</p>
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 分页列表查询：支持 username/phone 模糊、status 精确过滤
     *
     * @param query 过滤条件 + 分页参数（pageNum/pageSize 不传时默认第 1 页、每页 10 条）
     * @return 分页结果，data 结构为 {records, total, pageNum, pageSize, pages}
     */
    @GetMapping
    public Result<PageResult<UserVO>> list(UserQueryDTO query) {
        return Result.success(userService.listUsers(query));
    }

    /**
     * 详情查询
     */
    @GetMapping("/{id}")
    public Result<UserVO> detail(@PathVariable Long id) {
        return Result.success(userService.getUser(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public Result<UserVO> create(@Valid @RequestBody UserSaveDTO dto) {
        return Result.success(userService.createUser(dto));
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public Result<UserVO> update(@PathVariable Long id, @Valid @RequestBody UserSaveDTO dto) {
        return Result.success(userService.updateUser(id, dto));
    }

    /**
     * 删除（逻辑删除）
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }
}
