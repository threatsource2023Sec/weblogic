package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.FileChange;
import weblogic.management.mbeanservers.internal.ServiceImplBeanInfo;

public class ConfigurationManagerMBeanImplBeanInfo extends ServiceImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConfigurationManagerMBean.class;

   public ConfigurationManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConfigurationManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.ConfigurationManagerMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "Context");
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String("<p>Manages changes to the configuration of the current WebLogic Server domain. The operations in this MBean start and stop edit sessions, save, undo, and activate configuration changes.</p>  <p>The general process for changing the configuration of a domain is as follows:</p>  <ol> <li> <p>Use this MBean's <code>startEdit()</code> operation to start an edit session.</p>  <p>When you start an edit session, WebLogic Server locks other users from editing the pending configuration MBean hierarchy. If two users start an edit session under the same user identity, changes from both users are collected into a single set of changes.</p>  <p>The operation returns the pending <code>DomainMBean</code>, which is the root of the configuration MBean hierarchy.</p> </li>  <li> <p>Navigate to an MBean and change the value of its attributes or add or remove a child MBean.</p> </li>  <li> <p>Save your changes.</p>  <p>Your saved changes are written to the domain's pending configuration files.</p> </li>  <li> <p>(Optional) Make additional changes or undo the changes.</p> </li>  <li> <p>Use this MBean's <code>activate()</code> operation to activate the saved changes.</p>  <p>When you activate, the changes are propagated to all the servers in the domain and applied to the running configuration.</p> </li> </ol> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.ConfigurationManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ActivationTasks")) {
         getterName = "getActivationTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("ActivationTasks", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("ActivationTasks", currentResult);
         currentResult.setValue("description", "<p>Returns the list of all <code>ActivationTaskMBean</code> instances that have been created. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ActiveActivationTasks")) {
         getterName = "getActiveActivationTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveActivationTasks", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("ActiveActivationTasks", currentResult);
         currentResult.setValue("description", "<p>Contains the <code>ActivationTaskMBeans</code> that provide information about activation tasks that are in progress.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ActivationTaskMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Changes")) {
         getterName = "getChanges";
         setterName = null;
         currentResult = new PropertyDescriptor("Changes", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("Changes", currentResult);
         currentResult.setValue("description", "<p>Contains <code>Change</code> objects for all of the unsaved changes in the current edit session. Each change to an MBean attribute is represented in its own <code>Change</code> object.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompletedActivationTasks")) {
         getterName = "getCompletedActivationTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedActivationTasks", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("CompletedActivationTasks", currentResult);
         currentResult.setValue("description", "<p>Contains all <code>ActivationTaskMBeans</code> that are stored in memory and that describe activation tasks that have completed.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompletedActivationTasksCount")) {
         getterName = "getCompletedActivationTasksCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompletedActivationTasksCount";
         }

         currentResult = new PropertyDescriptor("CompletedActivationTasksCount", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("CompletedActivationTasksCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of <code>ActivationTaskMBeans</code> that WebLogic Server keeps in memory.</p>  <p>Each <code>ActivationTaskMBean</code> contains one <code>Change</code> object for each change that was activated. The MBean and its <code>Change</code> objects describe which user activated the changes, when the changes were activated, and which MBean attributes were modified.</p>  <p>WebLogic Server does not save this data to disk, and therefore it is not available across sessions of the Administration Server.</p>  <p>Because a large collection of <code>ActivationTaskMBean</code> MBeans could potentially use a significant amount of memory, the default number is 10.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentEditor")) {
         getterName = "getCurrentEditor";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentEditor", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("CurrentEditor", currentResult);
         currentResult.setValue("description", "<p>The name of the user who started the current edit session.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentEditorExpirationTime")) {
         getterName = "getCurrentEditorExpirationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentEditorExpirationTime", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("CurrentEditorExpirationTime", currentResult);
         currentResult.setValue("description", "<p>The time at which the current edit session expires as determined by the timeout parameter of the <code>startEdit</code> operation.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentEditorStartTime")) {
         getterName = "getCurrentEditorStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentEditorStartTime", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("CurrentEditorStartTime", currentResult);
         currentResult.setValue("description", "<p>The time at which the current edit session started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("EditSessionName")) {
         getterName = "getEditSessionName";
         setterName = null;
         currentResult = new PropertyDescriptor("EditSessionName", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("EditSessionName", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the edit session.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("FileChanges")) {
         getterName = "getFileChanges";
         setterName = null;
         currentResult = new PropertyDescriptor("FileChanges", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("FileChanges", currentResult);
         currentResult.setValue("description", "<p>Returns a <code>FileChange</code> object for each affected files in the current edit session. Each <code>FileChange</code> object represents a File that was either: edited, added or removed.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for FileChange[]");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>A unique key that WebLogic Server generates to identify the current instance of this MBean type.</p>  <p>For a singleton, such as <code>DomainRuntimeServiceMBean</code>, this key is often just the bean's short class name.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StartEditResolveResult")) {
         getterName = "getStartEditResolveResult";
         setterName = null;
         currentResult = new PropertyDescriptor("StartEditResolveResult", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("StartEditResolveResult", currentResult);
         currentResult.setValue("description", "<p>Method {@code startEdit()} contains optional {@code resolve(stopOnConflict=true)} operation in case when this session is suspicions that it is stale. Method provides result information. Result can be {@code null} if resolve operation was not included.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for AutoResolveResult");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "<p>The MBean type for this instance. This is useful for MBean types that support multiple intances, such as <code>ActivationTaskMBean</code>.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("UnactivatedChanges")) {
         getterName = "getUnactivatedChanges";
         setterName = null;
         currentResult = new PropertyDescriptor("UnactivatedChanges", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("UnactivatedChanges", currentResult);
         currentResult.setValue("description", "<p>Contains <code>Change</code> objects for all changes (saved or unsaved) that have been made since the <code>activate</code> operation completed successfully. This includes any changes that have been saved but not activated in the current and previous edit sessions. </p> <p>Each change to an MBean attribute is described in its own <code>Change</code> object.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Change[]");
      }

      if (!descriptors.containsKey("CurrentEditorExclusive")) {
         getterName = "isCurrentEditorExclusive";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentEditorExclusive", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("CurrentEditorExclusive", currentResult);
         currentResult.setValue("description", "<p>Whether the edit session is exclusive as determined by the exclusive parameter of the <code>startEdit</code> operation.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentEditorExpired")) {
         getterName = "isCurrentEditorExpired";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentEditorExpired", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("CurrentEditorExpired", currentResult);
         currentResult.setValue("description", "<p>Whether the edit session is expired as determined by the timeout parameter of the <code>startEdit</code> operation.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Editor")) {
         getterName = "isEditor";
         setterName = null;
         currentResult = new PropertyDescriptor("Editor", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("Editor", currentResult);
         currentResult.setValue("description", "<p>Returns true if the caller started the current edit session.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MergeNeeded")) {
         getterName = "isMergeNeeded";
         setterName = null;
         currentResult = new PropertyDescriptor("MergeNeeded", ConfigurationManagerMBean.class, getterName, setterName);
         descriptors.put("MergeNeeded", currentResult);
         currentResult.setValue("description", "<p>Returns {@code true} only if another session might have been activated since the last resolve. It means that there could be some difference which must be merged before activation.</p>  <p>Merge does not necessary mean that there are some conflicts.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
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
      Method mth = ConfigurationManagerMBean.class.getMethod("startEdit", Integer.TYPE, Integer.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("waitTimeInMillis", "Prevents other uses from starting an edit session until waitTimeInMillis expires, edits are activated, or the edit session is stopped. If the value of waitTimeInMillis is 0 and an edit session is active, this operation returns immediately. To block indefinitely, specify a value of -1. "), createParameterDescriptor("timeOutInMillis", "Specifies the number of milliseconds after which the lock on the configuration is no longer guaranteed. This time out is enforced lazily. If no other user starts an edit session after the timeout expires, the unsaved changes are left intact and may be saved. If another user starts an edit session after the timeout expires, unsaved changes are automatically reverted and the lock is given to that new user. Specify a value of -1 to indicate that you do not want the edit to time out. In this case, if you do not stop your edit session, only an administrator can stop the edit session by invokeing the cancelEdit operation. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("EditTimedOutException thrown when the start edit request times out because the wait time has elasped and another user still has the edit session.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts an edit session on behalf of the currently authenticated user and prevents other users from editing the configuration for the duration of the session. A user must call this operation before modifying the configuration of the domain.</p>  <p>If two users or processes start an edit session under the same user identity, changes from both users are collected into a single set of changes.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("startEdit", Integer.TYPE, Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("waitTimeInMillis", "Prevents other uses from starting an edit session until waitTimeInMillis expires, edits are activated, or the edit session is stopped. If the value of waitTimeInMillis is 0 and an edit session is active, this operation returns immediately. To block indefinitely, specify a value of -1. "), createParameterDescriptor("timeOutInMillis", "Specifies the number of milliseconds after which the lock on the configuration is no longer guaranteed. This time out is enforced lazily. If no other user starts an edit session after the timeout expires, the unsaved changes are left intact and may be saved. If another user starts an edit session after the timeout expires, unsaved changes are automatically reverted and the lock is given to that new user. Specify a value of -1 to indicate that you do not want the edit to time out. In this case, if you do not stop your edit session, only an administrator can stop the edit session by invokeing the cancelEdit operation. "), createParameterDescriptor("exclusive", "Specifies whether the edit session should be exclusive. An edit session will cause a subsequent call to startEdit by the same owner to wait until the edit session lock is released. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("EditTimedOutException thrown when the start edit request times out because the wait time has elasped and another user still has the edit session.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts an edit session on behalf of the currently authenticated user and prevents other users from editing the configuration for the duration of the session. A user must call this operation before modifying the configuration of the domain.</p>  <p>Prevents multiple users or processes from starting an edit session under the same user identity.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("stopEdit");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if the caller did not start the current edit session.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Stops the current edit session, releases the edit lock, and enables other users to start an edit session. Any unsaved changes are discarded.</p>  <p>This operation can be invoked only by the user who started the edit session.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("cancelEdit");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Cancels the current edit session, releases the edit lock, and enables other users to start an edit session. Any unsaved changes are discarded; saved changes remain pending.</p>  <p>This operation can be called by any user with administrator privileges, even if the user is not the one who started the edit session. Use this operation to cancel an edit session when the current editor can not be contacted to stop an edit session and release the lock. To instead discard all changes, saved and unsaved, see the undoUnactivatedChanges operation.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("validate");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if the caller did not start the current edit session."), BeanInfoHelper.encodeEntities("ValidationException thrown if an validation error occurs while validating changes.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Verifies that all unsaved changes satisfy dependencies between MBean attributes and makes other checks that cannot be made at the time that you set the value of a single attribute.</p>  <p>The <code>save</code> operation also validates changes, but you can use this (<code>validate</code>) operation to check that changes are valid before saving them.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("reload");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if the caller did not start the current edit session."), BeanInfoHelper.encodeEntities("ValidationException thrown if an validation error occurs  while reloading files.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Reloads the configuration files from the pending directory updates the configuration contained in the Edit MBeanServer.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("save");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if the caller did not start the current edit session."), BeanInfoHelper.encodeEntities("ValidationException thrown if an validation error occurs while saving changes.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Validates unsaved changes and saves them to the pending configuration files on disk.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("undo");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if the caller did not start the current edit session.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Undoes all unsaved changes. This reverts the hierarchy of pending configuration MBeans to the last saved state of the pending configuration files, discarding in-memory changes.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("haveUnactivatedChanges");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns true if any changes (saved or unsaved) have been made since the <code>activate</code> operation completed successfully. This includes any changes that have been saved but not activated in the current and previous edit sessions.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("undoUnactivatedChanges");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if the caller did not start the current edit session.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Undoes all of the changes (saved or unsaved) that have been made since the <code>activate</code> operation completed successfully. This includes any changes that have been saved but not activated in the current and previous edit sessions.</p>  <p>This reverts the hierarchy of pending configuration MBeans to the last successful activate state of the domain, discarding any changes made but not activated.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("activate", Long.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "long that contains the time (milliseconds) for the operation to complete. If the elasped time exceeds that value, then the activation of the configuration changes will be canceled. If -1, then the activation will not timeout. If a non-zero timeout is specified, then the activate will wait   until the activate operation has completed or until the timeout period   has elasped. If a zero timeout is specified, then the activate will return   immediately and the caller can wait for completion using the ActivationTaskMBean. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if the caller did not start the current edit session."), BeanInfoHelper.encodeEntities("ActivateConflictException thrown if an activate operation fails due to conflicts.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Activates the changes that have been saved to the pending configuration files.</p>  <p>To activate changes, WebLogic Server copies the pending configuration files to a pending directory within each server instance's root directory. Each server instance determines whether it can consume the changes. If all servers can, then the pending configuration files become the active configuration files and the in-memory hierarchy of active configuration MBeans is updated for each server.</p>  <p>If any server is unable to consume the change, then the activation fails for all servers. All saved changes remain in the pending configuration files and can be activated later.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("purgeCompletedActivationTasks");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Purges from memory all <code>ActivationTaskMBeans</code> that represent completed activation tasks.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConfigurationManagerMBean.class.getMethod("getChangesToDestroyBean", DescriptorBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configurationMBean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Change[]");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Contains <code>Change</code> objects for the changes required to destroy the specified instance of a configuration bean. Each change to an MBean attribute is represented in its own <code>Change</code> object.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Change[]");
      }

      mth = ConfigurationManagerMBean.class.getMethod("removeReferencesToBean", DescriptorBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configurationMBean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if the caller did not start the current edit session.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes references to bean that must be removed in order to destroy the specified instance of a configuration bean.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("getRemoteFileChanges", SystemComponentMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sysComp", "The SystemComponentMBean for the non-admin machine whose configuration should be compared against the admin server. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for FileChange[]");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the list of changed files on the remote non-admin machine. A FileChange element is returned for each file on the non-admin machine that is different, has been deleted, or has been added.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for FileChange[]");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("getRemoteFileContents", SystemComponentMBean.class, FileChange.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sysComp", "The SystemComponentMBean for the non-admin machine from which to retrieve the configuration file. "), createParameterDescriptor("chg", "The FileChange element that specifies the file to retrieve from the System Component machine. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the contents of the specified remote file.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("getFileContents", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("componentType", "The ComponentType from which to retrieve the configuration file. "), createParameterDescriptor("relativePath", "The File element that specifies the relative path of the file to retrieve. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the contents of the specified file on the admin server.<p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("updateConfigurationFromRemoteSystem", SystemComponentMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sysComp", "The SystemComponentMBean for the non-admin machine from which to retrieve the configuration file. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for FileChange[]");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if the caller did not start the          current edit session.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Updates the admin configuration with the contents of the remote system component. This replaces the admin server configuration files with those from the remote system component. Only the changed files are updated and they are placed in the pending directory so an subsequent active is required. An edit session is required for this call as configuration files are updated by this call.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for FileChange[]");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("enableOverwriteComponentChanges");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException"), BeanInfoHelper.encodeEntities("IOException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Force the pending changes to all system components in the next activate</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("resync", SystemComponentMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sysComp", "The SystemComponentMBean for the non-admin machine from which to discard the configuration files. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for ServerStatus[]");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if there's existing edit session")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Discard the configuration on the remote system from the admin configuration. This results in the changed files on the non-admin system being replaced on the next activate, thereby discarding any local changes. An edit session is required as configuration is updated.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for ServerStatus[]");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("resyncAll");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("excludeFromRest", "No default REST mapping for ServerStatus[]");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotEditorException thrown if there's existing edit session")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Resynchronizes the configuration for all of the system components in the domain. This will replace any locally changed configuration files on the remote non-admin machines. This will include pending changes from the current edit session. An edit session is required for this call as remote files are updated.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for ServerStatus[]");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("syncPartitionConfig", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", "name of the partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Pushes partition config directory contents to managed servers </p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("resolve", Boolean.TYPE, Long.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("stopOnConflict", "if {@code true} then resolve will apply only if there is no conflicting change. "), createParameterDescriptor("timeout", "resolve timeout. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Resolve changes with global edit configuration.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("getPropertyValues", ConfigurationMBean.class, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "the bean with properties to be examined "), createParameterDescriptor("propertyNames", "names of properties on the bean to be analyzed ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Describes assignment of value to selected properties of a config bean</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("getPropertyValues", ConfigurationMBean.class, String[].class, SettableBean[].class, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configBean", (String)null), createParameterDescriptor("navigationAttributeNames", (String)null), createParameterDescriptor("beans", (String)null), createParameterDescriptor("propertyNames", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Describes assignment of value to selected properties of a bean within a ConfigurationMBean's object graph. </p> <p>The specified beans form a path from the ConfigurationMBean to some other object of interest, following each method specified in the getMethodNames array. Starting with the ConfigurationMBean, the system retrieves the next attribute specified in the navigationAttributeNames array, expecting that attribute to be of a type assignable from the next bean in the Object[] array. This continues with the next navigationAttributeName applied to the current object until the end of the lists. Both arrays must be of the same length. At any point, if the attribute specified by the navigationAttributeName array element actually returns an array, then the base type of the array must expose the getName() method which returns a String so the system can select the correct child from the returned array to continue the navigation.</p> <p>The propertyNames apply to the last object specified in the beans array.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("getEffectiveValues", ConfigurationMBean.class, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configBean", (String)null), createParameterDescriptor("propertyNames", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Reports the effective values for specified properties on the indicated config bean, where effective values include any overrides that apply. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("getEffectiveValues", ConfigurationMBean.class, String[].class, SettableBean[].class, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configBean", (String)null), createParameterDescriptor("navigationAttributeNames", (String)null), createParameterDescriptor("beans", (String)null), createParameterDescriptor("propertyNames", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Reports the effective values for specified properties on the last of the beans specified, navigated to by following the navigation attribute names starting at the config bean. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("getWorkingValues", ConfigurationMBean.class, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configBean", (String)null), createParameterDescriptor("propertyNames", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Reports the working values for specified properties on the indicated config bean. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ConfigurationManagerMBean.class.getMethod("releaseEditAccess");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("excludeFromRest", "No default REST mapping for ServerStatus[]");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Explicitly releases the reference to {@code EditAccess} in order to make it eligible for garbage collection.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for ServerStatus[]");
         }
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
