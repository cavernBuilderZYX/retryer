package builder.cavern.retry.command;

import builder.cavern.retry.action.Actions;
import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.strategy.RetryStrategy;
import builder.cavern.retry.task.CustomTaskRunner;

import java.util.concurrent.Callable;

/**
 * @author cavernBuilder
 * @date 2022/2/24
 */
public class CustomRetryCommand<T> extends RetryCommand<T> {

    private final RetryStrategy retryStrategy;

    private final Actions actions;

    private CustomTaskRunner<T> taskRunner;

    public CustomRetryCommand(RetryStrategy retryStrategy, Actions actions) {
        //todo 校验与转换
        this.retryStrategy = retryStrategy;
        this.actions = actions;
    }

    @Override
    public FinalResult<T> execute(Callable<T> callableTask) {
        taskRunner = new CustomTaskRunner<>(callableTask, retryStrategy, actions);
        return taskRunner.scheduleTask();
    }


}
