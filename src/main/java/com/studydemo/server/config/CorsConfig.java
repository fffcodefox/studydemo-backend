package com.studydemo.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置。
 *
 * <p>允许的前端来源在 application-dev.yaml / application-prod.yaml 的
 * {@code app.cors.allowed-origins} 里配置（多个用逗号分隔），避免前端换端口、
 * 换域名时改 Java 代码。</p>
 *
 * <p>注意：前端走 Vite 代理（开发）或 Nginx 反向代理（生产）时，浏览器看到的是同源请求，
 * 根本不会触发跨域，这里的配置是「前端直连后端」场景的兜底。</p>
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /** 多个来源之间用逗号分隔 */
    private static final String ORIGIN_SEPARATOR = ",";

    private static final long MAX_AGE_SECONDS = 3600L;

    /** 允许的前端来源，来自配置；缺省值与前端 .env.development 的 DEV_SERVER_PORT 对应 */
    private final String[] allowedOrigins;

    public CorsConfig(
            @Value("${app.cors.allowed-origins:http://localhost:5173}") String allowedOrigins) {
        this.allowedOrigins = splitAndTrim(allowedOrigins);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECONDS);
    }

    /**
     * 按逗号切分并去掉两侧空格 —— 配置里写成 "a, b" 也能正确解析。
     *
     * @param origins 逗号分隔的来源串
     * @return 去空格后的来源数组
     */
    private static String[] splitAndTrim(String origins) {
        String[] parts = origins.split(ORIGIN_SEPARATOR);
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }
}
