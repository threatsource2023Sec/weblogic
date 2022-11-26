package weblogic.application.naming;

import javax.xml.namespace.QName;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.ServiceReferenceDescriptionBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

final class EnvEntriesValidateHelper {
   static boolean areEnvEntryBeansConflicting(EnvEntryBean env, EnvEntryBean previousEnv) {
      String newJNDIName = EnvUtils.decideJNDIName((String)null, env.getLookupName(), env.getMappedName());
      String oldJNDIName = EnvUtils.decideJNDIName((String)null, previousEnv.getLookupName(), previousEnv.getMappedName());
      return areConflicting(env.getEnvEntryType(), previousEnv.getEnvEntryType()) || areConflicting(env.getEnvEntryValue(), previousEnv.getEnvEntryValue()) || areConflicting(newJNDIName, oldJNDIName);
   }

   static boolean isJNDINameSpaceValid(EnvEntryBean env) {
      if (env.getLookupName() == null) {
         return env.getEnvEntryName().startsWith("java:app") || env.getEnvEntryName().startsWith("java:global");
      } else {
         return env.getLookupName().startsWith("java:app") || env.getLookupName().startsWith("java:global");
      }
   }

   static boolean areMessageDestinationRefBeansConflicting(MessageDestinationRefBean msgDesRefBean, MessageDestinationRefBean previousMsgDesRefBean) {
      String newJNDIName = EnvUtils.decideJNDIName((String)null, msgDesRefBean.getLookupName(), msgDesRefBean.getMappedName());
      String oldJNDIName = EnvUtils.decideJNDIName((String)null, previousMsgDesRefBean.getLookupName(), previousMsgDesRefBean.getMappedName());
      return areConflicting(msgDesRefBean.getMessageDestinationRefName(), previousMsgDesRefBean.getMessageDestinationRefName()) || areConflicting(msgDesRefBean.getMessageDestinationType(), previousMsgDesRefBean.getMessageDestinationType()) || areConflicting(msgDesRefBean.getMessageDestinationLink(), previousMsgDesRefBean.getMessageDestinationLink()) || areConflicting(newJNDIName, oldJNDIName);
   }

   static boolean arePersistenceContextRefBeansConflicting(PersistenceContextRefBean persistenceContextRefBean, PersistenceContextRefBean previousPersistenceContextRefBean) {
      return areConflicting(persistenceContextRefBean.getPersistenceContextRefName(), previousPersistenceContextRefBean.getPersistenceContextRefName()) || areConflicting(persistenceContextRefBean.getPersistenceUnitName(), previousPersistenceContextRefBean.getPersistenceUnitName()) || areConflicting(persistenceContextRefBean.getPersistenceContextType(), previousPersistenceContextRefBean.getPersistenceContextType()) || areConflicting(persistenceContextRefBean.getSynchronizationType(), previousPersistenceContextRefBean.getSynchronizationType());
   }

   static boolean arePersistenceUnitRefBeansConflicting(PersistenceUnitRefBean persistenceUnitRefBean, PersistenceUnitRefBean previousPersistenceUnitRefBean) {
      return areConflicting(persistenceUnitRefBean.getPersistenceUnitRefName(), previousPersistenceUnitRefBean.getPersistenceUnitRefName()) || areConflicting(persistenceUnitRefBean.getPersistenceUnitName(), previousPersistenceUnitRefBean.getPersistenceUnitName()) || areConflicting(persistenceUnitRefBean.getMappedName(), previousPersistenceUnitRefBean.getMappedName());
   }

   static boolean areDataSourceBeansConflicting(DataSourceBean dataSourceBean, DataSourceBean previousDataSourceBean) {
      return areConflicting(dataSourceBean.getName(), previousDataSourceBean.getName()) || areConflicting(dataSourceBean.getPassword(), previousDataSourceBean.getPassword()) || areConflicting(dataSourceBean.getUser(), previousDataSourceBean.getUser()) || areConflicting(dataSourceBean.getServerName(), previousDataSourceBean.getServerName()) || dataSourceBean.getPortNumber() != previousDataSourceBean.getPortNumber() || areConflicting(dataSourceBean.getDatabaseName(), previousDataSourceBean.getDatabaseName()) || areConflicting(dataSourceBean.getClassName(), previousDataSourceBean.getClassName()) || areConflicting(dataSourceBean.getDatabaseName(), previousDataSourceBean.getDatabaseName()) || dataSourceBean.getInitialPoolSize() != previousDataSourceBean.getInitialPoolSize() || dataSourceBean.getIsolationLevel() != previousDataSourceBean.getIsolationLevel() || dataSourceBean.getLoginTimeout() != previousDataSourceBean.getLoginTimeout() || dataSourceBean.getMaxIdleTime() != previousDataSourceBean.getMaxIdleTime() || dataSourceBean.getMaxStatements() != previousDataSourceBean.getMaxStatements() || dataSourceBean.getMaxPoolSize() != previousDataSourceBean.getMaxPoolSize() || dataSourceBean.getMinPoolSize() != previousDataSourceBean.getMinPoolSize() || areConflicting(dataSourceBean.getUrl(), previousDataSourceBean.getUrl()) || dataSourceBean.isTransactional() != previousDataSourceBean.isTransactional();
   }

   static boolean areResourceEnvRefBeansConflicting(ResourceEnvRefBean resourceEnvRefBean, String JNDIName, ResourceEnvRefInfo resourceEnvRefCache) {
      ResourceEnvRefBean previousResourceEnvRefBean = resourceEnvRefCache.getBean();
      return areConflicting(resourceEnvRefBean.getResourceEnvRefName(), previousResourceEnvRefBean.getResourceEnvRefName()) || areConflicting(resourceEnvRefBean.getResourceEnvRefType(), previousResourceEnvRefBean.getResourceEnvRefType()) || areConflicting(JNDIName, resourceEnvRefCache.getJNDIName());
   }

   static boolean isServiceRefBeanConflicting(ServiceRefBean serviceRefBean, ServiceRefBean preSvcRefBean, ServiceReferenceDescriptionBean serviceRefDesBean, ServiceReferenceDescriptionBean preSvcRefDesBean) {
      String wsdlURL = serviceRefDesBean != null ? serviceRefDesBean.getWsdlUrl() : null;
      String preWsdlURL = preSvcRefDesBean != null ? preSvcRefDesBean.getWsdlUrl() : null;
      QName preQN = preSvcRefBean.getServiceQname();
      QName curQN = serviceRefBean.getServiceQname();
      return areConflicting(serviceRefBean.getServiceRefName(), preSvcRefBean.getServiceRefName()) || areConflicting(serviceRefBean.getServiceInterface(), preSvcRefBean.getServiceInterface()) || areConflicting(serviceRefBean.getWsdlFile(), preSvcRefBean.getWsdlFile()) || areConflicting(serviceRefBean.getJaxrpcMappingFile(), preSvcRefBean.getJaxrpcMappingFile()) || curQN != null && preQN != null && !curQN.equals(preQN) || areConflicting(serviceRefBean.getServiceRefType(), preSvcRefBean.getServiceRefType()) || areConflicting(wsdlURL, preWsdlURL);
   }

   static boolean areConflicting(String s1, String s2) {
      return !isEmpty(s1) && !isEmpty(s2) && !s1.equals(s2);
   }

   private static boolean isEmpty(String s) {
      return s == null || "".equals(s);
   }

   static final class ResourceRefInfo {
      private final ResourceRefBean bean;
      private final String jndiName;
      private final EnvironmentBuilder.ResourceDescriptionData resourceDescriptionData;
      private final AuthenticatedSubject runAsSubject;

      ResourceRefInfo(ResourceRefBean bean, String jndiName, EnvironmentBuilder.ResourceDescriptionData resourceDescriptionData, AuthenticatedSubject runAsSubject) {
         this.bean = bean;
         this.jndiName = jndiName;
         this.runAsSubject = runAsSubject;
         this.resourceDescriptionData = resourceDescriptionData;
      }

      ResourceRefInfo mergeResourceRefAndVerify(EnvironmentBuilder.ResourceDescriptionData resourceDescriptionData, String newJndiName, String resRefName, ResourceRefBean resRef) {
         ResourceRefInfo resourceRefInfo = this;
         boolean isNewJndiNameFromWL = resourceDescriptionData != null;
         if (isNewJndiNameFromWL) {
            if (!this.isJndiNameFromWL()) {
               resourceRefInfo = new ResourceRefInfo(this.getBean(), newJndiName, resourceDescriptionData, this.getRunAsSubject());
            }
         } else if (this.isJndiNameFromWL()) {
            newJndiName = this.getJNDIName();
         }

         if (resourceRefInfo.areResourceRefBeansConflicting(resRef, newJndiName)) {
            resourceRefInfo = null;
         }

         return resourceRefInfo;
      }

      private boolean areResourceRefBeansConflicting(ResourceRefBean resourceRefBean, String JNDIName) {
         ResourceRefBean previousResourceRefBean = this.getBean();
         return EnvEntriesValidateHelper.areConflicting(resourceRefBean.getResRefName(), previousResourceRefBean.getResRefName()) || EnvEntriesValidateHelper.areConflicting(resourceRefBean.getResType(), previousResourceRefBean.getResType()) || EnvEntriesValidateHelper.areConflicting(resourceRefBean.getResAuth(), previousResourceRefBean.getResAuth()) || EnvEntriesValidateHelper.areConflicting(resourceRefBean.getResSharingScope(), previousResourceRefBean.getResSharingScope()) || EnvEntriesValidateHelper.areConflicting(JNDIName, this.getJNDIName());
      }

      ResourceRefBean getBean() {
         return this.bean;
      }

      String getJNDIName() {
         return this.jndiName;
      }

      boolean isJndiNameFromWL() {
         return this.resourceDescriptionData != null;
      }

      ResourceDescriptionBean getDesBean() {
         return this.resourceDescriptionData == null ? null : this.resourceDescriptionData.getResourceDescription();
      }

      AuthenticatedSubject getRunAsSubject() {
         return this.runAsSubject;
      }
   }

   static final class ServiceRefInfo {
      private final ServiceRefBean sr;
      private final ServiceReferenceDescriptionBean wlsr;

      ServiceRefInfo(ServiceRefBean sr, ServiceReferenceDescriptionBean wlsr) {
         this.sr = sr;
         this.wlsr = wlsr;
      }

      ServiceRefBean getSr() {
         return this.sr;
      }

      ServiceReferenceDescriptionBean getWlsr() {
         return this.wlsr;
      }
   }

   static final class ResourceEnvRefInfo {
      private final ResourceEnvRefBean bean;
      private final String jndiName;

      ResourceEnvRefInfo(ResourceEnvRefBean bean, String jndiName) {
         this.bean = bean;
         this.jndiName = jndiName;
      }

      ResourceEnvRefBean getBean() {
         return this.bean;
      }

      String getJNDIName() {
         return this.jndiName;
      }
   }
}
