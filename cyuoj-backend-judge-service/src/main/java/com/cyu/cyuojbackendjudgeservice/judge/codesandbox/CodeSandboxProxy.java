package com.cyu.cyuojbackendjudgeservice.judge.codesandbox;

import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 代理代码沙箱接口
 */
@Slf4j
@AllArgsConstructor
public class CodeSandboxProxy implements CodeSandbox {
    private final CodeSandbox codeSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("\n\n代码沙箱请求信息:" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("\n\n代码沙箱响应信息:" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
