package com.bea.security.saml2.artifact;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.util.SAML2Utils;
import java.security.NoSuchAlgorithmException;

public class SAMLArtifact {
   private byte[] artifact = null;

   public SAMLArtifact(byte[] artifact) throws IllegalArgumentException {
      if (artifact != null && artifact.length == 44) {
         this.artifact = artifact;
      } else {
         throw new IllegalArgumentException(Saml2Logger.getSAML2ArtifactConstructFail("artifact", "44"));
      }
   }

   public SAMLArtifact(short endPointIndex, byte[] sourceId, byte[] messageHandle) throws IllegalArgumentException, NoSuchAlgorithmException {
      if (sourceId != null && sourceId.length == 20) {
         if (messageHandle != null && messageHandle.length == 20) {
            byte[] eIndex = this.short2bytes(endPointIndex);
            this.artifact = new byte[44];
            this.artifact[0] = 0;
            this.artifact[1] = 4;
            System.arraycopy(eIndex, 0, this.artifact, 2, 2);
            System.arraycopy(sourceId, 0, this.artifact, 4, 20);
            System.arraycopy(messageHandle, 0, this.artifact, 24, 20);
         } else {
            throw new IllegalArgumentException(Saml2Logger.getSAML2ArtifactConstructFail("MessageHandle", "20"));
         }
      } else {
         throw new IllegalArgumentException(Saml2Logger.getSAML2ArtifactConstructFail("SourceID", "20"));
      }
   }

   public short getEndPointIndex() {
      short result = false;
      byte[] eIndex = new byte[2];
      System.arraycopy(this.artifact, 2, eIndex, 0, 2);
      short result = this.bytes2short(eIndex);
      return result;
   }

   public byte[] getSourceId() {
      byte[] result = new byte[20];
      System.arraycopy(this.artifact, 4, result, 0, 20);
      return result;
   }

   public byte[] getMessageHandle() {
      byte[] result = new byte[20];
      System.arraycopy(this.artifact, 24, result, 0, 20);
      return result;
   }

   public String toBase64String() {
      return SAML2Utils.base64Encode(this.artifact);
   }

   private short bytes2short(byte[] b) {
      int ch1 = b[0];
      int ch2 = b[1];
      return (short)((ch1 << 8) + (255 & ch2));
   }

   private byte[] short2bytes(short v) {
      byte[] b = new byte[]{(byte)(v >>> 8 & 255), (byte)(v & 255)};
      return b;
   }
}
