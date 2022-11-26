package org.opensaml.security.credential.impl;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;

public class CollectionCredentialResolver extends AbstractCriteriaFilteringCredentialResolver {
   private final Collection collection;

   public CollectionCredentialResolver() {
      this.collection = new ArrayList();
   }

   public CollectionCredentialResolver(@Nonnull Collection credentials) {
      this.collection = credentials;
   }

   @Nonnull
   public Collection getCollection() {
      return this.collection;
   }

   @Nonnull
   protected Iterable resolveFromSource(@Nullable CriteriaSet criteriaSet) throws ResolverException {
      return this.collection;
   }
}
