package com.skyrity.controller;

import com.skyrity.bean.Face_Register;
import com.skyrity.bean.Pager;
import com.skyrity.service.WebService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 21:12
 * @description : 提供后台PC端管理服务
 * @path : com.skyrity.controller.WebController
 * @modifiedBy ：
 */


@Controller
@RequestMapping("web")
public class WebController {
    private static Logger logger = Logger.getLogger(WebController.class);
    @Autowired
    WebService webService;

    @RequestMapping("/login.do")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("表现层，login...");
        String retStr;
        ModelAndView modelAndView = null;
        String userName = request.getParameter("userName");//用户名
        String password = request.getParameter("password");//密码
        //1.判断参数
        if (userName == null || password == null) {
            modelAndView = new ModelAndView("login");
            return modelAndView;
        }

        String retCode = webService.login(request, userName, password);
        JSONObject jsonObject = JSONObject.fromObject(retCode);

        if (jsonObject.getInt("result_code") == 0) {
           modelAndView = new ModelAndView("redirect:../views/approvefaces.jsp");
        } else {
            modelAndView = new ModelAndView("redirect:../views/login.jsp");
        }
        return modelAndView;
    }



}
