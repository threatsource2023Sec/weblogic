package org.opensaml.xmlsec.keyinfo.impl;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.impl.CollectionCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public class CollectionKeyInfoCredentialResolver extends CollectionCredentialResolver implements KeyInfoCredentialResolver {
   public CollectionKeyInfoCredentialResolver() {
      this(new ArrayList());
   }

   public CollectionKeyInfoCredentialResolver(@Nonnull Collection credentials) {
      super(credentials);
   }
}
