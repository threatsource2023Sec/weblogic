package weblogic.messaging.path;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.naming.NamingException;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.UndeploymentException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.utils.GenericAdminHandler;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.ServiceFailureException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.xa.PersistentStoreXA;

public class PathServiceAdmin implements GenericAdminHandler {
   private PathServiceMap pathMap;
   private boolean running;
   private PathServiceMBean activatedMBean;
   private Object originalScopeMBean;
   private PathHelper pathHelper;
   private int instanceNumber = -1;
   private String[] allJndiNames;
   private PathServiceRuntimeDelegate psRuntimeDelegate;
   private HashMap pathServiceMaps = new HashMap();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public PathServiceMap getPathMap() {
      return this.pathMap;
   }

   public boolean isRunning() {
      return this.running;
   }

   public void prepare(GenericManagedDeployment deployment) throws DeploymentException {
   }

   public synchronized void activate(GenericManagedDeployment deployment) throws DeploymentException {
      PathService ps;
      JMSService jmsService;
      try {
         jmsService = JMSService.getStartedService();
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("Actiating PathService: " + deployment.getName() + " for partition: " + JMSService.getSafePartitionNameFromThread());
         }

         ps = PathService.getService(jmsService.getComponentInvocationContext());
      } catch (ServiceFailureException | ManagementException | JMSException var30) {
         throw new DeploymentException(var30);
      }

      DeploymentMBean bean = deployment.getMBean();
      if (bean instanceof PathServiceMBean) {
         this.activatedMBean = (PathServiceMBean)bean;

         try {
            this.originalScopeMBean = PathHelper.getOriginalScopeMBean(this.activatedMBean);
            String pathServiceJndiName = PathHelper.pathServiceJndiNameFromScopeMBean(this.originalScopeMBean);
            this.pathHelper = PathHelper.partitionAwareFindOrCreate(jmsService.getCtx(true), pathServiceJndiName, ps.getCIC());
         } catch (NamingException var28) {
            throw new DeploymentException(var28);
         }

         PersistentStoreMBean storeMBean = this.activatedMBean.getPersistentStore();
         boolean isClustered = deployment.isClustered();
         this.allJndiNames = this.pathHelper.getAllJndiNames();
         PersistentStoreXA store;
         String storeName;
         String myJndiName;
         if (storeMBean == null) {
            if (isClustered) {
               throw new DeploymentException("cluster targeted path service can not use default store mBean=" + this.activatedMBean.getName() + " jndiName=" + this.pathHelper.getJndiName());
            }

            storeName = "DefaultStore";
            store = (PersistentStoreXA)PersistentStoreManager.getManager().getDefaultStore();
            myJndiName = this.pathHelper.getJndiName();
         } else {
            if (isClustered) {
               this.instanceNumber = deployment.getInstanceNumber();
               if (this.instanceNumber == -1) {
                  throw new DeploymentException("incorrect cluster path service configuration. PathServiceMBean name=" + this.activatedMBean.getName() + " instanceNumber=" + this.instanceNumber);
               }

               myJndiName = this.pathHelper.getJndiName();
               storeName = GenericDeploymentManager.getDecoratedSingletonInstanceName(storeMBean, deployment.getInstanceName());
            } else {
               storeName = storeMBean.getName();
               myJndiName = this.pathHelper.getJndiName();
            }

            store = (PersistentStoreXA)PersistentStoreManager.getManager().getStore(storeName);
         }

         if (store == null || this.instanceNumber == -1 && isClustered) {
            throw new DeploymentException("incorrect path service configuration. PathServiceMBean name=" + this.activatedMBean.getName() + " storeName=" + storeName + " store=" + store + " instanceNumber=" + this.instanceNumber);
         }

         this.pathMap = new PathServiceMap(myJndiName, this.pathHelper, store);
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
         String runName = deployment.getName();

         try {
            this.psRuntimeDelegate = new PathServiceRuntimeDelegate(runName, this.pathMap);
         } catch (ManagementException var27) {
            try {
               this.cleanup();
            } catch (Exception var24) {
            }

            throw new DeploymentException(var27.getMessage(), var27);
         }

         WebLogicMBean parent = this.psRuntimeDelegate.getParent();
         boolean addPathMap = false;

         try {
            ps.addPathServiceAdmin(myJndiName, this);
            this.doJNDIOperation(true, new AsyncMapImpl(myJndiName, this.pathHelper, this.pathMap));
            addPathMap = true;
            if (parent instanceof PartitionRuntimeMBean) {
               ((PartitionRuntimeMBean)parent).addPathServiceRuntime(this.psRuntimeDelegate, this.isDomainScope(this.pathMap));
            } else {
               runtimeAccess.getServerRuntime().addPathServiceRuntime(this.psRuntimeDelegate, this.isDomainScope(this.pathMap));
            }
         } catch (NamingException var26) {
            try {
               this.cleanup();
            } catch (Exception var25) {
            }

            throw new DeploymentException(var26.getMessage(), var26);
         } finally {
            if (addPathMap) {
               this.pathServiceMaps.put(this.pathMap.getJndiName(), this.pathMap);
            }

         }

         PathLogger.logPathStarted(runtimeAccess.getServer().getName(), this.pathMap.getJndiName(), storeName);
         this.running = true;
      }

   }

   public Iterator getPathServiceMapIterator() {
      HashMap cl = (HashMap)((HashMap)this.pathServiceMaps.clone());
      return cl.values().iterator();
   }

   public synchronized void deactivate(GenericManagedDeployment deployment) throws UndeploymentException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("Deactiating PathService: " + deployment.getName() + " for partition: " + JMSService.getSafePartitionNameFromThread());
      }

      try {
         this.cleanup();
      } catch (ServiceFailureException var3) {
         throw new UndeploymentException(var3);
      } catch (ManagementException var4) {
         throw new UndeploymentException(var4);
      }
   }

   public synchronized void cleanup() throws ManagementException, ServiceFailureException {
      if (this.pathMap != null) {
         PathServiceMap rememberPathMap = this.pathMap;
         this.pathMap = null;
         boolean var7 = false;

         WebLogicMBean parent;
         label165: {
            try {
               var7 = true;
               if (this.psRuntimeDelegate != null) {
                  PrivilegedActionUtilities.unregister(this.psRuntimeDelegate, KERNEL_ID);
               }

               if (this.running) {
                  this.running = false;

                  try {
                     this.doJNDIOperation(false, (Object)null);
                     var7 = false;
                     break label165;
                  } catch (NamingException var8) {
                     throw new ServiceFailureException(var8.getMessage(), var8);
                  }
               }

               var7 = false;
            } finally {
               if (var7) {
                  if (this.psRuntimeDelegate != null && rememberPathMap != null) {
                     WebLogicMBean parent = this.psRuntimeDelegate.getParent();
                     if (parent instanceof PartitionRuntimeMBean) {
                        ((PartitionRuntimeMBean)parent).removePathServiceRuntime(this.psRuntimeDelegate, this.isDomainScope(rememberPathMap));
                     } else {
                        ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().removePathServiceRuntime(this.psRuntimeDelegate, this.isDomainScope(rememberPathMap));
                     }

                     this.psRuntimeDelegate = null;
                  }

                  if (rememberPathMap != null) {
                     rememberPathMap.getPathHelper().unRegister(rememberPathMap);
                  }

               }
            }

            if (this.psRuntimeDelegate != null && rememberPathMap != null) {
               parent = this.psRuntimeDelegate.getParent();
               if (parent instanceof PartitionRuntimeMBean) {
                  ((PartitionRuntimeMBean)parent).removePathServiceRuntime(this.psRuntimeDelegate, this.isDomainScope(rememberPathMap));
               } else {
                  ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().removePathServiceRuntime(this.psRuntimeDelegate, this.isDomainScope(rememberPathMap));
               }

               this.psRuntimeDelegate = null;
            }

            if (rememberPathMap != null) {
               rememberPathMap.getPathHelper().unRegister(rememberPathMap);
            }

            return;
         }

         if (this.psRuntimeDelegate != null && rememberPathMap != null) {
            parent = this.psRuntimeDelegate.getParent();
            if (parent instanceof PartitionRuntimeMBean) {
               ((PartitionRuntimeMBean)parent).removePathServiceRuntime(this.psRuntimeDelegate, this.isDomainScope(rememberPathMap));
            } else {
               ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().removePathServiceRuntime(this.psRuntimeDelegate, this.isDomainScope(rememberPathMap));
            }

            this.psRuntimeDelegate = null;
         }

         if (rememberPathMap != null) {
            rememberPathMap.getPathHelper().unRegister(rememberPathMap);
         }

      }
   }

   private boolean isDomainScope(PathServiceMap rememberPathMap) {
      return "weblogic.PathService.default".equals(rememberPathMap.getJndiName());
   }

   public void unprepare(GenericManagedDeployment deployment) throws UndeploymentException {
   }

   private void doJNDIOperation(boolean doBind, Object jndiVal) throws NamingException {
      NamingException firstException = null;
      SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);

      try {
         PathServiceMap localPathMap = this.pathMap;
         PersistentStoreXA store = localPathMap == null ? null : localPathMap.getStore();
         String storeInfo;
         if (store == null) {
            storeInfo = "";
         } else {
            storeInfo = " , pathMap= " + store.getName();
         }

         String logInfo = this.activatedMBean.getName() + storeInfo + " , jndiName= ";
         String[] var8 = this.allJndiNames;
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String jndiName = var8[var10];
            if (jndiName != null) {
               if (doBind) {
                  this.pathHelper.getContext().bind(jndiName, jndiVal);
                  if (!localPathMap.getJndiName().equals(jndiName)) {
                     this.pathHelper.register(true, jndiName, localPathMap);
                  }
               } else {
                  this.pathHelper.getContext().unbind(jndiName);
               }
            }
         }
      } catch (NamingException var15) {
         if (firstException == null) {
            firstException = var15;
         }
      } finally {
         SecurityServiceManager.popSubject(KERNEL_ID);
      }

      if (firstException != null) {
         throw firstException;
      }
   }
}
