package com.yukiice.takeoutserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;

@SpringBootTest
class TakeOutServerApplicationTests {


    public static final char[] chs = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    /**
     * MD5加密
     * @param str
     * @return 32位16进制字符串,无符号
     * @throws Exception
     */
    public static String getMD5(String str) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(str.getBytes("UTF-8"));
        byte[] bys = md5.digest();

        StringBuilder sb = new StringBuilder(32);
        int index;
        for (byte b : bys) {
            // 将Java 中带符号位的byte 转换为不带符号位的
            index = b & 0xff;
            // 16进制数的高位
            sb.append(chs[index >> 4]);
            // 16进制数的低位
            sb.append(chs[index % 16]);
        }
        // 返回的结果为32位的16进制数字符串
        return sb.toString();
    }

    @Test
    void contextLoads() throws Exception {
        String appKey = "yunhetitian";
        String appKeyPwd = "I7x5W2p7t5e3J4B7";
        String time = String.valueOf(System.currentTimeMillis());
        String signature = getMD5(getMD5(appKey+"_"+appKeyPwd)+time);
        System.out.println(time);
        System.out.println(signature);
    }
}
