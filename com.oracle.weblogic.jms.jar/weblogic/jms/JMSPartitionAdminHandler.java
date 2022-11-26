package weblogic.jms;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import weblogic.deployment.jms.JMSSessionPoolManager;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.saf.SAFService;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.path.PathService;
import weblogic.messaging.saf.internal.SAFServerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;

public final class JMSPartitionAdminHandler {
   public static final boolean PARTITION_LIFECYCLE_DEBUG = false;
   private static JMSPartitionAdminHandler singleton;
   private Map cics = new HashMap();
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   JMSPartitionAdminHandler() throws ManagementException, JMSException, ServiceFailureException {
      this.registerPartitionNotification();
      this.createServices();
   }

   public static synchronized void initialize() throws ManagementException, JMSException, ServiceFailureException {
      if (singleton == null) {
         singleton = new JMSPartitionAdminHandler();
      }

   }

   public static JMSPartitionAdminHandler getInstance() {
      return singleton;
   }

   public synchronized void createServices(String partitionName) throws ManagementException, JMSException, ServiceFailureException {
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      ComponentInvocationContext cic = null;
      if (partitionName != null && !partitionName.equals("")) {
         cic = cicm.createComponentInvocationContext(partitionName);
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("JMSPartitionAdminHandler createServices manufactured cic " + this.debugCIC(cic));
         }

         this.cics.put(partitionName, cic);
      }

      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(cic);
      Throwable var5 = null;

      try {
         JMSService.getJMSServiceWithManagementException();
         BridgeService.getPartitionBridgeService();
         SAFService.getSAFService();
         PathService.getService();
         SAFServerService.getService();
      } catch (Throwable var14) {
         var5 = var14;
         throw var14;
      } finally {
         if (mic != null) {
            if (var5 != null) {
               try {
                  mic.close();
               } catch (Throwable var13) {
                  var5.addSuppressed(var13);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private String debugCIC(ComponentInvocationContext cic) {
      return cic + " id-class " + (cic != null ? System.identityHashCode(cic) + cic.getClass().getName() : "Nil");
   }

   synchronized void stopServices(String partitionName) throws ManagementException, JMSException, ServiceFailureException {
      JMSLogger.logJMSSuspending();
      ComponentInvocationContext cic = (ComponentInvocationContext)this.cics.get(partitionName);
      if (cic != null) {
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(cic);
         Throwable var4 = null;

         try {
            SAFServerService safServerService = SAFServerService.removeService(partitionName);
            if (safServerService != null) {
               safServerService.stop();
            }

            PathService pathService = PathService.removeService(partitionName);
            if (pathService != null) {
               pathService.stop();
            }

            SAFService safService = SAFService.removeSAFService(partitionName);
            if (safService != null) {
               safService.stop();
            }

            PartitionBridgeService bridgeService = BridgeService.removePartitionBridgeService(partitionName);
            if (bridgeService != null) {
               bridgeService.stop(false);
            }

            JMSService jmsService = JMSService.removeJMSService(partitionName);
            if (jmsService != null) {
               jmsService.stop(false);
            }

            JMSSessionPoolManager.removeSessionPoolManager(partitionName, false);
         } catch (Throwable var17) {
            var4 = var17;
            throw var17;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var16) {
                     var4.addSuppressed(var16);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }
   }

   private synchronized void createServices() throws ManagementException, JMSException, ServiceFailureException {
   }

   private void registerPartitionNotification() {
      DomainMBean domainBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      domainBean.addBeanUpdateListener(new BeanUpdateListener() {
         public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         }

         public void activateUpdate(BeanUpdateEvent event) {
            ServerRuntimeMBean serverRuntimeBean = ManagementService.getRuntimeAccess(JMSPartitionAdminHandler.kernelId).getServerRuntime();
            BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();

            for(int i = 0; i < updated.length; ++i) {
               BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
               String name;
               switch (propertyUpdate.getUpdateType()) {
                  case 2:
                     name = propertyUpdate.getPropertyName();
                     if (!"ResourceGroups".equals(name)) {
                        break;
                     }

                     ResourceGroupMBean rg = (ResourceGroupMBean)propertyUpdate.getAddedObject();
                     String newName = null;
                     if (rg.getParent() != null) {
                        if (rg.getParent() instanceof DomainMBean) {
                           break;
                        }

                        newName = rg.getParent().getName();
                     }

                     try {
                        JMSPartitionAdminHandler.this.createServices(newName);
                     } catch (ManagementException var13) {
                        var13.printStackTrace();
                     } catch (JMSException var14) {
                        var14.printStackTrace();
                     } catch (ServiceFailureException var15) {
                        var15.printStackTrace();
                     }
                     break;
                  case 3:
                     name = propertyUpdate.getPropertyName();
                     if ("Partitions".equals(name)) {
                        PartitionMBean removedPartition = (PartitionMBean)propertyUpdate.getRemovedObject();

                        try {
                           JMSPartitionAdminHandler.this.stopServices(removedPartition.getName());
                        } catch (ManagementException var10) {
                           var10.printStackTrace();
                        } catch (JMSException var11) {
                           var11.printStackTrace();
                        } catch (ServiceFailureException var12) {
                           var12.printStackTrace();
                        }
                     }
               }
            }

         }

         public void rollbackUpdate(BeanUpdateEvent event) {
         }
      });
   }
}
