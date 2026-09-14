package com.studydemo.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studydemo.server.common.PageVO;
import com.studydemo.server.domain.Project;
import com.studydemo.server.dto.ProjectQueryDTO;
import com.studydemo.server.dto.ProjectVO;
import com.studydemo.server.mapper.ProjectMapper;
import com.studydemo.server.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目业务实现。
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public PageVO<ProjectVO> listProjects(ProjectQueryDTO query) {
        // @TableLogic 自动追加 is_deleted = 0；次级排序用 id 保证翻页稳定
        LambdaQueryWrapper<Project> wrapper = Wrappers.<Project>lambdaQuery()
                .like(StringUtils.isNotBlank(query.getProjectName()), Project::getProjectName, query.getProjectName())
                .eq(query.getStatus() != null, Project::getStatus, query.getStatus())
                .orderByDesc(Project::getCreateTime)
                .orderByDesc(Project::getId);

        Page<Project> page = new Page<>(query.resolvePageNum(), query.resolvePageSize());
        // 分页由 MybatisPlusConfig 注册的 PaginationInnerInterceptor 落地
        Page<Project> result = projectMapper.selectPage(page, wrapper);

        List<ProjectVO> records = result.getRecords().stream()
                .map(this::toVo)
                .collect(Collectors.toList());
        return PageVO.of(result, records);
    }

    @Override
    public ProjectVO getProject(Long id) {
        Project entity = projectMapper.selectById(id);
        if (entity == null) {
            throw new IllegalArgumentException("项目不存在或已删除");
        }
        return toVo(entity);
    }

    /** 实体转视图对象，并把状态翻译为文案 */
    private ProjectVO toVo(Project entity) {
        ProjectVO vo = new ProjectVO();
        vo.setId(entity.getId());
        vo.setProjectName(entity.getProjectName());
        vo.setProjectCode(entity.getProjectCode());
        vo.setLeader(entity.getLeader());
        vo.setStatus(entity.getStatus());
        vo.setStatusText(toStatusText(entity.getStatus()));
        vo.setDescription(entity.getDescription());
        vo.setStartDate(entity.getStartDate());
        vo.setEndDate(entity.getEndDate());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    /** 状态翻译：1-进行中 0-已结束，其它返回未知 */
    private String toStatusText(Integer status) {
        if (status == null) {
            return null;
        }
        if (status == 1) {
            return "进行中";
        }
        if (status == 0) {
            return "已结束";
        }
        return "未知";
    }
}
