package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFWatchNotificationBeanImplBeanInfo extends WLDFBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFWatchNotificationBean.class;

   public WLDFWatchNotificationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFWatchNotificationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFWatchNotificationBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>Configures and controls the WebLogic Diagnostic Framework (WLDF) policy and action component; creates and deletes policy definitions; and defines the rules that apply to specific policies.</p>  <p>Note: As of WebLogic Server 12.2.1, the terms <i>watch</i> and <i>notification</i> are replaced by <i>policy</i> and <i>action</i>, respectively. However, the definition of these terms has not changed.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFWatchNotificationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Actions")) {
         getterName = "getActions";
         setterName = null;
         currentResult = new PropertyDescriptor("Actions", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("Actions", currentResult);
         currentResult.setValue("description", "<p>The actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createAction");
         currentResult.setValue("destroyer", "destroyAction");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("HeapDumpActions")) {
         getterName = "getHeapDumpActions";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapDumpActions", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("HeapDumpActions", currentResult);
         currentResult.setValue("description", "<p>Get the Heap Dump actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createHeapDumpAction");
         currentResult.setValue("destroyer", "destroyHeapDumpAction");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("ImageNotifications")) {
         getterName = "getImageNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("ImageNotifications", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("ImageNotifications", currentResult);
         currentResult.setValue("description", "<p>The Image actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createImageNotification");
         currentResult.setValue("destroyer", "destroyImageNotification");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSNotifications")) {
         getterName = "getJMSNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSNotifications", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("JMSNotifications", currentResult);
         currentResult.setValue("description", "<p>The JMS actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJMSNotification");
         currentResult.setValue("creator", "createJMSNotification");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMXNotifications")) {
         getterName = "getJMXNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("JMXNotifications", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("JMXNotifications", currentResult);
         currentResult.setValue("description", "<p>The JMX notification actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJMXNotification");
         currentResult.setValue("creator", "createJMXNotification");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogActions")) {
         getterName = "getLogActions";
         setterName = null;
         currentResult = new PropertyDescriptor("LogActions", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("LogActions", currentResult);
         currentResult.setValue("description", "<p>The Log actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyLogAction");
         currentResult.setValue("creator", "createLogAction");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("LogWatchSeverity")) {
         getterName = "getLogWatchSeverity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogWatchSeverity";
         }

         currentResult = new PropertyDescriptor("LogWatchSeverity", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("LogWatchSeverity", currentResult);
         currentResult.setValue("description", "<p>The threshold severity level of log messages evaluated by log policies. Messages with a lower severity than this value will be ignored and not evaluated against the policies.</p>  <p>Do not confuse LogWatchSeverity with Severity. LogWatchSeverity filters which log messages will be evaluated; Severity sets the default severity level for an action.</p> ");
         setPropertyDescriptorDefault(currentResult, "Warning");
         currentResult.setValue("legalValues", new Object[]{"Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Notifications")) {
         getterName = "getNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("Notifications", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("Notifications", currentResult);
         currentResult.setValue("description", "<p>The actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeNotification");
         currentResult.setValue("adder", "addNotification");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RESTNotifications")) {
         getterName = "getRESTNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("RESTNotifications", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("RESTNotifications", currentResult);
         currentResult.setValue("description", "<p>The REST actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyRESTNotification");
         currentResult.setValue("creator", "createRESTNotification");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("SMTPNotifications")) {
         getterName = "getSMTPNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("SMTPNotifications", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("SMTPNotifications", currentResult);
         currentResult.setValue("description", "<p>The SMTP actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSMTPNotification");
         currentResult.setValue("destroyer", "destroySMTPNotification");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SNMPNotifications")) {
         getterName = "getSNMPNotifications";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPNotifications", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("SNMPNotifications", currentResult);
         currentResult.setValue("description", "<p>The SNMP actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPNotification");
         currentResult.setValue("creator", "createSNMPNotification");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ScaleDownActions")) {
         getterName = "getScaleDownActions";
         setterName = null;
         currentResult = new PropertyDescriptor("ScaleDownActions", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("ScaleDownActions", currentResult);
         currentResult.setValue("description", "<p>The Scale-down actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createScaleDownAction");
         currentResult.setValue("destroyer", "destroyScaleDownAction");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ScaleUpActions")) {
         getterName = "getScaleUpActions";
         setterName = null;
         currentResult = new PropertyDescriptor("ScaleUpActions", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("ScaleUpActions", currentResult);
         currentResult.setValue("description", "<p>The Scale-Up actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyScaleUpAction");
         currentResult.setValue("creator", "createScaleUpAction");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ScriptActions")) {
         getterName = "getScriptActions";
         setterName = null;
         currentResult = new PropertyDescriptor("ScriptActions", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("ScriptActions", currentResult);
         currentResult.setValue("description", "<p>The Script actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createScriptAction");
         currentResult.setValue("destroyer", "destroyScriptAction");
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

         currentResult = new PropertyDescriptor("Severity", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("Severity", currentResult);
         currentResult.setValue("description", "<p>The default action severity level for all policies. When a policy triggers, the severity level is delivered with the action.</p>  <p>The severity levels are the same levels used by the logging framework and the {@link weblogic.logging.Severities} class. If no level is specified, the default value is <code>Notice</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, "Notice");
         currentResult.setValue("legalValues", new Object[]{"Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ThreadDumpActions")) {
         getterName = "getThreadDumpActions";
         setterName = null;
         currentResult = new PropertyDescriptor("ThreadDumpActions", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("ThreadDumpActions", currentResult);
         currentResult.setValue("description", "<p>Get the Thread Dump actions defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyThreadDumpAction");
         currentResult.setValue("creator", "createThreadDumpAction");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("Watches")) {
         getterName = "getWatches";
         setterName = null;
         currentResult = new PropertyDescriptor("Watches", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("Watches", currentResult);
         currentResult.setValue("description", "<p>The policies defined in this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWatch");
         currentResult.setValue("destroyer", "destroyWatch");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", WLDFWatchNotificationBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the policy and action component is enabled.</p>  <p>If <code>true</code> (the default), all configured policies are activated, incoming data or events are evaluated against the policies, and actions are generated when policy conditions are met. If <code>false</code>, all policies are rendered inactive.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFWatchNotificationBean.class.getMethod("createWatch", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the watch configuration ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a policy configuration with the given name.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Watches");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("destroyWatch", WLDFWatchBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("watch", "the watch configuration defined in this deployment ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the specified policy configuration defined in this deployment.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Watches");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("createAction", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The instance name for the action being created "), createParameterDescriptor("type", "The action type ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates an action bean with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "Actions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("destroyAction", WLDFActionBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the action configuration defined in this deployment ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Deletes the specified action configuration defined in this deployment.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "Actions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = WLDFWatchNotificationBean.class.getMethod("createImageNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Image notification being created ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates an Image action configuration with the specified name.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ImageNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("destroyImageNotification", WLDFImageNotificationBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the Image notification configuration defined in this deployment ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the specified Image action configuration defined in this deployment.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ImageNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("createJMSNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMS notification being created ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates an JMS action configuration with the specified name.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("destroyJMSNotification", WLDFJMSNotificationBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the JMS notification configuration defined in this deployment ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the specified JMS action configuration defined in this deployment.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSNotifications");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("createLogAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Log action being created ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates an Log action configuration with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "LogActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("destroyLogAction", WLDFLogActionBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the Log action configuration defined in this deployment ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Deletes the specified Log action configuration defined in this deployment.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "LogActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = WLDFWatchNotificationBean.class.getMethod("createJMXNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMX notification being created ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates an JMX notification configuration with the specified name.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMXNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("destroyJMXNotification", WLDFJMXNotificationBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the JMX notification configuration defined in this deployment ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the specified JMX notification configuration defined in this deployment.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMXNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("createSMTPNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the SMTP notification being created ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates an SMTP action configuration with the specified name.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SMTPNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("destroySMTPNotification", WLDFSMTPNotificationBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the SMTP notification configuration defined in this deployment ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the specified SMTP action configuration defined in this deployment.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SMTPNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("createSNMPNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the SNMP notification being created ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates an SNMP action configuration with the specified name.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SNMPNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("destroySNMPNotification", WLDFSNMPNotificationBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the SNMP notification configuration defined in this deployment ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the specified SNMP action configuration defined in this deployment.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SNMPNotifications");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("createRESTNotification", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the REST notification being created ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates an REST action configuration with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "RESTNotifications");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("destroyRESTNotification", WLDFRESTNotificationBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the REST notification configuration defined in this deployment ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Deletes the specified REST action configuration defined in this deployment.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "RESTNotifications");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("createScaleUpAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Scale-up action being created ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates a scale-up action configuration with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ScaleUpActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("destroyScaleUpAction", WLDFScaleUpActionBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the Scale-up action configuration defined in this deployment ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Deletes the specified Scale-up action configuration defined in this deployment.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ScaleUpActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("createScaleDownAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Scale-down action being created ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates a scale-down action configuration with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ScaleDownActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("destroyScaleDownAction", WLDFScaleDownActionBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the Scale-down action configuration defined in this deployment ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Deletes the specified Scale-down action configuration defined in this deployment.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ScaleDownActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("createScriptAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Script action being created ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates an Script action configuration with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ScriptActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("destroyScriptAction", WLDFScriptActionBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the Script action configuration defined in this deployment ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Deletes the specified Script action configuration defined in this deployment.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ScriptActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("createHeapDumpAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Heap Dump action being created ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates an Heap Dump action configuration with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "HeapDumpActions");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("destroyHeapDumpAction", WLDFHeapDumpActionBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the Heap Dump action configuration defined in this deployment ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Deletes the specified Heap Dump action configuration defined in this deployment.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "HeapDumpActions");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("createThreadDumpAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Thread Dump action being created ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates an Thread Dump action configuration with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ThreadDumpActions");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("destroyThreadDumpAction", WLDFThreadDumpActionBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("notification", "the Thread Dump action configuration defined in this deployment ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Deletes the specified Thread Dump action configuration defined in this deployment.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ThreadDumpActions");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("lookupAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up an action with the given name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "Actions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = WLDFWatchNotificationBean.class.getMethod("lookupImageNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Image notification being requested ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Looks up the Image action configuration with the specified name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ImageNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("lookupJMSNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMS notification being requested ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Looks up the JMS action configuration with the given name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSNotifications");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("lookupLogAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Log action being requested ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up the Log action configuration with the given name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "LogActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = WLDFWatchNotificationBean.class.getMethod("lookupJMXNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMX notification being requested ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Looks up the JMX notification action configuration with the specified name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMXNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("lookupSMTPNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the SMTP notification being requested ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Looks up the SMTP action configuration with the specified name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SMTPNotifications");
      }

      mth = WLDFWatchNotificationBean.class.getMethod("lookupSNMPNotification", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the SNMP notification being requested ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Looks up the SNMP action configuration with the given name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SNMPNotifications");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("lookupRESTNotification", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the REST notification being requested ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up the REST action configuration with the given name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "RESTNotifications");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("lookupScaleDownAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Scale-down action being requested ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up the Scale-down action configuration with the given name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ScaleDownActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("lookupScaleUpAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Scaling action being requested ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up the Scale-up action configuration with the given name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ScaleUpActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("lookupScriptAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Script action being requested ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up the Script action configuration with the given name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ScriptActions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("lookupHeapDumpAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Heap Dump action being requested ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up the Heap Dump action configuration with the given name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "HeapDumpActions");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("lookupThreadDumpAction", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Thread Dump action being requested ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up the Thread Dump action configuration with the given name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ThreadDumpActions");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      mth = WLDFWatchNotificationBean.class.getMethod("lookupWatch", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the policy being requested ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Looks up a policy configuration with the given name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Watches");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFWatchNotificationBean.class.getMethod("lookupNotification", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Looks up an action with the given name.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WLDFWatchNotificationBean.class.getMethod("lookupActions", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("typeName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
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
