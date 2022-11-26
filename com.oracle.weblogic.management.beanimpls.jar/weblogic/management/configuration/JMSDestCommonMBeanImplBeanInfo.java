package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMSDestCommonMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSDestCommonMBean.class;

   public JMSDestCommonMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSDestCommonMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JMSDestCommonMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("obsolete", "9.0.0.0");
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.j2ee.descriptor.wl.DestinationBean} ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class represents common JMS destination and JMS template methods. <p>A JMS destination identifies either a queue (Point-To-Point) or a topic (Pub/Sub) for a JMS server. A JMS template provides an efficient means of defining multiple destinations with similar attribute settings.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JMSDestCommonMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BytesMaximum")) {
         getterName = "getBytesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesMaximum";
         }

         currentResult = new PropertyDescriptor("BytesMaximum", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("BytesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum bytes quota (between <tt>0</tt> and a positive 64-bit integer) that can be stored in this destination. The default value of <i>-1</i> specifies that there is no WebLogic-imposed limit on the number of bytes that can be stored in the destination. However, excessive bytes volume can cause memory saturation, so this value should correspond to the total amount of available system memory relative to the rest of your application load.</p>  <p><b>Range of Values:</b> &gt;= BytesThresholdHigh.</p>  <p>This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p>  <p><i>Note:</i> If a JMS template is used for distributed destination members, then this setting applies only to those specific members and not the distributed destination set as a whole.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesThresholdHigh")) {
         getterName = "getBytesThresholdHigh";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesThresholdHigh";
         }

         currentResult = new PropertyDescriptor("BytesThresholdHigh", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("BytesThresholdHigh", currentResult);
         currentResult.setValue("description", "<p>The upper threshold value that triggers events based on the number of bytes stored in this JMS server. If the number of bytes exceeds this threshold, the following events are triggered :</p>  <ul> <li><b>Log Messages</b>  <p>- A message is logged on the server indicating a high threshold condition.</p> </li>  <li><b>Flow Control</b>  <p>- If flow control is enabled, the destination becomes armed and instructs producers to begin decreasing their message flow.</p> </li> </ul>  <p>A value of -1 specifies that flow control and threshold log messages are disabled for the destination.</p>  <p><b>Range of Values:</b> Between 0 and a positive 64-bit integer; &lt;= BytesMaximum; &gt;BytesThresholdLow.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesThresholdLow")) {
         getterName = "getBytesThresholdLow";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesThresholdLow";
         }

         currentResult = new PropertyDescriptor("BytesThresholdLow", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("BytesThresholdLow", currentResult);
         currentResult.setValue("description", "<p>The lower threshold value (between 0 and a positive 64-bit integer) that triggers events based on the number of bytes stored in this JMS server. If the number of bytes falls below this threshold, the following events are triggered:</p>  <ul> <li><b>Log Messages</b>  <p>- A message is logged on the server indicating that the threshold condition has cleared.</p> </li>  <li><b>Flow Control</b>  <p>- If flow control is enabled, the destination becomes disarmed and instructs producers to begin increasing their message flow.</p> </li> </ul>  <p>A value of -1 specifies that bytes paging, flow control, and threshold log messages are disabled for this JMS server.</p>  <p><b>Range of Values:</b> &lt; BytesThresholdHigh.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeliveryModeOverride")) {
         getterName = "getDeliveryModeOverride";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeliveryModeOverride";
         }

         currentResult = new PropertyDescriptor("DeliveryModeOverride", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("DeliveryModeOverride", currentResult);
         currentResult.setValue("description", "<p>The delivery mode assigned to all messages that arrive at the destination regardless of the DeliveryMode specified by the message producer.</p>  <p>A value of <tt>No-Delivery</tt> specifies that the DeliveryMode will not be overridden.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         setPropertyDescriptorDefault(currentResult, "No-Delivery");
         currentResult.setValue("legalValues", new Object[]{"Persistent", "Non-Persistent", "No-Delivery"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationKeys")) {
         getterName = "getDestinationKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationKeys";
         }

         currentResult = new PropertyDescriptor("DestinationKeys", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("DestinationKeys", currentResult);
         currentResult.setValue("description", "<p>A read-only array of the destination keys of the JMS template or destination. Destination keys define the sort order for messages that arrive on a specific destination. The keys are ordered from most significant to least significant. If more than one key is specified, a key based on the <tt>JMSMessageID</tt> property can only be the last key in the list.</p>  <p><i>Note:</i> If JMSMessageID is not defined in the key, it is implicitly assumed to be the last key and is set as \"ascending\" (first-in, first-out) for the sort order.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeDestinationKey");
         currentResult.setValue("adder", "addDestinationKey");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ErrorDestination")) {
         getterName = "getErrorDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setErrorDestination";
         }

         currentResult = new PropertyDescriptor("ErrorDestination", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("ErrorDestination", currentResult);
         currentResult.setValue("description", "<p>The destination for messages that have reached their redelivery limit, or for expired messages on the destination where the expiration policy is <code>Redirect</code>. If this destination has a template, <code>(none)</code> indicates that the error destination comes from the template. If this destination has no template, <code>(none)</code> indicates that there is no error destination configured.</p>  <p><i>Note:</i> If a redelivery limit is specified, but no error destination is set, then messages that have reached their redelivery limit are simply discarded.</p>  <p><i>Note:</i> The error destination must be a destination that is configured on the local JMS server.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExpirationLoggingPolicy")) {
         getterName = "getExpirationLoggingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExpirationLoggingPolicy";
         }

         currentResult = new PropertyDescriptor("ExpirationLoggingPolicy", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("ExpirationLoggingPolicy", currentResult);
         currentResult.setValue("description", "<p>The policy that defines what information about the message is logged when the Expiration Policy on this destination is set to <tt>Log</tt>.</p>  <p>The valid logging policy values are:</p>  <ul> <li><b>%header%</b>  <p>- All JMS header fields are logged.</p> </li>  <li><b>%properties%</b>  <p>- All user-defined properties are logged.</p> </li>  <li><b>JMSDeliveryTime</b>  <p>- This WebLogic JMS-specific extended header field is logged.</p> </li>  <li><b>JMSRedeliveryLimit</b>  <p>- This WebLogic JMS-specific extended header field is logged.</p> </li>  <li><b><i>foo</i></b>  <p>- Any valid JMS header field or user-defined property is logged.</p> </li> </ul>  <p>When specifying multiple values, enter them as a comma-separated list. The <tt><tt>%header%</tt></tt> and <tt>%properties%</tt> values are <i>not</i> case sensitive. For example, you could use <tt>\"%header%,%properties%\"</tt> for all the JMS header fields and user properties. However, the enumeration of individual JMS header fields and user-defined properties are case sensitive. To enumerate only individual JMS header fields you could use <tt>\"%header, name, address, city, state, zip\"</tt>.N</p>  <p><i>Note:</i> The <tt>JMSMessageID</tt> field is always logged and cannot be turned off. Therefore, if the Expiration Logging Policy is not defined (i.e., null) or is defined as an empty string, then the output to the log file contains only the <tt>JMSMessageID</tt> of the message.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("ExpirationPolicy")) {
         getterName = "getExpirationPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExpirationPolicy";
         }

         currentResult = new PropertyDescriptor("ExpirationPolicy", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("ExpirationPolicy", currentResult);
         currentResult.setValue("description", "<p>The message Expiration Policy uses when an expired message is encountered on a destination.</p>  <p>The valid expiration policies are:</p>  <p><b>None</b> - Same as the Discard policy; expired messages are simply removed from the destination.</p>  <p><b>Discard</b> - Removes expired messages from the messaging system. The removal is not logged and the message is not redirected to another location. If no value is defined for a given destination (i.e., None), then expired messages are discarded.</p>  <p><b>Log</b> - Removes expired messages from the system and writes an entry to the server log file indicating that the messages have been removed from the system. The actual information that is logged is defined by the Expiration Logging Policy.</p>  <p><b>Redirect</b> - Moves expired messages from their current location to the Error Destination defined for the destination. The message retains its body, and all of its properties. The message also retains all of its header fields, but with the following exceptions:</p>  <ul> <li> <p>The destination for the message becomes the error destination.</p> </li>  <li> <p>All property overrides associated with the error destination are applied to the redirected message.</p> </li>  <li> <p>If there is no Time-To-Live Override value for the error destination, then the message receives a new Expiration Time of zero (indicating that it will not expire again</p> </li> </ul>  <p>It is illegal to use the Redirect policy when there is no valid error destination defined for the destination. Similarly, it is illegal to remove the error destination for a destination that is using the Redirect policy.</p>  <p><i>Note:</i> The Maximum Message quota is only enforced for sending new messages. It is ignored when moving messages because of the Redirect policy.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setExpirationPolicy")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "Discard");
         currentResult.setValue("legalValues", new Object[]{"Discard", "Log", "Redirect"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumMessageSize")) {
         getterName = "getMaximumMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumMessageSize";
         }

         currentResult = new PropertyDescriptor("MaximumMessageSize", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("MaximumMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum size of a message in bytes that will be accepted from producers on this JMS server. The message size includes the message body, any user-defined properties, and the user-defined JMS header fields: <tt>JMSCorrelationID</tt> and <tt>JMSType</tt>. Producers sending messages that exceed the configured maximum message size for the JMS server receive a <tt>ResourceAllocationException</tt>.</p>  <p><b>Range of Values:</b> Between <tt>0</tt> and a positive 32-bit integer.</p>  <p>The maximum message size is only enforced for the initial production of a message. Messages that are redirected to an error destination or forwarded to a member of a distributed destination are not checked for size. For instance, if a destination and its corresponding error destination are configured with a maximum message size of 128K bytes and 64K bytes, respectively, a message of 96K bytes could be redirected to the error destination (even though it exceeds the Range of Values: the 64K byte maximum), but a producer could not directly send the 96K byte message to the error destination.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesMaximum")) {
         getterName = "getMessagesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesMaximum";
         }

         currentResult = new PropertyDescriptor("MessagesMaximum", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("MessagesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum message quota (total amount of messages) that can be stored in this JMS server. The default value of <i>-1</i> specifies that there is no WebLogic-imposed limit on the number of messages that can be stored. However, excessive message volume can cause memory saturation, so this value should correspond to the total amount of available system memory relative to the rest of your application load.</p>  <p><b>Range of Values:</b> &gt;= MessagesThresholdHigh.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p>  <p><i>Note:</i> If a JMS template is used for distributed destination members, then this setting applies only to those specific members and not the distributed destination set as a whole.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesThresholdHigh")) {
         getterName = "getMessagesThresholdHigh";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesThresholdHigh";
         }

         currentResult = new PropertyDescriptor("MessagesThresholdHigh", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("MessagesThresholdHigh", currentResult);
         currentResult.setValue("description", "<p>The upper threshold value that triggers events based on the number of messages stored in this JMS server. If the number of messages exceeds this threshold, the following events are triggered:</p>  <ul> <li><b>Log Messages</b>  <p>- A message is logged on the server indicating a high threshold condition.</p> </li>  <li><b>Flow Control</b>  <p>- If flow control is enabled, the destination becomes armed and instructs producers to begin decreasing their message flow.</p> </li> </ul>  <p>A value of -1 specifies that bytes paging, flow control, and threshold log messages are disabled for this JMS server.</p>  <p><b>Range of Values:</b> Between 0 and a positive 64-bit integer; &lt;= MessagesMaximum; &gt;MessagesThresholdLow.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesThresholdLow")) {
         getterName = "getMessagesThresholdLow";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesThresholdLow";
         }

         currentResult = new PropertyDescriptor("MessagesThresholdLow", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("MessagesThresholdLow", currentResult);
         currentResult.setValue("description", "<p>The lower threshold value that triggers events based on the number of messages stored in this JMS server. If the number of messages falls below this threshold, the following events are triggered:</p>  <ul> <li><b>Log Messages</b>  <p>- A message is logged on the server indicating that the threshold condition has cleared.</p> </li>  <li><b>Flow Control</b>  <p>- If flow control is enabled, the destination becomes disarmed and instructs producers to begin increasing their message flow.</p> </li> </ul>  <p>A value of -1 specifies that bytes paging, flow control, and threshold log messages are disabled for this JMS server.</p>  <p><b>Range of Values:</b> Between 0 and a positive 64-bit integer; &lt; MessagesThresholdHigh.</p>  <p>This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PriorityOverride")) {
         getterName = "getPriorityOverride";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPriorityOverride";
         }

         currentResult = new PropertyDescriptor("PriorityOverride", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("PriorityOverride", currentResult);
         currentResult.setValue("description", "<p>The priority assigned to all messages that arrive at the destination, regardless of the Priority specified by the message producer. The default value (-1) specifies that the destination will not override the Priority set by the message producer.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(9));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RedeliveryDelayOverride")) {
         getterName = "getRedeliveryDelayOverride";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedeliveryDelayOverride";
         }

         currentResult = new PropertyDescriptor("RedeliveryDelayOverride", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("RedeliveryDelayOverride", currentResult);
         currentResult.setValue("description", "<p>The delay, in milliseconds, before rolled back or recovered messages are redelivered, regardless of the RedeliveryDelay specified by the consumer and/or connection factory. Redelivered queue messages are put back into their originating destination; redelivered topic messages are put back into their originating subscription. The default value (-1) specifies that the destination will not override the RedeliveryDelay setting specified by the consumer and/or connection factory.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p>  <p><i>Note:</i> Changing the RedeliveryDelayOverride only affects future rollbacks and recovers, it does not affect rollbacks and recovers that have already occurred.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RedeliveryLimit")) {
         getterName = "getRedeliveryLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedeliveryLimit";
         }

         currentResult = new PropertyDescriptor("RedeliveryLimit", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("RedeliveryLimit", currentResult);
         currentResult.setValue("description", "<p>The number of redelivery tries (between <tt>0</tt> and a positive 32-bit integer) a message can have before it is moved to the error destination. This setting overrides any redelivery limit set by the message sender. If the redelivery limit is configured, but no error destination is configured, then persistent and non-persistent messages are simply dropped (deleted) when they reach their redelivery limit.</p>  <p>A value of <tt>-1</tt> means that this value is inherited from the JMS template, if one is configured. If no JMS template is configured, then <tt>-1</tt> means that there is no override.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; previously sent messages continue to use their original redelivery limit.</p>  <p><i>Note:</i> The number of times a message has been redelivered is not persisted. This means that after a restart, the number of delivery attempts on each message is reset to zero.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeToDeliverOverride")) {
         getterName = "getTimeToDeliverOverride";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeToDeliverOverride";
         }

         currentResult = new PropertyDescriptor("TimeToDeliverOverride", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("TimeToDeliverOverride", currentResult);
         currentResult.setValue("description", "<p>The default delay, either in milliseconds or as a schedule, between when a message is produced and when it is made visible on its target destination, regardless of the delivery time specified by the producer and/or connection factory. The default value (<tt>-1</tt>) specifies that the destination will not override the TimeToDeliver setting specified by the producer and/or connection factory. The TimeToDeliverOverride can be specified either as a long or as a schedule.</p>  <p><i>Note:</i> Changing the TimeToDeliverOverride only affects future message delivery, it does not affect message delivery of already produced messages.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.jms.extensions.Schedule")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "-1");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeToLiveOverride")) {
         getterName = "getTimeToLiveOverride";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeToLiveOverride";
         }

         currentResult = new PropertyDescriptor("TimeToLiveOverride", JMSDestCommonMBean.class, getterName, setterName);
         descriptors.put("TimeToLiveOverride", currentResult);
         currentResult.setValue("description", "<p>The time-to-live assigned to all messages that arrive at this topic, regardless of the TimeToLive value specified by the message producer. The default value (<tt>-1</tt>) specifies that this setting will not override the TimeToLive setting specified by the message producer.</p>  <p><b>Range of Values:</b> Between 0 and a positive 64-bit integer.</p>  <p><i>Note:</i> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSDestCommonMBean.class.getMethod("addDestinationKey", JMSDestinationKeyMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destinationKey", "a reference to JMSDestinationKeyMBean ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Add a destination key to the JMS template or destination. Destination keys define the sort order for messages that arrive on a specific destination. The keys are ordered from most significant to least significant. If more than one key is specified, a key based on the <tt>JMSMessageID</tt> property can only be the last key in the list.</p>  <p><i>Note:</i> If JMSMessageID is not defined in the key, it is implicitly assumed to be the last key and is set as \"ascending\" (first-in, first-out) for the sort order.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DestinationKeys");
      }

      mth = JMSDestCommonMBean.class.getMethod("removeDestinationKey", JMSDestinationKeyMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destinationKey", "a reference to  JMSDestinationKeyMBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Remove a destination key from the JMS template or destination. Destination keys define the sort order for messages that arrive on a specific destination. The keys are ordered from most significant to least significant. If more than one key is specified, a key based on the <tt>JMSMessageID</tt> property can only be the last key in the list.</p>  <p><i>Note:</i> If JMSMessageID is not defined in the key, it is implicitly assumed to be the last key and is set as \"ascending\" (first-in, first-out) for the sort order.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DestinationKeys");
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
