package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JTAMigratableTargetMBeanImplBeanInfo extends MigratableTargetMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JTAMigratableTargetMBean.class;

   public JTAMigratableTargetMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JTAMigratableTargetMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JTAMigratableTargetMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("MigratableTargetMBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("The target that is used internally to register the JTA recovery manager to the Migration Manager. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JTAMigratableTargetMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AllCandidateServers")) {
         getterName = "getAllCandidateServers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllCandidateServers";
         }

         currentResult = new PropertyDescriptor("AllCandidateServers", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("AllCandidateServers", currentResult);
         currentResult.setValue("description", "<p>The list of servers that are candidates to host the migratable services deployed to this migratable target. If the constrainedCandidateServers list is empty, all servers in the cluster are returned. If the constrainedCandidateServers list is not empty, only those servers will be returned. The user-preferred server will be the first element in the list.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Cluster")) {
         getterName = "getCluster";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCluster";
         }

         currentResult = new PropertyDescriptor("Cluster", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("Cluster", currentResult);
         currentResult.setValue("description", "<p>Returns the cluster this singleton service is associated with.</p> ");
         currentResult.setValue("relationship", "reference");
      }

      if (!descriptors.containsKey("ConstrainedCandidateServers")) {
         getterName = "getConstrainedCandidateServers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConstrainedCandidateServers";
         }

         currentResult = new PropertyDescriptor("ConstrainedCandidateServers", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("ConstrainedCandidateServers", currentResult);
         currentResult.setValue("description", "<p>The (user-restricted) list of servers that can host the migratable services deployed to this migratable target. The migratable service will not be allowed to migrate to a server that is not in the returned list of servers.</p>  <p>For example, this feature may be used to configure two servers that have access to a dual-ported ported disk. All servers in this list must be part of the cluster that is associated with the migratable target.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeConstrainedCandidateServer");
         currentResult.setValue("adder", "addConstrainedCandidateServer");
      }

      if (!descriptors.containsKey("HostingServer")) {
         getterName = "getHostingServer";
         setterName = null;
         currentResult = new PropertyDescriptor("HostingServer", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("HostingServer", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the server that currently hosts the singleton service.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("MigrationPolicy")) {
         getterName = "getMigrationPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMigrationPolicy";
         }

         currentResult = new PropertyDescriptor("MigrationPolicy", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("MigrationPolicy", currentResult);
         currentResult.setValue("description", "<p>Defines the type of migration policy to use for the services hosted by this migratable target. Valid options are:</p> <ul> <li><code>Manual Service Migration Only</code> Indicates that no automatic migration of services hosted by this migratable target will occur.</li> <li><code>Auto-Migrate Failure-Recovery Services</code> Indicates that the services hosted by this migratable target will only start if the migratable target's User Preferred Server (UPS) is started. If an administrator manually shuts down the UPS, either gracefully or forcibly, then a failure-recovery service will not migrate. However, if the UPS fails due to an internal error, then the service will be migrated to another candidate server. If such a candidate server is unavailable (due to either a manual shutdown or an internal failure), then the migration framework will first attempt to reactivate the service on its UPS server. If the UPS server is not available at that time, then the service will be migrated to another candidate server.</li> <li><code>Auto-Migrate Shutdown-Recovery Services</code> Indicates that the services hosted by this migratable target will migrate to one of the candidate servers, if server is administratively shut down (either gracefully or forcibly). Once recovery is done, service is migrated back to failed server.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "manual");
         currentResult.setValue("legalValues", new Object[]{"manual", "failure-recovery", "shutdown-recovery"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("UserPreferredServer")) {
         getterName = "getUserPreferredServer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserPreferredServer";
         }

         currentResult = new PropertyDescriptor("UserPreferredServer", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("UserPreferredServer", currentResult);
         currentResult.setValue("description", "<p>Returns the server that the user prefers the singleton service to be active on.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("StrictOwnershipCheck")) {
         getterName = "isStrictOwnershipCheck";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStrictOwnershipCheck";
         }

         currentResult = new PropertyDescriptor("StrictOwnershipCheck", JTAMigratableTargetMBean.class, getterName, setterName);
         descriptors.put("StrictOwnershipCheck", currentResult);
         currentResult.setValue("description", "<p>Whether continue to boot if cannot find the current owner of TRS to do failback. This attribute is only meaningful for servers in cluster. </p>  <p>If true: server will fail to boot under this situation.</p> <p>If false: server will continue to boot without trying to do failback.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JTAMigratableTargetMBean.class.getMethod("addConstrainedCandidateServer", ServerMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("constrainedCandidateServer", "The feature to be added to the ConstrainedCandidateServer attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "ConstrainedCandidateServers");
      }

      mth = JTAMigratableTargetMBean.class.getMethod("removeConstrainedCandidateServer", ServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("constrainedCandidateServer", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "ConstrainedCandidateServers");
      }

      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JTAMigratableTargetMBean.class.getMethod("addTag", String.class);
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
         mth = JTAMigratableTargetMBean.class.getMethod("removeTag", String.class);
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
      Method mth = JTAMigratableTargetMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = JTAMigratableTargetMBean.class.getMethod("restoreDefaultValue", String.class);
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
