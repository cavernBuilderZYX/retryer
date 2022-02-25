package builder.cavern.retry.task;

import builder.cavern.retry.common.RetryState;
import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.strategy.RetryStrategy;

/**
 * @author cavernBuilder
 * @date 2022/2/25
 */
public abstract class TaskRunner<T> {

    protected FinalResult<T> finalResult;

    protected RetryableTask<T> currentTask;

    protected RetryState state;

    protected RetryStrategy retryStrategy;

    public TaskRunner() {
        this.state = RetryState.INITIAL;

    }

    public abstract FinalResult<T> scheduleTask();

}
