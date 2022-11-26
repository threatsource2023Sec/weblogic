package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DynamicServersMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DynamicServersMBean.class;

   public DynamicServersMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DynamicServersMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.DynamicServersMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "release specific (what release of product did this appear in)");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Contains the properties used to control how dynamic servers are created. These properties allow you to control if listens ports are calculated, how servers are assigned to machines, and the number of dynamic servers to create.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DynamicServersMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DynamicClusterCooloffPeriodSeconds")) {
         getterName = "getDynamicClusterCooloffPeriodSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDynamicClusterCooloffPeriodSeconds";
         }

         currentResult = new PropertyDescriptor("DynamicClusterCooloffPeriodSeconds", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("DynamicClusterCooloffPeriodSeconds", currentResult);
         currentResult.setValue("description", "Get the cool-off period (in seconds) used by the Elasticity Framework while performing scale up or scale down operations. If a scale up or scale down operation was performed, subsequent requests for scale up or down operations will be ignored by the Elasticity Framework during this period. ");
         setPropertyDescriptorDefault(currentResult, new Integer(900));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DynamicClusterShutdownTimeoutSeconds")) {
         getterName = "getDynamicClusterShutdownTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDynamicClusterShutdownTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("DynamicClusterShutdownTimeoutSeconds", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("DynamicClusterShutdownTimeoutSeconds", currentResult);
         currentResult.setValue("description", "Get the timeout period (in seconds) used by the Elasticity Framework while gracefully shutting down a server. If the server does not shut down before the specified timeout period, the server will be forcibly shut down. With a negative time value, no timeout will be applied. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.ServerLifeCycleRuntimeMBean#shutdown(int, boolean, boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DynamicClusterSize")) {
         getterName = "getDynamicClusterSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDynamicClusterSize";
         }

         currentResult = new PropertyDescriptor("DynamicClusterSize", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("DynamicClusterSize", currentResult);
         currentResult.setValue("description", "Get the current size of the dynamic cluster (the number of dynamic server instances allowed to be created). The specified number of server instances are dynamically added to the configuration at runtime and associated ServerLifeCycleRuntimeMBeans are created. ");
         currentResult.setValue("legalMax", new Integer(800));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicServerNames")) {
         getterName = "getDynamicServerNames";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicServerNames", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("DynamicServerNames", currentResult);
         currentResult.setValue("description", "Return an array of all the dynamic server names ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MachineMatchExpression")) {
         getterName = "getMachineMatchExpression";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMachineMatchExpression";
         }

         currentResult = new PropertyDescriptor("MachineMatchExpression", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("MachineMatchExpression", currentResult);
         currentResult.setValue("description", "Get the machine tag expression to use when selecting machine names.  If the MachineMatchType is \"name\", each value will either match a machine name exactly or, if specified with a trailing '*' suffix, will match multiple machine names. If the MachineMatchType is \"tag\", the values will match all of the machines that have all of the tag values. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MachineMatchType")) {
         getterName = "getMachineMatchType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMachineMatchType";
         }

         currentResult = new PropertyDescriptor("MachineMatchType", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("MachineMatchType", currentResult);
         currentResult.setValue("description", "Get the machine tag expression to use when selecting machine names.  If the MachineMatchType is \"name\", each value will either match a machine name exactly or, if specified with a trailing '*' suffix, will match multiple machine names. If the MachineMatchType is \"tag\", the values will match all of the machines that have all of the tag values. ");
         setPropertyDescriptorDefault(currentResult, "name");
         currentResult.setValue("legalValues", new Object[]{"name", "tag"});
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("MachineNameMatchExpression")) {
         getterName = "getMachineNameMatchExpression";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMachineNameMatchExpression";
         }

         currentResult = new PropertyDescriptor("MachineNameMatchExpression", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("MachineNameMatchExpression", currentResult);
         currentResult.setValue("description", "The expression is used when determining machines to use for server assignments. If null, then all machines in the domain are used. If a expression is provided, then only the machines in the domain that match the expression will be used when assigning machine names to dynamic servers.  The expression is a comma separated set of values that specify the machines to match. Each value will either match a machine name exactly or if specified with a trailing '*' suffix, the value will match multiple machine names. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxDynamicClusterSize")) {
         getterName = "getMaxDynamicClusterSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxDynamicClusterSize";
         }

         currentResult = new PropertyDescriptor("MaxDynamicClusterSize", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("MaxDynamicClusterSize", currentResult);
         currentResult.setValue("description", "Get the upper bound on the DynamicClusterSize that the Elasticity Framework is allowed to assign to DynamicClusterSize. ");
         setPropertyDescriptorDefault(currentResult, new Integer(8));
         currentResult.setValue("legalMax", new Integer(800));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("MaximumDynamicServerCount")) {
         getterName = "getMaximumDynamicServerCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumDynamicServerCount";
         }

         currentResult = new PropertyDescriptor("MaximumDynamicServerCount", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("MaximumDynamicServerCount", currentResult);
         currentResult.setValue("description", "The maximum number of server instances allowed to be created. This value should be set to the number of servers expected to be in the cluster. The specified number of servers are dynamically added to the configuration at runtime and associated ServerLifeCycleRuntimeMBeans are created. ");
         currentResult.setValue("legalMax", new Integer(800));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("deprecated", "12.2.1.0.0 Use getDynamicClusterSize() ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MinDynamicClusterSize")) {
         getterName = "getMinDynamicClusterSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinDynamicClusterSize";
         }

         currentResult = new PropertyDescriptor("MinDynamicClusterSize", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("MinDynamicClusterSize", currentResult);
         currentResult.setValue("description", "Get the minimum number of running server instances that the Elasticity Framework will attempt to keep in the dynamic cluster. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(800));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ServerNamePrefix")) {
         getterName = "getServerNamePrefix";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerNamePrefix";
         }

         currentResult = new PropertyDescriptor("ServerNamePrefix", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("ServerNamePrefix", currentResult);
         currentResult.setValue("description", "The server name prefix is used to specify the naming convention when creating server names. When the server name prefix is defined, server names are calculated with the specified prefix followed by the index starting with the value specified by {@link #getServerNameStartingIndex()}. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.3.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerNameStartingIndex")) {
         getterName = "getServerNameStartingIndex";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerNameStartingIndex";
         }

         currentResult = new PropertyDescriptor("ServerNameStartingIndex", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("ServerNameStartingIndex", currentResult);
         currentResult.setValue("description", "Get the starting index to use in server names.  Server names are calculated with the prefix specified in {@link #getServerNamePrefix()} followed by an index starting with the value specified in this attribute. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getServerNamePrefix()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(2147480000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.3.1.0.0");
      }

      if (!descriptors.containsKey("ServerTemplate")) {
         getterName = "getServerTemplate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerTemplate";
         }

         currentResult = new PropertyDescriptor("ServerTemplate", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("ServerTemplate", currentResult);
         currentResult.setValue("description", "The server template used to create dynamic servers. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("CalculatedListenPorts")) {
         getterName = "isCalculatedListenPorts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCalculatedListenPorts";
         }

         currentResult = new PropertyDescriptor("CalculatedListenPorts", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("CalculatedListenPorts", currentResult);
         currentResult.setValue("description", "Specifies whether listen ports are calculated. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CalculatedMachineNames")) {
         getterName = "isCalculatedMachineNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCalculatedMachineNames";
         }

         currentResult = new PropertyDescriptor("CalculatedMachineNames", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("CalculatedMachineNames", currentResult);
         currentResult.setValue("description", "The CalculatedMachineNames attribute controls how server instances in a dynamic cluster are assigned to a machine. If the attribute is not set, then the dynamic servers will not be assigned to a machine. If the attribute is set, then the MachineNameMatchExpression attribute is used to select the set of machines to use for the dynamic servers. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("IgnoreSessionsDuringShutdown")) {
         getterName = "isIgnoreSessionsDuringShutdown";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreSessionsDuringShutdown";
         }

         currentResult = new PropertyDescriptor("IgnoreSessionsDuringShutdown", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("IgnoreSessionsDuringShutdown", currentResult);
         currentResult.setValue("description", "Indicates if the Elasticity Framework should ignore inflight HTTP sessions while shutting down servers. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.ServerLifeCycleRuntimeMBean#shutdown(int, boolean, boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WaitForAllSessionsDuringShutdown")) {
         getterName = "isWaitForAllSessionsDuringShutdown";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWaitForAllSessionsDuringShutdown";
         }

         currentResult = new PropertyDescriptor("WaitForAllSessionsDuringShutdown", DynamicServersMBean.class, getterName, setterName);
         descriptors.put("WaitForAllSessionsDuringShutdown", currentResult);
         currentResult.setValue("description", "Indicates if the Elasticity Framework should wait for all (persisted and non-persisted) HTTP sessions during inflight work handling while shutting down servers. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.ServerLifeCycleRuntimeMBean#shutdown(int, boolean, boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DynamicServersMBean.class.getMethod("addTag", String.class);
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
         mth = DynamicServersMBean.class.getMethod("removeTag", String.class);
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
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DynamicServersMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = DynamicServersMBean.class.getMethod("restoreDefaultValue", String.class);
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
