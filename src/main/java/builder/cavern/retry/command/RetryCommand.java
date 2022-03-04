package builder.cavern.retry.command;


import builder.cavern.retry.result.FinalResult;

import java.util.concurrent.Callable;

/**
 * 重试命令抽象类
 * @author cavernBuilder
 * @since 2022/2/22
 */
public abstract class RetryCommand {

    public RetryCommand() {
    }

    /**
     * 运行和重试任务方法，得到返回值
     * @param callableTask （最初的）重试任务
     * @return 运行或重试任务的返回值
     */
    public abstract <T> FinalResult<T> execute(Callable<T> callableTask);


    /**
     * 无返回值重试
     * @param runnableTask 重试任务
     * @return 结果
     */
    public  FinalResult<Void> execute(Runnable runnableTask) {
        RunnableAdapter<Void> callableTask = new RunnableAdapter<>(runnableTask, null);
        return execute(callableTask);
    }


    /**
     * 把Runnable包装成callable的适配器，从JDK中复制而来
     * @param <T> Callable的返回类型
     */
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
