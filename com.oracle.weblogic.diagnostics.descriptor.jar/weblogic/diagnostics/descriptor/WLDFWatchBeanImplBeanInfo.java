package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFWatchBeanImplBeanInfo extends WLDFBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFWatchBean.class;

   public WLDFWatchBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFWatchBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFWatchBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>Defines policies and actions.</p> <p>Note: As of WebLogic Server 12.2.1, the terms <i>watch</i> and <i>notification</i> are replaced by <i>policy</i> and <i>action</i>, respectively. However, the definition of these terms has not changed.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFWatchBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AlarmResetPeriod")) {
         getterName = "getAlarmResetPeriod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAlarmResetPeriod";
         }

         currentResult = new PropertyDescriptor("AlarmResetPeriod", WLDFWatchBean.class, getterName, setterName);
         descriptors.put("AlarmResetPeriod", currentResult);
         currentResult.setValue("description", "<p>For automatic alarms, the time period, in milliseconds, to wait after the policy evaluates to <code>true</code> before the alarm is automatically reset.</p>  <p>The default reset period is 60000 milliseconds, which is equivalent to 60 seconds.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60000));
         currentResult.setValue("legalMin", new Integer(1000));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AlarmType")) {
         getterName = "getAlarmType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAlarmType";
         }

         currentResult = new PropertyDescriptor("AlarmType", WLDFWatchBean.class, getterName, setterName);
         descriptors.put("AlarmType", currentResult);
         currentResult.setValue("description", "<p>The alarm type for the policy: manual or automatic. The default alarm type is manual.</p>  <p>Once a manually set alarm has triggered, it must be reset through the WebLogic Server Administration Console or programmatically before it can trigger again. An automatic reset alarm will reset after the specified time period has elapsed.</p> ");
         setPropertyDescriptorDefault(currentResult, "None");
         currentResult.setValue("legalValues", new Object[]{"None", "ManualReset", "AutomaticReset"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ExpressionLanguage")) {
         getterName = "getExpressionLanguage";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExpressionLanguage";
         }

         currentResult = new PropertyDescriptor("ExpressionLanguage", WLDFWatchBean.class, getterName, setterName);
         descriptors.put("ExpressionLanguage", currentResult);
         currentResult.setValue("description", "Returns the expression language type used by the policy, either \"EL\" or \"WLDF\" (deprecated). ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Notifications")) {
         getterName = "getNotifications";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotifications";
         }

         currentResult = new PropertyDescriptor("Notifications", WLDFWatchBean.class, getterName, setterName);
         descriptors.put("Notifications", currentResult);
         currentResult.setValue("description", "<p>The actions enabled for this policy.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeNotification");
         currentResult.setValue("adder", "addNotification");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RuleExpression")) {
         getterName = "getRuleExpression";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRuleExpression";
         }

         currentResult = new PropertyDescriptor("RuleExpression", WLDFWatchBean.class, getterName, setterName);
         descriptors.put("RuleExpression", currentResult);
         currentResult.setValue("description", "<p>The expression used to evaluate the policy.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RuleType")) {
         getterName = "getRuleType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRuleType";
         }

         currentResult = new PropertyDescriptor("RuleType", WLDFWatchBean.class, getterName, setterName);
         descriptors.put("RuleType", currentResult);
         currentResult.setValue("description", "<p>The expression type for the policy: <code>HARVESTER_RULE_TYPE</code> refers to harvested data, <code>LOG_RULE_TYPE</code> refers to server log entry data, <code>DOMAIN_LOG_RULE_TYPE</code> refers to domain log entry data, and <code>EVENT_DATA_RULE_TYPE</code> refers to instrumentation event data. The default type is <code>HARVESTER_RULE_TYPE</code>.</p>  <p>For information on policy expressions, see \"Using the Diagnostics Framework for Oracle WebLogic Server\" at <a href=\"http://docs.oracle.com/middleware/home/index.html\" shape=\"rect\">http://docs.oracle.com/middleware/home/index.html</a>.</p> ");
         setPropertyDescriptorDefault(currentResult, "Harvester");
         currentResult.setValue("legalValues", new Object[]{"Harvester", "Log", "DomainLog", "EventData"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Schedule")) {
         getterName = "getSchedule";
         setterName = null;
         currentResult = new PropertyDescriptor("Schedule", WLDFWatchBean.class, getterName, setterName);
         descriptors.put("Schedule", currentResult);
         currentResult.setValue("description", "<p>Defines the evaluation frequency for Harvester type rules, in seconds.</p>  <p>The default frequency is 5 minutes (300 seconds), minimum is 5 seconds.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("WLDFScheduleBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Severity")) {
         getterName = "getSeverity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSeverity";
         }

         currentResult = new PropertyDescriptor("Severity", WLDFWatchBean.class, getterName, setterName);
         descriptors.put("Severity", currentResult);
         currentResult.setValue("description", "<p>The severity level of the actions sent when this policy evaluates to <code>true</code>. When set, this level overrides the default value provided in the parent MBean. However, if no severity level is set (null), the value provided in the parent MBean is returned.</p>  <p>The severity levels are the same levels used by the logging framework and the {@link weblogic.logging.Severities} class.</p> ");
         setPropertyDescriptorDefault(currentResult, "Notice");
         currentResult.setValue("legalValues", new Object[]{"Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", WLDFWatchBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this policy is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFWatchBean.class.getMethod("addNotification", WLDFNotificationBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("action", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds an action to this policy.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Notifications");
      }

      mth = WLDFWatchBean.class.getMethod("removeNotification", WLDFNotificationBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("action", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes an action from this policy.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Notifications");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
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
