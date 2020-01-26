package com.skyrity;

import com.skyrity.bean.Face_System;
import com.skyrity.service.FaceSystemService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 19:57
 * @description : ${description}
 * @path : com.skyrity.FaceSystemServiceTest
 * @modifiedBy ：
 */
public class FaceSystemServiceTest {

    FaceSystemService faceSystemService;
    @Before
    public void init() throws IOException {
        //加载配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //获取对象
        faceSystemService = (FaceSystemService) applicationContext.getBean("FaceSystemService");

    }

    @Test
    public void getSystem(){
        Face_System faceSystem= faceSystemService.getSystem();
        System.out.println(faceSystem);
    }
}
