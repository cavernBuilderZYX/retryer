package builder.cavern.retry.strategy;

import builder.cavern.retry.result.ProcessResult;
import builder.cavern.retry.result.TaskResult;

import java.time.Duration;

/**
 * 同一原始任务两次执行的时间间隔策略
 * @author cavernBuilder
 * @since 2022/2/23
 */
@FunctionalInterface
public interface IntervalStrategy {

    /**
     * 获取下一次要执行的时间间隔
     * @param taskResult 单次任务结果
     * @param processResult 调度过程中间结果
     * @return 要执行的新生成的任务所需的时间间隔
     */
    Duration getNextInterval(TaskResult<?> taskResult, ProcessResult<?> processResult);

    IntervalStrategy DEFAULT_NO_INTERVAL = (taskResult, processResult) -> Duration.ZERO;

    static IntervalStrategy constantInterval(Duration interval) {
        return ((taskResult, processResult) -> interval);
    }

}
