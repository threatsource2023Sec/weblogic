package com.bea.common.security.legacy.spi;

import com.bea.common.logger.spi.LoggerSpi;
import weblogic.security.spi.PrincipalValidator;

public interface LegacyConfigInfoSpi {
   String getDomainName();

   String getServerName();

   String getRootDirectory();

   boolean getProductionModeEnabled();

   boolean getSecureModeEnabled();

   boolean getWebAppFilesCaseInsensitive();

   PrincipalValidator getPrincipalValidator(LoggerSpi var1);

   boolean getManagementModificationsSupported();

   LegacyEncryptorSpi getLegacyEncryptor();

   String getNamedSQLConnectionLookupServiceName();

   String getLDAPSSLSocketFactoryLookupServiceName();

   String getJAXPFactoryServiceName();

   String getSAMLKeyServiceName();

   String getStoreServiceName();

   String getBootStrapServiceName();

   String getNegotiateIdentityAsserterServiceName();

   String getPasswordValidationServiceName();
}
