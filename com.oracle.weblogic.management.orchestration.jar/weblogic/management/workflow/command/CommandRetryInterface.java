package weblogic.management.workflow.command;

public interface CommandRetryInterface extends CommandInterface {
   boolean retry() throws Exception;
}
