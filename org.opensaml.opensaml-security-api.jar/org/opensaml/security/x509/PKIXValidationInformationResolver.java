package org.opensaml.security.x509;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import net.shibboleth.utilities.java.support.resolver.ResolverException;

public interface PKIXValidationInformationResolver extends Resolver {
   @Nonnull
   Set resolveTrustedNames(@Nullable CriteriaSet var1) throws ResolverException;

   boolean supportsTrustedNameResolution();
}
