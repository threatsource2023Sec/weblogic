package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;

/** @deprecated */
@Deprecated
public interface JMSDestinationKeyMBean extends ConfigurationMBean, JMSConstants {
   String getProperty();

   void setProperty(String var1) throws InvalidAttributeValueException;

   String getKeyType();

   void setKeyType(String var1) throws InvalidAttributeValueException;

   String getDirection();

   void setDirection(String var1) throws InvalidAttributeValueException;

   void useDelegates(DestinationKeyBean var1);
}
