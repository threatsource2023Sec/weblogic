package weblogic.jdbc.common.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.j2ee.ComponentRuntimeMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JDBCReplayStatisticsRuntimeMBean;

public class JDBCReplayStatisticsRuntimeMBeanImplBeanInfo extends ComponentRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCReplayStatisticsRuntimeMBean.class;

   public JDBCReplayStatisticsRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCReplayStatisticsRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jdbc.common.internal.JDBCReplayStatisticsRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.jdbc.common.internal");
      String description = (new String("This class is used to get replay statics for an Oracle replay datasource aggregated across all connections in the pool. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JDBCReplayStatisticsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DeploymentState")) {
         getterName = "getDeploymentState";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentState", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentState", currentResult);
         currentResult.setValue("description", "<p>The current deployment state of the module.</p>  <p>A module can be in one and only one of the following states. State can be changed via deployment or administrator console.</p>  <ul> <li>UNPREPARED. State indicating at this  module is neither  prepared or active.</li>  <li>PREPARED. State indicating at this module of this application is prepared, but not active. The classes have been loaded and the module has been validated.</li>  <li>ACTIVATED. State indicating at this module  is currently active.</li>  <li>NEW. State indicating this module has just been created and is being initialized.</li> </ul> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setDeploymentState(int)")};
         currentResult.setValue("see", seeObjectArray);
      }

      if (!descriptors.containsKey("FailedReplayCount")) {
         getterName = "getFailedReplayCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedReplayCount", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedReplayCount", currentResult);
         currentResult.setValue("description", "<p>Obtains the number of replays that failed.</p>  <p>When replay fails, it rethrows the original SQLRecoverableException to the application, with the reason for the failure chained to that original exception. Application can call <code>getNextException</code> to retrieve the reason.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModuleId")) {
         getterName = "getModuleId";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleId", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleId", currentResult);
         currentResult.setValue("description", "<p>Returns the identifier for this Component.  The identifier is unique within the application.</p>  <p>Typical modules will use the URI for their id.  Web Modules will return their context-root since the web-uri may not be unique within an EAR.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ReplayDisablingCount")) {
         getterName = "getReplayDisablingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReplayDisablingCount", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReplayDisablingCount", currentResult);
         currentResult.setValue("description", "Obtains the number of times that replay is disabled.  When replay is disabled in the middle of a request, the remaining calls in that request are no longer protected by AC. In case an outage strikes one of those remaining calls, no replay will be attempted, and application simply gets an SQLRecoverableException. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SuccessfulReplayCount")) {
         getterName = "getSuccessfulReplayCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SuccessfulReplayCount", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuccessfulReplayCount", currentResult);
         currentResult.setValue("description", "Obtains the number of replays that succeeded. Successful replays mask the outages from applications. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalCalls")) {
         getterName = "getTotalCalls";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCalls", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCalls", currentResult);
         currentResult.setValue("description", "Obtains the total number of JDBC calls executed so far. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalCallsAffectedByOutages")) {
         getterName = "getTotalCallsAffectedByOutages";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCallsAffectedByOutages", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCallsAffectedByOutages", currentResult);
         currentResult.setValue("description", "Obtains the number of JDBC calls affected by outages. This includes both local calls and calls that involve roundtrip(s) to the database server. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalCallsAffectedByOutagesDuringReplay")) {
         getterName = "getTotalCallsAffectedByOutagesDuringReplay";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCallsAffectedByOutagesDuringReplay", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCallsAffectedByOutagesDuringReplay", currentResult);
         currentResult.setValue("description", "Obtains the number of JDBC calls affected by outages in the middle of replay. Outages may be cascaded and strike a call multiple times when replay is ongoing. AC automatically reattempts replay when this happens, unless it reaches the maximum retry limit. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalCallsTriggeringReplay")) {
         getterName = "getTotalCallsTriggeringReplay";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCallsTriggeringReplay", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCallsTriggeringReplay", currentResult);
         currentResult.setValue("description", "Obtains the number of JDBC calls that triggered replay. Not all the calls affected by an outage trigger replay, because replay can be disabled for some requests. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalCompletedRequests")) {
         getterName = "getTotalCompletedRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCompletedRequests", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCompletedRequests", currentResult);
         currentResult.setValue("description", "Obtains the total number of completed requests so far. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalProtectedCalls")) {
         getterName = "getTotalProtectedCalls";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalProtectedCalls", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalProtectedCalls", currentResult);
         currentResult.setValue("description", "Obtains the total number of JDBC calls executed so far that are protected by AC. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalReplayAttempts")) {
         getterName = "getTotalReplayAttempts";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalReplayAttempts", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalReplayAttempts", currentResult);
         currentResult.setValue("description", "Obtains the number of replay attempts.  AC automatically reattempts when replay fails, so this number may exceed the number of JDBC calls that triggered replay. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalRequests")) {
         getterName = "getTotalRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalRequests", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalRequests", currentResult);
         currentResult.setValue("description", "Obtains the total number of successfully submitted requests so far. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WorkManagerRuntimes")) {
         getterName = "getWorkManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntimes", JDBCReplayStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all work managers defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "9.0.0.0");
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
      Method mth = JDBCReplayStatisticsRuntimeMBean.class.getMethod("refreshStatistics");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Update the snapshot ");
         currentResult.setValue("role", "operation");
      }

      mth = JDBCReplayStatisticsRuntimeMBean.class.getMethod("clearStatistics");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Clear the statistics on all connections. ");
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
