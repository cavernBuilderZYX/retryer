package builder.cavern.retry.strategy;

import builder.cavern.retry.result.ProcessResult;
import builder.cavern.retry.result.TaskResult;

import java.time.Duration;

/**
 * @author cavernBuilder
 * @date 2022/2/23
 */
@FunctionalInterface
public interface StopStrategy {

    boolean shouldStop(TaskResult<?> taskResult, ProcessResult<?> processResult);

    StopStrategy NEVER_STOP = (taskResult, processResult) -> false;

    class StopWithLimitStrategy implements StopStrategy {

        Integer maxTryCount;

        Duration maxTryTime;

        public StopWithLimitStrategy(Integer maxTryCount) {
            this(maxTryCount, null);
        }

        public StopWithLimitStrategy(Duration maxTryTime) {
            this(null, maxTryTime);
        }

        public StopWithLimitStrategy(Integer maxTryCount, Duration maxTryTime) {
            if (maxTryTime != null && maxTryTime.compareTo(Duration.ZERO) <= 0) {
                throw new IllegalArgumentException("最大尝试时间必须为正!");
            }
            if (maxTryCount != null && maxTryCount <= 0) {
                throw new IllegalArgumentException("最大尝试次数必须为正!");
            }
            this.maxTryCount = maxTryCount;
            this.maxTryTime = maxTryTime;
        }

        @Override
        public boolean shouldStop(TaskResult<?> taskResult, ProcessResult<?> processResult) {
            if (maxTryCount == null && maxTryTime == null) {
                return false;
            }
            if (maxTryCount == null) {
                return processResult.getUsedTime().compareTo(maxTryTime) > 0;
            }
            if (maxTryTime == null) {
                return processResult.getAttemptCount() >= maxTryCount;
            }
            return processResult.getAttemptCount() >= maxTryCount || processResult.getUsedTime().compareTo(maxTryTime) > 0;
        }
    }

}
