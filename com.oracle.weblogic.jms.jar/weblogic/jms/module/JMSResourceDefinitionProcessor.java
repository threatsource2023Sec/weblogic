package weblogic.jms.module;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.application.naming.Environment;
import weblogic.application.naming.Environment.EnvType;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;
import weblogic.j2ee.descriptor.JmsDestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.jndi.internal.JNDIHelper;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;

public class JMSResourceDefinitionProcessor {
   private final Map namingContexts = new HashMap();
   private final Lock readWriteLock = new ReentrantLock();
   private Set jmsModules = new HashSet();
   private String applicationId = null;
   private String applicationName = null;
   private String componentName = null;
   private String moduleId = null;
   private Environment.EnvType envType = null;
   private String partitionNamePrefix = null;

   public JMSResourceDefinitionProcessor(String applicationId, String componentName, String moduleId, Environment.EnvType envType, Map namingContexts) {
      this.applicationId = applicationId;
      this.componentName = componentName;
      this.moduleId = moduleId;
      this.envType = envType;
      this.namingContexts.putAll(namingContexts);
   }

   private Context getNamingContext(String jndiName, String moduleNamePrefix, JMSResourceDefinitionManager resourceDefinitionManager) {
      this.moduleId = this.moduleId.replace('.', '_');
      if (this.moduleId != null && this.applicationId != null) {
         try {
            Context rootCtx = (new weblogic.jndi.Environment()).getInitialContext();
            Context globalJavaAppContext = (Context)rootCtx.lookup("__WL_GlobalJavaApp");
            Context applicationSubContext = globalJavaAppContext.createSubcontext(this.applicationId);
            if (applicationSubContext == null) {
               applicationSubContext = globalJavaAppContext.createSubcontext(this.applicationId);
            }

            Context appClientContext = applicationSubContext.createSubcontext(this.moduleId);
            appClientContext.addToEnvironment("weblogic.jndi.createIntermediateContexts", Boolean.TRUE.toString());
            resourceDefinitionManager.addApplicationClientNode(this.moduleId, moduleNamePrefix);
            return appClientContext;
         } catch (NamingException var8) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("JMSResourceDefinitionProcessor:getNamingContext NamingException : " + var8.getMessage());
            }
         }
      }

      return null;
   }

   private Context getNamingContext(String jndiName) {
      if (jndiName.startsWith("java:global")) {
         return (Context)this.namingContexts.get("java:global");
      } else if (jndiName.startsWith("java:app")) {
         return (Context)this.namingContexts.get("java:app");
      } else {
         return jndiName.startsWith("java:module") ? (Context)this.namingContexts.get("java:module") : (Context)this.namingContexts.get("java:comp");
      }
   }

   void bind(ApplicationContextInternal applicationContext, Set connectionFactories, Set destinations) throws ModuleException {
      JMSModule jmsModule = null;
      String jmsModuleName = null;
      String jndiName = null;
      boolean isModuleNodeRequired = false;
      Context namingContext = null;
      boolean isClient = EnvType.CLIENT.equals(this.envType);
      JMSService jmsService = JMSService.getJMSServiceWithModuleException();
      JMSResourceDefinitionManager resourceDefinitionManager = jmsService.getJMSResourceDefinitionManager();
      WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(applicationContext);
      if (deploymentScope instanceof ResourceGroupTemplateMBean) {
         this.applicationName = JMSModuleHelper.getConfigMBeanShortName(deploymentScope, applicationContext.getAppDeploymentMBean());
         if (!"DOMAIN".equals(applicationContext.getPartitionName())) {
            this.partitionNamePrefix = applicationContext.getPartitionName() + "_";
         }
      } else {
         this.applicationName = this.applicationId;
      }

      String moduleNamePrefix = this.partitionNamePrefix != null ? this.partitionNamePrefix + this.applicationName + "_" : this.applicationName + "_";
      this.readWriteLock.lock();
      Map validJMSModulesMap = resourceDefinitionManager.getAllJMSModules();

      try {
         JMSModule moduleToDeactivate;
         try {
            Iterator var15 = connectionFactories.iterator();

            JMSBean previousJMSBean;
            while(var15.hasNext()) {
               JmsConnectionFactoryBean connectionFactory = (JmsConnectionFactoryBean)var15.next();
               jndiName = connectionFactory.getName();
               if (!isClient || !jndiName.startsWith("java:module") && !jndiName.startsWith("java:comp")) {
                  namingContext = this.getNamingContext(jndiName);
               } else {
                  namingContext = this.getNamingContext(jndiName, moduleNamePrefix, resourceDefinitionManager);
                  isModuleNodeRequired = true;
               }

               jmsModuleName = JMSResourceDefinitionHelper.generateUniqueModuleName(jndiName, this.envType, this.componentName, this.moduleId, moduleNamePrefix);
               previousJMSBean = null;
               if (validJMSModulesMap.containsKey(jmsModuleName)) {
                  moduleToDeactivate = (JMSModule)validJMSModulesMap.get(jmsModuleName);
                  previousJMSBean = (JMSBean)moduleToDeactivate.getModuleDescriptor();
                  if (previousJMSBean.getConnectionFactories().length < 1) {
                     throw new ModuleException(JMSExceptionLogger.logJNDINameAlreadyBoundLoggable(jndiName, this.applicationName, "JMS Connection Factory").getMessage());
                  }
               }

               jmsModule = JMSResourceDefinitionHelper.commonResourceProcessing(applicationContext, connectionFactory, jmsModuleName, this.applicationName, namingContext, previousJMSBean, resourceDefinitionManager);
               if (jmsModule != null) {
                  resourceDefinitionManager.addJMSModule(jmsModuleName, jmsModule);
                  this.jmsModules.add(jmsModule);
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("JMSResourceDefinitionProcessor:bind JMS module name created from JMS Connection Factory Definition - " + jmsModuleName);
                  }
               }
            }

            var15 = destinations.iterator();

            while(var15.hasNext()) {
               JmsDestinationBean destination = (JmsDestinationBean)var15.next();
               jndiName = destination.getName();
               if (!isClient || !jndiName.startsWith("java:module") && !jndiName.startsWith("java:comp")) {
                  namingContext = this.getNamingContext(jndiName);
               } else {
                  namingContext = this.getNamingContext(jndiName, moduleNamePrefix, resourceDefinitionManager);
                  isModuleNodeRequired = true;
               }

               jmsModuleName = JMSResourceDefinitionHelper.generateUniqueModuleName(jndiName, this.envType, this.componentName, this.moduleId, moduleNamePrefix);
               previousJMSBean = null;
               if (validJMSModulesMap.containsKey(jmsModuleName)) {
                  moduleToDeactivate = (JMSModule)validJMSModulesMap.get(jmsModuleName);
                  previousJMSBean = (JMSBean)moduleToDeactivate.getModuleDescriptor();
                  if (previousJMSBean.getUniformDistributedQueues().length < 1 && previousJMSBean.getUniformDistributedTopics().length < 1) {
                     throw new ModuleException(JMSExceptionLogger.logJNDINameAlreadyBoundLoggable(jndiName, this.applicationName, "JMS Destination").getMessage());
                  }
               }

               moduleToDeactivate = null;
               boolean isQueue = "javax.jms.Queue".equals(destination.getInterfaceName());
               String uddDestinationName;
               if (destination.getDestinationName() != null && !"".equals(destination.getDestinationName())) {
                  uddDestinationName = destination.getDestinationName();
               } else {
                  uddDestinationName = jmsModuleName + (isQueue ? "_queue" : "_topic");
               }

               jmsModule = JMSResourceDefinitionHelper.commonResourceProcessing(applicationContext, destination, jmsModuleName, this.applicationName, namingContext, previousJMSBean, resourceDefinitionManager, uddDestinationName, isQueue);
               if (jmsModule != null) {
                  resourceDefinitionManager.addJMSModule(jmsModuleName, jmsModule);
                  this.jmsModules.add(jmsModule);
                  JMSLogger.logGeneratedJMSModuleAndDestinationName(this.applicationName, jndiName, jmsModuleName, uddDestinationName, isQueue ? "Uniform Distributed Queue" : "Uniform Distributed Topic");
               }
            }

            this.prepare(applicationContext);
            this.activate(applicationContext);
         } catch (Exception var29) {
            if (validJMSModulesMap != null) {
               Iterator moduleIterator = resourceDefinitionManager.getAllJMSModules().entrySet().iterator();

               while(moduleIterator.hasNext()) {
                  Map.Entry module = (Map.Entry)moduleIterator.next();
                  if (((String)module.getKey()).startsWith(moduleNamePrefix)) {
                     moduleToDeactivate = (JMSModule)module.getValue();

                     try {
                        moduleToDeactivate.deactivate(applicationContext.getProposedDomain());
                     } catch (ModuleException var28) {
                     }

                     try {
                        moduleToDeactivate.unprepare(applicationContext.getProposedDomain());
                     } catch (ModuleException var27) {
                     }

                     moduleIterator.remove();
                  }
               }
            }

            try {
               if (isModuleNodeRequired) {
                  this.destroyAppClientSubContext(applicationContext, resourceDefinitionManager.getAllApplicationClientNodes(), moduleNamePrefix);
               }
            } catch (Exception var26) {
            }

            throw new ModuleException(var29);
         }
      } finally {
         this.readWriteLock.unlock();
      }

   }

   void unbind(ApplicationContextInternal applicationContext) throws ModuleException {
      JMSService jmsService = JMSService.getJMSServiceWithModuleException();
      JMSResourceDefinitionManager resourceDefinitionManager = jmsService.getJMSResourceDefinitionManager();
      String moduleNamePrefix = this.partitionNamePrefix != null ? this.partitionNamePrefix + this.applicationName + "_" : this.applicationName + "_";
      this.readWriteLock.lock();

      try {
         Iterator moduleIterator = resourceDefinitionManager.getAllJMSModules().entrySet().iterator();

         while(moduleIterator.hasNext()) {
            Map.Entry module = (Map.Entry)moduleIterator.next();
            if (((String)module.getKey()).startsWith(moduleNamePrefix)) {
               JMSModule moduleToDeactivate = (JMSModule)module.getValue();
               moduleToDeactivate.deactivate(applicationContext.getProposedDomain());
               moduleToDeactivate.unprepare(applicationContext.getProposedDomain());
               moduleIterator.remove();
            }
         }
      } catch (ModuleException var11) {
         Iterator moduleIterator = resourceDefinitionManager.getAllJMSModules().entrySet().iterator();

         while(moduleIterator.hasNext()) {
            Map.Entry module = (Map.Entry)moduleIterator.next();
            if (((String)module.getKey()).startsWith(moduleNamePrefix)) {
               moduleIterator.remove();
            }
         }

         throw var11;
      } finally {
         if (!resourceDefinitionManager.getAllApplicationClientNodes().isEmpty()) {
            this.destroyAppClientSubContext(applicationContext, resourceDefinitionManager.getAllApplicationClientNodes(), moduleNamePrefix);
         }

         this.readWriteLock.unlock();
      }

   }

   private void prepare(ApplicationContextInternal appCtx) throws ModuleException {
      Iterator var2 = this.jmsModules.iterator();

      while(var2.hasNext()) {
         JMSModule jmsModule = (JMSModule)var2.next();
         jmsModule.prepare(appCtx.getProposedDomain());
      }

   }

   private void activate(ApplicationContextInternal appCtx) throws ModuleException {
      Iterator var2 = this.jmsModules.iterator();

      while(var2.hasNext()) {
         JMSModule jmsModule = (JMSModule)var2.next();
         jmsModule.activate(appCtx.getProposedDomain());
      }

   }

   private void destroyAppClientSubContext(ApplicationContextInternal appCtx, Map applicationClientNodes, String moduleNamePrefix) {
      try {
         Context rootCtx = (new weblogic.jndi.Environment()).getInitialContext();
         Context globalJavaAppContext = (Context)rootCtx.lookup("__WL_GlobalJavaApp");
         Context applicationContext = (Context)globalJavaAppContext.lookup(appCtx.getApplicationId());
         if (applicationContext != null) {
            Iterator appClientNodeIterator = applicationClientNodes.entrySet().iterator();

            while(appClientNodeIterator.hasNext()) {
               Map.Entry appClientNode = (Map.Entry)appClientNodeIterator.next();
               if (((String)appClientNode.getValue()).startsWith(moduleNamePrefix)) {
                  try {
                     JNDIHelper.unbindWithDestroySubcontexts((String)appClientNode.getKey(), applicationContext);
                  } catch (NamingException var10) {
                  }

                  appClientNodeIterator.remove();
               }
            }
         }
      } catch (NamingException var11) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("JMSResourceDefinitionProcessor:destroyAppClientSubContext NamingException " + var11.getMessage());
         }
      } catch (Exception var12) {
      }

   }
}
