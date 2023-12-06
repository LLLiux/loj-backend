package com.lin.loj.judge;

import com.lin.loj.model.entity.QuestionSubmit;

/**
 * 判题服务
 * @author L
 */
public interface JudgeService {
    QuestionSubmit doJudge(QuestionSubmit questionSubmit);
}
