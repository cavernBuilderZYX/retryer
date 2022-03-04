package builder.cavern.retry.result;

/**
 * 单次任务运行结果
 * @author cavernBuilder
 * @since 2022/2/23
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
