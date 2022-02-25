package builder.cavern.retry.task;

import builder.cavern.retry.common.TaskState;
import builder.cavern.retry.result.TaskResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * @author cavernBuilder
 * @date 2022/2/22
 */
public class RetryableTask<T> {
    Callable<T> originalTask;
    TaskState state;
    TaskResult<T> taskResult;

    public RetryableTask(Callable<T> originalTask) {
        this.originalTask = originalTask;
        this.state = TaskState.INITIAL;
    }


    public TaskResult<T> runOneTime() {
        TaskResult<T> taskResult = new TaskResult<>();
        taskResult.setStartTime(LocalDateTime.now());
        try {
            this.state = TaskState.RUNNING;
            T result = originalTask.call();
            taskResult.setResult(result);
        } catch (Exception e) {
            taskResult.setException(e);
        } finally {
            taskResult.setEndTime(LocalDateTime.now());
            this.state = TaskState.FINISHED;
            this.taskResult = taskResult;
        }
        return taskResult;
    }

    public Callable<T> getOriginalTask() {
        return originalTask;
    }

    public TaskState getState() {
        return state;
    }

    public TaskResult<T> getTaskResult() {
        return taskResult;
    }

    public void setOriginalTask(Callable<T> originalTask) {
        this.originalTask = originalTask;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public void setTaskResult(TaskResult<T> taskResult) {
        this.taskResult = taskResult;
    }
}
