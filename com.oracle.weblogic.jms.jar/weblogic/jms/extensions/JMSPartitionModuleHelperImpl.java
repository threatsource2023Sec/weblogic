package weblogic.jms.extensions;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.ForeignDestinationBean;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.SAFErrorHandlingBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.j2ee.descriptor.wl.SAFQueueBean;
import weblogic.j2ee.descriptor.wl.SAFRemoteContextBean;
import weblogic.j2ee.descriptor.wl.SAFTopicBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSEditHelper;
import weblogic.jms.common.JMSException;
import weblogic.jms.common.PartitionUtils;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ReplicatedStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditTimedOutException;
import weblogic.management.mbeanservers.edit.NotEditorException;
import weblogic.management.mbeanservers.edit.ValidationException;
import weblogic.management.utils.AppDeploymentHelper;

class JMSPartitionModuleHelperImpl implements IJMSModuleHelper {
   private final Context ctx;
   private final DomainMBean domain;
   private final ConfigurationManagerMBean manager;
   private IJMSModuleHelper.ScopeType scopeType;
   private String scopeName;
   private String partitionName;

   JMSPartitionModuleHelperImpl(Context ctx, IJMSModuleHelper.ScopeType scopeType, String scopeName) throws JMSException {
      try {
         this.partitionName = (String)ctx.lookup("weblogic.partitionName");
      } catch (NameNotFoundException var6) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("JMSPartitionModuleHelperImpl() NameNotFoundException: " + var6.getMessage(), var6);
            JMSDebug.JMSModule.debug("JMSPartitionModuleHelperImpl() assume talking to server of pre-1221 release");
         }
      } catch (NamingException var7) {
         throw new JMSException(JMSExceptionLogger.logPartitionAbsentInContextLoggable().getMessage(), var7);
      }

      this.init(this.partitionName, scopeType, scopeName);
      this.domain = null;

      try {
         this.manager = JMSEditHelper.getConfigurationManager(ctx);
      } catch (javax.jms.JMSException var5) {
         if (var5 instanceof JMSException) {
            throw (JMSException)var5;
         }

         throw new JMSException(var5);
      }

      this.ctx = ctx;
   }

   JMSPartitionModuleHelperImpl(DomainMBean domain, String partitionName, IJMSModuleHelper.ScopeType scopeType, String scopeName) throws JMSException {
      this.init(partitionName, scopeType, scopeName);
      this.domain = domain;
      this.manager = null;
      this.ctx = null;
   }

   private void init(String partitionName, IJMSModuleHelper.ScopeType scopeType, String scopeName) throws JMSException {
      if (partitionName == null && scopeType != IJMSModuleHelper.ScopeType.DOMAIN) {
         throw new JMSException(JMSExceptionLogger.logScopeConflictLoggable().getMessage());
      } else if (PartitionUtils.isDomain(partitionName) || scopeType != IJMSModuleHelper.ScopeType.DOMAIN && scopeType != IJMSModuleHelper.ScopeType.RGT) {
         this.partitionName = partitionName;
         this.scopeType = scopeType;
         this.scopeName = scopeName;
      } else {
         throw new JMSException(JMSExceptionLogger.logScopeConflictLoggable().getMessage());
      }
   }

   public void createJMSSystemResource(String resourceName, String targetName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName));
      this.validateTargetName(targetName);
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createJMSSystemResource(mydomain, resourceName, targetName);
         } else {
            this.getRgOrRgt(mydomain).createJMSSystemResource(resourceName);
         }

         success = true;
      } catch (Exception var9) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInDomainLoggable(this.getScopeAsString(mydomain), "JMSSystemResource", resourceName).getMessage(), var9);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, resourceName, success);
         }

      }

   }

   public void deleteJMSSystemResource(String resourceName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteJMSSystemResource(mydomain, resourceName);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            template.destroyJMSSystemResource(jmsResource);
         }

         success = true;
      } catch (Exception var9) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromDomainLoggable(this.getScopeAsString(mydomain), "JMSSystemResource", resourceName).getMessage(), var9);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, resourceName, success);
         }

      }

   }

   public void createPersistentStore(IJMSModuleHelper.StoreType storeType, String storeName, String targetName, PersistentStoreModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("storeName", storeName));
      this.validateTargetName(targetName);
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (storeType == null) {
            storeType = IJMSModuleHelper.StoreType.FILE;
         }

         PersistentStoreMBean store = null;
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            TargetMBean[] targetMBeans = this.targetNames2TargetMBeans(mydomain, targetName);
            if (storeType == IJMSModuleHelper.StoreType.FILE) {
               store = mydomain.createFileStore(storeName);
            } else if (storeType == IJMSModuleHelper.StoreType.JDBC) {
               store = mydomain.createJDBCStore(storeName);
            } else {
               if (storeType != IJMSModuleHelper.StoreType.REPLICATED) {
                  throw new JMSException("ERROR: invalid store type " + storeType);
               }

               store = mydomain.createReplicatedStore(storeName);
            }

            ((PersistentStoreMBean)store).setTargets(targetMBeans);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            if (storeType == IJMSModuleHelper.StoreType.FILE) {
               store = template.createFileStore(storeName);
            } else {
               if (storeType != IJMSModuleHelper.StoreType.JDBC) {
                  throw new JMSException("ERROR: invalid store type " + storeType);
               }

               store = template.createJDBCStore(storeName);
            }
         }

         if (modifier != null) {
            modifier.modify((PersistentStoreMBean)store);
         }

         success = true;
      } catch (Exception var12) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInDomainLoggable(this.getScopeAsString(mydomain), "PersistentStore", storeName).getMessage(), var12);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, storeName, success);
         }

      }

   }

   public void deletePersistentStore(String storeName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("storeName", storeName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         boolean foundStore = true;
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            FileStoreMBean fileStore = mydomain.lookupFileStore(storeName);
            if (fileStore != null) {
               mydomain.destroyFileStore(fileStore);
            } else {
               JDBCStoreMBean jdbcStore = mydomain.lookupJDBCStore(storeName);
               if (jdbcStore != null) {
                  mydomain.destroyJDBCStore(jdbcStore);
               } else {
                  ReplicatedStoreMBean replicatedStore = mydomain.lookupReplicatedStore(storeName);
                  if (replicatedStore != null) {
                     mydomain.destroyReplicatedStore(replicatedStore);
                  } else {
                     foundStore = false;
                  }
               }
            }
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            FileStoreMBean fileStore = template.lookupFileStore(storeName);
            if (fileStore != null) {
               template.destroyFileStore(fileStore);
            } else {
               JDBCStoreMBean jdbcStore = template.lookupJDBCStore(storeName);
               if (jdbcStore != null) {
                  template.destroyJDBCStore(jdbcStore);
               } else {
                  foundStore = false;
               }
            }
         }

         if (!foundStore) {
            throw new JMSException(JMSExceptionLogger.logResourceAbsentLoggable("Persistent Store", storeName, this.getScopeAsString(mydomain)).getMessage());
         }

         success = true;
      } catch (Exception var11) {
         var11.printStackTrace();
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromDomainLoggable(this.getScopeAsString(mydomain), "PersistentStore", storeName).getMessage(), var11);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, storeName, success);
         }

      }

   }

   public void createJMSServer(String jmsServerName, String targetName, String storeName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("jmsServerName", jmsServerName));
      this.validateTargetName(targetName);
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         PersistentStoreMBean store = null;
         if (storeName != null) {
            store = this.findStore(mydomain, storeName);
         }

         JMSServerMBean jmsServer = null;
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createJMSServer(mydomain, jmsServerName, targetName);
            jmsServer = mydomain.lookupJMSServer(jmsServerName);
         } else {
            jmsServer = this.getRgOrRgt(mydomain).createJMSServer(jmsServerName);
         }

         if (store != null) {
            jmsServer.setPersistentStore(store);
         }

         success = true;
      } catch (Exception var11) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInDomainLoggable(this.getScopeAsString(mydomain), "JMSServer", jmsServerName).getMessage(), var11);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, jmsServerName, success);
         }

      }

   }

   public void deleteJMSServer(String jmsServerName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("jmsServerName", jmsServerName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteJMSServer(mydomain, jmsServerName);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSServerMBean jmsServer = template.lookupJMSServer(jmsServerName);
            if (jmsServer == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInDomainLoggable(this.getScopeAsString(mydomain), "JMSServer", jmsServerName).getMessage());
            }

            template.destroyJMSServer(jmsServer);
         }

         success = true;
      } catch (Exception var9) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromDomainLoggable(this.getScopeAsString(mydomain), "JMSServer", jmsServerName).getMessage(), var9);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, jmsServerName, success);
         }

      }

   }

   public void createSAFAgent(String safAgentName, String targetName, String storeName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("safAgentName", safAgentName));
      this.validateTargetName(targetName);
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         PersistentStoreMBean store = null;
         if (storeName != null) {
            store = this.findStore(mydomain, storeName);
         }

         SAFAgentMBean safAgent = null;
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createSAFAgent(mydomain, safAgentName, targetName);
            safAgent = mydomain.lookupSAFAgent(safAgentName);
         } else {
            safAgent = this.getRgOrRgt(mydomain).createSAFAgent(safAgentName);
         }

         if (store != null) {
            safAgent.setStore(store);
         }

         success = true;
      } catch (Exception var11) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInDomainLoggable(this.getScopeAsString(mydomain), "SAFAgent", safAgentName).getMessage(), var11);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safAgentName, success);
         }

      }

   }

   public void deleteSAFAgent(String safAgentName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("safAgentName", safAgentName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteSAFAgent(mydomain, safAgentName);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            SAFAgentMBean safAgent = template.lookupSAFAgent(safAgentName);
            if (safAgent == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInDomainLoggable(this.getScopeAsString(mydomain), "SAFAgent", safAgentName).getMessage());
            }

            template.destroySAFAgent(safAgent);
         }

         success = true;
      } catch (Exception var9) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromDomainLoggable(this.getScopeAsString(mydomain), "SAFAgent", safAgentName).getMessage(), var9);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safAgentName, success);
         }

      }

   }

   public void deployJMSServer(String jmsServerName, String targetName) throws JMSException {
      if (this.scopeType != IJMSModuleHelper.ScopeType.DOMAIN) {
         throw new UnsupportedOperationException(JMSExceptionLogger.logInvalidDeploymentInRGTLoggable("IJMSModuleHelper.deployJMSServer").getMessage());
      } else {
         this.validateParameters(new AbstractMap.SimpleImmutableEntry("jmsServerName", jmsServerName));
         this.validateTargetName(targetName);
         DomainMBean mydomain = this.getDomainMBean();
         boolean success = false;

         try {
            JMSModuleHelper.deployJMSServer(mydomain, jmsServerName, targetName);
            success = true;
         } catch (Exception var9) {
            throw new JMSException("ERROR: failed to deploy JMSServer " + jmsServerName, var9);
         } finally {
            if (this.domain == null) {
               this.endEditSession(this.manager, jmsServerName, success);
            }

         }

      }
   }

   public void undeployJMSServer(String jmsServerName) throws JMSException {
      if (this.scopeType != IJMSModuleHelper.ScopeType.DOMAIN) {
         throw new UnsupportedOperationException(JMSExceptionLogger.logInvalidDeploymentInRGTLoggable("IJMSModuleHelper.undeployJMSServer").getMessage());
      } else {
         this.validateParameters(new AbstractMap.SimpleImmutableEntry("jmsServerName", jmsServerName));
         DomainMBean mydomain = this.getDomainMBean();
         boolean success = false;

         try {
            JMSModuleHelper.undeployJMSServer(mydomain, jmsServerName);
            success = true;
         } catch (Exception var8) {
            throw new JMSException("ERROR: failed to deploy JMSServer " + jmsServerName, var8);
         } finally {
            if (this.domain == null) {
               this.endEditSession(this.manager, jmsServerName, success);
            }

         }

      }
   }

   public void createConnectionFactory(String resourceName, String factoryName, String factoryJndiName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("factoryName", factoryName), new AbstractMap.SimpleImmutableEntry("targetName", targetName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createConnectionFactory(mydomain, resourceName, factoryName, factoryJndiName, targetName, modifier);
         } else {
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(this.getRgOrRgt(mydomain), resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            JMSConnectionFactoryBean factory = jmsBean.createConnectionFactory(factoryName);
            factory.setJNDIName(factoryJndiName);
            if (modifier != null) {
               modifier.modify(factory);
            }

            if (targetName.equalsIgnoreCase("*Default Targeting Enabled*")) {
               factory.setDefaultTargetingEnabled(true);
            } else {
               TargetMBean[] targetMBeans = this.targetNames2TargetMBeans(jmsResource, targetName);
               String subDeploymentName = factory.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = factoryName;
               }

               SubDeploymentMBean subDeployment = this.findOrCreateSubDeployment(jmsResource, subDeploymentName);
               subDeployment.setTargets(targetMBeans);
            }
         }

         success = true;
      } catch (Exception var17) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ConnectionFactory", factoryName).getMessage(), var17);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, factoryName, success);
         }

      }

   }

   public void deleteConnectionFactory(String resourceName, String factoryName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("factoryName", factoryName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteConnectionFactory(mydomain, resourceName, factoryName);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            JMSConnectionFactoryBean factoryBean = jmsBean.lookupConnectionFactory(factoryName);
            if (factoryBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ConnectionFactory", factoryName).getMessage());
            }

            jmsBean.destroyConnectionFactory(factoryBean);
         }

         success = true;
      } catch (Exception var10) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ConnectionFactory", factoryName).getMessage(), var10);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, factoryName, success);
         }

      }

   }

   public void createQueue(String resourceName, String jmsServerName, String queueName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("queueName", queueName), new AbstractMap.SimpleImmutableEntry("jmsServerName", jmsServerName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createQueue(mydomain, resourceName, jmsServerName, queueName, jndiName, modifier);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            QueueBean queueBean = jmsBean.createQueue(queueName);
            queueBean.setJNDIName(jndiName);
            if (modifier != null) {
               modifier.modify(queueBean);
            }

            if (jmsServerName.equalsIgnoreCase("*Default Targeting Enabled*")) {
               queueBean.setDefaultTargetingEnabled(true);
            } else {
               JMSServerMBean jmsServer = this.findJMSServer(template, jmsServerName);
               String subDeploymentName = queueBean.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = queueName;
               }

               SubDeploymentMBean subDeployment = this.findOrCreateSubDeployment(jmsResource, subDeploymentName);
               subDeployment.addTarget(jmsServer);
            }
         }

         success = true;
      } catch (Exception var18) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Queue", queueName).getMessage(), var18);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, queueName, success);
         }

      }

   }

   public void deleteQueue(String resourceName, String queueName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("queueName", queueName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteQueue(mydomain, resourceName, queueName);
         } else {
            this.deleteDestination(mydomain, resourceName, queueName, "Queue");
         }

         success = true;
      } catch (Exception var9) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Queue", queueName).getMessage(), var9);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, queueName, success);
         }

      }

   }

   public void createUniformDistributedQueue(String resourceName, String uniformDistributedQueueName, String jndiName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("uniformDistributedQueueName", uniformDistributedQueueName), new AbstractMap.SimpleImmutableEntry("targetName", targetName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createUniformDistributedQueue(mydomain, resourceName, uniformDistributedQueueName, jndiName, targetName, modifier);
         } else {
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(this.getRgOrRgt(mydomain), resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            UniformDistributedQueueBean uniformDistributedQueueBean = jmsBean.createUniformDistributedQueue(uniformDistributedQueueName);
            uniformDistributedQueueBean.setJNDIName(jndiName);
            if (modifier != null) {
               modifier.modify(uniformDistributedQueueBean);
            }

            if (targetName.equalsIgnoreCase("*Default Targeting Enabled*")) {
               uniformDistributedQueueBean.setDefaultTargetingEnabled(true);
            } else {
               TargetMBean[] targetMBeans = this.targetNames2TargetMBeans(jmsResource, targetName);
               String subDeploymentName = uniformDistributedQueueBean.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = uniformDistributedQueueName;
               }

               SubDeploymentMBean subDeployment = this.findOrCreateSubDeployment(jmsResource, subDeploymentName);
               subDeployment.setTargets(targetMBeans);
            }
         }

         success = true;
      } catch (Exception var17) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "UniformDistributedQueue", uniformDistributedQueueName).getMessage(), var17);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, uniformDistributedQueueName, success);
         }

      }

   }

   public void deleteUniformDistributedQueue(String resourceName, String uniformDistributedQueueName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("uniformDistributedQueueName", uniformDistributedQueueName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteUniformDistributedQueue(mydomain, resourceName, uniformDistributedQueueName);
         } else {
            this.deleteDestination(mydomain, resourceName, uniformDistributedQueueName, "UniformDistributedQueue");
         }

         success = true;
      } catch (Exception var9) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "UniformDistributedQueue", uniformDistributedQueueName).getMessage(), var9);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, uniformDistributedQueueName, success);
         }

      }

   }

   public void createUniformDistributedTopic(String resourceName, String uniformDistributedTopicName, String jndiName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("uniformDistributedTopicName", uniformDistributedTopicName), new AbstractMap.SimpleImmutableEntry("targetName", targetName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createUniformDistributedTopic(mydomain, resourceName, uniformDistributedTopicName, jndiName, targetName, modifier);
         } else {
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(this.getRgOrRgt(mydomain), resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            UniformDistributedTopicBean uniformDistributedTopicBean = jmsBean.createUniformDistributedTopic(uniformDistributedTopicName);
            uniformDistributedTopicBean.setJNDIName(jndiName);
            if (modifier != null) {
               modifier.modify(uniformDistributedTopicBean);
            }

            if (targetName.equalsIgnoreCase("*Default Targeting Enabled*")) {
               uniformDistributedTopicBean.setDefaultTargetingEnabled(true);
            } else {
               TargetMBean[] targetMBeans = this.targetNames2TargetMBeans(jmsResource, targetName);
               String subDeploymentName = uniformDistributedTopicBean.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = uniformDistributedTopicName;
               }

               SubDeploymentMBean subDeployment = this.findOrCreateSubDeployment(jmsResource, subDeploymentName);
               subDeployment.setTargets(targetMBeans);
            }
         }

         success = true;
      } catch (Exception var17) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "UniformDistributedTopic", uniformDistributedTopicName).getMessage(), var17);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, uniformDistributedTopicName, success);
         }

      }

   }

   public void deleteUniformDistributedTopic(String resourceName, String uniformDistributedTopicName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("uniformDistributedTopicName", uniformDistributedTopicName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteUniformDistributedTopic(mydomain, resourceName, uniformDistributedTopicName);
         } else {
            this.deleteDestination(mydomain, resourceName, uniformDistributedTopicName, "UniformDistributedTopic");
         }

         success = true;
      } catch (Exception var9) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "UniformDistributedTopic", uniformDistributedTopicName).getMessage(), var9);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, uniformDistributedTopicName, success);
         }

      }

   }

   public DestinationBean findDestinationBean(String name, JMSBean module) {
      return JMSModuleHelper.findDestinationBean(name, module);
   }

   private void deleteDestination(DomainMBean mydomain, String resourceName, String destinationName, String destinationType) throws JMSException {
      JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
      if (destinationType.equals("Queue")) {
         QueueBean queueBean = jmsBean.lookupQueue(destinationName);
         if (queueBean == null) {
            throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), destinationType, destinationName).getMessage());
         }

         jmsBean.destroyQueue(queueBean);
      } else if (destinationType.equals("Topic")) {
         TopicBean topicBean = jmsBean.lookupTopic(destinationName);
         if (topicBean == null) {
            throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), destinationType, destinationName).getMessage());
         }

         jmsBean.destroyTopic(topicBean);
      } else if (destinationType.equals("UniformDistributedQueue")) {
         UniformDistributedQueueBean udQueueBean = jmsBean.lookupUniformDistributedQueue(destinationName);
         if (udQueueBean == null) {
            throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), destinationType, destinationName).getMessage());
         }

         jmsBean.destroyUniformDistributedQueue(udQueueBean);
      } else if (destinationType.equals("UniformDistributedTopic")) {
         UniformDistributedTopicBean udTopicBean = jmsBean.lookupUniformDistributedTopic(destinationName);
         if (udTopicBean == null) {
            throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), destinationType, destinationName).getMessage());
         }

         jmsBean.destroyUniformDistributedTopic(udTopicBean);
      }

   }

   private ResourceGroupTemplateMBean getRgOrRgt(DomainMBean mydomain) throws JMSException {
      String rgScope = "Resource Group";
      String enClosingScope = null;
      ResourceGroupTemplateMBean template = null;
      if (this.scopeType == IJMSModuleHelper.ScopeType.RGT) {
         enClosingScope = "WebLogic domain=" + mydomain.getName();
         template = mydomain.lookupResourceGroupTemplate(this.scopeName);
         if (template == null) {
            throw new JMSException(JMSExceptionLogger.logResourceGroupAbsentLoggable("Resource Group Template", rgScope, enClosingScope).getMessage());
         }
      } else if (this.scopeType == IJMSModuleHelper.ScopeType.RG) {
         if (PartitionUtils.isDomain(this.partitionName)) {
            enClosingScope = "WebLogic domain=" + mydomain.getName();
            template = mydomain.lookupResourceGroup(this.scopeName);
         } else {
            PartitionMBean partition = mydomain.lookupPartition(this.partitionName);
            if (partition == null) {
               throw new JMSException(JMSExceptionLogger.logPartitionAbsent(this.partitionName, "WebLogic domain=" + mydomain.getName()));
            }

            template = partition.lookupResourceGroup(this.scopeName);
            enClosingScope = "WebLogic domain=" + mydomain.getName() + "/Partition=" + this.partitionName;
         }

         if (template == null) {
            throw new JMSException(JMSExceptionLogger.logResourceGroupAbsentLoggable("Resource Group", rgScope, enClosingScope).getMessage());
         }
      }

      return (ResourceGroupTemplateMBean)template;
   }

   private String getScopeAsString(DomainMBean mydomain) {
      String deploymentscope = null;
      if (this.scopeType == IJMSModuleHelper.ScopeType.RGT) {
         deploymentscope = "WebLogic domain=" + mydomain.getName() + "/" + "Resource Group Template" + "=" + this.scopeName;
      } else if (this.scopeType == IJMSModuleHelper.ScopeType.RG) {
         if (PartitionUtils.isDomain(this.partitionName)) {
            deploymentscope = "WebLogic domain=" + mydomain.getName() + "/" + "Resource Group" + "=" + this.scopeName;
         } else {
            deploymentscope = "Partition=" + this.partitionName + "/" + "Resource Group" + "=" + this.scopeName;
         }
      }

      return deploymentscope;
   }

   public JMSSystemResourceMBean findJMSSystemResource(String resourceName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName));
      DomainMBean mydomain = null;
      if (this.domain == null) {
         try {
            mydomain = JMSEditHelper.getDomain(this.ctx);
         } catch (Exception var5) {
            throw new JMSException("ERROR: cannot get domain", var5);
         }
      } else {
         mydomain = this.domain;
      }

      if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
         JMSSystemResourceMBean resource = mydomain.lookupJMSSystemResource(resourceName);
         return resource;
      } else {
         ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
         JMSSystemResourceMBean jmsResource = template.lookupJMSSystemResource(resourceName);
         return jmsResource;
      }
   }

   public void createQuota(String resourceName, String quotaName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("quotaName", quotaName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createQuota((DomainMBean)mydomain, resourceName, quotaName, (String)null, modifier);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            QuotaBean quotaBean = jmsBean.createQuota(quotaName);
            if (modifier != null) {
               modifier.modify(quotaBean);
            }
         }

         success = true;
      } catch (Exception var11) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Quota", quotaName).getMessage(), var11);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, quotaName, success);
         }

      }

   }

   public void deleteQuota(String resourceName, String quotaName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("quotaName", quotaName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteQuota(mydomain, resourceName, quotaName);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            QuotaBean quotaBean = jmsBean.lookupQuota(quotaName);
            if (quotaBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Quota", quotaName).getMessage());
            }

            jmsBean.destroyQuota(quotaBean);
         }

         success = true;
      } catch (Exception var10) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Quota", quotaName).getMessage(), var10);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, quotaName, success);
         }

      }

   }

   public void createSAFErrorHandling(String resourceName, String safErrorHandlingName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safErrorHandlingName", safErrorHandlingName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createSAFErrorHandling(mydomain, resourceName, safErrorHandlingName, modifier);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            SAFErrorHandlingBean safErrorHandlingBean = jmsBean.createSAFErrorHandling(safErrorHandlingName);
            if (modifier != null) {
               modifier.modify(safErrorHandlingBean);
            }
         }

         success = true;
      } catch (Exception var11) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFErrorHandling", safErrorHandlingName).getMessage(), var11);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safErrorHandlingName, success);
         }

      }

   }

   public void deleteSAFErrorHandling(String resourceName, String safErrorHandlingName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safErrorHandlingName", safErrorHandlingName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteSAFErrorHandling(mydomain, resourceName, safErrorHandlingName);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            SAFErrorHandlingBean safErrorHandlingBean = jmsBean.lookupSAFErrorHandling(safErrorHandlingName);
            if (safErrorHandlingBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFErrorHandling", safErrorHandlingName).getMessage());
            }

            jmsBean.destroySAFErrorHandling(safErrorHandlingBean);
         }

         success = true;
      } catch (Exception var10) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFErrorHandling", safErrorHandlingName).getMessage(), var10);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safErrorHandlingName, success);
         }

      }

   }

   public void createSAFImportedDestinations(String resourceName, String safImportedDestinationsName, String safRemoteContextName, String safErrorHandlingName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safImportedDestinationsName", safImportedDestinationsName), new AbstractMap.SimpleImmutableEntry("safRemoteContextName", safRemoteContextName), new AbstractMap.SimpleImmutableEntry("targetName", targetName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createSAFImportedDestinations(mydomain, resourceName, safImportedDestinationsName, safRemoteContextName, targetName, modifier);
         } else {
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(this.getRgOrRgt(mydomain), resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            TargetMBean[] targetMBeans = null;
            if (!targetName.equalsIgnoreCase("*Default Targeting Enabled*")) {
               targetMBeans = this.targetNames2TargetMBeans(jmsResource, targetName);
               TargetMBean[] var12 = targetMBeans;
               int var13 = targetMBeans.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  TargetMBean targetMBean = var12[var14];
                  if (targetMBean instanceof JMSServerMBean) {
                     throw new JMSException(JMSExceptionLogger.logInvalidSAFImportedDestinationsTargetInRGLoggable(this.getScopeAsString(mydomain, resourceName), targetName, "JMSServerMBean", safImportedDestinationsName).getMessage());
                  }
               }
            }

            SAFRemoteContextBean safRemoteContext = jmsBean.lookupSAFRemoteContext(safRemoteContextName);
            if (safRemoteContext == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFRemoteContext", safRemoteContextName).getMessage());
            }

            SAFImportedDestinationsBean safImportedDestinationsBean = jmsBean.createSAFImportedDestinations(safImportedDestinationsName);
            safImportedDestinationsBean.setSAFRemoteContext(safRemoteContext);
            if (safErrorHandlingName != null && !safErrorHandlingName.trim().equals("")) {
               SAFErrorHandlingBean safErrorHandlingBean = jmsBean.lookupSAFErrorHandling(safErrorHandlingName);
               if (safErrorHandlingBean != null) {
                  safImportedDestinationsBean.setSAFErrorHandling(safErrorHandlingBean);
               }
            }

            if (modifier != null) {
               modifier.modify(safImportedDestinationsBean);
            }

            if (targetName.equalsIgnoreCase("*Default Targeting Enabled*")) {
               safImportedDestinationsBean.setDefaultTargetingEnabled(true);
            } else {
               String subDeploymentName = safImportedDestinationsBean.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = safImportedDestinationsName;
               }

               SubDeploymentMBean subDeployment = this.findOrCreateSubDeployment(jmsResource, subDeploymentName);
               subDeployment.setTargets(targetMBeans);
            }
         }

         success = true;
      } catch (Exception var19) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFImportedDestinations", safImportedDestinationsName).getMessage(), var19);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safImportedDestinationsName, success);
         }

      }

   }

   public void deleteSAFImportedDestinations(String resourceName, String safImportedDestinationsName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safImportedDestinationsName", safImportedDestinationsName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteSAFImportedDestinations(mydomain, resourceName, safImportedDestinationsName);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            SAFImportedDestinationsBean safImportedDestinationsBean = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
            if (safImportedDestinationsBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFImportedDestinations", safImportedDestinationsName).getMessage());
            }

            SAFQueueBean[] safQueueBeans = safImportedDestinationsBean.getSAFQueues();
            if (safQueueBeans != null) {
               for(int i = 0; i < safQueueBeans.length; ++i) {
                  safImportedDestinationsBean.destroySAFQueue(safQueueBeans[i]);
               }
            }

            SAFTopicBean[] safTopicBeans = safImportedDestinationsBean.getSAFTopics();
            if (safTopicBeans != null) {
               for(int i = 0; i < safTopicBeans.length; ++i) {
                  safImportedDestinationsBean.destroySAFTopic(safTopicBeans[i]);
               }
            }

            jmsBean.destroySAFImportedDestinations(safImportedDestinationsBean);
         }

         success = true;
      } catch (Exception var13) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFImportedDestinations", safImportedDestinationsName).getMessage(), var13);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safImportedDestinationsName, success);
         }

      }

   }

   public void createSAFQueue(String resourceName, String safImportedDestinationsName, String safQueueName, String safQueueRemoteJNDIName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safImportedDestinationsName", safImportedDestinationsName), new AbstractMap.SimpleImmutableEntry("safQueueName", safQueueName), new AbstractMap.SimpleImmutableEntry("safQueueRemoteJNDIName", safQueueRemoteJNDIName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createSAFQueue(mydomain, resourceName, safImportedDestinationsName, safQueueName, safQueueRemoteJNDIName, modifier);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            SAFImportedDestinationsBean safImportedDestinations = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
            if (safImportedDestinations == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFImportedDestinations", safImportedDestinationsName).getMessage());
            }

            SAFQueueBean safQueueBean = safImportedDestinations.createSAFQueue(safQueueName);
            safQueueBean.setRemoteJNDIName(safQueueRemoteJNDIName);
            if (modifier != null) {
               modifier.modify(safQueueBean);
            }
         }

         success = true;
      } catch (Exception var14) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFQueue", safQueueName).getMessage(), var14);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safQueueName, success);
         }

      }

   }

   public void deleteSAFQueue(String resourceName, String safImportedDestinationsName, String safQueueName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safImportedDestinationsName", safImportedDestinationsName), new AbstractMap.SimpleImmutableEntry("safQueueName", safQueueName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteSAFQueue(mydomain, resourceName, safImportedDestinationsName, safQueueName);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            SAFImportedDestinationsBean safImportedDestinationsBean = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
            if (safImportedDestinationsBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFImportedDestinations", safImportedDestinationsName).getMessage());
            }

            SAFQueueBean safQueueBean = safImportedDestinationsBean.lookupSAFQueue(safQueueName);
            if (safQueueBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFQueue", safQueueName).getMessage());
            }

            safImportedDestinationsBean.destroySAFQueue(safQueueBean);
         }

         success = true;
      } catch (Exception var12) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFQueue", safQueueName).getMessage(), var12);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safQueueName, success);
         }

      }

   }

   public void createSAFRemoteContext(String resourceName, String safRemoteContextName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safRemoteContextName", safRemoteContextName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createSAFRemoteContext(mydomain, resourceName, safRemoteContextName, modifier);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            SAFRemoteContextBean safRemoteContextBean = jmsBean.createSAFRemoteContext(safRemoteContextName);
            if (modifier != null) {
               modifier.modify(safRemoteContextBean);
            }
         }

         success = true;
      } catch (Exception var11) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFRemoteContext", safRemoteContextName).getMessage(), var11);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safRemoteContextName, success);
         }

      }

   }

   public void deleteSAFRemoteContext(String resourceName, String safRemoteContextName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safRemoteContextName", safRemoteContextName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteSAFRemoteContext(mydomain, resourceName, safRemoteContextName);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            SAFRemoteContextBean safRemoteContextBean = jmsBean.lookupSAFRemoteContext(safRemoteContextName);
            if (safRemoteContextBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFRemoteContext", safRemoteContextName).getMessage());
            }

            jmsBean.destroySAFRemoteContext(safRemoteContextBean);
         }

         success = true;
      } catch (Exception var10) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFRemoteContext", safRemoteContextName).getMessage(), var10);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safRemoteContextName, success);
         }

      }

   }

   public void createSAFTopic(String resourceName, String safImportedDestinationsName, String safTopicName, String safTopicRemoteJNDIName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safImportedDestinationsName", safImportedDestinationsName), new AbstractMap.SimpleImmutableEntry("safTopicName", safTopicName), new AbstractMap.SimpleImmutableEntry("safTopicRemoteJNDIName", safTopicRemoteJNDIName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createSAFTopic(mydomain, resourceName, safImportedDestinationsName, safTopicName, safTopicRemoteJNDIName, modifier);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            SAFImportedDestinationsBean safImportedDestinations = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
            if (safImportedDestinations == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFImportedDestinations", safImportedDestinationsName).getMessage());
            }

            SAFTopicBean safTopicBean = safImportedDestinations.createSAFTopic(safTopicName);
            safTopicBean.setRemoteJNDIName(safTopicRemoteJNDIName);
            if (modifier != null) {
               modifier.modify(safTopicBean);
            }
         }

         success = true;
      } catch (Exception var14) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFTopic", safTopicName).getMessage(), var14);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safTopicName, success);
         }

      }

   }

   public void deleteSAFTopic(String resourceName, String safImportedDestinationsName, String safTopicName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("safImportedDestinationsName", safImportedDestinationsName), new AbstractMap.SimpleImmutableEntry("safTopicName", safTopicName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteSAFTopic(mydomain, resourceName, safImportedDestinationsName, safTopicName);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            SAFImportedDestinationsBean safImportedDestinationsBean = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
            if (safImportedDestinationsBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFImportedDestinations", safImportedDestinationsName).getMessage());
            }

            SAFTopicBean safTopicBean = safImportedDestinationsBean.lookupSAFTopic(safTopicName);
            if (safTopicBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFTopic", safTopicName).getMessage());
            }

            safImportedDestinationsBean.destroySAFTopic(safTopicBean);
         }

         success = true;
      } catch (Exception var12) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "SAFTopic", safTopicName).getMessage(), var12);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, safTopicName, success);
         }

      }

   }

   public void createTemplate(String resourceName, String templateName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("templateName", templateName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createTemplate(mydomain, resourceName, templateName, modifier);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            TemplateBean templateBean = jmsBean.createTemplate(templateName);
            if (modifier != null) {
               modifier.modify(templateBean);
            }
         }

         success = true;
      } catch (Exception var11) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Template", templateName).getMessage(), var11);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, templateName, success);
         }

      }

   }

   public void deleteTemplate(String resourceName, String templateName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("templateName", templateName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteTemplate(mydomain, resourceName, templateName);
         } else {
            JMSBean jmsBean = this.getJMSBean(this.getRgOrRgt(mydomain), resourceName);
            TemplateBean templateBean = jmsBean.lookupTemplate(templateName);
            if (templateBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Template", templateName).getMessage());
            }

            jmsBean.destroyTemplate(templateBean);
         }

         success = true;
      } catch (Exception var10) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Template", templateName).getMessage(), var10);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, templateName, success);
         }

      }

   }

   public void createTopic(String resourceName, String jmsServerName, String topicName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("topicName", topicName), new AbstractMap.SimpleImmutableEntry("jmsServerName", jmsServerName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createTopic(mydomain, resourceName, jmsServerName, topicName, jndiName, modifier);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            TopicBean topicBean = jmsBean.createTopic(topicName);
            topicBean.setJNDIName(jndiName);
            if (modifier != null) {
               modifier.modify(topicBean);
            }

            if (jmsServerName.equalsIgnoreCase("*Default Targeting Enabled*")) {
               topicBean.setDefaultTargetingEnabled(true);
            } else {
               JMSServerMBean jmsServer = this.findJMSServer(template, jmsServerName);
               String subDeploymentName = topicBean.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = topicName;
               }

               SubDeploymentMBean subDeployment = this.findOrCreateSubDeployment(jmsResource, subDeploymentName);
               subDeployment.addTarget(jmsServer);
            }
         }

         success = true;
      } catch (Exception var18) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Topic", topicName).getMessage(), var18);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, topicName, success);
         }

      }

   }

   public void deleteTopic(String resourceName, String topicName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("topicName", topicName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteTopic(mydomain, resourceName, topicName);
         } else {
            this.deleteDestination(mydomain, resourceName, topicName, "Topic");
         }

         success = true;
      } catch (Exception var9) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "Topic", topicName).getMessage(), var9);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, topicName, success);
         }

      }

   }

   public void deploySAFAgent(String safAgentName, String targetName) throws JMSException {
      if (this.scopeType != IJMSModuleHelper.ScopeType.DOMAIN) {
         throw new UnsupportedOperationException(JMSExceptionLogger.logInvalidDeploymentInRGTLoggable("IJMSModuleHelper.deploySAFAgent").getMessage());
      } else {
         this.validateParameters(new AbstractMap.SimpleImmutableEntry("safAgentName", safAgentName));
         this.validateTargetName(targetName);
         DomainMBean mydomain = this.getDomainMBean();
         boolean success = false;

         try {
            JMSModuleHelper.deploySAFAgent(mydomain, safAgentName, targetName);
            success = true;
         } catch (Exception var9) {
            throw new JMSException("ERROR: failed to deploy SAFAgent " + safAgentName, var9);
         } finally {
            if (this.domain == null) {
               this.endEditSession(this.manager, safAgentName, success);
            }

         }

      }
   }

   public void undeploySAFAgent(String safAgentName) throws JMSException {
      if (this.scopeType != IJMSModuleHelper.ScopeType.DOMAIN) {
         throw new UnsupportedOperationException(JMSExceptionLogger.logInvalidDeploymentInRGTLoggable("IJMSModuleHelper.undeploySAFAgent").getMessage());
      } else {
         this.validateParameters(new AbstractMap.SimpleImmutableEntry("safAgentName", safAgentName));
         DomainMBean mydomain = this.getDomainMBean();
         boolean success = false;

         try {
            JMSModuleHelper.undeploySAFAgent(mydomain, safAgentName);
            success = true;
         } catch (Exception var8) {
            throw new JMSException("ERROR: failed to deploy SAFAgent " + safAgentName, var8);
         } finally {
            if (this.domain == null) {
               this.endEditSession(this.manager, safAgentName, success);
            }

         }

      }
   }

   public boolean isTargetInDeploymentScope(ConfigurationMBean configBean, WebLogicMBean deploymentScope) {
      return JMSModuleHelper.isTargetInDeploymentScope(configBean, deploymentScope);
   }

   public String uddMemberName(String jmsServerName, String name) {
      return JMSModuleHelper.uddMemberName(jmsServerName, name);
   }

   public String uddMemberJNDIName(String jmsServerName, String name) {
      return JMSModuleHelper.uddMemberJNDIName(jmsServerName, name);
   }

   public String[] uddReturnJMSServers(JMSSystemResourceMBean jmsSystemResourceMBean, UniformDistributedDestinationBean uddMBean) {
      if (jmsSystemResourceMBean != null && uddMBean != null) {
         try {
            DomainMBean mydomain = this.getReadOnlyDomainMBean();
            if (uddMBean.isDefaultTargetingEnabled()) {
               if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
                  HashMap fillMe = new HashMap();
                  JMSModuleHelper.uddFillWithMyTargets(fillMe, mydomain, (TargetMBean[])jmsSystemResourceMBean.getTargets(), jmsSystemResourceMBean, "Uniform Distributed Queue/Topic", uddMBean.getName(), false);
                  return (String[])((String[])fillMe.keySet().toArray(new String[0]));
               } else {
                  Set fillMe = new HashSet();
                  boolean isRGTDeployment = AppDeploymentHelper.isDeployedThroughRGT(jmsSystemResourceMBean);
                  JMSServerMBean[] candidateJMSServers = JMSModuleHelper.getCandidateJMSServers(mydomain, JMSModuleHelper.getDeploymentScope((BasicDeploymentMBean)jmsSystemResourceMBean), "Distributed", isRGTDeployment);
                  this.uddFillWithMyTargets(fillMe, candidateJMSServers, jmsSystemResourceMBean);
                  return (String[])fillMe.toArray(new String[0]);
               }
            } else {
               String subDeploymentName = uddMBean.getSubDeploymentName();
               SubDeploymentMBean subDeploymentBean = jmsSystemResourceMBean.lookupSubDeployment(subDeploymentName);
               return this.uddReturnJMSServers(subDeploymentBean);
            }
         } catch (JMSException var7) {
            JMSExceptionLogger.logFindFailedLoggable();
            return null;
         }
      } else {
         JMSExceptionLogger.logInvalidParameterLoggable("JMSSystemResource or UniformDistributedDestinationBean");
         return null;
      }
   }

   public String[] uddReturnJMSServers(SubDeploymentMBean subDeploymentBean) {
      try {
         DomainMBean mydomain = this.getReadOnlyDomainMBean();
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            return JMSModuleHelper.uddReturnJMSServers(mydomain, subDeploymentBean, subDeploymentBean.getName());
         } else {
            Set fillMe = new HashSet();
            this.uddFillWithMyTargets(fillMe, subDeploymentBean.getTargets(), (BasicDeploymentMBean)subDeploymentBean.getParent());
            return (String[])fillMe.toArray(new String[1]);
         }
      } catch (JMSException var4) {
         JMSExceptionLogger.logFindFailedLoggable();
         return null;
      }
   }

   public void createDestinationKey(String resourceName, String destinationKeyName, String property, String keyType, String sortOrder, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("destinationKeyName", destinationKeyName), new AbstractMap.SimpleImmutableEntry("property", property), new AbstractMap.SimpleImmutableEntry("keyType", keyType), new AbstractMap.SimpleImmutableEntry("sortOrder", sortOrder));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createDestinationKey(mydomain, resourceName, destinationKeyName, property, keyType, sortOrder, modifier);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            DestinationKeyBean destinationKeyBean = jmsBean.createDestinationKey(destinationKeyName);
            destinationKeyBean.setProperty(property);
            destinationKeyBean.setKeyType(keyType);
            destinationKeyBean.setSortOrder(sortOrder);
            if (modifier != null) {
               modifier.modify(destinationKeyBean);
            }
         }

         success = true;
      } catch (Exception var16) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "DestinationKey", destinationKeyName).getMessage(), var16);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, destinationKeyName, success);
         }

      }

   }

   public void deleteDestinationKey(String resourceName, String destinationKeyName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("destinationKeyName", destinationKeyName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteDestinationKey(mydomain, resourceName, destinationKeyName);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            DestinationKeyBean destinationKeyBean = jmsBean.lookupDestinationKey(destinationKeyName);
            if (destinationKeyBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "DestinationKey", destinationKeyName).getMessage());
            }

            jmsBean.destroyDestinationKey(destinationKeyBean);
         }

         success = true;
      } catch (Exception var12) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "DestinationKey", destinationKeyName).getMessage(), var12);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, destinationKeyName, success);
         }

      }

   }

   public void createForeignServer(String resourceName, String foreignServerName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("foreignServerName", foreignServerName), new AbstractMap.SimpleImmutableEntry("targetName", targetName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createForeignServer(mydomain, resourceName, foreignServerName, targetName, modifier);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            ForeignServerBean fsBean = jmsBean.createForeignServer(foreignServerName);
            if (modifier != null) {
               modifier.modify(fsBean);
            }

            if (targetName.equalsIgnoreCase("*Default Targeting Enabled*")) {
               fsBean.setDefaultTargetingEnabled(true);
            } else {
               TargetMBean[] targetMBeans = this.targetNames2TargetMBeans(jmsResource, targetName);
               String subDeploymentName = fsBean.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = foreignServerName;
               }

               SubDeploymentMBean subDeployment = this.findOrCreateSubDeployment(jmsResource, subDeploymentName);
               subDeployment.setTargets(targetMBeans);
            }
         }

         success = true;
      } catch (Exception var17) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignServer", foreignServerName).getMessage(), var17);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, foreignServerName, success);
         }

      }

   }

   public void deleteForeignServer(String resourceName, String foreignServerName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("foreignServerName", foreignServerName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteForeignServer(mydomain, resourceName, foreignServerName);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignServer", foreignServerName).getMessage());
            }

            ForeignConnectionFactoryBean[] fcs = foreignServerBean.getForeignConnectionFactories();
            if (fcs != null) {
               for(int i = 0; i < fcs.length; ++i) {
                  foreignServerBean.destroyForeignConnectionFactory(fcs[i]);
               }
            }

            ForeignDestinationBean[] fds = foreignServerBean.getForeignDestinations();
            if (fds != null) {
               for(int i = 0; i < fds.length; ++i) {
                  foreignServerBean.destroyForeignDestination(fds[i]);
               }
            }

            jmsBean.destroyForeignServer(foreignServerBean);
         }

         success = true;
      } catch (Exception var15) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignServer", foreignServerName).getMessage(), var15);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, foreignServerName, success);
         }

      }

   }

   public void createForeignConnectionFactory(String resourceName, String foreignServerName, String foreignConnectionFactoryName, String localJNDIName, String remoteJNDIName, String username, String password, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("foreignServerName", foreignServerName), new AbstractMap.SimpleImmutableEntry("foreignConnectionFactoryName", foreignConnectionFactoryName), new AbstractMap.SimpleImmutableEntry("localJNDIName", localJNDIName), new AbstractMap.SimpleImmutableEntry("remoteJNDIName", remoteJNDIName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createForeignConnectionFactory(mydomain, resourceName, foreignServerName, foreignConnectionFactoryName, localJNDIName, remoteJNDIName, username, password, modifier);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignServer", foreignServerName).getMessage());
            }

            ForeignConnectionFactoryBean fcBean = foreignServerBean.createForeignConnectionFactory(foreignConnectionFactoryName);
            fcBean.setLocalJNDIName(localJNDIName);
            fcBean.setRemoteJNDIName(remoteJNDIName);
            if (username != null && !username.equals("")) {
               fcBean.setUsername(username);
            }

            if (password != null && !password.equals("")) {
               fcBean.setPassword(password);
            }

            if (modifier != null) {
               modifier.modify(fcBean);
            }
         }

         success = true;
      } catch (Exception var19) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignConnectionFactory", foreignConnectionFactoryName).getMessage(), var19);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, foreignServerName, success);
         }

      }

   }

   public void deleteForeignConnectionFactory(String resourceName, String foreignServerName, String foreignConnectionFactoryName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("foreignServerName", foreignServerName), new AbstractMap.SimpleImmutableEntry("foreignConnectionFactoryName", foreignConnectionFactoryName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteForeignConnectionFactory(mydomain, resourceName, foreignServerName, foreignConnectionFactoryName);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignServer", foreignServerName).getMessage());
            }

            ForeignConnectionFactoryBean fcBean = foreignServerBean.lookupForeignConnectionFactory(foreignConnectionFactoryName);
            if (fcBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignConnectionFactory", foreignConnectionFactoryName).getMessage());
            }

            foreignServerBean.destroyForeignConnectionFactory(fcBean);
         }

         success = true;
      } catch (Exception var14) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignConnectionFactory", foreignConnectionFactoryName).getMessage(), var14);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, foreignServerName, success);
         }

      }

   }

   public void createForeignDestination(String resourceName, String foreignServerName, String foreignDestinationName, String localJNDIName, String remoteJNDIName, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("foreignServerName", foreignServerName), new AbstractMap.SimpleImmutableEntry("foreignDestinationName", foreignDestinationName), new AbstractMap.SimpleImmutableEntry("localJNDIName", localJNDIName), new AbstractMap.SimpleImmutableEntry("remoteJNDIName", remoteJNDIName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.createForeignDestination(mydomain, resourceName, foreignServerName, foreignDestinationName, localJNDIName, remoteJNDIName);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignServer", foreignServerName).getMessage());
            }

            ForeignDestinationBean fdBean = foreignServerBean.createForeignDestination(foreignDestinationName);
            fdBean.setLocalJNDIName(localJNDIName);
            fdBean.setRemoteJNDIName(remoteJNDIName);
            if (modifier != null) {
               modifier.modify(fdBean);
            }
         }

         success = true;
      } catch (Exception var17) {
         throw new JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignDestination", foreignDestinationName).getMessage(), var17);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, foreignServerName, success);
         }

      }

   }

   public void deleteForeignDestination(String resourceName, String foreignServerName, String foreignDestinationName) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("foreignServerName", foreignServerName), new AbstractMap.SimpleImmutableEntry("foreignDestinationName", foreignDestinationName));
      DomainMBean mydomain = this.getDomainMBean();
      boolean success = false;

      try {
         if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
            JMSModuleHelper.deleteForeignDestination(mydomain, resourceName, foreignServerName, foreignDestinationName);
         } else {
            ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
            JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
            JMSBean jmsBean = jmsResource.getJMSResource();
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignServer", foreignServerName).getMessage());
            }

            ForeignDestinationBean fdBean = foreignServerBean.lookupForeignDestination(foreignDestinationName);
            if (fdBean == null) {
               throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignDestination", foreignDestinationName).getMessage());
            }

            foreignServerBean.destroyForeignDestination(fdBean);
         }

         success = true;
      } catch (Exception var14) {
         throw new JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), "ForeignDestination", foreignDestinationName).getMessage(), var14);
      } finally {
         if (this.domain == null) {
            this.endEditSession(this.manager, foreignServerName, success);
         }

      }

   }

   public DestinationBean[] findAllInheritedDestinations(String templateName, JMSBean module) {
      return JMSModuleHelper.findAllInheritedDestinations(templateName, module);
   }

   public QueueBean[] findAllInheritedQueueBeans(String templateName, JMSBean module) {
      return JMSModuleHelper.findAllInheritedQueueBeans(templateName, module);
   }

   public TopicBean[] findAllInheritedTopicBeans(String templateName, JMSBean module) {
      return JMSModuleHelper.findAllInheritedTopicBeans(templateName, module);
   }

   public void findAndModifyEntity(String resourceName, String entityName, String entityType, JMSNamedEntityModifier modifier) throws JMSException {
      this.validateParameters(new AbstractMap.SimpleImmutableEntry("resourceName", resourceName), new AbstractMap.SimpleImmutableEntry("entityName", entityName), new AbstractMap.SimpleImmutableEntry("entityType", entityType));
      DomainMBean mydomain = this.getDomainMBean();
      if (modifier == null) {
         throw new JMSException(JMSExceptionLogger.logInvalidModuleEntityModifierLoggable(this.getScopeAsString(mydomain), entityType, entityName).getMessage());
      } else {
         boolean success = false;

         try {
            if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
               JMSModuleHelper.findAndModifyEntity(mydomain, resourceName, entityName, entityType, modifier);
            } else {
               ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
               JMSSystemResourceMBean jmsResource = this.findJMSSystemResource(template, resourceName);
               JMSBean jmsBean = jmsResource.getJMSResource();
               Class jmsBeanClass = jmsBean.getClass();
               String finderName = "lookup" + entityType;
               Method finderMethod = null;
               Class[] parameterTypes = new Class[]{String.class};
               Object[] arguments = new Object[]{entityName};
               NamedEntityBean entity = null;

               try {
                  finderMethod = jmsBeanClass.getMethod(finderName, parameterTypes);
                  entity = (NamedEntityBean)finderMethod.invoke(jmsBean, arguments);
               } catch (Exception var21) {
                  throw new JMSException(JMSExceptionLogger.logCannotFindAndModifyEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), entityType, entityName).getMessage(), var21);
               }

               if (entity == null) {
                  throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), entityType, entityName).getMessage());
               }

               modifier.modify(entity);
            }

            success = true;
         } catch (Exception var22) {
            throw new JMSException(JMSExceptionLogger.logCannotFindAndModifyEntityFromJMSSystemResourceLoggable(this.getScopeAsString(mydomain, resourceName), entityType, entityName).getMessage(), var22);
         } finally {
            if (this.domain == null) {
               this.endEditSession(this.manager, entityName, success);
            }

         }

      }
   }

   @SafeVarargs
   private final void validateParameters(AbstractMap.SimpleImmutableEntry... parameters) throws JMSException {
      AbstractMap.SimpleImmutableEntry[] var2 = parameters;
      int var3 = parameters.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         AbstractMap.SimpleImmutableEntry parameter = var2[var4];
         if (parameter.getValue() == null || ((String)parameter.getValue()).trim().equals("")) {
            throw new JMSException(JMSExceptionLogger.logInvalidParameterLoggable((String)parameter.getKey()).getMessage());
         }
      }

   }

   private DomainMBean getDomainMBean() throws JMSException {
      return this.domain != null ? this.domain : this.beginEditSession(this.manager);
   }

   private DomainMBean getReadOnlyDomainMBean() throws JMSException {
      if (this.domain != null) {
         return this.domain;
      } else {
         try {
            return JMSEditHelper.getDomain(this.ctx);
         } catch (Exception var2) {
            throw new JMSException("ERROR: cannot get domain", var2);
         }
      }
   }

   private String getScopeAsString(DomainMBean domainName, String resourceName) {
      return this.getScopeAsString(domainName) + "/" + resourceName;
   }

   private JMSSystemResourceMBean findJMSSystemResource(ResourceGroupTemplateMBean template, String resourceName) throws JMSException {
      JMSSystemResourceMBean jmsResource = template.lookupJMSSystemResource(resourceName);
      if (jmsResource == null) {
         throw new JMSException(JMSExceptionLogger.logCouldNotFindLoggable("JMS Resource", resourceName, template.getName()).getMessage());
      } else {
         return jmsResource;
      }
   }

   private PersistentStoreMBean findStore(DomainMBean mydomain, String storeName) throws JMSException {
      PersistentStoreMBean store = null;
      if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
         if (storeName == null || storeName.trim().equals("")) {
            return null;
         }

         store = mydomain.lookupFileStore(storeName);
         if (store == null) {
            store = mydomain.lookupJDBCStore(storeName);
         }

         if (store == null) {
            store = mydomain.lookupReplicatedStore(storeName);
         }
      } else {
         if (storeName == null || storeName.trim().equals("")) {
            throw new JMSException(JMSExceptionLogger.logInvalidParameterLoggable("storeName").getMessage());
         }

         ResourceGroupTemplateMBean template = this.getRgOrRgt(mydomain);
         store = template.lookupFileStore(storeName);
         if (store == null) {
            store = template.lookupJDBCStore(storeName);
         }
      }

      if (store == null) {
         throw new JMSException(JMSExceptionLogger.logResourceAbsentLoggable("Persistent Store", storeName, this.getScopeAsString(mydomain)).getMessage());
      } else {
         return (PersistentStoreMBean)store;
      }
   }

   private JMSServerMBean findJMSServer(ResourceGroupTemplateMBean template, String jmsServerName) throws JMSException {
      JMSServerMBean jmsServer = template.lookupJMSServer(jmsServerName);
      if (jmsServer == null) {
         throw new JMSException(JMSExceptionLogger.logCouldNotFindLoggable("JMS Server", jmsServerName, template.getName()).getMessage());
      } else {
         return jmsServer;
      }
   }

   private JMSBean getJMSBean(ResourceGroupTemplateMBean template, String resourceName) throws JMSException {
      JMSSystemResourceMBean resource = this.findJMSSystemResource(template, resourceName);
      return resource.getJMSResource();
   }

   private DomainMBean beginEditSession(ConfigurationManagerMBean manager) throws JMSException {
      DomainMBean domain = null;

      try {
         domain = manager.startEdit(-1, -1);
         return domain;
      } catch (EditTimedOutException var4) {
         throw new JMSException("ERROR: Unable to start the edit session", var4);
      }
   }

   private void endEditSession(ConfigurationManagerMBean manager, String entityName, boolean result) throws JMSException {
      if (!result) {
         manager.cancelEdit();
      } else {
         try {
            activateEdit(manager);
         } catch (JMSException var6) {
            manager.cancelEdit();
            Throwable cause = var6.getCause();
            throw new JMSException("ERROR: Could not activate update for the entity " + entityName + ". The edit session was cancelled and the changes made in this edit session were discarded. \nREASON: ", (Throwable)(cause == null ? var6 : cause));
         }
      }

   }

   private static void activateEdit(ConfigurationManagerMBean configurationManager) throws JMSException {
      try {
         configurationManager.save();
      } catch (NotEditorException var5) {
         throw new JMSException("ERROR: Not editor while saving edit", var5);
      } catch (ValidationException var6) {
         throw new JMSException("ERROR: Validation error while saving edit", var6);
      }

      ActivationTaskMBean atm;
      try {
         atm = configurationManager.activate(-1L);
      } catch (NotEditorException var3) {
         throw new JMSException("ERROR: Edit session activation failed", var3);
      } catch (Throwable var4) {
         var4.printStackTrace();
         throw new JMSException(var4);
      }

      Exception e = atm.getError();
      if (e != null) {
         throw new JMSException("ERROR: Edit session activation failed. Reason:" + e.getMessage(), e);
      }
   }

   private TargetMBean[] targetNames2TargetMBeans(DomainMBean mydomain, String targetName) throws JMSException {
      String[] targetNames = targetName.split(",");
      TargetMBean[] targetMBeans = new TargetMBean[targetNames.length];

      for(int i = 0; i < targetNames.length; ++i) {
         targetMBeans[i] = this.findTarget(mydomain, targetNames[i]);
      }

      return targetMBeans;
   }

   private TargetMBean[] targetNames2TargetMBeans(JMSSystemResourceMBean resource, String targetName) throws JMSException {
      String[] targetNames = targetName.split(",");
      TargetMBean[] targetMBeans = new TargetMBean[targetNames.length];

      for(int i = 0; i < targetNames.length; ++i) {
         ResourceGroupTemplateMBean template = (ResourceGroupTemplateMBean)resource.getParent();
         targetMBeans[i] = this.findTarget(template, targetNames[i]);
      }

      return targetMBeans;
   }

   private TargetMBean findTarget(DomainMBean mydomain, String targetName) throws JMSException {
      TargetMBean target = mydomain.lookupMigratableTarget(targetName);
      if (target == null) {
         target = mydomain.lookupCluster(targetName);
         if (target == null) {
            target = mydomain.lookupServer(targetName);
            if (target == null) {
               target = mydomain.lookupJMSServer(targetName);
               if (target == null) {
                  target = mydomain.lookupSAFAgent(targetName);
                  if (target == null) {
                     throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(this.domain.getName(), "MigratableTarget/Cluster/Server/JMSServer/SAFAgent", targetName).getMessage());
                  }
               }
            }
         }
      }

      return (TargetMBean)target;
   }

   private TargetMBean findTarget(ResourceGroupTemplateMBean template, String targetName) throws JMSException {
      TargetMBean target = template.lookupJMSServer(targetName);
      if (target == null) {
         target = template.lookupSAFAgent(targetName);
      }

      if (target == null) {
         throw new JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(template.getName(), "JMS Server/SAF Agent", targetName).getMessage());
      } else {
         return (TargetMBean)target;
      }
   }

   private SubDeploymentMBean findOrCreateSubDeployment(JMSSystemResourceMBean resource, String name) {
      SubDeploymentMBean subDeployment = resource.lookupSubDeployment(name);
      if (subDeployment == null) {
         subDeployment = resource.createSubDeployment(name);
      }

      return subDeployment;
   }

   private void validateTargetName(String targetName) throws JMSException {
      String error = null;
      String scope = null;
      if (this.scopeType == IJMSModuleHelper.ScopeType.DOMAIN) {
         scope = "Domain";
         if (targetName == null) {
            error = JMSExceptionLogger.logInvalidTargetNameLoggable(targetName, scope).getMessage();
         }
      } else {
         scope = "Resource Group or Resource Group Template";
         if (targetName != null) {
            error = JMSExceptionLogger.logInvalidTargetNameLoggable(targetName, scope).getMessage();
         }
      }

      if (error != null) {
         throw new JMSException(error);
      }
   }

   private void uddFillWithMyTargets(Set fillMe, TargetMBean[] targetBeans, BasicDeploymentMBean basic) throws JMSException {
      if (targetBeans != null && targetBeans.length != 0) {
         Set candinateJMSServers = new HashSet();
         TargetMBean[] var5 = targetBeans;
         int var6 = targetBeans.length;

         PersistentStoreMBean enClosingScope;
         for(int var7 = 0; var7 < var6; ++var7) {
            TargetMBean targetBean = var5[var7];
            if (!(targetBean instanceof JMSServerMBean)) {
               throw new IllegalArgumentException(JMSExceptionLogger.logInvalidTargetScopeLoggable(targetBean.getName()).getMessage());
            }

            enClosingScope = ((JMSServerMBean)targetBean).getPersistentStore();
            if (enClosingScope != null && enClosingScope.getDistributionPolicy().equalsIgnoreCase("Distributed") && !candinateJMSServers.add((JMSServerMBean)targetBean)) {
               throw new IllegalArgumentException(JMSExceptionLogger.logUddTargetOverLapLoggable(targetBean.getName()).getMessage());
            }
         }

         WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(basic);
         if (candinateJMSServers.isEmpty()) {
            JMSLogger.logMatchingJMSServerNotFound("Distributed", "Uniform Distributed Queue/Topic", "", JMSModuleHelper.getConfigMBeanShortName(deploymentScope, basic), JMSModuleHelper.getDeploymentScopeAsString(basic));
         }

         if (candinateJMSServers.size() > 1) {
            throw new IllegalArgumentException(JMSExceptionLogger.logMultipleCandidateJMSServersLoggable("Distributed", "Uniform Distributed Queue/Topic", "", JMSModuleHelper.getConfigMBeanShortName(deploymentScope, basic), JMSModuleHelper.getDeploymentScopeAsString(basic), fillMe.toString()).getMessage());
         } else {
            JMSServerMBean candinateJMSServer = (JMSServerMBean)candinateJMSServers.toArray()[0];
            Set targetSet = new HashSet();
            DomainMBean mydomain = this.getReadOnlyDomainMBean();
            enClosingScope = null;
            if (this.scopeType == IJMSModuleHelper.ScopeType.RGT) {
               throw new IllegalArgumentException(JMSExceptionLogger.logInvalidScope("JMSPartitionModuleHelperImpl.uddReturnJMSServers", "Resource Group Template"));
            } else {
               TargetMBean[] targets;
               TargetMBean[] var13;
               int var14;
               int var15;
               TargetMBean target;
               if (this.scopeType == IJMSModuleHelper.ScopeType.RG) {
                  ResourceGroupMBean rg = null;
                  PartitionMBean partition = null;
                  String enClosingScope;
                  if (PartitionUtils.isDomain(this.partitionName)) {
                     rg = mydomain.lookupResourceGroup(this.scopeName);
                     enClosingScope = "WebLogic domain=" + mydomain.getName();
                  } else {
                     partition = mydomain.lookupPartition(this.partitionName);
                     if (partition == null) {
                        enClosingScope = "WebLogic domain=" + mydomain.getName();
                        throw new IllegalArgumentException(JMSExceptionLogger.logPartitionAbsent(this.partitionName, enClosingScope));
                     }

                     rg = partition.lookupResourceGroup(this.scopeName);
                     enClosingScope = "WebLogic domain=" + mydomain.getName() + "/Partition=" + this.partitionName;
                  }

                  if (rg == null) {
                     throw new IllegalArgumentException(JMSExceptionLogger.logResourceGroupAbsentLoggable("Resource Group", this.scopeName, enClosingScope).getMessage());
                  }

                  if (rg.isUseDefaultTarget() && partition != null) {
                     targets = partition.getDefaultTargets();
                  } else {
                     targets = rg.getTargets();
                  }

                  var13 = targets;
                  var14 = targets.length;

                  for(var15 = 0; var15 < var14; ++var15) {
                     target = var13[var15];
                     targetSet.add(target);
                  }
               }

               Iterator setIt = targetSet.iterator();

               while(true) {
                  TargetMBean targetBean;
                  do {
                     if (!setIt.hasNext()) {
                        return;
                     }

                     targetBean = (TargetMBean)setIt.next();
                  } while(!(targetBean instanceof VirtualTargetMBean));

                  targets = ((VirtualTargetMBean)targetBean).getTargets();
                  var13 = targets;
                  var14 = targets.length;

                  for(var15 = 0; var15 < var14; ++var15) {
                     target = var13[var15];
                     String jmsServerInstanceName;
                     if (!(target instanceof ClusterMBean)) {
                        jmsServerInstanceName = this.getUndecoratedDistributedInstanceName(candinateJMSServer, (String)null);
                        fillMe.add(jmsServerInstanceName);
                     } else {
                        ClusterMBean cluster = (ClusterMBean)target;
                        if (cluster.getDynamicServers().getDynamicClusterSize() > 0) {
                           for(int i = 0; i < cluster.getDynamicServers().getDynamicClusterSize(); ++i) {
                              String serverInstanceName = cluster.getDynamicServers().getServerNamePrefix() + (cluster.getDynamicServers().getServerNameStartingIndex() + i);
                              jmsServerInstanceName = this.getUndecoratedDistributedInstanceName(candinateJMSServer, serverInstanceName);
                              fillMe.add(jmsServerInstanceName);
                           }
                        }

                        ServerMBean[] memberServers = cluster.getServers();

                        for(int i = 0; i < memberServers.length; ++i) {
                           jmsServerInstanceName = this.getUndecoratedDistributedInstanceName(candinateJMSServer, memberServers[i].getName());
                           fillMe.add(jmsServerInstanceName);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private String getUndecoratedDistributedInstanceName(ConfigurationMBean bean, String serverInstanceName) {
      String beanName = bean.getName();
      beanName = PartitionUtils.stripDecoratedPartitionName(this.partitionName, beanName);
      if (serverInstanceName != null && !serverInstanceName.isEmpty()) {
         beanName = beanName + "@" + serverInstanceName;
      }

      return beanName;
   }
}
