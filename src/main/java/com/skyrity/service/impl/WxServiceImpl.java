package com.skyrity.service.impl;

import com.skyrity.bean.Face_Register;
import com.skyrity.bean.Pager;
import com.skyrity.service.FaceRegisterService;
import com.skyrity.service.WxService;
import com.skyrity.utils.ComUtil;
import com.skyrity.utils.RetCode;
import com.skyrity.utils.wxCoreUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 20:18
 * @description : ${description}
 * @path : com.skyrity.service.impl.WxServiceImpl
 */
@Service("WxService")
public class WxServiceImpl implements WxService {
    private static Logger logger = Logger.getLogger(WxServiceImpl.class);

    @Autowired
    private FaceRegisterService faceRegisterService;

    @Override
    public String login(HttpServletRequest request) {
        String retStr;
        try {
            String code = request.getParameter("code");
            String encryptedData = request.getParameter("encryptedData");
            String iv = request.getParameter("iv");
            if (code == null || encryptedData == null || iv == null) {
                retStr = ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
                logger.info(retStr);
                return retStr;
            }
            String session_key;
            String openId ;

            if ("test".equals(code)) {
                session_key = "test";
                openId = "test";
            } else {
                JSONObject jsonObject = wxCoreUtil.getSessionKeyOropenid(code);
                if (jsonObject == null) {
                    retStr = ComUtil.getResultTime(RetCode.RET_ERROR_SESSIONKEY);
                    logger.info(retStr);
                    return retStr;
                }

                JSONObject jsonUserInfo = wxCoreUtil.getUserInfo(jsonObject.getString("session_key"), encryptedData, iv);
                if (jsonUserInfo == null) {
                    retStr = ComUtil.getResultTime(RetCode.RET_ERROR_DECRYPTED);
                    logger.info(retStr);
                    return retStr;
                }
                session_key = (String) jsonObject.get("session_key");
                openId = (String) jsonObject.get("openid");
            }

            request.getSession().setAttribute("session_key", session_key);
            request.getSession().setAttribute("openId", openId);
            request.getSession().setMaxInactiveInterval(3600);


            return RetCode.RET_SUCCESS.replace("RESULT_DATA", "{\"accessToken\":\"" + session_key + "\"}").
                    replace("RESULT_TIME", Long.toString(new Date().getTime()));
        } catch (Exception e) {

            e.printStackTrace();
            logger.error(e.getMessage());
            retStr = ComUtil.getResultTime(RetCode.RET_ERROR_EXCEPTION);
            logger.info(retStr);
            return retStr;
        }
    }


    @Override
    public String getfaces(HttpServletRequest request, int state, String fields, int pageNum, int pageSize) {
        String retStr;

        String openId= (String) request.getSession().getAttribute("openId");
        if(openId==null || openId.equals("")){
            openId="-1";
        }
        StringBuilder in_Where = new StringBuilder("openId="+openId);
        if (fields != null && !"".equals(fields)) {
            String field1=" and (name like '%" + fields + "%' or telNo like '%" + fields + "%') ";
            in_Where.append(field1);
        }

        if (state != Face_Register.VALUE_ALL) {
            //if (in_Where.length() == 0) {
            //    in_Where.append("state=" + state);
            //} else {
            String field2="and state="+state;
                in_Where.append(field2);
            //}
        }
        //3.查询数据库
        Pager<Face_Register> pager = faceRegisterService.getPager(in_Where.toString(), pageNum, pageSize);

        JSONObject jsonData = JSONObject.fromObject(pager);
        request.setAttribute("pager", pager);
        retStr = RetCode.RET_SUCCESS.replace("RESULT_DATA", jsonData.toString()).
                replace("RESULT_TIME", Long.toString(new Date().getTime()));
        logger.info(retStr);
        return retStr;
    }
}
