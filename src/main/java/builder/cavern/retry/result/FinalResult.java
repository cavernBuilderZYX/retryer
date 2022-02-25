package builder.cavern.retry.result;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

/**
 * @author cavernBuilder
 * @date 2022/2/23
 */
public class FinalResult<T> implements Result<T> {

    T result;

    Exception finalException;

    Duration totalUsedTime;

    int attemptCount;

    @Override
    public Duration getElapseTime() {
        return totalUsedTime;
    }

    @Override
    public T getResult() {
        return result;
    }

    @Override
    public Exception getException() {
        return finalException;
    }

    @Override
    public int getAttemptCount() {
        return attemptCount;
    }

    public FinalResult<T> addTime(Duration taskUsedTime) {
        if (totalUsedTime == null) {
            totalUsedTime = taskUsedTime;
        } else {
            totalUsedTime = totalUsedTime.plus(taskUsedTime);
        }
        return this;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setFinalException(Exception finalException) {
        this.finalException = finalException;
    }
}
