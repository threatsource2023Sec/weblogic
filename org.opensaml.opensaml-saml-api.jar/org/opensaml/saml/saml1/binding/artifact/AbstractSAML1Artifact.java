package org.opensaml.saml.saml1.binding.artifact;

import org.opensaml.saml.common.binding.artifact.AbstractSAMLArtifact;

public abstract class AbstractSAML1Artifact extends AbstractSAMLArtifact implements SAML1Artifact {
   protected AbstractSAML1Artifact(byte[] typeCode) {
      super(typeCode);
   }
}
