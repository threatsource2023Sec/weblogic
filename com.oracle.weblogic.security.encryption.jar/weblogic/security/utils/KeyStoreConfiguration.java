package weblogic.security.utils;

public interface KeyStoreConfiguration {
   String getKeyStores();

   String getCustomIdentityKeyStoreFileName();

   String getCustomIdentityKeyStoreType();

   String getCustomIdentityKeyStorePassPhrase();

   String getCustomIdentityAlias();

   String getCustomIdentityPrivateKeyPassPhrase();

   String getOutboundPrivateKeyAlias();

   String getOutboundPrivateKeyPassPhrase();

   String getCustomTrustKeyStoreFileName();

   String getCustomTrustKeyStoreType();

   String getCustomTrustKeyStorePassPhrase();

   String getJavaStandardTrustKeyStorePassPhrase();
}
