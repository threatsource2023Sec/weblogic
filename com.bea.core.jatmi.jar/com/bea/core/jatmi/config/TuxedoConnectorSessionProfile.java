package com.bea.core.jatmi.config;

public interface TuxedoConnectorSessionProfile {
   void setSessionProfileName(String var1);

   String getSessionProfileName();

   void setSecurity(String var1);

   String getSecurity();

   void setConnectionPolicy(String var1);

   String getConnectionPolicy();

   void setAclPolicy(String var1);

   String getAclPolicy();

   void setCredentialPolicy(String var1);

   String getCredentialPolicy();

   void setRetryInterval(long var1);

   long getRetryInterval();

   void setMaxRetries(long var1);

   long getMaxRetries();

   void setCmpLimit(int var1);

   int getCmpLimit();

   void setMinEncryptBits(int var1);

   int getMinEncryptBits();

   void setMaxEncryptBits(int var1);

   int getMaxEncryptBits();

   void setKeepAlive(int var1);

   int getKeepAlive();

   void setKeepAliveWait(int var1);

   int getKeepAliveWait();
}
