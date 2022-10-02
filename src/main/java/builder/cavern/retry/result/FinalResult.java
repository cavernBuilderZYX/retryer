package builder.cavern.retry.result;

/**
 * 最终任务执行结果
 * @author cavernBuilder
 * @since 2022/2/25
 */
public class FinalResult<T> extends ProcessResult<T>{
    /**
     * 运行或重试是否最终成功了
     */
    public boolean successful;

    public FinalResult(ProcessResult<T> processResult, boolean successful) {
        this.attemptCount = processResult.attemptCount;
        this.startTime = processResult.startTime;
        this.endTime = processResult.endTime;
        this.result = processResult.result;
        this.exception = processResult.exception;
        this.successful = successful;
    }


    public boolean isSuccessful() {
        return successful;
    }
}
