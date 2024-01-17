package com.zrgj.quartz.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 肘劉祁
 * Jasypt 加密配置类
 */
@Configuration
public class JasyptConfig {

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        // 创建 PooledPBEStringEncryptor 实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 创建 SimpleStringPBEConfig 实例，用于配置加密算法的参数
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        // 设置加密密钥
        config.setPassword("NjlMhBsBLnX8StlAOXYkXX4Vh8Rpq40I");
        // 设置加密算法
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        // 设置密钥获取迭代次数
        config.setKeyObtentionIterations("1000");
        // 设置加密池大小
        config.setPoolSize("1");
        // 设置加密提供者名称
        config.setProviderName("SunJCE");
        // 设置盐生成器类名
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        // 设置初始化向量（IV）生成器类名
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        // 设置加密输出类型为 base64
        config.setStringOutputType("base64");
        // 将配置应用到 PooledPBEStringEncryptor 实例
        encryptor.setConfig(config);
        return encryptor;
    }
}
