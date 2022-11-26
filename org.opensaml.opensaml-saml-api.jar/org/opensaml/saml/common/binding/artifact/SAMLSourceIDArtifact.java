package org.opensaml.saml.common.binding.artifact;

import javax.annotation.Nonnull;

public interface SAMLSourceIDArtifact extends SAMLArtifact {
   @Nonnull
   byte[] getSourceID();
}
