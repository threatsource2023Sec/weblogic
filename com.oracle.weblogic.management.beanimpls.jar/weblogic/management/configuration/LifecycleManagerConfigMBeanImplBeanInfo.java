package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class LifecycleManagerConfigMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LifecycleManagerConfigMBean.class;

   public LifecycleManagerConfigMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LifecycleManagerConfigMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.LifecycleManagerConfigMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("LifecycleManagerConfigMBean maintains the information necessary to enable and configure a LifecycleManager instance associated with this domain.  LifecycleManager instances may either be local or remote to this domain. User credentials may be configured to support authentication, especially important when interaction with a remote domain. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.LifecycleManagerConfigMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConfigFileLockTimeout")) {
         getterName = "getConfigFileLockTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfigFileLockTimeout";
         }

         currentResult = new PropertyDescriptor("ConfigFileLockTimeout", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("ConfigFileLockTimeout", currentResult);
         currentResult.setValue("description", "<p>Returns the Lifecycle configuration lock timeout. This is used when the persistence type is LifecycleManagerConfigMBean.PERSISTENCE_TYPE_XML, while attempting to lock the configuration file to persist configuration changes. </p> ");
         setPropertyDescriptorDefault(currentResult, new Long(120000L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("ConfiguredEndPoints")) {
         getterName = "getConfiguredEndPoints";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfiguredEndPoints", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("ConfiguredEndPoints", currentResult);
         currentResult.setValue("description", "<p>Returns the LifecycleManager endpoints that have been configured as involved with this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyConfiguredEndPoint");
         currentResult.setValue("creator", "createConfiguredEndPoint");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataSourceName")) {
         getterName = "getDataSourceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceName";
         }

         currentResult = new PropertyDescriptor("DataSourceName", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("DataSourceName", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the DataSource that should be used when LifecycleManager is configured to maintain its configuration in a database.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentType")) {
         getterName = "getDeploymentType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeploymentType";
         }

         currentResult = new PropertyDescriptor("DeploymentType", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("DeploymentType", currentResult);
         currentResult.setValue("description", "<p>The deployment model for LifecycleManager services in this domain.</p> ");
         setPropertyDescriptorDefault(currentResult, "none");
         currentResult.setValue("legalValues", new Object[]{"none", "admin", "cluster"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EndPoints")) {
         getterName = "getEndPoints";
         setterName = null;
         currentResult = new PropertyDescriptor("EndPoints", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("EndPoints", currentResult);
         currentResult.setValue("description", "<p>Returns the REST endpoints for each LifecycleManager that is participating in the management of this domain.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("restRelationship", "reference");
      }

      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("LCMInitiatedConnectTimeout")) {
         getterName = "getLCMInitiatedConnectTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLCMInitiatedConnectTimeout";
         }

         currentResult = new PropertyDescriptor("LCMInitiatedConnectTimeout", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("LCMInitiatedConnectTimeout", currentResult);
         currentResult.setValue("description", "Returns setting for connect timeout for LCM initiated REST requests which may have been triggered by OOB, sync, patching. 0 return implies that the option is disabled (i.e., timeout of infinity). ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("java.net.URLConnection#getConnectTimeout()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("LCMInitiatedConnectTimeoutForElasticity")) {
         getterName = "getLCMInitiatedConnectTimeoutForElasticity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLCMInitiatedConnectTimeoutForElasticity";
         }

         currentResult = new PropertyDescriptor("LCMInitiatedConnectTimeoutForElasticity", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("LCMInitiatedConnectTimeoutForElasticity", currentResult);
         currentResult.setValue("description", "Returns setting for connect timeout for LCM initiated REST requests triggered by Elasticity 0 return implies that the option is disabled (i.e., timeout of infinity). ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("java.net.URLConnection#getConnectTimeout()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("LCMInitiatedReadTimeout")) {
         getterName = "getLCMInitiatedReadTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLCMInitiatedReadTimeout";
         }

         currentResult = new PropertyDescriptor("LCMInitiatedReadTimeout", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("LCMInitiatedReadTimeout", currentResult);
         currentResult.setValue("description", " ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("java.net.URLConnection#getReadTimeout()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("LCMInitiatedReadTimeoutForElasticity")) {
         getterName = "getLCMInitiatedReadTimeoutForElasticity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLCMInitiatedReadTimeoutForElasticity";
         }

         currentResult = new PropertyDescriptor("LCMInitiatedReadTimeoutForElasticity", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("LCMInitiatedReadTimeoutForElasticity", currentResult);
         currentResult.setValue("description", " ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("java.net.URLConnection#getReadTimeout()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("PeriodicSyncInterval")) {
         getterName = "getPeriodicSyncInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPeriodicSyncInterval";
         }

         currentResult = new PropertyDescriptor("PeriodicSyncInterval", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("PeriodicSyncInterval", currentResult);
         currentResult.setValue("description", "<p>Get periodic interval for lifecycle configuration synchronization in hours. When synchronizing configuration, Lifecycle contacts the different runtimes that are registered with it, gets the list of partitions from those runtimes, and ensure that its configuration is synchronized with those runtimes. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("PersistenceType")) {
         getterName = "getPersistenceType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistenceType";
         }

         currentResult = new PropertyDescriptor("PersistenceType", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("PersistenceType", currentResult);
         currentResult.setValue("description", "<p>The persistence model that is used by this LifecycleManager service. Either database or a local XML file may be used for an admin server deployment, but a database configuration model is required for a cluster deployment.</p> ");
         setPropertyDescriptorDefault(currentResult, "XML");
         currentResult.setValue("legalValues", new Object[]{"XML", "database"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("PropagationActivateTimeout")) {
         getterName = "getPropagationActivateTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPropagationActivateTimeout";
         }

         currentResult = new PropertyDescriptor("PropagationActivateTimeout", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("PropagationActivateTimeout", currentResult);
         currentResult.setValue("description", "<p>Returns the activation timeout in milliseconds for Lifecycle configuration propagation to managed server. </p> ");
         setPropertyDescriptorDefault(currentResult, new Long(180000L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerReadyTimeout")) {
         getterName = "getServerReadyTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerReadyTimeout";
         }

         currentResult = new PropertyDescriptor("ServerReadyTimeout", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("ServerReadyTimeout", currentResult);
         currentResult.setValue("description", "<p>The timeout in milliseconds for waiting for a server to be ready to receive requests.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(60000L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerRuntimeTimeout")) {
         getterName = "getServerRuntimeTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerRuntimeTimeout";
         }

         currentResult = new PropertyDescriptor("ServerRuntimeTimeout", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("ServerRuntimeTimeout", currentResult);
         currentResult.setValue("description", "<p>Returns the timeout in milliseconds for accessing server runtime mbeans. </p> ");
         setPropertyDescriptorDefault(currentResult, new Long(600000L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.2.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Target")) {
         getterName = "getTarget";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTarget";
         }

         currentResult = new PropertyDescriptor("Target", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("Target", currentResult);
         currentResult.setValue("description", "<p>The cluster target defined in the current domain that should host the LifecycleManager service when running deployed in Cluster mode.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("Enabled", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Determine if LifeycleManager features are available, either locally on this admin server or remotely via a configured endpoint.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutOfBandEnabled")) {
         getterName = "isOutOfBandEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOutOfBandEnabled";
         }

         currentResult = new PropertyDescriptor("OutOfBandEnabled", LifecycleManagerConfigMBean.class, getterName, setterName);
         descriptors.put("OutOfBandEnabled", currentResult);
         currentResult.setValue("description", "<p>Determine if LifecycleManager should listen for configuration changes on this server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = LifecycleManagerConfigMBean.class.getMethod("createConfiguredEndPoint", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the LifecycleManager endpoint configuration ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory for creating a new LifecycleManager endpoint configuration.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfiguredEndPoints");
      }

      mth = LifecycleManagerConfigMBean.class.getMethod("destroyConfiguredEndPoint", LifecycleManagerEndPointMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("endpoint", "to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes a LifecycleManager endpoint.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfiguredEndPoints");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = LifecycleManagerConfigMBean.class.getMethod("addTag", String.class);
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
         mth = LifecycleManagerConfigMBean.class.getMethod("removeTag", String.class);
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
      Method mth = LifecycleManagerConfigMBean.class.getMethod("lookupConfiguredEndPoint", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the LifecycleManager endpoint configuration ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Lookup a LifecycleManager EndPoint configuration by name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ConfiguredEndPoints");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = LifecycleManagerConfigMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = LifecycleManagerConfigMBean.class.getMethod("restoreDefaultValue", String.class);
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
