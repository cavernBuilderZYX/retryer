package builder.cavern.retry;

import builder.cavern.retry.command.RetryCommand;

import java.util.concurrent.Callable;

/**
 * 主入口
 * @author cavernBuilder
 * @date 2022/2/22
 */
public class Retryer {

    public static void retry(Runnable task, RetryCommand retryOption) {

    }

    public static <T> T retry(Callable<T> task, RetryCommand retryOption) {
        try {
            return task.call();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
