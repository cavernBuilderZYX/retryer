package builder.cavern.retry.command;

import builder.cavern.retry.action.Actions;
import builder.cavern.retry.common.RetryState;
import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.result.ProcessResult;
import builder.cavern.retry.result.TaskResult;
import builder.cavern.retry.strategy.IntervalStrategy;
import builder.cavern.retry.strategy.RetryStrategy;
import builder.cavern.retry.strategy.StopStrategy;
import builder.cavern.retry.strategy.SuccessStrategy;
import builder.cavern.retry.task.CustomTaskRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * 简单的重试命令，含重试次数和间隔
 * @author cavernBuilder
 * @since 2022/2/24
 */
public class SimpleRetryCommand extends RetryCommand {
    /**
     * 最大尝试次数
     */
    int times;
    /**
     * 两次尝试的时间间隔
     */
    Duration interval;

    @Override
    public <T> FinalResult<T> execute(Callable<T> callableTask) {
        CustomTaskRunner<T> customTaskRunner = new CustomTaskRunner<T>(callableTask,
                RetryStrategy.create().stopStrategy(StopStrategy.limit(times, null))
                        .intervalStrategy(IntervalStrategy.constantInterval(interval)).successStrategy(SuccessStrategy.DEFAULT_NO_EXCEPTION).build(),
                Actions.NONE);
        return customTaskRunner.scheduleTask();
    }

    public SimpleRetryCommand(int times) {
        this(times, null);
    }

    public SimpleRetryCommand(int times, Duration interval) {
        this(times, interval, null, null);
    }

    public SimpleRetryCommand(int times, Duration interval, Runnable failureAction, Runnable succeedAction) {
        if (times <= 0) {
            throw new IllegalArgumentException("尝试次数必须为正数。");
        }
        if (interval != null && interval.compareTo(Duration.ZERO) < 0) {
            throw new IllegalArgumentException("重试间隔若非空，必须为非负时长。");
        }
        this.times = times;
        this.interval = interval;
    }
}
