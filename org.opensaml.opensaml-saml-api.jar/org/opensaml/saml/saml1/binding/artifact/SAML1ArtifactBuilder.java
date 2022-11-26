package org.opensaml.saml.saml1.binding.artifact;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.saml1.core.Assertion;

public interface SAML1ArtifactBuilder {
   @Nullable
   AbstractSAML1Artifact buildArtifact(@Nonnull MessageContext var1, @Nonnull Assertion var2);

   @Nullable
   AbstractSAML1Artifact buildArtifact(@Nonnull @NotEmpty byte[] var1);
}
