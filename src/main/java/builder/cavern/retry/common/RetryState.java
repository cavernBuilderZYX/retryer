package builder.cavern.retry.common;

/**
 * @author cavernBuilder
 * @date 2022/2/23
 */
public enum RetryState {
    /**
     * 重试任务状态
     */
    INITIAL,
    FIRST_RUNNING,
    WAITING,
    RETRY_RUNNING,
    SUCCEEDED,
    FAILED;
}
