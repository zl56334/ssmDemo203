package com.example.ssmdemo203.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 自定义配置
 * </p>
 *
 * @author zhanglin
 * @date Created in 2018-12-13 10:56
 */
@Configuration
@ConfigurationProperties(prefix = "custom.config")
@Data
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
