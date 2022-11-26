package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMSConnectionFactoryMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSConnectionFactoryMBean.class;

   public JMSConnectionFactoryMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSConnectionFactoryMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JMSConnectionFactoryMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("obsolete", "9.0.0.0");
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean} ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class represents a JMS connection factory. Connection factories are objects that enable JMS clients to create JMS connections. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JMSConnectionFactoryMBean");
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

         currentResult = new PropertyDescriptor("AcknowledgePolicy", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("AcknowledgePolicy", currentResult);
         currentResult.setValue("description", "<p>Acknowledge policy for non-transacted sessions that use the <tt>CLIENT_ACKNOWLEDGE</tt> mode. <tt>All</tt> specifies that calling acknowledge on a message acknowledges all unacknowledged messages received on the session. <tt>Previous</tt> specifies that calling acknowledge on a message acknowledges only unacknowledged messages up to, and including, the given message.</p>  <p><i>Note:</i> This value only applies to implementations that use the <tt>CLIENT_ACKNOWLEDGE</tt> acknowledge mode for a non-transacted session.</p>  <p><i>Note:</i> This value works around a change in the JMS specification. Specifically, the specification allowed users to acknowledge all messages before and including the message being acknowledged. The specification was changed so that acknowledging any message acknowledges all messages ever received (even those received after the message being acknowledge), as follows:</p>  <ul> <li> <p>An acknowledge policy of <tt>ACKNOWLEDGE_PREVIOUS</tt> retains the old behavior (acknowledge all message up to and including the message being acknowledged).</p> </li>  <li> <p>An acknowledge policy of <tt>ACKNOWLEDGE_ALL</tt> yields the new behavior, where all messages received by the given session are acknowledged regardless of which message is being used to effect the acknowledge.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "All");
         currentResult.setValue("secureValue", "All");
         currentResult.setValue("legalValues", new Object[]{"All", "Previous"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowCloseInOnMessage")) {
         getterName = "getAllowCloseInOnMessage";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowCloseInOnMessage";
         }

         currentResult = new PropertyDescriptor("AllowCloseInOnMessage", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("AllowCloseInOnMessage", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a connection factory creates message consumers that allow a <tt>close()</tt> or <tt>stop()</tt> method to be issued within its <tt>onMessage()</tt> method call.</p>  <ul> <li> <p>If selected (set to true) on a custom connection factory, an <tt>onMessage()</tt> method callback is allowed to issue a <tt>close()</tt> method on its own Session, Connection and JMSContext objects, or a <tt>stop()</tt> call on its own Connection and JMSContext objects. If false, these calls will throw an exception.</p> </li>  <li> <p>Default JMS Connection Factories (for example, \"java:comp/DefaultJMSConnectionFactory\", \"weblogic.jms.ConnectionFactory\", or a \"weblogic.jms.XAConnectionFactory\") set this option to false. </p> </li> </ul>  <p><i>Note:</i> This value is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientId")) {
         getterName = "getClientId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientId";
         }

         currentResult = new PropertyDescriptor("ClientId", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("ClientId", currentResult);
         currentResult.setValue("description", "<p>An optional client ID for a durable subscriber that uses this JMS connection factory. Configuring this value on the connection factory prevents more than one JMS client from using a connection from the factory. Generally, JMS durable subscriber applications set their client IDs dynamically using the <tt>javax.jms.Connection.setClientID()</tt> call.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultDeliveryMode")) {
         getterName = "getDefaultDeliveryMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultDeliveryMode";
         }

         currentResult = new PropertyDescriptor("DefaultDeliveryMode", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("DefaultDeliveryMode", currentResult);
         currentResult.setValue("description", "<p>The delivery mode assigned to all messages sent by a producer using this connection factory.</p>  <p>Message producers can get the delivery mode explicitly by calling the <tt>javax.jms.MessageProducer.getDeliveryMode()</tt> method.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, "Persistent");
         currentResult.setValue("legalValues", new Object[]{"Persistent", "Non-Persistent"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultPriority")) {
         getterName = "getDefaultPriority";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultPriority";
         }

         currentResult = new PropertyDescriptor("DefaultPriority", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("DefaultPriority", currentResult);
         currentResult.setValue("description", "<p>The default priority used for messages when a priority is not explicitly defined.</p>  <p>Message producers can set the priority explicitly by calling the <tt>javax.jms.MessageProducer.setPriority()</tt> method.</p>  <p><b>Range of Values:</b> Between 0 and 9.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(4));
         currentResult.setValue("legalMax", new Integer(9));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultRedeliveryDelay")) {
         getterName = "getDefaultRedeliveryDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRedeliveryDelay";
         }

         currentResult = new PropertyDescriptor("DefaultRedeliveryDelay", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("DefaultRedeliveryDelay", currentResult);
         currentResult.setValue("description", "<p>The number of milliseconds before rolled back or recovered messages are redelivered. This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p>  <p>Message consumers can get the redelivery delay explicitly by calling the <tt>weblogic.jms.extensions.WLSession.getRedliveryDelay()</tt> method.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 64-bit integer.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTimeToDeliver")) {
         getterName = "getDefaultTimeToDeliver";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTimeToDeliver";
         }

         currentResult = new PropertyDescriptor("DefaultTimeToDeliver", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("DefaultTimeToDeliver", currentResult);
         currentResult.setValue("description", "<p>The number of milliseconds between when a message is produced and when it is made visible on its target destination.</p>  <p>Message producers can get the time-to-deliver explicitly by calling the <tt>weblogic.jms.extensions.WLMessageProducer.getTimeToDeliver()</tt> method.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 64-bit integer.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTimeToLive")) {
         getterName = "getDefaultTimeToLive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTimeToLive";
         }

         currentResult = new PropertyDescriptor("DefaultTimeToLive", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("DefaultTimeToLive", currentResult);
         currentResult.setValue("description", "<p>The default maximum number of milliseconds that a message will exist. Used for messages for which a Time to Live was not explicitly defined.</p>  <p>The default value of <tt>0</tt> indicates that the message has an infinite amount time to live.</p>  <p>Message producers can get the time-to-live explicitly by calling the <tt>javax.jms.MessageProducer.getTimeToLive()</tt> method.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 64-bit integer.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FlowInterval")) {
         getterName = "getFlowInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFlowInterval";
         }

         currentResult = new PropertyDescriptor("FlowInterval", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("FlowInterval", currentResult);
         currentResult.setValue("description", "<p>The number of seconds (between 0 and a positive 32-bit integer) when a producer adjusts its flow from the Flow Maximum number of messages to the Flow Minimum amount, or vice versa.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FlowMaximum")) {
         getterName = "getFlowMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFlowMaximum";
         }

         currentResult = new PropertyDescriptor("FlowMaximum", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("FlowMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of messages-per-second (between 0 and a positive 32-bit integer) allowed for a producer that is experiencing a threshold condition on the JMS server or queue/topic destination. When a producer is flow controlled it will never be allowed to go faster than this number of messages per second.</p>  <p>If a producer is not currently limiting its flow when a threshold condition is reached, the initial flow limit for that producer is set to FlowMaximum. If a producer is already limiting its flow when a threshold condition is reached (the flow limit is less than FlowMaximum), then the producer will continue at its current flow limit until the next time the flow is evaluated.</p>  <p><i>Note:</i> Once a threshold condition has subsided, the producer is not permitted to ignore its flow limit. If its flow limit is less than the FlowMaximum, then the producer must gradually increase its flow to the FlowMaximum each time the flow is evaluated. When the producer finally reaches the FlowMaximum, it can then ignore its flow limit and send without limiting its flow.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(500));
         currentResult.setValue("secureValue", new Integer(500));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FlowMinimum")) {
         getterName = "getFlowMinimum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFlowMinimum";
         }

         currentResult = new PropertyDescriptor("FlowMinimum", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("FlowMinimum", currentResult);
         currentResult.setValue("description", "<p>The minimum number of messages-per-second allowed for a producer that is experiencing a threshold condition. That is, WebLogic JMS will not further slow down a producer whose message flow limit is at its Flow Minimum.</p>  <p><b>Range of Values</b>: Between 0 and a positive 32-bit integer.</p>  <p><i>Note:</i> When a producer is flow controlled it will never be required to go slower than FlowMinimum messages per second.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FlowSteps")) {
         getterName = "getFlowSteps";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFlowSteps";
         }

         currentResult = new PropertyDescriptor("FlowSteps", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("FlowSteps", currentResult);
         currentResult.setValue("description", "<p>The number of steps (between 1 and a positive 32-bit integer) used when a producer is adjusting its flow from the Flow Maximum amount of messages to the Flow Minimum amount, or vice versa.</p>  <p>Also, the movement (i.e., the rate of adjustment) is calculated by dividing the difference between the Flow Maximum and the Flow Minimum into steps. At each Flow Step, the flow is adjusted upward or downward, as necessary, based on the current conditions, as follows:</p>  <ul> <li> <p>The downward movement (the decay) is geometric over the specified period of time (Flow Interval) and according to the specified number of Flow Steps. (For example, 100, 50, 25, 12.5)</p> </li>  <li> <p>The movement upward is linear. The difference is simply divided by the number of steps.</p> </li> </ul>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIName";
         }

         currentResult = new PropertyDescriptor("JNDIName", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", "<p>The JNDI name used to look up this JMS connection factory within the JNDI namespace.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesMaximum")) {
         getterName = "getMessagesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesMaximum";
         }

         currentResult = new PropertyDescriptor("MessagesMaximum", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("MessagesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of messages that may exist for an asynchronous session and that have not yet been passed to the message listener. A value of <tt>-1</tt> indicates that there is no limit on the number of messages. This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory. (For topic subscribers that use the multicast extension, also see the Overrun Policy field.)</p>  <p>When the number of messages reaches the MessagesMaximum value:</p>  <ul> <li> <p>For multicast sessions, new messages are discarded according the policy specified by the OverrunPolicy value and a DataOverrunException is thrown.</p> </li>  <li> <p>For non-multicast sessions, new messages are flow-controlled, or retained on the server until the application can accommodate the messages.</p> </li> </ul>  <p><b>Range of Values:</b> Between -1 and a positive 32-bit integer.</p>  <p><i>Note:</i> For multicast sessions, when a connection is stopped, messages will continue to be delivered, but only until the MessagesMaximum value is reached. Once this value is reached, messages will be discarded based on the Overrun policy.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Notes")) {
         getterName = "getNotes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotes";
         }

         currentResult = new PropertyDescriptor("Notes", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("Notes", currentResult);
         currentResult.setValue("description", "<p>Optional information that you can include to describe this configuration.</p>  <p>WebLogic Server saves this note in the domain's configuration file (<code>config.xml</code>) as XML PCDATA. All left angle brackets (&lt;) are converted to the XML entity <code>&amp;lt;</code>. Carriage returns/line feeds are preserved.</p>  <p>Note: If you create or edit a note from the Administration Console, the Administration Console does not preserve carriage returns/line feeds.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         String[] roleObjectArraySet = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowedSet", roleObjectArraySet);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("OverrunPolicy")) {
         getterName = "getOverrunPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOverrunPolicy";
         }

         currentResult = new PropertyDescriptor("OverrunPolicy", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("OverrunPolicy", currentResult);
         currentResult.setValue("description", "<p>Overrun policy for topic subscribers that use the multicast extension. The policy to use when the number of outstanding multicast messages reaches the value specified in the Messages Maximum field and some messages must be discarded. <tt>Keep New</tt> indicates that the most recent messages are given priority over the oldest messages, and the oldest messages are discarded, as needed. <tt>Keep Old</tt> indicates that the oldest messages are given priority over the most recent messages, and the most recent messages are discarded, as needed. Message age is defined by the order of receipt, not by the JMSTimestamp value.</p>  <p>The policy to use when the number of outstanding multicast messages reaches the value specified in MessagesMaximum and some messages must be discarded.</p>  <ul> <li> <p>If set to <code>Keep New</code>, the most recent messages are given priority over the oldest messages, and the oldest messages are discarded, as needed.</p> </li>  <li> <p>If set to <code>Keep Old</code>, the oldest messages are given priority over the most recent messages, and the most recent messages are discarded, as needed.</p> </li> </ul>  <p>Message age is defined by the order of receipt, not by the JMSTimestamp value.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, "KeepOld");
         currentResult.setValue("legalValues", new Object[]{"KeepOld", "KeepNew"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProducerLoadBalancingPolicy")) {
         getterName = "getProducerLoadBalancingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProducerLoadBalancingPolicy";
         }

         currentResult = new PropertyDescriptor("ProducerLoadBalancingPolicy", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("ProducerLoadBalancingPolicy", currentResult);
         currentResult.setValue("description", "<p>Gets the value of the ProducerLoadBalancingPolicy attribute.</p> <p>See the non-deprecated form of this bean for full javadoc.</p> ");
         setPropertyDescriptorDefault(currentResult, JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER);
         currentResult.setValue("legalValues", new Object[]{JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER, JMSConstants.PRODUCER_LB_POLICY_PER_JVM});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SendTimeout")) {
         getterName = "getSendTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSendTimeout";
         }

         currentResult = new PropertyDescriptor("SendTimeout", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("SendTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of milliseconds that a sender will wait for sufficient space (quota) on a JMS server and destination to accommodate the message being sent. Also see the Blocking Send Policy field on the JMS Server &gt; Configuration &gt; Thresholds &amp; Quotas tab.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 64-bit integer.</p>  <p>The default time is <code>10</code> milliseconds. A value of <code>0</code> indicates that the sender does not want to wait for space.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections or their producers. It only affects new connections made with this connection factory. Producers inherit the setting from the connection factory used to create their session and connection. The value can then be overridden at run time by setting the value on the producer.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(10L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>You must select a target on which an MBean will be deployed from this list of the targets in the current domain on which this item can be deployed. Targets must be either servers or clusters. The deployment will only occur once if deployments overlap.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("TransactionTimeout")) {
         getterName = "getTransactionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionTimeout";
         }

         currentResult = new PropertyDescriptor("TransactionTimeout", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("TransactionTimeout", currentResult);
         currentResult.setValue("description", "<p>The timeout seconds for all transactions on transacted sessions created with this JMS connection factory. This setting has no effect on the transaction-timeout for JTA user transactions.</p>  <p><b>Range of Values:</b> Between 0 and a positive 32-bit integer.</p>  <p><i>Note:</i> This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p>  <p><i>Note:</i> If a transacted session is still active after the timeout has elapsed, the transaction is rolled back. A value of 0 indicates that the default value will be used. If you have long-running transactions, you might want to adjust the value of this value to allow transactions to complete.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(3600L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("FlowControlEnabled")) {
         getterName = "isFlowControlEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFlowControlEnabled";
         }

         currentResult = new PropertyDescriptor("FlowControlEnabled", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("FlowControlEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether flow control is enabled for a producer created using this connection factory. If true, the associated message producers will be slowed down if the JMS server reaches Bytes/MessagesThresholdHigh.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoadBalancingEnabled")) {
         getterName = "isLoadBalancingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadBalancingEnabled";
         }

         currentResult = new PropertyDescriptor("LoadBalancingEnabled", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("LoadBalancingEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether non-anonymous producers sending to a distributed destination are load balanced on a per-send basis.</p>  <ul> <li> <p>If true, the associated message producers will be load balanced on every <tt>send()</tt> or <tt>publish()</tt>.</p> </li>  <li> <p>If false, the associated message producers will be load balanced on the first <tt>send()</tt> or <tt>publish()</tt>.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerAffinityEnabled")) {
         getterName = "isServerAffinityEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerAffinityEnabled";
         }

         currentResult = new PropertyDescriptor("ServerAffinityEnabled", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("ServerAffinityEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether a server that is load balancing consumers or producers across multiple physical destinations in a distributed destination set will first attempt to load balance across any other physical destinations that are also running on the same WebLogic Server instance.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserTransactionsEnabled")) {
         getterName = "isUserTransactionsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserTransactionsEnabled";
         }

         currentResult = new PropertyDescriptor("UserTransactionsEnabled", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("UserTransactionsEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a connection factory creates sessions that are JTA aware. If true, the associated message producers and message consumers look into the running thread for a transaction context. Otherwise, the current JTA transaction will be ignored. This value is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p>  <p><i>Note:</i> This value is now deprecated. If the XAServerEnabled value is set, then this value is automatically set as well.</p>  <p><i>Note:</i> Transacted sessions ignore the current threads transaction context in favor of their own internal transaction, regardless of the setting. This setting only affects non-transacted sessions.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link weblogic.management.configuration.JMSConnectionFactoryMBean#isXAConnectionFactoryEnabled() isXAConnectionFactoryEnabled} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XAConnectionFactoryEnabled")) {
         getterName = "isXAConnectionFactoryEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXAConnectionFactoryEnabled";
         }

         currentResult = new PropertyDescriptor("XAConnectionFactoryEnabled", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("XAConnectionFactoryEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a XA queue or XA topic connection factory is returned, instead of a queue or topic connection factory. An XA factory is required for JMS applications to use JTA user-transactions, but is not required for transacted sessions. All connections created from an XA factory, whether they are XAConnections or plain Connections, become JTA user-transaction-aware.</p>  <p>In addition, this value indicates whether or not a connection factory creates sessions that are JTA aware. If true, the associated message producers and message consumers look into the running thread for a transaction context. Otherwise, the current JTA transaction will be ignored.</p>  <p><i>Note:</i> Transacted sessions ignore the current threads transaction context in favor of their own internal transaction, regardless of the setting. This setting only affects non-transacted sessions.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XAServerEnabled")) {
         getterName = "isXAServerEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXAServerEnabled";
         }

         currentResult = new PropertyDescriptor("XAServerEnabled", JMSConnectionFactoryMBean.class, getterName, setterName);
         descriptors.put("XAServerEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether an XA connection factory will be returned instead of a standard connection factory.</p>  <p><i>Note:</i> This value is deprecated. It is now possible to use a single XA-enabled connection factory for both XA and non-XA purposes.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link weblogic.management.configuration.JMSConnectionFactoryMBean#isXAConnectionFactoryEnabled() isXAConnectionFactoryEnabled} ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSConnectionFactoryMBean.class.getMethod("addTarget", TargetMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the Target attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>You can add a target to specify additional servers on which the deployment can be deployed. The targets must be either clusters or servers.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = JMSConnectionFactoryMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the value of the addTarget attribute.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addTarget")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JMSConnectionFactoryMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JMSConnectionFactoryMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSConnectionFactoryMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSConnectionFactoryMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
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
