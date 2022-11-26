package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;

public interface DefaultIdentityAsserterMBean extends StandardInterface, DescriptorBean, IdentityAsserterMBean, IdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedTypes();

   String[] getActiveTypes();

   void setActiveTypes(String[] var1) throws InvalidAttributeValueException;

   String getUserNameMapperClassName();

   void setUserNameMapperClassName(String var1) throws InvalidAttributeValueException;

   String[] getTrustedClientPrincipals();

   void setTrustedClientPrincipals(String[] var1) throws InvalidAttributeValueException;

   String[] getBASE64EncodingExceptions();

   void setBASE64EncodingExceptions(String[] var1) throws InvalidAttributeValueException;

   boolean isUseDefaultUserNameMapper();

   void setUseDefaultUserNameMapper(boolean var1) throws InvalidAttributeValueException;

   String getDefaultUserNameMapperAttributeType();

   void setDefaultUserNameMapperAttributeType(String var1) throws InvalidAttributeValueException;

   String getDefaultUserNameMapperAttributeDelimiter();

   void setDefaultUserNameMapperAttributeDelimiter(String var1) throws InvalidAttributeValueException;

   boolean isDigestReplayDetectionEnabled();

   void setDigestReplayDetectionEnabled(boolean var1) throws InvalidAttributeValueException;

   int getDigestExpirationTimePeriod();

   void setDigestExpirationTimePeriod(int var1) throws InvalidAttributeValueException;

   String getDigestDataSourceName();

   void setDigestDataSourceName(String var1) throws InvalidAttributeValueException;

   boolean isVirtualUserAllowed();

   void setVirtualUserAllowed(boolean var1) throws InvalidAttributeValueException;

   String getName();
}
