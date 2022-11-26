package org.opensaml.xmlsec;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import org.opensaml.xmlsec.encryption.support.RSAOAEPParameters;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;

public interface EncryptionConfiguration extends WhitelistBlacklistConfiguration {
   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   List getDataEncryptionCredentials();

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   List getDataEncryptionAlgorithms();

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   List getKeyTransportEncryptionCredentials();

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   List getKeyTransportEncryptionAlgorithms();

   @Nullable
   NamedKeyInfoGeneratorManager getDataKeyInfoGeneratorManager();

   @Nullable
   NamedKeyInfoGeneratorManager getKeyTransportKeyInfoGeneratorManager();

   @Nullable
   RSAOAEPParameters getRSAOAEPParameters();

   boolean isRSAOAEPParametersMerge();

   @Nullable
   KeyTransportAlgorithmPredicate getKeyTransportAlgorithmPredicate();
}
