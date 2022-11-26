package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;

/** @deprecated */
@Deprecated
public interface JMSDistributedTopicMemberMBean extends JMSDistributedDestinationMemberMBean {
   JMSTopicMBean getJMSTopic();

   void setJMSTopic(JMSTopicMBean var1) throws InvalidAttributeValueException;

   void useDelegates(DomainMBean var1, DistributedDestinationMemberBean var2);
}
