package org.opensaml.xmlsec;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public interface DecryptionConfiguration extends WhitelistBlacklistConfiguration {
   @Nullable
   KeyInfoCredentialResolver getDataKeyInfoCredentialResolver();

   @Nullable
   KeyInfoCredentialResolver getKEKKeyInfoCredentialResolver();

   @Nullable
   EncryptedKeyResolver getEncryptedKeyResolver();
}
