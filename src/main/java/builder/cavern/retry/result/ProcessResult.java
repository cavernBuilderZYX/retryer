package builder.cavern.retry.result;

/**
 * 任务调度情况
 * @author cavernBuilder
 * @since 2022/2/23
 */
public class ProcessResult<T> extends Result<T> {

    /**
     * 到当前为止进行的尝试次数
     */
    protected int attemptCount;

    @Override
    public int getAttemptCount() {
        return attemptCount;
    }

    public void incrementAttemptCount() {
        ++attemptCount;
    }

    public ProcessResult() {
        this.attemptCount = 0;
    }

    public void changedByTaskResult(TaskResult<T> taskResult) {
        this.result = taskResult.getResult();
        this.exception = taskResult.getException();
        this.endTime = taskResult.getEndTime();
    }
}
