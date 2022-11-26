package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface JMSBridgeDestinationMBean extends BridgeDestinationCommonMBean {
   String DEFAULT_INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";

   String getName();

   String getConnectionFactoryJNDIName();

   void setConnectionFactoryJNDIName(String var1) throws InvalidAttributeValueException;

   String getInitialContextFactory();

   void setInitialContextFactory(String var1) throws InvalidAttributeValueException;

   String getConnectionURL();

   void setConnectionURL(String var1) throws InvalidAttributeValueException;

   String getDestinationJNDIName();

   void setDestinationJNDIName(String var1) throws InvalidAttributeValueException;

   String getDestinationType();

   void setDestinationType(String var1) throws InvalidAttributeValueException;
}
