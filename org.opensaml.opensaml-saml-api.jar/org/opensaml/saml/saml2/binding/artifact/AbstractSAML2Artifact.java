package org.opensaml.saml.saml2.binding.artifact;

import javax.annotation.Nonnull;
import org.opensaml.saml.common.binding.artifact.AbstractSAMLArtifact;

public abstract class AbstractSAML2Artifact extends AbstractSAMLArtifact implements SAML2Artifact {
   private byte[] endpointIndex;

   protected AbstractSAML2Artifact(byte[] artifactType) {
      super(artifactType);
   }

   public AbstractSAML2Artifact(byte[] artifactType, byte[] index) {
      super(artifactType);
      this.setEndpointIndex(index);
   }

   public byte[] getArtifactBytes() {
      byte[] remainingArtifact = this.getRemainingArtifact();
      byte[] artifact = new byte[4 + remainingArtifact.length];
      System.arraycopy(this.getTypeCode(), 0, artifact, 0, 2);
      System.arraycopy(this.getEndpointIndex(), 0, artifact, 2, 2);
      System.arraycopy(remainingArtifact, 0, artifact, 4, remainingArtifact.length);
      return artifact;
   }

   @Nonnull
   public byte[] getEndpointIndex() {
      return this.endpointIndex;
   }

   public void setEndpointIndex(byte[] newIndex) {
      if (newIndex.length != 2) {
         throw new IllegalArgumentException("Artifact endpoint index must be two bytes long");
      } else {
         this.endpointIndex = newIndex;
      }
   }
}
