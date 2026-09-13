package com.studydemo.server.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 插件配置。
 *
 * <p><b>分页插件是分页功能的前置条件，缺了它 {@code selectPage} 不会报错，
 * 而是静默退化成「查全表再内存分页」</b> —— 数据量小时看不出问题，
 * 上线后才暴露，所以这里显式注册。</p>
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 单页最大条数上限。
     *
     * <p>和 {@code UserQueryDTO#MAX_PAGE_SIZE} 保持一致：那边负责把入参收敛到合理范围，
     * 这边作为最后一道兜底，防止有别的入口绕过 DTO 直接构造 Page 对象。</p>
     */
    private static final long MAX_PAGE_SIZE = 100L;

    /**
     * 注册分页拦截器。
     *
     * @return MyBatis-Plus 插件链
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor pagination = new PaginationInnerInterceptor(DbType.MYSQL);
        pagination.setMaxLimit(MAX_PAGE_SIZE);
        // 注意顺序：多个内部拦截器时，分页拦截器必须最后添加
        interceptor.addInnerInterceptor(pagination);
        return interceptor;
    }
}
