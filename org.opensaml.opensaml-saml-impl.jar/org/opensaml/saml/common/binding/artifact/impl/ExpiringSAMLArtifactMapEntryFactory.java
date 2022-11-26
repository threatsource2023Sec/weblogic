package org.opensaml.saml.common.binding.artifact.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.XMLRuntimeException;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.artifact.ExpiringSAMLArtifactMapEntry;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;

public class ExpiringSAMLArtifactMapEntryFactory implements SAMLArtifactMap.SAMLArtifactMapEntryFactory {
   @Nonnull
   public SAMLArtifactMap.SAMLArtifactMapEntry newEntry(@Nonnull @NotEmpty String artifact, @Nonnull @NotEmpty String issuerId, @Nonnull @NotEmpty String relyingPartyId, @Nonnull SAMLObject samlMessage) {
      try {
         return new ExpiringSAMLArtifactMapEntry(artifact, issuerId, relyingPartyId, samlMessage);
      } catch (UnmarshallingException | MarshallingException var6) {
         throw new XMLRuntimeException("Error creating BasicSAMLArtifactMapEntry", var6);
      }
   }
}
