package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface EmbeddedLDAPMBean extends ConfigurationMBean {
   String getCredential();

   void setCredential(String var1) throws InvalidAttributeValueException;

   byte[] getCredentialEncrypted();

   void setCredentialEncrypted(byte[] var1) throws InvalidAttributeValueException;

   int getBackupHour();

   void setBackupHour(int var1) throws InvalidAttributeValueException;

   int getBackupMinute();

   void setBackupMinute(int var1) throws InvalidAttributeValueException;

   int getBackupCopies();

   void setBackupCopies(int var1) throws InvalidAttributeValueException;

   boolean isCacheEnabled();

   void setCacheEnabled(boolean var1);

   int getCacheSize();

   void setCacheSize(int var1) throws InvalidAttributeValueException;

   int getCacheTTL();

   void setCacheTTL(int var1) throws InvalidAttributeValueException;

   boolean isRefreshReplicaAtStartup();

   void setRefreshReplicaAtStartup(boolean var1);

   boolean isMasterFirst();

   void setMasterFirst(boolean var1);

   int getTimeout();

   void setTimeout(int var1);

   boolean isKeepAliveEnabled();

   void setKeepAliveEnabled(boolean var1);

   boolean isAnonymousBindAllowed();

   void setAnonymousBindAllowed(boolean var1);
}
