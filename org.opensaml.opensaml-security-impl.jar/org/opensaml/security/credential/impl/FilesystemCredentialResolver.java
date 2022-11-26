package org.opensaml.security.credential.impl;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;

public class FilesystemCredentialResolver extends AbstractCriteriaFilteringCredentialResolver {
   public FilesystemCredentialResolver(@Nonnull String credentialDirectory, @Nonnull Map passwords) {
   }

   @Nonnull
   protected Iterable resolveFromSource(@Nullable CriteriaSet criteriaSet) {
      throw new UnsupportedOperationException("Functionality not yet implemented");
   }
}
