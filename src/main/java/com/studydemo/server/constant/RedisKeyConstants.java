package com.studydemo.server.constant;

/**
 * Redis 键常量。
 *
 * <p>P3C 规约：不允许任何魔法值出现在代码中，统一在此定义；常量类必须私有构造方法。</p>
 */
public final class RedisKeyConstants {

    public static final String HELLO_VISIT_KEY = "studydemo:hello:visits";

    private RedisKeyConstants() {
    }
}
