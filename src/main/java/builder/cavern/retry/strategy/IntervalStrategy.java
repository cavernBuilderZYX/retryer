package builder.cavern.retry.strategy;

import builder.cavern.retry.result.ProcessResult;
import builder.cavern.retry.result.TaskResult;

import java.time.Duration;

/**
 * @author cavernBuilder
 * @date 2022/2/23
 */
@FunctionalInterface
public interface IntervalStrategy {

    Duration getNextInterval(TaskResult<?> taskResult, ProcessResult<?> processResult);

    IntervalStrategy NO_INTERVAL = (taskResult, processResult) -> Duration.ZERO;

}
