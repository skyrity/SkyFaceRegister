package com.skyrity;

import com.skyrity.utils.MD5Util;
import org.junit.Test;

/**
 * @author ： VULCAN
 * @date ：2020/01/06 14:48
 * @description : ${description}
 * @path : com.skyrity.MD5Test
 * @modifiedBy ：
 */
public class MD5Test {

    @Test
    public void md5(){
        System.out.println(MD5Util.stringToMD5("admin"));
    }
}
