package builder.cavern.retry;

import builder.cavern.retry.command.RetryCommand;
import builder.cavern.retry.command.SimpleRetryCommand;
import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.strategy.RetryStrategy;
import builder.cavern.retry.strategy.StopStrategy;
import builder.cavern.retry.strategy.SuccessStrategy;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 简单重试示例
 * @author cavernBuilder
 * @since 2022/3/3 12:57
 */
public class SimpleRetryerExample {

    int times = 0;

    public static void main(String[] args) {
        Retryer retryer = new Retryer();
        RetryStrategy retryStrategy = RetryStrategy.create().intervalStrategy((taskResult, processResult) -> Duration.of(1, ChronoUnit.SECONDS))
                .stopStrategy( new StopStrategy.StopWithLimitStrategy(2))
                .successStrategy(SuccessStrategy.DEFAULT_NO_EXCEPTION)
                .build();
        RetryCommand retryCommand = new SimpleRetryCommand(2, Duration.of(1, ChronoUnit.SECONDS));
        FinalResult<Void> finalResult = retryer.retry(new SimpleRetryerExample()::testMethod, retryCommand);
        System.out.println("重试结果：" + finalResult);
    }

    void testMethod() {
        ++times;
        if (times < 3) {
            throw new IllegalStateException("错误了！times为：" + times);
        }
        System.out.println("运行正常！times为：" + times);
    }
}
