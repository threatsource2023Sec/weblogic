package com.bea.common.security.servicecfg;

public interface SAMLKeyServiceConfig {
   String getKeyStoreFile();

   String getKeyStoreType();

   char[] getKeyStorePassPhrase();

   int getStoreValidationPollInterval();

   String getDefaultKeyAlias();

   char[] getDefaultKeyPassphrase();
}
