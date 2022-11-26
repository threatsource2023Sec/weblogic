package weblogic.diagnostics.runtimecontrol.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFWatchManagerRuntimeMBean;

public class WatchManagerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFWatchManagerRuntimeMBean.class;

   public WatchManagerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WatchManagerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.runtimecontrol.internal.WatchManagerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.2.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.runtimecontrol.internal");
      String description = (new String("<p>Provides access to Policy and Action statistical data for the current instance of this server.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFWatchManagerRuntimeMBean");
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
         currentResult = new PropertyDescriptor("ActiveAlarmWatches", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveAlarmWatches", currentResult);
         currentResult.setValue("description", "<p>The names of active alarm policies.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AverageEventDataWatchEvaluationTime")) {
         getterName = "getAverageEventDataWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageEventDataWatchEvaluationTime", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageEventDataWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "<p>The average Instrumentation event data evaluation cycle time, in milliseconds.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AverageHarvesterWatchEvaluationTime")) {
         getterName = "getAverageHarvesterWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageHarvesterWatchEvaluationTime", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageHarvesterWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "<p>The average Harvester evaluation cycle time, in milliseconds.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AverageLogWatchEvaluationTime")) {
         getterName = "getAverageLogWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageLogWatchEvaluationTime", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageLogWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "<p>The average Log evaluation cycle time, in milliseconds.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentActiveAlarmsCount")) {
         getterName = "getCurrentActiveAlarmsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentActiveAlarmsCount", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentActiveAlarmsCount", currentResult);
         currentResult.setValue("description", "<p>The number of active alarms of any type.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumActiveAlarmsCount")) {
         getterName = "getMaximumActiveAlarmsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumActiveAlarmsCount", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumActiveAlarmsCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of active alarms at any one time.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumEventDataWatchEvaluationTime")) {
         getterName = "getMaximumEventDataWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumEventDataWatchEvaluationTime", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumEventDataWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The maximum time spent evaluating EventData policies. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumHarvesterWatchEvaluationTime")) {
         getterName = "getMaximumHarvesterWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumHarvesterWatchEvaluationTime", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumHarvesterWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The maximum time spent evaluating Harvester policies. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumLogWatchEvaluationTime")) {
         getterName = "getMaximumLogWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumLogWatchEvaluationTime", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumLogWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The maximum time spent evaluating Log policies. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimumEventDataWatchEvaluationTime")) {
         getterName = "getMinimumEventDataWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimumEventDataWatchEvaluationTime", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimumEventDataWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The minimum time spent evaluating Log policies. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimumHarvesterWatchEvaluationTime")) {
         getterName = "getMinimumHarvesterWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimumHarvesterWatchEvaluationTime", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimumHarvesterWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The minimum time spent evaluating Harvester policies. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimumLogWatchEvaluationTime")) {
         getterName = "getMinimumLogWatchEvaluationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimumLogWatchEvaluationTime", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimumLogWatchEvaluationTime", currentResult);
         currentResult.setValue("description", "The minimum time spent evaluating Log policies. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalActiveAutomaticResetAlarms")) {
         getterName = "getTotalActiveAutomaticResetAlarms";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalActiveAutomaticResetAlarms", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalActiveAutomaticResetAlarms", currentResult);
         currentResult.setValue("description", "<p>The total number of active automatically reset alarms.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalActiveManualResetAlarms")) {
         getterName = "getTotalActiveManualResetAlarms";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalActiveManualResetAlarms", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalActiveManualResetAlarms", currentResult);
         currentResult.setValue("description", "<p>The total number of active manually reset alarms.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalDIMGNotificationsPerformed")) {
         getterName = "getTotalDIMGNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalDIMGNotificationsPerformed", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalDIMGNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of Diagnostic Image actions fired.  Diagnostic Image files are not true actions, but this records the number of image captures requested by the policy component. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalEventDataEvaluationCycles")) {
         getterName = "getTotalEventDataEvaluationCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalEventDataEvaluationCycles", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalEventDataEvaluationCycles", currentResult);
         currentResult.setValue("description", "<p>The total number of times Instrumentation event data policies have been evaluated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalEventDataWatchEvaluations")) {
         getterName = "getTotalEventDataWatchEvaluations";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalEventDataWatchEvaluations", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalEventDataWatchEvaluations", currentResult);
         currentResult.setValue("description", "<p>The total number of Instrumentation event data policies that have been evaluated. For each cycle, the Policy and Action component evaluates all of the enabled Instrumentation event data policies.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalEventDataWatchesTriggered")) {
         getterName = "getTotalEventDataWatchesTriggered";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalEventDataWatchesTriggered", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalEventDataWatchesTriggered", currentResult);
         currentResult.setValue("description", "<p>The total number of Instrumentation event data policies that have evaluated to <code>true</code> and triggered actions.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalFailedDIMGNotifications")) {
         getterName = "getTotalFailedDIMGNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedDIMGNotifications", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedDIMGNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed Diagnostic Image action requests. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalFailedJMSNotifications")) {
         getterName = "getTotalFailedJMSNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedJMSNotifications", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedJMSNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed JMS action attempts. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalFailedJMXNotifications")) {
         getterName = "getTotalFailedJMXNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedJMXNotifications", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedJMXNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed JMX action attempts. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalFailedNotifications")) {
         getterName = "getTotalFailedNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedNotifications", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed action requests. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalFailedSMTPNotifications")) {
         getterName = "getTotalFailedSMTPNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedSMTPNotifications", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedSMTPNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed SMTP action attempts. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalFailedSNMPNotifications")) {
         getterName = "getTotalFailedSNMPNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFailedSNMPNotifications", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFailedSNMPNotifications", currentResult);
         currentResult.setValue("description", "The total number of failed SNMP action attempts. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalHarvesterEvaluationCycles")) {
         getterName = "getTotalHarvesterEvaluationCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalHarvesterEvaluationCycles", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalHarvesterEvaluationCycles", currentResult);
         currentResult.setValue("description", "<p>The total number of times the Harvester has invoked the Policy and Action component to evaluate Harvester policies. (This number corresponds to the number of sampling cycles.)</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalHarvesterWatchEvaluations")) {
         getterName = "getTotalHarvesterWatchEvaluations";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalHarvesterWatchEvaluations", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalHarvesterWatchEvaluations", currentResult);
         currentResult.setValue("description", "<p>The total number of Harvester policies that have been evaluated. For each cycle, the Policy and Action component evaluates all of the enabled Harvester policies.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalHarvesterWatchesTriggered")) {
         getterName = "getTotalHarvesterWatchesTriggered";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalHarvesterWatchesTriggered", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalHarvesterWatchesTriggered", currentResult);
         currentResult.setValue("description", "<p>The total number of Harvester policies that have evaluated to <code>true</code> and triggered actions. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalJMSNotificationsPerformed")) {
         getterName = "getTotalJMSNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalJMSNotificationsPerformed", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalJMSNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of JMS actions successfully fired. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalJMXNotificationsPerformed")) {
         getterName = "getTotalJMXNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalJMXNotificationsPerformed", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalJMXNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of JMX actions successfully fired. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalLogEvaluationCycles")) {
         getterName = "getTotalLogEvaluationCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalLogEvaluationCycles", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalLogEvaluationCycles", currentResult);
         currentResult.setValue("description", "<p>The total number of times Log policies have been evaluated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalLogWatchEvaluations")) {
         getterName = "getTotalLogWatchEvaluations";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalLogWatchEvaluations", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalLogWatchEvaluations", currentResult);
         currentResult.setValue("description", "<p>The total number of Log policies that have been evaluated. For each cycle, the Policy and Action component evaluates all of the enabled Log policies.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalLogWatchesTriggered")) {
         getterName = "getTotalLogWatchesTriggered";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalLogWatchesTriggered", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalLogWatchesTriggered", currentResult);
         currentResult.setValue("description", "<p>The total number of Log policies that have evaluated to <code>true</code> and triggered actions. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalNotificationsPerformed")) {
         getterName = "getTotalNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNotificationsPerformed", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalNotificationsPerformed", currentResult);
         currentResult.setValue("description", "<p>The total number of actions performed.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalSMTPNotificationsPerformed")) {
         getterName = "getTotalSMTPNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSMTPNotificationsPerformed", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSMTPNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of SMTP actions successfully fired. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalSNMPNotificationsPerformed")) {
         getterName = "getTotalSNMPNotificationsPerformed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSNMPNotificationsPerformed", WLDFWatchManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSNMPNotificationsPerformed", currentResult);
         currentResult.setValue("description", "The total number of SNMP actions successfully fired. ");
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
      Method mth = WLDFWatchManagerRuntimeMBean.class.getMethod("resetWatchAlarm", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("watchName", "the name of the policy to reset ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
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
