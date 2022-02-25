package builder.cavern.retry.result;

/**
 * @author cavernBuilder
 * @date 2022/2/23
 */
public class ProcessResult<T> extends Result<T> {

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
