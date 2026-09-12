package com.studydemo.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.server.domain.SysUser;

import org.springframework.stereotype.Repository;

/**
 * 用户 Mapper。
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
}
