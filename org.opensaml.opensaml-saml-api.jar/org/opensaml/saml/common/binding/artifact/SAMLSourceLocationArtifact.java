package org.opensaml.saml.common.binding.artifact;

import javax.annotation.Nonnull;

public interface SAMLSourceLocationArtifact extends SAMLArtifact {
   @Nonnull
   String getSourceLocation();
}
