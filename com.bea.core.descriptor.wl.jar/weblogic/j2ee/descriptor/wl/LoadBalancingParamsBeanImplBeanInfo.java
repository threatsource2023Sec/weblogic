package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class LoadBalancingParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LoadBalancingParamsBean.class;

   public LoadBalancingParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LoadBalancingParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.LoadBalancingParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>The load-balancing parameters allow client to choose how they wish to distribute the work to the configured servers.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.LoadBalancingParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ProducerLoadBalancingPolicy")) {
         getterName = "getProducerLoadBalancingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProducerLoadBalancingPolicy";
         }

         currentResult = new PropertyDescriptor("ProducerLoadBalancingPolicy", LoadBalancingParamsBean.class, getterName, setterName);
         descriptors.put("ProducerLoadBalancingPolicy", currentResult);
         currentResult.setValue("description", "<p>The <code>Producer Load Balancing Policy</code> restricts where a JMS Message Producer can load balance its messages among members of a distributed destination (DD).</p>  <p>The valid values are:</p> <ul> <li><code>Per-Member</code> - The default value. All running members are candidates.</li> <li><code>Per-JVM</code> - Only one member per JVM is a candidate.</li> </ul>  <p>Notes:</p>  <ul> <li> Unit of Order and Unit of Work messages are not affected by this setting.</li> <li> If every WebLogic Server JVM is running a single member of the DD, then <code>Per-JVM</code> and <code>Per-Member</code> yield equivalent behavior.</li> <li> There can be at most one <code>Per-JVM</code> candidate member of a particular DD per WebLogic Server JVM.</li> <li>The <code>Per-JVM</code> load balance candidates will either be (a) members of a DD hosted on a cluster targeted JMS Server or SAF Agent that are running on their preferred server (for example, members that have not failed over or migrated), OR (b) the lexicographically least member name on the same JVM where the DD member is not hosted on a cluster targeted JMS Server or SAF Agent. A member in (a) takes precedence over a member in (b). If two members satisfy (a), then the lexicographically least member name is chosen.</li> <li> If the DD is a <code>Replicated Distributed Topic</code> or if <code>Load Balance Enabled</code> is set to <code>false</code>, then MessageProducers are 'pinned' to a single member and therefore only load balance on initial creation or after a failure. If a MessageProducer is already pinned to a particular DD member on a particular JVM, and the DD is not hosted on JMS Servers or SAF Agents that are targeted to a cluster, and a new member of the DD starts on the same JVM, then the MessageProducer's future messages stay pinned to its original member regardless of whether the member is still a <code>Per-JVM</code> load balance candidate.</li> <li> You can override the <code>Producer Load Balancing Policy</code> on a custom Connection Factory by specifying the system properties <code>weblogic.jms.ProducerLoadBalancingPolicy</code> or <code>weblogic.jms.ProducerLoadBalancingPolicy.MODULENAME</code> on every WebLogic Server in a cluster (the latter property takes precedence over the former). If a Connection Factory is overridden by one of these system properties, then the host WebLogic Server will log an Info message BEA-040538 with the name of the Connection Factory, the system property, and the system property value once the first MessageProducer uses the connection factory.</li> <li> For a similar setting that controls the load balancing of messages forwarded Exactly-Once via a Store-and-Forward Agent (SAF Agent), see the Exactly Once Load Balancing Policy attribute on a SAF Imported Destinations bean.</li> </ul>  <p>This attribute is dynamic and can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isLoadBalancingEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER);
         currentResult.setValue("legalValues", new Object[]{JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER, JMSConstants.PRODUCER_LB_POLICY_PER_JVM});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoadBalancingEnabled")) {
         getterName = "isLoadBalancingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadBalancingEnabled";
         }

         currentResult = new PropertyDescriptor("LoadBalancingEnabled", LoadBalancingParamsBean.class, getterName, setterName);
         descriptors.put("LoadBalancingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether non-anonymous producers created through a connection factory are load balanced within a distributed destination on a per-call basis.</p> <ul> <li>If enabled, the associated message producers are load balanced on every <code>send()</code> or <code>publish() </code>.</li>  <li>If disabled, the associated message producers are load balanced on the first <code>send()</code> or <code> publish()</code>.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerAffinityEnabled")) {
         getterName = "isServerAffinityEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerAffinityEnabled";
         }

         currentResult = new PropertyDescriptor("ServerAffinityEnabled", LoadBalancingParamsBean.class, getterName, setterName);
         descriptors.put("ServerAffinityEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a server instance that is load balancing consumers or producers across multiple members destinations of a distributed destination, will first attempt to load balance across any other physical destinations that are also running on the same server instance. </p> ");
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
