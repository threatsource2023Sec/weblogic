package weblogic.management.provider.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.EditSessionConfigurationManager;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ConfigurationValidator;
import weblogic.management.internal.ConfigLogger;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditSessionLifecycleListener;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.StringUtils;

@Service
@Named
@RunLevel(10)
public class EditSessionConfigurationManagerService extends AbstractServerService implements EditSessionConfigurationManager {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   @Inject
   private EditSessionServerManager sessionManager;
   @Inject
   private RuntimeAccess runtimeAccess;
   private volatile EditSessionConfigurationManagerMBeanImpl editSessionManager;
   private final Map editSessionManagers = new HashMap();

   public void start() throws ServiceFailureException {
      if (this.runtimeAccess.isAdminServer()) {
         try {
            EditAccessImpl.initGlobalEditAccess();
            EditAccessImpl.initNamedEditAccess();
            DomainRuntimeMBean domainRuntimeMBean = this.getDomainRuntimeMBean();
            this.editSessionManager = this.createEditSessionConfigurationManagerMBean(domainRuntimeMBean, (String)null, kernelId);
            domainRuntimeMBean.setEditSessionConfigurationManager(this.editSessionManager);
         } catch (Exception var7) {
            throw new ServiceFailureException(var7);
         }

         EditAccess global = ManagementServiceRestricted.getEditAccess(kernelId);
         Map map = ManagementServiceRestricted.getNamedEditAccess(kernelId);
         if (map != null && this.editSessionManager != null) {
            try {
               Iterator var3 = map.values().iterator();

               while(var3.hasNext()) {
                  Map partitionSessions = (Map)var3.next();
                  Iterator var5 = partitionSessions.values().iterator();

                  while(var5.hasNext()) {
                     EditAccess named = (EditAccess)var5.next();
                     if (named != global) {
                        this.sessionManager.startNamedEditSessionServer(named);
                     }
                  }
               }
            } catch (Exception var8) {
               throw new ServiceFailureException(var8);
            }
         }

      }
   }

   public void stop() throws ServiceFailureException {
      Map map = ManagementServiceRestricted.getNamedEditAccess(kernelId);
      if (map != null) {
         Iterator var2 = map.values().iterator();

         while(var2.hasNext()) {
            Map partitionSessions = (Map)var2.next();
            Iterator var4 = partitionSessions.values().iterator();

            while(var4.hasNext()) {
               EditAccess named = (EditAccess)var4.next();
               named.shutdown();
            }
         }
      }

   }

   public EditSessionConfigurationManagerMBeanImpl createEditSessionConfigurationManagerMBean(RuntimeMBean parent, String partitionName, AuthenticatedSubject sub) throws ManagementException {
      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         return null;
      } else {
         SecurityHelper.assertIfNotKernel(sub);
         if (StringUtils.isEmptyString(partitionName)) {
            partitionName = "DOMAIN";
         }

         synchronized(this.editSessionManagers) {
            EditSessionConfigurationManagerMBeanImpl result = new EditSessionConfigurationManagerMBeanImpl(parent, this.sessionManager, this, partitionName);
            this.editSessionManagers.put(partitionName, result);
            Map eaMap = (Map)ManagementServiceRestricted.getNamedEditAccess(sub).get(partitionName);
            if (eaMap == null && !"DOMAIN".equals(partitionName)) {
               try {
                  EditAccess ea = ManagementServiceRestricted.createDefaultEditSession(partitionName, kernelId);
                  this.sessionManager.startNamedEditSessionServer(ea);
                  eaMap = (Map)ManagementServiceRestricted.getNamedEditAccess(sub).get(partitionName);
               } catch (ServiceFailureException var10) {
                  throw new RuntimeException(var10);
               }
            }

            Iterator var12 = eaMap.values().iterator();

            while(var12.hasNext()) {
               EditAccess editAccess = (EditAccess)var12.next();
               result.addEditSessionConfiguration(editAccess);
            }

            return result;
         }
      }
   }

   public void destroyAllPartitionEditSessions(String partitionName, AuthenticatedSubject sub) {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         SecurityHelper.assertIfNotKernel(sub);
         if (partitionName != null && !"DOMAIN".equals(partitionName)) {
            synchronized(this.editSessionManagers) {
               Map eaMap = (Map)ManagementServiceRestricted.getNamedEditAccess(sub).get(partitionName);
               if (eaMap != null) {
                  Collection eaCopy = new ArrayList(eaMap.values());
                  Iterator var6 = eaCopy.iterator();

                  while(var6.hasNext()) {
                     EditAccess editAccess = (EditAccess)var6.next();
                     String name = normalizeEditSessionName(editAccess.getEditSessionName());
                     ManagementServiceRestricted.destroyEditSession(name, partitionName, sub);
                  }
               }

               this.editSessionManagers.remove(partitionName);
            }
         } else {
            throw new IllegalArgumentException(ConfigLogger.getCanNotDestroyDomainPartitionES());
         }
      }
   }

   EditSessionConfigurationManagerMBeanImpl redirect(String partitionName) throws IllegalArgumentException {
      EditSessionConfigurationManagerMBeanImpl result = (EditSessionConfigurationManagerMBeanImpl)this.editSessionManagers.get(partitionName);
      if (result == null) {
         throw new IllegalArgumentException("EditSessionConfigurationManagerMBean is not initialised for partition " + partitionName);
      } else {
         return result;
      }
   }

   private DomainRuntimeMBean getDomainRuntimeMBean() {
      return ManagementService.getDomainAccess(kernelId).getDomainRuntime();
   }

   public EditAccess createEditSession(String name, String description) throws ServiceFailureException, ManagementException {
      String partitionName = CallerPartitionContext.getPartitionName(kernelId);
      if (partitionName == null) {
         partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      }

      return this.createEditSession(partitionName, name, description);
   }

   public EditAccess createEditSession(String partitionName, String name, String description) throws ServiceFailureException, ManagementException {
      try {
         ConfigurationValidator.validateStrictName(name);
      } catch (IllegalArgumentException var10) {
         throw new IllegalArgumentException(ConfigLogger.getInvalidEditSessionName(name), var10);
      }

      partitionName = normalizePartitionName(partitionName);
      if (ManagementServiceRestricted.getEditSession(partitionName, name) != null) {
         throw new IllegalArgumentException(ConfigLogger.getEditSessionNameExist(name));
      } else {
         synchronized(this.editSessionManagers) {
            EditAccess editAccess = ManagementServiceRestricted.createEditSession(partitionName, name, description);

            try {
               this.sessionManager.startNamedEditSessionServer(editAccess);
            } catch (ServiceFailureException var8) {
               throw new RuntimeException(var8);
            }

            EditSessionConfigurationManagerMBeanImpl bean = (EditSessionConfigurationManagerMBeanImpl)this.editSessionManagers.get(partitionName);
            if (bean != null) {
               bean.addEditSessionConfiguration(editAccess);
            }

            return editAccess;
         }
      }
   }

   public List getEditSessions() {
      List sessions = new ArrayList();
      String[] var2 = ManagementServiceRestricted.getEditSessions();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String sessionName = var2[var4];
         sessions.add(ManagementServiceRestricted.getEditSession(sessionName));
      }

      return sessions;
   }

   public List getEditSessions(String partitionName) {
      partitionName = normalizePartitionName(partitionName);
      Map map = (Map)ManagementServiceRestricted.getNamedEditAccess(kernelId).get(partitionName);
      if (map == null) {
         return Collections.EMPTY_LIST;
      } else {
         List result = new ArrayList(map.size());
         result.addAll(map.values());
         return result;
      }
   }

   public EditAccess lookupEditSession(String name) {
      String partitionName = CallerPartitionContext.getPartitionName(kernelId);
      if (partitionName == null) {
         partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      }

      return this.lookupEditSession(partitionName, name);
   }

   public EditAccess lookupEditSession(String partitionName, String name) {
      partitionName = normalizePartitionName(partitionName);
      return ManagementServiceRestricted.getEditSession(partitionName, name);
   }

   public void destroyEditSession(EditAccess editAccess) throws ManagementException {
      if (editAccess.getEditSessionName() != null && !"default".equals(editAccess.getEditSessionName())) {
         String partitionName = editAccess.getPartitionName();
         partitionName = normalizePartitionName(partitionName);
         EditSessionConfigurationManagerMBeanImpl mbean = (EditSessionConfigurationManagerMBeanImpl)this.editSessionManagers.get(partitionName);
         ManagementServiceRestricted.destroyEditSession(editAccess);
         if (mbean != null) {
            mbean.removeEditSessionConfiguration(editAccess);
         }

      } else {
         throw new IllegalArgumentException(ConfigLogger.getCanNOtDestroyDefaultEditSession());
      }
   }

   static String normalizePartitionName(String partitionName) {
      return StringUtils.isEmptyString(partitionName) ? "DOMAIN" : partitionName;
   }

   static String normalizeEditSessionName(String editSessionName) {
      return StringUtils.isEmptyString(editSessionName) ? "default" : editSessionName;
   }

   public void registerSessionLifecycleListener(EditSessionLifecycleListener sessionLifecycleListener) {
      ManagementServiceRestricted.addEditSessionLifecycleListener(sessionLifecycleListener);
   }

   public void unregisterSessionLifecycleListener(EditSessionLifecycleListener sessionLifecycleListener) {
      ManagementServiceRestricted.removeEditSessionLifecycleListener(sessionLifecycleListener);
   }
}
