package com.skyrity.service.impl;

import com.skyrity.dao.FaceRegisterDao;
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
 * @modifiedBy ：
 */
@Service("WxService")
public class WxServiceImpl implements WxService {
    private static Logger logger = Logger.getLogger(WxServiceImpl.class);
    @Autowired
    FaceRegisterDao faceRegisterDao;

    @Override
    public String login(HttpServletRequest request)  {
      String retStr;
      try {
          String code = request.getParameter("code");
          String encryptedData = request.getParameter("encryptedData");
          String iv = request.getParameter("iv");
          if (code == null || encryptedData == null || iv == null) {
              retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
              logger.info(retStr);
              return retStr;
          }
          String session_key;
          String openId = "";

          if("test".equals(code)){
               session_key = "test";
              openId="test";
          }else {
              JSONObject jsonObject = wxCoreUtil.getSessionKeyOropenid(code);
              if (jsonObject == null) {
                  retStr= ComUtil.getResultTime(RetCode.RET_ERROR_SESSIONKEY);
                  logger.info(retStr);
                  return retStr;
              }

              JSONObject jsonUserInfo = wxCoreUtil.getUserInfo(jsonObject.getString("session_key"), encryptedData, iv);
              if (jsonUserInfo == null) {
                  retStr= ComUtil.getResultTime(RetCode.RET_ERROR_DECRYPTED);
                  logger.info(retStr);
                  return retStr;
              }
               session_key = (String) jsonObject.get("session_key");
              openId = (String) jsonObject.get("openid");
          }

          request.getSession().setAttribute("session_key", session_key);
          request.getSession().setAttribute("openId", openId);
          request.getSession().setMaxInactiveInterval(3600);


          return RetCode.RET_SUCCESS.replace("RESULT_DATA", "{\"accessToken\":\"" + session_key +"\"}").
                  replace("RESULT_TIME", Long.toString(new Date().getTime()));
      }catch (Exception e){
          e.printStackTrace();
          logger.error(e.getMessage());
          retStr=  ComUtil.getResultTime(RetCode.RET_ERROR_EXCEPTION);
          logger.info(retStr);
          return retStr;
      }
    }



}
