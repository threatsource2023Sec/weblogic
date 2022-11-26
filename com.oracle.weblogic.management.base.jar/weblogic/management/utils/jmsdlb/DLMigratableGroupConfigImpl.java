package weblogic.management.utils.jmsdlb;

import weblogic.cluster.migration.MigratableGroupConfig;
import weblogic.management.configuration.ServerMBean;

public class DLMigratableGroupConfigImpl extends DLMigratableGroup implements MigratableGroupConfig {
   DLMigratableGroupConfigImpl(DLContext context, String idname, DLStoreConfig config, String targetServer) {
      super(context, idname, config, targetServer);
   }

   public boolean isManualService() {
      return false;
   }

   public boolean isCritical() {
      return false;
   }

   public int getNumberOfRestartAttempts() {
      return 0;
   }

   public int getSecondsBetweenRestarts() {
      return 0;
   }

   public boolean getRestartOnFailure() {
      return false;
   }

   public ServerMBean getUserPreferredServer() {
      return null;
   }

   public String getPreScript() {
      return null;
   }

   public String getPostScript() {
      return null;
   }

   public boolean isPostScriptFailureFatal() {
      return false;
   }

   public boolean isNonLocalPostAllowed() {
      return true;
   }

   public String toString() {
      return super.toString() + "[name=" + this.getName() + " ," + this.getTargetServer() + "]";
   }
}
