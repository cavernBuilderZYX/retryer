package builder.cavern.retry.action;

import builder.cavern.retry.result.ProcessResult;
import builder.cavern.retry.result.TaskResult;

/**
 * @author cavernBuilder
 * @date 2022/2/25
 */
@FunctionalInterface
public interface TaskFailureAction {

     void onTaskFailure(TaskResult<?> taskResult, ProcessResult<?> processResult);
}
