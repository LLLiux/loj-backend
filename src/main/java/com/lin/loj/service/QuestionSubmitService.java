package com.lin.loj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.loj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.lin.loj.model.entity.QuestionSubmit;
import com.lin.loj.model.entity.User;

/**
* @author L
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-11-29 18:04:19
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);
}
