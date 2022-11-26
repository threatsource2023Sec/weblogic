package org.opensaml.xmlsec.keyinfo.impl;

import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.StaticCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public class StaticKeyInfoCredentialResolver extends StaticCredentialResolver implements KeyInfoCredentialResolver {
   public StaticKeyInfoCredentialResolver(@Nonnull List credentials) {
      super(credentials);
   }

   public StaticKeyInfoCredentialResolver(@Nonnull Credential credential) {
      super(credential);
   }
}
