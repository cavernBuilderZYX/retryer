package builder.cavern.retry.task;

import builder.cavern.retry.action.Actions;
import builder.cavern.retry.common.RetryState;
import builder.cavern.retry.common.TaskState;
import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.result.ProcessResult;
import builder.cavern.retry.result.TaskResult;
import builder.cavern.retry.strategy.RetryStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * @author cavernBuilder
 * @since 2022/2/25
 */
public class CustomTaskRunner<T> extends TaskRunner<T>{

    /**重试策略*/
    protected RetryStrategy retryStrategy;

    /**过程节点重试动作*/
    protected Actions actions;

    public CustomTaskRunner(Callable<T> originalTask, RetryStrategy retryStrategy, Actions actions) {
        super(originalTask);
        this.retryStrategy = retryStrategy;
        this.actions = actions;
    }

    @Override
    public FinalResult<T> scheduleTask() {
        this.state = RetryState.FIRST_RUNNING;
        ProcessResult<T> processResult = new ProcessResult<>();
        processResult.setStartTime(LocalDateTime.now());
        this.processResult = processResult;
        do {
            processResult.incrementAttemptCount();
            //单次尝试运行任务
            TaskResult<T> taskResult = currentTask.runOneTime();
            processResult.changedByTaskResult(taskResult);
            //Once task succeeds, or runner fails and meets the conditions to stop, result is returned
            //一旦任务成功， 则直接停止重试，返回结果
            boolean taskSucceeded = retryStrategy.getSuccessStrategy().succeed(taskResult);
            if (taskSucceeded) {
                currentTask.state = TaskState.SUCCEEDED;
                this.state = RetryState.SUCCEEDED;
                if (actions.getSucceededAction() != null) {
                    actions.getSucceededAction().run();
                }
                break;
            }
            //单次任务失败
            currentTask.state = TaskState.FAILED;
            if (actions.getTaskFailureAction() != null) {
                actions.getTaskFailureAction().onTaskFailure(taskResult, processResult);
            }

            boolean processFailed = retryStrategy.getStopStrategy().shouldStop(taskResult, processResult);
            //任务运行失败，评估不能继续重试，则整个重试过程结束
            if (processFailed) {
                this.state = RetryState.FAILED;
                if (actions.getFinalFailureAction() != null) {
                    actions.getFinalFailureAction().run();
                }
                break;
            }
            //进入下一个周期，首先要等待指定时长
            Duration nextInterval = retryStrategy.getIntervalStrategy().getNextInterval(taskResult, processResult);
            if (nextInterval != null && !nextInterval.isZero()) {
                try {
                    this.state = RetryState.WAITING;
                    Thread.sleep(nextInterval.toMillis());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.state = RetryState.RETRY_RUNNING;
            this.currentTask = new RetryableTask<>(currentTask.originalTask);
        } while (true);
        //复制成最终结果
        return new FinalResult<>(processResult, this.state.equals(RetryState.SUCCEEDED));
    }
}
