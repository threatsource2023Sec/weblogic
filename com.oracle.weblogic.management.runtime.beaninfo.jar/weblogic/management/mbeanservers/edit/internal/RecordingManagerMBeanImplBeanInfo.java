package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.edit.RecordingManagerMBean;
import weblogic.management.mbeanservers.internal.ServiceImplBeanInfo;

public class RecordingManagerMBeanImplBeanInfo extends ServiceImplBeanInfo {
   public static final Class INTERFACE_CLASS = RecordingManagerMBean.class;

   public RecordingManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RecordingManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.RecordingManagerMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "Context");
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String("<p>This MBean records configuration actions that occur during an edit session and writes the actions as series of WebLogic Scripting Tool (WLST) commands. You can then use WLST to replay the commands.</p>  <p>WLST is a command-line scripting environment that you can use to create, manage, and monitor WebLogic Server domains. It is installed on your system when you install WebLogic Server. </p> <p> This MBean does <b>not</b> record WLST commands for the following: </p> <ul> <li> Changes to the security data that is maintained by a security provider. For example, you cannot record the commands to add or remove users, roles, and policies. </li> <li>Changes to deployment plans.</li> <li> Runtime operations found on Control or Monitoring pages, such as starting and stopping applications or servers. </li> </ul> <p> You cannot remove or undo a command once it has been recorded. Instead, you can edit the script file after you stop recording. </p> <p>If you record commands that get or set the values of encrypted attributes (such the password for a server's Java Standard Trust keystore), this MBean creates two files in addition to the script file: a user configuration file that contains the encrypted data and a key file that contains the key used to encrypt the data. Use the file system to limit read and write access to the key file. Users who can read the key file can read all of the encrypted data that you recorded. </p>  <p>The key file and user configuration files are created in the same directory as the recorded script file and are named <code><i>recording-file</i>Config</code> and <code><i>recording-file</i>Secret</code> where <code><i>recording-file</i></code> is the name of the recorded script file.</p> <p>When you use WLST to replay the commands, the user configuration and key files must be in the same directory as the script file. If you move the script file, you must also move the user configuration and key files. Only the key file that was used to encrypt the data can be used to unencrypt the data.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.RecordingManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", RecordingManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>A unique key that WebLogic Server generates to identify the current instance of this MBean type.</p>  <p>For a singleton, such as <code>DomainRuntimeServiceMBean</code>, this key is often just the bean's short class name.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", RecordingManagerMBean.class, getterName, (String)setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", RecordingManagerMBean.class, getterName, (String)setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", RecordingManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecordingFileName")) {
         getterName = "getRecordingFileName";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordingFileName", RecordingManagerMBean.class, getterName, (String)setterName);
         descriptors.put("RecordingFileName", currentResult);
         currentResult.setValue("description", "<p>Returns the full path of the recording file. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", RecordingManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "<p>The MBean type for this instance. This is useful for MBean types that support multiple intances, such as <code>ActivationTaskMBean</code>.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Recording")) {
         getterName = "isRecording";
         setterName = null;
         currentResult = new PropertyDescriptor("Recording", RecordingManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Recording", currentResult);
         currentResult.setValue("description", "<p>Indicates whether a recording session is currently in progress.</p> ");
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
      Method mth = RecordingManagerMBean.class.getMethod("startRecording", String.class, Boolean.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("fileName", "Absolute path and file name for the file in which to write WLST commands. "), createParameterDescriptor("append", "If set to true, this method writes WLST commands at the end of the recording file instead of the beginning. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RecordingException If a recording session is already started or            the specified file cannot be opened.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts a recording session. The recorded actions will be saved as WLST commands in the specified file. Actions are recorded and written as you invoke them.</p> <p>If the specified file already exists, this method adds the WLST commands to the beginning or end of the file, depending on which value you pass in the <code>append</code> argument. This method does not overwrite an existing file.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = RecordingManagerMBean.class.getMethod("startRecording", String.class, Map.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("fileName", "recording filename "), createParameterDescriptor("options", "contains flags to control recording behavior. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RecordingException if a recording session is already started or the specified file           cannot be opened for some reason")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts a recording session. The generated WLST scripts will be saved to the specified file.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = RecordingManagerMBean.class.getMethod("stopRecording");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RecordingException If there is no active recording session")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Stops a recording session. </p> ");
         currentResult.setValue("role", "operation");
      }

      mth = RecordingManagerMBean.class.getMethod("record", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("str", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RecordingException if there is no active recording session")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Writes a string to the script file.</p> <p>If you invoke this method while a recording session is in progress, the method writes the string immediately after the WLST command that it has most recently recorded.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = RecordingManagerMBean.class.getMethod("releaseEditAccess");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Explicitly releases the reference to {@code EditAccess} in order to make it eligible for garbage collection.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
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
