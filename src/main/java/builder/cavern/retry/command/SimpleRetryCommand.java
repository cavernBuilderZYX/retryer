package builder.cavern.retry.command;

import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.result.ProcessResult;
import builder.cavern.retry.result.TaskResult;

import java.time.Duration;
import java.util.concurrent.Callable;

/**
 * @author cavernBuilder
 * @date 2022/2/24
 */
public class SimpleRetryCommand<T> extends RetryCommand<T> {
    int times;
    Duration interval;

    @Override
    public FinalResult<T> execute(Callable<T> callableTask) {
        boolean needToRetry = false;
        TaskResult<T> taskResult;
        FinalResult<T> processResult = null;
        //todo 待完成
        return processResult;
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
