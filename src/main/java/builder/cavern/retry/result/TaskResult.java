package builder.cavern.retry.result;

import java.time.Duration;

/**
 * @author cavernBuilder
 * @date 2022/2/23
 */
public class TaskResult<T> implements Result<T> {


    Exception exception;

    T result;

    Duration usedTime;



    public TaskResult() {
    }

    @Override
    public Duration getElapseTime() {
        return usedTime;
    }

    @Override
    public T getResult() {
        return result;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public int getAttemptCount() {
        return 1;
    }


    public TaskResult<T> setException(Exception exception) {
        this.exception = exception;
        return this;
    }

    public TaskResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public TaskResult<T> setUsedTime(Duration usedTime) {
        this.usedTime = usedTime;
        return this;
    }

}
