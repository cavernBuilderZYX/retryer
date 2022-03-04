package builder.cavern.retry.strategy;

import java.time.Duration;

/**
 * 重试策略的组合
 * @author cavernBuilder
 * @since 2022/2/23
 */
public class RetryStrategy {
    private IntervalStrategy intervalStrategy;
    private StopStrategy stopStrategy;
    private SuccessStrategy successStrategy;


    public static RetryStrategyBuilder create() {
        return new RetryStrategyBuilder();
    }


    private RetryStrategy(IntervalStrategy intervalStrategy, StopStrategy stopStrategy, SuccessStrategy successStrategy) {
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


    public static final class RetryStrategyBuilder {

        private IntervalStrategy intervalStrategy;
        private StopStrategy stopStrategy;
        private SuccessStrategy successStrategy;

        private RetryStrategyBuilder(){}

        public RetryStrategyBuilder intervalStrategy(IntervalStrategy intervalStrategy) {
            this.intervalStrategy = intervalStrategy;
            return this;
        }

        public RetryStrategyBuilder stopStrategy(StopStrategy stopStrategy) {
            this.stopStrategy = stopStrategy;
            return this;
        }

        public RetryStrategyBuilder successStrategy(SuccessStrategy successStrategy) {
            this.successStrategy = successStrategy;
            return this;
        }

        public RetryStrategy build() {
            //设置默认值
            if (intervalStrategy == null) {
                intervalStrategy = IntervalStrategy.DEFAULT_NO_INTERVAL;
            }
            if (stopStrategy == null) {
                stopStrategy = StopStrategy.limit(1, Duration.ZERO);
            }
            if (successStrategy == null) {
                successStrategy = SuccessStrategy.DEFAULT_NO_EXCEPTION;
            }
            return new RetryStrategy(intervalStrategy, stopStrategy, successStrategy);
        }
    }
}
