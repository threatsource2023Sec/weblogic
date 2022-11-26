package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.BlockEncryptionAlgorithm;

public final class BlockEncryptionAES128GCM implements BlockEncryptionAlgorithm {
   @Nonnull
   public String getKey() {
      return "AES";
   }

   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2009/xmlenc11#aes128-gcm";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.BlockEncryption;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return String.format("%s/%s/%s", this.getKey(), this.getCipherMode(), this.getPadding());
   }

   @Nonnull
   public Integer getKeyLength() {
      return 128;
   }

   @Nonnull
   public String getCipherMode() {
      return "GCM";
   }

   @Nonnull
   public String getPadding() {
      return "NoPadding";
   }
}
