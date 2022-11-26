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
import weblogic.management.runtime.PartitionLifeCycleRuntimeMBean;

public class PartitionLifeCycleRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PartitionLifeCycleRuntimeMBean.class;

   public PartitionLifeCycleRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionLifeCycleRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.server.PartitionLifeCycleRuntime");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.server");
      String description = (new String("<p>Provides methods that transition partitions from one state to another. This class is instantiated once for each partition but only on the Administration Server. Even so, you can use a partition's instance to change the state of the partition on Managed Servers as well as on the Administration Server.</p>  <p id=\"useTaskObject\"><b>Detecting when Operations Complete</b></p>  <p>Methods representing operations on partitions return a {@link PartitionLifeCycleTaskRuntimeMBean} object. The caller should monitor the returned instance, invoking its {@link PartitionLifeCycleTaskRuntimeMBean#isRunning()} method to detect when the operation has completed. In particular, the caller should <b>not</b> rely on the value returned from {@link #getState()} to indicate when an operation on a partition that is targeted to multiple servers has finished. That value will be accurate as described in detail on {@link #getState()} but can change before the operation has finished.</p>  <p id=\"desired\"><b>Desired States</b></p> <p>The system tracks the <b>desired</b> state for each partition, which defaults to the state <code>SHUTDOWN</code> and substate <code>HALTED</code>. Whenever a lifecycle operation method is called the system not only performs the requested action on the partition but also records the resulting state as the new desired state for the partition.</p> <p>The system uses the desired state for a partition to bring the partition to the correct state during server restart. It can also be used when the {@link #getState()} method computes the overall state for the partition.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PartitionLifeCycleRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ResourceGroupLifeCycleRuntimes")) {
         getterName = "getResourceGroupLifeCycleRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceGroupLifeCycleRuntimes", PartitionLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceGroupLifeCycleRuntimes", currentResult);
         currentResult.setValue("description", "<p>The resource group lifecycle tasks for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", PartitionLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The current overall state of the partition.</p> <p>The value returned is the highest state for the partition on all servers where it is targeted. As a result, the overall partition state can change (for example, to <code>RUNNING</code>) as soon as the operation (such as start) has finished on one server, even while the operation is still in progress elsewhere.</p> <p>If the partition is not currently targeted to any running server then its reported state is the <a href=\"#desired\">desired state</a> indicated by the most recent partition life cycle operation on the partition.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SubState")) {
         getterName = "getSubState";
         setterName = null;
         currentResult = new PropertyDescriptor("SubState", PartitionLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubState", currentResult);
         currentResult.setValue("description", "<p>The current substate of the partition. It is mostly useful for knowing whether the partition is completely shutdown and the administrative resource groups are not running, in which case this method returns <code>HALTED</code>, or if the partition is in the <code>SHUTDOWN.BOOTED</code> state and the administrative resource groups are running, in which case this method returns <code>BOOTED</code>.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("Tasks")) {
         getterName = "getTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("Tasks", PartitionLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Tasks", currentResult);
         currentResult.setValue("description", "<p>The preexisting partition lifecycle tasks.</p> ");
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
      Method mth = PartitionLifeCycleRuntimeMBean.class.getMethod("lookupTask", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("taskName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The preexisting partition lifecycle task.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Tasks");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("lookupResourceGroupLifeCycleRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "resource lifecycle name ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Look up the named resource group lifecycle task for this partition.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ResourceGroupLifeCycleRuntimes");
         currentResult.setValue("excludeFromRest", "");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PartitionLifeCycleRuntimeMBean.class.getMethod("start");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If start operation fails")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to start this partition on all targeted servers.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("start", TargetMBean[].class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to start this partition ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the start fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to start this partition on the specified targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.start(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("start", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to start this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the start fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to start this partition on the specified servers.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("startInAdmin", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to start this partition in \"admin\" state ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the start fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to start this partition in the <code>ADMIN</code> state on the specified targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.startInAdmin(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("startInAdmin", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to start this partition in \"admin\" state ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the start fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to start this partition in the <code>ADMIN</code> state on the specified targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("startInAdmin");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the start fails")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to start this partition in the <code>ADMIN</code> state on all targeted servers.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("start", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("initialState", "can be can be <code>ADMIN</code> or <code>RUNNING</code>, default is <code>RUNNING</code>. "), createParameterDescriptor("timeOut", "Specifies the number of milliseconds to start a Partition. Throws InterruptedException if Partition is unable to start during that duration and leaves the Partition in <code>UNKNOWN</code> state. Default is 60000ms ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("InterruptedException"), BeanInfoHelper.encodeEntities("PartitionLifeCycleException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Begins to start this partition in the specified <code>initialState</code> within the specified <code>timeOut</code> on all targeted servers.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and force shutting down the server. "), createParameterDescriptor("ignoreSessions", "Set to <code>true</code> to ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "Set to <code>true</code> to wait for all HTTP sessions duirng inflight work handling; <code>false</code> to wait for non-persisted HTTP sessions only. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the partition fails to gracefully                                      shutdown. A {@link #forceShutdown()} operation can be invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begins to shut down this partition gracefully on all targeted servers. <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, Boolean.TYPE, TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and force shutting down the server. "), createParameterDescriptor("ignoreSessions", "Set to <code>true</code> to ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "Set to <code>true</code> to wait for all HTTP sessions duirng inflight work handling; <code>false</code> to wait for non-persisted HTTP sessions only. "), createParameterDescriptor("targets", "the specific targets on which to shut down this partition ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the partition fails to gracefully                                      shutdown. A {@link #forceShutdown()} operation can be invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to shut down this partition gracefully on the specified targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions, String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, Boolean.TYPE, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and force shutting down the server. "), createParameterDescriptor("ignoreSessions", "Set to <code>true</code> to ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "Set to <code>true</code> to wait for all HTTP sessions duirng inflight work handling; <code>false</code> to wait for non-persisted HTTP sessions only. "), createParameterDescriptor("targetNames", "the specific servers on which to shut down this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the partition fails to gracefully                                      shutdown. A {@link #forceShutdown()} operation can be invoked.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to shut down this partition gracefully on the specified targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the server. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException Thrown if the partition cannot                                      gracefully shutdown. Use the forceShutdown command to shutdown the partition.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to shut down this partition gracefully on all targeted servers.</p> <p>(Equivalent to shutdown(timeout, ignoreSessions, false, targets)).</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the server. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("targets", "the specific targets on which to shut down this partition ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException Thrown if the partition cannot                                      gracefully shutdown. Use the forceShutdown command to shutdown the partition.")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to shut down this partition gracefully on the specified targets.</p> <p>(Equivalent to shutdown(timeout, ignoreSessions, false, targets)).</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.shutdown(int timeout, boolean ignoreSessions, String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the server. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("targetNames", "the specific servers on which to shut down this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException Thrown if the partition cannot                                      gracefully shutdown. Use the forceShutdown command to shutdown the partition.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to shut down this partition gracefully on the specified targets.</p> <p>(Equivalent to shutdown(timeout, ignoreSessions, false, targets)).</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("shutdown", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to shut down this partition ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the shutdown fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to shut down this partition gracefully on the specified targets.</p> <p> (Equivalent to shutdown(targets, 0, false)).</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.shutdown(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("shutdown", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to shut down this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the shutdown fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to shut down this partition gracefully on the specified targets.</p> <p> (Equivalent to shutdown(targets, 0, false)).</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("shutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the shutdown fails")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to shut down this partition gracefully on all targeted servers.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("forceShutdown", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to shut down this partition ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the partition fails to shut down")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to force the shut down of this partition on the specified targets.</p> <p>On each target the partition is shut down and then restored to its current state on that server.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.forceShutdown(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("forceShutdown", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to shut down this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the partition fails to shut down")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to force the shut down of this partition on the specified targets.</p> <p>On each target the partition is shut down and then restored to its current state on that server.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("forceShutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException if the partition fails to shut down")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to force the shutdown of this partition on all targeted servers.</p> <p>On each targeted server the partition is shut down and thenrestored to its current state on that server.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("forceShutdown", Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException"), BeanInfoHelper.encodeEntities("InterruptedException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins to force the shutdown of this partition with the specified timeout on all targeted servers.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Boolean.TYPE, TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Seconds to wait for the partition to transition gracefully. The partition automatically calls {@link #forceSuspend()} after timeout. "), createParameterDescriptor("ignoreSessions", "drop inflight HTTP sessions during graceful suspend "), createParameterDescriptor("targets", "the specific targets on which to suspend this partition ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException partition failed to suspend gracefully.                                      A {@link #forceSuspend()} or a {@link #forceShutdown()} operation can be                                      invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins the graceful transition of the partition from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets. </p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Applications are in admin mode. Inflight work is completed.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.suspend(int timeout, boolean ignoreSessions, String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Boolean.TYPE, String[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Seconds to wait for the partition to transition gracefully. The partition automatically calls {@link #forceSuspend()} after timeout. "), createParameterDescriptor("ignoreSessions", "drop inflight HTTP sessions during graceful suspend "), createParameterDescriptor("targetNames", "the specific servers on which to suspend this partition ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException partition failed to suspend gracefully.                                      A {@link #forceSuspend()} or a {@link #forceShutdown()} operation can be                                      invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins the graceful transition of the partition from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets. </p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Applications are in admin mode. Inflight work is completed.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Seconds to wait for the partition to transition gracefully. The partition automatically calls {@link #forceSuspend()} after timeout. "), createParameterDescriptor("ignoreSessions", "drop inflight HTTP sessions during graceful suspend ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException partition failed to suspend gracefully.                                      A {@link #forceSuspend()} or a {@link #forceShutdown()} operation can be                                      invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins the graceful transition of the partition from the <code>RUNNING</code> state to the <code>ADMIN</code> state on all targeted servers. </p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Applications are in admin mode. Inflight work is completed.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("suspend", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to suspend this partition ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] seeObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the operation fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins the graceful transition of the partition from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets. (Equivalent to suspend(0, false, targets))</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>New requests are rejected and inflight work is allowed to complete.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.suspend(String[] targetNames)</code> </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspend(int, boolean)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("suspend", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to suspend this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins the graceful transition of the partition from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets. (Equivalent to suspend(0, false, targets))</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> New requests are rejected and inflight work is allowed to complete. ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspend(int, boolean)")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("suspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the operation fails")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins the graceful transition of the partition from the <code>RUNNING</code> state to the <code>ADMIN</code> state on all targeted servers.</p> (Equivalent to suspend(0, false)) <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> New requests are rejected and inflight work is allowed to complete. ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspend(int, boolean)")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("forceSuspend", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to suspend this partition ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException the partition could not suspend.")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins the forceful transition of the partition from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.forceSuspend(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("forceSuspend", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to suspend this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException the partition could not suspend.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins the forceful transition of the partition from the <code>RUNNING</code> state to the <code>ADMIN</code> state on the specified targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("forceSuspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException the partition could not suspend.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins the forceful transition of the partition from the <code>RUNNING</code> state to the <code>ADMIN</code> state on all targeted servers.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("resume", TargetMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to resume this partition ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the resume operation fails")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.3.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins the transition of the partition from the <code>ADMIN</code> state to the <code>RUNNING</code> state on the specified targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.resume(String[] targetNames)</code> </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("resume", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to resume this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the resume operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins the transition of the partition from the <code>ADMIN</code> state to the <code>RUNNING</code> state on the specified targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("resume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the resume operation fails")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Begins the transition of the partition from the <code>ADMIN</code> state to the <code>RUNNING</code> state on all targeted servers.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("boot", TargetMBean[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to boot this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the boot operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("deprecated", "12.2.1.3.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins the transition of the partition from the <code>SHUTDOWN</code> state and <code>HALTED</code> substate to the <code>SHUTDOWN</code> state and <code>BOOTED</code> substate on the specified targets.</p> <p>This operation has the effect of starting the partition's administrative resource groups on those targets, changing their states on those targets from <code>SHUTDOWN</code> to <code>RUNNING</code>.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.boot(String[] targetNames)</code> </p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("boot", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to boot this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the boot operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins the transition of the partition from the <code>SHUTDOWN</code> state and <code>HALTED</code> substate to the <code>SHUTDOWN</code> state and <code>BOOTED</code> substate on the specified targets.</p> <p>This operation has the effect of starting the partition's administrative resource groups on those targets, changing their states on those targets from <code>SHUTDOWN</code> to <code>RUNNING</code>.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("boot");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the halt operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begins the transition of the partition from the <code>SHUTDOWN</code> state and <code>HALTED</code> substate to the <code>SHUTDOWN</code> state and <code>BOOTED</code> substate on all targeted servers. <p>This operation has the effect of starting the partition's administrative resource groups, changing their states from <code>SHUTDOWN</code> to <code>RUNNING</code>.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("halt", TargetMBean[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "the specific targets on which to halt this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the halt operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("deprecated", "12.2.1.3.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins the forceful transition of the partition from the <code>RUNNING</code> state or the <code>ADMIN</code> state to the <code>SHUTDOWN</code> state and <code>HALTED</code> substate on a specific set of targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> <p>Please use <code>PartitionLifeCycleRuntimeMBean.halt(String[] targetNames)</code> </p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("halt", String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targetNames", "the specific servers on which to halt this partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the halt operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins the forceful transition of the partition from the <code>RUNNING</code> state or the <code>ADMIN</code> state to the <code>SHUTDOWN</code> state and <code>HALTED</code> substate on a specific set of targets.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("halt");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException If the halt operation fails")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins the forceful transition of the partition from the <code>RUNNING</code> state or the <code>ADMIN</code> state to the <code>SHUTDOWN</code> state and <code>HALTED</code> substate on all targeted servers.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("purgeTasks");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Clears the retained task collection. ");
         currentResult.setValue("role", "operation");
      }

      mth = PartitionLifeCycleRuntimeMBean.class.getMethod("getState", ServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverMBean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", "12.2.1.2.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The state of the partition on a specific server.  <p> This method has been deprecated in favor of retrieving partition state correctly on dynamic servers. ServerMBean does not exist for dynamic servers in the domain runtime tree. So, although invoking this method from domain runtime tree works for static servers, does not work for dynamic servers.</p> <p> Please use <code>PartitionLifeCycleRuntimeMBean.getState(String serverName)</code> </p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("getSubState", ServerMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverMBean", "the server on which the substate to check ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("deprecated", "12.2.1.2.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>The current substate of the partition on a specific server.</p>  <p> This method has been deprecated in favor of retrieving partition substate correctly on dynamic servers. ServerMBean does not exist for dynamic servers in the domain runtime tree. So, although invoking this method from domain runtime tree works for static servers, does not work for dynamic servers.</p> <p> Please use <code>PartitionLifeCycleRuntimeMBean.getSubState(ServerLifeCycleRuntimeMBean serverLifeCycleRuntimeMBean)</code></p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.2.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("getState", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", "name of the server on which the substate to check ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "The current state of the partition on a specific server. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.2.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.2.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("getSubState", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", "name of the server on which the substate to check ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>The current substate of the partition on a specific server.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.2.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionLifeCycleRuntimeMBean.class.getMethod("forceRestart");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("excludeFromRest", "");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Begins to halt forcefully the partition and then return it to its current state.</p> <p>Use the returned {@link PartitionLifeCycleTaskRuntimeMBean} value to <a href=\"#useTaskObject\">check for completion</a>.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("excludeFromRest", "");
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
