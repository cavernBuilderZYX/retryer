package builder.cavern.retry.task;

import builder.cavern.retry.Retryer;
import builder.cavern.retry.action.Actions;
import builder.cavern.retry.command.CustomRetryCommand;
import builder.cavern.retry.command.RetryCommand;
import builder.cavern.retry.strategy.IntervalStrategy;
import builder.cavern.retry.strategy.RetryStrategy;
import builder.cavern.retry.strategy.StopStrategy;
import builder.cavern.retry.strategy.SuccessStrategy;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author cavernBuilder
 * @date 2022/2/26
 */
public class RetryerExample {
    int times = 0;

    public static void main(String[] args) {
        Retryer retryer = new Retryer();
        RetryStrategy retryStrategy = new RetryStrategy((taskResult, processResult) -> Duration.of(1, ChronoUnit.SECONDS),
                new StopStrategy.StopWithLimitStrategy(4),
                SuccessStrategy.DEFAULT);
        RetryCommand<Void> retryCommand = new CustomRetryCommand<>(retryStrategy, Actions.create()
                .taskFailureAction(((taskResult, processResult) -> System.out.println("Exception:" + taskResult.getException()))).build());
        retryer.retry(new RetryerExample()::testMethod, retryCommand);
    }

    void testMethod() {
        ++times;
        if (times < 3) {
            throw new IllegalStateException("错误了！times为：" + times);
        }
        System.out.println("运行正常！times为：" + times);
    }
}
