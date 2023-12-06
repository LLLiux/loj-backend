package com.lin.loj.judge;

import com.lin.loj.judge.codeSandBox.model.JudgeInfo;
import com.lin.loj.judge.strategy.DefaultJudgeStrategy;
import com.lin.loj.judge.strategy.JavaJudgeStrategy;
import com.lin.loj.judge.strategy.JudgeContext;
import com.lin.loj.judge.strategy.JudgeStrategy;
import org.springframework.stereotype.Service;

/**
 * @author L
 */
@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext) {
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
