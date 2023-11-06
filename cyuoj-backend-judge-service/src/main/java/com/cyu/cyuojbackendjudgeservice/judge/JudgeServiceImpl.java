package com.cyu.cyuojbackendjudgeservice.judge;
import cn.hutool.json.JSONUtil;
import com.cyu.cyuojbackendcommon.common.ErrorCode;
import com.cyu.cyuojbackendcommon.exception.BusinessException;
import com.cyu.cyuojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.cyu.cyuojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.cyu.cyuojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.cyu.cyuojbackendjudgeservice.judge.strategy.JudgeContext;
import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.cyu.cyuojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.cyu.cyuojbackendmodel.model.codesandbox.JudgeInfo;
import com.cyu.cyuojbackendmodel.model.dto.question.JudgeCase;
import com.cyu.cyuojbackendmodel.model.entity.Question;
import com.cyu.cyuojbackendmodel.model.entity.QuestionSubmit;
import com.cyu.cyuojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.cyu.cyuojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
/**
 * 判题服务实现类
 */
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionFeignClient questionFeignClient;


    @Resource
    private JudgeManager judgeManager;
    @Value("${codesandbox.type:example}")
    private String type;


    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1).传入题目的提交id,获取到对应的题目,提交信息(包含代码,编程语言等)
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //2)如果题目提交状态不为等待中,就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        //3)更改判题(题目提交)的主管那台为"判题中",防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        //4).调用沙箱,获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String judgeCaseStr = question.getJudgeCase();
        //获取输入用例,并将字符串转为JSON数组形式
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        //使用lamda表达式获取每一项输入值拼成一个数组
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        //5.根据沙箱的执行结果,设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));

        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionId);
        return questionSubmitResult;
    }


}
