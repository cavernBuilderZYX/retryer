package builder.cavern.retry.result;

import java.time.Duration;

/**
 * @author cavernBuilder
 * @date 2022/2/25
 */
public interface Result<T> {

    Duration getElapseTime();

    T getResult();

    Exception getException();

    int getAttemptCount();

}
