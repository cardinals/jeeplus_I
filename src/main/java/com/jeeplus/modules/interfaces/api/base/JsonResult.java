package com.jeeplus.modules.interfaces.api.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Maps;
import com.jeeplus.common.persistence.Page;

import java.util.Map;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JsonResult {
    private boolean success = true;// 是否成功
    private String errorCode = "-1";//错误代码
    private String msg = "操作成功";// 提示信息
    private Object body;




    public JsonResult(){}
    public JsonResult(Object data){
        setBody(data);
    }

    public JsonResult(Exception e){
        if(e instanceof RuntimeException){
            errorCode = ErrorCode.RunTime_Error.ordinal()+"";
        }else{
            errorCode = "999"; //未知错误
        }
        msg = e.getMessage();
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        if (body instanceof Page) {
            Page page = (Page) body;
            Map map = Maps.newHashMap();
            map.put("data",page.getList());
            map.put("pageNo",page.getPageNo());
            map.put("pageSize",page.getPageSize());
            map.put("totalPage",page.getTotalPage());
            this.body = map;
        } else {
            this.body = body;
        }
    }
}
