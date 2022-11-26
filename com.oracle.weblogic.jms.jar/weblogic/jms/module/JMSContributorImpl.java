package weblogic.jms.module;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.application.naming.Environment;
import weblogic.application.naming.Environment.EnvType;
import weblogic.application.naming.jms.JMSContributor;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;
import weblogic.j2ee.descriptor.JmsDestinationBean;
import weblogic.jms.JMSExceptionLogger;

public class JMSContributorImpl implements JMSContributor {
   private final Map namingContexts = new HashMap();
   private JMSResourceDefinitionProcessor processor = null;

   public JMSContributorImpl(Context javaGlobalCtx, Context javaAppContext, Context javaModuleCtx, Context javaCompCtx) {
      this.namingContexts.put("java:global", javaGlobalCtx);
      this.namingContexts.put("java:app", javaAppContext);
      this.namingContexts.put("java:module", javaModuleCtx);
      this.namingContexts.put("java:comp", javaCompCtx);
   }

   public void bindJMSResourceDefinitions(ApplicationContextInternal applicationContext, J2eeClientEnvironmentBean clientEnvironmentBean, Set connectionFactoryResources, Set administeredObjects, String applicationName, String moduleId, String moduleName, String componentName, Environment.EnvType envType) throws ResourceException {
      String jndiName = null;
      Set connectionFactories = new HashSet();
      Set destinations = new HashSet();
      boolean isClient = EnvType.CLIENT.equals(envType);
      this.processor = new JMSResourceDefinitionProcessor(applicationName, componentName, moduleId, envType, this.namingContexts);

      try {
         JmsConnectionFactoryBean[] var14 = clientEnvironmentBean.getJmsConnectionFactories();
         int var15 = var14.length;

         int var16;
         for(var16 = 0; var16 < var15; ++var16) {
            JmsConnectionFactoryBean connectionFactory = var14[var16];
            jndiName = this.validateJNDIName(applicationName, connectionFactory.getName(), "JMS Connection Factory", envType);
            if (!connectionFactory.getName().equals(jndiName)) {
               connectionFactory.setName(jndiName);
            }

            if (connectionFactory.getResourceAdapter() != null && !"".equals(connectionFactory.getResourceAdapter())) {
               if (isClient) {
                  throw new ResourceException(JMSExceptionLogger.logInvalidResourceDefinitionInAppClientModuleLoggable(applicationName, "JMS Connection Factory", "JMS Connection Factory Definition", jndiName, moduleName, connectionFactory.getResourceAdapter()).getMessage());
               }

               connectionFactoryResources.add(this.createConnectionFactoryResourceBean(clientEnvironmentBean, connectionFactory));
            } else {
               connectionFactories.add(connectionFactory);
            }
         }

         JmsDestinationBean[] var22 = clientEnvironmentBean.getJmsDestinations();
         var15 = var22.length;

         for(var16 = 0; var16 < var15; ++var16) {
            JmsDestinationBean destination = var22[var16];
            jndiName = this.validateJNDIName(applicationName, destination.getName(), "JMS Destination", envType);
            if (!destination.getName().equals(jndiName)) {
               destination.setName(jndiName);
            }

            if (destination.getResourceAdapter() != null && !"".equals(destination.getResourceAdapter())) {
               if (isClient) {
                  throw new ResourceException(JMSExceptionLogger.logInvalidResourceDefinitionInAppClientModuleLoggable(applicationName, "JMS Destination", "JMS Destination Definition", jndiName, moduleName, destination.getResourceAdapter()).getMessage());
               }

               administeredObjects.add(this.createAdministeredObjectDefinitionBean(clientEnvironmentBean, destination));
            } else {
               destinations.add(destination);
            }
         }
      } catch (ResourceException var21) {
         try {
            this.unbindJMSResourceDefinitions(applicationContext);
         } catch (Exception var19) {
         }

         throw var21;
      }

      if (!connectionFactories.isEmpty() || !destinations.isEmpty()) {
         try {
            this.processor.bind(applicationContext, connectionFactories, destinations);
         } catch (ModuleException var20) {
            try {
               this.unbindJMSResourceDefinitions(applicationContext);
            } catch (Exception var18) {
            }

            throw new ResourceException(var20.getCause());
         }
      }
   }

   public void unbindJMSResourceDefinitions(ApplicationContextInternal applicationContext) throws ResourceException {
      if (this.processor != null) {
         try {
            this.processor.unbind(applicationContext);
         } catch (ModuleException var3) {
            throw new ResourceException(var3.getCause());
         }
      }

   }

   private String validateJNDIName(String applicationName, String jndiName, String jmsResource, Environment.EnvType envType) throws ResourceException {
      if (envType.equals(EnvType.APPLICATION)) {
         if (!jndiName.startsWith("java:global") && !jndiName.startsWith("java:app")) {
            throw new ResourceException(JMSExceptionLogger.logInvalidJNDINameSpaceInAppXMLAndLibrariesLoggable(jmsResource, applicationName, jndiName.toString()).getMessage());
         } else {
            return jndiName;
         }
      } else if (jndiName.startsWith("java:") && !jndiName.startsWith("java:global") && !jndiName.startsWith("java:app") && !jndiName.startsWith("java:module") && !jndiName.startsWith("java:comp")) {
         throw new ResourceException(JMSExceptionLogger.logInvalidJNDINameSpaceForJMSResourceDefinitionLoggable(jmsResource, applicationName, jndiName.toString()).getMessage());
      } else {
         return !jndiName.startsWith("java:global") && !jndiName.startsWith("java:app") && !jndiName.startsWith("java:module") && !jndiName.startsWith("java:comp") ? "java:comp/env/" + jndiName : jndiName;
      }
   }

   private ConnectionFactoryResourceBean createConnectionFactoryResourceBean(J2eeClientEnvironmentBean clientEnvironmentBean, JmsConnectionFactoryBean connectionFactory) {
      ConnectionFactoryResourceBean resourceBean = clientEnvironmentBean.createConnectionFactoryResourceBean();
      resourceBean.setName(connectionFactory.getName());
      resourceBean.setInterfaceName(connectionFactory.getInterfaceName());
      resourceBean.setResourceAdapter(connectionFactory.getResourceAdapter());
      resourceBean.setDescription(connectionFactory.getDescription());
      resourceBean.setMaxPoolSize(connectionFactory.getMaxPoolSize());
      resourceBean.setMinPoolSize(connectionFactory.getMinPoolSize());
      if (connectionFactory.isTransactional()) {
         resourceBean.setTransactionSupport("XATransaction");
      } else {
         resourceBean.setTransactionSupport("NoTransaction");
      }

      JavaEEPropertyBean[] var4 = connectionFactory.getProperties();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JavaEEPropertyBean propertyBean = var4[var6];
         JavaEEPropertyBean newProperty = resourceBean.createProperty();
         newProperty.setName(propertyBean.getName());
         newProperty.setValue(propertyBean.getValue());
      }

      JavaEEPropertyBean newProperty;
      if (!"".equals(connectionFactory.getClientId())) {
         newProperty = resourceBean.createProperty();
         newProperty.setName("clientId");
         newProperty.setValue(connectionFactory.getClientId());
      }

      if (connectionFactory.getUser() != "") {
         newProperty = resourceBean.createProperty();
         newProperty.setName("user");
         newProperty.setValue(connectionFactory.getUser());
      }

      if (connectionFactory.getPassword() != "") {
         newProperty = resourceBean.createProperty();
         newProperty.setName("password");
         newProperty.setValue(connectionFactory.getPassword());
      }

      return resourceBean;
   }

   private AdministeredObjectBean createAdministeredObjectDefinitionBean(J2eeClientEnvironmentBean clientEnvironmentBean, JmsDestinationBean destinationBean) {
      AdministeredObjectBean administeredObject = clientEnvironmentBean.createAdministeredObjectBean();
      administeredObject.setName(destinationBean.getName());
      administeredObject.setInterfaceName(destinationBean.getInterfaceName());
      administeredObject.setResourceAdapter(destinationBean.getResourceAdapter());
      administeredObject.setDescription(destinationBean.getDescription());
      if (!"".equals(destinationBean.getClassName())) {
         administeredObject.setClassName(destinationBean.getClassName());
      }

      JavaEEPropertyBean[] var4 = destinationBean.getProperties();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JavaEEPropertyBean propertyBean = var4[var6];
         JavaEEPropertyBean newProperty = administeredObject.createProperty();
         newProperty.setName(propertyBean.getName());
         newProperty.setValue(propertyBean.getValue());
      }

      if (destinationBean.lookupProperty("name") == null && !"".equals(destinationBean.getDestinationName())) {
         JavaEEPropertyBean newProperty = administeredObject.createProperty();
         newProperty.setName("name");
         newProperty.setValue(destinationBean.getDestinationName());
      }

      return administeredObject;
   }
}
