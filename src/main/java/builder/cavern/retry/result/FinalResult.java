package builder.cavern.retry.result;

/**
 * @author cavernBuilder
 * @date 2022/2/25
 */
public class FinalResult<T> extends ProcessResult<T>{
    public FinalResult(ProcessResult<T> processResult) {
        this.attemptCount = processResult.attemptCount;
        this.startTime = processResult.startTime;
        this.endTime = processResult.endTime;
        this.result = processResult.result;
        this.exception = processResult.exception;
    }
}
