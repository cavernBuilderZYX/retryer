package builder.cavern.retry.strategy;

import builder.cavern.retry.result.ProcessResult;
import builder.cavern.retry.result.TaskResult;

import java.time.Duration;

/**
 * 重试失败停止策略
 * @author cavernBuilder
 * @since 2022/2/23
 */
@FunctionalInterface
public interface StopStrategy {

    /**
     * 当前是否应该停止重试，返回结果
     * @param taskResult 单次任务调度结果
     * @param processResult 调度（重试）任务执行中间结果
     * @return 是否立刻停止重试
     */
    boolean shouldStop(TaskResult<?> taskResult, ProcessResult<?> processResult);

    /**
     * 有永不停止策略。慎用。
     */
    StopStrategy NEVER_STOP = (taskResult, processResult) -> false;

    public static StopStrategy limit(Integer maxTryCount, Duration maxTryTime) {
        return new StopWithLimitStrategy(maxTryCount, maxTryTime);
    }

    /**
     * 带最大重试次数和最大重试时间的停止策略类
     */
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
