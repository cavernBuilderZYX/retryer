package builder.cavern.retry.strategy;

import builder.cavern.retry.result.TaskResult;

/**
 * 任务执行成功判定
 * @author cavernBuilder
 * @since 2022/2/24
 */
@FunctionalInterface
public interface SuccessStrategy {

    /**
     * 单次任务是否运行成功
     * @param taskResult 单次任务运行结果
     * @return 是否运行成功
     */
    boolean succeed(TaskResult<?> taskResult);

    SuccessStrategy DEFAULT_NO_EXCEPTION = taskResult -> taskResult.getException() == null;
}
