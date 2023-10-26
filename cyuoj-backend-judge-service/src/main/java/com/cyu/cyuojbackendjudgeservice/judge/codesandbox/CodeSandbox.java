package com.cyu.cyuojbackendjudgeservice.judge.codesandbox;

import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeResponse;



/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
