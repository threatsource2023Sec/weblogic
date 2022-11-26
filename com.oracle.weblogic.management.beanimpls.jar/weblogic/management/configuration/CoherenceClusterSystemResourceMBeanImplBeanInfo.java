package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceClusterSystemResourceMBeanImplBeanInfo extends SystemResourceMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceClusterSystemResourceMBean.class;

   public CoherenceClusterSystemResourceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceClusterSystemResourceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CoherenceClusterSystemResourceMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This bean defines a system-level Coherence cluster resource. It links to a separate descriptor that specifies the definition. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CoherenceClusterSystemResourceMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClusterHosts")) {
         getterName = "getClusterHosts";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterHosts", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("ClusterHosts", currentResult);
         currentResult.setValue("description", "<p>The list of hostnames of the managed servers belonging to this Coherence cluster.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("CoherenceCacheConfigs")) {
         getterName = "getCoherenceCacheConfigs";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceCacheConfigs", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("CoherenceCacheConfigs", currentResult);
         currentResult.setValue("description", "<p>An array of CacheConfigBeans, each of which represents a cache configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCoherenceCacheConfig");
         currentResult.setValue("destroyer", "destroyCoherenceCacheConfig");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceClusterResource")) {
         getterName = "getCoherenceClusterResource";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterResource", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("CoherenceClusterResource", currentResult);
         currentResult.setValue("description", "<p>The Coherence cluster resource descriptor.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomClusterConfigurationFileName")) {
         getterName = "getCustomClusterConfigurationFileName";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomClusterConfigurationFileName", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("CustomClusterConfigurationFileName", currentResult);
         currentResult.setValue("description", "<p> The external custom Coherence cluster configuration file. </p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("CustomConfigFileLastUpdatedTime")) {
         getterName = "getCustomConfigFileLastUpdatedTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomConfigFileLastUpdatedTime", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("CustomConfigFileLastUpdatedTime", currentResult);
         currentResult.setValue("description", "<p>The time when the custom configuration file used by the cluster was last updated</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("DescriptorFileName")) {
         getterName = "getDescriptorFileName";
         setterName = null;
         currentResult = new PropertyDescriptor("DescriptorFileName", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("DescriptorFileName", currentResult);
         currentResult.setValue("description", "<p>The name of the file that contains the module configuration. By default the file resides in the <code>DOMAIN_DIR/config/coherence/<i>bean_name</i></code> directory.</p>  <p>The module file derives its name from the bean name using the following pattern:</p>  <p>&lt;beanName&gt;.xml</p> <p>Note that this is a read-only property that can only be set when the bean is created.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("FederationRemoteClusterListenPort")) {
         getterName = "getFederationRemoteClusterListenPort";
         setterName = null;
         currentResult = new PropertyDescriptor("FederationRemoteClusterListenPort", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("FederationRemoteClusterListenPort", currentResult);
         currentResult.setValue("description", "<p>The Coherence Cluster Listen Port of the remote participant. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("FederationRemoteClusterName")) {
         getterName = "getFederationRemoteClusterName";
         setterName = null;
         currentResult = new PropertyDescriptor("FederationRemoteClusterName", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("FederationRemoteClusterName", currentResult);
         currentResult.setValue("description", "<p>The Coherence Cluster Name of the remote participant cluster. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("FederationRemoteParticipantHosts")) {
         getterName = "getFederationRemoteParticipantHosts";
         setterName = null;
         currentResult = new PropertyDescriptor("FederationRemoteParticipantHosts", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("FederationRemoteParticipantHosts", currentResult);
         currentResult.setValue("description", "<p>The list of remote participant hosts, who will be added as participants in the federation topology. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("FederationTopology")) {
         getterName = "getFederationTopology";
         setterName = null;
         currentResult = new PropertyDescriptor("FederationTopology", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("FederationTopology", currentResult);
         currentResult.setValue("description", "<p>The federation topology. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("PersistenceActiveDirectory")) {
         getterName = "getPersistenceActiveDirectory";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceActiveDirectory", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("PersistenceActiveDirectory", currentResult);
         currentResult.setValue("description", "<p>The active directory for the default persistence environment. If no value is specified, the directory which will be used is the coherence/active sub-directory under Domain Home directory. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("PersistenceDefaultMode")) {
         getterName = "getPersistenceDefaultMode";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceDefaultMode", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("PersistenceDefaultMode", currentResult);
         currentResult.setValue("description", "<p>The default persistence mode. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("PersistenceSnapshotDirectory")) {
         getterName = "getPersistenceSnapshotDirectory";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceSnapshotDirectory", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("PersistenceSnapshotDirectory", currentResult);
         currentResult.setValue("description", "<p>The snapshot directory for the default persistence environment. If no value is specified, the directory which will be used is coherence/snapshots sub-directory under Domain Home directory. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("PersistenceTrashDirectory")) {
         getterName = "getPersistenceTrashDirectory";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceTrashDirectory", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("PersistenceTrashDirectory", currentResult);
         currentResult.setValue("description", "<p>The trash directory for the default persistence environment. If no value is specified, the directory which will be used is coherence/trash sub-directory under Domain Home directory. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("ReportGroupFile")) {
         getterName = "getReportGroupFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReportGroupFile";
         }

         currentResult = new PropertyDescriptor("ReportGroupFile", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("ReportGroupFile", currentResult);
         currentResult.setValue("description", "<p>Get the report group file representing the superset of runtime metrics to be collected for this cluster.</p> ");
         setPropertyDescriptorDefault(currentResult, "em/metadata/reports/coherence/report-group.xml");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Resource")) {
         getterName = "getResource";
         setterName = null;
         currentResult = new PropertyDescriptor("Resource", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("Resource", currentResult);
         currentResult.setValue("description", "<p>Return the Descriptor for the system resource. This should be overridden by the derived system resources.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>You must select a target on which an MBean will be deployed from this list of the targets in the current domain on which this item can be deployed. Targets must be either servers or clusters. The deployment will only occur once if deployments overlap.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("UsingCustomClusterConfigurationFile")) {
         getterName = "isUsingCustomClusterConfigurationFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUsingCustomClusterConfigurationFile";
         }

         currentResult = new PropertyDescriptor("UsingCustomClusterConfigurationFile", CoherenceClusterSystemResourceMBean.class, getterName, setterName);
         descriptors.put("UsingCustomClusterConfigurationFile", currentResult);
         currentResult.setValue("description", "<p> Specifies whether you are using a custom external Coherence cluster configuration file.</p> ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceClusterSystemResourceMBean.class.getMethod("createCoherenceCacheConfig", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory to create CoherenceCacheConfigMBean instances.</p>  <p>This method is here to force the binding code to generate correctly.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceCacheConfigs");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion)) {
         mth = CoherenceClusterSystemResourceMBean.class.getMethod("destroyCoherenceCacheConfig", CoherenceCacheConfigMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cacheConfig", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>destroys CoherenceCacheConfigMBean</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CoherenceCacheConfigs");
            currentResult.setValue("since", "12.1.2.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceClusterSystemResourceMBean.class.getMethod("addTarget", TargetMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the Target attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>You can add a target to specify additional servers on which the deployment can be deployed. The targets must be either clusters or servers.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = CoherenceClusterSystemResourceMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The target to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the value of the Target attribute.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addTarget")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = CoherenceClusterSystemResourceMBean.class.getMethod("addTag", String.class);
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
         mth = CoherenceClusterSystemResourceMBean.class.getMethod("removeTag", String.class);
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
      Method mth = CoherenceClusterSystemResourceMBean.class.getMethod("lookupCoherenceCacheConfig", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a CoherenceCacheConfigMBean with the specified name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CoherenceCacheConfigs");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceClusterSystemResourceMBean.class.getMethod("importCustomClusterConfigurationFile", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("file", "Absolute path to the custom Coherence cluster configuration file ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Allows you to specify a custom Coherence cluster configuration file. The file must be present locally on the Administration Server.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = CoherenceClusterSystemResourceMBean.class.getMethod("freezeCurrentValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = CoherenceClusterSystemResourceMBean.class.getMethod("restoreDefaultValue", String.class);
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
