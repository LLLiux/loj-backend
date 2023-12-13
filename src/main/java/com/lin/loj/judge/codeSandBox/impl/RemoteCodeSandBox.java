package com.lin.loj.judge.codeSandBox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.lin.loj.common.ErrorCode;
import com.lin.loj.exception.BusinessException;
import com.lin.loj.judge.codeSandBox.CodeSandBox;
import com.lin.loj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.lin.loj.judge.codeSandBox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

public class RemoteCodeSandBox implements CodeSandBox {
    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://localhost:8100/executeCode";
        String json= JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
