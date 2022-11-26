package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

/** @deprecated */
@Deprecated
public interface JMSSessionPoolMBean extends ConfigurationMBean, JMSConstants {
   /** @deprecated */
   @Deprecated
   JMSConnectionConsumerMBean[] getConnectionConsumers();

   /** @deprecated */
   @Deprecated
   void setConnectionConsumers(JMSConnectionConsumerMBean[] var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean addConnectionConsumer(JMSConnectionConsumerMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   boolean removeConnectionConsumer(JMSConnectionConsumerMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   JMSConnectionConsumerMBean[] getJMSConnectionConsumers();

   JMSConnectionConsumerMBean createJMSConnectionConsumer(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   void destroyJMSConnectionConsumer(JMSConnectionConsumerMBean var1);

   JMSConnectionConsumerMBean lookupJMSConnectionConsumer(String var1);

   String getConnectionFactory();

   void setConnectionFactory(String var1) throws InvalidAttributeValueException;

   JMSConnectionConsumerMBean createJMSConnectionConsumer(String var1, JMSConnectionConsumerMBean var2);

   void destroyJMSConnectionConsumer(String var1, JMSConnectionConsumerMBean var2);

   String getListenerClass();

   void setListenerClass(String var1) throws InvalidAttributeValueException;

   String getAcknowledgeMode();

   void setAcknowledgeMode(String var1) throws InvalidAttributeValueException;

   int getSessionsMaximum();

   void setSessionsMaximum(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isTransacted();

   void setTransacted(boolean var1) throws InvalidAttributeValueException;
}
