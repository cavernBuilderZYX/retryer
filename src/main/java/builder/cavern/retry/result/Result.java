package builder.cavern.retry.result;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author cavernBuilder
 * @date 2022/2/25
 */
public abstract class Result<T> {

    protected T result;

    protected Exception exception;

    protected LocalDateTime startTime;

    protected LocalDateTime endTime;

    public Result() {};

    public Result(T result, Exception exception) {
        this.result = result;
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                ", exception=" + exception +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    /**
     * 获取运行时间
     * @return 运行时长
     */
    public Duration getUsedTime() {
        return Duration.between(startTime, endTime);
    }

    public T getResult() {
        return result;
    }

    public Result<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public Exception getException() {
        return exception;
    }

    public Result<T> setException(Exception exception) {
        this.exception = exception;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Result<T> setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Result<T> setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public abstract int getAttemptCount();

}
