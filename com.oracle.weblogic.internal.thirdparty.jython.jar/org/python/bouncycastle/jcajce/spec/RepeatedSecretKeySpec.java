package org.python.bouncycastle.jcajce.spec;

import javax.crypto.SecretKey;

public class RepeatedSecretKeySpec implements SecretKey {
   private String algorithm;

   public RepeatedSecretKeySpec(String var1) {
      this.algorithm = var1;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getFormat() {
      return null;
   }

   public byte[] getEncoded() {
      return null;
   }
}
