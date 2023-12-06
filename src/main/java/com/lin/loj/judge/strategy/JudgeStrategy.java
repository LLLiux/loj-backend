package com.lin.loj.judge.strategy;

import com.lin.loj.judge.codeSandBox.model.JudgeInfo;

/**
 * 判题策略接口（策略模式）
 * @author L
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
