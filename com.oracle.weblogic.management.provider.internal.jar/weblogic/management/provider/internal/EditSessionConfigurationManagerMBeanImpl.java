package weblogic.management.provider.internal;

import java.beans.FeatureDescriptor;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.internal.ConfigLogger;
import weblogic.management.internal.JMXContextHandler;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.EditAccess;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.EditSessionConfigurationRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.server.ServiceFailureException;

public class EditSessionConfigurationManagerMBeanImpl extends RuntimeMBeanDelegate implements EditSessionConfigurationManagerMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private final EditSessionConfigurationManagerService service;
   private final EditSessionServerManager editSessionServerManager;
   private final String partitionName;
   private final Map editSessionConfigs = new HashMap();
   private final boolean partitionBasedOnCIC;

   EditSessionConfigurationManagerMBeanImpl(RuntimeMBean parent, EditSessionServerManager editSessionServerManager, EditSessionConfigurationManagerService service, String partitionName) throws ManagementException {
      super(parent.getName(), parent);
      this.editSessionServerManager = editSessionServerManager;
      this.service = service;
      this.partitionName = EditSessionConfigurationManagerService.normalizePartitionName(partitionName);
      this.partitionBasedOnCIC = "DOMAIN".equals(this.partitionName);
   }

   synchronized EditSessionConfigurationRuntimeMBeanImpl addEditSessionConfiguration(EditAccess editAccess) throws ManagementException {
      if (editAccess != null) {
         String editSessionName = editAccess.getEditSessionName();
         if (editSessionName == null) {
            editSessionName = "default";
         }

         if (this.editSessionConfigs.containsKey(editSessionName)) {
            return (EditSessionConfigurationRuntimeMBeanImpl)this.editSessionConfigs.get(editSessionName);
         } else {
            EditSessionConfigurationRuntimeMBeanImpl result = new EditSessionConfigurationRuntimeMBeanImpl(editAccess, this, this.editSessionServerManager);
            this.editSessionConfigs.put(editSessionName, result);
            return result;
         }
      } else {
         return null;
      }
   }

   synchronized void removeEditSessionConfiguration(EditAccess editAccess) throws ManagementException {
      String editSessionName = editAccess.getEditSessionName();
      EditSessionConfigurationManagerService var10000 = this.service;
      editSessionName = EditSessionConfigurationManagerService.normalizeEditSessionName(editSessionName);
      EditSessionConfigurationRuntimeMBeanImpl bean = (EditSessionConfigurationRuntimeMBeanImpl)this.editSessionConfigs.remove(editSessionName);
      if (bean != null) {
         bean.unregister();
      }

   }

   public synchronized EditSessionConfigurationRuntimeMBeanImpl[] getEditSessionConfigurations() {
      if (this.partitionBasedOnCIC) {
         String pn = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
         EditSessionConfigurationManagerService var10000 = this.service;
         pn = EditSessionConfigurationManagerService.normalizePartitionName(pn);
         if (!this.partitionName.equals(pn)) {
            return this.service.redirect(pn).getEditSessionConfigurations();
         }
      }

      List result = new ArrayList(this.editSessionConfigs.size());
      result.addAll(this.editSessionConfigs.values());
      return (EditSessionConfigurationRuntimeMBeanImpl[])result.toArray(new EditSessionConfigurationRuntimeMBeanImpl[result.size()]);
   }

   public synchronized EditSessionConfigurationRuntimeMBeanImpl lookupEditSessionConfiguration(String name) {
      EditSessionConfigurationManagerService var10000;
      if (this.partitionBasedOnCIC) {
         String pn = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
         var10000 = this.service;
         pn = EditSessionConfigurationManagerService.normalizePartitionName(pn);
         if (!this.partitionName.equals(pn)) {
            return this.service.redirect(pn).lookupEditSessionConfiguration(name);
         }
      }

      var10000 = this.service;
      name = EditSessionConfigurationManagerService.normalizeEditSessionName(name);
      EditSessionConfigurationRuntimeMBeanImpl result = (EditSessionConfigurationRuntimeMBeanImpl)this.editSessionConfigs.get(name);
      if (result == null) {
         EditAccess editAccess = this.service.lookupEditSession(name);
         if (editAccess != null) {
            try {
               return this.addEditSessionConfiguration(editAccess);
            } catch (ManagementException var5) {
               debugLogger.debug("Can not register EditSessionConfigurationRuntimeMBeanImpl on the fly.", var5);
            }
         }
      }

      return result;
   }

   public synchronized EditSessionConfigurationRuntimeMBeanImpl createEditSessionConfiguration(String name, String description) throws IllegalArgumentException, ServiceFailureException, ManagementException {
      if (this.partitionBasedOnCIC) {
         String pn = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
         EditSessionConfigurationManagerService var10000 = this.service;
         pn = EditSessionConfigurationManagerService.normalizePartitionName(pn);
         if (!this.partitionName.equals(pn)) {
            return this.service.redirect(pn).createEditSessionConfiguration(name, description);
         }
      }

      EditAccess ea = this.service.createEditSession(this.partitionName, name, description);
      EditSessionConfigurationRuntimeMBeanImpl result = (EditSessionConfigurationRuntimeMBeanImpl)this.editSessionConfigs.get(name);
      return result == null ? this.addEditSessionConfiguration(ea) : result;
   }

   public synchronized void destroyEditSessionConfiguration(EditSessionConfigurationRuntimeMBean editSession) throws ServiceFailureException, ManagementException {
      this.destroyEditSessionConfiguration(editSession, false);
   }

   public synchronized void forceDestroyEditSessionConfiguration(EditSessionConfigurationRuntimeMBean editSession) throws ServiceFailureException, ManagementException {
      this.destroyEditSessionConfiguration(editSession, true);
   }

   private synchronized void destroyEditSessionConfiguration(EditSessionConfigurationRuntimeMBean editSession, boolean force) throws ServiceFailureException, ManagementException {
      String pn;
      EditSessionConfigurationManagerService var10000;
      if (this.partitionBasedOnCIC) {
         pn = editSession.getPartitionName();
         var10000 = this.service;
         pn = EditSessionConfigurationManagerService.normalizePartitionName(pn);
         if (!this.partitionName.equals(pn)) {
            this.service.redirect(pn).destroyEditSessionConfiguration(editSession);
            return;
         }
      }

      pn = editSession.getPartitionName();
      var10000 = this.service;
      pn = EditSessionConfigurationManagerService.normalizePartitionName(pn);
      if (pn.equals(this.partitionName) && editSession instanceof EditSessionConfigurationRuntimeMBeanImpl) {
         boolean isAdmin = false;

         try {
            SecurityHelper.checkForAdminRole(SecurityHelper.getResourceContextHandler((ObjectName)null, new JMXContextHandler((ObjectName)null), (FeatureDescriptor)null, (FeatureDescriptor)null, (String)null));
            isAdmin = true;
         } catch (Exception var13) {
         }

         EditSessionConfigurationRuntimeMBeanImpl bean = (EditSessionConfigurationRuntimeMBeanImpl)editSession;
         EditAccess ea = bean.getEditAccess();
         String creatorName = ea.getCreator();
         AuthenticatedSubject caller = SecurityManager.getCurrentSubject(kernelId);
         String callerName = SubjectUtils.getUsername(caller);
         boolean isSessionOwner = callerName.equals(creatorName);
         boolean allowDestroy = false;
         if (force && isAdmin) {
            allowDestroy = true;
         } else {
            if (!isSessionOwner) {
               throw new IllegalArgumentException(isAdmin ? ConfigLogger.getCannotDeleteEditSessionOfAnotherUserAdmin(editSession.getName(), ea.getCreator()) : ConfigLogger.getCannotDeleteEditSessionOfAnotherUser(editSession.getName(), ea.getCreator()));
            }

            if (ea.getEditor() != null && !ea.isEditor()) {
               String msg = isAdmin ? ConfigLogger.getCanNotRemoveLockedEditSessionAdmin(editSession.getName(), ea.getEditor()) : ConfigLogger.getCanNotRemoveLockedEditSession(editSession.getName(), ea.getEditor());
               throw new IllegalArgumentException(msg);
            }

            if (editSession.containsUnactivatedChanges() && !force) {
               throw new IllegalArgumentException(ConfigLogger.getCanNotRemoveEditSessionWithChanges(editSession.getName()));
            }

            allowDestroy = true;
         }

         if (allowDestroy) {
            this.service.destroyEditSession(bean.getEditAccess());
         }

      } else {
         throw new IllegalArgumentException(ConfigLogger.getCanNotRemoveEditSessionOfDifferentPartition());
      }
   }
}
