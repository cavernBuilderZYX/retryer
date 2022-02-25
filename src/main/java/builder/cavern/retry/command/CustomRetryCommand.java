package builder.cavern.retry.command;

import builder.cavern.retry.result.FinalResult;

import java.util.concurrent.Callable;

/**
 * @author cavernBuilder
 * @date 2022/2/24
 */
public class CustomRetryCommand extends RetryCommand {

    protected Runnable failureAction;
    protected Runnable succeedAction;

    @Override
    public <T> FinalResult<T> execute(Callable<T> callableTask) {
        return null;
    }


}
