package com.lin.loj.judge.strategy;

import com.lin.loj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.lin.loj.model.dto.question.JudgeCase;
import com.lin.loj.model.entity.Question;
import com.lin.loj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（策略模式）
 * @author L
 */
@Data
public class JudgeContext {
    /**
     * 判题用例
     */
    List<JudgeCase> judgeCaseList;

    /**
     * 题目
     */
    Question question;

    /**
     * 题目提交
     */
    QuestionSubmit questionSubmit;

    /**
     * 代码执行结果
     */
    ExecuteCodeResponse executeCodeResponse;
}
