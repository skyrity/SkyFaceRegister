package com.skyrity.utils;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ： VULCAN
 * @date ：2020/01/06 14:55
 * @description : ${description}
 * @path : com.skyrity.utils.MD5Util
 * @modifiedBy ：
 */
public class MD5Util {
    private static Logger logger = Logger.getLogger(MD5Util.class);
    public static String stringToMD5(String plainText) {

        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

}
