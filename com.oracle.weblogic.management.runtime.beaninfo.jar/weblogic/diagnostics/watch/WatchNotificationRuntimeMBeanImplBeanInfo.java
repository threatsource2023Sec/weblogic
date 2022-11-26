package weblogic.diagnostics.watch;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.diagnostics.runtimecontrol.internal.WatchManagerRuntimeMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFWatchNotificationRuntimeMBean;

public class WatchNotificationRuntimeMBeanImplBeanInfo extends WatchManagerRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFWatchNotificationRuntimeMBean.class;

   public WatchNotificationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WatchNotificationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.watch.WatchNotificationRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("notificationTranslator", "weblogic.diagnostics.watch.NotificationTranslator");
      beanDescriptor.setValue("package", "weblogic.diagnostics.watch");
      String description = (new String("<p>Provides access to policy and action statistical data for the current instance of this server.</p> <p>Note: As of WebLogic Server 12.2.1, the terms <i>watch</i> and <i>notification</i> are replaced by <i>policy</i> and <i>action</i>, respectively. However, the definition of these terms has not changed.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFWatchNotificationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveAlarmWatches")) {
         getterName = "getActiveAlarmWatches";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveAlarmWatches", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveAlarmWatches", currentResult);
         currentResult.setValue("description", "<p>The names of active alarm policies.</p> ");
      }

      if (!descriptors.containsKey("AverageEventDataWatchEvaluationTime")) {
         getterName = "getAverageEventDataWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageEventDataWatchEvaluationTime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageEventDataWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "<p>The average Instrumentation event data evaluation cycle time, in milliseconds.</p> ");
      }

      if (!descriptors.containsKey("AverageHarvesterWatchEvaluationTime")) {
         getterName = "getAverageHarvesterWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageHarvesterWatchEvaluationTime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageHarvesterWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "<p>The average Harvester evaluation cycle time, in milliseconds.</p> ");
      }

      if (!descriptors.containsKey("AverageLogWatchEvaluationTime")) {
         getterName = "getAverageLogWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageLogWatchEvaluationTime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageLogWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "<p>The average Log evaluation cycle time, in milliseconds.</p> ");
      }

      if (!descriptors.containsKey("CurrentActiveAlarmsCount")) {
         getterName = "getCurrentActiveAlarmsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentActiveAlarmsCount", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentActiveAlarmsCount", currentResult);
         currentResult.setValue("description", "<p>The number of active alarms of any type.</p> ");
      }

      if (!descriptors.containsKey("MaximumActiveAlarmsCount")) {
         getterName = "getMaximumActiveAlarmsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumActiveAlarmsCount", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumActiveAlarmsCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of active alarms at any one time.</p> ");
      }

      if (!descriptors.containsKey("MaximumEventDataWatchEvaluationTime")) {
         getterName = "getMaximumEventDataWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumEventDataWatchEvaluationTime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumEventDataWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The maximum time spent evaluating EventData policies. ");
      }

      if (!descriptors.containsKey("MaximumHarvesterWatchEvaluationTime")) {
         getterName = "getMaximumHarvesterWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumHarvesterWatchEvaluationTime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumHarvesterWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The maximum time spent evaluating Harvester policies. ");
      }

      if (!descriptors.containsKey("MaximumLogWatchEvaluationTime")) {
         getterName = "getMaximumLogWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumLogWatchEvaluationTime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumLogWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The maximum time spent evaluating Log policies. ");
      }

      if (!descriptors.containsKey("MinimumEventDataWatchEvaluationTime")) {
         getterName = "getMinimumEventDataWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimumEventDataWatchEvaluationTime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimumEventDataWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The minimum time spent evaluating Log policies. ");
      }

      if (!descriptors.containsKey("MinimumHarvesterWatchEvaluationTime")) {
         getterName = "getMinimumHarvesterWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimumHarvesterWatchEvaluationTime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimumHarvesterWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The minimum time spent evaluating Harvester policies. ");
      }

      if (!descriptors.containsKey("MinimumLogWatchEvaluationTime")) {
         getterName = "getMinimumLogWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimumLogWatchEvaluationTime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimumLogWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The minimum time spent evaluating Log policies. ");
      }

      if (!descriptors.containsKey("TotalActiveAutomaticResetAlarms")) {
         getterName = "getTotalActiveAutomaticResetAlarms";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalActiveAutomaticResetAlarms", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalActiveAutomaticResetAlarms", currentResult);
         currentResult.setValue("description", "<p>The total number of active automatically reset alarms.</p> ");
      }

      if (!descriptors.containsKey("TotalActiveManualResetAlarms")) {
         getterName = "getTotalActiveManualResetAlarms";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalActiveManualResetAlarms", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalActiveManualResetAlarms", currentResult);
         currentResult.setValue("description", "<p>The total number of active manually reset alarms.</p> ");
      }

      if (!descriptors.containsKey("TotalDIMGNotificationsPerformed")) {
         getterName = "getTotalDIMGNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalDIMGNotificationsPerformed", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalDIMGNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of Diagnostic Image actions fired.  Diagnostic Image files are not true actions, but this records the number of image captures requested by the policy component. ");
      }

      if (!descriptors.containsKey("TotalEventDataEvaluationCycles")) {
         getterName = "getTotalEventDataEvaluationCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalEventDataEvaluationCycles", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalEventDataEvaluationCycles", currentResult);
         currentResult.setValue("description", "<p>The total number of times Instrumentation event data policies have been evaluated.</p> ");
      }

      if (!descriptors.containsKey("TotalEventDataWatchEvaluations")) {
         getterName = "getTotalEventDataWatchEvaluations";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalEventDataWatchEvaluations", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalEventDataWatchEvaluations", currentResult);
         currentResult.setValue("description", "<p>The total number of Instrumentation event data policies that have been evaluated. For each cycle, the Policy and Action component evaluates all of the enabled Instrumentation event data policies.</p> ");
      }

      if (!descriptors.containsKey("TotalEventDataWatchesTriggered")) {
         getterName = "getTotalEventDataWatchesTriggered";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalEventDataWatchesTriggered", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalEventDataWatchesTriggered", currentResult);
         currentResult.setValue("description", "<p>The total number of Instrumentation event data policies that have evaluated to <code>true</code> and triggered actions.</p> ");
      }

      if (!descriptors.containsKey("TotalFailedDIMGNotifications")) {
         getterName = "getTotalFailedDIMGNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedDIMGNotifications", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedDIMGNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed Diagnostic Image action requests. ");
      }

      if (!descriptors.containsKey("TotalFailedJMSNotifications")) {
         getterName = "getTotalFailedJMSNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedJMSNotifications", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedJMSNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed JMS action attempts. ");
      }

      if (!descriptors.containsKey("TotalFailedJMXNotifications")) {
         getterName = "getTotalFailedJMXNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedJMXNotifications", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedJMXNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed JMX action attempts. ");
      }

      if (!descriptors.containsKey("TotalFailedNotifications")) {
         getterName = "getTotalFailedNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedNotifications", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed action requests. ");
      }

      if (!descriptors.containsKey("TotalFailedSMTPNotifications")) {
         getterName = "getTotalFailedSMTPNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedSMTPNotifications", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedSMTPNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed SMTP action attempts. ");
      }

      if (!descriptors.containsKey("TotalFailedSNMPNotifications")) {
         getterName = "getTotalFailedSNMPNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedSNMPNotifications", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedSNMPNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed SNMP action attempts. ");
      }

      if (!descriptors.containsKey("TotalHarvesterEvaluationCycles")) {
         getterName = "getTotalHarvesterEvaluationCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalHarvesterEvaluationCycles", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalHarvesterEvaluationCycles", currentResult);
         currentResult.setValue("description", "<p>The total number of times the Harvester has invoked the Policy and Action component to evaluate Harvester policies. (This number corresponds to the number of sampling cycles.)</p> ");
      }

      if (!descriptors.containsKey("TotalHarvesterWatchEvaluations")) {
         getterName = "getTotalHarvesterWatchEvaluations";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalHarvesterWatchEvaluations", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalHarvesterWatchEvaluations", currentResult);
         currentResult.setValue("description", "<p>The total number of Harvester policies that have been evaluated. For each cycle, the Policy and Action component evaluates all of the enabled Harvester policies.</p> ");
      }

      if (!descriptors.containsKey("TotalHarvesterWatchesTriggered")) {
         getterName = "getTotalHarvesterWatchesTriggered";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalHarvesterWatchesTriggered", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalHarvesterWatchesTriggered", currentResult);
         currentResult.setValue("description", "<p>The total number of Harvester policies that have evaluated to <code>true</code> and triggered actions. </p> ");
      }

      if (!descriptors.containsKey("TotalJMSNotificationsPerformed")) {
         getterName = "getTotalJMSNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalJMSNotificationsPerformed", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalJMSNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of JMS actions successfully fired. ");
      }

      if (!descriptors.containsKey("TotalJMXNotificationsPerformed")) {
         getterName = "getTotalJMXNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalJMXNotificationsPerformed", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalJMXNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of JMX actions successfully fired. ");
      }

      if (!descriptors.containsKey("TotalLogEvaluationCycles")) {
         getterName = "getTotalLogEvaluationCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalLogEvaluationCycles", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalLogEvaluationCycles", currentResult);
         currentResult.setValue("description", "<p>The total number of times Log policies have been evaluated.</p> ");
      }

      if (!descriptors.containsKey("TotalLogWatchEvaluations")) {
         getterName = "getTotalLogWatchEvaluations";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalLogWatchEvaluations", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalLogWatchEvaluations", currentResult);
         currentResult.setValue("description", "<p>The total number of Log policies that have been evaluated. For each cycle, the Policy and Action component evaluates all of the enabled Log policies.</p> ");
      }

      if (!descriptors.containsKey("TotalLogWatchesTriggered")) {
         getterName = "getTotalLogWatchesTriggered";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalLogWatchesTriggered", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalLogWatchesTriggered", currentResult);
         currentResult.setValue("description", "<p>The total number of Log policies that have evaluated to <code>true</code> and triggered actions. </p> ");
      }

      if (!descriptors.containsKey("TotalNotificationsPerformed")) {
         getterName = "getTotalNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNotificationsPerformed", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalNotificationsPerformed", currentResult);
         currentResult.setValue("description", "<p>The total number of actions performed.</p> ");
      }

      if (!descriptors.containsKey("TotalSMTPNotificationsPerformed")) {
         getterName = "getTotalSMTPNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSMTPNotificationsPerformed", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSMTPNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of SMTP actions successfully fired. ");
      }

      if (!descriptors.containsKey("TotalSNMPNotificationsPerformed")) {
         getterName = "getTotalSNMPNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSNMPNotificationsPerformed", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSNMPNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of SNMP actions successfully fired. ");
      }

      if (!descriptors.containsKey("WLDFWatchJMXNotificationRuntime")) {
         getterName = "getWLDFWatchJMXNotificationRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFWatchJMXNotificationRuntime", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFWatchJMXNotificationRuntime", currentResult);
         currentResult.setValue("description", "<p>The WLDFWatchJMXNotificationRuntimeMBean instance that sends JMX actions when a configured policy evaluates to true.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLDFWatchJMXNotificationSource")) {
         getterName = "getWLDFWatchJMXNotificationSource";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFWatchJMXNotificationSource", WLDFWatchNotificationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFWatchJMXNotificationSource", currentResult);
         currentResult.setValue("description", "<p> Returns the MBean that serves as the source for all generated JMX actions from policies. This replaces the deprecated {@link #getWLDFWatchJMXNotificationRuntime()} action source. </p> ");
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
      Method mth = WLDFWatchNotificationRuntimeMBean.class.getMethod("execute", String.class, String[].class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("expression", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Execute a standalone EL expression, for testing purposes only. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationRuntimeMBean.class.getMethod("getBeanInfo", String.class, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("expression", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Gets the BeanInfo for the result type of an EL expression. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = WLDFWatchNotificationRuntimeMBean.class.getMethod("resetWatchAlarm", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("watchName", "the name of the policy to reset ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resets a policy alarm.</p> ");
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
