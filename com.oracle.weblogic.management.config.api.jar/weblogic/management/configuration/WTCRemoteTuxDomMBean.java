package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WTCRemoteTuxDomMBean extends ConfigurationMBean {
   void setAccessPoint(String var1) throws InvalidAttributeValueException;

   String getAccessPoint();

   void setAccessPointId(String var1) throws InvalidAttributeValueException;

   String getAccessPointId();

   void setConnectionPolicy(String var1) throws InvalidAttributeValueException;

   String getConnectionPolicy();

   void setAclPolicy(String var1) throws InvalidAttributeValueException;

   String getAclPolicy();

   void setCredentialPolicy(String var1) throws InvalidAttributeValueException;

   String getCredentialPolicy();

   void setTpUsrFile(String var1) throws InvalidAttributeValueException;

   String getTpUsrFile();

   void setLocalAccessPoint(String var1) throws InvalidAttributeValueException;

   String getLocalAccessPoint();

   void setConnPrincipalName(String var1) throws InvalidAttributeValueException;

   String getConnPrincipalName();

   void setRetryInterval(long var1) throws InvalidAttributeValueException;

   long getRetryInterval();

   void setMaxRetries(long var1) throws InvalidAttributeValueException;

   long getMaxRetries();

   void setNWAddr(String var1) throws InvalidAttributeValueException;

   String getNWAddr();

   void setFederationURL(String var1) throws InvalidAttributeValueException;

   String getFederationURL();

   void setFederationName(String var1) throws InvalidAttributeValueException;

   String getFederationName();

   void setCmpLimit(int var1) throws InvalidAttributeValueException;

   int getCmpLimit();

   void setMinEncryptBits(String var1) throws InvalidAttributeValueException;

   String getMinEncryptBits();

   void setMaxEncryptBits(String var1) throws InvalidAttributeValueException;

   String getMaxEncryptBits();

   void setSSLProtocolVersion(String var1) throws InvalidAttributeValueException;

   String getSSLProtocolVersion();

   void setAppKey(String var1) throws InvalidAttributeValueException;

   String getAppKey();

   void setAllowAnonymous(boolean var1) throws InvalidAttributeValueException;

   boolean getAllowAnonymous();

   void setDefaultAppKey(int var1) throws InvalidAttributeValueException;

   int getDefaultAppKey();

   void setTuxedoUidKw(String var1) throws InvalidAttributeValueException;

   String getTuxedoUidKw();

   void setTuxedoGidKw(String var1) throws InvalidAttributeValueException;

   String getTuxedoGidKw();

   void setCustomAppKeyClass(String var1) throws InvalidAttributeValueException;

   String getCustomAppKeyClass();

   void setCustomAppKeyClassParam(String var1) throws InvalidAttributeValueException;

   String getCustomAppKeyClassParam();

   void setKeepAlive(int var1) throws InvalidAttributeValueException;

   int getKeepAlive();

   void setKeepAliveWait(int var1) throws InvalidAttributeValueException;

   int getKeepAliveWait();
}
