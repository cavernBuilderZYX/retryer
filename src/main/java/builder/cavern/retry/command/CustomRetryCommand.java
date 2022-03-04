package builder.cavern.retry.command;

import builder.cavern.retry.action.Actions;
import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.strategy.RetryStrategy;
import builder.cavern.retry.task.CustomTaskRunner;
import builder.cavern.retry.task.TaskRunner;

import java.util.concurrent.Callable;

/**
 * 自定义的重试命令，支持自定义的重试策略
 * @author cavernBuilder
 * @since 2022/2/24
 */
public class CustomRetryCommand extends RetryCommand {


    private final RetryStrategy retryStrategy;

    private final Actions actions;

    /**
     * 使用重试策略和节点动作初始化自定义的重试命令。方法保证传参的鲁棒性。
     * @param retryStrategy
     * @param actions
     */
    public CustomRetryCommand(RetryStrategy retryStrategy, Actions actions) {
        if (retryStrategy == null) {
            retryStrategy = RetryStrategy.create().build();
        }
        if (actions == null) {
            actions = Actions.NONE;
        }
        this.retryStrategy = retryStrategy;
        this.actions = actions;
    }

    @Override
    public <T> FinalResult<T> execute(Callable<T> callableTask) {
        TaskRunner<T> taskRunner = new CustomTaskRunner<>(callableTask, retryStrategy, actions);
        return taskRunner.scheduleTask();
    }


}
