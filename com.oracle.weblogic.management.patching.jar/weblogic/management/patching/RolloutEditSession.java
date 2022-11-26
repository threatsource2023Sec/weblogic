package weblogic.management.patching;

import java.security.AccessController;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditException;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.edit.EditTimedOutException;
import weblogic.management.mbeanservers.edit.NotEditorException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.EditSessionConfigurationRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;

public class RolloutEditSession {
   private static String EDIT_SESSION_NAME_PREFIX = "wls-rollout-";
   private AtomicInteger seqNum = new AtomicInteger();
   MBeanServerConnection connection;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static RolloutEditSession INSTANCE = null;
   private EditSessionConfigurationManagerMBean editSessionConfigurationManagerMBean;
   private EditServiceMBean editServiceMBean;

   public static RolloutEditSession getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new RolloutEditSession();
      }

      return INSTANCE;
   }

   private RolloutEditSession() {
      DomainRuntimeServiceMBean ds = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
      this.editSessionConfigurationManagerMBean = ds.getDomainRuntime().getEditSessionConfigurationManager();
      this.connection = ManagementService.getEditMBeanServer(kernelId);
      ObjectName oName = null;

      try {
         oName = new ObjectName(EditServiceMBean.OBJECT_NAME);
      } catch (MalformedObjectNameException var4) {
      }

      if (oName != null) {
         this.editServiceMBean = (EditServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.connection, oName);
      }

   }

   public String createEditSession() throws ServiceFailureException, EditException, ManagementException, EditTimedOutException {
      int seq = this.seqNum.incrementAndGet();
      String sessionName = EDIT_SESSION_NAME_PREFIX + seq;
      ConfigurationManagerMBean configurationManager = this.editServiceMBean.getConfigurationManager();
      EditSessionConfigurationRuntimeMBean erm = this.editSessionConfigurationManagerMBean.lookupEditSessionConfiguration(sessionName);
      if (erm == null) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Creating rollout managed edit session " + sessionName);
         }

         this.editSessionConfigurationManagerMBean.createEditSessionConfiguration(sessionName, "Rollout Edit Session");
      } else if (erm.isMergeNeeded()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Resolving edit session");
         }

         configurationManager.resolve(false, 10000L);
      }

      return sessionName;
   }

   public DomainMBean startEdit() throws EditTimedOutException {
      DomainMBean domainMBean = this.editServiceMBean.getConfigurationManager().startEdit(-1, -1);
      return domainMBean;
   }

   public void cancelEdit() {
      ConfigurationManagerMBean configurationManager = this.editServiceMBean.getConfigurationManager();
      if (configurationManager.isEditor()) {
         configurationManager.cancelEdit();
      }

   }

   public void activate() throws NotEditorException {
      this.editServiceMBean.getConfigurationManager().activate(-1L);
   }

   public void endEdit(String editSessionName) throws ServiceFailureException, ManagementException {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Removing rollout managed edit session " + editSessionName);
      }

      EditSessionConfigurationRuntimeMBean editSession = this.editSessionConfigurationManagerMBean.lookupEditSessionConfiguration(editSessionName);
      if (editSession == null) {
         throw new IllegalArgumentException("Edit session '" + editSessionName + "' does not exist.");
      } else {
         this.editSessionConfigurationManagerMBean.destroyEditSessionConfiguration(editSession);
      }
   }
}
