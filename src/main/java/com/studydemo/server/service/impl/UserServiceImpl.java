package com.studydemo.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.studydemo.server.domain.SysUser;
import com.studydemo.server.dto.UserQueryDTO;
import com.studydemo.server.dto.UserSaveDTO;
import com.studydemo.server.dto.UserVO;
import com.studydemo.server.mapper.SysUserMapper;
import com.studydemo.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户业务实现。
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    /** 状态缺省值：1-启用 */
    private static final int DEFAULT_STATUS = 1;

    private final SysUserMapper sysUserMapper;

    public UserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public List<UserVO> listUsers(UserQueryDTO query) {
        // 条件按需拼接；@TableLogic 自动追加 is_deleted = 0
        List<SysUser> list = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                .like(StringUtils.isNotBlank(query.getUsername()), SysUser::getUsername, query.getUsername())
                .like(StringUtils.isNotBlank(query.getPhone()), SysUser::getPhone, query.getPhone())
                .eq(query.getStatus() != null, SysUser::getStatus, query.getStatus())
                .orderByDesc(SysUser::getCreateTime));
        return list.stream().map(this::toVo).collect(Collectors.toList());
    }

    @Override
    public UserVO getUser(Long id) {
        SysUser entity = sysUserMapper.selectById(id);
        if (entity == null) {
            throw new IllegalArgumentException("用户不存在或已删除");
        }
        return toVo(entity);
    }

    @Override
    public UserVO createUser(UserSaveDTO dto) {
        SysUser entity = new SysUser();
        entity.setUsername(dto.getUsername());
        entity.setPhone(dto.getPhone());
        // 状态缺省默认启用，避免写入 NULL
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : DEFAULT_STATUS);
        entity.setRemark(dto.getRemark());
        sysUserMapper.insert(entity);
        return toVo(entity);
    }

    @Override
    public UserVO updateUser(Long id, UserSaveDTO dto) {
        SysUser exist = sysUserMapper.selectById(id);
        if (exist == null) {
            throw new IllegalArgumentException("用户不存在或已删除");
        }
        exist.setUsername(dto.getUsername());
        exist.setPhone(dto.getPhone());
        if (dto.getStatus() != null) {
            exist.setStatus(dto.getStatus());
        }
        exist.setRemark(dto.getRemark());
        sysUserMapper.updateById(exist);
        return toVo(exist);
    }

    @Override
    public void deleteUser(Long id) {
        // 逻辑删除：@TableLogic 把 is_deleted 置 1，返回受影响行数
        if (sysUserMapper.deleteById(id) == 0) {
            throw new IllegalArgumentException("用户不存在或已删除");
        }
    }

    /**
     * 实体转视图对象。
     *
     * @param entity 持久化实体
     * @return 前端视图对象
     */
    private UserVO toVo(SysUser entity) {
        UserVO vo = new UserVO();
        vo.setId(entity.getId());
        vo.setUsername(entity.getUsername());
        vo.setPhone(entity.getPhone());
        vo.setStatus(entity.getStatus());
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }
}
