package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SNMPLogFilterMBeanImplBeanInfo extends SNMPTrapSourceMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPLogFilterMBean.class;

   public SNMPLogFilterMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPLogFilterMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPLogFilterMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean represents a filter to qualify log messages which are logged to the server logfile. A message must qualify criteria specified as a filter in order to generate a notification. Multiple instances of this MBean can be defined, if needed. If there are multiple instances, a message must qualify atleast one filter to qualify for the server logfile. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPLogFilterMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MessageIds")) {
         getterName = "getMessageIds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageIds";
         }

         currentResult = new PropertyDescriptor("MessageIds", SNMPLogFilterMBean.class, getterName, setterName);
         descriptors.put("MessageIds", currentResult);
         currentResult.setValue("description", "<p>A list of message IDs or ID ranges that cause a WebLogic Server SNMP agent to generate a notification.</p>  <p>If no IDs are specified, this filter selects all message IDs.</p>  <p>Example list: 20,50-100,300</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageSubstring")) {
         getterName = "getMessageSubstring";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageSubstring";
         }

         currentResult = new PropertyDescriptor("MessageSubstring", SNMPLogFilterMBean.class, getterName, setterName);
         descriptors.put("MessageSubstring", currentResult);
         currentResult.setValue("description", "<p>A string that is searched for in the message text. Only messages that contain the string are selected. If a string is not specified, all messages are selected.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SeverityLevel")) {
         getterName = "getSeverityLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSeverityLevel";
         }

         currentResult = new PropertyDescriptor("SeverityLevel", SNMPLogFilterMBean.class, getterName, setterName);
         descriptors.put("SeverityLevel", currentResult);
         currentResult.setValue("description", "<p>The minimum severity of a message that causes a WebLogic Server SNMP agent to generate a notification.</p> ");
         setPropertyDescriptorDefault(currentResult, "Notice");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubsystemNames")) {
         getterName = "getSubsystemNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSubsystemNames";
         }

         currentResult = new PropertyDescriptor("SubsystemNames", SNMPLogFilterMBean.class, getterName, setterName);
         descriptors.put("SubsystemNames", currentResult);
         currentResult.setValue("description", "<p>A list of subsystems whose messages are selected by this log filter. If none are specified, messages from all subsystems are selected.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserIds")) {
         getterName = "getUserIds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserIds";
         }

         currentResult = new PropertyDescriptor("UserIds", SNMPLogFilterMBean.class, getterName, setterName);
         descriptors.put("UserIds", currentResult);
         currentResult.setValue("description", "<p>A list of user IDs that causes a WebLogic Server SNMP agent to generate a notification.</p>  <p>Every message includes the user ID from the security context in which the message was generated.</p>  <p>If the user ID field for a message matches one of the user IDs you specify in the filter, WebLogic Server generates a notification.</p>  <p>If this log filter doesn't specify user IDs, WebLogic Server can generate a notification for messages from all user IDs.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SNMPLogFilterMBean.class.getMethod("addSubsystemName", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("subsystem", "The feature to be added to the SubsystemName attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the SubsystemName attribute of the SNMPLogFilterMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SubsystemNames");
      }

      mth = SNMPLogFilterMBean.class.getMethod("removeSubsystemName", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("subsystem", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SubsystemNames");
      }

      mth = SNMPLogFilterMBean.class.getMethod("addUserId", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userid", "The feature to be added to the UserId attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the UserId attribute of the SNMPLogFilterMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "UserIds");
      }

      mth = SNMPLogFilterMBean.class.getMethod("removeUserId", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "UserIds");
      }

      mth = SNMPLogFilterMBean.class.getMethod("addMessageId", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("messageid", "The feature to be added to the MessageId attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the MessageId attribute of the SNMPLogFilterMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "MessageIds");
      }

      mth = SNMPLogFilterMBean.class.getMethod("removeMessageId", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("messageid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "MessageIds");
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
