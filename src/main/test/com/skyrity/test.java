package com.skyrity;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author ： VULCAN
 * @date ：2020/01/14 11:42
 * @description : ${description}
 * @path : com.skyrity.test
 * @modifiedBy ：
 */
public class test {
    @Test
    public  void run(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.CHINA);
        System.out.println(sdf.format(new Date()));
    }
}
