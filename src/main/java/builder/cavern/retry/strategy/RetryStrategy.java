package builder.cavern.retry.strategy;

/**
 * 重试策略
 * @author cavernBuilder
 * @date 2022/2/23
 */
public class RetryStrategy {
    IntervalStrategy intervalStrategy;
    StopStrategy stopStrategy;
    SuccessStrategy successStrategy;

    //todo 改为建造者模式
    public RetryStrategy(IntervalStrategy intervalStrategy, StopStrategy stopStrategy, SuccessStrategy successStrategy) {
        this.intervalStrategy = intervalStrategy;
        this.stopStrategy = stopStrategy;
        this.successStrategy = successStrategy;
    }

    public IntervalStrategy getIntervalStrategy() {
        return intervalStrategy;
    }

    public StopStrategy getStopStrategy() {
        return stopStrategy;
    }

    public SuccessStrategy getSuccessStrategy() {
        return successStrategy;
    }

    public RetryStrategy setIntervalStrategy(IntervalStrategy intervalStrategy) {
        this.intervalStrategy = intervalStrategy;
        return this;
    }

    public RetryStrategy setStopStrategy(StopStrategy stopStrategy) {
        this.stopStrategy = stopStrategy;
        return this;
    }

    public RetryStrategy setSuccessStrategy(SuccessStrategy successStrategy) {
        this.successStrategy = successStrategy;
        return this;
    }
}
