package com.skyrity.service.impl;

import com.skyrity.bean.Face_Register;
import com.skyrity.bean.Pager;
import com.skyrity.dao.FaceRegisterDao;
import com.skyrity.service.FaceRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 19:50
 * @description : ${description}
 * @path : com.skyrity.service.impl.FaceRegisterServiceImpl
 * @modifiedBy ：
 */
@Service("FaceRegisterService")
public class FaceRegisterServiceImpl implements FaceRegisterService{

    @Autowired
    FaceRegisterDao faceRegisterDao;

    @Override
    public long add(Face_Register faceRegister) {
        return faceRegisterDao.add(faceRegister);
    }

    @Override
    public long edit(Face_Register faceRegister) {
        return faceRegisterDao.edit(faceRegister);
    }

    @Override
    public Pager<Face_Register> getAll(int pageIndex,int pageSize) {
        String in_Where="";
        return getPager(in_Where,pageIndex,pageSize);
    }

    @Override
    public Pager<Face_Register> getFields(int state,String fields, int pageIndex, int pageSize) {
        //String in_Where;
        StringBuilder in_Where=new StringBuilder("");
        if(fields!=null && !"".equals(fields)){
            in_Where.append("(name like '%"+fields+"%' or telNo like '%"+fields+"%') ");
        }

        if(state!=Face_Register.VALUE_ALL){
           if (in_Where.length()==0) {
               in_Where.append("state=" + state);
           }else{
               in_Where.append("and state=" + state);
           }
        }
        return getPager(in_Where.toString(),pageIndex,pageSize);
    }

    @Override
    public Face_Register getByTelephone(String telNo) {

        return faceRegisterDao.getByTelephone(telNo);
    }

    @Override
    public long approve(int id, int state) {
        return faceRegisterDao.approve(id,state);
    }


    public Pager<Face_Register> getPager(String in_Where, int pageIndex, int pageSize) {
        Map<String,String> params=new HashMap<String, String>(3);
        params.put("out_PageCount", null);
        params.put("out_ResultCount", null);
        params.put("out_SQL", null);
        params.put("in_Table", "Face_Register");
        params.put("in_Key", "id");
        params.put("in_Fields", "*");
        params.put("in_Where", in_Where);
        params.put("in_Order", "id desc");
        params.put("in_Begin", "0");
        params.put("in_PageIndex", ""+pageIndex);
        params.put("in_PageSize", ""+pageSize);

        List<Face_Register> faceRegisters=faceRegisterDao.getByAttrs(params);
        int recordTotal=Integer.parseInt(String.valueOf(params.get("out_ResultCount")));
        int pageCount= Integer.parseInt(String.valueOf(params.get("out_PageCount")));
        return new Pager<Face_Register>(pageIndex,pageSize,recordTotal,pageCount,faceRegisters);

    }
}
