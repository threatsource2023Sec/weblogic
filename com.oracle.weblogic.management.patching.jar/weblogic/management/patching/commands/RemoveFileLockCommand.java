package weblogic.management.patching.commands;

public class RemoveFileLockCommand extends FileLockBase {
   private static final long serialVersionUID = -3825075238265873796L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      String tmpUpdateScript = System.getenv("TMP_UPDATE_SCRIPT");
      this.fileLock.removeFileLock(tmpUpdateScript);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
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
}
