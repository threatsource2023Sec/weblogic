package org.python.bouncycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.oiw.ElGamalParameter;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.python.bouncycastle.jce.spec.ElGamalParameterSpec;

public class AlgorithmParametersSpi extends BaseAlgorithmParameters {
   ElGamalParameterSpec currentSpec;

   protected byte[] engineGetEncoded() {
      ElGamalParameter var1 = new ElGamalParameter(this.currentSpec.getP(), this.currentSpec.getG());

      try {
         return var1.getEncoded("DER");
      } catch (IOException var3) {
         throw new RuntimeException("Error encoding ElGamalParameters");
      }
   }

   protected byte[] engineGetEncoded(String var1) {
      return !this.isASN1FormatString(var1) && !var1.equalsIgnoreCase("X.509") ? null : this.engineGetEncoded();
   }

   protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
      if (var1 != ElGamalParameterSpec.class && var1 != AlgorithmParameterSpec.class) {
         if (var1 == DHParameterSpec.class) {
            return new DHParameterSpec(this.currentSpec.getP(), this.currentSpec.getG());
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
         }
      } else {
         return this.currentSpec;
      }
   }

   protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
      if (!(var1 instanceof ElGamalParameterSpec) && !(var1 instanceof DHParameterSpec)) {
         throw new InvalidParameterSpecException("DHParameterSpec required to initialise a ElGamal algorithm parameters object");
      } else {
         if (var1 instanceof ElGamalParameterSpec) {
            this.currentSpec = (ElGamalParameterSpec)var1;
         } else {
            DHParameterSpec var2 = (DHParameterSpec)var1;
            this.currentSpec = new ElGamalParameterSpec(var2.getP(), var2.getG());
         }

      }
   }

   protected void engineInit(byte[] var1) throws IOException {
      try {
         ElGamalParameter var2 = ElGamalParameter.getInstance(ASN1Primitive.fromByteArray(var1));
         this.currentSpec = new ElGamalParameterSpec(var2.getP(), var2.getG());
      } catch (ClassCastException var3) {
         throw new IOException("Not a valid ElGamal Parameter encoding.");
      } catch (ArrayIndexOutOfBoundsException var4) {
         throw new IOException("Not a valid ElGamal Parameter encoding.");
      }
   }

   protected void engineInit(byte[] var1, String var2) throws IOException {
      if (!this.isASN1FormatString(var2) && !var2.equalsIgnoreCase("X.509")) {
         throw new IOException("Unknown parameter format " + var2);
      } else {
         this.engineInit(var1);
      }
   }

   protected String engineToString() {
      return "ElGamal Parameters";
   }
}
