package weblogic.jms.saf;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ModuleException;
import weblogic.health.HealthMonitorService;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.SAFRuntimeMBean;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class SAFService extends AbstractServerService {
   @Inject
   @Named("BridgeService")
   private ServerService dependencyOnBridgeService;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final SAFAgentDeployer safDeployer;
   private SAFRuntimeMBeanImpl runtimeMBean;
   private String mbeanName;
   private static Map safServices = new HashMap();
   private IDEntityHelper idEntityHelper;
   private final ComponentInvocationContext cic;

   public SAFService() throws ManagementException {
      this(ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext());
   }

   public SAFService(ComponentInvocationContext cic) throws ManagementException {
      this.safDeployer = new SAFAgentDeployer();
      RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNEL_ID);
      this.mbeanName = ra.getServerName() + ".saf";
      this.runtimeMBean = new SAFRuntimeMBeanImpl(this.mbeanName);
      WebLogicMBean parent = this.runtimeMBean.getParent();
      if (parent instanceof PartitionRuntimeMBean) {
         ((PartitionRuntimeMBean)parent).setSAFRuntime(this.runtimeMBean);
      } else {
         ra.getServerRuntime().setSAFRuntime(this.runtimeMBean);
      }

      this.idEntityHelper = new IDEntityHelper(this.getPartitionName());
      SAFOutgoingReplyHandler.init();
      HealthMonitorService.register(this.mbeanName, this.runtimeMBean, false);
      this.safDeployer.start();
      this.cic = cic;
      Class var4 = SAFService.class;
      synchronized(SAFService.class) {
         safServices.put(this.getPartitionName(), this);
      }
   }

   public static synchronized SAFService getSAFService() {
      String partitionName = JMSService.getSafePartitionNameFromThread();
      SAFService service = null;
      if (safServices.get(partitionName) != null) {
         service = (SAFService)safServices.get(partitionName);
      } else {
         try {
            service = new SAFService(ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext());
            service.start();
         } catch (ServiceFailureException | ManagementException var3) {
            throw new RuntimeException("Failed to initialize SAFService for partition=" + partitionName, var3);
         }
      }

      return service;
   }

   public static synchronized SAFService getSAFService(String pName) {
      return (SAFService)safServices.get(pName);
   }

   public static synchronized SAFService getSAFServiceWithModuleException(String pName) throws ModuleException {
      SAFService svc = (SAFService)safServices.get(pName);
      if (svc == null) {
         throw new ModuleException("SAFService for partition " + pName + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static synchronized SAFService removeSAFService(String pName) {
      return (SAFService)safServices.remove(pName);
   }

   public SAFRuntimeMBeanImpl getRuntimeMBean() {
      return this.runtimeMBean;
   }

   public void setRuntimeMBean(SAFRuntimeMBeanImpl rmb) {
      this.runtimeMBean = rmb;
   }

   public SAFAgentDeployer getDeployer() {
      return this.safDeployer;
   }

   IDEntityHelper getIDEntityHelper() {
      return this.idEntityHelper;
   }

   String getPartitionName() {
      return JMSService.getSafePartitionKey(this.cic);
   }

   public void start() throws ServiceFailureException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("weblogic.jms.saf.SAFService start");
      }

   }

   public void stop() throws ServiceFailureException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("weblogic.jms.saf.SAFService stop");
      }

      if ("DOMAIN".equals(this.getPartitionName())) {
      }

      try {
         this.stopInternal();
      } catch (Exception var2) {
         if (var2 instanceof ServiceFailureException) {
            throw (ServiceFailureException)var2;
         } else {
            throw new ServiceFailureException(var2);
         }
      }
   }

   private void stopInternalWithCIC() throws ServiceFailureException {
      try {
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
         Throwable var2 = null;

         try {
            this.stopInternal();
         } catch (Throwable var12) {
            var2 = var12;
            throw var12;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var11) {
                     var2.addSuppressed(var11);
                  }
               } else {
                  mic.close();
               }
            }

         }

      } catch (Exception var14) {
         if (var14 instanceof ServiceFailureException) {
            throw (ServiceFailureException)var14;
         } else {
            throw new ServiceFailureException(var14);
         }
      }
   }

   public void stopAll() throws ServiceFailureException {
      List exceptions = new ArrayList();
      Class var2 = SAFService.class;
      synchronized(SAFService.class) {
         Iterator itr = safServices.values().iterator();

         while(itr.hasNext()) {
            SAFService service = (SAFService)itr.next();

            try {
               service.stopInternalWithCIC();
               itr.remove();
            } catch (ServiceFailureException var7) {
               exceptions.add(var7);
            }
         }
      }

      if (exceptions.size() > 0) {
         if (exceptions.size() > 1) {
            throw new ServiceFailureException(this.composeMultiExceptions(exceptions));
         } else {
            throw new ServiceFailureException((Throwable)exceptions.get(0));
         }
      }
   }

   private void stopInternal() throws Exception {
      Exception e = null;

      try {
         this.getDeployer().stop();
      } catch (Exception var11) {
         e = var11;
      }

      try {
         HealthMonitorService.unregister(this.mbeanName);
      } catch (Exception var14) {
         if (e != null) {
            e = var14;
         }
      }

      SAFRuntimeMBeanImpl safRuntimeDelegate = this.getRuntimeMBean();
      boolean var10 = false;

      label134: {
         WebLogicMBean parent;
         label133: {
            try {
               var10 = true;
               if (safRuntimeDelegate != null) {
                  PrivilegedActionUtilities.unregister(safRuntimeDelegate, KERNEL_ID);
                  var10 = false;
               } else {
                  var10 = false;
               }
               break label133;
            } catch (ManagementException var12) {
               e = var12;
               var10 = false;
            } finally {
               if (var10) {
                  if (safRuntimeDelegate != null) {
                     WebLogicMBean parent = safRuntimeDelegate.getParent();
                     if (parent instanceof PartitionRuntimeMBean) {
                        ((PartitionRuntimeMBean)parent).setSAFRuntime((SAFRuntimeMBean)null);
                     } else {
                        ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().setSAFRuntime((SAFRuntimeMBean)null);
                     }

                     this.setRuntimeMBean((SAFRuntimeMBeanImpl)null);
                  }

               }
            }

            if (safRuntimeDelegate != null) {
               parent = safRuntimeDelegate.getParent();
               if (parent instanceof PartitionRuntimeMBean) {
                  ((PartitionRuntimeMBean)parent).setSAFRuntime((SAFRuntimeMBean)null);
               } else {
                  ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().setSAFRuntime((SAFRuntimeMBean)null);
               }

               this.setRuntimeMBean((SAFRuntimeMBeanImpl)null);
            }
            break label134;
         }

         if (safRuntimeDelegate != null) {
            parent = safRuntimeDelegate.getParent();
            if (parent instanceof PartitionRuntimeMBean) {
               ((PartitionRuntimeMBean)parent).setSAFRuntime((SAFRuntimeMBean)null);
            } else {
               ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().setSAFRuntime((SAFRuntimeMBean)null);
            }

            this.setRuntimeMBean((SAFRuntimeMBeanImpl)null);
         }
      }

      if (e != null) {
         throw (Exception)e;
      }
   }

   public void halt() throws ServiceFailureException {
      this.stop();
   }

   private String composeMultiExceptions(List exceptions) {
      StringBuffer sb = new StringBuffer("Multiple exceptions are raised:\r\n");

      for(int i = 0; i < exceptions.size(); ++i) {
         Exception e = (Exception)exceptions.get(i);
         sb.append(i).append(". ").append(e.toString()).append("\r\n");
      }

      return sb.toString();
   }
}
