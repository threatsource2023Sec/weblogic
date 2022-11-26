package org.opensaml.xmlsec.keyinfo.impl;

import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.impl.AbstractChainingCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public class ChainingKeyInfoCredentialResolver extends AbstractChainingCredentialResolver implements KeyInfoCredentialResolver {
   public ChainingKeyInfoCredentialResolver(@Nonnull List resolverChain) {
      super(resolverChain);
   }
}
