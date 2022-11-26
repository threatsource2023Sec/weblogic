package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.internal.ServiceImplBeanInfo;

public class ActivationTaskMBeanImplBeanInfo extends ServiceImplBeanInfo {
   public static final Class INTERFACE_CLASS = ActivationTaskMBean.class;

   public ActivationTaskMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ActivationTaskMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.ActivationTaskMBeanImpl");
      } catch (Throwable var6) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "Context");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ConfigurationManagerMBean#activate(long)")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String("<p>Provides information about an activation task, which is initiated by invoking the <code>ConfigurationManagerMBean activate</code> operation.</p>  <p>To describe the changes that are being activated in the domain, this MBean contains one <code>Configuration</code> object for each change that was saved to the domain's pending configuration files.</p>  <p>This MBean also contains attributes that describe the status of the activation operation as it attempts to distribute changes to all servers in the domain.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.ActivationTaskMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Changes")) {
         getterName = "getChanges";
         setterName = null;
         currentResult = new PropertyDescriptor("Changes", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("Changes", currentResult);
         currentResult.setValue("description", "<p>Contains the <code>Change</code> objects that describe the changes that are being activated.</p>  <p>Each <code>Change</code> object describes a change to a single MBean attribute.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompletionTime")) {
         getterName = "getCompletionTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletionTime", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("CompletionTime", currentResult);
         currentResult.setValue("description", "<p>The time at which the activation task completes, either by successfully activating changes on all servers, rolling back all changes (because a server was unable to consume the changes), or by timing out.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restName", "endTimeAsLong");
      }

      if (!descriptors.containsKey("Details")) {
         getterName = "getDetails";
         setterName = null;
         currentResult = new PropertyDescriptor("Details", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("Details", currentResult);
         currentResult.setValue("description", "<p>Contains all available information about the current activation task in a single <code>String</code> object.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("EditSessionName")) {
         getterName = "getEditSessionName";
         setterName = null;
         currentResult = new PropertyDescriptor("EditSessionName", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("EditSessionName", currentResult);
         currentResult.setValue("description", "<p>Returns name of edit session which is activated.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Error")) {
         getterName = "getError";
         setterName = null;
         currentResult = new PropertyDescriptor("Error", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("Error", currentResult);
         currentResult.setValue("description", "<p>Returns the exception that describes why the activation has failed.</p>  <p>To see how each server responded to the activation request, get the value of this MBean's <code>StatusByServer</code> attribute.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restName", "taskError");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>A unique key that WebLogic Server generates to identify the current instance of this MBean type.</p>  <p>For a singleton, such as <code>DomainRuntimeServiceMBean</code>, this key is often just the bean's short class name.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p>Returns partition name of edit session which is activated.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StartTime")) {
         getterName = "getStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("StartTime", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("StartTime", currentResult);
         currentResult.setValue("description", "<p>The time at which the <code>ConfigurationManagerMBean activate</code> operation was invoked.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restName", "startTimeAsLong");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The state of the activation task, which is initiated by invoking the <code>ConfigurationManagerMBean activate</code> operation.</p>  <p>States are:</p> <ul> <li>STATE_NEW - Indicates that the task has been created but distribution has not started.</li> <li>STATE_DISTRIBUTING - Indicates that the changes have been validated and are being distributed to the various servers.</li> <li>STATE_DISTRIBUTED -  Indicates that the changes have been distributed to all servers.</li> <li>STATE_PENDING - Indicates that the configuration changes require that the server be restarted for the changes to become available.</li> <li> STATE_COMMITTED - Indicates that the changes have been distributed to all servers and made permanent.</li> <li> STATE_FAILED -  Indicates that the changes failed in the distribution phase.</li> <li> STATE_CANCELING - Indicates that the changes are canceling in the distribution phase.</li> <li> STATE_COMMIT_FAILING - Indicates that the changes are failing in the commit phase. </li> </ul> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StatusByServer")) {
         getterName = "getStatusByServer";
         setterName = null;
         currentResult = new PropertyDescriptor("StatusByServer", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("StatusByServer", currentResult);
         currentResult.setValue("description", "<p>The state of the activation task on each server in the domain.</p>  <p>If any server fails to activate the changes, the activation task is rolled back for all servers in the domain.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SystemComponentsToRestart")) {
         getterName = "getSystemComponentsToRestart";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemComponentsToRestart", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("SystemComponentsToRestart", currentResult);
         currentResult.setValue("description", "Get list of names of system component, which need to be restarted. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "<p>The MBean type for this instance. This is useful for MBean types that support multiple intances, such as <code>ActivationTaskMBean</code>.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("User")) {
         getterName = "getUser";
         setterName = null;
         currentResult = new PropertyDescriptor("User", ActivationTaskMBean.class, getterName, (String)setterName);
         descriptors.put("User", currentResult);
         currentResult.setValue("description", "<p>The name of the user who invoked the <code>ConfigurationManagerMBean activate</code> operation.</p> ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ActivationTaskMBean.class.getMethod("waitForTaskCompletion");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Allows the caller to wait until the activation task completes (either by successfully distributing changes or by rolling back the task) or times out.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ActivationTaskMBean.class.getMethod("waitForTaskCompletion", Long.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "long that specifies the time (milliseconds) to wait for the activate deployment request to complete. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Allows the caller to wait until any of the following occurs:</p>  <ul> <li>The activation task completes (either by successfully distributing changes or by rolling back the task) </li> <li>The activation task times out</li> <li>The number of specified milliseconds elapses</li> </ul> ");
         currentResult.setValue("role", "operation");
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
