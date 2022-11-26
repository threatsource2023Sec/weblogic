package net.shibboleth.utilities.java.support.resolver;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;

public interface Resolver {
   @Nonnull
   @NonnullElements
   Iterable resolve(@Nullable Object var1) throws ResolverException;

   @Nullable
   Object resolveSingle(@Nullable Object var1) throws ResolverException;
}
