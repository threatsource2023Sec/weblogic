package org.opensaml.xmlsec.impl;

import java.util.Collections;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.EncryptionParametersResolver;

public class StaticEncryptionParametersResolver implements EncryptionParametersResolver {
   private EncryptionParameters params;

   public StaticEncryptionParametersResolver(EncryptionParameters parameters) {
      this.params = (EncryptionParameters)Constraint.isNotNull(parameters, "Parameters instance may not be null");
   }

   @Nonnull
   public Iterable resolve(CriteriaSet criteria) throws ResolverException {
      return Collections.singleton(this.params);
   }

   @Nonnull
   public EncryptionParameters resolveSingle(CriteriaSet criteria) throws ResolverException {
      return this.params;
   }
}
