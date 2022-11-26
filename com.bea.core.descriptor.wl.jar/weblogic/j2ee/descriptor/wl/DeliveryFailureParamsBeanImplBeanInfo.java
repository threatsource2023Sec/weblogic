package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DeliveryFailureParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeliveryFailureParamsBean.class;

   public DeliveryFailureParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeliveryFailureParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("These parameters control what should happen to messages when failures occur. They allow the adminstrator to control error destinations, logging and other actions that might be taken when a message can not be delivered or when other failures are detected ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ErrorDestination")) {
         getterName = "getErrorDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setErrorDestination";
         }

         currentResult = new PropertyDescriptor("ErrorDestination", DeliveryFailureParamsBean.class, getterName, setterName);
         descriptors.put("ErrorDestination", currentResult);
         currentResult.setValue("description", "<p>The name of the target error destination for messages that have expired or reached their redelivery limit. If no error destination is configured, then such messages are simply dropped. If a message has expired or reached its redelivery limit, and the Expiration Policy is set to Redirect, then the message is moved to the specified Error Destination.</p>  <p>For standalone destinations, an error destination must be another standalone destination that is targeted to the same JMS server as the destinations for which the error destination is defined. For uniform distributed destinations (UDDs), the error destination must be another UDD that shares the same subdeployment (i.e., targets) as the current UDD.</p>  <p>This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExpirationLoggingPolicy")) {
         getterName = "getExpirationLoggingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExpirationLoggingPolicy";
         }

         currentResult = new PropertyDescriptor("ExpirationLoggingPolicy", DeliveryFailureParamsBean.class, getterName, setterName);
         descriptors.put("ExpirationLoggingPolicy", currentResult);
         currentResult.setValue("description", "<p>The policy that defines what information about the message is logged when the Expiration Policy is set to Log. The valid logging policy values are:</p> <ul> <li> <b>%header%</b> - All JMS header fields are logged.</li> <li> <b>%properties%</b> - All user-defined properties are logged.</li> <li> <b>JMSDeliveryTime</b> - This WebLogic JMS-specific extended header field is logged.</li> <li> <b>JMSRedeliveryLimit</b> - This WebLogic JMS-specific extended header field is logged.</li> <li> <b><i>foo</i> </b> - Any valid JMS header field or user-defined property is logged.</li> </ul>  <p>When specifying multiple values, enter them as a comma-separated list. The <code>%header%</code> and <code>%properties% </code> values are <i>not</i> case sensitive. For example, you could use <code>\"%header%,%properties%\"</code> for all the JMS header fields and user properties. However, the enumeration of individual JMS header fields and user-defined properties are case sensitive. To enumerate only individual JMS header fields you could use <code>\"%header, name, address, city, state, zip\"</code>.</p>  <p><b>Note:</b> The <code>JMSMessageID</code> field is always logged and cannot be turned off. Therefore, if the Expiration Logging Policy is not defined (i.e., null) or is defined as an empty string, then the output to the log file contains only the <code>JMSMessageID</code> of the message. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExpirationPolicy")) {
         getterName = "getExpirationPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExpirationPolicy";
         }

         currentResult = new PropertyDescriptor("ExpirationPolicy", DeliveryFailureParamsBean.class, getterName, setterName);
         descriptors.put("ExpirationPolicy", currentResult);
         currentResult.setValue("description", "<p>The message Expiration Policy to use when an expired message is encountered on a destination. The valid expiration policies are:</p>  <p><b>None</b> - Same as the Discard policy; expired messages are simply removed from the destination.</p>  <p><b>Discard</b> - Removes expired messages from the messaging system. The removal is not logged and the message is not redirected to another location. If no value is defined for a given destination (i.e., None), then expired messages are discarded.</p>  <p><b>Log</b> - Removes expired messages from the system and writes an entry to the server log file indicating that the messages have been removed from the system. The actual information that is logged is defined by the Expiration Logging Policy.</p>  <p><b>Redirect</b> - Moves expired messages from their current location to the Error Destination defined for the destination. The message retains its body, and all of its properties. The message also retains all of its header fields, but with the following exceptions:</p> <ul> <li> The destination for the message becomes the error destination.</li> <li> All property overrides associated with the error destination are applied to the redirected message.</li> <li> If there is no Time-To-Live Override value for the error destination, then the message receives a new Expiration Time of zero (indicating that it will not expire again).</li> </ul>  <p>It is illegal to use the Redirect policy when there is no valid error destination defined for the destination. Similarly, it is illegal to remove the error destination for a destination that is using the Redirect policy.</p>  <b>Note:</b> The Maximum Message quota is only enforced for sending new messages. It is ignored when moving messages because of the Redirect policy. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "Discard");
         currentResult.setValue("legalValues", new Object[]{"Discard", "Log", "Redirect"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RedeliveryLimit")) {
         getterName = "getRedeliveryLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedeliveryLimit";
         }

         currentResult = new PropertyDescriptor("RedeliveryLimit", DeliveryFailureParamsBean.class, getterName, setterName);
         descriptors.put("RedeliveryLimit", currentResult);
         currentResult.setValue("description", "<p>The number of redelivery tries a message can have before it is moved to the error destination. This setting overrides any redelivery limit set by the message sender. If the redelivery limit is configured, but no error destination is configured, then persistent and non-persistent messages are simply dropped (deleted) when they reach their redelivery limit.</p>  <p>The default value (-1) specifies that the destination will not override the message sender's redelivery limit setting. </p>  <p><b>Note:</b> WebLogic Server supports the JMSXDeliveryCount message property, which specifies the number of message delivery attempts, where the first attempt is 1, the next attempt is 2, and so on. WebLogic Server makes a best effort to persist the delivery count, so that the delivery count does not reset back to 1 after a server reboot.</p>  <p>This attribute is dynamically configurable, but only incoming messages are impacted; previously sent messages continue to use their original redelivery limit.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TemplateBean")) {
         getterName = "getTemplateBean";
         setterName = null;
         currentResult = new PropertyDescriptor("TemplateBean", DeliveryFailureParamsBean.class, getterName, setterName);
         descriptors.put("TemplateBean", currentResult);
         currentResult.setValue("description", "This is used to find the template bean for this destination ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
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
      Method mth = DeliveryFailureParamsBean.class.getMethod("findSubDeploymentName");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This is used to find the sub-deployment-name for this destination. ");
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
