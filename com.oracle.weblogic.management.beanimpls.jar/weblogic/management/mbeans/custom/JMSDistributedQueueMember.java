package weblogic.management.mbeans.custom;

import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSQueueMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class JMSDistributedQueueMember extends JMSDistributedDestinationMember {
   private static final String DQ_MEMBER = "JMSQueue";
   private static final String QUEUE_PROP = "PhysicalDestinationName";
   private DomainMBean domain;
   private DistributedDestinationMemberBean delegate;

   public JMSDistributedQueueMember(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DomainMBean paramDomain, DistributedDestinationMemberBean paramDelegate) {
      this.domain = paramDomain;
      this.delegate = paramDelegate;
      super.useDelegates(this.delegate);
   }

   public JMSQueueMBean getJMSQueue() {
      return this.delegate == null ? (JMSQueueMBean)this.getValue("JMSQueue") : null;
   }

   public void setJMSQueue(JMSQueueMBean queue) {
      if (this.delegate == null) {
         this.putValue("JMSQueue", queue);
      } else {
         if (queue == null) {
            this.delegate.unSet("PhysicalDestinationName");
         } else {
            this.delegate.setPhysicalDestinationName(queue.getName());
         }

      }
   }
}
