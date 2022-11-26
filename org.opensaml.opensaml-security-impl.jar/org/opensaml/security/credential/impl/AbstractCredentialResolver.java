package org.opensaml.security.credential.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;

public abstract class AbstractCredentialResolver implements CredentialResolver {
   @Nullable
   public Credential resolveSingle(@Nullable CriteriaSet criteriaSet) throws ResolverException {
      Iterable creds = this.resolve(criteriaSet);
      return creds.iterator().hasNext() ? (Credential)creds.iterator().next() : null;
   }

   @Nonnull
   public abstract Iterable resolve(@Nullable CriteriaSet var1) throws ResolverException;
}
