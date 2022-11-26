package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class MessagingBridgeMBeanImplBeanInfo extends DynamicDeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MessagingBridgeMBean.class;

   public MessagingBridgeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MessagingBridgeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.MessagingBridgeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean represents a WebLogic messaging bridge. A messaging bridge instance interoperates between separate implementations of WebLogic JMS or between WebLogic JMS and another messaging product.</p>  <p>For WebLogic JMS and third-party JMS products, a messaging bridge communicates with a configured source and target destinations using the resource adapters provided with WebLogic Server.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.MessagingBridgeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BatchInterval")) {
         getterName = "getBatchInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBatchInterval";
         }

         currentResult = new PropertyDescriptor("BatchInterval", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("BatchInterval", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time, in milliseconds, that a messaging bridge instance waits before sending a batch of messages in one transaction, regardless of whether the <code>Batch Size</code> has been reached or not.</p>  <ul> <li> <p>Only applies to a messaging bridge instance forwarding messages in synchronous mode and has a QOS (quality of service) that requires two-phase transactions.</p> </li>  <li> <p>The default value of <code>-1</code> indicates that the bridge instance waits until the number of messages reaches the <code>Batch Size</code> before it completes a transaction.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BatchSize")) {
         getterName = "getBatchSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBatchSize";
         }

         currentResult = new PropertyDescriptor("BatchSize", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("BatchSize", currentResult);
         currentResult.setValue("description", "<p>The number of messages that are processed within one transaction.</p>  <p><code>Batch Size</code> only applies to a messaging bridge instance forwarding messages in synchronous mode and has a QOS (quality of service) that requires two-phase transactions.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdleTimeMaximum")) {
         getterName = "getIdleTimeMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdleTimeMaximum";
         }

         currentResult = new PropertyDescriptor("IdleTimeMaximum", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("IdleTimeMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time, in seconds, that a messaging bridge instance remains idle.</p>  <ul> <li> <p>In <i>asynchronous</i> mode, this is the longest amount of time a messaging bridge instance stays idle before it checks the sanity of its connection to the source.</p> </li>  <li> <p>In <i>synchronous</i> mode, this is the amount of time the messaging bridge can block on a receive call if no transaction is involved.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PreserveMsgProperty")) {
         getterName = "getPreserveMsgProperty";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPreserveMsgProperty";
         }

         currentResult = new PropertyDescriptor("PreserveMsgProperty", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("PreserveMsgProperty", currentResult);
         currentResult.setValue("description", "<p>Specifies if message properties are preserved when messages are forwarded by a bridge instance.</p>  <p>The following message properties are preserved:</p>  <ul> <li> <p><code>message ID</code></p> </li>  <li> <p><code>message timestamp</code></p> </li>  <li> <p><code>user ID</code></p> </li>  <li> <p><code>delivery mode</code></p> </li>  <li> <p><code>priority</code></p> </li>  <li> <p><code>expiration time</code></p> </li>  <li> <p><code>redelivery limit</code></p> </li>  <li> <p><code>unit of order name</code></p> </li> </ul>  <p>If the target bridge destination is on a foreign JMS server, the following message properties are preserved:</p>  <ul> <li> <p><code>delivery mode</code></p> </li>  <li> <p><code>priority</code></p> </li>  <li> <p><code>expiration time</code></p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QualityOfService")) {
         getterName = "getQualityOfService";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setQualityOfService";
         }

         currentResult = new PropertyDescriptor("QualityOfService", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("QualityOfService", currentResult);
         currentResult.setValue("description", "<p>The QOS (quality of service) for this messaging bridge instance.</p>  <ul> <li><p><code>Exactly-once</code>: Each message in the source destination is transferred to the target exactly once. This is the highest QOS a messaging bridge instance can offer.</p> </li>  <li><p><code>Atmost-once</code>: Each message in the source is transferred to the target only once with the possibility of being lost during the forwarding.</p> </li>  <li><p><code>Duplicate-okay</code>: Messages in the source destination are transferred to the target (none are lost) but some may appear in the target more than once.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "Exactly-once");
         currentResult.setValue("secureValue", "Exactly-once");
         currentResult.setValue("legalValues", new Object[]{"Exactly-once", "Atmost-once", "Duplicate-okay"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReconnectDelayIncrease")) {
         getterName = "getReconnectDelayIncrease";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReconnectDelayIncrease";
         }

         currentResult = new PropertyDescriptor("ReconnectDelayIncrease", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("ReconnectDelayIncrease", currentResult);
         currentResult.setValue("description", "<p>The incremental delay time, in seconds, that a messaging bridge instance increases its waiting time between one failed reconnection attempt and the next retry.</p>  <p>Use with <code>ReconnectDelayMinimum</code> and <code>ReconnectDelayMaximum</code>. After the first failure to connect to a destination, the bridge instance waits for the number of seconds defined by <code>ReconnectDelayMinimum</code>. Each time a reconnect attempt fails, the bridge instance increases its waiting time by the number of seconds defined by <code>ReconnectDelayIncrease</code>. The maximum delay time is defined by <code>ReconnectDelayMaximum</code>. Once the waiting time is increased to the maximum value, the bridge instance stops increase its waiting time. Once the bridge instance successfully connects to the destination, the bridge instance resets its waiting time to the initial value defined by <code>ReconnectDelayMinimum</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReconnectDelayMaximum")) {
         getterName = "getReconnectDelayMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReconnectDelayMaximum";
         }

         currentResult = new PropertyDescriptor("ReconnectDelayMaximum", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("ReconnectDelayMaximum", currentResult);
         currentResult.setValue("description", "<p>The longest amount of time, in seconds, that a messaging bridge instance waits between one failed attempt to connect to the source or target, and the next retry.</p>  <p>Use with <code>ReconnectDelayMinimum</code> and <code>ReconnectDelayIncrease</code>. After the first failure to connect to a destination, a bridge instance waits for the number of seconds defined by <code>ReconnectDelayMinimum</code>. Each time a reconnect attempt fails, the bridge instance increases its waiting time by the number of seconds defined by <code>ReconnectDelayIncrease</code>. The maximum delay time is defined by <code>ReconnectDelayMaximum</code>. Once the waiting time is increased to the maximum value, the bridge instance stops increase its waiting time. Once the bridge instance successfully connects to the destination, the bridge instance resets its waiting time to the initial value defined by <code>ReconnectDelayMinimum</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReconnectDelayMinimum")) {
         getterName = "getReconnectDelayMinimum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReconnectDelayMinimum";
         }

         currentResult = new PropertyDescriptor("ReconnectDelayMinimum", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("ReconnectDelayMinimum", currentResult);
         currentResult.setValue("description", "<p>The minimum amount of time, in seconds, that a messaging bridge instance waits before it tries to reconnect to the source or target destination after a failure.</p>  <p>Use with <code>ReconnectDelayMaximum</code> and <code>ReconnectDelayIncrease</code>. After the first failure to connect to a destination, the bridge instance waits for the number of seconds defined by <code>ReconnectDelayMinimum</code>. Each time a reconnect attempt fails, the bridge instance increases its waiting time by the number of seconds defined by <code>ReconnectDelayIncrease</code>. The maximum delay time is defined by <code>ReconnectDelayMaximum</code>. Once the waiting time is increased to the maximum value, the bridge instance stops increase its waiting time. Once the bridge instance successfully connects to the destination, the bridge instance resets its waiting time to the initial value defined by <code>ReconnectDelayMinimum</code>.</p>> ");
         setPropertyDescriptorDefault(currentResult, new Integer(15));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Selector")) {
         getterName = "getSelector";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSelector";
         }

         currentResult = new PropertyDescriptor("Selector", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("Selector", currentResult);
         currentResult.setValue("description", "<p>The filter for messages that are sent across the messaging bridge instance.</p>  <p>Only messages that match the selection criteria are sent across the messaging bridge:</p>  <ul> <li> <p>For queues, messages that do not match the selection criteria are left behind and accumulate in the queue.</p> </li>  <li> <p>For topics, messages that do not match the connection criteria are dropped.</p> </li> </ul> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceDestination")) {
         getterName = "getSourceDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourceDestination";
         }

         currentResult = new PropertyDescriptor("SourceDestination", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("SourceDestination", currentResult);
         currentResult.setValue("description", "<p>The source destination from which this messaging bridge instance reads messages.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TargetDestination")) {
         getterName = "getTargetDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargetDestination";
         }

         currentResult = new PropertyDescriptor("TargetDestination", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("TargetDestination", currentResult);
         currentResult.setValue("description", "<p>The target destination where a messaging bridge instance sends the messages it receives from the source destination.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionTimeout")) {
         getterName = "getTransactionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionTimeout";
         }

         currentResult = new PropertyDescriptor("TransactionTimeout", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("TransactionTimeout", currentResult);
         currentResult.setValue("description", "<p>The amount of time, in seconds, that the transaction manager waits for each transaction before timing it out.</p>  <ul> <li> <p>Transaction timeouts are used when the QOS (quality of service) for a messaging bridge instance requires transactions.</p> </li>  <li> <p>If a bridge is configured with <i>Exactly-once</i> QOS, the receiving and sending is completed in one transaction.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AsyncEnabled")) {
         getterName = "isAsyncEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAsyncEnabled";
         }

         currentResult = new PropertyDescriptor("AsyncEnabled", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("AsyncEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies if a messaging bridge instance forwards in asynchronous messaging mode.</p>  <p>AsyncEnabled only applies to messaging bridge instances whose source destination supports asynchronous receiving. Messaging bridges instances that forward in asynchronous mode are driven by the source destination. A messaging bridge instance listens for messages and forwards them as they arrive. When <code>AsyncEnabled</code> is not selected, a bridge instance is forced to work in synchronous mode, even if the source supports asynchronous receiving.</p>  <p><b>Note:</b> For a messaging bridge instance with a QOS of <i>Exactly-once</i> to work in asynchronous mode, the source destination has to support the <code>MDBTransaction</code> interface. Otherwise, the bridge automatically switches to synchronous mode if it detects that <code>MDBTransaction</code> is not supported by the source destination.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DurabilityEnabled")) {
         getterName = "isDurabilityEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDurabilityEnabled";
         }

         currentResult = new PropertyDescriptor("DurabilityEnabled", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("DurabilityEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether or not the messaging bridge allows durable messages.</p>  <p>When enabled and the source destination is a JMS topic, a messaging bridge instance uses a durable subscription to ensure that no messages are lost in the event of a failure. <code>DurabilityEnabled</code> ignored if the source destination is a JMS queue.</p>  <ul> <li> <p>When enabled and the source destination uses durable subscriptions, the source JMS implementation saves messages that are sent when a messaging bridge instance is not running. When the bridge instance is restarted, these messages are forwarded to the target destination. The administrator can choose not to be durable.</p> </li>  <li> <p>When not enabled, messages that are sent to the source JMS implementation while the bridge instance is down cannot be forwarded to the target destination.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QOSDegradationAllowed")) {
         getterName = "isQOSDegradationAllowed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setQOSDegradationAllowed";
         }

         currentResult = new PropertyDescriptor("QOSDegradationAllowed", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("QOSDegradationAllowed", currentResult);
         currentResult.setValue("description", "<p>Specifies if this messaging bridge instance allows the degradation of its QOS (quality of service) when the configured QOS is not available.</p>  <ul> <li> <p>When enabled, the messaging bridge instance degrades the QOS when the configured QOS is not available. If the QOS is degraded, a log message is delivered to the WebLogic startup window or log file.</p> </li>  <li> <p>When not enabled, if messaging bridge instance cannot satisfy the quality of service requested, an error results and the messaging bridge instance does not start.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Started")) {
         getterName = "isStarted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStarted";
         }

         currentResult = new PropertyDescriptor("Started", MessagingBridgeMBean.class, getterName, setterName);
         descriptors.put("Started", currentResult);
         currentResult.setValue("description", "<p>Specifies the initial operating state of a targeted messaging bridge instance.</p>  <ul> <li> <p>If enabled, the messaging bridge instance forwards messages (running).</p> </li>  <li> <p>If not enabled, the messaging bridge instance does not forward messages (temporarily stopped).</p> </li> </ul>  <p>After a messaging bridge has started forwarding messages (running), use <code>Started</code> to temporarily suspend an active messaging bridge instance or restart an stopped messaging bridge instance.</p>  <ul> <li> <p>Select the <code>Started</code> checkbox to start a messaging bridge instance that has been temporarily stopped.</p> </li>  <li> <p>Clear the <code>Started</code> checkbox to temporarily stop a messaging bridge instance that was running.</p> </li>  <li> <p>This value does not indicate the run-time state of a messaging bridge instance.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
