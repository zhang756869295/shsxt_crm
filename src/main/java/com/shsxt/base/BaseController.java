package com.shsxt.base;


import com.shsxt.crm.model.ResultInfo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lp on 2018/1/5.
 */
public class BaseController {


   public ResultInfo success(String msg){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public ResultInfo success(String msg,Object result){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }


   @ModelAttribute
   public  void preMethod(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
   }
}
