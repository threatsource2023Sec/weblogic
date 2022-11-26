package org.opensaml.xmlsec.impl;

import java.util.Collections;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.DecryptionParametersResolver;

public class StaticDecryptionParametersResolver implements DecryptionParametersResolver {
   private DecryptionParameters params;

   public StaticDecryptionParametersResolver(DecryptionParameters parameters) {
      this.params = (DecryptionParameters)Constraint.isNotNull(parameters, "Parameters instance may not be null");
   }

   @Nonnull
   public Iterable resolve(CriteriaSet criteria) throws ResolverException {
      return Collections.singleton(this.params);
   }

   @Nonnull
   public DecryptionParameters resolveSingle(CriteriaSet criteria) throws ResolverException {
      return this.params;
   }
}
