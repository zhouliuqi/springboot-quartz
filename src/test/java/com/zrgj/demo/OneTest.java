package com.zrgj.demo;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author 肘劉祁
 */

public class OneTest {
    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("zrgj");
        System.out.println(textEncryptor.encrypt("123456"));
    }
}
