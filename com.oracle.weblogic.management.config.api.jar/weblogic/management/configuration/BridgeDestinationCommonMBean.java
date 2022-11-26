package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface BridgeDestinationCommonMBean extends ConfigurationMBean {
   String JMS_XA_ADAPTER_JNDI = "eis.jms.WLSConnectionFactoryJNDIXA";

   String getAdapterJNDIName();

   void setAdapterJNDIName(String var1) throws InvalidAttributeValueException;

   String getUserName();

   void setUserName(String var1) throws InvalidAttributeValueException;

   String getUserPassword();

   void setUserPassword(String var1) throws InvalidAttributeValueException;

   byte[] getUserPasswordEncrypted();

   void setUserPasswordEncrypted(byte[] var1);

   /** @deprecated */
   @Deprecated
   String getClasspath();

   /** @deprecated */
   @Deprecated
   void setClasspath(String var1) throws InvalidAttributeValueException;
}
