package org.opensaml.saml.saml2.binding.artifact;

import java.util.Arrays;
import org.opensaml.saml.common.binding.artifact.SAMLSourceIDArtifact;

public class SAML2ArtifactType0004 extends AbstractSAML2Artifact implements SAMLSourceIDArtifact {
   public static final byte[] TYPE_CODE = new byte[]{0, 4};
   private byte[] sourceID;
   private byte[] messageHandle;

   public SAML2ArtifactType0004() {
      super(TYPE_CODE);
   }

   public SAML2ArtifactType0004(byte[] endpointIndex, byte[] source, byte[] handle) {
      super(TYPE_CODE, endpointIndex);
      this.setSourceID(source);
      this.setMessageHandle(handle);
   }

   public static SAML2ArtifactType0004 parseArtifact(byte[] artifact) {
      if (artifact.length != 44) {
         throw new IllegalArgumentException("Artifact length must be 44 bytes it was " + artifact.length + "bytes");
      } else {
         byte[] typeCode = new byte[]{artifact[0], artifact[1]};
         if (!Arrays.equals(typeCode, TYPE_CODE)) {
            throw new IllegalArgumentException("Illegal artifact type code");
         } else {
            byte[] endpointIndex = new byte[]{artifact[2], artifact[3]};
            byte[] sourceID = new byte[20];
            System.arraycopy(artifact, 4, sourceID, 0, 20);
            byte[] messageHandle = new byte[20];
            System.arraycopy(artifact, 24, messageHandle, 0, 20);
            return new SAML2ArtifactType0004(endpointIndex, sourceID, messageHandle);
         }
      }
   }

   public byte[] getSourceID() {
      return this.sourceID;
   }

   public void setSourceID(byte[] newSourceID) {
      if (newSourceID.length != 20) {
         throw new IllegalArgumentException("Artifact source ID must be 20 bytes long");
      } else {
         this.sourceID = newSourceID;
      }
   }

   public byte[] getMessageHandle() {
      return this.messageHandle;
   }

   public void setMessageHandle(byte[] handle) {
      if (handle.length != 20) {
         throw new IllegalArgumentException("Artifact message handle must be 20 bytes long");
      } else {
         this.messageHandle = handle;
      }
   }

   public byte[] getRemainingArtifact() {
      byte[] remainingArtifact = new byte[40];
      System.arraycopy(this.getSourceID(), 0, remainingArtifact, 0, 20);
      System.arraycopy(this.getMessageHandle(), 0, remainingArtifact, 20, 20);
      return remainingArtifact;
   }
}
