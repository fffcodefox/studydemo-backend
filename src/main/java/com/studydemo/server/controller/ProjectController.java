package com.studydemo.server.controller;

import com.studydemo.server.common.ApiResponseVo;
import com.studydemo.server.common.PageVO;
import com.studydemo.server.dto.ProjectIdReq;
import com.studydemo.server.dto.ProjectQueryDTO;
import com.studydemo.server.dto.ProjectVO;
import com.studydemo.server.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目管理接口（对齐 cdp_service 的 BasicPropertyManageController 风格）。
 *
 * <p>统一返回 {@link ApiResponseVo}；列表、详情均走 POST（入参放 body）；
 * 路径分组在 /api/console/projectManage 下，与现有 /api 代理约定保持一致，
 * 因此前端无需改动 vite proxy（请求 /api/console/projectManage/xxx 仍由 {@code ^/api/} 转发到 8080）。</p>
 */
@RestController
@Tag(name = "内容管理-项目管理API", description = "项目管理API")
@RequestMapping("/api/console/projectManage")
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "项目管理列表查询接口", description = "项目管理列表查询接口")
    @PostMapping(value = "queryProjectList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseVo<PageVO<ProjectVO>> queryProjectList(@RequestBody ProjectQueryDTO reqVo) {
        try {
            return new ApiResponseVo<>(projectService.listProjects(reqVo));
        } catch (Exception e) {
            log.error("项目管理列表查询接口异常，异常信息：{}", e.getMessage(), e);
            return new ApiResponseVo<>(e.getClass().getName(), e.getMessage(), null);
        }
    }

    @Operation(summary = "项目详情查询接口", description = "项目详情查询接口")
    @PostMapping(value = "getProjectDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseVo<ProjectVO> getProjectDetail(@RequestBody ProjectIdReq req) {
        try {
            return new ApiResponseVo<>(projectService.getProject(req.getId()));
        } catch (Exception e) {
            log.error("项目详情查询接口异常，异常信息：{}", e.getMessage(), e);
            return new ApiResponseVo<>(e.getClass().getName(), e.getMessage(), null);
        }
    }
}
