package weblogic.messaging.saf.internal;

import java.security.AccessController;
import javax.naming.NamingException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSService;
import weblogic.jms.saf.SAFAgentDeployer;
import weblogic.jms.saf.SAFService;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.GenericAdminHandler;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFLogger;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;

public final class SAFServiceAdmin implements GenericAdminHandler {
   private SAFAgentDeployer jmsSAFAgentDeployer = SAFService.getSAFService().getDeployer();
   private SAFServerService safService;
   private String serverName;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String partitionName;
   private ComponentInvocationContext cic;
   private final boolean isPartition;

   public SAFServiceAdmin() throws ServiceFailureException {
      this.serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      this.safService = SAFServerService.getService();
      this.partitionName = JMSService.getSafePartitionNameFromThread();
      this.isPartition = JMSService.isPartition(this.partitionName);
      if (this.isPartition) {
         this.cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      }

   }

   private void addAgent(SAFAgentAdmin agent) {
      this.safService.addAgent(agent);
   }

   private SAFAgentAdmin getAgent(String name) {
      return this.safService.getAgent(name);
   }

   private void removeAgent(String name) {
      this.safService.removeAgent(name);
   }

   public void prepare(GenericManagedDeployment deployment) throws DeploymentException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("SAFServiceAdmin.prepare(" + deployment + ")");
      }

      DeploymentMBean bean = deployment.getMBean();
      if (bean instanceof SAFAgentMBean) {
         this.jmsSAFAgentDeployer.prepare(deployment);

         try {
            this.safService.checkShutdown();
         } catch (ServiceFailureException var4) {
            throw new DeploymentException(var4);
         }

         try {
            this.addAgent(new SAFAgentAdmin(this, (SAFAgentMBean)bean, deployment));
         } catch (ManagementException var5) {
            if (SAFDebug.SAFAdmin.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
               SAFDebug.SAFAdmin.debug("Error preparing SAF agent " + this.getAgentName(bean.getName()), var5);
            }

            SAFLogger.logErrorPrepareSAFAgent(this.getAgentName(bean.getName()), var5);
            throw new DeploymentException("Error preparing SAF agent " + this.getAgentName(bean.getName()), var5);
         }

         SAFLogger.logSAFAgentPrepared(this.getAgentName(bean.getName()));
      }
   }

   public void activate(GenericManagedDeployment deployment) throws DeploymentException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("SAFServiceAdmin.activate(" + deployment + ")");
      }

      DeploymentMBean bean = deployment.getMBean();
      if (bean instanceof SAFAgentMBean) {
         this.jmsSAFAgentDeployer.activate(deployment);

         try {
            this.safService.checkShutdown();
         } catch (ServiceFailureException var5) {
            throw new DeploymentException(var5);
         }

         SAFAgentAdmin agentAdmin = this.getAgent(bean.getName());
         if (agentAdmin == null) {
            throw new DeploymentException("Error activating SAF agent " + this.getAgentName(bean.getName()) + ": it was not successfully prepared");
         } else {
            try {
               agentAdmin.start(deployment);
            } catch (SAFException var6) {
               if (SAFDebug.SAFAdmin.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                  SAFDebug.SAFAdmin.debug("Error activating SAF agent " + this.getAgentName(bean.getName()), var6);
               }

               SAFLogger.logErrorStartSAFAgent(this.getAgentName(bean.getName()), var6);
               throw new DeploymentException("Error activating SAF agent " + this.getAgentName(bean.getName()), var6);
            } catch (NamingException var7) {
               if (SAFDebug.SAFAdmin.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                  SAFDebug.SAFAdmin.debug("Error activating SAF agent " + this.getAgentName(bean.getName()), var7);
               }

               SAFLogger.logErrorStartSAFAgent(this.getAgentName(bean.getName()), var7);
               throw new DeploymentException("Error activating SAF agent " + this.getAgentName(bean.getName()), var7);
            }

            SAFLogger.logSAFAgentActivated(this.getAgentName(bean.getName()));
         }
      }
   }

   public void deactivate(GenericManagedDeployment deployment) throws UndeploymentException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("SAFServiceAdmin.deactivate(" + deployment + ")");
      }

      String currentPartitionName = JMSService.getSafePartitionNameFromThread();
      ManagedInvocationContext mic = null;
      if (!JMSService.isPartition(currentPartitionName)) {
         mic = JMSCICHelper.pushJMSCIC(this.cic);
      }

      try {
         DeploymentMBean bean = deployment.getMBean();
         if (!(bean instanceof SAFAgentMBean)) {
            return;
         }

         if (SAFDebug.SAFAdmin.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFAdmin.debug("Undeploying " + bean.getName());
         }

         SAFAgentAdmin agentAdmin = this.getAgent(bean.getName());
         if (agentAdmin == null) {
            return;
         }

         agentAdmin.close();
         SAFLogger.logSAFAgentDeactivated(this.getAgentName(bean.getName()));
         this.jmsSAFAgentDeployer.deactivate(deployment);
      } finally {
         if (mic != null) {
            mic.close();
         }

      }

   }

   public void unprepare(GenericManagedDeployment deployment) throws UndeploymentException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("SAFServiceAdmin.unprepare(" + deployment + ")");
      }

      String currentPartitionName = JMSService.getSafePartitionNameFromThread();
      ManagedInvocationContext mic = null;
      if (!JMSService.isPartition(currentPartitionName)) {
         mic = JMSCICHelper.pushJMSCIC(this.cic);
      }

      try {
         DeploymentMBean bean = deployment.getMBean();
         if (bean instanceof SAFAgentMBean) {
            this.removeAgent(bean.getName());
            SAFLogger.logSAFAgentUnprepared(this.getAgentName(bean.getName()));
            this.jmsSAFAgentDeployer.unprepare(deployment);
            return;
         }
      } finally {
         if (mic != null) {
            mic.close();
         }

      }

   }

   private String getAgentName(String mbeanName) {
      return mbeanName + "@" + this.serverName;
   }

   boolean isPartition() {
      return this.isPartition;
   }
}
