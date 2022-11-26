package weblogic.management.provider.internal;

import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.provider.EditAccess;
import weblogic.management.runtime.EditSessionConfigurationRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.utils.StringUtils;

public class EditSessionConfigurationRuntimeMBeanImpl extends RuntimeMBeanDelegate implements EditSessionConfigurationRuntimeMBean {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private final EditAccess editAccess;
   private final String jndi;
   private final EditSessionServerManager editSessionServerManager;

   public EditSessionConfigurationRuntimeMBeanImpl(EditAccess editAccess, RuntimeMBean parentMBean, EditSessionServerManager editSessionServerManager) throws ManagementException {
      super(StringUtils.isEmptyString(editAccess.getEditSessionName()) ? "default" : editAccess.getEditSessionName(), parentMBean);
      this.editAccess = editAccess;
      this.jndi = editSessionServerManager.constructJndiName(editAccess.getPartitionName(), editAccess.getEditSessionName());
      this.editSessionServerManager = editSessionServerManager;
   }

   public EditSessionConfigurationRuntimeMBeanImpl(EditAccess editAccess, RuntimeMBean parentMBean, String jndi) throws ManagementException {
      super(StringUtils.isEmptyString(editAccess.getEditSessionName()) ? "default" : editAccess.getEditSessionName(), parentMBean);
      this.editAccess = editAccess;
      this.jndi = jndi;
      this.editSessionServerManager = null;
   }

   public String getCurrentEditor() {
      return this.editAccess.getEditor();
   }

   public String getCreator() {
      return this.editAccess.getCreator();
   }

   public String getEditSessionName() {
      String result = this.editAccess.getEditSessionName();
      if (result == null) {
         result = "default";
      }

      return result;
   }

   public String getPartitionName() {
      String result = this.editAccess.getPartitionName();
      if (result == null) {
         result = "DOMAIN";
      }

      return result;
   }

   public String getDescription() {
      return this.editAccess.getEditSessionDescription();
   }

   public boolean containsUnactivatedChanges() {
      if (this.editAccess.isPendingChange()) {
         return true;
      } else if (!this.editAccess.isModified()) {
         return false;
      } else {
         try {
            Iterator changes = this.editAccess.getUnactivatedChanges();
            return changes.hasNext();
         } catch (Exception var2) {
            return false;
         }
      }
   }

   public boolean isMergeNeeded() {
      return this.editAccess.isMergeNeeded();
   }

   public String getEditSessionServerJndi() {
      return this.jndi;
   }

   public EditAccess getEditAccess() {
      return this.editAccess;
   }

   public void unregister() throws ManagementException {
      try {
         if (this.editSessionServerManager != null) {
            this.editSessionServerManager.stopNamedEditSessionServer(this.editAccess);
         }
      } catch (Exception var5) {
         debugLogger.debug("Can not stop edit session server during EditSessionConfigurationRuntimeMBeanImpl uregistration.", var5);
      } finally {
         super.unregister();
      }

   }
}
