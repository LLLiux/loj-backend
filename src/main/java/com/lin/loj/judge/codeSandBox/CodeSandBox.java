package com.lin.loj.judge.codeSandBox;

import com.lin.loj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.lin.loj.judge.codeSandBox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 * @author L
 */
public interface CodeSandBox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
