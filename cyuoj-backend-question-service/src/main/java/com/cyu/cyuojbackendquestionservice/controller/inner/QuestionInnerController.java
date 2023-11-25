package com.cyu.cyuojbackendquestionservice.controller.inner;

import com.cyu.cyuojbackendmodel.model.entity.Question;
import com.cyu.cyuojbackendmodel.model.entity.QuestionSubmit;
import com.cyu.cyuojbackendserviceclient.service.QuestionFeignClient;
import com.cyu.cyuojbackendquestionservice.service.QuestionService;
import com.cyu.cyuojbackendquestionservice.service.QuestionSubmitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 实现服务内部调用的一些接口(不给前端)
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
