package com.skyrity;

import com.skyrity.bean.Face_Register;
import com.skyrity.bean.Pager;
import com.skyrity.service.FaceRegisterService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 19:44
 * @description : ${description}
 * @path : com.skyrity.FaceRegisterServiceTest
 * @modifiedBy ：
 */
public class FaceRegisterServiceTest {

    FaceRegisterService faceRegisterService;
    @Before
    public void init() throws IOException {
        //加载配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //获取对象
        faceRegisterService = (FaceRegisterService) applicationContext.getBean("FaceRegisterService");

    }

   @Test
    public void getAll(){
       Pager<Face_Register> pager= faceRegisterService.getAll(1,30);
       System.out.println(pager);
   }

}
