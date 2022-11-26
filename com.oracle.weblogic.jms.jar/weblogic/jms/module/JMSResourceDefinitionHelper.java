package weblogic.jms.module;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.application.naming.Environment;
import weblogic.application.naming.Environment.EnvType;
import weblogic.application.utils.PathUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;
import weblogic.j2ee.descriptor.JmsDestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.utils.XXEUtils;

public class JMSResourceDefinitionHelper {
   private static String connectionFactoryResource = "ConnectionFactory";
   private static String queueResource = "Queue";
   private static String topicResource = "Topic";

   private static String createJMSDescriptor(String applicationId, String cfName) {
      return createJMSDescriptor(applicationId, cfName, (String)null, false);
   }

   private static String createJMSDescriptor(String applicationId, String destinationName, boolean isQueue) {
      return createJMSDescriptor(applicationId, (String)null, destinationName, isQueue);
   }

   private static Document fillJMSDescriptor(Document doc, Element rootElement, String tagName, String attributeValue) {
      Element elementToAppend = doc.createElement(tagName);
      rootElement.appendChild(elementToAppend);
      Attr name = doc.createAttribute("name");
      name.setValue(attributeValue);
      elementToAppend.setAttributeNode(name);
      return doc;
   }

   private static String createJMSDescriptor(String applicationId, String cfName, String destinationName, boolean isQueue) {
      String uri = "";

      try {
         File descriptorFile = File.createTempFile("resource", "-jms.xml", PathUtils.getTempDirForAppArchive(applicationId));
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("JMSResourceDefinitionHelper:createJMSDescriptor, descriptorFile : " + descriptorFile);
         }

         uri = descriptorFile.getAbsolutePath();
         DocumentBuilderFactory docFactory = XXEUtils.createDocumentBuilderFactoryInstance();
         DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
         Document doc = docBuilder.newDocument();
         Element rootElement = doc.createElement("weblogic-jms");
         rootElement.setAttribute("xmlns", "http://xmlns.oracle.com/weblogic/weblogic-jms");
         rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
         rootElement.setAttribute("xsi:schemaLocation", "http://xmlns.oracle.com/weblogic/weblogic-jms http://xmlns.oracle.com/weblogic/weblogic-jms/1.1/weblogic-jms.xsd");
         doc.appendChild(rootElement);
         if (cfName != null) {
            doc = fillJMSDescriptor(doc, rootElement, "connection-factory", cfName);
         }

         if (cfName == null && isQueue) {
            doc = fillJMSDescriptor(doc, rootElement, "uniform-distributed-queue", destinationName);
         }

         if (cfName == null && !isQueue) {
            doc = fillJMSDescriptor(doc, rootElement, "uniform-distributed-topic", destinationName);
         }

         TransformerFactory transformerFactory = XXEUtils.createTransformerFactoryInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(new File(uri));
         transformer.transform(source, result);
      } catch (Exception var14) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("JMSResourceDefinitionHelper:createJMSDescriptor exception : " + var14.getMessage());
         }
      }

      return uri;
   }

   static JMSModule commonResourceProcessing(ApplicationContextInternal applicationContext, JmsConnectionFactoryBean jmsConnectionFactoryBean, String moduleName, String applicationName, Context context, JMSBean previousJMSBean, JMSResourceDefinitionManager resourceDefinitionManager) throws ModuleException {
      String jndiName = jmsConnectionFactoryBean.getName();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSResourceDefinitionHelper:commonResourceProcessing jndiName : " + jndiName + " moduleName " + moduleName);
      }

      String connectionFactoryName = null;
      String subDeploymentName = null;
      if (jmsConnectionFactoryBean.getProperties().length > 0 && resourceDefinitionManager.getConnectionFactoryBeanSetMethods().isEmpty()) {
         fillBeanMethods(new ArrayList(Arrays.asList(JMSConnectionFactoryBean.class)), resourceDefinitionManager, connectionFactoryResource);
      }

      JavaEEPropertyBean propertyBean = jmsConnectionFactoryBean.lookupProperty("name");
      if (propertyBean != null) {
         connectionFactoryName = propertyBean.getValue();
      }

      propertyBean = jmsConnectionFactoryBean.lookupProperty("jms-server-name");
      if (propertyBean != null) {
         subDeploymentName = propertyBean.getValue();
      }

      if (connectionFactoryName == null || "".equals(connectionFactoryName)) {
         connectionFactoryName = moduleName + "_cf";
      }

      String jmsDescriptorFile = createJMSDescriptor(applicationContext.getApplicationId(), connectionFactoryName);
      JMSBean jmsBean = JMSParser.createJMSDescriptor(jmsDescriptorFile);
      JMSConnectionFactoryBean jmsCFBean = jmsBean.lookupConnectionFactory(connectionFactoryName);
      if (subDeploymentName != null && !"".equals(subDeploymentName)) {
         jmsCFBean.setSubDeploymentName(subDeploymentName);
      } else {
         jmsCFBean.setDefaultTargetingEnabled(true);
      }

      try {
         jmsCFBean = populateJMSConnectionFactoryBean(applicationName, JMSModuleHelper.getDeploymentScopeAsString(applicationContext), jmsCFBean, jmsConnectionFactoryBean, resourceDefinitionManager);
      } catch (IllegalArgumentException var19) {
         throw new ModuleException(JMSExceptionLogger.logInvalidValueForJMSResourceDefinitionPropertyLoggable(applicationName, "JMS Connection Factory", "JMS Connection Factory Definition".toString()).getMessage(), var19);
      }

      JMSModuleFactory factory = new JMSModuleFactory();
      String jmsServerName = "";
      if (!jmsCFBean.isDefaultTargetingEnabled()) {
         jmsServerName = jmsCFBean.getSubDeploymentName();
      }

      JMSModule jmsModule = (JMSModule)factory.createModule(jmsDescriptorFile, moduleName);
      if (previousJMSBean != null) {
         String previousJMSBeanString = DescriptorUtils.toString((DescriptorBean)previousJMSBean);
         String currentJMSBeanString = DescriptorUtils.toString((DescriptorBean)jmsBean);
         if (previousJMSBeanString.equals(currentJMSBeanString)) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("JMSResourceDefinitionHelper:commonResourceProcessingThe application '" + applicationName + "' has defined duplicate compatible JMS Connection Factories with JNDI name " + jndiName + " using " + "JMS Connection Factory Definition");
            }

            return null;
         } else {
            throw new ModuleException(JMSExceptionLogger.logDuplicateJMSResourceDefinitionsLoggable(jndiName, applicationName, "JMS Connection Factory").getMessage());
         }
      } else {
         jmsModule.internalInit(applicationContext, (UpdateListener.Registration)null, jmsBean, context, jmsServerName, jndiName, false);
         return jmsModule;
      }
   }

   static JMSModule commonResourceProcessing(ApplicationContextInternal applicationContext, JmsDestinationBean jmsDestinationBean, String moduleName, String applicationName, Context context, JMSBean previousJMSBean, JMSResourceDefinitionManager resourceDefinitionManager, String destinationName, boolean isQueue) throws ModuleException {
      String jndiName = jmsDestinationBean.getName();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSResourceDefinitionHelper:commonResourceProcessing jndiName " + jndiName + " moduleName " + moduleName);
      }

      if (jmsDestinationBean.getProperties().length > 0) {
         if (isQueue && resourceDefinitionManager.getUDQueueBeanSetMethods().isEmpty()) {
            fillBeanMethods(getSuperInterfaces(new Class[]{UniformDistributedQueueBean.class}), resourceDefinitionManager, queueResource);
         }

         if (!isQueue && resourceDefinitionManager.getUDTopicBeanSetMethods().isEmpty()) {
            fillBeanMethods(getSuperInterfaces(new Class[]{UniformDistributedTopicBean.class}), resourceDefinitionManager, topicResource);
         }
      }

      String jmsDescriptorFile = createJMSDescriptor(applicationContext.getApplicationId(), destinationName, isQueue);
      JMSBean jmsBean = JMSParser.createJMSDescriptor(jmsDescriptorFile);
      String subDeploymentName = null;
      JavaEEPropertyBean propertyBean = jmsDestinationBean.lookupProperty("jms-server-name");
      if (propertyBean != null) {
         subDeploymentName = propertyBean.getValue();
      }

      UniformDistributedDestinationBean uddBean = null;

      try {
         if (isQueue) {
            UniformDistributedQueueBean udQueueBean = jmsBean.lookupUniformDistributedQueue(destinationName);
            if (subDeploymentName != null && !"".equals(subDeploymentName)) {
               udQueueBean.setSubDeploymentName(subDeploymentName);
            } else {
               udQueueBean.setDefaultTargetingEnabled(true);
            }

            uddBean = populateUniformDistributedBean(applicationName, JMSModuleHelper.getDeploymentScopeAsString(applicationContext), udQueueBean, jmsDestinationBean, resourceDefinitionManager);
            if (!udQueueBean.isDefaultTargetingEnabled()) {
               subDeploymentName = udQueueBean.getSubDeploymentName();
            }
         } else {
            UniformDistributedTopicBean udTopicBean = jmsBean.lookupUniformDistributedTopic(destinationName);
            if (subDeploymentName != null && !"".equals(subDeploymentName)) {
               udTopicBean.setSubDeploymentName(subDeploymentName);
            } else {
               udTopicBean.setDefaultTargetingEnabled(true);
            }

            udTopicBean.setForwardingPolicy("partitioned");
            uddBean = populateUniformDistributedBean(applicationName, JMSModuleHelper.getDeploymentScopeAsString(applicationContext), udTopicBean, jmsDestinationBean, resourceDefinitionManager);
            if (!((UniformDistributedDestinationBean)uddBean).isDefaultTargetingEnabled()) {
               subDeploymentName = ((UniformDistributedDestinationBean)uddBean).getSubDeploymentName();
            }
         }
      } catch (IllegalArgumentException var18) {
         throw new ModuleException(JMSExceptionLogger.logInvalidValueForJMSResourceDefinitionPropertyLoggable(applicationName, "JMS Destination", "JMS Destination Definition".toString()).getMessage(), var18);
      }

      String jmsServerName;
      if (previousJMSBean != null) {
         jmsServerName = DescriptorUtils.toString((DescriptorBean)previousJMSBean);
         String currentJMSBeanString = DescriptorUtils.toString((DescriptorBean)jmsBean);
         if (jmsServerName.equals(currentJMSBeanString)) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("JMSResourceDefinitionHelper:commonResourceProcessingThe application '" + applicationName + "' has defined duplicate compatible JMS Destinations with JNDI name " + jndiName + " using " + "JMS Destination Definition");
            }

            return null;
         } else {
            throw new ModuleException(JMSExceptionLogger.logDuplicateJMSResourceDefinitionsLoggable(jndiName, applicationName, "JMS Destination").getMessage());
         }
      } else {
         jmsServerName = "";
         if (!((UniformDistributedDestinationBean)uddBean).isDefaultTargetingEnabled()) {
            jmsServerName = ((UniformDistributedDestinationBean)uddBean).getSubDeploymentName();
         }

         JMSModuleFactory factory = new JMSModuleFactory();
         JMSModule jmsModule = (JMSModule)factory.createModule(jmsDescriptorFile, moduleName);
         jmsModule.internalInit(applicationContext, (UpdateListener.Registration)null, jmsBean, context, jmsServerName, jndiName, true);
         return jmsModule;
      }
   }

   static String generateUniqueModuleName(String jndiName, Environment.EnvType envType, String componentName, String moduleId, String moduleNamePrefix) {
      String moduleName = null;
      if (!envType.equals(EnvType.APPLICATION) && !jndiName.startsWith("java:app") && !jndiName.startsWith("java:global")) {
         if (jndiName.startsWith("java:module")) {
            moduleName = moduleNamePrefix + envType + "_" + moduleId + "_" + jndiName;
         } else {
            moduleName = moduleNamePrefix + envType + "_" + moduleId + "_" + componentName + "_" + jndiName;
         }
      } else {
         moduleName = moduleNamePrefix + jndiName;
      }

      char[] var6 = moduleName.toCharArray();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         char c = var6[var8];
         if (Arrays.binarySearch(JMSResourceDefinitionConstants.INVALID_CHARS_IN_JMS_MODULE, c) >= 0) {
            moduleName = moduleName.replace(c, '_');
         }
      }

      moduleName = moduleName.replaceAll("/", ".");
      return moduleName;
   }

   private static void fillBeanMethods(List allClasses, JMSResourceDefinitionManager resourceDefinitionManager, String resourceType) throws ModuleException {
      try {
         Iterator var3 = allClasses.iterator();

         while(var3.hasNext()) {
            Class aClass = (Class)var3.next();
            PropertyDescriptor[] var5 = Introspector.getBeanInfo(aClass).getPropertyDescriptors();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               PropertyDescriptor propertyDescriptor = var5[var7];
               Method writeMethod = propertyDescriptor.getWriteMethod();
               if (writeMethod != null) {
                  Class[] var10 = writeMethod.getParameterTypes();
                  int var11 = var10.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     Class param = var10[var12];
                     if (param.getPackage() == null || !"weblogic.j2ee.descriptor.wl".equals(param.getPackage().getName())) {
                        if (resourceType.equals(connectionFactoryResource)) {
                           resourceDefinitionManager.addConnectionFactoryBeanSetMethod(propertyDescriptor.getName().toLowerCase(), writeMethod);
                        }

                        if (resourceType.equals(queueResource)) {
                           resourceDefinitionManager.addUDQueueBeanSetMethod(propertyDescriptor.getName().toLowerCase(), writeMethod);
                        }

                        if (resourceType.equals(topicResource)) {
                           resourceDefinitionManager.addUDTopicBeanSetMethod(propertyDescriptor.getName().toLowerCase(), writeMethod);
                        }
                     }
                  }
               }

               Method readMethod = propertyDescriptor.getReadMethod();
               if (readMethod != null) {
                  Package packageInfo = readMethod.getReturnType().getPackage();
                  if (packageInfo != null && packageInfo.getName().equals("weblogic.j2ee.descriptor.wl")) {
                     if (resourceType.equals(connectionFactoryResource)) {
                        resourceDefinitionManager.addConnectionFactoryBeanGetTypeMethod(readMethod.getName(), readMethod.getReturnType());
                     }

                     if (resourceType.equals(queueResource)) {
                        resourceDefinitionManager.addUDQueueBeanGetTypeMethod(readMethod.getName(), readMethod.getReturnType());
                     }

                     if (resourceType.equals(topicResource)) {
                        resourceDefinitionManager.addUDTopicBeanGetTypeMethods(readMethod.getName(), readMethod.getReturnType());
                     }
                  }
               }
            }
         }

      } catch (IntrospectionException var14) {
         throw new AssertionError("Setting properties on a " + resourceType + " failed.", var14);
      }
   }

   private static UniformDistributedQueueBean populateUniformDistributedBean(String applicationName, String deploymentScope, UniformDistributedQueueBean moduleBean, JmsDestinationBean resourceBean, JMSResourceDefinitionManager resourceDefinitionManager) throws ModuleException {
      moduleBean.setJNDIName(resourceBean.getName());
      if (resourceBean.getDescription() != null && !"".equals(resourceBean.getDescription())) {
         moduleBean.setNotes(resourceBean.getDescription());
      }

      if (resourceBean.getProperties().length == 0) {
         return moduleBean;
      } else {
         Map unrecognizedProperties = null;
         String logLevel = "LogWarning";
         JavaEEPropertyBean propertyBean = resourceBean.lookupProperty("unrecognized-property-log-level");
         if (propertyBean != null && "LogNone".equalsIgnoreCase(propertyBean.getValue())) {
            logLevel = propertyBean.getValue();
         }

         if (!logLevel.equals("LogNone")) {
            unrecognizedProperties = new LinkedHashMap();
         }

         Map beanGetMethodsForComplexType = resourceDefinitionManager.getUDQueueBeanGetTypeMethods();
         Map beanSetMethods = resourceDefinitionManager.getUDQueueBeanSetMethods();
         String name = null;
         String value = null;
         JavaEEPropertyBean[] var12 = resourceBean.getProperties();
         int var13 = var12.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            JavaEEPropertyBean resourceProperty = var12[var14];
            name = resourceProperty.getName();
            value = resourceProperty.getValue();
            if (Arrays.binarySearch(JMSResourceDefinitionConstants.ACCEPTED_UD_QUEUE_PROPERTIES, name) > -1) {
               setProperty(applicationName, "JMS Destination", "JMS Destination Definition", moduleBean, name, value, beanGetMethodsForComplexType, beanSetMethods);
            } else if (!logLevel.equals("LogNone") && !"jms-server-name".equals(name)) {
               unrecognizedProperties.put(name, value);
            }
         }

         if (unrecognizedProperties != null && !unrecognizedProperties.isEmpty()) {
            JMSLogger.logInvalidJMSResourceDefinitionProperties(applicationName, resourceBean.getName(), deploymentScope, "JMS Destination Definition", unrecognizedProperties.toString(), "unrecognized-property-log-level".toString());
         }

         return moduleBean;
      }
   }

   private static UniformDistributedTopicBean populateUniformDistributedBean(String applicationName, String deploymentScope, UniformDistributedTopicBean moduleBean, JmsDestinationBean resourceBean, JMSResourceDefinitionManager resourceDefinitionManager) throws ModuleException {
      moduleBean.setJNDIName(resourceBean.getName());
      if (resourceBean.getDescription() != null && !"".equals(resourceBean.getDescription())) {
         moduleBean.setNotes(resourceBean.getDescription());
      }

      if (resourceBean.getProperties().length == 0) {
         return moduleBean;
      } else {
         Map unrecognizedProperties = null;
         String logLevel = "LogWarning";
         JavaEEPropertyBean propertyBean = resourceBean.lookupProperty("unrecognized-property-log-level");
         if (propertyBean != null && "LogNone".equalsIgnoreCase(propertyBean.getValue())) {
            logLevel = propertyBean.getValue();
         }

         if (!logLevel.equals("LogNone")) {
            unrecognizedProperties = new LinkedHashMap();
         }

         Map beanGetMethodsForComplexType = resourceDefinitionManager.getUDTopicBeanGetTypeMethodss();
         Map beanSetMethods = resourceDefinitionManager.getUDTopicBeanSetMethods();
         String name = null;
         String value = null;
         JavaEEPropertyBean[] var12 = resourceBean.getProperties();
         int var13 = var12.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            JavaEEPropertyBean resourceProperty = var12[var14];
            name = resourceProperty.getName();
            value = resourceProperty.getValue();
            if (Arrays.binarySearch(JMSResourceDefinitionConstants.ACCEPTED_UD_TOPIC_PROPERTIES, name) > -1) {
               setProperty(applicationName, "JMS Destination", "JMS Destination Definition", moduleBean, name, value, beanGetMethodsForComplexType, beanSetMethods);
            } else if (!logLevel.equals("LogNone") && !"jms-server-name".equals(name)) {
               unrecognizedProperties.put(name, value);
            }
         }

         if (unrecognizedProperties != null && !unrecognizedProperties.isEmpty()) {
            JMSLogger.logInvalidJMSResourceDefinitionProperties(applicationName, resourceBean.getName(), deploymentScope, "JMS Destination Definition", unrecognizedProperties.toString(), "unrecognized-property-log-level".toString());
         }

         return moduleBean;
      }
   }

   private static JMSConnectionFactoryBean populateJMSConnectionFactoryBean(String applicationName, String deploymentScope, JMSConnectionFactoryBean moduleBean, JmsConnectionFactoryBean resourceBean, JMSResourceDefinitionManager resourceDefinitionManager) throws ModuleException {
      moduleBean.setJNDIName(resourceBean.getName());
      if (resourceBean.getDescription() != null && !"".equals(resourceBean.getDescription())) {
         moduleBean.setNotes(resourceBean.getDescription());
      }

      if (resourceBean.getClientId() != null && !"".equals(resourceBean.getClientId())) {
         moduleBean.getClientParams().setClientId(resourceBean.getClientId());
      }

      if (!resourceBean.isTransactional()) {
         moduleBean.getTransactionParams().setXAConnectionFactoryEnabled(false);
      } else {
         moduleBean.getTransactionParams().setXAConnectionFactoryEnabled(true);
      }

      moduleBean.getClientParams().setAllowCloseInOnMessage(false);
      if (resourceBean.getProperties().length == 0) {
         return moduleBean;
      } else {
         Map unrecognizedProperties = null;
         String logLevel = "LogWarning";
         JavaEEPropertyBean propertyBean = resourceBean.lookupProperty("unrecognized-property-log-level");
         if (propertyBean != null && "LogNone".equalsIgnoreCase(propertyBean.getValue())) {
            logLevel = propertyBean.getValue();
         }

         if (!logLevel.equals("LogNone")) {
            unrecognizedProperties = new LinkedHashMap();
         }

         Map beanGetMethodsForComplexType = resourceDefinitionManager.getConnectionFactoryBeanGetTypeMethods();
         Map beanSetMethods = resourceDefinitionManager.getConnectionFactoryBeanSetMethods();
         String name = null;
         String value = null;
         JavaEEPropertyBean[] var12 = resourceBean.getProperties();
         int var13 = var12.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            JavaEEPropertyBean resourceProperty = var12[var14];
            name = resourceProperty.getName();
            value = resourceProperty.getValue();
            if (Arrays.binarySearch(JMSResourceDefinitionConstants.ACCEPTED_CF_PROPERTIES, name) > -1) {
               setProperty(applicationName, "JMS Connection Factory", "JMS Connection Factory Definition", moduleBean, name, value, beanGetMethodsForComplexType, beanSetMethods);
            } else if (logLevel.equals("LogNone") || !"name".equals(name) && !"jms-server-name".equals(name)) {
               unrecognizedProperties.put(name, value);
            }
         }

         if (unrecognizedProperties != null && !unrecognizedProperties.isEmpty()) {
            JMSLogger.logInvalidJMSResourceDefinitionProperties(applicationName, resourceBean.getName(), deploymentScope, "JMS Connection Factory Definition", unrecognizedProperties.toString(), "unrecognized-property-log-level".toString());
         }

         return moduleBean;
      }
   }

   private static List getSuperInterfaces(Class[] childInterfaces) {
      List allInterfaces = new ArrayList();
      Class[] var2 = childInterfaces;
      int var3 = childInterfaces.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class myInterface = var2[var4];
         allInterfaces.add(myInterface);
         allInterfaces.addAll(getSuperInterfaces(myInterface.getInterfaces()));
      }

      return allInterfaces;
   }

   private static Object invokeInstanceMethod(String applicationName, String jmsResource, String jmsResourceDefinition, Object instance, Method methodToInvoke, String propertyName, Object propertyValue) throws ModuleException {
      String expectedType = methodToInvoke.getParameterTypes()[0].getName();

      try {
         if (expectedType.equals("int")) {
            return methodToInvoke.invoke(instance, Integer.valueOf(String.valueOf(propertyValue)));
         } else if (expectedType.equals("long")) {
            return methodToInvoke.invoke(instance, Long.valueOf(String.valueOf(propertyValue)));
         } else {
            return expectedType.equals("boolean") ? methodToInvoke.invoke(instance, Boolean.valueOf(String.valueOf(propertyValue))) : methodToInvoke.invoke(instance, propertyValue);
         }
      } catch (NumberFormatException var9) {
         throw new IllegalArgumentException(JMSExceptionLogger.logInvalidTypeForPropertyLoggable(applicationName, jmsResource, jmsResourceDefinition, propertyName, propertyValue.toString()).getMessage());
      } catch (InvocationTargetException | IllegalAccessException var10) {
         throw new ModuleException(var10.getCause());
      }
   }

   private static void setProperty(String applicationName, String jmsResource, String jmsResourceDefinition, Object entity, String propertyName, Object propertyValue, Map beanMethodsMap, Map beanWriteMethodsMap) throws ModuleException {
      String beanPropertyName = propertyName.toLowerCase().replaceAll("-", "");

      try {
         if (beanWriteMethodsMap.containsKey(beanPropertyName)) {
            Method methodToInvoke = (Method)beanWriteMethodsMap.get(beanPropertyName);
            invokeInstanceMethod(applicationName, jmsResource, jmsResourceDefinition, entity, methodToInvoke, beanPropertyName, propertyValue);
         } else {
            Iterator var9 = beanMethodsMap.entrySet().iterator();

            while(var9.hasNext()) {
               Map.Entry entry = (Map.Entry)var9.next();
               Class aClass = (Class)entry.getValue();
               PropertyDescriptor[] var12 = Introspector.getBeanInfo(aClass).getPropertyDescriptors();
               int var13 = var12.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  PropertyDescriptor propertyDescriptor = var12[var14];
                  if (propertyDescriptor.getName().toLowerCase().equals(beanPropertyName)) {
                     Method writeMethod = propertyDescriptor.getWriteMethod();
                     Class[] noparams = new Class[0];
                     Method getter = null;
                     String key = (String)entry.getKey();

                     try {
                        getter = entity.getClass().getDeclaredMethod(key, noparams);
                     } catch (NoSuchMethodException var26) {
                        Method[] allGetterMethods = entity.getClass().getMethods();
                        Method[] var22 = allGetterMethods;
                        int var23 = allGetterMethods.length;

                        for(int var24 = 0; var24 < var23; ++var24) {
                           Method aMethod = var22[var24];
                           if (key.equals(aMethod.getName())) {
                              getter = aMethod;
                              break;
                           }
                        }
                     }

                     if (getter != null) {
                        invokeInstanceMethod(applicationName, jmsResource, jmsResourceDefinition, aClass.cast(getter.invoke(entity)), writeMethod, propertyName, propertyValue);
                     }
                  }
               }
            }

         }
      } catch (IntrospectionException | InvocationTargetException | IllegalAccessException var27) {
         throw new AssertionError("Setting property " + propertyName + "  specified by " + jmsResourceDefinition + " in application " + applicationName + " failed.", var27);
      }
   }
}
