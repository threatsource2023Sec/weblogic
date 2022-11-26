package weblogic.management.patching.commands;

import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.model.MigrationInfo;
import weblogic.management.workflow.command.SharedState;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class CheckSingletonsPrerequisitesCommand extends CheckPrerequisitesBaseCommand {
   private static final long serialVersionUID = 3486656163995697415L;
   final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String MIGRATION_INFO_MAP_KEY = "migrationInfo";
   @SharedState
   public transient Map migrationInfo;

   public boolean execute() throws Exception {
      boolean result = super.execute();
      if (!result) {
         return result;
      } else {
         String className = this.getClass().getName();
         String workflowId = this.getContext().getWorkflowId();
         PatchingLogger.logExecutingStep(workflowId, className, this.logTarget);

         try {
            this.checkSingletons();
            result = true;
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("CheckSingletonsPrerequisitesCommand.execute checkSingletons returned " + result);
            }

            PatchingLogger.logCompletedStep(workflowId, className, this.logTarget);
            return result;
         } catch (Exception var5) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("CheckSingletonsPrerequisitesCommand.execute checkSingletons got exception " + var5);
            }

            PatchingLogger.logFailedStepNoError(workflowId, className, this.logTarget);
            throw var5;
         }
      }
   }

   public void checkSingletons() throws CommandException {
      Iterator var1 = this.migrationInfo.entrySet().iterator();

      MigrationInfo mInfo;
      do {
         if (!var1.hasNext()) {
            return;
         }

         Map.Entry entry = (Map.Entry)var1.next();
         mInfo = (MigrationInfo)entry.getValue();
      } while(mInfo == null || mInfo.getJTAInfo() == null || !mInfo.getJTAInfo().getFailback());

      throw new CommandException(PatchingMessageTextFormatter.getInstance().invalidJTAOption());
   }
}
