package builder.cavern.retry.action;

import builder.cavern.retry.result.ProcessResult;
import builder.cavern.retry.result.TaskResult;

/**
 * 重试过程中单次任务执行失败后的回调操作
 * @author cavernBuilder
 * @since 2022/2/25
 */
@FunctionalInterface
public interface TaskFailureAction {

     /**
      * 重试过程中单次任务执行失败后的回调操作
      * @param taskResult 单次运行结果（失败状态)
      * @param processResult 重试调度过程的中间结果
      */
     void onTaskFailure(TaskResult<?> taskResult, ProcessResult<?> processResult);
}
