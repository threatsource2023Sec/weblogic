package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import org.opensaml.security.credential.CredentialContext;
import org.opensaml.xmlsec.signature.Signature;

public class XMLSignatureCredentialContext implements CredentialContext {
   private final Signature sig;

   public XMLSignatureCredentialContext(@Nonnull Signature signature) {
      this.sig = signature;
   }

   @Nonnull
   public Signature getSignature() {
      return this.sig;
   }
}
