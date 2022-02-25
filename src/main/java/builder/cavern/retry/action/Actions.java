package builder.cavern.retry.action;

/**
 * 节点动作
 * @author cavernBuilder
 * @date 2022/2/25
 */
public class Actions {

    private Runnable succeededAction;

    private Runnable finalFailureAction;

    private TaskFailureAction taskFailureAction;

    private Actions(ActionsBuilder actionsBuilder) {
        this.succeededAction = actionsBuilder.succeededAction;
        this.finalFailureAction = actionsBuilder.finalFailureAction;
        this.taskFailureAction = actionsBuilder.taskFailureAction;
    }

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
