package weblogic.server;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;

public class ResourceGroupLifeCycleRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ResourceGroupLifeCycleRuntimeMBean.class;

   public ResourceGroupLifeCycleRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ResourceGroupLifeCycleRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.server.ResourceGroupLifeCycleRuntime");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.server");
      String description = (new String("<p>Provides methods that transition resource groups from one state to another. This class is instantiated once for each resource group but only on the Administration Server. Even so, you can use a resource group's instance to change the state of the resource group on Managed Servers as well as on the Administration Server.</p>  <p id=\"useTaskObject\"><b>Detecting when Operations Complete</b></p> <p>Methods representing operations on resource groups return a {@link ResourceGroupLifeCycleTaskRuntimeMBean} object. The caller should monitor the returned instance, invoking its {@link ResourceGroupLifeCycleTaskRuntimeMBean#isRunning()} method to detect when the operation has completed. In particular, the caller should <b>not</b> rely on the value returned from {@link #getState()} to indicate when an operation on a resource group that is targeted to multiple servers has finished. That value will be accurate as described in detail on {@link #getState()} but can change before the operation has finished.</p>  <p id=\"desired\"><b>Desired States</b></p> <p>The system tracks the <b>desired</b> state for each resource group, which defaults to the state <code>RUNNING</code>. Whenever a lifecycle operation method is called the system not only performs the requested action on the resource group but also records the resulting state as the new desired state for the resource group.</p> <p>The system uses the desired state for a resource group to bring the resource group to the correct state during server and partition start-up. The desired can also be used when the {@link #getState()} method computes the overall state for the resource group.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", ResourceGroupLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The current overall state of the resource group.</p> <p>The value returned is the highest state for the resource group on all servers where it is targeted. As a result, the overall resource group state can change (for example, to <code>RUNNING</code>) as soon as the operation (such as start) has finished on one server, even while the operation is still in progress elsewhere.</p> <p>If the resource group is not currently targeted to any running server then its reported state is the <a href=\"#desired\">desired state</a> indicated by the most recent resource group life cycle operation on the resource group.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Tasks")) {
         getterName = "getTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("Tasks", ResourceGroupLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Tasks", currentResult);
         currentResult.setValue("description", "The preexisting resource group lifecycle tasks ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("lookupTask", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("taskName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The preexisting partition lifecycle task.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Tasks");
         currentResult.setValue("excludeFromRest", "");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("start");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to start this resource group on all targeted servers, changing its state to <code>RUNNING</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("getInternalState");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The internal state enum value. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("start", TargetMBean[].class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to start this resource group ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.ResourceGroupLifecycleException if the start fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to start this resource group on the specified targets, changing its state to <code>RUNNING</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.start(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("start", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to start this resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.ResourceGroupLifecycleException if the start fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to start this resource group on the specified targets, changing its state to <code>RUNNING</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("startInAdmin", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to start this resource group in \"admin\" state ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException if the start fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to start this resource group in the admin state on the specified targets, changing its state to <code>ADMIN</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.startInAdmin(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("startInAdmin", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to start this resource group in \"admin\" state ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException if the start fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to start this resource group in the admin state on the specified targets, changing its state to <code>ADMIN</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("startInAdmin");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException if the start fails")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to start this resource group in the admin state on all targeted servers, changing its state to <code>ADMIN</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and force shutting down the server. "), createParameterDescriptor("ignoreSessions", "Set to <code>true</code> to ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "Set to <code>true</code> to wait for all HTTP sessions duirng inflight work handling; <code>false</code> to wait for non-persisted HTTP sessions only. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException If the resource group fails to gracefully                                      shutdown. A {@link #forceShutdown()} operation can be invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins to shut down this resource group gracefully on all targeted servers, changing its state to <code>SHUTDOWN</code>. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, Boolean.TYPE, TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and force shutting down the server. "), createParameterDescriptor("ignoreSessions", "Set to <code>true</code> to ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "Set to <code>true</code> to wait for all HTTP sessions duirng inflight work handling; <code>false</code> to wait for non-persisted HTTP sessions only. "), createParameterDescriptor("targets", "the specific targets on which to shut down this resource group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException If the resource group fails to gracefully                                      shutdown. A {@link #forceShutdown()} operation can be invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins to shut down this resource group gracefully on the specified targets, changing its state to <code>SHUTDOWN</code>. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions, TargetMBean[] targets)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, Boolean.TYPE, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and force shutting down the server. "), createParameterDescriptor("ignoreSessions", "Set to <code>true</code> to ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "Set to <code>true</code> to wait for all HTTP sessions duirng inflight work handling; <code>false</code> to wait for non-persisted HTTP sessions only. "), createParameterDescriptor("targetNames", "the specific servers on which to shut down this resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException If the resource group fails to gracefully                                      shutdown. A {@link #forceShutdown()} operation can be invoked.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begins to shut down this resource group gracefully on the specified targets, changing its state to <code>SHUTDOWN</code>. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the server. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("targets", "the specific targets on which to shut down this resource group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException Thrown if the resource group cannot                                      gracefully shutdown. Use the forceShutdown command to shutdown the resource group.")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to shut down this resource group gracefully on the specified targets, changing its state to <code>SHUTDOWN</code>. (equivalent to shutdown(timeout, ignoreSessions, false, targets)).</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.shutdown(int timeout, boolean ignoreSessions, String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the server. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("targetNames", "the specific servers on which to shut down this resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException Thrown if the resource group cannot                                      gracefully shutdown. Use the forceShutdown command to shutdown the resource group.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to shut down this resource group gracefully on the specified targets, changing its state to <code>SHUTDOWN</code>. (equivalent to shutdown(timeout, ignoreSessions, false, targets)).</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the server. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException Thrown if the resource group cannot                                      gracefully shutdown. Use the forceShutdown command to shutdown the resource group.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to shut down this resource group gracefully on all targeted servers, changing its state to <code>SHUTDOWN</code>. (equivalent to shutdown(timeout, ignoreSessions, false)).</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("shutdown", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to start this resource group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException if the shutdown fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to shut down this resource group gracefully on the specified targets, changing its state to <code>SHUTDOWN</code>. (equivalent to shutdown(targets, 0, false)).</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.shutdown(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("shutdown", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to shut down this resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException if the shutdown fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to shut down this resource group gracefully on the specified targets, changing its state to <code>SHUTDOWN</code>. (equivalent to shutdown(targets, 0, false)).</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("shutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException if the shutdown fails")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to shut down the resource group gracefully on all targeted servers, changing its state to <code>SHUTDOWN</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("forceShutdown", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to force a shutdown on this resource group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException if the resource group fails to shut down")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to force the shutdown of this resource group on the specified targets, changing its state to <code>SHUTDOWN</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.forceShutdown(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("forceShutdown", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to force a shutdown on this resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException if the resource group fails to shut down")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to force the shutdown of this resource group on the specified targets, changing its state to <code>SHUTDOWN</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("forceShutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException if the resource group fails to shut down")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to force the shutdown of this resource group on all targeted servers, changing its state to <code>SHUTDOWN</code>.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Boolean.TYPE, TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Seconds to wait for the resource group to transition gracefully. The resource group automatically calls {@link #forceSuspend()} after timeout. "), createParameterDescriptor("ignoreSessions", "drop inflight HTTP sessions during graceful suspend "), createParameterDescriptor("targets", "the specific targets on which to start this resource group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException resource group failed to suspend gracefully.                                      A {@link #forceSuspend()} or a {@link #forceShutdown()} operation can be                                      invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins to transition this resource group from <code>RUNNING</code> to <code>ADMIN</code> state gracefully on the specified targets. <p/> <p>Applications are in admin mode. Inflight work is completed.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.suspend(timeout, boolean ignoreSessions, String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Boolean.TYPE, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Seconds to wait for the resource group to transition gracefully. The resource group automatically calls {@link #forceSuspend()} after timeout. "), createParameterDescriptor("ignoreSessions", "drop inflight HTTP sessions during graceful suspend "), createParameterDescriptor("targetNames", "the specific servers on which to suspend this resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException resource group failed to suspend gracefully.                                      A {@link #forceSuspend()} or a {@link #forceShutdown()} operation can be                                      invoked.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begins to transition this resource group from <code>RUNNING</code> to <code>ADMIN</code> state gracefully on the specified targets. <p/> <p>Applications are in admin mode. Inflight work is completed.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Seconds to wait for the resource group to transition gracefully. The resource group automatically calls {@link #forceSuspend()} after timeout. "), createParameterDescriptor("ignoreSessions", "drop inflight HTTP sessions during graceful suspend ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException resource group failed to suspend gracefully.                                      A {@link #forceSuspend()} or a {@link #forceShutdown()} operation can be                                      invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins to transition the resource group from <code>RUNNING</code> to <code>ADMIN</code> state gracefully on all targeted servers. <p/> <p>Applications are in admin mode. Inflight work is completed.</p> <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("suspend", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to start this resource group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] seeObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException If the operation fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins the graceful transition of this resource group from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets. (equivalent to suspend(0, false, targets)) New requests are rejected and inflight work is allowed to complete. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.suspend(String[] targetNames)</code> </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspend(int, boolean)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("suspend", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to suspend this resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException If the operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begins the graceful transition of this resource group from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets. (equivalent to suspend(0, false, targets)) New requests are rejected and inflight work is allowed to complete. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspend(int, boolean)")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("suspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException If the operation fails")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins the graceful transition of this resource group from the <code>RUNNING</code> state to the <code>ADMIN</code> state on all targeted servers. (equivalent to suspend(0, false)) New requests are rejected and inflight work is allowed to complete. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspend(int, boolean)")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("forceSuspend", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to start this resource group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException the resource group could not suspend.")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins the forceful transition of this resource group from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.forceSuspend(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("forceSuspend", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to force a suspend of this resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException the resource group could not suspend.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begins the forceful transition of this resource group from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("forceSuspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException the resource group could not suspend.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins the forceful transition of this resource group from the <code>RUNNING</code> state to the <code>ADMIN</code> state on all targeted servers. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("resume", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to start this resource group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException If the resume operation fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins the transition of this resource group from the <code>ADMIN</code> state to the <code>RUNNING</code> state on the specified targets. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.resume(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("resume", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to resume this resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException If the resume operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begins the transition of this resource group from the <code>ADMIN</code> state to the <code>RUNNING</code> state on the specified targets. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("resume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException If the resume operation fails")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins the transition of this resource group from the <code>ADMIN</code> state to the <code>RUNNING</code> state on all targeted servers. <p>Use the returned {@link ResourceGroupLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("purgeTasks");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Clears the retained task collection. ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("getState", ServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverMBean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The state of resource group on a specific server. <p>Please use <code>ResourceGroupLifeCycleTaskRuntimeMBean.getState(String serverName)</code> </p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = ResourceGroupLifeCycleRuntimeMBean.class.getMethod("getState", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", "name of the server to check ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "The current state of the resource group on a specific server. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
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
