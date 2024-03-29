package builder.cavern.retry.common;

/**
 * @author cavernBuilder
 * @since 2022/2/23
 */
public enum TaskState {
    /**
     * 单次任务状态
     */
    INITIAL,
    RUNNING,
    FINISHED,
    SUCCEEDED,
    FAILED;
}
