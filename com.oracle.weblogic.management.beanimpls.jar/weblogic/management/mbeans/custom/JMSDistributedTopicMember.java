package weblogic.management.mbeans.custom;

import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSTopicMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class JMSDistributedTopicMember extends JMSDistributedDestinationMember {
   private static final String DT_MEMBER = "JMSTopic";
   private static final String TOPIC_PROP = "PhysicalDestinationName";
   private DistributedDestinationMemberBean delegate;
   private DomainMBean domain;

   public JMSDistributedTopicMember(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DomainMBean paramDomain, DistributedDestinationMemberBean paramDelegate) {
      this.domain = paramDomain;
      this.delegate = paramDelegate;
      super.useDelegates(this.delegate);
   }

   public JMSTopicMBean getJMSTopic() {
      return this.delegate == null ? (JMSTopicMBean)this.getValue("JMSTopic") : null;
   }

   public void setJMSTopic(JMSTopicMBean topic) {
      if (this.delegate == null) {
         this.putValue("JMSTopic", topic);
      } else {
         if (topic == null) {
            this.delegate.unSet("PhysicalDestinationName");
         } else {
            this.delegate.setPhysicalDestinationName(topic.getName());
         }

      }
   }
}
