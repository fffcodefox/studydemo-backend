package com.studydemo.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.studydemo.server.constant.RedisKeyConstants;
import com.studydemo.server.domain.DemoMessage;
import com.studydemo.server.dto.HelloVO;
import com.studydemo.server.mapper.DemoMessageMapper;
import com.studydemo.server.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 打招呼业务实现。
 */
@Service
public class HelloServiceImpl implements HelloService {

    private static final Logger log = LoggerFactory.getLogger(HelloServiceImpl.class);

    private static final String DEFAULT_NAME = "World";
    private static final String EMPTY_NAME = "";
    private static final String GREETING_PREFIX = "Hello, ";
    private static final String GREETING_SUFFIX = "! 欢迎来到 studydemo 前后端分离示例。";

    private final StringRedisTemplate stringRedisTemplate;
    private final DemoMessageMapper demoMessageMapper;

    public HelloServiceImpl(StringRedisTemplate stringRedisTemplate, DemoMessageMapper demoMessageMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.demoMessageMapper = demoMessageMapper;
    }

    @Override
    public HelloVO sayHello(String name) {
        String realName = EMPTY_NAME.equals(name) || name == null ? DEFAULT_NAME : name;
        String message = GREETING_PREFIX + realName + GREETING_SUFFIX;

        // Redis 访问计数（验证 Redis 连通）
        Long visits = stringRedisTemplate.opsForValue().increment(RedisKeyConstants.HELLO_VISIT_KEY);
        if (visits == null) {
            visits = 0L;
        }

        // MySQL 落库（验证 MySQL 连通；表由 schema.sql 在 dev 自动创建，不可用时忽略）
        DemoMessage entity = new DemoMessage();
        entity.setName(realName);
        entity.setContent(message);
        try {
            demoMessageMapper.insert(entity);
        } catch (Exception ex) {
            log.warn("写入示例消息表失败（已忽略）：{}", ex.getMessage());
        }

        Long dbCount = demoMessageMapper.selectCount(Wrappers.emptyWrapper());
        if (dbCount == null) {
            dbCount = 0L;
        }

        HelloVO vo = new HelloVO();
        vo.setName(realName);
        vo.setMessage(message);
        vo.setVisits(visits);
        vo.setDbCount(dbCount);
        return vo;
    }
}
