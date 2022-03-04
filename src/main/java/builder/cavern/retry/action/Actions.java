package builder.cavern.retry.action;

/**
 * 重试过程中的节点动作
 * @author cavernBuilder
 * @since 2022/2/25
 */
public class Actions {

    /**
     * 任务执行成功后的回调动作
     */
    private Runnable succeededAction;

    /**
     * 运行、重试的最终失败后的回调动作
     */
    private Runnable finalFailureAction;

    /**
     * 单次任务失败后的回调操作
     */
    private TaskFailureAction taskFailureAction;

    private Actions(ActionsBuilder actionsBuilder) {
        this.succeededAction = actionsBuilder.succeededAction;
        this.finalFailureAction = actionsBuilder.finalFailureAction;
        this.taskFailureAction = actionsBuilder.taskFailureAction;
    }

    public static final Actions NONE = Actions.create().build();

    public static ActionsBuilder create() {
        return new ActionsBuilder();
    }

    public Runnable getSucceededAction() {
        return succeededAction;
    }

    public Runnable getFinalFailureAction() {
        return finalFailureAction;
    }

    public TaskFailureAction getTaskFailureAction() {
        return taskFailureAction;
    }

    public static class ActionsBuilder {

        Runnable succeededAction;

        Runnable finalFailureAction;

        TaskFailureAction taskFailureAction;

        private ActionsBuilder() {

        }

        public Actions build() {
            return new Actions(this);
        }

        public ActionsBuilder succeededAction(Runnable succeededAction) {
            this.succeededAction = succeededAction;
            return this;
        }

        public ActionsBuilder finalFailureAction(Runnable finalFailureAction) {
            this.finalFailureAction = finalFailureAction;
            return this;
        }

        public ActionsBuilder taskFailureAction(TaskFailureAction taskFailureAction) {
            this.taskFailureAction = taskFailureAction;
            return this;
        }
    }


}
