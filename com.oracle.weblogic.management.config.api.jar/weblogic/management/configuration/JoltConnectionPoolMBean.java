package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface JoltConnectionPoolMBean extends DeploymentMBean {
   String[] getPrimaryAddresses();

   void setPrimaryAddresses(String[] var1) throws InvalidAttributeValueException;

   boolean addPrimaryAddress(String var1) throws InvalidAttributeValueException;

   boolean removePrimaryAddress(String var1) throws InvalidAttributeValueException;

   String[] getFailoverAddresses();

   void setFailoverAddresses(String[] var1) throws InvalidAttributeValueException;

   boolean addFailoverAddress(String var1) throws InvalidAttributeValueException;

   boolean removeFailoverAddress(String var1) throws InvalidAttributeValueException;

   int getMinimumPoolSize();

   void setMinimumPoolSize(int var1) throws InvalidAttributeValueException;

   int getMaximumPoolSize();

   void setMaximumPoolSize(int var1) throws InvalidAttributeValueException;

   String getUserName();

   void setUserName(String var1) throws InvalidAttributeValueException;

   String getUserPassword();

   void setUserPassword(String var1) throws InvalidAttributeValueException;

   byte[] getUserPasswordEncrypted();

   void setUserPasswordEncrypted(byte[] var1) throws InvalidAttributeValueException;

   String getApplicationPassword();

   void setApplicationPassword(String var1) throws InvalidAttributeValueException;

   byte[] getApplicationPasswordEncrypted();

   void setApplicationPasswordEncrypted(byte[] var1) throws InvalidAttributeValueException;

   String getUserRole();

   void setUserRole(String var1) throws InvalidAttributeValueException;

   boolean isSecurityContextEnabled();

   void setSecurityContextEnabled(boolean var1) throws InvalidAttributeValueException;

   int getRecvTimeout();

   void setRecvTimeout(int var1) throws InvalidAttributeValueException;

   String getKeyStoreName();

   void setKeyStoreName(String var1) throws InvalidAttributeValueException;

   String getTrustStoreName();

   void setTrustStoreName(String var1) throws InvalidAttributeValueException;

   String getKeyPassPhrase();

   void setKeyPassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getKeyPassPhraseEncrypted();

   void setKeyPassPhraseEncrypted(byte[] var1);

   String getKeyStorePassPhrase();

   void setKeyStorePassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getKeyStorePassPhraseEncrypted();

   void setKeyStorePassPhraseEncrypted(byte[] var1);

   String getTrustStorePassPhrase();

   void setTrustStorePassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getTrustStorePassPhraseEncrypted();

   void setTrustStorePassPhraseEncrypted(byte[] var1);
}
