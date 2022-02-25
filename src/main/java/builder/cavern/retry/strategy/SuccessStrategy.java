package builder.cavern.retry.strategy;

import builder.cavern.retry.result.TaskResult;

import java.util.function.Predicate;

/**
 * @author cavernBuilder
 * @date 2022/2/24
 */
@FunctionalInterface
public interface SuccessStrategy {

    boolean succeed(TaskResult<?> taskResult);

    SuccessStrategy DEFAULT = taskResult -> taskResult.getException() == null;
}
