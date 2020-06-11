package com.hxzy.crowdfunding.util;

import com.hxzy.crowdfunding.enums.ResponseCodeEnume;
import lombok.Data;

@Data
public class ResponseData<T> { //响应的数据对象

    private Integer code; // 响应码
    private String msg; // 响应信息

    private T data; // 响应数据

    /**
     * 快速响应成功
     * @param data
     * @return
     */
    public static<T> ResponseData ok(T data){
        ResponseData<T> resp = new ResponseData<>();
        resp.setCode(ResponseCodeEnume.SUCCESS.getCode());
        resp.setMsg(ResponseCodeEnume.SUCCESS.getMsg());
        resp.setData(data);
        return resp;
    }

    /**
     * 快速响应失败
     */
    public static<T> ResponseData<T> fail(T data){
        ResponseData<T> resp = new ResponseData<>();
        resp.setCode(ResponseCodeEnume.FAIL.getCode());
        resp.setMsg(ResponseCodeEnume.FAIL.getMsg());
        resp.setData(data);
        return resp;
    }

}
