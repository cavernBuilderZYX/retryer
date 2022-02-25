package builder.cavern.retry;

import builder.cavern.retry.command.RetryCommand;
import builder.cavern.retry.result.FinalResult;

import java.util.concurrent.Callable;

/**
 * 主入口
 * @author cavernBuilder
 * @date 2022/2/22
 */
public class Retryer {

    public <T> FinalResult<T> retry(Runnable task, RetryCommand<T> retryCommand) {
        try {
            return retryCommand.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> FinalResult<T> retry(Callable<T> task, RetryCommand<T> retryCommand) {
        try {
            return retryCommand.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
