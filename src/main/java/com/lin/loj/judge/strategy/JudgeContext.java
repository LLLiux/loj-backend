package com.lin.loj.judge.strategy;

import com.lin.loj.judge.codeSandBox.model.JudgeInfo;
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
     * 执行输出
     */
    List<String> outputList;

    /**
     * 判题用例
     */
    List<JudgeCase> judgeCaseList;

    /**
     * 判题信息
     */
    JudgeInfo judgeInfo;

    /**
     * 题目
     */
    Question question;

    /**
     * 题目提交
     */
    QuestionSubmit questionSubmit;
}
