package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.Signature;

@ThreadSafe
public interface SignatureValidationProvider {
   void validate(@Nonnull Signature var1, @Nonnull Credential var2) throws SignatureException;
}
