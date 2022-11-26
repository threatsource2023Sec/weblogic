package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class FlowControlParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = FlowControlParamsBean.class;

   public FlowControlParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public FlowControlParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.FlowControlParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Many clients producing messages can cause the server to fall behind in processing messages.  The flow control parameters can help by slowing down production of messages.  Using flow control can help the overall throughput of the system.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.FlowControlParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("FlowInterval")) {
         getterName = "getFlowInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFlowInterval";
         }

         currentResult = new PropertyDescriptor("FlowInterval", FlowControlParamsBean.class, getterName, setterName);
         descriptors.put("FlowInterval", currentResult);
         currentResult.setValue("description", "<p> The adjustment period of time, in seconds, when a producer adjusts its flow from the FlowMaximum number of messages to the FlowMinimum amount, or vice versa.</p> <p> When a producer is flow controlled, it is slowed down from its FlowMaximum to its FlowMinimum over the specified FlowInterval amount of seconds.</p> ");
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

         currentResult = new PropertyDescriptor("FlowMaximum", FlowControlParamsBean.class, getterName, setterName);
         descriptors.put("FlowMaximum", currentResult);
         currentResult.setValue("description", "<p> The maximum number of messages-per-second allowed for a producer that is experiencing a threshold condition. When a producer is flow controlled it will never be allowed to go faster than the FlowMaximum messages per second.</p> <p> If a producer is not currently limiting its flow when a threshold condition is reached, the initial flow limit for that producer is set to FlowMaximum. If a producer is already limiting its flow when a threshold condition is reached (the flow limit is less than FlowMaximum), then the producer will continue at its current flow limit until the next time the flow is evaluated.</p> <p> <b>Note:</b> Once a threshold condition has subsided, the producer is not permitted to ignore its flow limit. If its flow limit is less than the FlowMaximum, then the producer must gradually increase its flow to the FlowMaximum each time the flow is evaluated. When the producer finally reaches the FlowMaximum, it can then ignore its flow limit and send without limiting its flow. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(500));
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

         currentResult = new PropertyDescriptor("FlowMinimum", FlowControlParamsBean.class, getterName, setterName);
         descriptors.put("FlowMinimum", currentResult);
         currentResult.setValue("description", "<p> The minimum number of messages-per-second allowed for a producer that is experiencing a threshold condition. This is the lower boundary of a producer's flow limit. That is, WebLogic JMS will not further slow down a producer whose message flow limit is at its FlowMinimum. </p> <p> When a producer is flow controlled it will never be required to go slower than FlowMinimum messages per second. </p> ");
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

         currentResult = new PropertyDescriptor("FlowSteps", FlowControlParamsBean.class, getterName, setterName);
         descriptors.put("FlowSteps", currentResult);
         currentResult.setValue("description", "<p>The number of steps used when a producer is adjusting its flow from the Flow Maximum amount of messages to the Flow Minimum amount, or vice versa. Specifically, the Flow Interval adjustment period is divided into the number of Flow Steps (for example, 60 seconds divided by 6 steps is 10 seconds per step). </p>  <p>Also, the movement (i.e., the rate of adjustment) is calculated by dividing the difference between the Flow Maximum and the Flow Minimum into steps. At each Flow Step, the flow is adjusted upward or downward, as necessary, based on the current conditions, as follows:</p> <ul> <li>The downward movement (the decay) is geometric over the specified period of time (Flow Interval) and according to the specified number of Flow Steps. (For example, 100, 50, 25, 12.5)</li> <li>The movement upward is linear. The difference is simply divided by the number of steps.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OneWaySendMode")) {
         getterName = "getOneWaySendMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOneWaySendMode";
         }

         currentResult = new PropertyDescriptor("OneWaySendMode", FlowControlParamsBean.class, getterName, setterName);
         descriptors.put("OneWaySendMode", currentResult);
         currentResult.setValue("description", "<p>Specifies whether message producers created using this connection factory are allowed to do one-way message sends to improve typical non-persistent, non-transactional messaging performance. When enabled, the associated queue senders and/or topic publishers can send messages without internally waiting for a response from the target destination's host JMS server.</p>  <p>One-way sends are supported only when the connection factory hosting the producer and the JMS server hosting the target destination are targeted to the same WebLogic Server instance. In addition, one-way sends are not supported for higher quality-of-service (QOS) features, such as XA, transacted sessions, persistent messaging, unit-of-order, unit-of-work, distributed destinations, and client-side store-and-forward. If the producer and target destination are in separate domains, or if any of these higher QOS features are detected, then the one-way mode setting will be ignored and standard two-way sends will be used instead.</p>  <ul> <li><b>Disabled</b> One-way send is disabled.</li>  <li><b>Enabled</b> One-way send is permitted for queue senders or topic publishers.</li>  <li><b>Topic Only</b> Only topic publishers are permitted to do one-way sends.</li> </ul>  <p><b>Notes:</b></p> <ul> <li>One-way message sends are disabled if your connection factory is configured with <code>XA Connection Factory Enabled</code>. This setting disables one-way sends whether or not the sender actually uses transactions.</li>  <li>To benefit from one-way performance, the default <code>One-Way Send Window Size</code> value must also be configured higher. After every <code>OneWaySendWindowSize - 1</code> number of messages, a two-way is used instead of a one-way, so all messages are actually two-way when the <code>OneWaySendWindowSize = 1</code>.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "disabled");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OneWaySendWindowSize")) {
         getterName = "getOneWaySendWindowSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOneWaySendWindowSize";
         }

         currentResult = new PropertyDescriptor("OneWaySendWindowSize", FlowControlParamsBean.class, getterName, setterName);
         descriptors.put("OneWaySendWindowSize", currentResult);
         currentResult.setValue("description", "<p> Specifies the maximum number of sent messages per window when <code>One-Way Send Mode</code> is set to allow queue senders and/or topic publishers to make one-way sends. The window size determines when a two-way message is required to regulate the producer before it can continue making additional one-way sends.</p>  <p>To benefit from one-way performance, the default <code>One-Way Send Window Size</code> value must be configured higher <i>in addition to</i> enabling one-way sends. After every <code>OneWaySendWindowSize - 1</code> number of messages, a two-way is used instead of a one-way, so all messages are actually two-way when the <code>OneWaySendWindowSize = 1</code>.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getOneWaySendMode")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FlowControlEnabled")) {
         getterName = "isFlowControlEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFlowControlEnabled";
         }

         currentResult = new PropertyDescriptor("FlowControlEnabled", FlowControlParamsBean.class, getterName, setterName);
         descriptors.put("FlowControlEnabled", currentResult);
         currentResult.setValue("description", "<p> Specifies whether a producer created using a connection factory allows flow control. If true, the associated message producers will be slowed down if a JMS server or a destination reaches its specified upper byte or message threshold.</p> ");
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
