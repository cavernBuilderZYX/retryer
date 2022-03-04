package builder.cavern.retry;

import builder.cavern.retry.action.Actions;
import builder.cavern.retry.command.CustomRetryCommand;
import builder.cavern.retry.command.RetryCommand;
import builder.cavern.retry.strategy.RetryStrategy;
import builder.cavern.retry.strategy.StopStrategy;
import builder.cavern.retry.strategy.SuccessStrategy;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 自定义重试策略示例
 * @author cavernBuilder
 * @since 2022/2/26
 */
public class CustomRetryerExample {
    int times = 0;

    public static void main(String[] args) {
        Retryer retryer = new Retryer();
        RetryStrategy retryStrategy = RetryStrategy.create().intervalStrategy((taskResult, processResult) -> Duration.of(1, ChronoUnit.SECONDS))
                .stopStrategy( new StopStrategy.StopWithLimitStrategy(4))
                .successStrategy(SuccessStrategy.DEFAULT_NO_EXCEPTION)
                .build();
        RetryCommand retryCommand = new CustomRetryCommand(retryStrategy, Actions.create()
                .taskFailureAction(((taskResult, processResult) -> System.out.println("Exception:" + taskResult.getException())))
                .build());
        retryer.retry(new CustomRetryerExample()::testMethod, retryCommand);
    }

    void testMethod() {
        ++times;
        if (times < 3) {
            throw new IllegalStateException("错误了！times为：" + times);
        }
        System.out.println("运行正常！times为：" + times);
    }
}
