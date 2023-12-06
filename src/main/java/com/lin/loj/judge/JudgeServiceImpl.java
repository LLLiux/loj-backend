package com.lin.loj.judge;

import cn.hutool.json.JSONUtil;
import com.lin.loj.common.ErrorCode;
import com.lin.loj.exception.BusinessException;
import com.lin.loj.judge.codeSandBox.CodeSandBox;
import com.lin.loj.judge.codeSandBox.CodeSandBoxFactory;
import com.lin.loj.judge.codeSandBox.CodeSandBoxProxy;
import com.lin.loj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.lin.loj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.lin.loj.judge.codeSandBox.model.JudgeInfo;
import com.lin.loj.judge.strategy.JudgeContext;
import com.lin.loj.model.dto.question.JudgeCase;
import com.lin.loj.model.entity.Question;
import com.lin.loj.model.entity.QuestionSubmit;
import com.lin.loj.model.enums.QuestionSubmitStatusEnum;
import com.lin.loj.service.QuestionService;
import com.lin.loj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author L
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codeSandBox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(QuestionSubmit questionSubmit) {
        Long questionSubmitId = questionSubmit.getId();

        // 判断当前状态
        Integer status = questionSubmit.getStatus();
        if (!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "判题中，请勿重复操作");
        }
        // 更新数据库（题目状态）
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        // 获取输入用例
        Question question = questionService.getById(questionSubmit.getQuestionId());
        String judgeCaseListJson = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseListJson, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 组装请求
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(questionSubmit.getCode())
                .language(questionSubmit.getLanguage())
                .inputList(inputList)
                .build();
        // 调用代码沙箱执行代码（工厂模式 + 代理模式）
        CodeSandBox codeSandBox = new CodeSandBoxProxy(CodeSandBoxFactory.newInstance(type));
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        // 填充上下文 用于后续判题
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        // 判断执行结果
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 更新数据库（题目状态 + 判题信息）
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }

        return questionSubmitService.getById(questionSubmitId);
    }
}
