package org.opensaml.saml.common.binding.artifact;

import javax.annotation.Nonnull;

public interface SAMLArtifact {
   @Nonnull
   byte[] getArtifactBytes();

   @Nonnull
   byte[] getTypeCode();
}
