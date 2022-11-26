package weblogic.server;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SystemComponentLifeCycleRuntimeMBean;

public class SystemComponentLifeCycleRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SystemComponentLifeCycleRuntimeMBean.class;

   public SystemComponentLifeCycleRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SystemComponentLifeCycleRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.server.SystemComponentLifeCycleRuntime");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.2.0");
      beanDescriptor.setValue("package", "weblogic.server");
      String description = (new String("<p>Provides methods that transition system component from one state to another. This class is instantiated only on the Administration Server, but you can use it to transition the states of all managed system component servers.</p>  <p>To start system components, you must first configure a Node Manager on each component's host machine.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SystemComponentLifeCycleRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("NodeManagerRestartCount")) {
         getterName = "getNodeManagerRestartCount";
         setterName = null;
         currentResult = new PropertyDescriptor("NodeManagerRestartCount", SystemComponentLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NodeManagerRestartCount", currentResult);
         currentResult.setValue("description", "The number of times the server has been restarted using the Node Manager since its creation. The first start does not count. The count is valid only if the Node Manager is used to start and restart the server every time. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", SystemComponentLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The current state of the server.</p> Server states are described in \"Managing Server Startup and Shutdown for Oracle WebLogic Server.\" ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StateVal")) {
         getterName = "getStateVal";
         setterName = null;
         currentResult = new PropertyDescriptor("StateVal", SystemComponentLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StateVal", currentResult);
         currentResult.setValue("description", "<p>An integer that identifies the current state of the server. Values range from <code>0</code> to <code>8</code>.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Tasks")) {
         getterName = "getTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("Tasks", SystemComponentLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Tasks", currentResult);
         currentResult.setValue("description", "Gets pre-existing server life cycle tasks. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         Method mth = SystemComponentLifeCycleRuntimeMBean.class.getMethod("lookupTask", String.class);
         ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("taskName", "a string used to find a task of the same name ")};
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Gets a pre-existing server life cycle task by name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "Tasks");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SystemComponentLifeCycleRuntimeMBean.class.getMethod("start", Properties.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("props", "properties to use when starting this system component. These properties are used by the nodemanager plugin. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Uses Node Manager to start a system component.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = SystemComponentLifeCycleRuntimeMBean.class.getMethod("shutdown", Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("props", "properties to use when shutting down this system component. These properties are used by the nodemanager plugin. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Uses Node Manager to shut down a system component.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = SystemComponentLifeCycleRuntimeMBean.class.getMethod("clearOldServerLifeCycleTaskRuntimes");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes all SystemComponentLifeCycleTaskRuntimeMBeans that have completed and been around for over 30 minutes. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = SystemComponentLifeCycleRuntimeMBean.class.getMethod("softRestart", Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("props", "properties to use when soft-restarting this system component. These properties are used by the nodemanager plugin. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Uses Node Manager to signal a system component for softRestart.</p> ");
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
