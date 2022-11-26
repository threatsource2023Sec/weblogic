package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.BlockEncryptionAlgorithm;

public final class BlockEncryptionAES256CBC implements BlockEncryptionAlgorithm {
   @Nonnull
   public String getKey() {
      return "AES";
   }

   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
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
      return 256;
   }

   @Nonnull
   public String getCipherMode() {
      return "CBC";
   }

   @Nonnull
   public String getPadding() {
      return "ISO10126Padding";
   }
}
