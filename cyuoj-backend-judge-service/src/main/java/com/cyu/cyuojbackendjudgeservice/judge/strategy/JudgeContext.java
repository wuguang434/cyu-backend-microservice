package com.cyu.cyuojbackendjudgeservice.judge.strategy;

import com.cyu.cyuojbackendmodel.model.codesandbox.JudgeInfo;
import com.cyu.cyuojbackendmodel.model.dto.question.JudgeCase;
import com.cyu.cyuojbackendmodel.model.entity.Question;
import com.cyu.cyuojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文(用于定义在策略中传递的参数
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Question question;
    private QuestionSubmit questionSubmit;
}
