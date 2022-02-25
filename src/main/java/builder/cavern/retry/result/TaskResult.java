package builder.cavern.retry.result;

import java.time.Duration;

/**
 * @author cavernBuilder
 * @date 2022/2/23
 */
public class TaskResult<T> extends Result<T> {

    @Override
    public int getAttemptCount() {
        return 1;
    }

    public TaskResult() {};

    public TaskResult(T result, Exception exception) {
        super(result, exception);
    }
}
