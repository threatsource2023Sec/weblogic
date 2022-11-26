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
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;

public class ServerLifeCycleRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServerLifeCycleRuntimeMBean.class;

   public ServerLifeCycleRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerLifeCycleRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.server.ServerLifeCycleRuntime");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.server");
      String description = (new String("<p>Provides methods that transition servers from one state to another. This class is instantiated only on the Administration Server, but you can use it to transition the states of Managed Servers as well as Administration Servers.</p>  <p>You cannot use it to start an Administration Server, and if you want to use it to start Managed Servers, you must first set up a Node Manager on each Managed Server's host machine.</p>  <p>If you want to use the methods that transition a server into the <code>ADMIN</code> state, you must first set up an administration channel for that server.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ServerLifeCycleRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("LastKnownMachine")) {
         getterName = "getLastKnownMachine";
         setterName = null;
         currentResult = new PropertyDescriptor("LastKnownMachine", ServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastKnownMachine", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("MiddlewareHome")) {
         getterName = "getMiddlewareHome";
         setterName = null;
         currentResult = new PropertyDescriptor("MiddlewareHome", ServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MiddlewareHome", currentResult);
         currentResult.setValue("description", "<p>The Oracle Middleware installation directory. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      if (!descriptors.containsKey("NodeManagerRestartCount")) {
         getterName = "getNodeManagerRestartCount";
         setterName = null;
         currentResult = new PropertyDescriptor("NodeManagerRestartCount", ServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NodeManagerRestartCount", currentResult);
         currentResult.setValue("description", "Number of times the server has been restarted using the NodeManager since creation. The first start does not count. The count is valid only if the NodeManager is used to start and restart the server everytime. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ProgressAsXml")) {
         getterName = "getProgressAsXml";
         setterName = null;
         currentResult = new PropertyDescriptor("ProgressAsXml", ServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProgressAsXml", currentResult);
         currentResult.setValue("description", "Gets information about the progress of the boot of the managed server. <p> The XML returned will conform to the schema found in the progress-tracker.xsd found in the root of the weblogic.utils jar ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", ServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The current state of the server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StateVal")) {
         getterName = "getStateVal";
         setterName = null;
         currentResult = new PropertyDescriptor("StateVal", ServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StateVal", currentResult);
         currentResult.setValue("description", "<p>An integer that identifies the current state of the server. Values range from <code>0</code> to <code>8</code>.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Tasks")) {
         getterName = "getTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("Tasks", ServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Tasks", currentResult);
         currentResult.setValue("description", "<p>Get preexisting Server Lifecycle Tasks</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("WeblogicHome")) {
         getterName = "getWeblogicHome";
         setterName = null;
         currentResult = new PropertyDescriptor("WeblogicHome", ServerLifeCycleRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WeblogicHome", currentResult);
         currentResult.setValue("description", "<p>The directory where the WebLogic Server instance (server) is installed, without the trailing \"/server\".</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         Method mth = ServerLifeCycleRuntimeMBean.class.getMethod("lookupTask", String.class);
         ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("taskName", (String)null)};
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Get preexisting Server Lifecycle Task</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "Tasks");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ServerLifeCycleRuntimeMBean.class.getMethod("start");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Uses the Node Manager to start a Managed Server.</p> ");
         currentResult.setValue("role", "operation");
      }

      String methodKey;
      ParameterDescriptor[] parameterDescriptors;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerLifeCycleRuntimeMBean.class.getMethod("start", Boolean.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("disableMsiMode", "if true, the server will not start in Managed Server Independence mode ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Uses the Node Manager to start a Managed Server on the given machine</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("start", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Uses the Node Manager to start a Managed Server on the given machine</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("startInStandby");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Uses the Node Manager to start a Managed Server and place it in the <code>STANDBY</code> state.</p>  <p>The server transitions through the following states:</p> <p><code>SHUTDOWN</code>&gt;<code>SUSPENDING</code>&gt;<code>STANDBY</code>.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerLifeCycleRuntimeMBean.class.getMethod("startInStandby", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Uses the Node Manager to start a Managed Server on the given machine and place it in the <code>STANDBY</code> state</p>  <p>The server transitions through the following states:</p> <p><code>SHUTDOWN</code>&gt;<code>SUSPENDING</code>&gt;<code>STANDBY</code>.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerLifeCycleRuntimeMBean.class.getMethod("startInAdmin");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Uses the Node Manager to start a Managed Server and place it in the <code>ADMIN</code> state.</p>  <p>The server transitions through the following states:</p> <p><code>SHUTDOWN</code>&gt;<code>SUSPENDING</code>&gt;<code>STANDBY</code><code>ADMIN</code>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerLifeCycleRuntimeMBean.class.getMethod("startInAdmin", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Uses the Node Manager to start a Managed Server on the given machine and place it in the <code>ADMIN</code> state.</p>  <p>The server transitions through the following states:</p> <p><code>SHUTDOWN</code>&gt;<code>SUSPENDING</code>&gt;<code>STANDBY</code><code>ADMIN</code>.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("resume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Transitions the server from <code>ADMIN</code> to <code>RUNNING</code> state.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("suspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully suspends server to <code>ADMIN</code> state. New requests are rejected and inflight work is allowed to complete.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspend(int, boolean)")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Seconds to wait for server to transition gracefully. The server calls {@link #forceSuspend()} after timeout. "), createParameterDescriptor("ignoreSessions", "drop inflight HTTP sessions during graceful suspend ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerLifecycleException server failed to suspend gracefully.            A {@link #forceSuspend()} or a {@link #forceShutdown()} operation can be   invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Transitions the server from <code>RUNNING</code> to <code>ADMIN</code> state gracefully.</p>  <p>Applications are in admin mode. Inflight work is completed.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("forceSuspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerLifecycleException server failed to force suspend.            A {@link #forceShutdown()} operation can be invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Transitions the server from RUNNING to ADMIN state forcefully cancelling inflight work.</p>  <p>Work that cannot be cancelled is dropped. Applications are brought into the admin mode forcefully.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("shutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully transitions a server to the <code>SHUTDOWN</code> state. The server completes all current work before it shuts down.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and force shutting down the server. "), createParameterDescriptor("ignoreSessions", "Set to <code>true</code> to ignore pending HTTP sessions during inflight work handling. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerLifecycleException If the server fails to gracefully  shutdown. A {@link #forceShutdown()} operation can be invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully transitions a server to the <code>SHUTDOWN</code> state. The server completes all current work before it shuts down.</p>  <p>This method is the same as calling:</p> <p><code>shutdown(timeout, ignoreSessions, false);</code></p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerLifeCycleRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and force shutting down the server. "), createParameterDescriptor("ignoreSessions", "Set to <code>true</code> to ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "Set to <code>true</code> to wait for all HTTP sessions duirng inflight work handling; <code>false</code> to wait for non-persisted HTTP sessions only. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Gracefully shutdown the server after handling inflight work. The following inflight work is handled :</p>  <ul> <li> <p>Pending transaction's and TLOG checkpoint</p> </li>  <li> <p>Pending HTTP sessions</p> </li>  <li> <p>Pending JMS work</p> </li>  <li> <p>Pending work in the Work Managers</p> </li>  <li> <p>RMI requests with tx context or administrator calls</p> </li> </ul> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("forceShutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Immediately transitions a server to the <code>SHUTDOWN</code> state. The server immediately terminates all current work, moves through the <code>SHUTTING_DOWN</code> state, and ends in the <code>SHUTDOWN</code> state.</p>  <p>You can forcefully shut down a server from any state except <code>UNKNOWN</code>.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerLifeCycleRuntimeMBean.class.getMethod("clearOldServerLifeCycleTaskRuntimes");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes all ServerLifeCycleTaskRuntimeMBeans that have completed and been around for over 30 minutes. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion)) {
         mth = ServerLifeCycleRuntimeMBean.class.getMethod("getIPv4URL", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("protocol", "the desired protocol ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>The IPv4 URL that clients use when connecting to this server using the specified protocol.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.3.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion)) {
         mth = ServerLifeCycleRuntimeMBean.class.getMethod("getIPv6URL", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("protocol", "the desired protocol ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>The IPv6 URL that clients use when connecting to this server using the specified protocol.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.3.3.0");
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
