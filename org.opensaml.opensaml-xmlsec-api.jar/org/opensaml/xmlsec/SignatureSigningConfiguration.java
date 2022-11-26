package org.opensaml.xmlsec;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;

public interface SignatureSigningConfiguration extends WhitelistBlacklistConfiguration {
   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   List getSigningCredentials();

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   List getSignatureAlgorithms();

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   List getSignatureReferenceDigestMethods();

   @Nullable
   String getSignatureCanonicalizationAlgorithm();

   @Nullable
   Integer getSignatureHMACOutputLength();

   @Nullable
   NamedKeyInfoGeneratorManager getKeyInfoGeneratorManager();
}
