package weblogic.management.utils;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface LDAPServerMBean extends StandardInterface, DescriptorBean {
   String getHost();

   void setHost(String var1) throws InvalidAttributeValueException;

   int getPort();

   void setPort(int var1) throws InvalidAttributeValueException;

   boolean isSSLEnabled();

   void setSSLEnabled(boolean var1) throws InvalidAttributeValueException;

   String getPrincipal();

   void setPrincipal(String var1) throws InvalidAttributeValueException;

   String getCredential();

   void setCredential(String var1) throws InvalidAttributeValueException;

   byte[] getCredentialEncrypted();

   void setCredentialEncrypted(byte[] var1) throws InvalidAttributeValueException;

   boolean isCacheEnabled();

   void setCacheEnabled(boolean var1) throws InvalidAttributeValueException;

   int getCacheSize();

   void setCacheSize(int var1) throws InvalidAttributeValueException;

   int getCacheTTL();

   void setCacheTTL(int var1) throws InvalidAttributeValueException;

   boolean isFollowReferrals();

   void setFollowReferrals(boolean var1) throws InvalidAttributeValueException;

   boolean isBindAnonymouslyOnReferrals();

   void setBindAnonymouslyOnReferrals(boolean var1) throws InvalidAttributeValueException;

   int getResultsTimeLimit();

   void setResultsTimeLimit(int var1) throws InvalidAttributeValueException;

   int getConnectTimeout();

   void setConnectTimeout(int var1) throws InvalidAttributeValueException;

   int getParallelConnectDelay();

   void setParallelConnectDelay(int var1) throws InvalidAttributeValueException;

   int getConnectionRetryLimit();

   void setConnectionRetryLimit(int var1) throws InvalidAttributeValueException;

   int getConnectionPoolSize();

   void setConnectionPoolSize(int var1) throws InvalidAttributeValueException;
}
