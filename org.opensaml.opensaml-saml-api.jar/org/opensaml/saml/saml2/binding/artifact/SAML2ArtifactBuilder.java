package org.opensaml.saml.saml2.binding.artifact;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.MessageContext;

public interface SAML2ArtifactBuilder {
   @Nullable
   AbstractSAML2Artifact buildArtifact(@Nonnull MessageContext var1);

   @Nullable
   AbstractSAML2Artifact buildArtifact(@Nonnull @NotEmpty byte[] var1);
}
