package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ResourceGroupTemplateMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ResourceGroupTemplateMBean.class;

   public ResourceGroupTemplateMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ResourceGroupTemplateMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ResourceGroupTemplateMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("A resource group template is a named, domain-level collection of deployable resources intended to be used as a pattern by (usually) multiple resource groups. Each resource group that refers to a given template will have its own runtime copies of the resources defined in the template. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ResourceGroupTemplateMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AppDeployments")) {
         getterName = "getAppDeployments";
         setterName = null;
         currentResult = new PropertyDescriptor("AppDeployments", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("AppDeployments", currentResult);
         currentResult.setValue("description", "The collection of deployable entities in this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("CoherenceClusterSystemResources")) {
         getterName = "getCoherenceClusterSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterSystemResources", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("CoherenceClusterSystemResources", currentResult);
         currentResult.setValue("description", "The CoherenceClusterSystemResourceMBeans that have been defined for this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCoherenceClusterSystemResource");
         currentResult.setValue("creator", "createCoherenceClusterSystemResource");
         currentResult.setValue("deprecated", "12.2.1.1.0 Coherence Clusters cannot be targeted to ResourceGroupTemplates ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("FileStores")) {
         getterName = "getFileStores";
         setterName = null;
         currentResult = new PropertyDescriptor("FileStores", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("FileStores", currentResult);
         currentResult.setValue("description", "The file stores defined in this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyFileStore");
         currentResult.setValue("creator", "createFileStore");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ForeignJNDIProviders")) {
         getterName = "getForeignJNDIProviders";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignJNDIProviders", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("ForeignJNDIProviders", currentResult);
         currentResult.setValue("description", "The Foreign JNDI Providers defined for this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createForeignJNDIProvider");
         currentResult.setValue("destroyer", "destroyForeignJNDIProvider");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JDBCStores")) {
         getterName = "getJDBCStores";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCStores", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("JDBCStores", currentResult);
         currentResult.setValue("description", "The JDBCStores defined in this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJDBCStore");
         currentResult.setValue("destroyer", "destroyJDBCStore");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JDBCSystemResources")) {
         getterName = "getJDBCSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCSystemResources", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("JDBCSystemResources", currentResult);
         currentResult.setValue("description", "<p>The JDBCSystemResourceMBeans that have been defined for this resource group template. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJDBCSystemResource");
         currentResult.setValue("creator", "createJDBCSystemResource");
         currentResult.setValue("creator", "createJDBCSystemResource");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JMSBridgeDestinations")) {
         getterName = "getJMSBridgeDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSBridgeDestinations", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("JMSBridgeDestinations", currentResult);
         currentResult.setValue("description", "The JMSBridgeDestinations for this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSBridgeDestination");
         currentResult.setValue("destroyer", "destroyJMSBridgeDestination");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JMSServers")) {
         getterName = "getJMSServers";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSServers", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("JMSServers", currentResult);
         currentResult.setValue("description", "Define JMSServers for this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSServer");
         currentResult.setValue("destroyer", "destroyJMSServer");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JMSSystemResources")) {
         getterName = "getJMSSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSSystemResources", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("JMSSystemResources", currentResult);
         currentResult.setValue("description", "The JMSSystemResourceMBeans that have been defined for this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJMSSystemResource");
         currentResult.setValue("creator", "createJMSSystemResource");
         currentResult.setValue("creator", "createJMSSystemResource");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Libraries")) {
         getterName = "getLibraries";
         setterName = null;
         currentResult = new PropertyDescriptor("Libraries", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("Libraries", currentResult);
         currentResult.setValue("description", "Define libraries for this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("MailSessions")) {
         getterName = "getMailSessions";
         setterName = null;
         currentResult = new PropertyDescriptor("MailSessions", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("MailSessions", currentResult);
         currentResult.setValue("description", "The MailSessions for this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMailSession");
         currentResult.setValue("creator", "createMailSession");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ManagedExecutorServices")) {
         getterName = "getManagedExecutorServices";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServices", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("ManagedExecutorServices", currentResult);
         currentResult.setValue("description", "All the ManagedExecutorServices. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyManagedExecutorService");
         currentResult.setValue("creator", "createManagedExecutorService");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ManagedScheduledExecutorServices")) {
         getterName = "getManagedScheduledExecutorServices";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServices", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("ManagedScheduledExecutorServices", currentResult);
         currentResult.setValue("description", "All the ManagedScheduledExecutorService. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedScheduledExecutorService");
         currentResult.setValue("destroyer", "destroyManagedScheduledExecutorService");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ManagedThreadFactories")) {
         getterName = "getManagedThreadFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactories", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("ManagedThreadFactories", currentResult);
         currentResult.setValue("description", "All the ManagedThreadFactories. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyManagedThreadFactory");
         currentResult.setValue("creator", "createManagedThreadFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("MessagingBridges")) {
         getterName = "getMessagingBridges";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagingBridges", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("MessagingBridges", currentResult);
         currentResult.setValue("description", "The MessagingBridgeMBean representing the messaging bridges that have been configured to be part of this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMessagingBridge");
         currentResult.setValue("destroyer", "destroyMessagingBridge");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("OsgiFrameworks")) {
         getterName = "getOsgiFrameworks";
         setterName = null;
         currentResult = new PropertyDescriptor("OsgiFrameworks", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("OsgiFrameworks", currentResult);
         currentResult.setValue("description", "<p>OSGi framework definition for use by applications wishing to share services and code</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOsgiFramework");
         currentResult.setValue("creator", "createOsgiFramework");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PathServices")) {
         getterName = "getPathServices";
         setterName = null;
         currentResult = new PropertyDescriptor("PathServices", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("PathServices", currentResult);
         currentResult.setValue("description", "The PathServices for this resource group template. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPathService");
         currentResult.setValue("creator", "createPathService");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("SAFAgents")) {
         getterName = "getSAFAgents";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFAgents", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("SAFAgents", currentResult);
         currentResult.setValue("description", "The SAFAgentMBeans for this resource group template ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySAFAgent");
         currentResult.setValue("creator", "createSAFAgent");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("UploadDirectoryName")) {
         getterName = "getUploadDirectoryName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUploadDirectoryName";
         }

         currentResult = new PropertyDescriptor("UploadDirectoryName", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("UploadDirectoryName", currentResult);
         currentResult.setValue("description", "<p>The directory path on the Administration Server where the uploaded applications for this resource group template are placed.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("WLDFSystemResources")) {
         getterName = "getWLDFSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFSystemResources", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("WLDFSystemResources", currentResult);
         currentResult.setValue("description", "<p>The WLDFSystemResourceMBeans that have been defined for this resource group template</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWLDFSystemResource");
         currentResult.setValue("creator", "createWLDFSystemResource");
         currentResult.setValue("creator", "createWLDFSystemResource");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", ResourceGroupTemplateMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceGroupTemplateMBean.class.getMethod("createJMSServer", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMSServer to create ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a JMSServer object ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSServers");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyJMSServer", JMSServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jmsServer", "the JMSServer to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the named JMSServer from this resource group template. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSServers");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createMessagingBridge", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the MessagingBridge to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a MessagingBridge with specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessagingBridges");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyMessagingBridge", MessagingBridgeMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bridge", "the MessagingBridge to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Deletes specified MessagingBridge object. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessagingBridges");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createPathService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the PathService to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Factory method to create PathService object ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PathServices");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyPathService", PathServiceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("pathService", "the PathService to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a PathService from this resource group template. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PathServices");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createJMSBridgeDestination", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMSBridgeDestination to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a JMSBridgeDestination instance in the resource group template. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSBridgeDestinations");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyJMSBridgeDestination", JMSBridgeDestinationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jmsBridgeDestination", "the JMSBridgeDestination to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Deletes the specified JMSBridgeDestination object ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSBridgeDestinations");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createMailSession", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the MailSession to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a MailSession object with the specified name ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MailSessions");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyMailSession", MailSessionMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("ms", "the MailSession to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Deletes a MailSession from this resource group template ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MailSessions");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createFileStore", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the FileStore to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new FileStore. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FileStores");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyFileStore", FileStoreMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("store", "the FileStore to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Delete the specified file store. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FileStores");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createJDBCStore", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JDBCStore to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new JDBCStore with the specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCStores");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyJDBCStore", JDBCStoreMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("store", "the JDBCStore to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy The specified JDBCStore. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCStores");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createJMSSystemResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean and base name for descriptor file. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new JMS system resource. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSSystemResources");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createJMSSystemResource", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of bean "), createParameterDescriptor("descriptorFileName", "base name for descriptor file ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new JMS system resource. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSSystemResources");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyJMSSystemResource", JMSSystemResourceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "- bean to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy the given system resource bean and delete the descriptor file that it refers to. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSSystemResources");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createForeignJNDIProvider", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ForeignJNDIProvider ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new ForeignJNDIProvider with the specified name ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJNDIProviders");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyForeignJNDIProvider", ForeignJNDIProviderMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("provider", "the JNDIProvider to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Delete a ForeignJNDIProvider from the resource group template. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJNDIProviders");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createJDBCSystemResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean and base name for descriptor file. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new JDBC system resource.  The file for this resource will be DOMAIN_DIR/config/<name>.xml ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCSystemResources");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createJDBCSystemResource", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of bean "), createParameterDescriptor("descriptorFileName", "name of descriptor file relative to DOMAIN_DIR/config/jdbc. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a new JDBC system resource whose descriptor is stored in the given fileName relative to DOMAIN_DIR/config. If not file by this name is defined, it will be created. If a file by this name exists and contains a valid JDBC descriptor, the new bean will link to that descriptor.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCSystemResources");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyJDBCSystemResource", JDBCSystemResourceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "bean to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy the given system resource bean and delete the descriptor file that it refers to. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCSystemResources");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createWLDFSystemResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of bean and base name for descriptor file. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new WLDFSystemResourceMBean. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WLDFSystemResources");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createWLDFSystemResource", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of bean "), createParameterDescriptor("descriptorFileName", "name of descriptor file relative to DOMAIN_DIR/config/diagnostics. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a new WLDF system resource whose descriptor is stored in the given fileName relative to DOMAIN_DIR/config. If not file by this name is defined, it will be created. If a file by this name exists and contains a valid WLDF descriptor, the new bean will link to that descriptor.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WLDFSystemResources");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyWLDFSystemResource", WLDFSystemResourceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "bean to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy the given system resource bean and delete the descriptor file that it refers to. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WLDFSystemResources");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createSAFAgent", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the SAFAgent mbean to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates SAFAgent object with the specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFAgents");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroySAFAgent", SAFAgentMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sAFAgent", "object ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a SAFAgent from this resource group template. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFAgents");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createCoherenceClusterSystemResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of bean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "12.2.1.1.0 Coherence Clusters cannot be targeted to  ResourceGroupTemplates ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new CoherenceClusterSystemResource.  The file for this resource will be in DOMAIN_DIR/config/coherence/<name>/<name_xxx>.xml ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceClusterSystemResources");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyCoherenceClusterSystemResource", CoherenceClusterSystemResourceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "bean to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "12.2.1.1.0 Coherence Clusters cannot be targeted to ResourceGroupTemplates ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy the given CoherenceClusterSystemResource. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceClusterSystemResources");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createOsgiFramework", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the OSGi framework to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory to create a new OSGi framework instance for this Server.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OsgiFrameworks");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyOsgiFramework", OsgiFrameworkMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("osgiFramework", "to be destroyed ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys an OSGi framework object.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OsgiFrameworks");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createManagedExecutorService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedExecutorServiceMBean mbean to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates ManagedExecutorService with the specified name ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedExecutorServices");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyManagedExecutorService", ManagedExecutorServiceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "the name of the ManagedExecutorServiceMBean mbean to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a ManagedExecutorService which with the specified short name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedExecutorServices");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createManagedScheduledExecutorService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedScheduledExecutorService to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates ManagedScheduledExecutorService with the specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedScheduledExecutorServices");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyManagedScheduledExecutorService", ManagedScheduledExecutorServiceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "the ManagedScheduledExecutorServiceMBean to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a ManagedScheduledExecutorService which with the specified short name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedScheduledExecutorServices");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("createManagedThreadFactory", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedThreadFactory to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates ManagedThreadFactory with the specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedThreadFactories");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("destroyManagedThreadFactory", ManagedThreadFactoryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "the ManagedThreadFactoryMBean to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a ManagedThreadFactory which with the specified short name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedThreadFactories");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupTemplateMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupTemplateMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceGroupTemplateMBean.class.getMethod("lookupAppDeployment", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the AppDeployment mbean to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a AppDeploymentMBean with the specified name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "AppDeployments");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupLibrary", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the library name to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a LibraryMBean with the specified name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Libraries");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupJMSServer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMSServer to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a JMSServerMBean with the specified name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSServers");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupMessagingBridge", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the MessagingBridge to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a MessagingBridgeMBean with the specified name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MessagingBridges");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupPathService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the PathService to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a PathServiceMBean with the specified name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "PathServices");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupJMSBridgeDestination", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMSBridgeDestination to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a JMSBridgeDestinationMBean with the specified name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSBridgeDestinations");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupMailSession", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the MailSession to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a MailSessionMBean with the specified name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MailSessions");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupFileStore", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the FileStore to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a FileStoreMBean with the specified name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "FileStores");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupJDBCStore", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JDBCStore to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a JDBCStoreMBean with the specified name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JDBCStores");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupJMSSystemResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the JMS system resource ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Find a JMSSystem resource with the given name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSSystemResources");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupForeignJNDIProvider", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ForeignJNDIProvider ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Find a ForeignJNDIProvider resource with the given name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignJNDIProviders");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupJDBCSystemResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JDBCSystemResource ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Find a JDBCSystemResource with the given name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JDBCSystemResources");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupWLDFSystemResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the WLDFSystemResource. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up a WLDFSystemResourceMBean by name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WLDFSystemResources");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupSAFAgent", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the SAFAgent mbean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up a SAFAgentMBean by name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFAgents");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupCoherenceClusterSystemResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the CoherenceClusterSystemResource ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("deprecated", "12.2.1.1.0 Coherence Clusters cannot be targeted to ResourceGroupTemplates ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Find a CoherenceClusterSystemResource with the given name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CoherenceClusterSystemResources");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupOsgiFramework", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the key of the osgi framework. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Look up an OSGi framework by name</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "OsgiFrameworks");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupManagedExecutorService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedExecutorServiceMBean mbean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Lookup a particular ManagedExecutorService from the list. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ManagedExecutorServices");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupManagedScheduledExecutorService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedScheduledExecutorServiceMBean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Lookup a particular ManagedScheduledExecutorService from the list. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ManagedScheduledExecutorServices");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("lookupManagedThreadFactory", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedThreadFactory to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Lookup a particular ManagedThreadFactory from the list. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ManagedThreadFactories");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceGroupTemplateMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("processResources", GeneralResourceProcessor.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("processor", "the processor to invoke ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Invokes the specified processor for each resource configured in the resource group template. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupTemplateMBean.class.getMethod("processResources", DiscreteResourceProcessor.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("processor", "the processor to invoke ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Invokes the specified processor for each resource configured in the resource group template. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
