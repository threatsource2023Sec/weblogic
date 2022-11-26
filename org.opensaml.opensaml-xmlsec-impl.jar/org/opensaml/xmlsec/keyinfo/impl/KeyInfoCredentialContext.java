package org.opensaml.xmlsec.keyinfo.impl;

import javax.annotation.Nonnull;
import org.opensaml.security.credential.CredentialContext;
import org.opensaml.xmlsec.signature.KeyInfo;

public class KeyInfoCredentialContext implements CredentialContext {
   private final KeyInfo keyInfo;

   public KeyInfoCredentialContext(@Nonnull KeyInfo ki) {
      this.keyInfo = ki;
   }

   @Nonnull
   public KeyInfo getKeyInfo() {
      return this.keyInfo;
   }
}
