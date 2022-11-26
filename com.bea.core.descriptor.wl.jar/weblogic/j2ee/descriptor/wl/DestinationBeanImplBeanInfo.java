package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DestinationBeanImplBeanInfo extends TargetableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DestinationBean.class;

   public DestinationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DestinationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DestinationBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>This bean contains all the attributes of destinations that are common between queues and topics.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DestinationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AttachSender")) {
         getterName = "getAttachSender";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAttachSender";
         }

         currentResult = new PropertyDescriptor("AttachSender", DestinationBean.class, getterName, setterName);
         descriptors.put("AttachSender", currentResult);
         currentResult.setValue("description", "<p>Specifies whether messages landing on this destination should attach the credential of the sending user. You should consult the JMSXUserID documentation in <i>Programming WebLogic JMS</i> before using this feature.</p>  <ul> <li><b>Supports</b> The JMSXUserID property is set with the security principal of the sending user if requested. The sender can request its identity to be attached to its messages by using a connection factory with the AttachJMSXUserID security property enabled.</li>  <li><b>Never</b> The JMSXUserID property is never set with the security principal of the sending user no matter how the AttachJMSXUserID property is configured on connection factories.</li>  <li><b>Always</b> The JMSXUserID property is always set with the security principal of the sending user no matter how the AttachJMSXUserID property is configured on connection factories.</li> </ul>  <p>This attribute is dynamically configurable. A dynamic change of this attribute will affect only messages received after the update has been made.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "supports");
         currentResult.setValue("legalValues", new Object[]{"supports", "never", "always"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeliveryFailureParams")) {
         getterName = "getDeliveryFailureParams";
         setterName = null;
         currentResult = new PropertyDescriptor("DeliveryFailureParams", DestinationBean.class, getterName, setterName);
         descriptors.put("DeliveryFailureParams", currentResult);
         currentResult.setValue("description", "<p><code>DeliveryFailureParams</code> control what should happen to messages when failures occur. They allow the adminstrator to control error destinations, logging and other actions that might be taken when a message can not be delivered or when other failures are detected.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeliveryParamsOverrides")) {
         getterName = "getDeliveryParamsOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("DeliveryParamsOverrides", DestinationBean.class, getterName, setterName);
         descriptors.put("DeliveryParamsOverrides", currentResult);
         currentResult.setValue("description", "<p>Many delivery parameters can be set by the JMS client. Setting an override causes the current value of a delivery parameter to be ignored and replaced by the value set here.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationKeys")) {
         getterName = "getDestinationKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationKeys";
         }

         currentResult = new PropertyDescriptor("DestinationKeys", DestinationBean.class, getterName, setterName);
         descriptors.put("DestinationKeys", currentResult);
         currentResult.setValue("description", "<p>The list of potential destination keys for sorting the messages that arrive on a JMS destination.</p>  <p>The keys are ordered from most significant to least significant. If more than one key is specified, a key based on the JMSMessageID can only be the last key in the list.</p>  <p><b>Note:</b> If JMSMessageID is not defined in the key, it is implicitly assumed to be the last key and is set as \"Ascending\" (FIFO) for the sort order.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IncompleteWorkExpirationTime")) {
         getterName = "getIncompleteWorkExpirationTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncompleteWorkExpirationTime";
         }

         currentResult = new PropertyDescriptor("IncompleteWorkExpirationTime", DestinationBean.class, getterName, setterName);
         descriptors.put("IncompleteWorkExpirationTime", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum length of time, in milliseconds, before undelivered messages in an incomplete UOW are expired. Such messages will then follow the expiration policy defined for undeliverable messages.</p>  <p><b>Note:</b> An error destination for UOW messages cannot be configured with a Unit-of-Work Handling Policy of <b>Single Message Delivery</b> value.</p>  <p>This attribute is effective only if the Unit-of-Work Handling Policy is set to <b>Single Message Delivery</b> value. A value of -1 means that UOW messages will never expire.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSCreateDestinationIdentifier")) {
         getterName = "getJMSCreateDestinationIdentifier";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJMSCreateDestinationIdentifier";
         }

         currentResult = new PropertyDescriptor("JMSCreateDestinationIdentifier", DestinationBean.class, getterName, setterName);
         descriptors.put("JMSCreateDestinationIdentifier", currentResult);
         currentResult.setValue("description", "<p>A reference name for a destination or a member of a distributed destination that provides a way to lookup that destination without JNDI using <code>javax.jms.Session createQueue </code> or <code>createTopic</code>. See \"How to Lookup a Destination\" in  <i>Programming JMS</i> for more information on how to use this attribute.</p>  <p>This name must be unique within the scope of the JMS server to which this destination is targeted. However, it does not need to be unique within the scope of an entire JMS module or WebLogic Cluster. For example, two queues can have the same destination name as long as those queues are targeted to different JMS servers.</p>  <p><b>Note:</b> Since this name must be unique within the scope of a JMS server, verify whether other JMS modules may contain destination names that conflict with this name. It is the responsibility of the deployer to resolve the destination names targeted to JMS servers.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("declaration", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIName";
         }

         currentResult = new PropertyDescriptor("JNDIName", DestinationBean.class, getterName, setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", "<p>The global JNDI name used to look up the destination within the JNDI namespace.</p>  <p>In a clustered environment, this name is propagated to the entire cluster. If you want the JNDI name to be bound only on the local server, and not propagated to the rest of the cluster, then use the <code>Local JNDI Name</code> parameter.</p>  <p>If not specified, the destination name will not be advertised through the global JNDI namespace.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoadBalancingPolicy")) {
         getterName = "getLoadBalancingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadBalancingPolicy";
         }

         currentResult = new PropertyDescriptor("LoadBalancingPolicy", DestinationBean.class, getterName, setterName);
         descriptors.put("LoadBalancingPolicy", currentResult);
         currentResult.setValue("description", "<p>Determines how messages are distributed to the members of this destination.</p>  <p>Choose from the following distribution techniques:</p> <ul> <li><b>Round-Robin</b> The system maintains an ordering of physical topic members within the set by distributing the messaging load across the topic members one at a time in the order that they are defined in the configuration file. Each WebLogic Server instance maintains an identical ordering, but may be at a different point within the ordering. If weights are assigned to any of the topic members in the set, then those members appear multiple times in the ordering.</li>  <li><b>Random</b> The weight assigned to the topic members is used to compute a weighted distribution for the members of the set. The messaging load is distributed across the topic members by pseudo-randomly accessing the distribution. In the short run, the load will not be directly proportional to the weight. In the long run, the distribution will approach the limit of the distribution. A pure random distribution can be achieved by setting all the weights to the same value, which is typically set to 1.</li> </ul>  <p> Note: This attribute is ignored by standalone/singleton Queues and Topics, it only applies to distributed destinations. </p> ");
         setPropertyDescriptorDefault(currentResult, "Round-Robin");
         currentResult.setValue("legalValues", new Object[]{"Round-Robin", "Random"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalJNDIName")) {
         getterName = "getLocalJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalJNDIName";
         }

         currentResult = new PropertyDescriptor("LocalJNDIName", DestinationBean.class, getterName, setterName);
         descriptors.put("LocalJNDIName", currentResult);
         currentResult.setValue("description", "<p>The local JNDI name used to look up the destination within the JNDI namespace of the server where the destination resource is targeted. In a clustered environment, this name is bound only on the local server instance and is not propagated to the rest of the cluster.</p>  <p>A destination can have both a local JNDI name and a (global) JNDI name.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumMessageSize")) {
         getterName = "getMaximumMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumMessageSize";
         }

         currentResult = new PropertyDescriptor("MaximumMessageSize", DestinationBean.class, getterName, setterName);
         descriptors.put("MaximumMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum size of a message that is accepted from producers on this destination.</p>  <p>The message size includes the message body, any user-defined properties, and the user-defined JMS header fields: <code>JMSCorrelationID</code> and <code>JMSType</code>. Producers sending messages that exceed the configured maximum message size for the destination receive a <code>ResourceAllocationException</code>.</p>  <p>The maximum message size is only enforced for the initial production of a message. Messages that are redirected to an error destination or forwarded to a member of a distributed destination are not checked for size. For instance, if a destination and its corresponding error destination are configured with a maximum message size of 128K bytes and 64K bytes, respectively, a message of 96K bytes could be redirected to the error destination (even though it exceeds the 64K byte maximum), but a producer could not directly send the 96K byte message to the error destination.</p>  <p>This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageLoggingParams")) {
         getterName = "getMessageLoggingParams";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageLoggingParams", DestinationBean.class, getterName, setterName);
         descriptors.put("MessageLoggingParams", currentResult);
         currentResult.setValue("description", "<p>These parameters control how the WebLogic Server performs message logging.</p>  <p>They allow the adminstrator to configure the server to change message logging when a message life cycle changes are detected.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagingPerformancePreference")) {
         getterName = "getMessagingPerformancePreference";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagingPerformancePreference";
         }

         currentResult = new PropertyDescriptor("MessagingPerformancePreference", DestinationBean.class, getterName, setterName);
         descriptors.put("MessagingPerformancePreference", currentResult);
         currentResult.setValue("description", "<p>Controls how long destinations are willing to wait to create full batches of available messages (if at all) for delivery to consumers. At the minimum value, batching is disabled; at the default value, less-than-full batches will not wait and are delivered immediately with currently available messages; tuning higher than the default value controls the maximum wait time for additional messages before less-than-full batches are delivered to consumers.</p>  <p>The maximum message count of a full batch is controlled by the JMS connection factory's Messages Maximum setting.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(25));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Quota")) {
         getterName = "getQuota";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setQuota";
         }

         currentResult = new PropertyDescriptor("Quota", DestinationBean.class, getterName, setterName);
         descriptors.put("Quota", currentResult);
         currentResult.setValue("description", "<p>A Quota controls the allotment of system resources available to destinations. For example, the number of bytes a destination is allowed to store can be configured with a Quota.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFExportPolicy")) {
         getterName = "getSAFExportPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSAFExportPolicy";
         }

         currentResult = new PropertyDescriptor("SAFExportPolicy", DestinationBean.class, getterName, setterName);
         descriptors.put("SAFExportPolicy", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a user can send messages to a destination using Store-and-Forward.</p>  <ul> <li><b>All</b> All users can send messages to this destination using Store-and-Forward.</li>  <li><b>None</b> Remote users can not send messages to a destination using Store-and-Forward.</li> </ul>  <p>This attribute is dynamically configurable. A dynamic change of this attribute will affect only messages sent after the update has been made.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "All");
         currentResult.setValue("legalValues", new Object[]{"All", "None"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Template")) {
         getterName = "getTemplate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTemplate";
         }

         currentResult = new PropertyDescriptor("Template", DestinationBean.class, getterName, setterName);
         descriptors.put("Template", currentResult);
         currentResult.setValue("description", "<p>The JMS template from which the destination is derived. A template provides an efficient means of defining multiple destinations with similar configuration values.</p>  <p>If a JMS template is specified, destination parameters that are set to their default values will instead inherit their values from the JMS template at run-time. However, if a JMS template is not defined, then the configuration values for the destination must be specified as part of the destination.</p>  <p>Although you can dynamically modify the configuration of a JMS template, the configuration values on a destination are static.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Thresholds")) {
         getterName = "getThresholds";
         setterName = null;
         currentResult = new PropertyDescriptor("Thresholds", DestinationBean.class, getterName, setterName);
         descriptors.put("Thresholds", currentResult);
         currentResult.setValue("description", "<p>A threshold is an action point that must be exceeded in order to produce a given effect. These action points may cause logging, flow control, or other actions, as defined by the specific points whose values have been exceeded.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnitOfOrderRouting")) {
         getterName = "getUnitOfOrderRouting";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnitOfOrderRouting";
         }

         currentResult = new PropertyDescriptor("UnitOfOrderRouting", DestinationBean.class, getterName, setterName);
         descriptors.put("UnitOfOrderRouting", currentResult);
         currentResult.setValue("description", "<p>Determines how a distributed destination member is selected as the destination for a message that is part of a unit-of-order. <b>Hash</b>, indicates that a message producer computes the member destination from the <code>hashCode</code> of the unit-of-order. <b>PathService</b> indicates that the configured Path Service determines the member destination.</p>  <p>Unit-of-Order Routing can be set programmatically with WLProducer, or administratively on the connection factory or destination. </p>  <p>Unit of Order Routing is not dynamically configurable.</p>  <p> Note: This attribute is ignored by standalone/singleton Queues and Topics, it only applies to distributed destinations. </p> ");
         setPropertyDescriptorDefault(currentResult, "Hash");
         currentResult.setValue("declaration", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnitOfWorkHandlingPolicy")) {
         getterName = "getUnitOfWorkHandlingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnitOfWorkHandlingPolicy";
         }

         currentResult = new PropertyDescriptor("UnitOfWorkHandlingPolicy", DestinationBean.class, getterName, setterName);
         descriptors.put("UnitOfWorkHandlingPolicy", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Unit-of-Work (UOW) feature is enabled for this destination. A UOW is a set of messages that are processed as a single unit.</p> <ul> <li> <b>Pass-Through</b> By default, this destination does not treat messages as part of a UOW.</li> <li> <b>Single Message Delivery</b> This value should only be selected if UOW consumers are receiving messages on this destination. When selected, UOW messages are formed into a list and are consumed as an <code>ObjectMessage</code> containing the list.</li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "PassThrough");
         currentResult.setValue("legalValues", new Object[]{"PassThrough", "SingleMessageDelivery"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumptionPausedAtStartup")) {
         getterName = "isConsumptionPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsumptionPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("ConsumptionPausedAtStartup", DestinationBean.class, getterName, setterName);
         descriptors.put("ConsumptionPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether consumption is paused on a destination at startup.</p>  <ul> <li><b>default</b> If a JMS template is specified, then this value inherits the template's Consumption Paused At Startup value. If no JMS template is configured for the destination, then the Default value is equivalent to <b>false</b>.</li>  <li><b>false</b> Consumption Paused is explicitly disabled for this destination. </li>  <li><b>true</b> Consumption Paused is explicitly turned on for this destination.</li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultUnitOfOrder")) {
         getterName = "isDefaultUnitOfOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultUnitOfOrder";
         }

         currentResult = new PropertyDescriptor("DefaultUnitOfOrder", DestinationBean.class, getterName, setterName);
         descriptors.put("DefaultUnitOfOrder", currentResult);
         currentResult.setValue("description", "<p>Specifies whether WebLogic Server creates a system-generated unit-of-order name based on the domain, JMS server, and destination name. Any message arriving at this destination that does not already belong to a unit-of-order is assigned this default name.</p>  <p>This field is recommended for advanced use. Generally, it is  recommended to set a message unit-of-order using application calls or connection factory configuration. When relying on a destination default unit order to enforce ordering with a distributed destination, the application must be aware that unit-of-order routing doesn't apply. Instead the application must specifically ensure that any particular set of messages that must be processed in order are all sent to the same specific member destination even in the event of process failures or service migration. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InsertionPausedAtStartup")) {
         getterName = "isInsertionPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInsertionPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("InsertionPausedAtStartup", DestinationBean.class, getterName, setterName);
         descriptors.put("InsertionPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether new message insertion is paused on a destination at startup.</p>  <ul> <li><b>default</b> If a JMS template is specified, then this value inherits the template's Insertion Paused At Startup value. If no JMS template is configured for the destination, then the Default value is equivalent to <b>false</b>.</li>  <li><b>false</b> Insertion Paused is explicitly disabled for this destination.</li>  <li><b>true</b> Insertion Paused is explicitly turned on for this destination.</li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProductionPausedAtStartup")) {
         getterName = "isProductionPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProductionPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("ProductionPausedAtStartup", DestinationBean.class, getterName, setterName);
         descriptors.put("ProductionPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether new message production is paused on a destination at startup.</p>  <ul> <li><b>default</b> If a JMS template is specified, then this value inherits the template's Production Paused At Startup value. If no JMS template is configured for the destination, then the Default value is equivalent to <b>false</b>.</li>  <li><b>false</b> Production Paused is explicitly disabled for this destination.</li>  <li><b>true</b> Production Paused is explicitly turned on for this destination.</li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DestinationBean.class.getMethod("addDestinationKey", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destinationKey", "The string to be added as a key. The string is added added to the end of the list of keys. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a destination key.</p>  <p>Adds a string to the list of keys to be used for sorting destinations. The string given will be added to the end of the list.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DestinationKeys");
      }

      mth = DestinationBean.class.getMethod("removeDestinationKey", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destinationKey", "The string to be removed from the list of sorting keys. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a destination key.</p>  <p>If the given string is in the list of keys used for sorting destinations, it will be removed from the list.</p> ");
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
