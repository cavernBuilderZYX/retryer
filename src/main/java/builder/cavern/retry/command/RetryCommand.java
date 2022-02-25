package builder.cavern.retry.command;


import builder.cavern.retry.result.FinalResult;
import builder.cavern.retry.result.ProcessResult;

import java.util.concurrent.Callable;

/**
 * @author cavernBuilder
 * @date 2022/2/22
 */
public abstract class RetryCommand<T> {

    public RetryCommand() {
    }

    /**
     * 重试方法，得到返回值
     * @param callableTask
     * @return
     */
    public abstract FinalResult<T> execute(Callable<T> callableTask);


    /**
     * 无返回值重试
     * @param runnableTask 重试任务
     * @return 结果
     */
    public FinalResult<T> execute(Runnable runnableTask) {
        RunnableAdapter<T> callableTask = new RunnableAdapter<>(runnableTask, null);
        return execute(callableTask);
    }


    protected static final class RunnableAdapter<T> implements Callable<T> {
        private final Runnable task;
        private final T result;
        RunnableAdapter(Runnable task, T result) {
            this.task = task;
            this.result = result;
        }

        @Override
        public T call() {
            task.run();
            return result;
        }

        @Override
        public String toString() {
            return super.toString() + "[Wrapped task = " + task + "]";
        }
    }
}
