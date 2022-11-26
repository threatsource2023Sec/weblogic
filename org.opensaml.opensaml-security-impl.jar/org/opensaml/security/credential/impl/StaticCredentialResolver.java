package org.opensaml.security.credential.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;

public class StaticCredentialResolver extends AbstractCredentialResolver {
   private List creds;

   public StaticCredentialResolver(@Nonnull List credentials) {
      Constraint.isNotNull(credentials, "Input credentials list cannot be null");
      this.creds = new ArrayList(credentials);
   }

   public StaticCredentialResolver(@Nonnull Credential credential) {
      Constraint.isNotNull(credential, "Input credential cannot be null");
      this.creds = new ArrayList();
      this.creds.add(credential);
   }

   @Nonnull
   public Iterable resolve(@Nullable CriteriaSet criteria) throws ResolverException {
      return this.creds;
   }
}
