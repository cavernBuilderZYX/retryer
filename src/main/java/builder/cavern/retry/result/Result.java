package builder.cavern.retry.result;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 抽象任务结果类
 * @author cavernBuilder
 * @since 2022/2/25
 */
public abstract class Result<T> {

    /**
     * 原始任务的运行结果
     */
    protected T result;

    /**
     * 任务运行时抛出的异常
     */
    protected Exception exception;

    /**
     * 起始时间
     */
    protected LocalDateTime startTime;

    /**
     * 任务或过程的结束时间
     */
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
