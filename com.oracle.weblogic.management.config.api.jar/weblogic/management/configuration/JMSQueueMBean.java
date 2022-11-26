package weblogic.management.configuration;

import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QueueBean;

/** @deprecated */
@Deprecated
public interface JMSQueueMBean extends JMSDestinationMBean {
   void useDelegates(DomainMBean var1, JMSBean var2, QueueBean var3);
}
