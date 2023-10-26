package com.cyu.cyuojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.cyu.cyuojbackendcommon.common.ErrorCode;
import com.cyu.cyuojbackendcommon.exception.BusinessException;
import com.cyu.cyuojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeResponse;


/**
 * 远程代码沙箱(实际调用接口的沙箱)
 */
public class RemoteCodeSandbox implements CodeSandbox {

    //定义鉴权请求头和密码
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("==============================远程代码沙箱启动==============================");
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);//把请求参数转化为JSON格式使用
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StrUtil.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error,message =" + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);//把字符串转化为需要的响应类
    }
}
