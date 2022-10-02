package builder.cavern.retry;

import builder.cavern.retry.command.RetryCommand;
import builder.cavern.retry.command.SimpleRetryCommand;
import builder.cavern.retry.result.FinalResult;

import java.time.Duration;
import java.util.concurrent.Callable;

/**
 * 重试器主入口
 * @author cavernBuilder
 * @since 2022/2/22
 */
public class Retryer {
    //默认配置的重试策略
    private final RetryCommand defaultCommand;

    /**
     * （无通用重试策略）初始化重试器
     */
    public Retryer() {
        //默认不重试，防止因使用不当而报错
        defaultCommand = new SimpleRetryCommand(1, Duration.ZERO);
    }

    /**
     * 初始化重试器，配置通用的重试命令配置
     * @param defaultRetryCommand 重试器通用的重试命令
     */
    public Retryer(RetryCommand defaultRetryCommand) {
        defaultCommand = defaultRetryCommand;
    }


    /**
     * 运行、重试无返回值任务，使用单次运行的重试命令
     * @param task 无返回值任务
     * @param retryCommand 单次运行任务的重试命令
     * @return 运行结果，含任务结果、异常和统计信息
     */
    public FinalResult<Void> retry(Runnable task, RetryCommand retryCommand) {
        return retryCommand.execute(task);
    }

    /**
     * 运行、重试有返回值任务，使用单次运行的重试命令
     * @param task 有返回值任务
     * @param retryCommand 单次运行任务的重试命令
     * @param <T> 返回值类型
     * @return 运行结果，含任务结果、异常和统计信息
     */
    public <T> FinalResult<T> retry(Callable<T> task, RetryCommand retryCommand) {
        return retryCommand.execute(task);

    }

    /**
     * 运行、重试无返回值任务，使用重试器通用配置的重试命令
     * @param task 无返回值任务
     * @return 运行结果，含任务结果、异常和统计信息
     */
    public FinalResult<Void> retry(Runnable task) {
        return retry(task, defaultCommand);
    }

    /**
     * 运行、重试有返回值任务，使用重试器通用配置的重试命令
     * @param task 有返回值任务
     * @param <T> 返回值类型
     * @return 运行结果，含任务结果、异常和统计信息
     */
    public <T> FinalResult<T> retry(Callable<T> task) {
        return retry(task, defaultCommand);
    }
}
