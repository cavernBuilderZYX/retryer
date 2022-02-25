package builder.cavern.retry.command;

import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.result.TaskResult;
import builder.cavern.retry.task.RetryableTask;

import java.time.Duration;
import java.util.concurrent.Callable;

/**
 * @author cavernBuilder
 * @date 2022/2/24
 */
public class SimpleRetryCommand extends RetryCommand {
    int times;
    Duration interval;

    @Override
    public <T> FinalResult<T> execute(Callable<T> callableTask) {
        boolean needToRetry = false;
        TaskResult<T> taskResult;
        FinalResult<T> finalResult = null;
        do {
            SimpleRetryableTask<T> retryableTask = new SimpleRetryableTask<>(callableTask);
            retryableTask.runOneTime();
        } while (needToRetry);
        return finalResult;
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
        this.failureAction = failureAction;
        this.succeedAction = succeedAction;
    }

    static class SimpleRetryableTask<T> extends RetryableTask<T> {
        public SimpleRetryableTask(Callable<T> originalTask) {
            super(originalTask);
        }


    }
}
