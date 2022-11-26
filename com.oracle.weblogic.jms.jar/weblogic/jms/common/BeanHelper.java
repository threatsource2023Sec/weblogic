package weblogic.jms.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.management.ManagementException;
import weblogic.management.utils.GenericBeanListener;

public abstract class BeanHelper {
   public static final Map connectionFactoryBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("JNDIName", String.class);
         this.put("LocalJNDIName", String.class);
         this.put("DefaultTargetingEnabled", Boolean.TYPE);
      }
   });
   public static final Map defaultDeliveryParamsBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("DefaultDeliveryMode", String.class);
         this.put("DefaultTimeToDeliver", String.class);
         this.put("DefaultTimeToLive", Long.TYPE);
         this.put("DefaultPriority", Integer.TYPE);
         this.put("DefaultRedeliveryDelay", Long.TYPE);
         this.put("SendTimeout", Long.TYPE);
         this.put("DefaultCompressionThreshold", Integer.TYPE);
         this.put("DefaultUnitOfOrder", String.class);
      }
   });
   public static final Map clientParamsBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("ClientId", String.class);
         this.put("ClientIdPolicy", String.class);
         this.put("SubscriptionSharingPolicy", String.class);
         this.put("AcknowledgePolicy", String.class);
         this.put("AllowCloseInOnMessage", Boolean.TYPE);
         this.put("MessagesMaximum", Integer.TYPE);
         this.put("MulticastOverrunPolicy", String.class);
         this.put("SynchronousPrefetchMode", String.class);
         this.put("ReconnectPolicy", String.class);
         this.put("ReconnectBlockingMillis", Long.TYPE);
         this.put("TotalReconnectPeriodMillis", Long.TYPE);
      }
   });
   public static final Map transactionParamsBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("TransactionTimeout", Long.TYPE);
         this.put("XAConnectionFactoryEnabled", Boolean.TYPE);
      }
   });
   public static final Map flowControlParamsBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("FlowMinimum", Integer.TYPE);
         this.put("FlowMaximum", Integer.TYPE);
         this.put("FlowInterval", Integer.TYPE);
         this.put("FlowSteps", Integer.TYPE);
         this.put("FlowControlEnabled", Boolean.TYPE);
         this.put("OneWaySendMode", String.class);
         this.put("OneWaySendWindowSize", Integer.TYPE);
      }
   });
   public static final Map loadBalancingParamsBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("LoadBalancingEnabled", Boolean.TYPE);
         this.put("ServerAffinityEnabled", Boolean.TYPE);
         this.put("ProducerLoadBalancingPolicy", String.class);
      }
   });
   public static final Map securityParamsBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("AttachJMSXUserId", Boolean.TYPE);
         this.put("SecurityPolicy", String.class);
      }
   });

   public static void copyConnectionFactory(JMSConnectionFactoryBean to, JMSConnectionFactoryBean from) throws ManagementException {
      GenericBeanListener gbl = new GenericBeanListener((DescriptorBean)from, to, connectionFactoryBeanSignatures);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getDefaultDeliveryParams(), to.getDefaultDeliveryParams(), defaultDeliveryParamsBeanSignatures);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getClientParams(), to.getClientParams(), clientParamsBeanSignatures);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getTransactionParams(), to.getTransactionParams(), transactionParamsBeanSignatures);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getFlowControlParams(), to.getFlowControlParams(), flowControlParamsBeanSignatures);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getLoadBalancingParams(), to.getLoadBalancingParams(), loadBalancingParamsBeanSignatures);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getSecurityParams(), to.getSecurityParams(), securityParamsBeanSignatures);
      gbl.initialize(false);
   }
}
