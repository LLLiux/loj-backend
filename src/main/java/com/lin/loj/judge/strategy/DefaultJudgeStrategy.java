package com.lin.loj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.lin.loj.judge.codeSandBox.model.JudgeInfo;
import com.lin.loj.model.dto.question.JudgeCase;
import com.lin.loj.model.dto.question.JudgeConfig;
import com.lin.loj.model.entity.Question;
import com.lin.loj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略（默认为JAVA）
 * @author L
 */
public class DefaultJudgeStrategy implements JudgeStrategy{
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        // 设置判题信息中的时间与内存
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(judgeInfo.getMemory());
        judgeInfoResponse.setTime(judgeInfo.getTime());
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        // 判断输出个数是否正确
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        if (outputList.size() != judgeCaseList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 逐个判断输出是否正确
        for (int i = 0; i < judgeCaseList.size(); i++) {
            if (!judgeCaseList.get(i).getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // 判断运行时间与内存是否符合要求
        Question question = judgeContext.getQuestion();
        String judgeConfigJson = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigJson, JudgeConfig.class);
        if (judgeInfo.getTime() > judgeConfig.getTimeLimit()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (judgeInfo.getMemory() > judgeConfig.getMemoryLimit()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 设置判题信息中的消息
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
