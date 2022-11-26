package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;

/** @deprecated */
@Deprecated
public interface JMSDistributedQueueMemberMBean extends JMSDistributedDestinationMemberMBean {
   JMSQueueMBean getJMSQueue();

   void setJMSQueue(JMSQueueMBean var1) throws InvalidAttributeValueException;

   void useDelegates(DomainMBean var1, DistributedDestinationMemberBean var2);
}
