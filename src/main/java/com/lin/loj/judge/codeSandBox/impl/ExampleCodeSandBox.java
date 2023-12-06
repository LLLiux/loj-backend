package com.lin.loj.judge.codeSandBox.impl;

import com.lin.loj.judge.codeSandBox.CodeSandBox;
import com.lin.loj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.lin.loj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.lin.loj.judge.codeSandBox.model.JudgeInfo;

public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("这是测试版代码沙箱");
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setMessage("测试执行成功");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMemory(1000L);
        judgeInfo.setTime(1000L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
