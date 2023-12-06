package com.lin.loj.judge.codeSandBox.model;

import lombok.Data;

import java.util.List;

/**
 * @author L
 */
@Data
public class ExecuteCodeResponse {
    /**
     * 接口消息
     */
    private String message;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 执行输出
     */
    private List<String> outputList;
}
