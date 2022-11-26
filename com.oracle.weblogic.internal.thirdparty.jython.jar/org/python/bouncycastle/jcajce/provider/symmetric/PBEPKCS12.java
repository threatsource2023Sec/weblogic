package org.python.bouncycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public class PBEPKCS12 {
   private PBEPKCS12() {
   }

   public static class AlgParams extends BaseAlgorithmParameters {
      PKCS12PBEParams params;

      protected byte[] engineGetEncoded() {
         try {
            return this.params.getEncoded("DER");
         } catch (IOException var2) {
            throw new RuntimeException("Oooops! " + var2.toString());
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         return this.isASN1FormatString(var1) ? this.engineGetEncoded() : null;
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if (var1 == PBEParameterSpec.class) {
            return new PBEParameterSpec(this.params.getIV(), this.params.getIterations().intValue());
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to PKCS12 PBE parameters object.");
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if (!(var1 instanceof PBEParameterSpec)) {
            throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PKCS12 PBE parameters algorithm parameters object");
         } else {
            PBEParameterSpec var2 = (PBEParameterSpec)var1;
            this.params = new PKCS12PBEParams(var2.getSalt(), var2.getIterationCount());
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         this.params = PKCS12PBEParams.getInstance(ASN1Primitive.fromByteArray(var1));
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if (this.isASN1FormatString(var2)) {
            this.engineInit(var1);
         } else {
            throw new IOException("Unknown parameters format in PKCS12 PBE parameters object");
         }
      }

      protected String engineToString() {
         return "PKCS12 PBE Parameters";
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = PBEPKCS12.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("AlgorithmParameters.PKCS12PBE", PREFIX + "$AlgParams");
      }
   }
}
