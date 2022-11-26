package weblogic.jms.backend;

import java.security.AccessController;
import java.util.List;
import javax.naming.Context;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.ModuleName;
import weblogic.jms.deployer.BEDeployer;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.jms.module.JMSModuleManagedEntityProvider;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class DestinationEntityProvider implements JMSModuleManagedEntityProvider {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public JMSModuleManagedEntity createEntity(ApplicationContext appCtx, EntityName entityName, Context namingContext, JMSBean wholeModule, NamedEntityBean specificBean, List localTargets, DomainMBean proposedDomain, boolean isJMSResourceDefinition) throws ModuleException {
      DestinationBean destBean = (DestinationBean)specificBean;
      JMSServerMBean jmsServerMBean = null;
      if (destBean.isDefaultTargetingEnabled()) {
         ApplicationContextInternal appCtxInternal = (ApplicationContextInternal)appCtx;
         WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(appCtxInternal);
         boolean isDeployedToRGT = appCtxInternal.isDeployedThroughResourceGroupTemplate();
         JMSServerMBean[] allJMSServers = JMSModuleHelper.getCandidateJMSServers(proposedDomain, deploymentScope, "Singleton", isDeployedToRGT);
         if (allJMSServers.length > 0) {
            jmsServerMBean = allJMSServers[0];
         }
      } else if (localTargets != null) {
         jmsServerMBean = (JMSServerMBean)localTargets.get(0);
      } else if (proposedDomain != null && destBean != null) {
         jmsServerMBean = proposedDomain.lookupJMSServer(destBean.getSubDeploymentName());
      }

      String backEndName;
      if (jmsServerMBean != null) {
         backEndName = jmsServerMBean.getName();
      } else {
         backEndName = destBean.getSubDeploymentName();
         jmsServerMBean = proposedDomain.lookupJMSServer(backEndName);
      }

      if (jmsServerMBean == null) {
         throw new ModuleException(JMSExceptionLogger.logNoBackEndLoggable(backEndName, entityName.getEntityName(), entityName.getFullyQualifiedModuleName()).getMessage());
      } else {
         TargetMBean[] targets = jmsServerMBean.getTargets();
         if (targets != null && targets.length != 0 && targets[0] instanceof ClusterMBean) {
            backEndName = GenericDeploymentManager.getDecoratedSingletonInstanceName(jmsServerMBean, "01", true);
         }

         return this.internalCreateEntity(entityName, namingContext, false, wholeModule, specificBean, backEndName, entityName);
      }
   }

   JMSModuleManagedEntity createTemporaryEntity(JMSBean wholeModule, NamedEntityBean specificBean, String backEndName, ModuleName auxiliaryModuleName) throws ModuleException {
      EntityName entityName = new EntityName(backEndName, (String)null, specificBean.getName());
      return this.internalCreateEntity(entityName, (Context)null, true, wholeModule, specificBean, backEndName, auxiliaryModuleName);
   }

   public JMSModuleManagedEntity createEntity(ApplicationContext appCtx, EntityName entityName, Context namingContext, JMSBean wholeModule, NamedEntityBean specificBean, String backEndName) throws ModuleException {
      return this.internalCreateEntity(entityName, namingContext, false, wholeModule, specificBean, backEndName, entityName);
   }

   private JMSModuleManagedEntity internalCreateEntity(EntityName entityName, Context applicationNamingContext, boolean isTemporary, JMSBean wholeModule, NamedEntityBean specificBean, String backEndName, ModuleName auxiliaryModuleName) throws ModuleException {
      BEDeployer bed = JMSService.getJMSServiceWithModuleException().getBEDeployer();
      BackEnd backEnd = bed.findBackEnd(backEndName);
      if (backEnd == null) {
         throw new ModuleException(JMSExceptionLogger.logNoBackEndLoggable(backEndName, entityName.getEntityName(), entityName.getFullyQualifiedModuleName()).getMessage());
      } else {
         SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);
         JMSModuleManagedEntity retVal = null;

         try {
            if (specificBean instanceof QueueBean) {
               retVal = new BEQueueRuntimeDelegate(entityName, backEnd, applicationNamingContext, isTemporary, auxiliaryModuleName, wholeModule, (QueueBean)specificBean);
            } else {
               retVal = new BETopicRuntimeDelegate(entityName, backEnd, applicationNamingContext, isTemporary, auxiliaryModuleName, wholeModule, (TopicBean)specificBean);
            }
         } finally {
            SecurityServiceManager.popSubject(KERNEL_ID);
         }

         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("Destination " + entityName.getEntityName() + " from module " + entityName.getFullyQualifiedModuleName() + " successfully created");
         }

         return (JMSModuleManagedEntity)retVal;
      }
   }
}
