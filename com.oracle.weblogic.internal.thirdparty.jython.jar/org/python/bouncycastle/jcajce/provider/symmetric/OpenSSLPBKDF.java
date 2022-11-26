package org.python.bouncycastle.jcajce.provider.symmetric;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import org.python.bouncycastle.util.Strings;

public final class OpenSSLPBKDF {
   private OpenSSLPBKDF() {
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = OpenSSLPBKDF.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("SecretKeyFactory.PBKDF-OPENSSL", PREFIX + "$PBKDF");
      }
   }

   public static class PBKDF extends BaseSecretKeyFactory {
      public PBKDF() {
         super("PBKDF-OpenSSL", (ASN1ObjectIdentifier)null);
      }

      protected SecretKey engineGenerateSecret(KeySpec var1) throws InvalidKeySpecException {
         if (var1 instanceof PBEKeySpec) {
            PBEKeySpec var2 = (PBEKeySpec)var1;
            if (var2.getSalt() == null) {
               throw new InvalidKeySpecException("missing required salt");
            } else if (var2.getIterationCount() <= 0) {
               throw new InvalidKeySpecException("positive iteration count required: " + var2.getIterationCount());
            } else if (var2.getKeyLength() <= 0) {
               throw new InvalidKeySpecException("positive key length required: " + var2.getKeyLength());
            } else if (var2.getPassword().length == 0) {
               throw new IllegalArgumentException("password empty");
            } else {
               OpenSSLPBEParametersGenerator var3 = new OpenSSLPBEParametersGenerator();
               var3.init(Strings.toByteArray(var2.getPassword()), var2.getSalt());
               return new SecretKeySpec(((KeyParameter)var3.generateDerivedParameters(var2.getKeyLength())).getKey(), "OpenSSLPBKDF");
            }
         } else {
            throw new InvalidKeySpecException("Invalid KeySpec");
         }
      }
   }
}
