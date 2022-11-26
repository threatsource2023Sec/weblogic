package weblogic.jms.extensions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.naming.Context;
import weblogic.application.ApplicationContextInternal;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.j2ee.descriptor.wl.DistributedQueueBean;
import weblogic.j2ee.descriptor.wl.DistributedTopicBean;
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
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSEditHelper;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.PartitionUtils;
import weblogic.jms.module.JMSModuleBeanHelper;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PersistentStoreMBean;
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
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class JMSModuleHelper extends JMSRuntimeHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static DestinationBean findDestinationBean(String name, JMSBean module) {
      return JMSModuleBeanHelper.findDestinationBean(name, module);
   }

   public static DestinationBean[] findAllInheritedDestinations(String templateName, JMSBean module) {
      QueueBean[] queues = findAllInheritedQueueBeans(templateName, module);
      TopicBean[] topics = findAllInheritedTopicBeans(templateName, module);
      ArrayList list1 = new ArrayList();
      int i;
      if (queues != null) {
         for(i = 0; i < queues.length; ++i) {
            list1.add(queues[i]);
         }
      }

      if (topics != null) {
         for(i = 0; i < topics.length; ++i) {
            list1.add(topics[i]);
         }
      }

      return (DestinationBean[])((DestinationBean[])list1.toArray(new DestinationBean[0]));
   }

   public static QueueBean[] findAllInheritedQueueBeans(String templateName, JMSBean module) {
      ArrayList list1 = new ArrayList();
      QueueBean[] queues = module.getQueues();

      for(int i = 0; i < queues.length; ++i) {
         TemplateBean template = queues[i].getTemplate();
         String tmplName = template == null ? null : template.getName();
         if (tmplName != null && tmplName.equals(templateName)) {
            list1.add(queues[i]);
         }
      }

      return (QueueBean[])((QueueBean[])list1.toArray(new QueueBean[0]));
   }

   public static TopicBean[] findAllInheritedTopicBeans(String templateName, JMSBean module) {
      ArrayList list1 = new ArrayList();
      TopicBean[] topics = module.getTopics();

      for(int i = 0; i < topics.length; ++i) {
         TemplateBean template = topics[i].getTemplate();
         String tmplName = template == null ? null : template.getName();
         if (tmplName != null && tmplName.equals(templateName)) {
            list1.add(topics[i]);
         }
      }

      return (TopicBean[])((TopicBean[])list1.toArray(new TopicBean[0]));
   }

   public static JMSSystemResourceMBean findJMSSystemResource(Context ctx, String resourceName) throws JMSException {
      if (resourceName == null) {
         return null;
      } else {
         JMSSystemResourceMBean resource = null;
         ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
         DomainMBean domain = beginEditSession(manager);
         boolean success = false;
         resource = findJMSSystemResource(domain, resourceName);
         endEditSession(manager, resourceName, success);
         return resource;
      }
   }

   public static JMSSystemResourceMBean findJMSSystemResource(String name) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      return domain.lookupJMSSystemResource(name);
   }

   public static void createJMSSystemResource(Context ctx, String resourceName, String targetName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createJMSSystemResource(domain, resourceName, targetName);
         success = true;
      } finally {
         endEditSession(manager, resourceName, success);
      }

   }

   public static void createJMSSystemResource(DomainMBean domain, String resourceName, String targetName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (resourceName != null && !resourceName.trim().equals("")) {
         try {
            TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);
            if (!(targetMBeans[0] instanceof MigratableTargetMBean) && !(targetMBeans[0] instanceof JMSServerMBean)) {
               JMSSystemResourceMBean resource = domain.createJMSSystemResource(resourceName);
               resource.setTargets(targetMBeans);
            } else {
               throw new JMSException("ERROR: MigratableTarget and JMSServer cannot be set as target for JMSSystemResource");
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInDomainLoggable(domain.getName(), "JMSSystemResource", resourceName).getMessage(), var5);
         }
      } else {
         throw new JMSException("ERROR: resourceName cannot be null or empty");
      }
   }

   public static void deleteJMSSystemResource(Context ctx, String resourceName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteJMSSystemResource(domain, resourceName);
         success = true;
      } finally {
         endEditSession(manager, resourceName, success);
      }

   }

   public static void deleteJMSSystemResource(DomainMBean domain, String resourceName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
            domain.destroyJMSSystemResource(resource);
         } catch (Exception var3) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromDomainLoggable(domain.getName(), "JMSSystemResource", resourceName).getMessage(), var3);
         }
      }
   }

   public static void createTemplate(Context ctx, String resourceName, String templateName) throws JMSException {
      createTemplate((Context)ctx, resourceName, templateName, (JMSNamedEntityModifier)null);
   }

   public static void createTemplate(DomainMBean domain, String resourceName, String templateName) throws JMSException {
      createTemplate((DomainMBean)domain, resourceName, templateName, (JMSNamedEntityModifier)null);
   }

   public static void createTemplate(Context ctx, String resourceName, String templateName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createTemplate(domain, resourceName, templateName, modifier);
         success = true;
      } finally {
         endEditSession(manager, resourceName, success);
      }

   }

   public static void createTemplate(DomainMBean domain, String resourceName, String templateName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (templateName != null && !templateName.trim().equals("")) {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            TemplateBean templateBean = jmsBean.createTemplate(templateName);
            if (modifier != null) {
               modifier.modify(templateBean);
            }

         } catch (Exception var6) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "Template", templateName).getMessage(), var6);
         }
      } else {
         throw new JMSException("ERROR: templateName cannot be null or empty");
      }
   }

   public static void deleteTemplate(Context ctx, String resourceName, String templateName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteTemplate(domain, resourceName, templateName);
         success = true;
      } finally {
         endEditSession(manager, templateName, success);
      }

   }

   public static void deleteTemplate(DomainMBean domain, String resourceName, String templateName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            TemplateBean templateBean = jmsBean.lookupTemplate(templateName);
            if (templateBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "Template", templateName).getMessage());
            } else {
               jmsBean.destroyTemplate(templateBean);
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "Template", templateName).getMessage(), var5);
         }
      }
   }

   public static void createQuota(Context ctx, String resourceName, String quotaName, String targetName) throws JMSException {
      createQuota((Context)ctx, resourceName, quotaName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createQuota(DomainMBean domain, String resourceName, String quotaName, String targetName) throws JMSException {
      createQuota((DomainMBean)domain, resourceName, quotaName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createQuota(Context ctx, String resourceName, String quotaName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createQuota(domain, resourceName, quotaName, targetName, modifier);
         success = true;
      } finally {
         endEditSession(manager, quotaName, success);
      }

   }

   public static void createQuota(DomainMBean domain, String resourceName, String quotaName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (quotaName != null && !quotaName.trim().equals("")) {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            QuotaBean quotaBean = jmsBean.createQuota(quotaName);
            if (modifier != null) {
               modifier.modify(quotaBean);
            }

         } catch (Exception var7) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "Quota", quotaName).getMessage(), var7);
         }
      } else {
         throw new JMSException("ERROR: quotaName cannot be null or empty");
      }
   }

   public static void deleteQuota(Context ctx, String resourceName, String quotaName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteQuota(domain, resourceName, quotaName);
         success = true;
      } finally {
         endEditSession(manager, quotaName, success);
      }

   }

   public static void deleteQuota(DomainMBean domain, String resourceName, String quotaName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            QuotaBean quotaBean = jmsBean.lookupQuota(quotaName);
            if (quotaBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "Quota", quotaName).getMessage());
            } else {
               jmsBean.destroyQuota(quotaBean);
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "Quota", quotaName).getMessage(), var5);
         }
      }
   }

   public static void createDestinationKey(Context ctx, String resourceName, String destinationKeyName, String property, String keyType, String sortOrder) throws JMSException {
      createDestinationKey((Context)ctx, resourceName, destinationKeyName, property, keyType, sortOrder, (JMSNamedEntityModifier)null);
   }

   public static void createDestinationKey(DomainMBean domain, String resourceName, String destinationKeyName, String property, String keyType, String sortOrder) throws JMSException {
      createDestinationKey((DomainMBean)domain, resourceName, destinationKeyName, property, keyType, sortOrder, (JMSNamedEntityModifier)null);
   }

   public static void createDestinationKey(Context ctx, String resourceName, String destinationKeyName, String property, String keyType, String sortOrder, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createDestinationKey(domain, resourceName, destinationKeyName, property, keyType, sortOrder, modifier);
         success = true;
      } finally {
         endEditSession(manager, destinationKeyName, success);
      }

   }

   public static void createDestinationKey(DomainMBean domain, String resourceName, String destinationKeyName, String property, String keyType, String sortOrder, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (destinationKeyName != null && !destinationKeyName.trim().equals("")) {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            DestinationKeyBean destinationKeyBean = jmsBean.createDestinationKey(destinationKeyName);
            destinationKeyBean.setProperty(property);
            destinationKeyBean.setKeyType(keyType);
            destinationKeyBean.setSortOrder(sortOrder);
            if (modifier != null) {
               modifier.modify(destinationKeyBean);
            }

         } catch (Exception var9) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "DestinationKey", destinationKeyName).getMessage(), var9);
         }
      } else {
         throw new JMSException("ERROR: destinationKeyName cannot be null or empty");
      }
   }

   public static void deleteDestinationKey(Context ctx, String resourceName, String destinationKeyName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteDestinationKey(domain, resourceName, destinationKeyName);
         success = true;
      } finally {
         endEditSession(manager, destinationKeyName, success);
      }

   }

   public static void deleteDestinationKey(DomainMBean domain, String resourceName, String destinationKeyName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            DestinationKeyBean destinationKeyBean = jmsBean.lookupDestinationKey(destinationKeyName);
            if (destinationKeyBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DestinationKey", destinationKeyName).getMessage());
            } else {
               jmsBean.destroyDestinationKey(destinationKeyBean);
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "DestinationKey", destinationKeyName).getMessage(), var5);
         }
      }
   }

   public static void createConnectionFactory(Context ctx, String resourceName, String factoryName, String factoryJndiName, String targetName) throws JMSException {
      createConnectionFactory((Context)ctx, resourceName, factoryName, factoryJndiName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createConnectionFactory(DomainMBean domain, String resourceName, String factoryName, String factoryJndiName, String targetName) throws JMSException {
      createConnectionFactory((DomainMBean)domain, resourceName, factoryName, factoryJndiName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createConnectionFactory(Context ctx, String resourceName, String factoryName, String factoryJndiName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createConnectionFactory(domain, resourceName, factoryName, factoryJndiName, targetName, modifier);
         success = true;
      } finally {
         endEditSession(manager, resourceName, success);
      }

   }

   public static void createConnectionFactory(DomainMBean domain, String resourceName, String factoryName, String factoryJndiName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (factoryName != null && !factoryName.trim().equals("")) {
         try {
            TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);
            if (targetMBeans[0] instanceof MigratableTargetMBean) {
               throw new JMSException(JMSExceptionLogger.logInvalidSubDeploymentTargetLoggable(resourceName, targetName, "MigratableTargetMBean", factoryName).getMessage());
            } else {
               JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
               JMSBean jmsBean = resource.getJMSResource();
               JMSConnectionFactoryBean factory = jmsBean.createConnectionFactory(factoryName);
               factory.setJNDIName(factoryJndiName);
               if (modifier != null) {
                  modifier.modify(factory);
               }

               String subDeploymentName = factory.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = factoryName;
               }

               SubDeploymentMBean subDeployment = findOrCreateSubDeployment(resource, subDeploymentName);
               subDeployment.setTargets(targetMBeans);
            }
         } catch (Exception var12) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "ConnectionFactory", factoryName).getMessage(), var12);
         }
      } else {
         throw new JMSException("ERROR: factoryName cannot be null or empty");
      }
   }

   public static void deleteConnectionFactory(Context ctx, String resourceName, String factoryName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteConnectionFactory(domain, resourceName, factoryName);
         success = true;
      } finally {
         endEditSession(manager, factoryName, success);
      }

   }

   public static void deleteConnectionFactory(DomainMBean domain, String resourceName, String factoryName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            JMSConnectionFactoryBean factoryBean = jmsBean.lookupConnectionFactory(factoryName);
            if (factoryBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "ConnectionFactory", factoryName).getMessage());
            } else {
               jmsBean.destroyConnectionFactory(factoryBean);
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "ConnectionFactory", factoryName).getMessage(), var5);
         }
      }
   }

   public static void createQueue(Context ctx, String resourceName, String jmsServerName, String queueName, String jndiName) throws JMSException {
      createQueue((Context)ctx, resourceName, jmsServerName, queueName, jndiName, (JMSNamedEntityModifier)null);
   }

   public static void createQueue(DomainMBean domain, String resourceName, String jmsServerName, String queueName, String jndiName) throws JMSException {
      createQueue((DomainMBean)domain, resourceName, jmsServerName, queueName, jndiName, (JMSNamedEntityModifier)null);
   }

   public static void createQueue(Context ctx, String resourceName, String jmsServerName, String queueName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createQueue(domain, resourceName, jmsServerName, queueName, jndiName, modifier);
         success = true;
      } finally {
         endEditSession(manager, queueName, success);
      }

   }

   public static void createQueue(DomainMBean domain, String resourceName, String jmsServerName, String queueName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (queueName != null && !queueName.equals("")) {
         try {
            JMSServerMBean jmsServer = domain.lookupJMSServer(jmsServerName);
            if (jmsServer == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInDomainLoggable(domain.getName(), "JMSServer", jmsServerName).getMessage());
            } else {
               JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
               JMSBean jmsBean = resource.getJMSResource();
               QueueBean queueBean = jmsBean.createQueue(queueName);
               queueBean.setJNDIName(jndiName);
               if (modifier != null) {
                  modifier.modify(queueBean);
               }

               String subDeploymentName = queueBean.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = queueName;
               }

               SubDeploymentMBean subDeployment = findOrCreateSubDeployment(resource, subDeploymentName);
               subDeployment.addTarget(jmsServer);
            }
         } catch (Exception var12) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "Queue", queueName).getMessage(), var12);
         }
      } else {
         throw new JMSException("ERROR: queueName cannot be null or empty");
      }
   }

   public static void deleteQueue(Context ctx, String resourceName, String queueName) throws JMSException {
      deleteDestination(ctx, resourceName, queueName, "JMSQueue");
   }

   public static void deleteQueue(DomainMBean domain, String resourceName, String queueName) throws JMSException {
      deleteDestination(domain, resourceName, queueName, "JMSQueue");
   }

   public static void createTopic(Context ctx, String resourceName, String jmsServerName, String topicName, String jndiName) throws JMSException {
      createTopic((Context)ctx, resourceName, jmsServerName, topicName, jndiName, (JMSNamedEntityModifier)null);
   }

   public static void createTopic(DomainMBean domain, String resourceName, String jmsServerName, String topicName, String jndiName) throws JMSException {
      createTopic((DomainMBean)domain, resourceName, jmsServerName, topicName, jndiName, (JMSNamedEntityModifier)null);
   }

   public static void createTopic(Context ctx, String resourceName, String jmsServerName, String topicName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createTopic(domain, resourceName, jmsServerName, topicName, jndiName, modifier);
         success = true;
      } finally {
         endEditSession(manager, topicName, success);
      }

   }

   public static void createTopic(DomainMBean domain, String resourceName, String jmsServerName, String topicName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (topicName != null && !topicName.trim().equals("")) {
         try {
            JMSServerMBean jmsServer = domain.lookupJMSServer(jmsServerName);
            if (jmsServer == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInDomainLoggable(domain.getName(), "JMSServer", jmsServerName).getMessage());
            } else {
               JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
               JMSBean jmsBean = resource.getJMSResource();
               TopicBean topicBean = jmsBean.createTopic(topicName);
               topicBean.setJNDIName(jndiName);
               if (modifier != null) {
                  modifier.modify(topicBean);
               }

               String subDeploymentName = topicBean.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = topicName;
               }

               SubDeploymentMBean subDeployment = findOrCreateSubDeployment(resource, subDeploymentName);
               subDeployment.addTarget(jmsServer);
            }
         } catch (Exception var12) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "Topic", topicName).getMessage(), var12);
         }
      } else {
         throw new JMSException("ERROR: topicName cannot be null or empty");
      }
   }

   public static void deleteTopic(Context ctx, String resourceName, String topicName) throws JMSException {
      deleteDestination(ctx, resourceName, topicName, "JMSTopic");
   }

   public static void deleteTopic(DomainMBean domain, String resourceName, String topicName) throws JMSException {
      deleteDestination(domain, resourceName, topicName, "JMSTopic");
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedQueue(Context ctx, String resourceName, String distributedQueueName, String jndiName) throws JMSException {
      createDistributedQueue((Context)ctx, resourceName, distributedQueueName, jndiName, (JMSNamedEntityModifier)null);
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedQueue(DomainMBean domain, String resourceName, String distributedQueueName, String jndiName) throws JMSException {
      createDistributedQueue((DomainMBean)domain, resourceName, distributedQueueName, jndiName, (JMSNamedEntityModifier)null);
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedQueue(Context ctx, String resourceName, String distributedQueueName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createDistributedQueue(domain, resourceName, distributedQueueName, jndiName, modifier);
         success = true;
      } finally {
         endEditSession(manager, distributedQueueName, success);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedQueue(DomainMBean domain, String resourceName, String distributedQueueName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (distributedQueueName != null && !distributedQueueName.trim().equals("")) {
         try {
            JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
            JMSBean jmsBean = resource.getJMSResource();
            DistributedQueueBean distributedQueueBean = jmsBean.createDistributedQueue(distributedQueueName);
            distributedQueueBean.setJNDIName(jndiName);
            if (modifier != null) {
               modifier.modify(distributedQueueBean);
            }

         } catch (Exception var8) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "DistributedQueue", distributedQueueName).getMessage(), var8);
         }
      } else {
         throw new JMSException("ERROR: distributedQueueName cannot be null or empty");
      }
   }

   /** @deprecated */
   @Deprecated
   public static void deleteDistributedQueue(Context ctx, String resourceName, String distributedQueueName, boolean deleteMembers) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteDistributedQueue(domain, resourceName, distributedQueueName, deleteMembers);
         success = true;
      } finally {
         endEditSession(manager, distributedQueueName, success);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void deleteDistributedQueue(DomainMBean domain, String resourceName, String distributedQueueName, boolean deleteMembers) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            DistributedQueueBean distributedQueueBean = jmsBean.lookupDistributedQueue(distributedQueueName);
            if (distributedQueueBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedQueue", distributedQueueName).getMessage());
            } else {
               if (deleteMembers) {
                  DistributedDestinationMemberBean[] members = distributedQueueBean.getDistributedQueueMembers();
                  if (members != null) {
                     for(int i = 0; i < members.length; ++i) {
                        distributedQueueBean.destroyDistributedQueueMember(members[i]);
                     }
                  }
               }

               jmsBean.destroyDistributedQueue(distributedQueueBean);
            }
         } catch (Exception var8) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "DistributedQueue", distributedQueueName).getMessage(), var8);
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedQueueMember(Context ctx, String resourceName, String distributedQueueName, String distributedQueueMemberName, int weight) throws JMSException {
      createDistributedQueueMember((Context)ctx, resourceName, distributedQueueName, distributedQueueMemberName, weight, (JMSNamedEntityModifier)null);
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedQueueMember(DomainMBean domain, String resourceName, String distributedQueueName, String distributedQueueMemberName, int weight) throws JMSException {
      createDistributedQueueMember((DomainMBean)domain, resourceName, distributedQueueName, distributedQueueMemberName, weight, (JMSNamedEntityModifier)null);
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedQueueMember(Context ctx, String resourceName, String distributedQueueName, String distributedQueueMemberName, int weight, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createDistributedQueueMember(domain, resourceName, distributedQueueName, distributedQueueMemberName, weight, modifier);
         success = true;
      } finally {
         endEditSession(manager, distributedQueueMemberName, success);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedQueueMember(DomainMBean domain, String resourceName, String distributedQueueName, String distributedQueueMemberName, int weight, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (distributedQueueMemberName != null && !distributedQueueMemberName.trim().equals("")) {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            DistributedQueueBean distributedQueueBean = jmsBean.lookupDistributedQueue(distributedQueueName);
            if (distributedQueueBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedQueue", distributedQueueName).getMessage());
            } else {
               DistributedDestinationMemberBean dqm = distributedQueueBean.createDistributedQueueMember(distributedQueueMemberName);
               dqm.setWeight(weight);
               if (modifier != null) {
                  modifier.modify(dqm);
               }

            }
         } catch (Exception var9) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "DistributedQueueMember", distributedQueueMemberName).getMessage(), var9);
         }
      } else {
         throw new JMSException("ERROR: distributedQueueMemberName cannot be null or empty");
      }
   }

   /** @deprecated */
   @Deprecated
   public static void deleteDistributedQueueMember(Context ctx, String resourceName, String distributedQueueName, String distributedQueueMemberName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteDistributedQueueMember(domain, resourceName, distributedQueueName, distributedQueueMemberName);
         success = true;
      } finally {
         endEditSession(manager, distributedQueueMemberName, success);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void deleteDistributedQueueMember(DomainMBean domain, String resourceName, String distributedQueueName, String distributedQueueMemberName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            DistributedQueueBean distributedQueueBean = jmsBean.lookupDistributedQueue(distributedQueueName);
            if (distributedQueueBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedQueue", distributedQueueName).getMessage());
            } else {
               DistributedDestinationMemberBean dqm = distributedQueueBean.lookupDistributedQueueMember(distributedQueueMemberName);
               if (dqm == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedQueueMember", distributedQueueMemberName).getMessage());
               } else {
                  distributedQueueBean.destroyDistributedQueueMember(dqm);
               }
            }
         } catch (Exception var7) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "DistributedQueueMember", distributedQueueMemberName).getMessage(), var7);
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static String[] getDistributedQueueMemberNames(Context ctx, String resourceName, String distributedQueueName) throws JMSException {
      String[] memberQueueNames = new String[0];
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         memberQueueNames = getDistributedQueueMemberNames(domain, resourceName, distributedQueueName);
         success = true;
      } finally {
         endEditSession(manager, distributedQueueName, success);
      }

      return memberQueueNames;
   }

   /** @deprecated */
   @Deprecated
   public static String[] getDistributedQueueMemberNames(DomainMBean domain, String resourceName, String distributedQueueName) throws JMSException {
      String[] memberQueueNames = new String[0];
      JMSBean jmsBean = getJMSBean(domain, resourceName);
      DistributedQueueBean distributedQueueBean = jmsBean.lookupDistributedQueue(distributedQueueName);
      if (distributedQueueBean == null) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedQueue", distributedQueueName).getMessage());
      } else {
         DistributedDestinationMemberBean[] members = distributedQueueBean.getDistributedQueueMembers();
         memberQueueNames = new String[members.length];

         for(int i = 0; i < members.length; ++i) {
            memberQueueNames[i] = members[i].getPhysicalDestinationName();
         }

         return memberQueueNames;
      }
   }

   /** @deprecated */
   @Deprecated
   public static String[] getDistributedQueueMemberJndiNames(Context ctx, String resourceName, String distributedQueueName) throws JMSException {
      String[] memberQueueJndiNames = new String[0];
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         memberQueueJndiNames = getDistributedQueueMemberJndiNames(domain, resourceName, distributedQueueName);
         success = true;
      } finally {
         endEditSession(manager, distributedQueueName, success);
      }

      return memberQueueJndiNames;
   }

   /** @deprecated */
   @Deprecated
   public static String[] getDistributedQueueMemberJndiNames(DomainMBean domain, String resourceName, String distributedQueueName) throws JMSException {
      String[] memberQueueJndiNames = new String[0];
      JMSBean jmsBean = getJMSBean(domain, resourceName);
      DistributedQueueBean distributedQueueBean = jmsBean.lookupDistributedQueue(distributedQueueName);
      if (distributedQueueBean == null) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedQueue", distributedQueueName).getMessage());
      } else {
         DistributedDestinationMemberBean[] members = distributedQueueBean.getDistributedQueueMembers();
         memberQueueJndiNames = new String[members.length];

         for(int i = 0; i < members.length; ++i) {
            QueueBean queue = jmsBean.lookupQueue(members[i].getPhysicalDestinationName());
            if (queue != null) {
               memberQueueJndiNames[i] = queue.getJNDIName();
            }
         }

         return memberQueueJndiNames;
      }
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedTopic(Context ctx, String resourceName, String distributedTopicName, String jndiName) throws JMSException {
      createDistributedTopic((Context)ctx, resourceName, distributedTopicName, jndiName, (JMSNamedEntityModifier)null);
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedTopic(DomainMBean domain, String resourceName, String distributedTopicName, String jndiName) throws JMSException {
      createDistributedTopic((DomainMBean)domain, resourceName, distributedTopicName, jndiName, (JMSNamedEntityModifier)null);
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedTopic(Context ctx, String resourceName, String distributedTopicName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createDistributedTopic(domain, resourceName, distributedTopicName, jndiName, modifier);
         success = true;
      } finally {
         endEditSession(manager, distributedTopicName, success);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedTopic(DomainMBean domain, String resourceName, String distributedTopicName, String jndiName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (distributedTopicName != null && !distributedTopicName.trim().equals("")) {
         try {
            JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
            JMSBean jmsBean = resource.getJMSResource();
            DistributedTopicBean distributedTopicBean = jmsBean.createDistributedTopic(distributedTopicName);
            distributedTopicBean.setJNDIName(jndiName);
            if (modifier != null) {
               modifier.modify(distributedTopicBean);
            }

         } catch (Exception var8) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "DistributedTopic", distributedTopicName).getMessage(), var8);
         }
      } else {
         throw new JMSException("ERROR: distributedTopicName cannot be null or empty");
      }
   }

   /** @deprecated */
   @Deprecated
   public static void deleteDistributedTopic(Context ctx, String resourceName, String distributedTopicName, boolean deleteMembers) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteDistributedTopic(domain, resourceName, distributedTopicName, deleteMembers);
         success = true;
      } finally {
         endEditSession(manager, distributedTopicName, success);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void deleteDistributedTopic(DomainMBean domain, String resourceName, String distributedTopicName, boolean deleteMembers) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            DistributedTopicBean distributedTopicBean = jmsBean.lookupDistributedTopic(distributedTopicName);
            if (distributedTopicBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedTopic", distributedTopicName).getMessage());
            } else {
               if (deleteMembers) {
                  DistributedDestinationMemberBean[] members = distributedTopicBean.getDistributedTopicMembers();
                  if (members != null) {
                     for(int i = 0; i < members.length; ++i) {
                        distributedTopicBean.destroyDistributedTopicMember(members[i]);
                     }
                  }
               }

               jmsBean.destroyDistributedTopic(distributedTopicBean);
            }
         } catch (Exception var8) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "DistributedTopic", distributedTopicName).getMessage(), var8);
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedTopicMember(Context ctx, String resourceName, String distributedTopicName, String distributedTopicMemberName, int weight) throws JMSException {
      createDistributedTopicMember((Context)ctx, resourceName, distributedTopicName, distributedTopicMemberName, weight, (JMSNamedEntityModifier)null);
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedTopicMember(DomainMBean domain, String resourceName, String distributedTopicName, String distributedTopicMemberName, int weight) throws JMSException {
      createDistributedTopicMember((DomainMBean)domain, resourceName, distributedTopicName, distributedTopicMemberName, weight, (JMSNamedEntityModifier)null);
   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedTopicMember(Context ctx, String resourceName, String distributedTopicName, String distributedTopicMemberName, int weight, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createDistributedTopicMember(domain, resourceName, distributedTopicName, distributedTopicMemberName, weight, modifier);
         success = true;
      } finally {
         endEditSession(manager, distributedTopicMemberName, success);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void createDistributedTopicMember(DomainMBean domain, String resourceName, String distributedTopicName, String distributedTopicMemberName, int weight, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (distributedTopicMemberName != null && !distributedTopicMemberName.trim().equals("")) {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            DistributedTopicBean distributedTopicBean = jmsBean.lookupDistributedTopic(distributedTopicName);
            if (distributedTopicBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedTopic", distributedTopicName).getMessage());
            } else {
               DistributedDestinationMemberBean dtm = distributedTopicBean.createDistributedTopicMember(distributedTopicMemberName);
               dtm.setWeight(weight);
               if (modifier != null) {
                  modifier.modify(dtm);
               }

            }
         } catch (Exception var9) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "DistributedTopicMember", distributedTopicMemberName).getMessage(), var9);
         }
      } else {
         throw new JMSException("ERROR: distributedTopicMemberName cannot be null or empty");
      }
   }

   /** @deprecated */
   @Deprecated
   public static void deleteDistributedTopicMember(Context ctx, String resourceName, String distributedTopicName, String distributedTopicMemberName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteDistributedTopicMember(domain, resourceName, distributedTopicName, distributedTopicMemberName);
         success = true;
      } finally {
         endEditSession(manager, distributedTopicMemberName, success);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void deleteDistributedTopicMember(DomainMBean domain, String resourceName, String distributedTopicName, String distributedTopicMemberName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            DistributedTopicBean distributedTopicBean = jmsBean.lookupDistributedTopic(distributedTopicName);
            if (distributedTopicBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedTopic", distributedTopicName).getMessage());
            } else {
               DistributedDestinationMemberBean dtm = distributedTopicBean.lookupDistributedTopicMember(distributedTopicMemberName);
               if (dtm == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedTopicMember", distributedTopicMemberName).getMessage());
               } else {
                  distributedTopicBean.destroyDistributedTopicMember(dtm);
               }
            }
         } catch (Exception var7) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "DistributedTopicMember", distributedTopicMemberName).getMessage(), var7);
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static String[] getDistributedTopicMemberNames(Context ctx, String resourceName, String distributedTopicName) throws JMSException {
      String[] memberTopicNames = new String[0];
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         memberTopicNames = getDistributedTopicMemberNames(domain, resourceName, distributedTopicName);
         success = true;
      } finally {
         endEditSession(manager, distributedTopicName, success);
      }

      return memberTopicNames;
   }

   /** @deprecated */
   @Deprecated
   public static String[] getDistributedTopicMemberNames(DomainMBean domain, String resourceName, String distributedTopicName) throws JMSException {
      String[] memberTopicNames = new String[0];
      JMSBean jmsBean = getJMSBean(domain, resourceName);
      DistributedTopicBean distributedTopicBean = jmsBean.lookupDistributedTopic(distributedTopicName);
      if (distributedTopicBean == null) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedTopic", distributedTopicName).getMessage());
      } else {
         DistributedDestinationMemberBean[] members = distributedTopicBean.getDistributedTopicMembers();
         memberTopicNames = new String[members.length];

         for(int i = 0; i < members.length; ++i) {
            memberTopicNames[i] = members[i].getPhysicalDestinationName();
         }

         return memberTopicNames;
      }
   }

   public static void createUniformDistributedTopic(Context ctx, String resourceName, String uniformDistributedTopicName, String jndiName, String targetName) throws JMSException {
      createUniformDistributedTopic((Context)ctx, resourceName, uniformDistributedTopicName, jndiName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createUniformDistributedTopic(DomainMBean domain, String resourceName, String uniformDistributedTopicName, String jndiName, String targetName) throws JMSException {
      createUniformDistributedTopic((DomainMBean)domain, resourceName, uniformDistributedTopicName, jndiName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createUniformDistributedTopic(Context ctx, String resourceName, String uniformDistributedTopicName, String jndiName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createUniformDistributedTopic(domain, resourceName, uniformDistributedTopicName, jndiName, targetName, modifier);
         success = true;
      } finally {
         endEditSession(manager, uniformDistributedTopicName, success);
      }

   }

   public static void createUniformDistributedTopic(DomainMBean domain, String resourceName, String uniformDistributedTopicName, String jndiName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (uniformDistributedTopicName != null && !uniformDistributedTopicName.trim().equals("")) {
         try {
            TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);

            for(int i = 0; i < targetMBeans.length; ++i) {
               if (targetMBeans[i] instanceof MigratableTargetMBean) {
                  throw new JMSException(JMSExceptionLogger.logInvalidSubDeploymentTargetLoggable(resourceName, targetName, "MigratableTargetMBean", uniformDistributedTopicName).getMessage());
               }
            }

            JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
            JMSBean jmsBean = resource.getJMSResource();
            UniformDistributedTopicBean uniformDistributedTopicBean = jmsBean.createUniformDistributedTopic(uniformDistributedTopicName);
            uniformDistributedTopicBean.setJNDIName(jndiName);
            if (modifier != null) {
               modifier.modify(uniformDistributedTopicBean);
            }

            String subDeploymentName = uniformDistributedTopicBean.getSubDeploymentName();
            if (subDeploymentName == null) {
               subDeploymentName = uniformDistributedTopicName;
            }

            SubDeploymentMBean subDeployment = findOrCreateSubDeployment(resource, subDeploymentName);
            subDeployment.setTargets(targetMBeans);
         } catch (Exception var12) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "UniformDistributedTopic", uniformDistributedTopicName).getMessage(), var12);
         }
      } else {
         throw new JMSException("ERROR: uniformDistributedTopicName cannot be null or empty");
      }
   }

   public static void createUniformDistributedQueue(Context ctx, String resourceName, String uniformDistributedQueueName, String jndiName, String targetName) throws JMSException {
      createUniformDistributedQueue((Context)ctx, resourceName, uniformDistributedQueueName, jndiName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createUniformDistributedQueue(DomainMBean domain, String resourceName, String uniformDistributedQueueName, String jndiName, String targetName) throws JMSException {
      createUniformDistributedQueue((DomainMBean)domain, resourceName, uniformDistributedQueueName, jndiName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createUniformDistributedQueue(Context ctx, String resourceName, String uniformDistributedQueueName, String jndiName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createUniformDistributedQueue(domain, resourceName, uniformDistributedQueueName, jndiName, targetName, modifier);
         success = true;
      } finally {
         endEditSession(manager, uniformDistributedQueueName, success);
      }

   }

   public static void createUniformDistributedQueue(DomainMBean domain, String resourceName, String uniformDistributedQueueName, String jndiName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (uniformDistributedQueueName != null && !uniformDistributedQueueName.trim().equals("")) {
         try {
            TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);

            for(int i = 0; i < targetMBeans.length; ++i) {
               if (targetMBeans[i] instanceof MigratableTargetMBean) {
                  throw new JMSException(JMSExceptionLogger.logInvalidSubDeploymentTargetLoggable(resourceName, targetName, "MigratableTargetMBean", uniformDistributedQueueName).getMessage());
               }
            }

            JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
            JMSBean jmsBean = resource.getJMSResource();
            UniformDistributedQueueBean uniformDistributedQueueBean = jmsBean.createUniformDistributedQueue(uniformDistributedQueueName);
            uniformDistributedQueueBean.setJNDIName(jndiName);
            if (modifier != null) {
               modifier.modify(uniformDistributedQueueBean);
            }

            String subDeploymentName = uniformDistributedQueueBean.getSubDeploymentName();
            if (subDeploymentName == null) {
               subDeploymentName = uniformDistributedQueueName;
            }

            SubDeploymentMBean subDeployment = findOrCreateSubDeployment(resource, subDeploymentName);
            subDeployment.setTargets(targetMBeans);
         } catch (Exception var12) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "UniformDistributedQueue", uniformDistributedQueueName).getMessage(), var12);
         }
      } else {
         throw new JMSException("ERROR: uniformDistributedQueueName cannot be null or empty");
      }
   }

   public static void deleteUniformDistributedQueue(Context ctx, String resourceName, String queueName) throws JMSException {
      deleteDestination(ctx, resourceName, queueName, "UniformDistributedQueue");
   }

   public static void deleteUniformDistributedQueue(DomainMBean domain, String resourceName, String queueName) throws JMSException {
      deleteDestination(domain, resourceName, queueName, "UniformDistributedQueue");
   }

   public static void deleteUniformDistributedTopic(Context ctx, String resourceName, String topicName) throws JMSException {
      deleteDestination(ctx, resourceName, topicName, "UniformDistributedTopic");
   }

   public static void deleteUniformDistributedTopic(DomainMBean domain, String resourceName, String topicName) throws JMSException {
      deleteDestination(domain, resourceName, topicName, "UniformDistributedTopic");
   }

   /** @deprecated */
   @Deprecated
   public static String[] getDistributedTopicMemberJndiNames(Context ctx, String resourceName, String distributedTopicName) throws JMSException {
      String[] memberTopicJndiNames = new String[0];
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         getDistributedTopicMemberJndiNames(domain, resourceName, distributedTopicName);
         success = true;
      } finally {
         endEditSession(manager, distributedTopicName, success);
      }

      return memberTopicJndiNames;
   }

   /** @deprecated */
   @Deprecated
   public static String[] getDistributedTopicMemberJndiNames(DomainMBean domain, String resourceName, String distributedTopicName) throws JMSException {
      String[] memberTopicJndiNames = new String[0];
      JMSBean jmsBean = getJMSBean(domain, resourceName);
      DistributedTopicBean distributedTopicBean = jmsBean.lookupDistributedTopic(distributedTopicName);
      if (distributedTopicBean == null) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "DistributedTopic", distributedTopicName).getMessage());
      } else {
         DistributedDestinationMemberBean[] members = distributedTopicBean.getDistributedTopicMembers();
         memberTopicJndiNames = new String[members.length];

         for(int i = 0; i < members.length; ++i) {
            TopicBean topic = jmsBean.lookupTopic(members[i].getPhysicalDestinationName());
            if (topic != null) {
               memberTopicJndiNames[i] = topic.getJNDIName();
            }
         }

         return memberTopicJndiNames;
      }
   }

   public static void createForeignServer(Context ctx, String resourceName, String foreignServerName, String targetName) throws JMSException {
      createForeignServer((Context)ctx, resourceName, foreignServerName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createForeignServer(DomainMBean domain, String resourceName, String foreignServerName, String targetName) throws JMSException {
      createForeignServer((DomainMBean)domain, resourceName, foreignServerName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createForeignServer(Context ctx, String resourceName, String foreignServerName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createForeignServer(domain, resourceName, foreignServerName, targetName, modifier);
         success = true;
      } finally {
         endEditSession(manager, foreignServerName, success);
      }

   }

   public static void createForeignServer(DomainMBean domain, String resourceName, String foreignServerName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (foreignServerName != null && !foreignServerName.trim().equals("")) {
         try {
            TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);

            for(int i = 0; i < targetMBeans.length; ++i) {
               if (targetMBeans[i] instanceof MigratableTargetMBean) {
                  throw new JMSException(JMSExceptionLogger.logInvalidSubDeploymentTargetLoggable(resourceName, targetName, "MigratableTargetMBean", foreignServerName).getMessage());
               }
            }

            JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
            JMSBean jmsBean = resource.getJMSResource();
            ForeignServerBean fsBean = jmsBean.createForeignServer(foreignServerName);
            if (modifier != null) {
               modifier.modify(fsBean);
            }

            String subDeploymentName = fsBean.getSubDeploymentName();
            if (subDeploymentName == null) {
               subDeploymentName = foreignServerName;
            }

            SubDeploymentMBean subDeployment = findOrCreateSubDeployment(resource, subDeploymentName);
            subDeployment.setTargets(targetMBeans);
         } catch (Exception var11) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "ForeignServer", foreignServerName).getMessage(), var11);
         }
      } else {
         throw new JMSException("ERROR: foreignServerName cannot be null or empty");
      }
   }

   public static void deleteForeignServer(Context ctx, String resourceName, String foreignServerName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteForeignServer(domain, resourceName, foreignServerName);
         success = true;
      } finally {
         endEditSession(manager, foreignServerName, success);
      }

   }

   public static void deleteForeignServer(DomainMBean domain, String resourceName, String foreignServerName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "ForeignServer", foreignServerName).getMessage());
            } else {
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
         } catch (Exception var8) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "ForeignServer", foreignServerName).getMessage(), var8);
         }
      }
   }

   public static void createForeignDestination(Context ctx, String resourceName, String foreignServerName, String foreignDestinationName, String localJNDIName, String remoteJNDIName) throws JMSException {
      createForeignDestination((Context)ctx, resourceName, foreignServerName, foreignDestinationName, localJNDIName, remoteJNDIName, (JMSNamedEntityModifier)null);
   }

   public static void createForeignDestination(DomainMBean domain, String resourceName, String foreignServerName, String foreignDestinationName, String localJNDIName, String remoteJNDIName) throws JMSException {
      createForeignDestination((DomainMBean)domain, resourceName, foreignServerName, foreignDestinationName, localJNDIName, remoteJNDIName, (JMSNamedEntityModifier)null);
   }

   public static void createForeignDestination(Context ctx, String resourceName, String foreignServerName, String foreignDestinationName, String localJNDIName, String remoteJNDIName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createForeignDestination(domain, resourceName, foreignServerName, foreignDestinationName, localJNDIName, remoteJNDIName, modifier);
         success = true;
      } finally {
         endEditSession(manager, foreignDestinationName, success);
      }

   }

   public static void createForeignDestination(DomainMBean domain, String resourceName, String foreignServerName, String foreignDestinationName, String localJNDIName, String remoteJNDIName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (foreignDestinationName != null && !foreignDestinationName.trim().equals("")) {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "ForeignServer", foreignServerName).getMessage());
            } else {
               ForeignDestinationBean fdBean = foreignServerBean.createForeignDestination(foreignDestinationName);
               fdBean.setLocalJNDIName(localJNDIName);
               fdBean.setRemoteJNDIName(remoteJNDIName);
               if (modifier != null) {
                  modifier.modify(fdBean);
               }

            }
         } catch (Exception var10) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "ForeignDestination", foreignDestinationName).getMessage(), var10);
         }
      } else {
         throw new JMSException("ERROR: foreignDestinationName cannot be null or empty");
      }
   }

   public static void deleteForeignDestination(Context ctx, String resourceName, String foreignServerName, String foreignDestinationName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteForeignDestination(domain, resourceName, foreignServerName, foreignDestinationName);
         success = true;
      } finally {
         endEditSession(manager, foreignDestinationName, success);
      }

   }

   public static void deleteForeignDestination(DomainMBean domain, String resourceName, String foreignServerName, String foreignDestinationName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "ForeignServer", foreignServerName).getMessage());
            } else {
               ForeignDestinationBean fdBean = foreignServerBean.lookupForeignDestination(foreignDestinationName);
               if (fdBean == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "ForeignDestination", foreignDestinationName).getMessage());
               } else {
                  foreignServerBean.destroyForeignDestination(fdBean);
               }
            }
         } catch (Exception var7) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "ForeignDestination", foreignDestinationName).getMessage(), var7);
         }
      }
   }

   public static void createForeignConnectionFactory(Context ctx, String resourceName, String foreignServerName, String foreignConnectionFactoryName, String localJNDIName, String remoteJNDIName, String username, String password) throws JMSException {
      createForeignConnectionFactory((Context)ctx, resourceName, foreignServerName, foreignConnectionFactoryName, localJNDIName, remoteJNDIName, username, password, (JMSNamedEntityModifier)null);
   }

   public static void createForeignConnectionFactory(DomainMBean domain, String resourceName, String foreignServerName, String foreignConnectionFactoryName, String localJNDIName, String remoteJNDIName, String username, String password) throws JMSException {
      createForeignConnectionFactory((DomainMBean)domain, resourceName, foreignServerName, foreignConnectionFactoryName, localJNDIName, remoteJNDIName, username, password, (JMSNamedEntityModifier)null);
   }

   public static void createForeignConnectionFactory(Context ctx, String resourceName, String foreignServerName, String foreignConnectionFactoryName, String localJNDIName, String remoteJNDIName, String username, String password, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createForeignConnectionFactory(domain, resourceName, foreignServerName, foreignConnectionFactoryName, localJNDIName, remoteJNDIName, username, password, modifier);
         success = true;
      } finally {
         endEditSession(manager, foreignConnectionFactoryName, success);
      }

   }

   public static void createForeignConnectionFactory(DomainMBean domain, String resourceName, String foreignServerName, String foreignConnectionFactoryName, String localJNDIName, String remoteJNDIName, String username, String password, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (foreignConnectionFactoryName != null && !foreignConnectionFactoryName.trim().equals("")) {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "ForeignServer", foreignServerName).getMessage());
            } else {
               ForeignConnectionFactoryBean fcBean = foreignServerBean.createForeignConnectionFactory(foreignConnectionFactoryName);
               fcBean.setLocalJNDIName(localJNDIName);
               fcBean.setRemoteJNDIName(remoteJNDIName);
               fcBean.setUsername(username);
               fcBean.setPassword(password);
               if (modifier != null) {
                  modifier.modify(fcBean);
               }

            }
         } catch (Exception var12) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "ForeignConnectionFactory", foreignConnectionFactoryName).getMessage(), var12);
         }
      } else {
         throw new JMSException("ERROR: foreignConnectionFactoryName cannot be null or empty");
      }
   }

   public static void deleteForeignConnectionFactory(Context ctx, String resourceName, String foreignServerName, String foreignConnectionFactoryName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteForeignConnectionFactory(domain, resourceName, foreignServerName, foreignConnectionFactoryName);
         success = true;
      } finally {
         endEditSession(manager, foreignConnectionFactoryName, success);
      }

   }

   public static void deleteForeignConnectionFactory(DomainMBean domain, String resourceName, String foreignServerName, String foreignConnectionFactoryName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            ForeignServerBean foreignServerBean = jmsBean.lookupForeignServer(foreignServerName);
            if (foreignServerBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "ForeignServer", foreignServerName).getMessage());
            } else {
               ForeignConnectionFactoryBean fcBean = foreignServerBean.lookupForeignConnectionFactory(foreignConnectionFactoryName);
               if (fcBean == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "ForeignConnectionFactory", foreignConnectionFactoryName).getMessage());
               } else {
                  foreignServerBean.destroyForeignConnectionFactory(fcBean);
               }
            }
         } catch (Exception var7) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "ForeignConnectionFactory", foreignConnectionFactoryName).getMessage(), var7);
         }
      }
   }

   public static void createSAFImportedDestinations(Context ctx, String resourceName, String safImportedDestinationsName, String safRemoteContextName, String targetName) throws JMSException {
      createSAFImportedDestinations((Context)ctx, resourceName, safImportedDestinationsName, safRemoteContextName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFImportedDestinations(DomainMBean domain, String resourceName, String safImportedDestinationsName, String safRemoteContextName, String targetName) throws JMSException {
      createSAFImportedDestinations((DomainMBean)domain, resourceName, safImportedDestinationsName, safRemoteContextName, targetName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFImportedDestinations(Context ctx, String resourceName, String safImportedDestinationsName, String safRemoteContextName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createSAFImportedDestinations(domain, resourceName, safImportedDestinationsName, safRemoteContextName, targetName, modifier);
         success = true;
      } finally {
         endEditSession(manager, safImportedDestinationsName, success);
      }

   }

   public static void createSAFImportedDestinations(DomainMBean domain, String resourceName, String safImportedDestinationsName, String safRemoteContextName, String targetName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (safImportedDestinationsName != null && !safImportedDestinationsName.trim().equals("")) {
         try {
            TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);

            for(int i = 0; i < targetMBeans.length; ++i) {
               String targetType = null;
               if (targetMBeans[i] instanceof MigratableTargetMBean) {
                  targetType = "MigratableTargetMBean";
               } else if (targetMBeans[i] instanceof JMSServerMBean) {
                  targetType = "JMSServerMBean";
               }

               if (targetType != null) {
                  throw new JMSException(JMSExceptionLogger.logInvalidSAFImportedDestinationsTargetInDomainLoggable(resourceName, targetName, targetType, safImportedDestinationsName).getMessage());
               }
            }

            JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
            JMSBean jmsBean = resource.getJMSResource();
            SAFRemoteContextBean safRemoteContext = jmsBean.lookupSAFRemoteContext(safRemoteContextName);
            if (safRemoteContext == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFRemoteContext", safRemoteContextName).getMessage());
            } else {
               SAFImportedDestinationsBean safImportedDestinations = jmsBean.createSAFImportedDestinations(safImportedDestinationsName);
               safImportedDestinations.setSAFRemoteContext(safRemoteContext);
               if (modifier != null) {
                  modifier.modify(safImportedDestinations);
               }

               String subDeploymentName = safImportedDestinations.getSubDeploymentName();
               if (subDeploymentName == null) {
                  subDeploymentName = safImportedDestinationsName;
               }

               SubDeploymentMBean subDeployment = findOrCreateSubDeployment(resource, subDeploymentName);
               subDeployment.setTargets(targetMBeans);
            }
         } catch (Exception var13) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "SAFImportedDestinations", safImportedDestinationsName).getMessage(), var13);
         }
      } else {
         throw new JMSException("ERROR: safImportedDestinationsName cannot be null or empty");
      }
   }

   public static void deleteSAFImportedDestinations(Context ctx, String resourceName, String safImportedDestinationsName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteSAFImportedDestinations(domain, resourceName, safImportedDestinationsName);
         success = true;
      } finally {
         endEditSession(manager, safImportedDestinationsName, success);
      }

   }

   public static void deleteSAFImportedDestinations(DomainMBean domain, String resourceName, String safImportedDestinationsName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            SAFImportedDestinationsBean safImportedDestinationsBean = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
            if (safImportedDestinationsBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFImportedDestinations", safImportedDestinationsName).getMessage());
            } else {
               SAFQueueBean[] safQueues = safImportedDestinationsBean.getSAFQueues();
               if (safQueues != null) {
                  for(int i = 0; i < safQueues.length; ++i) {
                     safImportedDestinationsBean.destroySAFQueue(safQueues[i]);
                  }
               }

               SAFTopicBean[] safTopics = safImportedDestinationsBean.getSAFTopics();
               if (safTopics != null) {
                  for(int i = 0; i < safTopics.length; ++i) {
                     safImportedDestinationsBean.destroySAFTopic(safTopics[i]);
                  }
               }

               jmsBean.destroySAFImportedDestinations(safImportedDestinationsBean);
            }
         } catch (Exception var8) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "SAFImportedDestinations", safImportedDestinationsName).getMessage(), var8);
         }
      }
   }

   public static void createSAFRemoteContext(Context ctx, String resourceName, String safRemoteContextName) throws JMSException {
      createSAFRemoteContext((Context)ctx, resourceName, safRemoteContextName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFRemoteContext(DomainMBean domain, String resourceName, String safRemoteContextName) throws JMSException {
      createSAFRemoteContext((DomainMBean)domain, resourceName, safRemoteContextName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFRemoteContext(Context ctx, String resourceName, String safRemoteContextName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createSAFRemoteContext(domain, resourceName, safRemoteContextName, modifier);
         success = true;
      } finally {
         endEditSession(manager, safRemoteContextName, success);
      }

   }

   public static void createSAFRemoteContext(DomainMBean domain, String resourceName, String safRemoteContextName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (safRemoteContextName != null && !safRemoteContextName.trim().equals("")) {
         try {
            JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
            JMSBean jmsBean = resource.getJMSResource();
            SAFRemoteContextBean safRemoteContext = jmsBean.createSAFRemoteContext(safRemoteContextName);
            if (modifier != null) {
               modifier.modify(safRemoteContext);
            }

         } catch (Exception var7) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "SAFRemoteContext", safRemoteContextName).getMessage(), var7);
         }
      } else {
         throw new JMSException("ERROR: SAFRemoteContextName cannot be null or empty");
      }
   }

   public static void deleteSAFRemoteContext(Context ctx, String resourceName, String safRemoteContextName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteSAFRemoteContext(domain, resourceName, safRemoteContextName);
         success = true;
      } finally {
         endEditSession(manager, safRemoteContextName, success);
      }

   }

   public static void deleteSAFRemoteContext(DomainMBean domain, String resourceName, String safRemoteContextName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            SAFRemoteContextBean safRemoteContextBean = jmsBean.lookupSAFRemoteContext(safRemoteContextName);
            if (safRemoteContextBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFRemoteContext", safRemoteContextName).getMessage());
            } else {
               jmsBean.destroySAFRemoteContext(safRemoteContextBean);
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "SAFRemoteContext", safRemoteContextName).getMessage(), var5);
         }
      }
   }

   public static void createSAFErrorHandling(Context ctx, String resourceName, String safErrorHandlingName) throws JMSException {
      createSAFErrorHandling((Context)ctx, resourceName, safErrorHandlingName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFErrorHandling(DomainMBean domain, String resourceName, String safErrorHandlingName) throws JMSException {
      createSAFErrorHandling((DomainMBean)domain, resourceName, safErrorHandlingName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFErrorHandling(Context ctx, String resourceName, String safErrorHandlingName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createSAFErrorHandling(domain, resourceName, safErrorHandlingName, modifier);
         success = true;
      } finally {
         endEditSession(manager, safErrorHandlingName, success);
      }

   }

   public static void createSAFErrorHandling(DomainMBean domain, String resourceName, String safErrorHandlingName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (safErrorHandlingName != null && !safErrorHandlingName.trim().equals("")) {
         try {
            JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
            JMSBean jmsBean = resource.getJMSResource();
            SAFErrorHandlingBean safErrorHandling = jmsBean.createSAFErrorHandling(safErrorHandlingName);
            if (modifier != null) {
               modifier.modify(safErrorHandling);
            }

         } catch (Exception var7) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "SAFErrorHandling", safErrorHandlingName).getMessage(), var7);
         }
      } else {
         throw new JMSException("ERROR: SAFErrorHandlingName cannot be null or empty");
      }
   }

   public static void deleteSAFErrorHandling(Context ctx, String resourceName, String safErrorHandlingName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteSAFErrorHandling(domain, resourceName, safErrorHandlingName);
         success = true;
      } finally {
         endEditSession(manager, safErrorHandlingName, success);
      }

   }

   public static void deleteSAFErrorHandling(DomainMBean domain, String resourceName, String safErrorHandlingName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            SAFErrorHandlingBean safErrorHandlingBean = jmsBean.lookupSAFErrorHandling(safErrorHandlingName);
            if (safErrorHandlingBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFErrorHandling", safErrorHandlingName).getMessage());
            } else {
               jmsBean.destroySAFErrorHandling(safErrorHandlingBean);
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "SAFErrorHandling", safErrorHandlingName).getMessage(), var5);
         }
      }
   }

   public static void createSAFQueue(Context ctx, String resourceName, String safImportedDestinationsName, String safQueueName, String safQueueRemoteJNDIName) throws JMSException {
      createSAFQueue((Context)ctx, resourceName, safImportedDestinationsName, safQueueName, safQueueRemoteJNDIName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFQueue(DomainMBean domain, String resourceName, String safImportedDestinationsName, String safQueueName, String safQueueRemoteJNDIName) throws JMSException {
      createSAFQueue((DomainMBean)domain, resourceName, safImportedDestinationsName, safQueueName, safQueueRemoteJNDIName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFQueue(Context ctx, String resourceName, String safImportedDestinationsName, String safQueueName, String safQueueRemoteJNDIName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createSAFQueue(domain, resourceName, safImportedDestinationsName, safQueueName, safQueueRemoteJNDIName, modifier);
         success = true;
      } finally {
         endEditSession(manager, safQueueName, success);
      }

   }

   public static void createSAFQueue(DomainMBean domain, String resourceName, String safImportedDestinationsName, String safQueueName, String safQueueRemoteJNDIName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (safQueueName != null && !safQueueName.trim().equals("")) {
         if (safQueueRemoteJNDIName != null && !safQueueRemoteJNDIName.trim().equals("")) {
            try {
               JMSBean jmsBean = getJMSBean(domain, resourceName);
               SAFImportedDestinationsBean safImportedDestinations = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
               if (safImportedDestinations == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFImportedDestinations", safImportedDestinationsName).getMessage());
               } else {
                  SAFQueueBean safQueue = safImportedDestinations.createSAFQueue(safQueueName);
                  safQueue.setRemoteJNDIName(safQueueRemoteJNDIName);
                  if (modifier != null) {
                     modifier.modify(safQueue);
                  }

               }
            } catch (Exception var9) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "SAFQueue", safQueueName).getMessage(), var9);
            }
         } else {
            throw new JMSException("ERROR: RemoteJNDIName of a SAFQueue cannot be null or empty");
         }
      } else {
         throw new JMSException("ERROR: safQueueName cannot be null or empty");
      }
   }

   public static void deleteSAFQueue(Context ctx, String resourceName, String safImportedDestinationsName, String safQueueName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteSAFQueue(domain, resourceName, safImportedDestinationsName, safQueueName);
         success = true;
      } finally {
         endEditSession(manager, safQueueName, success);
      }

   }

   public static void deleteSAFQueue(DomainMBean domain, String resourceName, String safImportedDestinationsName, String safQueueName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            SAFImportedDestinationsBean safImportedDestinationsBean = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
            if (safImportedDestinationsBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFImportedDestinations", safImportedDestinationsName).getMessage());
            } else {
               SAFQueueBean safQueue = safImportedDestinationsBean.lookupSAFQueue(safQueueName);
               if (safQueue == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFQueue", safQueueName).getMessage());
               } else {
                  safImportedDestinationsBean.destroySAFQueue(safQueue);
               }
            }
         } catch (Exception var7) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "SAFQueue", safQueueName).getMessage(), var7);
         }
      }
   }

   public static void createSAFTopic(Context ctx, String resourceName, String safImportedDestinationsName, String safTopicName, String safTopicRemoteJNDIName) throws JMSException {
      createSAFTopic((Context)ctx, resourceName, safImportedDestinationsName, safTopicName, safTopicRemoteJNDIName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFTopic(DomainMBean domain, String resourceName, String safImportedDestinationsName, String safTopicName, String safTopicRemoteJNDIName) throws JMSException {
      createSAFTopic((DomainMBean)domain, resourceName, safImportedDestinationsName, safTopicName, safTopicRemoteJNDIName, (JMSNamedEntityModifier)null);
   }

   public static void createSAFTopic(Context ctx, String resourceName, String safImportedDestinationsName, String safTopicName, String safTopicRemoteJNDIName, JMSNamedEntityModifier modifier) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createSAFTopic(domain, resourceName, safImportedDestinationsName, safTopicName, safTopicRemoteJNDIName, modifier);
         success = true;
      } finally {
         endEditSession(manager, safTopicName, success);
      }

   }

   public static void createSAFTopic(DomainMBean domain, String resourceName, String safImportedDestinationsName, String safTopicName, String safTopicRemoteJNDIName, JMSNamedEntityModifier modifier) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (safTopicName != null && !safTopicName.trim().equals("")) {
         if (safTopicRemoteJNDIName != null && !safTopicRemoteJNDIName.trim().equals("")) {
            try {
               JMSBean jmsBean = getJMSBean(domain, resourceName);
               SAFImportedDestinationsBean safImportedDestinations = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
               if (safImportedDestinations == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFImportedDestinations", safImportedDestinationsName).getMessage());
               } else {
                  SAFTopicBean safTopic = safImportedDestinations.createSAFTopic(safTopicName);
                  safTopic.setRemoteJNDIName(safTopicRemoteJNDIName);
                  if (modifier != null) {
                     modifier.modify(safTopic);
                  }

               }
            } catch (Exception var9) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInJMSSystemResourceLoggable(resourceName, "SAFTopic", safTopicName).getMessage(), var9);
            }
         } else {
            throw new JMSException("ERROR: RemoteJNDIName of a SAFTopic cannot be null or empty");
         }
      } else {
         throw new JMSException("ERROR: topicName cannot be null or empty");
      }
   }

   public static void deleteSAFTopic(Context ctx, String resourceName, String safImportedDestinationsName, String safTopicName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteSAFTopic(domain, resourceName, safImportedDestinationsName, safTopicName);
         success = true;
      } finally {
         endEditSession(manager, safTopicName, success);
      }

   }

   public static void deleteSAFTopic(DomainMBean domain, String resourceName, String safImportedDestinationsName, String safTopicName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            SAFImportedDestinationsBean safImportedDestinationsBean = jmsBean.lookupSAFImportedDestinations(safImportedDestinationsName);
            if (safImportedDestinationsBean == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFImportedDestinations", safImportedDestinationsName).getMessage());
            } else {
               SAFTopicBean safTopic = safImportedDestinationsBean.lookupSAFTopic(safTopicName);
               if (safTopic == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, "SAFTopic", safTopicName).getMessage());
               } else {
                  safImportedDestinationsBean.destroySAFTopic(safTopic);
               }
            }
         } catch (Exception var7) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, "SAFTopic", safTopicName).getMessage(), var7);
         }
      }
   }

   public static void findAndModifyEntity(Context ctx, String resourceName, String entityName, String entityType, JMSNamedEntityModifier modifier) throws JMSException {
      if (modifier == null) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logInvalidModuleEntityModifierLoggable(resourceName, entityType, entityName).getMessage());
      } else {
         ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
         DomainMBean domain = beginEditSession(manager);
         boolean success = false;

         try {
            findAndModifyEntity(domain, resourceName, entityName, entityType, modifier);
            success = true;
         } finally {
            endEditSession(manager, entityName, success);
         }

      }
   }

   public static void findAndModifyEntity(DomainMBean domain, String resourceName, String entityName, String entityType, JMSNamedEntityModifier modifier) throws JMSException {
      if (modifier == null) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logInvalidModuleEntityModifierLoggable(resourceName, entityType, entityName).getMessage());
      } else if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (resourceName != null && !resourceName.trim().equals("")) {
         if (entityName != null && !entityName.trim().equals("")) {
            if (entityType != null && !entityType.trim().equals("")) {
               try {
                  JMSBean jmsBean = getJMSBean(domain, resourceName);
                  Class jmsBeanClass = jmsBean.getClass();
                  String finderName = "lookup" + entityType;
                  Method finderMethod = null;
                  Class[] parameterTypes = new Class[]{String.class};
                  Object[] arguments = new Object[]{entityName};
                  NamedEntityBean entity = null;

                  try {
                     finderMethod = jmsBeanClass.getMethod(finderName, parameterTypes);
                  } catch (Exception var14) {
                     throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotFindAndModifyEntityFromJMSSystemResourceLoggable(resourceName, entityType, entityName).getMessage(), var14);
                  }

                  try {
                     entity = (NamedEntityBean)finderMethod.invoke(jmsBean, arguments);
                  } catch (Exception var13) {
                     throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotFindAndModifyEntityFromJMSSystemResourceLoggable(resourceName, entityType, entityName).getMessage(), var13);
                  }

                  if (entity == null) {
                     throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, entityType, entityName).getMessage());
                  } else {
                     modifier.modify(entity);
                  }
               } catch (Exception var15) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotFindAndModifyEntityFromJMSSystemResourceLoggable(resourceName, entityType, entityName).getMessage(), var15);
               }
            } else {
               throw new JMSException("ERROR: entityType cannot be null or empty");
            }
         } else {
            throw new JMSException("ERROR: entityName cannot be null or empty");
         }
      } else {
         throw new JMSException("ERROR: resourceName cannot be null or empty");
      }
   }

   public static void createJMSServer(Context ctx, String jmsServerName, String targetName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createJMSServer(domain, jmsServerName, targetName);
         success = true;
      } finally {
         endEditSession(manager, jmsServerName, success);
      }

   }

   public static void createJMSServer(DomainMBean domain, String jmsServerName, String targetName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (jmsServerName != null && !jmsServerName.trim().equals("")) {
         try {
            TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);
            if (targetMBeans.length != 1) {
               throw new JMSException("ERROR: A JMSServer cannot be targeted multiple targets");
            } else if (!(targetMBeans[0] instanceof ServerMBean) && !(targetMBeans[0] instanceof ClusterMBean) && !(targetMBeans[0] instanceof MigratableTargetMBean)) {
               throw new JMSException("ERROR: " + targetMBeans[0] + " is not a valid target for JMSServer");
            } else {
               JMSServerMBean jmsServer = domain.createJMSServer(jmsServerName);
               jmsServer.setTargets(targetMBeans);
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInDomainLoggable(domain.getName(), "JMSServer", jmsServerName).getMessage(), var5);
         }
      } else {
         throw new JMSException("ERROR: jmsServerName cannot be null or empty");
      }
   }

   public static void deleteJMSServer(Context ctx, String jmsServerName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteJMSServer(domain, jmsServerName);
         success = true;
      } finally {
         endEditSession(manager, jmsServerName, success);
      }

   }

   public static void deleteJMSServer(DomainMBean domain, String jmsServerName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSServerMBean jmsServer = domain.lookupJMSServer(jmsServerName);
            if (jmsServer == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInDomainLoggable(domain.getName(), "JMSServer", jmsServerName).getMessage());
            } else {
               TargetMBean target = jmsServer.getTargets()[0];
               jmsServer.removeTarget(target);
               domain.destroyJMSServer(jmsServer);
            }
         } catch (Exception var4) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromDomainLoggable(domain.getName(), "JMSServer", jmsServerName).getMessage(), var4);
         }
      }
   }

   public static void deployJMSServer(Context ctx, String jmsServerName, String targetName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deployJMSServer(domain, jmsServerName, targetName);
         success = true;
      } finally {
         endEditSession(manager, jmsServerName, success);
      }

   }

   public static void deployJMSServer(DomainMBean domain, String jmsServerName, String targetName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         JMSServerMBean jmsServer = domain.lookupJMSServer(jmsServerName);
         if (jmsServer == null) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInDomainLoggable(domain.getName(), "JMSServer", jmsServerName).getMessage());
         } else {
            try {
               TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);
               if (targetMBeans.length != 1) {
                  throw new JMSException("ERROR: A JMSServer cannot be targeted multiple targets");
               } else if (!(targetMBeans[0] instanceof ServerMBean) && !(targetMBeans[0] instanceof ClusterMBean) && !(targetMBeans[0] instanceof MigratableTargetMBean)) {
                  throw new JMSException("ERROR: " + targetMBeans[0] + " is not a valid target for JMSServer");
               } else {
                  jmsServer.setTargets(targetMBeans);
               }
            } catch (Exception var5) {
               throw new weblogic.jms.common.JMSException("ERROR: Could not deploy JMSSerever " + jmsServerName + " in the domain " + domain.getName(), var5);
            }
         }
      }
   }

   public static void undeployJMSServer(Context ctx, String jmsServerName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         undeployJMSServer(domain, jmsServerName);
         success = true;
      } finally {
         endEditSession(manager, jmsServerName, success);
      }

   }

   public static void undeployJMSServer(DomainMBean domain, String jmsServerName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         JMSServerMBean jmsServer = domain.lookupJMSServer(jmsServerName);
         if (jmsServer == null) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(domain.getName(), "JMSServer", jmsServerName).getMessage());
         } else {
            TargetMBean[] targets = jmsServer.getTargets();
            if (targets != null && targets.length != 0) {
               TargetMBean target = targets[0];
               ServerMBean serverMBean = null;
               if (target == null) {
                  throw new JMSException("JMSServer " + jmsServerName + " is not currently deployed");
               } else {
                  int i;
                  if (target instanceof MigratableTargetMBean) {
                     MigratableTargetMBean[] mts = domain.getMigratableTargets();

                     for(i = 0; i < mts.length; ++i) {
                        if (target.getName().equals(mts[i].getName())) {
                           serverMBean = mts[i].getUserPreferredServer();
                           break;
                        }
                     }
                  } else {
                     ServerMBean[] svrs = domain.getServers();

                     for(i = 0; i < svrs.length; ++i) {
                        if (target.getName().equals(svrs[i].getName())) {
                           serverMBean = svrs[i];
                           break;
                        }
                     }
                  }

                  try {
                     if (serverMBean != null) {
                        jmsServer.removeTarget(target);
                     }

                  } catch (Exception var8) {
                     throw new weblogic.jms.common.JMSException("ERROR: Could not undeploy JMSServer " + jmsServerName + " in the domain " + domain.getName(), var8);
                  }
               }
            } else {
               throw new weblogic.jms.common.JMSException("ERROR: Could not undeploy JMSServer " + jmsServerName + " in the domain " + domain.getName() + ", it is not currently deployed");
            }
         }
      }
   }

   public static void createSAFAgent(Context ctx, String safAgentName, String targetName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         createSAFAgent(domain, safAgentName, targetName);
         success = true;
      } finally {
         endEditSession(manager, safAgentName, success);
      }

   }

   public static void createSAFAgent(DomainMBean domain, String safAgentName, String targetName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else if (safAgentName != null && !safAgentName.trim().equals("")) {
         try {
            TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);

            for(int i = 0; i < targetMBeans.length; ++i) {
               if (targetMBeans[i] instanceof MigratableTargetMBean || targetMBeans[i] instanceof JMSServerMBean || targetMBeans[i] instanceof SAFAgentMBean) {
                  throw new JMSException("ERROR: MigratableTarget, JMSServer and SAFAgent cannot be set as target for SAFAgent");
               }
            }

            SAFAgentMBean safAgent = domain.createSAFAgent(safAgentName);
            safAgent.setTargets(targetMBeans);
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotCreateEntityInDomainLoggable(domain.getName(), "SAFAgent", safAgentName).getMessage(), var5);
         }
      } else {
         throw new JMSException("ERROR: safAgentName cannot be null or empty");
      }
   }

   public static void deleteSAFAgent(Context ctx, String safAgentName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteSAFAgent(domain, safAgentName);
         success = true;
      } finally {
         endEditSession(manager, safAgentName, success);
      }

   }

   public static void deleteSAFAgent(DomainMBean domain, String safAgentName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            SAFAgentMBean safAgent = domain.lookupSAFAgent(safAgentName);
            if (safAgent == null) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInDomainLoggable(domain.getName(), "SAFAgent", safAgentName).getMessage());
            } else {
               TargetMBean[] targetMBeans = safAgent.getTargets();

               for(int i = 0; i < targetMBeans.length; ++i) {
                  safAgent.removeTarget(targetMBeans[i]);
               }

               domain.destroySAFAgent(safAgent);
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromDomainLoggable(domain.getName(), "SAFAgent", safAgentName).getMessage(), var5);
         }
      }
   }

   public static void deploySAFAgent(Context ctx, String safAgentName, String targetName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deploySAFAgent(domain, safAgentName, targetName);
         success = true;
      } finally {
         endEditSession(manager, safAgentName, success);
      }

   }

   public static void deploySAFAgent(DomainMBean domain, String safAgentName, String targetName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         SAFAgentMBean safAgent = domain.lookupSAFAgent(safAgentName);
         if (safAgent == null) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInDomainLoggable(domain.getName(), "SAFAgent", safAgentName).getMessage());
         } else {
            try {
               TargetMBean[] targetMBeans = targetNames2TargetMBeans(domain, targetName);

               for(int i = 0; i < targetMBeans.length; ++i) {
                  if (targetMBeans[i] instanceof MigratableTargetMBean || targetMBeans[i] instanceof JMSServerMBean || targetMBeans[i] instanceof SAFAgentMBean) {
                     throw new JMSException("ERROR: MigratableTarget, JMSServer and SAFAgent cannot be set as target for SAFAgent");
                  }
               }

               safAgent.setTargets(targetMBeans);
            } catch (Exception var6) {
               throw new weblogic.jms.common.JMSException("ERROR: Could not deploy SAFAgent " + safAgentName + " in the domain " + domain.getName(), var6);
            }
         }
      }
   }

   public static void undeploySAFAgent(Context ctx, String safAgentName) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         undeploySAFAgent(domain, safAgentName);
         success = true;
      } finally {
         endEditSession(manager, safAgentName, success);
      }

   }

   public static void undeploySAFAgent(DomainMBean domain, String safAgentName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         SAFAgentMBean safAgent = domain.lookupSAFAgent(safAgentName);
         if (safAgent == null) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(domain.getName(), "SAFAgent", safAgentName).getMessage());
         } else {
            TargetMBean[] targetMBeans = safAgent.getTargets();
            if (targetMBeans != null && targetMBeans.length != 0) {
               try {
                  for(int i = 0; i < targetMBeans.length; ++i) {
                     safAgent.removeTarget(targetMBeans[i]);
                  }

               } catch (Exception var5) {
                  throw new weblogic.jms.common.JMSException("ERROR: Could not deploy SAFAgent " + safAgentName + " in the domain " + domain.getName(), var5);
               }
            } else {
               throw new JMSException("SAFAgent " + safAgentName + " is not currently deployed");
            }
         }
      }
   }

   private static TargetMBean findMatchingTargetMBean(DomainMBean domain, String targetName) throws JMSException {
      TargetMBean target = domain.lookupMigratableTarget(targetName);
      if (target == null) {
         target = domain.lookupCluster(targetName);
         if (target == null) {
            target = domain.lookupServer(targetName);
            if (target == null) {
               target = domain.lookupJMSServer(targetName);
               if (target == null) {
                  target = domain.lookupSAFAgent(targetName);
                  if (target == null) {
                     throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(domain.getName(), "MigratableTarget/Cluster/Server/JMSServer/SAFAgent", targetName).getMessage());
                  }
               }
            }
         }
      }

      return (TargetMBean)target;
   }

   private static TargetMBean[] targetNames2TargetMBeans(DomainMBean domain, String targetName) throws JMSException {
      String[] targetNames = targetName.split(",");
      TargetMBean[] targetMBeans = new TargetMBean[targetNames.length];

      for(int i = 0; i < targetNames.length; ++i) {
         targetMBeans[i] = findMatchingTargetMBean(domain, targetNames[i]);
      }

      return targetMBeans;
   }

   private static void deleteDestination(Context ctx, String resourceName, String destinationName, String destinationType) throws JMSException {
      ConfigurationManagerMBean manager = JMSEditHelper.getConfigurationManager(ctx);
      DomainMBean domain = beginEditSession(manager);
      boolean success = false;

      try {
         deleteDestination(domain, resourceName, destinationName, destinationType);
         success = true;
      } finally {
         endEditSession(manager, destinationName, success);
      }

   }

   private static void deleteDestination(DomainMBean domain, String resourceName, String destinationName, String destinationType) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: domain cannot be null");
      } else {
         try {
            JMSBean jmsBean = getJMSBean(domain, resourceName);
            if (destinationType.equals("JMSQueue")) {
               QueueBean queueBean = jmsBean.lookupQueue(destinationName);
               if (queueBean == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, destinationType, destinationName).getMessage());
               }

               jmsBean.destroyQueue(queueBean);
            } else if (destinationType.equals("JMSTopic")) {
               TopicBean topicBean = jmsBean.lookupTopic(destinationName);
               if (topicBean == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, destinationType, destinationName).getMessage());
               }

               jmsBean.destroyTopic(topicBean);
            } else if (destinationType.equals("UniformDistributedQueue")) {
               UniformDistributedQueueBean udQueueBean = jmsBean.lookupUniformDistributedQueue(destinationName);
               if (udQueueBean == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, destinationType, destinationName).getMessage());
               }

               jmsBean.destroyUniformDistributedQueue(udQueueBean);
            } else if (destinationType.equals("UniformDistributedTopic")) {
               UniformDistributedTopicBean udTopicBean = jmsBean.lookupUniformDistributedTopic(destinationName);
               if (udTopicBean == null) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logEntityNotFoundInJMSSystemResourceLoggable(resourceName, destinationType, destinationName).getMessage());
               }

               jmsBean.destroyUniformDistributedTopic(udTopicBean);
            }

         } catch (Exception var6) {
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCannotDeleteEntityFromJMSSystemResourceLoggable(resourceName, destinationType, destinationName).getMessage(), var6);
         }
      }
   }

   private static void activateEdit(ConfigurationManagerMBean configurationManager) throws JMSException {
      try {
         configurationManager.save();
      } catch (NotEditorException var4) {
         throw new weblogic.jms.common.JMSException("ERROR: Not editor while saving edit", var4);
      } catch (ValidationException var5) {
         throw new weblogic.jms.common.JMSException("ERROR: Validation error while saving edit", var5);
      }

      ActivationTaskMBean atm;
      try {
         atm = configurationManager.activate(-1L);
      } catch (NotEditorException var3) {
         throw new weblogic.jms.common.JMSException("ERROR: Edit session activation failed", var3);
      }

      Exception e = atm.getError();
      if (e != null) {
         throw new weblogic.jms.common.JMSException("ERROR: Edit session activation failed. Reason:" + e.getMessage(), e);
      }
   }

   public static String uddMakeName(String serverName, String name) {
      name = JMSServerUtilities.transformJNDIName(name);
      int index = name.indexOf("!");
      String baseName;
      if (index != -1) {
         baseName = name.substring(index + 1);
      } else {
         baseName = name;
      }

      return serverName + "@" + baseName;
   }

   public static String uddMemberName(String jmsServerName, String name) {
      return uddMakeName(jmsServerName, name);
   }

   public static String uddMemberJNDIName(String jmsServerName, String name) {
      return uddMakeName(jmsServerName, name);
   }

   private static void uddFillWithJMSServers(Map fillMe, DomainMBean domain, ServerMBean[] servers, BasicDeploymentMBean basic, String entityType, String entityName, boolean isJMSResourceDefinition) {
      if (servers != null) {
         for(int i = 0; i < servers.length; ++i) {
            uddFillWithJMSServers(fillMe, domain, servers[i], basic, entityType, entityName, isJMSResourceDefinition);
         }

      }
   }

   private static void uddFillWithJMSServers(Map fillMe, DomainMBean domain, ServerMBean server, BasicDeploymentMBean basic, String entityType, String entityName, boolean isJMSResourceDefinition) {
      if (server != null) {
         WebLogicMBean deploymentScope = getDeploymentScope(basic);
         boolean isDeployedToRGT = AppDeploymentHelper.isDeployedThroughRGT(basic);
         List ignoredJMSServers = new ArrayList();
         JMSServerMBean[] allJMSServers = getCandidateJMSServers(domain, deploymentScope, "Distributed", isDeployedToRGT, ignoredJMSServers);
         if (!ignoredJMSServers.isEmpty()) {
            if (isJMSResourceDefinition) {
               JMSLogger.logJMSResDefRestrictedJMSServerHosts(ignoredJMSServers.toString(), "Distributed", entityName, getConfigMBeanShortName(deploymentScope, basic), getDeploymentScopeAsString(basic));
            } else {
               JMSLogger.logRestrictedJMSServerHosts(ignoredJMSServers.toString(), "Distributed", getDeploymentScopeAsString(basic), entityName);
            }
         }

         for(int i = 0; i < allJMSServers.length; ++i) {
            TargetMBean[] targets = allJMSServers[i].getTargets();
            if (targets != null && targets.length != 0) {
               TargetMBean target = targets[0];
               if (target.getName().equals(server.getName())) {
                  if (fillMe.put(allJMSServers[i].getName(), allJMSServers[i].getName()) != null) {
                     throw new IllegalArgumentException("Targets of UDD deployment overlap. JMSServer named: " + allJMSServers[i].getName() + " is been identified twice as potential JMSServer for hosting the UDD's members under the UDD's targeting scope");
                  }
               } else {
                  ClusterMBean svrCluster = server.getCluster();
                  if (svrCluster != null && target instanceof MigratableTargetMBean) {
                     ClusterMBean mtCluster = ((MigratableTargetMBean)target).getCluster();
                     ServerMBean[] candidateServers = ((MigratableTargetMBean)target).getAllCandidateServers();
                     if (candidateServers != null) {
                        if (candidateServers.length == 0) {
                           if (mtCluster != null && svrCluster.getName().equals(mtCluster.getName()) && fillMe.get(allJMSServers[i].getName()) == null) {
                              fillMe.put(allJMSServers[i].getName(), allJMSServers[i].getName());
                           }
                        } else {
                           for(int j = 0; j < candidateServers.length; ++j) {
                              if (candidateServers[j].getName().equals(server.getName()) && fillMe.get(allJMSServers[i].getName()) == null) {
                                 fillMe.put(allJMSServers[i].getName(), allJMSServers[i].getName());
                              }
                           }
                        }
                     }
                  } else if (svrCluster != null && target instanceof ClusterMBean && svrCluster.getName().equals(target.getName()) && fillMe.get(allJMSServers[i].getName()) == null) {
                     PersistentStoreMBean store = allJMSServers[i].getPersistentStore();
                     if (store != null && store.getDistributionPolicy().equalsIgnoreCase("Singleton")) {
                        if (JMSDebug.JMSModule.isDebugEnabled()) {
                           JMSDebug.JMSModule.debug("JMSServer " + allJMSServers[i] + " targeted to Cluster " + svrCluster.getName() + " is filtered out of UDD targets, since it is configured with Singleton Policy");
                        }
                     } else {
                        fillMe.put(allJMSServers[i].getName(), allJMSServers[i].getName());
                     }
                  }
               }
            }
         }

      }
   }

   public static void uddFillWithMyTargets(Map fillMe, DomainMBean domain, SubDeploymentMBean subDeploymentBean, BasicDeploymentMBean basic, String entityType, String entityName, boolean isJMSResourceDefinition) {
      if (subDeploymentBean == null || subDeploymentBean.getTargets() != null && subDeploymentBean.getTargets().length > 0 && !(subDeploymentBean.getTargets()[0] instanceof VirtualTargetMBean)) {
         uddFillWithMyTargets(fillMe, domain, subDeploymentBean.getTargets(), basic, entityType, entityName, false);
      }
   }

   private static void uddFillWithJMSServers(Map fillMe, DomainMBean domain, DynamicServersMBean dynamicServers, BasicDeploymentMBean basic) {
      WebLogicMBean deploymentScope = getDeploymentScope(basic);
      boolean isDeployedToRGT = AppDeploymentHelper.isDeployedThroughRGT(basic);
      if (dynamicServers != null && dynamicServers.getMaximumDynamicServerCount() > 0) {
         JMSServerMBean[] allJMSServers = getCandidateJMSServers(domain, deploymentScope, "Distributed", isDeployedToRGT);

         for(int i = 0; i < allJMSServers.length; ++i) {
            TargetMBean[] targets = allJMSServers[i].getTargets();
            if (targets != null && targets.length != 0) {
               TargetMBean target = targets[0];
               ClusterMBean svrCluster = (ClusterMBean)dynamicServers.getParent();
               if (svrCluster != null && target instanceof MigratableTargetMBean) {
                  ClusterMBean mtCluster = ((MigratableTargetMBean)target).getCluster();
                  ServerMBean[] candidateServers = ((MigratableTargetMBean)target).getAllCandidateServers();
                  if (candidateServers != null && candidateServers.length == 0 && mtCluster != null && svrCluster.getName().equals(mtCluster.getName()) && fillMe.get(allJMSServers[i].getName()) == null) {
                     fillMe.put(allJMSServers[i].getName(), allJMSServers[i].getName());
                  }
               } else if (svrCluster != null && target instanceof ClusterMBean && svrCluster.getName().equals(target.getName()) && fillMe.get(allJMSServers[i].getName()) == null) {
                  PersistentStoreMBean store = allJMSServers[i].getPersistentStore();
                  if (store != null && store.getDistributionPolicy().equalsIgnoreCase("Singleton")) {
                     if (JMSDebug.JMSModule.isDebugEnabled()) {
                        JMSDebug.JMSModule.debug("JMSServer " + allJMSServers[i] + " targeted to Cluster " + svrCluster.getName() + " is filtered out of UDD targets, since it is configured with Singleton Policy");
                     }
                  } else {
                     fillMe.put(allJMSServers[i].getName(), allJMSServers[i].getName());
                  }
               }
            }
         }

      }
   }

   public static void uddFillWithMyTargets(Map fillMe, DomainMBean domain, TargetMBean[] targetBeans, BasicDeploymentMBean basic, String entityType, String entityName, boolean isJMSResourceDefinition) {
      WebLogicMBean deploymentScope = getDeploymentScope(basic);
      if (targetBeans != null && targetBeans.length != 0) {
         ServerMBean[] memberServers;
         for(int i = 0; i < targetBeans.length; ++i) {
            TargetMBean targetBean = targetBeans[i];
            if (targetBean instanceof ClusterMBean) {
               ServerMBean[] servers = ((ClusterMBean)targetBean).getServers();
               uddFillWithJMSServers(fillMe, domain, servers, basic, entityType, entityName, isJMSResourceDefinition);
               uddFillWithJMSServers(fillMe, domain, ((ClusterMBean)targetBean).getDynamicServers(), basic);
            } else if (targetBean instanceof ServerMBean) {
               uddFillWithJMSServers(fillMe, domain, (ServerMBean)targetBean, basic, entityType, entityName, isJMSResourceDefinition);
            } else if (targetBean instanceof JMSServerMBean) {
               PersistentStoreMBean store = ((JMSServerMBean)targetBean).getPersistentStore();
               if (store != null && store.getDistributionPolicy().equalsIgnoreCase("Singleton")) {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("JMSServer " + targetBean.getName() + " is filtered out of UDD targets, since it is configured with Singleton Policy");
                  }
               } else if (fillMe.put(targetBean.getName(), targetBean.getName()) != null) {
                  throw new IllegalArgumentException("Targets of UDD deployment overlap. JMSServer named: " + targetBean.getName() + " is been identified twice as potential JMSServer for hosting the UDD's members under the UDD's targeting scope");
               }
            } else {
               if (!(targetBean instanceof VirtualTargetMBean)) {
                  throw new IllegalArgumentException("A UDD cannot be targeted to a migratable target");
               }

               TargetMBean[] targetMBeans = ((VirtualTargetMBean)targetBean).getTargets();
               TargetMBean[] var11 = targetMBeans;
               int var12 = targetMBeans.length;

               for(int var13 = 0; var13 < var12; ++var13) {
                  TargetMBean targetMBean = var11[var13];
                  if (targetMBeans.length != 0 && targetMBean instanceof ClusterMBean) {
                     memberServers = ((ClusterMBean)targetMBean).getServers();
                     uddFillWithJMSServers(fillMe, domain, memberServers, basic, entityType, entityName, isJMSResourceDefinition);
                     uddFillWithJMSServers(fillMe, domain, ((ClusterMBean)targetMBeans[0]).getDynamicServers(), basic);
                  } else if (targetMBean instanceof ServerMBean) {
                     uddFillWithJMSServers(fillMe, domain, (ServerMBean)targetMBean, basic, entityType, entityName, isJMSResourceDefinition);
                  }
               }
            }
         }

         if (fillMe.isEmpty()) {
            if (isJMSResourceDefinition) {
               JMSLogger.logJMSResDefnMatchingJMSServerNotFound(entityName, getConfigMBeanShortName(deploymentScope, basic), getDeploymentScopeAsString(basic));
            } else {
               JMSLogger.logMatchingJMSServerNotFound("Distributed", entityType, entityName, getConfigMBeanShortName(deploymentScope, basic), getDeploymentScopeAsString(basic));
            }
         }

         if (fillMe.size() > 1 && deploymentScope instanceof ResourceGroupTemplateMBean) {
            if (isJMSResourceDefinition) {
               throw new IllegalArgumentException(JMSExceptionLogger.logJMSResDefnMultipleCandidateJMSServersLoggable(entityName, getConfigMBeanShortName(deploymentScope, basic), getDeploymentScopeAsString(basic)).getMessage());
            } else {
               throw new IllegalArgumentException(JMSExceptionLogger.logMultipleCandidateJMSServersLoggable("Distributed", entityType, entityName, getConfigMBeanShortName(deploymentScope, basic), getDeploymentScopeAsString(basic), getConfigMBeanShortNames(deploymentScope, fillMe.values())).getMessage());
            }
         } else {
            String jmsServerMBeanName;
            if (fillMe.size() != 1) {
               Iterator iter = fillMe.values().iterator();
               String clusterName = null;

               while(iter.hasNext()) {
                  jmsServerMBeanName = (String)iter.next();
                  JMSServerMBean myBean = domain.lookupJMSServer(jmsServerMBeanName);
                  if (myBean.getTargets().length != 0) {
                     TargetMBean target = myBean.getTargets()[0];
                     ClusterMBean cluster;
                     if (target instanceof ClusterMBean) {
                        cluster = (ClusterMBean)target;
                     } else if (target instanceof MigratableTargetMBean) {
                        cluster = ((MigratableTargetMBean)target).getCluster();
                     } else {
                        cluster = ((ServerMBean)target).getCluster();
                     }

                     String myClusterName;
                     if (cluster == null) {
                        myClusterName = "Stand Alone Server " + target.getName();
                     } else {
                        myClusterName = "Cluster " + cluster.getName();
                     }

                     if (clusterName == null) {
                        clusterName = myClusterName;
                     } else if (!myClusterName.equals(clusterName)) {
                        throw new IllegalArgumentException("A UDD must be targeted to servers within a single cluster or a single stand-alone server, rather than " + clusterName + " and " + myClusterName);
                     }
                  }
               }
            }

            Map fillMeWithInstances = new HashMap();
            Iterator iter = fillMe.values().iterator();

            while(true) {
               while(true) {
                  JMSServerMBean myBean;
                  do {
                     if (!iter.hasNext()) {
                        if (fillMeWithInstances.size() != 0) {
                           fillMe.clear();
                           Iterator it = fillMeWithInstances.keySet().iterator();

                           while(it.hasNext()) {
                              String instanceName = (String)it.next();
                              fillMe.put(instanceName, fillMeWithInstances.get(instanceName));
                           }
                        }

                        return;
                     }

                     jmsServerMBeanName = (String)iter.next();
                     myBean = domain.lookupJMSServer(jmsServerMBeanName);
                  } while(myBean.getTargets().length == 0);

                  TargetMBean target = myBean.getTargets()[0];
                  String jmsServerInstanceName;
                  if (target instanceof ClusterMBean) {
                     ClusterMBean cluster = (ClusterMBean)target;
                     if (cluster.getDynamicServers().getMaximumDynamicServerCount() > 0) {
                        for(int i = 0; i < cluster.getDynamicServers().getMaximumDynamicServerCount(); ++i) {
                           String serverInstanceName = cluster.getDynamicServers().getServerNamePrefix() + (cluster.getDynamicServers().getServerNameStartingIndex() + i);
                           jmsServerInstanceName = GenericDeploymentManager.getDecoratedDistributedInstanceName(myBean, serverInstanceName);
                           fillMeWithInstances.put(jmsServerInstanceName, jmsServerMBeanName);
                        }
                     }

                     memberServers = cluster.getServers();

                     for(int i = 0; i < memberServers.length; ++i) {
                        jmsServerInstanceName = GenericDeploymentManager.getDecoratedDistributedInstanceName(myBean, memberServers[i].getName());
                        fillMeWithInstances.put(jmsServerInstanceName, jmsServerMBeanName);
                     }
                  } else {
                     jmsServerInstanceName = GenericDeploymentManager.getDecoratedDistributedInstanceName(myBean, (String)null);
                     fillMeWithInstances.put(jmsServerInstanceName, jmsServerMBeanName);
                  }
               }
            }
         }
      }
   }

   public static String[] uddReturnJMSServers(DomainMBean domain, SubDeploymentMBean subDeploymentBean, String entityName) {
      Map fillMe = new HashMap();
      WebLogicMBean basicDeployment = subDeploymentBean.getParent();
      if (basicDeployment instanceof BasicDeploymentMBean) {
         if (!(getDeploymentScope((BasicDeploymentMBean)basicDeployment) instanceof DomainMBean)) {
            throw new IllegalArgumentException("The SubDeployment " + subDeploymentBean.getName() + " is not deployed in Global scope. Please call relevant API in IJMSModuleHelper for JMS resource deployed in resource group and resource group template.");
         }

         uddFillWithMyTargets(fillMe, domain, (SubDeploymentMBean)subDeploymentBean, (BasicDeploymentMBean)basicDeployment, "Uniform Distributed Queue/Topic", entityName, false);
      }

      return (String[])((String[])fillMe.keySet().toArray(new String[1]));
   }

   private static JMSSystemResourceMBean findJMSSystemResource(DomainMBean domain, String resourceName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: Invalid domain: DomainMBean cannot be null ");
      } else if (resourceName != null && !resourceName.trim().equals("")) {
         JMSSystemResourceMBean resource = domain.lookupJMSSystemResource(resourceName);
         if (resource == null) {
            throw new JMSException("ERROR: Could not find JMSSystemResource " + resourceName + " in the domain " + domain.getName());
         } else {
            return resource;
         }
      } else {
         throw new JMSException("ERROR: Invalid JMS System Resource Name: resource name cannot be null or empty");
      }
   }

   private static JMSBean getJMSBean(DomainMBean domain, String resourceName) throws JMSException {
      JMSSystemResourceMBean resource = findJMSSystemResource(domain, resourceName);
      return resource.getJMSResource();
   }

   private static DomainMBean beginEditSession(ConfigurationManagerMBean manager) throws JMSException {
      DomainMBean domain = null;

      try {
         domain = manager.startEdit(-1, -1);
         return domain;
      } catch (EditTimedOutException var3) {
         throw new weblogic.jms.common.JMSException("ERROR: Unable to start the edit session", var3);
      }
   }

   private static void endEditSession(ConfigurationManagerMBean manager, String entityName, boolean result) throws JMSException {
      if (!result) {
         manager.cancelEdit();
      } else {
         try {
            activateEdit(manager);
         } catch (JMSException var5) {
            manager.cancelEdit();
            Throwable cause = var5.getCause();
            throw new weblogic.jms.common.JMSException("ERROR: Could not activate update for the entity " + entityName + ". The edit session was cancelled and the changes made in this edit session were discarded. \nREASON: ", (Throwable)(cause == null ? var5 : cause));
         }
      }

   }

   private static SubDeploymentMBean findOrCreateSubDeployment(JMSSystemResourceMBean resource, String name) {
      SubDeploymentMBean subDeployment = resource.lookupSubDeployment(name);
      if (subDeployment == null) {
         subDeployment = resource.createSubDeployment(name);
      }

      return subDeployment;
   }

   public static boolean isTargetInDeploymentScope(ConfigurationMBean configMBean, WebLogicMBean deploymentScope) {
      ConfigurationMBean clonedTargetBean = ((ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0])).toOriginalBean(configMBean);
      WebLogicMBean configBeanScope = clonedTargetBean.getParent();
      return isScopeEqual(deploymentScope, configBeanScope);
   }

   public static JMSServerMBean[] getCandidateJMSServers(DomainMBean domain, WebLogicMBean deploymentScope, String distributionPolicyCriteria, boolean isRGTDeployment) {
      return getCandidateJMSServers(domain, deploymentScope, distributionPolicyCriteria, isRGTDeployment, (List)null);
   }

   public static JMSServerMBean[] getCandidateJMSServers(DomainMBean domain, WebLogicMBean deploymentScope, String distributionPolicyCriteria, boolean isRGTDeployment, List ignoredJMSServers) {
      JMSServerMBean[] allJMSServers = domain.getJMSServers();
      List filteredJMSServers = new ArrayList();
      JMSServerMBean[] candidateJMSServers = allJMSServers;
      int var8 = allJMSServers.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         JMSServerMBean jmsServerBean = candidateJMSServers[var9];
         if (isTargetInDeploymentScope(jmsServerBean, deploymentScope)) {
            PersistentStoreMBean store = jmsServerBean.getPersistentStore();
            if (deploymentScope instanceof DomainMBean) {
               if (store != null) {
                  if (store.getDistributionPolicy().equalsIgnoreCase(distributionPolicyCriteria)) {
                     filteredJMSServers.add(jmsServerBean);
                  } else if (ignoredJMSServers != null) {
                     ignoredJMSServers.add(jmsServerBean.getName());
                  }
               } else {
                  filteredJMSServers.add(jmsServerBean);
               }
            } else if (distributionPolicyCriteria != null && !"".equals(distributionPolicyCriteria)) {
               if (store != null && store.getDistributionPolicy().equalsIgnoreCase(distributionPolicyCriteria)) {
                  filteredJMSServers.add(jmsServerBean);
               } else if (ignoredJMSServers != null) {
                  ignoredJMSServers.add(jmsServerBean.getName());
               }
            } else {
               filteredJMSServers.add(jmsServerBean);
            }
         }
      }

      candidateJMSServers = (JMSServerMBean[])filteredJMSServers.toArray(new JMSServerMBean[0]);
      if (isRGTDeployment && deploymentScope instanceof ResourceGroupMBean) {
         ResourceGroupTemplateMBean associatedRGT = ((ResourceGroupMBean)deploymentScope).getResourceGroupTemplate();
         if (associatedRGT == null) {
            throw new AssertionError("When a JMS module is deployed to a resource group template, the resource group must have a resource group template associated to it");
         }

         associatedRGT = domain.lookupResourceGroupTemplate(associatedRGT.getName());
         if (associatedRGT == null) {
            throw new AssertionError("When a JMS module is deployed to a resource group template, the resource group template associated to the resource group must exist in the domain.");
         }

         candidateJMSServers = (JMSServerMBean[])((JMSServerMBean[])excludeRGDefinedJMSTargets((ResourceGroupMBean)deploymentScope, associatedRGT, filteredJMSServers, JMSServerMBean.class));
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModuleHelper:getCandidateJMSServers() : candidateJMSServers " + Arrays.toString(candidateJMSServers));
      }

      return candidateJMSServers;
   }

   public static TargetMBean[] excludeRGDefinedJMSTargets(ResourceGroupMBean resourceGroupBean, ResourceGroupTemplateMBean associatedRGT, List availableJMSTargets, Class jmsTargetType) {
      assert associatedRGT != null;

      ArrayList candidateResources = new ArrayList();
      Object rgtDefinedTargets;
      if (JMSServerMBean.class.equals(jmsTargetType)) {
         rgtDefinedTargets = associatedRGT.getJMSServers();
      } else {
         if (!SAFAgentMBean.class.equals(jmsTargetType)) {
            throw new AssertionError("Invalid JMS target type '" + jmsTargetType + "'. Only JMSServerMBean/SAFAgentMBean can be valid target types");
         }

         rgtDefinedTargets = associatedRGT.getSAFAgents();
      }

      Iterator var6 = availableJMSTargets.iterator();

      while(true) {
         while(var6.hasNext()) {
            TargetMBean aClonedJMSTarget = (TargetMBean)var6.next();
            String clonedJMSTargetName = getConfigMBeanShortName(resourceGroupBean, aClonedJMSTarget);
            Object var9 = rgtDefinedTargets;
            int var10 = ((Object[])rgtDefinedTargets).length;

            for(int var11 = 0; var11 < var10; ++var11) {
               TargetMBean aRGTDefinedTarget = ((Object[])var9)[var11];
               if (((TargetMBean)aRGTDefinedTarget).getName().equals(clonedJMSTargetName)) {
                  candidateResources.add(aClonedJMSTarget);
                  break;
               }
            }
         }

         if (JMSServerMBean.class.equals(jmsTargetType)) {
            return (TargetMBean[])candidateResources.toArray(new JMSServerMBean[0]);
         }

         if (SAFAgentMBean.class.equals(jmsTargetType)) {
            return (TargetMBean[])candidateResources.toArray(new SAFAgentMBean[0]);
         }

         return new TargetMBean[0];
      }
   }

   public static WebLogicMBean getDeploymentScope(ApplicationContextInternal applicationContext) {
      BasicDeploymentMBean basicDeploymentMBean = applicationContext.getBasicDeploymentMBean();
      return getDeploymentScope(basicDeploymentMBean);
   }

   public static WebLogicMBean getDeploymentScope(BasicDeploymentMBean basicDeploymentMBean) {
      return basicDeploymentMBean != null ? ((BasicDeploymentMBean)((ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0])).toOriginalBean(basicDeploymentMBean)).getParent() : null;
   }

   public static boolean isSubDeploymentTargeted(JMSSystemResourceMBean jmsSystemResource, String subdeploymentName) {
      SubDeploymentMBean subDeploymentMBean = jmsSystemResource.lookupSubDeployment(subdeploymentName);
      if (subDeploymentMBean == null) {
         return false;
      } else {
         TargetMBean[] subDeploymentTargets = subDeploymentMBean.getTargets();
         if (subDeploymentTargets != null && subDeploymentTargets.length != 0) {
            if (subDeploymentTargets[0] instanceof VirtualTargetMBean) {
               return false;
            } else if (!(subDeploymentTargets[0] instanceof JMSServerMBean) && !(subDeploymentTargets[0] instanceof SAFAgentMBean)) {
               throw new AssertionError("Subdeployment " + subdeploymentName + " used by an entity in a JMS module deployed to Resource Group or Resource Group Template is allowed to have a JMS Server or a SAF Agent or null as the target.");
            } else {
               return true;
            }
         } else {
            return false;
         }
      }
   }

   public static String getDeploymentScopeAsString(ApplicationContextInternal applicationContext) {
      return applicationContext == null ? "" : getDeploymentScopeAsString(applicationContext.getBasicDeploymentMBean());
   }

   public static String getDeploymentScopeAsString(BasicDeploymentMBean basic) {
      if (basic == null) {
         return "";
      } else {
         WebLogicMBean deploymentScope = getDeploymentScope(basic);
         if (deploymentScope instanceof ResourceGroupTemplateMBean) {
            boolean isDeployedThroughRGT = AppDeploymentHelper.isDeployedThroughRGT(basic);
            String rgtName = isDeployedThroughRGT ? ((ResourceGroupMBean)deploymentScope).getResourceGroupTemplate().getName() : deploymentScope.getName();
            return isDeployedThroughRGT ? "Resource Group Template \"" + rgtName + "\"" : "Resource Group \"" + rgtName + "\"";
         } else {
            return "WebLogic domain " + deploymentScope.getName();
         }
      }
   }

   public static String getConfigMBeanShortName(WebLogicMBean appDeploymentScope, ConfigurationMBean configMBean) {
      return configMBean == null ? "" : ((ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0])).toOriginalBean(configMBean).getName();
   }

   public static String getConfigMBeanShortNames(WebLogicMBean appDeploymentScope, Collection values) {
      StringBuilder sb = new StringBuilder();
      Iterator valuesIter = values.iterator();
      if (!valuesIter.hasNext()) {
         return sb.append("[]").toString();
      } else {
         sb.append("[");

         while(true) {
            Object aValue = valuesIter.next();
            if (aValue instanceof String) {
               String targetName = (String)aValue;
               String partitionName = PartitionUtils.getPartitionName();
               sb.append(PartitionUtils.stripDecoratedPartitionName(partitionName, targetName));
            } else if (aValue instanceof ConfigurationMBean) {
               sb.append(getConfigMBeanShortName(appDeploymentScope, (ConfigurationMBean)aValue));
            }

            if (!valuesIter.hasNext()) {
               return sb.append("]").toString();
            }

            sb.append(",").append(' ');
         }
      }
   }

   public static boolean isScopeEqual(WebLogicMBean aScope, WebLogicMBean otherScope) {
      if (aScope instanceof DomainMBean && otherScope instanceof DomainMBean) {
         return true;
      } else {
         WebLogicMBean aScopeParent = aScope.getParent();
         WebLogicMBean otherScopeParent = otherScope.getParent();
         if (aScope instanceof ResourceGroupMBean && otherScope instanceof ResourceGroupMBean) {
            if (aScopeParent instanceof DomainMBean && otherScopeParent instanceof DomainMBean) {
               return aScope.getName().equals(otherScope.getName());
            } else if (aScopeParent instanceof PartitionMBean && otherScopeParent instanceof PartitionMBean) {
               return aScope.getName().equals(otherScope.getName()) && aScopeParent.getName().equals(otherScopeParent.getName());
            } else {
               return false;
            }
         } else if (aScope instanceof ResourceGroupTemplateMBean && otherScope instanceof ResourceGroupTemplateMBean) {
            if (aScopeParent instanceof DomainMBean && otherScopeParent instanceof DomainMBean) {
               return aScope.getName().equals(otherScope.getName()) && aScopeParent.getName().equals(otherScopeParent.getName());
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }
}
