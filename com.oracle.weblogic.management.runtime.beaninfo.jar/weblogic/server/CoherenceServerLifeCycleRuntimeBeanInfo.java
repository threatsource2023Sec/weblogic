package weblogic.server;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.CoherenceServerLifeCycleRuntimeMBean;

public class CoherenceServerLifeCycleRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceServerLifeCycleRuntimeMBean.class;

   public CoherenceServerLifeCycleRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceServerLifeCycleRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.server.CoherenceServerLifeCycleRuntime");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "12.2.1.0.0 Standalone Coherence Cache Server control using Nodemanager feature has been deprecated and replaced with Managed Coherence Servers introduced in 12.1.2 ");
      beanDescriptor.setValue("package", "weblogic.server");
      String description = (new String("<p>Provides methods that transition servers from one state to another. This class is instantiated only on the Administration Server, but you can use it to transition the states of all managed Coherence cache servers.</p>  <p>To start Coherence cache servers, you must first configure a Node Manager on each Coherence cache server's host machine.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.CoherenceServerLifeCycleRuntimeMBean");
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
         currentResult = new PropertyDescriptor("NodeManagerRestartCount", CoherenceServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NodeManagerRestartCount", currentResult);
         currentResult.setValue("description", "The number of times the server has been restarted using the Node Manager since its creation. The first start does not count. The count is valid only if the Node Manager is used to start and restart the server every time. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", CoherenceServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The current state of the server.</p> Server states are described in \"Managing Server Startup and Shutdown for Oracle WebLogic Server.\" ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StateVal")) {
         getterName = "getStateVal";
         setterName = null;
         currentResult = new PropertyDescriptor("StateVal", CoherenceServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StateVal", currentResult);
         currentResult.setValue("description", "<p>An integer that identifies the current state of the server. Values range from <code>0</code> to <code>8</code>.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Tasks")) {
         getterName = "getTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("Tasks", CoherenceServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
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
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceServerLifeCycleRuntimeMBean.class.getMethod("start");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Uses Node Manager to start a Coherence cache server.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = CoherenceServerLifeCycleRuntimeMBean.class.getMethod("forceShutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Immediately transitions a server to the <code>SHUTDOWN</code> state. The server immediately terminates all current work, moves through the <code>SHUTTING_DOWN</code> state, and ends in the <code>SHUTDOWN</code> state.</p>  <p>You can forcefully shut down a server from any state except <code>UNKNOWN</code>.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = CoherenceServerLifeCycleRuntimeMBean.class.getMethod("clearOldServerLifeCycleTaskRuntimes");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes all CoherenceServerLifeCycleTaskRuntimeMBeans that have completed and been around for over 30 minutes. ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
