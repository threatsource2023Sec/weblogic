package weblogic.management.patching.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;

public abstract class FileLockBase extends AbstractCommand implements CommandRevertInterface {
   private static final long serialVersionUID = -3785468878257705562L;
   @SharedState
   public transient String machineName;
   @SharedState
   public boolean isUpdateJavaHome;
   public transient FileLock fileLock;

   public void initialize(WorkflowContext workFlowContext) {
      this.fileLock = new FileLock();
      super.initialize(workFlowContext);
   }

   protected void writeLockFile(File file) throws Exception {
      String workflowId = this.getContext().getWorkflowId();
      Properties p = new Properties();
      if (this.isUpdateJavaHome) {
         p.put("ResetJava", "true");
      } else {
         p.put("ResetJava", "false");
      }

      p.put("WFID", workflowId);
      FileOutputStream fos = null;

      try {
         fos = new FileOutputStream(file);
         p.store(fos, "ZDT Lock File for collocated NodeManager w/o Admin server");
         fos.flush();
      } finally {
         if (fos != null) {
            fos.close();
         }

      }

   }
}
