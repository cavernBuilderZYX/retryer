package builder.cavern.retry.task;

import builder.cavern.retry.common.RetryState;
import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.result.ProcessResult;

import java.util.concurrent.Callable;

/**
 * 同一个原始任务多次运行、重试过程的调度和管理者
 * @author cavernBuilder
 * @since 2022/2/25
 */
public abstract class TaskRunner<T> {

    /** 调度过程中间结果 */
    protected ProcessResult<T> processResult;

    /** 当前调度运行的任务 */
    protected RetryableTask<T> currentTask;

    /** 重试状态*/
    protected RetryState state;




    public TaskRunner(Callable<T> originalTask) {
        this.currentTask = new RetryableTask<>(originalTask);
        this.state = RetryState.INITIAL;
    }

    public abstract FinalResult<T> scheduleTask();

    public void setFinalResult(ProcessResult<T> processResult) {
        this.processResult = processResult;
    }

    public void setCurrentTask(RetryableTask<T> currentTask) {
        this.currentTask = currentTask;
    }

    public void setState(RetryState state) {
        this.state = state;
    }

    public ProcessResult<T> getFinalResult() {
        return processResult;
    }

    public RetryableTask<T> getCurrentTask() {
        return currentTask;
    }

    public RetryState getState() {
        return state;
    }

}
