package weblogic.management.deploy.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.DeploymentProgressObjectMBean;

public class DeploymentProgressObjectImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeploymentProgressObjectMBean.class;

   public DeploymentProgressObjectImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeploymentProgressObjectImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.deploy.internal.DeploymentProgressObjectImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.deploy.internal");
      String description = (new String("<p>This MBean is the user API for monitoring deployment operations and exists only on an Administration Server. Currently only start and stop operations initiated by {@link AppDeploymentRuntimeMBean} are supported.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.DeploymentProgressObjectMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AppDeploymentMBean")) {
         getterName = "getAppDeploymentMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("AppDeploymentMBean", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("AppDeploymentMBean", currentResult);
         currentResult.setValue("description", "<p>The AppDeploymentMBean for the current deployment operation.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restRelationship", "reference");
         currentResult.setValue("excludeFromRest", "work around bug 20088067");
      }

      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationName", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "<p>The name of the application for the current deployment operation.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeginTime")) {
         getterName = "getBeginTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BeginTime", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("BeginTime", currentResult);
         currentResult.setValue("description", "<p>The time that the current deployment operation began. The value is in milliseconds consistent with the system time.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restName", "startTimeAsLong");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DeploymentTaskRuntime")) {
         getterName = "getDeploymentTaskRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentTaskRuntime", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentTaskRuntime", currentResult);
         currentResult.setValue("description", "<p>The task associated with the deployment operation</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("EndTime")) {
         getterName = "getEndTime";
         setterName = null;
         currentResult = new PropertyDescriptor("EndTime", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("EndTime", currentResult);
         currentResult.setValue("description", "<p>The time that the current deployment operation ended. The value is in milliseconds consistent with the system time. If the operation has not ended, the value will be zero.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restName", "endTimeAsLong");
      }

      if (!descriptors.containsKey("FailedTargets")) {
         getterName = "getFailedTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedTargets", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("FailedTargets", currentResult);
         currentResult.setValue("description", "<p>The targets on which the current deployment operation failed.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         currentResult = new PropertyDescriptor("Id", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "<p>The unique ID for the current deployment operation.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryMBean")) {
         getterName = "getLibraryMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryMBean", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("LibraryMBean", currentResult);
         currentResult.setValue("description", "<p>Return the LibraryMBean for the current deployment operation.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restRelationship", "reference");
         currentResult.setValue("excludeFromRest", "work around bug 20088067");
      }

      if (!descriptors.containsKey("Messages")) {
         getterName = "getMessages";
         setterName = null;
         currentResult = new PropertyDescriptor("Messages", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("Messages", currentResult);
         currentResult.setValue("description", "<p>Provides an ordered array of status messages generated for the current deployment operation.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restName", "deploymentMessages");
      }

      if (!descriptors.containsKey("OperationType")) {
         getterName = "getOperationType";
         setterName = null;
         currentResult = new PropertyDescriptor("OperationType", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("OperationType", currentResult);
         currentResult.setValue("description", "<p>The deployment operation type for the current deployment operation. Possible values are 1 (start) and 2 (stop).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RootExceptions")) {
         getterName = "getRootExceptions";
         setterName = null;
         currentResult = new PropertyDescriptor("RootExceptions", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("RootExceptions", currentResult);
         currentResult.setValue("description", "<p>If the current deployment operation has failed, this method may return zero or more exception(s) which represent the root cause of the failure. The array will not contain WLS exception classes; instead they will be new Exceptions containing the stack traces and messages from the original WLS Exceptions.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The state of the current deployment operation. Possible values are STATE_INITIALIZED, STATE_RUNNING, STATE_COMPLETED, STATE_FAILED and STATE_DEFERRED.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("Targets", DeploymentProgressObjectMBean.class, getterName, (String)setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>The targets specified for the current deployment operation.</p> ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DeploymentProgressObjectMBean.class.getMethod("addMessages", List.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for List");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>add messages for current deployment operation.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Messages");
         currentResult.setValue("excludeFromRest", "No default REST mapping for List");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DeploymentProgressObjectMBean.class.getMethod("getExceptions", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "the target where exceptions might have occurred. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the current deployment operation has failed, this method may return zero or more exception(s) which represent the errors for this target. The array will not contain WLS exception classes; instead they will be new RuntimeExceptions containing the stack traces and messages from the original WLS Exceptions.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentProgressObjectMBean.class.getMethod("cancel");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Attempt to cancel the deployment operation. Any actions which have yet to start will be inhibited. Any completed actions will remain in place.</p> ");
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
