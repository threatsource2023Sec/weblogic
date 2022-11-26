package org.opensaml.saml.saml1.binding.artifact;

import java.util.Arrays;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.common.binding.artifact.SAMLSourceLocationArtifact;

public class SAML1ArtifactType0002 extends AbstractSAML1Artifact implements SAMLSourceLocationArtifact {
   public static final byte[] TYPE_CODE = new byte[]{0, 2};
   private byte[] assertionHandle;
   private String sourceLocation;

   public SAML1ArtifactType0002() {
      super(TYPE_CODE);
   }

   public SAML1ArtifactType0002(byte[] handle, String location) {
      super(TYPE_CODE);
      this.setAssertionHandle(handle);
      this.setSourceLocation(location);
   }

   public static SAML1ArtifactType0002 parseArtifact(byte[] artifact) {
      byte[] typeCode = new byte[]{artifact[0], artifact[1]};
      if (!Arrays.equals(typeCode, TYPE_CODE)) {
         throw new IllegalArgumentException("Artifact is not of appropriate type.");
      } else {
         byte[] assertionHandle = new byte[20];
         System.arraycopy(artifact, 2, assertionHandle, 0, 20);
         int locationLength = artifact.length - 22;
         byte[] sourceLocation = new byte[locationLength];
         System.arraycopy(artifact, 22, sourceLocation, 0, locationLength);
         return new SAML1ArtifactType0002(assertionHandle, new String(sourceLocation));
      }
   }

   public byte[] getAssertionHandle() {
      return this.assertionHandle;
   }

   public void setAssertionHandle(byte[] handle) {
      if (handle.length != 20) {
         throw new IllegalArgumentException("Artifact assertion handle must be 20 bytes long");
      } else {
         this.assertionHandle = handle;
      }
   }

   public String getSourceLocation() {
      return this.sourceLocation;
   }

   protected void setSourceLocation(String newLocation) {
      String location = StringSupport.trimOrNull(newLocation);
      if (location == null) {
         throw new IllegalArgumentException("Artifact source location may not be a null or empty string");
      } else {
         this.sourceLocation = location;
      }
   }

   public byte[] getRemainingArtifact() {
      byte[] location = this.getSourceLocation().getBytes();
      byte[] remainingArtifact = new byte[20 + location.length];
      System.arraycopy(this.getAssertionHandle(), 0, remainingArtifact, 0, 20);
      System.arraycopy(location, 0, remainingArtifact, 20, location.length);
      return remainingArtifact;
   }
}
