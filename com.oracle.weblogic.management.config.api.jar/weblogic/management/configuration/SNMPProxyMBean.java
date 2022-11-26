package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SNMPProxyMBean extends ConfigurationMBean {
   String NOAUTH_NOPRIV = "noAuthNoPriv";
   String AUTH_NOPRIV = "authNoPriv";
   String AUTH_PRIV = "authPriv";

   String getListenAddress();

   void setListenAddress(String var1);

   int getPort();

   void setPort(int var1) throws InvalidAttributeValueException, ConfigurationException;

   String getOidRoot();

   void setOidRoot(String var1) throws InvalidAttributeValueException, ConfigurationException;

   String getCommunity();

   void setCommunity(String var1) throws InvalidAttributeValueException, ConfigurationException;

   long getTimeout();

   void setTimeout(long var1) throws InvalidAttributeValueException, ConfigurationException;

   String getSecurityName();

   void setSecurityName(String var1);

   String getSecurityLevel();

   void setSecurityLevel(String var1);
}
