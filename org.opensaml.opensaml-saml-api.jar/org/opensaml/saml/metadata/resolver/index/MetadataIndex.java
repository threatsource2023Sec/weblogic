package org.opensaml.saml.metadata.resolver.index;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public interface MetadataIndex {
   @Nullable
   @NonnullElements
   @Unmodifiable
   @NotLive
   Set generateKeys(@Nonnull EntityDescriptor var1);

   @Nullable
   @NonnullElements
   @Unmodifiable
   @NotLive
   Set generateKeys(@Nonnull CriteriaSet var1);
}
