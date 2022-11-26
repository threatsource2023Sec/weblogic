package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class MigratableTargetMBeanImplBeanInfo extends SingletonServiceBaseMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MigratableTargetMBean.class;

   public MigratableTargetMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MigratableTargetMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.MigratableTargetMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("TargetMBean"), BeanInfoHelper.encodeEntities("SingletonServiceBaseMBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("A target that is suitable for services that shall be active on at most one server of a cluster at a time. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.MigratableTargetMBean");
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

         currentResult = new PropertyDescriptor("AllCandidateServers", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("AllCandidateServers", currentResult);
         currentResult.setValue("description", "<p>The list of servers that are candidates to host the migratable services deployed to this migratable target. If the constrainedCandidateServers list is empty, all servers in the cluster are returned. If the constrainedCandidateServers list is not empty, only those servers will be returned. The user-preferred server will be the first element in the list.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Cluster")) {
         getterName = "getCluster";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCluster";
         }

         currentResult = new PropertyDescriptor("Cluster", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("Cluster", currentResult);
         currentResult.setValue("description", "<p>Returns the cluster this singleton service is associated with.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConstrainedCandidateServers")) {
         getterName = "getConstrainedCandidateServers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConstrainedCandidateServers";
         }

         currentResult = new PropertyDescriptor("ConstrainedCandidateServers", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("ConstrainedCandidateServers", currentResult);
         currentResult.setValue("description", "<p>The (user-restricted) list of servers that can host the migratable services deployed to this migratable target. The migratable service will not be allowed to migrate to a server that is not in the returned list of servers.</p>  <p>For example, this feature may be used to configure two servers that have access to a dual-ported ported disk. All servers in this list must be part of the cluster that is associated with the migratable target.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeConstrainedCandidateServer");
         currentResult.setValue("adder", "addConstrainedCandidateServer");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HostingServer")) {
         getterName = "getHostingServer";
         setterName = null;
         currentResult = new PropertyDescriptor("HostingServer", MigratableTargetMBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("MigrationPolicy", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("MigrationPolicy", currentResult);
         currentResult.setValue("description", "<p>Defines the type of migration policy to use for the services hosted by this migratable target. Valid options are:</p> <ul> <li><code>Manual Service Migration Only</code> Indicates that no automatic migration of services hosted by this migratable target will occur.</li> <li><code>Auto-Migrate Exactly-Once Services</code> Indicates that if at least one Managed Server in the candidate server list is running, the services hosted by this migratable target will be active somewhere in the cluster if servers should fail or are administratively shut down (either gracefully or forcibly). For example, it is a recommended best practice to use this policy when a migratable target hosts a path service, so if its preferred server fails or is shut down, the path service will automatically migrate to another candidate server, and so will always be active in the cluster. <p><b>Notes</b> <br/>This value can lead to target grouping on a server member. For example, if you have five exactly-once migratable targets and only one Managed Server is started in the cluster, then all five targets will be activated on that server. <br/>This policy does not apply for JTA service migration.</p></li> <li><code>Auto-Migrate Failure-Recovery Services</code> Indicates that the services hosted by this migratable target will only start if the migratable target's User Preferred Server (UPS) is started. If an administrator manually shuts down the UPS, either gracefully or forcibly, then a failure-recovery service will not migrate. However, if the UPS fails due to an internal error, then the service will be migrated to another candidate server. If such a candidate server is unavailable (due to either a manual shutdown or an internal failure), then the migration framework will first attempt to reactivate the service on its UPS server. If the UPS server is not available at that time, then the service will be migrated to another candidate server.</li> </ul> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setUserPreferredServer")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "manual");
         currentResult.setValue("legalValues", new Object[]{"manual", "exactly-once", "failure-recovery", "shutdown-recovery"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("NumberOfRestartAttempts")) {
         getterName = "getNumberOfRestartAttempts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumberOfRestartAttempts";
         }

         currentResult = new PropertyDescriptor("NumberOfRestartAttempts", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("NumberOfRestartAttempts", currentResult);
         currentResult.setValue("description", "<p>Specifies how many restart attempts to make before migrating the failed service.</p>  <p>Note that these are consecutive attempts. If the value is set to 6, and the service restart fails 5 times before succeeding, but then fails again later, it will not instantly migrate. Each failure gets its own count of restart attempts.</p>  <p>A value of 0 is identical to setting {@link #getRestartOnFailure} to false. A value of -1 indicates the service should <i> never</i> be migrated; instead, it will be restarted until it either works or the server shuts down.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(6));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostScript")) {
         getterName = "getPostScript";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPostScript";
         }

         currentResult = new PropertyDescriptor("PostScript", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("PostScript", currentResult);
         currentResult.setValue("description", "<p>Specifies the path to the post-migration script to run after a migratable target is fully deactivated. The script <i>must</i> be in the <code><i>MIDDLEWARE_HOME</i>/user_projects/domains/<i>mydomain</i>/bin/service_migration</code> directory.</p>  <p>After the migratable target is deactivated, if there is a script specified, <i>and</i> Node Manager is available, then the script will run. Specifying a script without an available Node Manager will result in an error upon migration.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreScript")) {
         getterName = "getPreScript";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPreScript";
         }

         currentResult = new PropertyDescriptor("PreScript", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("PreScript", currentResult);
         currentResult.setValue("description", "<p>Specifies the path to the pre-migration script to run before a migratable target is actually activated. The script <i>must</i> be in the <code><i>MIDDLEWARE_HOME</i>/user_projects/domains/<i>mydomain</i>/bin/service_migration</code> directory.</p>  <p>Before the migratable target is activated, if there is a script specified, <i>and</i> Node Manager is available, then the script will run. Specifying a script without an available Node Manager will result in an error upon migration.</p>  <p>If the script fails or cannot be found, migration will not proceed on the current server, and will be tried on the next suitable server. This could be the next server in the candidate server list, or in the cluster, if there is no candidate server list.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestartOnFailure")) {
         getterName = "getRestartOnFailure";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartOnFailure";
         }

         currentResult = new PropertyDescriptor("RestartOnFailure", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("RestartOnFailure", currentResult);
         currentResult.setValue("description", "<p>Specifies whether or not a failed service will first be deactivated and reactivated in place, instead of being migrated.</p>  <p>The number of restart attempts is controlled by {@link #getNumberOfRestartAttempts}. Once these restart attempts are exhausted, the service will migrate. A restarting migratable target will deactivate all services on it in order, then reactivate them all.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondsBetweenRestarts")) {
         getterName = "getSecondsBetweenRestarts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecondsBetweenRestarts";
         }

         currentResult = new PropertyDescriptor("SecondsBetweenRestarts", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("SecondsBetweenRestarts", currentResult);
         currentResult.setValue("description", "<p>Specifies how many seconds to wait in between attempts to restart the failed service.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", MigratableTargetMBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("UserPreferredServer", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("UserPreferredServer", currentResult);
         currentResult.setValue("description", "<p>Returns the server that the user prefers the singleton service to be active on.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Critical")) {
         getterName = "isCritical";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCritical";
         }

         currentResult = new PropertyDescriptor("Critical", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("Critical", currentResult);
         currentResult.setValue("description", "<p>Returns true if the MigratableTarget is critical to the overall health of the WLS Server</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("NonLocalPostAllowed")) {
         getterName = "isNonLocalPostAllowed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNonLocalPostAllowed";
         }

         currentResult = new PropertyDescriptor("NonLocalPostAllowed", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("NonLocalPostAllowed", currentResult);
         currentResult.setValue("description", "<p>Specifies whether or not the post-deactivation script is allowed to run on a different machine.</p>  <p>Normally, when auto migration occurs, the post-deactivation script will be run on the service's current location, and the pre-activation script on the service's new location. If the current location is unreachable for some reason, this value will be checked to see if it is safe to run it on the service's new machine.</p>  <p>This is useful if the post-deactivation script controls access to a networked resource and does not need any data from the current machine.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostScriptFailureFatal")) {
         getterName = "isPostScriptFailureFatal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPostScriptFailureFatal";
         }

         currentResult = new PropertyDescriptor("PostScriptFailureFatal", MigratableTargetMBean.class, getterName, setterName);
         descriptors.put("PostScriptFailureFatal", currentResult);
         currentResult.setValue("description", "<p>Specifies whether or not a failure during execution of the post-deactivation script is fatal to the migration.</p>  <p>If it is fatal, the migratable target will <i>not</i> be automatically migrated until an administrator manually migrates it to a server, thus reactivating it.</p>  <p><b>Note:</b> Enabling this value will result in weakening the exactly-once guarantee. It is provided to prevent more dangerous data corruption if the post-deactivation script fails. Also if this value is enabled, then the script may be called more than once by the migration framework after the Migratable Target is deactivated or the server or machine hosting the Migratable Target crashed or is network partitioned. The script is expected not to return different exit values when invoked multiple times in such scenarios.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = MigratableTargetMBean.class.getMethod("addConstrainedCandidateServer", ServerMBean.class);
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

      mth = MigratableTargetMBean.class.getMethod("removeConstrainedCandidateServer", ServerMBean.class);
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
         mth = MigratableTargetMBean.class.getMethod("addTag", String.class);
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
         mth = MigratableTargetMBean.class.getMethod("removeTag", String.class);
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
      Method mth = MigratableTargetMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = MigratableTargetMBean.class.getMethod("restoreDefaultValue", String.class);
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
