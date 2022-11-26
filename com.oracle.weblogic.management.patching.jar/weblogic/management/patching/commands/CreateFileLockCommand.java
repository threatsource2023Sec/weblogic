package weblogic.management.patching.commands;

public class CreateFileLockCommand extends FileLockBase {
   private static final long serialVersionUID = 6582627171587713819L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      String tmpUpdateScript = System.getenv("TMP_UPDATE_SCRIPT");
      this.fileLock.createFileLock(tmpUpdateScript);
      this.writeLockFile(this.fileLock.getPath(tmpUpdateScript).toFile());
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      String tmpUpdateScript = System.getenv("TMP_UPDATE_SCRIPT");
      this.fileLock.removeFileLock(tmpUpdateScript);
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
