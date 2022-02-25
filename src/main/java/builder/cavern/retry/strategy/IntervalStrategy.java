package builder.cavern.retry.strategy;

import builder.cavern.retry.result.TaskResult;

import java.time.Duration;

/**
 * @author cavernBuilder
 * @date 2022/2/23
 */
@FunctionalInterface
public interface IntervalStrategy {

    Duration getNextInterval(TaskResult<?> lastTaskResult);

    IntervalStrategy NO_INTERVAL = lastTaskResult -> Duration.ZERO;

}
