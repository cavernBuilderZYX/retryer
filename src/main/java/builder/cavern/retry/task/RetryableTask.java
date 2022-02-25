package builder.cavern.retry.task;

import builder.cavern.retry.common.TaskState;
import builder.cavern.retry.result.TaskResult;

import java.time.Duration;
import java.util.concurrent.Callable;

/**
 * @author cavernBuilder
 * @date 2022/2/22
 */
public class RetryableTask<T> {
    protected Callable<T> originalTask;
    protected TaskState state;
    protected TaskResult<T> taskResult;

    public RetryableTask(Callable<T> originalTask) {
        this.originalTask = originalTask;
        this.state = TaskState.INITIAL;
    }


    public TaskResult<T> runOneTime() {
        TaskResult<T> taskResult = new TaskResult<>();
        long startTime = System.currentTimeMillis();
        try {
            this.state = TaskState.RUNNING;
            T result = originalTask.call();
            taskResult.setResult(result);
        } catch (Exception e) {
            taskResult.setException(e);
        } finally {
            taskResult.setUsedTime(Duration.ofMillis(System.currentTimeMillis() - startTime));
            this.state = TaskState.FINISHED;
            this.taskResult = taskResult;
        }
        return taskResult;
    }




}
