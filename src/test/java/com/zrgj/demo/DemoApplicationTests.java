package com.zrgj.demo;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {

    @Resource(name = "jasyptStringEncryptor")
    private StringEncryptor encryptor;

    @Test
    void contextLoads() {
        System.out.println(encryptor.encrypt("123456"));
        System.out.println(encryptor.encrypt("123456"));
        System.out.println(encryptor.encrypt("123456"));
        System.out.println(encryptor.encrypt("123456"));
    }

}
