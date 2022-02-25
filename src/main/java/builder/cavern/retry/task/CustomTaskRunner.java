package builder.cavern.retry.task;

import builder.cavern.retry.common.RetryState;
import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.result.TaskResult;
import builder.cavern.retry.strategy.RetryStrategy;

import java.time.Duration;

/**
 * @author cavernBuilder
 * @date 2022/2/25
 */
public class CustomTaskRunner<T> extends TaskRunner<T>{

    protected RetryStrategy retryStrategy;

    public CustomTaskRunner(RetryStrategy retryStrategy) {
        super();
        this.retryStrategy = retryStrategy;
    }

    @Override
    public FinalResult<T> scheduleTask() {
        boolean needToRetry = false;
        this.state = RetryState.FIRST_RUNNING;
        FinalResult<T> finalResult = new FinalResult<>();
        do {
            if (this.state == RetryState.FIRST_RUNNING) {
                this.state = RetryState.RETRY_RUNNING;
            }
            TaskResult<T> taskResult = currentTask.runOneTime();
            boolean taskSucceeded = retryStrategy.getSuccessStrategy().succeed(taskResult);
            if (taskSucceeded) {
                //todo
                break;
            }

            boolean retryShouldStop = retryStrategy.getStopStrategy().shouldStop(taskResult);
            needToRetry = !retryShouldStop;

            //todo
            //进入下一个周期，首先要等待指定时长
            Duration nextInterval = retryStrategy.getIntervalStrategy().getNextInterval(taskResult);
            if (nextInterval != null && !nextInterval.isZero()) {
                try {
                    Thread.sleep(nextInterval.toMillis());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        } while (needToRetry);
        return finalResult;
    }
}
