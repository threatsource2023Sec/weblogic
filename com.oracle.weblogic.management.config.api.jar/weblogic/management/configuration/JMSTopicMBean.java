package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.TopicBean;

/** @deprecated */
@Deprecated
public interface JMSTopicMBean extends JMSDestinationMBean {
   String getMulticastAddress();

   void setMulticastAddress(String var1) throws InvalidAttributeValueException;

   int getMulticastTTL();

   void setMulticastTTL(int var1) throws InvalidAttributeValueException;

   int getMulticastPort();

   void setMulticastPort(int var1) throws InvalidAttributeValueException;

   void useDelegates(DomainMBean var1, JMSBean var2, TopicBean var3);
}
