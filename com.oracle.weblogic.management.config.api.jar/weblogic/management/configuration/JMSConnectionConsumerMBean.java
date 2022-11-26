package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

/** @deprecated */
@Deprecated
public interface JMSConnectionConsumerMBean extends ConfigurationMBean {
   int getMessagesMaximum();

   void setMessagesMaximum(int var1) throws InvalidAttributeValueException;

   String getSelector();

   void setSelector(String var1) throws InvalidAttributeValueException;

   String getDestination();

   void setDestination(String var1) throws InvalidAttributeValueException;
}
