package org.python.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.util.Arrays;

public class KTSParameterSpec implements AlgorithmParameterSpec {
   private final String wrappingKeyAlgorithm;
   private final int keySizeInBits;
   private final AlgorithmParameterSpec parameterSpec;
   private final AlgorithmIdentifier kdfAlgorithm;
   private byte[] otherInfo;

   private KTSParameterSpec(String var1, int var2, AlgorithmParameterSpec var3, AlgorithmIdentifier var4, byte[] var5) {
      this.wrappingKeyAlgorithm = var1;
      this.keySizeInBits = var2;
      this.parameterSpec = var3;
      this.kdfAlgorithm = var4;
      this.otherInfo = var5;
   }

   public String getKeyAlgorithmName() {
      return this.wrappingKeyAlgorithm;
   }

   public int getKeySize() {
      return this.keySizeInBits;
   }

   public AlgorithmParameterSpec getParameterSpec() {
      return this.parameterSpec;
   }

   public AlgorithmIdentifier getKdfAlgorithm() {
      return this.kdfAlgorithm;
   }

   public byte[] getOtherInfo() {
      return Arrays.clone(this.otherInfo);
   }

   // $FF: synthetic method
   KTSParameterSpec(String var1, int var2, AlgorithmParameterSpec var3, AlgorithmIdentifier var4, byte[] var5, Object var6) {
      this(var1, var2, var3, var4, var5);
   }

   public static final class Builder {
      private final String algorithmName;
      private final int keySizeInBits;
      private AlgorithmParameterSpec parameterSpec;
      private AlgorithmIdentifier kdfAlgorithm;
      private byte[] otherInfo;

      public Builder(String var1, int var2) {
         this(var1, var2, (byte[])null);
      }

      public Builder(String var1, int var2, byte[] var3) {
         this.algorithmName = var1;
         this.keySizeInBits = var2;
         this.kdfAlgorithm = new AlgorithmIdentifier(X9ObjectIdentifiers.id_kdf_kdf3, new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256));
         this.otherInfo = var3 == null ? new byte[0] : Arrays.clone(var3);
      }

      public Builder withParameterSpec(AlgorithmParameterSpec var1) {
         this.parameterSpec = var1;
         return this;
      }

      public Builder withKdfAlgorithm(AlgorithmIdentifier var1) {
         this.kdfAlgorithm = var1;
         return this;
      }

      public KTSParameterSpec build() {
         return new KTSParameterSpec(this.algorithmName, this.keySizeInBits, this.parameterSpec, this.kdfAlgorithm, this.otherInfo);
      }
   }
}
