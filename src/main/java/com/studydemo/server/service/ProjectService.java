package com.studydemo.server.service;

import com.studydemo.server.common.PageVO;
import com.studydemo.server.dto.ProjectQueryDTO;
import com.studydemo.server.dto.ProjectVO;

/**
 * 项目业务接口。
 */
public interface ProjectService {

    /**
     * 条件分页查询项目列表，按创建时间倒序。
     *
     * @param query 过滤条件（项目名称模糊、状态精确）+ 分页参数
     * @return 分页结果（对齐 cdp 的 PageVO）
     */
    PageVO<ProjectVO> listProjects(ProjectQueryDTO query);

    /**
     * 查询单个项目详情。
     *
     * @param id 项目主键
     * @return 项目视图对象
     */
    ProjectVO getProject(Long id);
}
