package builder.cavern.retry.command;


import builder.cavern.retry.result.FinalResult;

import java.util.concurrent.Callable;

/**
 * @author cavernBuilder
 * @date 2022/2/22
 */
public abstract class RetryCommand {

    protected Runnable failureAction;
    protected Runnable succeedAction;


    /**
     * 重试方法，得到返回值
     * @param callableTask
     * @param <T>
     * @return
     */
    public abstract <T> FinalResult<T> execute(Callable<T> callableTask);


    /**
     * 无返回值重试
     * @param runnableTask 重试任务
     * @return 结果
     */
    public FinalResult<Void> execute(Runnable runnableTask) {
        RunnableAdapter<Void> callableTask = new RunnableAdapter<>(runnableTask, null);
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
