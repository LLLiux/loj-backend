package com.lin.loj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.loj.common.ErrorCode;
import com.lin.loj.exception.BusinessException;
import com.lin.loj.mapper.QuestionSubmitMapper;
import com.lin.loj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.lin.loj.model.entity.Question;
import com.lin.loj.model.entity.QuestionSubmit;
import com.lin.loj.model.entity.User;
import com.lin.loj.model.enums.QuestionSubmitLanguageEnum;
import com.lin.loj.model.enums.QuestionSubmitStatusEnum;
import com.lin.loj.service.QuestionService;
import com.lin.loj.service.QuestionSubmitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author L
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2023-11-29 18:04:19
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    /**
     * 提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        Long questionId = questionSubmitAddRequest.getQuestionId();
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(loginUser.getId());
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setLanguage(language);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        // 设置初始状态
        questionSubmit.setJudgeInfo("{}");
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());

        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交数据插入失败");
        }

        Long questionSubmitId = questionSubmit.getId();
        // 判题
        return questionSubmitId;
    }
}




