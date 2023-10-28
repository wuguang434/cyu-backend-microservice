package com.cyu.cyuojbackendjudgeservice.judge.codesandbox;

import com.cyu.cyuojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.cyu.cyuojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.cyu.cyuojbackendjudgeservice.judge.codesandbox.impl.ThirPartyCodeSandbox;

/**
 * 代码沙箱工程(根据字符串参数创建指定的代码沙箱实例
 */
public class CodeSandboxFactory {
    /**
     * 创建代码沙箱实例
     * @param type
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
