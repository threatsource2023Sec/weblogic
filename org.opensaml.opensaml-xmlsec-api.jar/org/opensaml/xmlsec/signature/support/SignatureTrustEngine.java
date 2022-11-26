package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public interface SignatureTrustEngine extends TrustEngine {
   @Nullable
   KeyInfoCredentialResolver getKeyInfoResolver();

   boolean validate(@Nonnull byte[] var1, @Nonnull byte[] var2, @Nonnull String var3, @Nullable CriteriaSet var4, @Nullable Credential var5) throws SecurityException;
}
