package org.opensaml.security.credential.impl;

import java.util.List;
import javax.annotation.Nonnull;

public class ChainingCredentialResolver extends AbstractChainingCredentialResolver {
   public ChainingCredentialResolver(@Nonnull List resolverChain) {
      super(resolverChain);
   }
}
