package com.studydemo.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.server.domain.DemoMessage;

import org.springframework.stereotype.Repository;

/**
 * 示例消息 Mapper。
 */
@Repository
public interface DemoMessageMapper extends BaseMapper<DemoMessage> {
}
