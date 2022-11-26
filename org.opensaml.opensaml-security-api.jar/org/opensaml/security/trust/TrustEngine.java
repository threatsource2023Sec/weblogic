package org.opensaml.security.trust;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.security.SecurityException;

public interface TrustEngine {
   boolean validate(@Nonnull Object var1, @Nullable CriteriaSet var2) throws SecurityException;
}
