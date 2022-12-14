package org.python.bouncycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class SPHINCS256KeyGenParameterSpec implements AlgorithmParameterSpec {
   public static final String SHA512_256 = "SHA512-256";
   public static final String SHA3_256 = "SHA3-256";
   private final String treeHash;

   public SPHINCS256KeyGenParameterSpec() {
      this("SHA512-256");
   }

   public SPHINCS256KeyGenParameterSpec(String var1) {
      this.treeHash = var1;
   }

   public String getTreeDigest() {
      return this.treeHash;
   }
}
