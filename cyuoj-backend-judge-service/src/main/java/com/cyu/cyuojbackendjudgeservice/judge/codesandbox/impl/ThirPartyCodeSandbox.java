package com.cyu.cyuojbackendjudgeservice.judge.codesandbox.impl;

import com.cyu.cyuojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱(待实现)
 */
public class ThirPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
