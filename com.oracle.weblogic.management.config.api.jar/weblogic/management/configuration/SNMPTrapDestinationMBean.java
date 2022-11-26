package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SNMPTrapDestinationMBean extends ConfigurationMBean {
   String NOAUTH_NOPRIV = "noAuthNoPriv";
   String AUTH_NOPRIV = "authNoPriv";
   String AUTH_PRIV = "authPriv";

   String getHost();

   void setHost(String var1) throws InvalidAttributeValueException, ConfigurationException;

   int getPort();

   void setPort(int var1) throws InvalidAttributeValueException, ConfigurationException;

   /** @deprecated */
   @Deprecated
   String getCommunity();

   void setCommunity(String var1) throws InvalidAttributeValueException, ConfigurationException;

   String getSecurityName();

   void setSecurityName(String var1);

   String getSecurityLevel();

   void setSecurityLevel(String var1);
}
