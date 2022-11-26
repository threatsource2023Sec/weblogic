package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ClientParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ClientParamsBean.class;

   public ClientParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ClientParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>This package provides parameters that govern JMS server behavior with regard to a client. For example, setting the client Id when using a particular connection factory.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ClientParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AcknowledgePolicy")) {
         getterName = "getAcknowledgePolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAcknowledgePolicy";
         }

         currentResult = new PropertyDescriptor("AcknowledgePolicy", ClientParamsBean.class, getterName, setterName);
         descriptors.put("AcknowledgePolicy", currentResult);
         currentResult.setValue("description", "<p>Acknowledge policy for non-transacted sessions that use the <code>CLIENT_ACKNOWLEDGE</code> mode. <code>All</code> indicates that calling acknowledge on a message acknowledges <i>all</i> unacknowledged messages received on the session. <code>Previous</code> specifies that calling acknowledge on a message acknowledges only unacknowledged messages up to, and including, the given message.</p>  <p>This parameter works around a change in the JMS specification and only applies to implementations that use the <code>CLIENT_ACKNOWLEDGE</code> acknowledge mode for a non-transacted session. Specifically, the specification allowed users to acknowledge all messages before and including the message being acknowledged. The specification was changed so that acknowledging any message acknowledges all messages ever received (even those received after the message being acknowledge). </p>  <p><code>ACKNOWLEDGE_PREVIOUS</code> retains the old behavior (acknowledge all message up to and including the message being acknowledged). Whereas, <code>ACKNOWLEDGE_ALL</code> yields the new behavior, where all messages received by the given session are acknowledged regardless of which message is being used to effect the acknowledge.</p> <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, "All");
         currentResult.setValue("legalValues", new Object[]{"All", "Previous"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientId")) {
         getterName = "getClientId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientId";
         }

         currentResult = new PropertyDescriptor("ClientId", ClientParamsBean.class, getterName, setterName);
         descriptors.put("ClientId", currentResult);
         currentResult.setValue("description", "<p>An optional Client ID for applications that use this JMS connection factory. If the Client ID Policy is set to <code>Restricted </code>(the default), then configuring a Client ID on the connection factory prevents more than one JMS client from using a connection from this factory.</p> <p>This attribute is rarely configured and should normally be left at the default (blank), as JMS application programs can set their Client IDs dynamically using the standard JMS APIs <code>javax.jms.JMSContext.setClientID()</code> or <code>javax.jms.Connection.setClientID()</code>. The JMS application message processing containers (such as MDBs) normally make the Client ID configurable as part of container configuration.</p> <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientIdPolicy")) {
         getterName = "getClientIdPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientIdPolicy";
         }

         currentResult = new PropertyDescriptor("ClientIdPolicy", ClientParamsBean.class, getterName, setterName);
         descriptors.put("ClientIdPolicy", currentResult);
         currentResult.setValue("description", "<p>The Client ID Policy indicates whether more than one JMS connection can use the same Client ID.</p>  <p>The valid values are:</p> <ul> <li><code>CLIENT_ID_POLICY_RESTRICTED</code> - The default. Only one connection that uses this policy can exist in a cluster at any given time for a particular Client ID (if a connection already exists with a given Client ID, attempts to create new connections using this policy with the same Client ID fail with an exception).</li>  <li><code>CLIENT_ID_POLICY_UNRESTRICTED</code>  - Connections created using this policy can specify any Client ID, even when other restricted or unrestricted connections already use the same Client ID. The Unrestricted Client ID capability was added in WebLogic 10.3.4 (11gR1PS3). </li> </ul> <p>Notes:</p> <ul><li>WebLogic JMS applications can override the Client ID Policy specified on the connection factory configuration by casting a <code>javax.jms.JMSContext</code> instance to <code>weblogic.jms.extensions.WLJMSContext</code> or a <code>javax.jms.Connection</code> instance to <code>weblogic.jms.extension.WLConnection</code> and calling <code>setClientID(String clientID, String clientIDPolicy)</code>.</li> <li>Two connections with the same Client ID are treated as two different independent connections if they have a different Client ID Policy. This means a cluster can host a single Restricted Client ID Policy connection, and also concurrently host multiple Unrestricted Client ID Policy connections that have the same Client ID as the Restricted connection.</li> <li>Two durable subscriptions with the same Client ID and Subscription Name are treated as two different independent subscriptions if they have a different Client ID Policy. Similarly, two Sharable non-durable subscriptions with the same Client Id are treated as two different independent subscriptions if they have a different Client ID Policy.</li> <li>Durable subscriptions created using an Unrestricted  Client Id must be unsubscribed using the <code>weblogic.jms.extensions.WLJMSContext.unsubscribe(Topic topic, String name)</code> instead of <code>javax.jms.JMSContext.unsubscribe(String name)</code> or using the <code>weblogic.jms.extensions.WLSession.unsubscribe(Topic topic, String name)</code>, instead of <code>javax.jms.Session.unsubscribe(String name)</code>.</li> </ul> <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, JMSConstants.CLIENT_ID_POLICY_RESTRICTED);
         currentResult.setValue("legalValues", new Object[]{JMSConstants.CLIENT_ID_POLICY_RESTRICTED, JMSConstants.CLIENT_ID_POLICY_UNRESTRICTED});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesMaximum")) {
         getterName = "getMessagesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesMaximum";
         }

         currentResult = new PropertyDescriptor("MessagesMaximum", ClientParamsBean.class, getterName, setterName);
         descriptors.put("MessagesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of messages that can exist for an asynchronous session and that have not yet been passed to the message listener. When the Synchronous Prefetch Mode is enabled, this value also affects synchronous sessions with a message consumer that will prefetch messages in one server access.</p>  <p>A value of -1 indicates that there is no limit on the number of messages. In this case, however, the limit is set to the amount of remaining virtual memory.</p> <p> When the number of messages reaches the MessagesMaximum value:</p> <ul> <li>For multicast sessions, new messages are discarded according the policy specified by the <code>OverrunPolicy</code> parameter and a DataOverrunException is thrown.</li>  <li>For non-multicast sessions, new messages are flow-controlled, or retained on the server until the application can accommodate the messages.</li> </ul> <p> For multicast sessions, when a connection is stopped, messages will continue to be delivered, but only until the MessagesMaximum value is reached. Once this value is reached, messages will be discarded based on the Overrun policy.</p> <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSynchronousPrefetchMode")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastOverrunPolicy")) {
         getterName = "getMulticastOverrunPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastOverrunPolicy";
         }

         currentResult = new PropertyDescriptor("MulticastOverrunPolicy", ClientParamsBean.class, getterName, setterName);
         descriptors.put("MulticastOverrunPolicy", currentResult);
         currentResult.setValue("description", "<p>The policy to use when the number of outstanding multicast messages reaches the value specified in MessagesMaximum and some messages must be discarded.</p> <ul> <li> <b>Keep New</b> - Indicates that the most recent messages are given priority over the oldest messages, and the oldest messages are discarded, as needed.</li> <li> <b>Keep Old</b> - Indicates that the oldest messages are given priority over the most recent messages, and the most recent messages are discarded, as needed.</li> </ul> <p> Message age is defined by the order of receipt, not by the <code>JMSTimestamp</code> value.</p> <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, "KeepOld");
         currentResult.setValue("legalValues", new Object[]{"KeepOld", "KeepNew"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReconnectBlockingMillis")) {
         getterName = "getReconnectBlockingMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReconnectBlockingMillis";
         }

         currentResult = new PropertyDescriptor("ReconnectBlockingMillis", ClientParamsBean.class, getterName, setterName);
         descriptors.put("ReconnectBlockingMillis", currentResult);
         currentResult.setValue("description", "<p>The maximum length of time, in milliseconds, that any synchronous JMS calls, such as a <code>producer.send()</code>, <code>consumer.receive()</code>, or <code>session.createBrowser()</code>, will block the calling thread before giving up on a JMS client reconnect in progress.</p>  <p> This attribute is effective only if the Reconnect Policy option is set to either <b>Producers</b> or <b>All</b>. A value of 0 will cause synchronous JMS calls to not wait for any reconnect in progress; a value of -1 will cause an infinite wait for a reconnect.</p>  <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(60000L));
         currentResult.setValue("deprecated", "12.2.1.3.0 The WebLogic JMS Automatic Reconnect feature is deprecated. The JMS Connection Factory configuration, javax.jms.extension.WLConnection API, and javax.jms.extension.JMSContext API for this feature will be removed or ignored in a future release. Oracle recommends that client applications handle connection exceptions as described in \"Client Resiliency Best Practices\" in \"Best Practices for JMS Beginners and Advanced Users\" in Administering JMS Resources. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReconnectPolicy")) {
         getterName = "getReconnectPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReconnectPolicy";
         }

         currentResult = new PropertyDescriptor("ReconnectPolicy", ClientParamsBean.class, getterName, setterName);
         descriptors.put("ReconnectPolicy", currentResult);
         currentResult.setValue("description", "<p>Specifies which types of JMS clients will be explicitly and implicitly refreshed after a lost network connection with a server or upon a server reboot. For example, selecting the <b>Producers</b> option will explicitly refresh JMS Producers and implicitly refresh any related Session and Connection clients.</p>  <ul> <li><b>None</b> Do not refresh any JMS clients derived from this connection factory.</li>  <li><b>Producers</b> Refresh all JMS Producer clients derived from this connection factory. This option does not refresh Consumers, QueueBrowsers, or Connections with a configured Client ID for a durable subscriber.</li>  <li><b>All</b> Refresh all JMS Consumer and Producer clients derived from this connection factory, including Connections with a configured Client ID for a durable subscriber. This option does not refresh QueueBrowser clients.</li> </ul>  <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, JMSConstants.RECONNECT_POLICY_PRODUCER);
         currentResult.setValue("deprecated", "12.2.1.3.0 The WebLogic JMS Automatic Reconnect feature is deprecated. The JMS Connection Factory configuration, javax.jms.extension.WLConnection API, and javax.jms.extension.JMSContext API for this feature will be removed or ignored in a future release. Oracle recommends that client applications handle connection exceptions as described in \"Client Resiliency Best Practices\" in \"Best Practices for JMS Beginners and Advanced Users\" in Administering JMS Resources. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscriptionSharingPolicy")) {
         getterName = "getSubscriptionSharingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSubscriptionSharingPolicy";
         }

         currentResult = new PropertyDescriptor("SubscriptionSharingPolicy", ClientParamsBean.class, getterName, setterName);
         descriptors.put("SubscriptionSharingPolicy", currentResult);
         currentResult.setValue("description", "<p>Specifies the subscription sharing policy on this connection. Although this attribute is dynamic, the new values only take effect on future connections and have no impact to existing connections created before the value was changed.</p>  <p>The valid values are: </p> <ul> <li><code>Exclusive</code> - The default. All subscribers created using this connection factory cannot share subscriptions with any other subscribers.</li> <li><code>Sharable</code>  - Subscribers created using this connection factory can share their subscriptions with other subscribers, regardless of whether those subscribers are created using the same connection factory or a different connection factory. Consumers can share a non-durable subscriptions only if they have the same Client ID and Client ID Policy; consumers can share a durable subscription only if they have the same Client ID, Client ID Policy, and Subscription Name. The Sharable Subscription Sharing capability was added in WebLogic 10.3.4 (11gR1PS3).</li> </ul>  <p>Notes: </p> <ul> <li>WebLogic JMS applications can override the Subscription Sharing Policy specified on the connection factory configuration by casting a <code>javax.jms.JMSContext</code> instance to <code>weblogic.jms.extensions.WLJMSContext</code> or a <code>javax.jms.Connection</code> instance to <code>weblogic.jms.extensions.WLConnection</code> and calling <code>setSubscriptionSharingPolicy(String subscriptionSharingPolicy)</code>.</li> <li>Most applications with a Sharable Subscription Sharing Policy will  also use an Unrestricted Client ID Policy in order to ensure that multiple connections with the same client ID can exist.</li> <li>Two durable subscriptions with the same Client ID and Subscription Name are treated as two different independent subscriptions if they have a different Client ID Policy. Similarly, two Sharable non-durable subscriptions with the same Client ID are treated as two different independent subscriptions if they have a different Client ID Policy.</li> <li>Durable subscriptions created using an Unrestricted Client Id must be unsubscribed using <code>weblogic.jms.extensions.WLJMSContext.unsubscribe(Topic topic, String name)</code>, instead of <code>javax.jms.JMSContext.unsubscribe(String name)</code> or using <code>weblogic.jms.extensions.WLSession.unsubscribe(Topic topic, String name)</code>, instead of <code>javax.jms.Session.unsubscribe(String name)</code>, regardless of the Subscription Sharing Policy (Exclusive or Sharable).</li> </ul> <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, JMSConstants.SUBSCRIPTION_EXCLUSIVE);
         currentResult.setValue("legalValues", new Object[]{JMSConstants.SUBSCRIPTION_EXCLUSIVE, JMSConstants.SUBSCRIPTION_SHARABLE});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SynchronousPrefetchMode")) {
         getterName = "getSynchronousPrefetchMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSynchronousPrefetchMode";
         }

         currentResult = new PropertyDescriptor("SynchronousPrefetchMode", ClientParamsBean.class, getterName, setterName);
         descriptors.put("SynchronousPrefetchMode", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a synchronous consumer will prefetch messages (that is, messages sent from the server to the client) in one server access.</p>  <ul> <li><b>Disabled</b> - Indicates that message prefetching is disabled.</li>  <li><b>Enabled</b> - Indicates that a synchronous consumer (queue receiver or topic subscriber) will prefetch messages. The amount of prefetched messages cannot exceed the maximum number of messages defined by the Messages Maximum parameter.</li>  <li><b>Topic Subscriber Only</b> - Indicates that only a synchronous topic subscriber will prefetch messages. The amount of prefetched messages cannot exceed the maximum number of messages defined by the Messages Maximum parameter.</li> </ul>  <p>Synchronous message prefetching does not support the following conditions, and will throw a JMS Exception when encountered:</p> <ul> <li>User (XA) transactions for synchronous message receives</li> <li>Multiple synchronous consumers per session (regardless of queue or topic)</li> </ul>  <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, "disabled");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalReconnectPeriodMillis")) {
         getterName = "getTotalReconnectPeriodMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTotalReconnectPeriodMillis";
         }

         currentResult = new PropertyDescriptor("TotalReconnectPeriodMillis", ClientParamsBean.class, getterName, setterName);
         descriptors.put("TotalReconnectPeriodMillis", currentResult);
         currentResult.setValue("description", "<p>The maximum length of time, in milliseconds, that JMS clients (particularly asynchronous consumers) will continue to try to reconnect to the server after either the initial network disconnect or the last synchronous call attempt, whichever occurred most recently, before giving up retrying.</p>  <p> This attribute is effective only if the ReconnectPolicy attribute is set to either <b>Producers</b> or <b>All</b>. The default value of -1 means that that it will keep trying to reconnect indefinitely; a value of 0 means that there would be exactly one retry attempt.</p>  <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("deprecated", "12.2.1.3.0 The WebLogic JMS Automatic Reconnect feature is deprecated. The JMS Connection Factory configuration, javax.jms.extension.WLConnection API, and javax.jms.extension.JMSContext API for this feature will be removed or ignored in a future release. Oracle recommends that client applications handle connection exceptions as described in \"Client Resiliency Best Practices\" in \"Best Practices for JMS Beginners and Advanced Users\" in Administering JMS Resources. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowCloseInOnMessage")) {
         getterName = "isAllowCloseInOnMessage";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowCloseInOnMessage";
         }

         currentResult = new PropertyDescriptor("AllowCloseInOnMessage", ClientParamsBean.class, getterName, setterName);
         descriptors.put("AllowCloseInOnMessage", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a connection factory creates message consumers that allow a <code>close()</code> or <code>stop()</code> method to be issued within its <code>onMessage()</code> method call.</p>  <ul> <li>If selected (set to true) on a custom connection factory, an <code>onMessage()</code> method callback is allowed to issue a <code>close()</code> method on its own Session, Connection and JMSContext objects, or a <code>stop()</code> call on its own Connection and JMSContext objects. If false, these calls will throw an exception.</li>  <li>Default JMS Connection Factories (\"java:comp/DefaultJMSConnectionFactory\", \"weblogic.jms.ConnectionFactory\", or \"weblogic.jms.XAConnectionFactory\") set this option to false and it cannot be modified.</li> </ul>  <p><i>Note:</i> The <code>onMessage()</code> method of the Message Listener is allowed to call <code>close()</code> on its own MessageConsumer and JMSConsumer even when AllowCloseInOnMessage is set to false. <br/> This value is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
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
