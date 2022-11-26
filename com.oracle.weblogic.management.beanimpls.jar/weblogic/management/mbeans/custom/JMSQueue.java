package weblogic.management.mbeans.custom;

import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public final class JMSQueue extends JMSDestination {
   private static final long serialVersionUID = 6209479225384067069L;
   private long _creationTime = 1L;

   public JMSQueue(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void setCreationTime(long t) {
      this._creationTime = t;
   }

   public long getCreationTime() {
      return this._creationTime;
   }

   public void useDelegates(DomainMBean domain, JMSBean interopModule, QueueBean delegate) {
      super.useDelegates(domain, interopModule, delegate);
   }
}
