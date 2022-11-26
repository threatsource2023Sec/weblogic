package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import org.opensaml.xmlsec.signature.Signature;

@ThreadSafe
public interface SignerProvider {
   void signObject(@Nonnull Signature var1) throws SignatureException;
}
