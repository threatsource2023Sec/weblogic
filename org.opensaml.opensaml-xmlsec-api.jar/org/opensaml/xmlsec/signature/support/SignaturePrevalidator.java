package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.signature.Signature;

public interface SignaturePrevalidator {
   void validate(@Nonnull Signature var1) throws SignatureException;
}
