package org.cryptacular.pbe;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class OpenSSLEncryptionScheme extends AbstractEncryptionScheme {
   public OpenSSLEncryptionScheme(BufferedBlockCipher cipher, byte[] salt, int keyBitLength, char[] password) {
      OpenSSLPBEParametersGenerator generator = new OpenSSLPBEParametersGenerator();
      generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password), salt);
      this.setCipher(cipher);
      this.setCipherParameters(generator.generateDerivedParameters(keyBitLength));
   }

   public OpenSSLEncryptionScheme(OpenSSLAlgorithm algorithm, byte[] iv, char[] password) {
      byte[] salt = iv;
      if (iv.length > 8) {
         salt = new byte[8];
         System.arraycopy(iv, 0, salt, 0, 8);
      }

      OpenSSLPBEParametersGenerator generator = new OpenSSLPBEParametersGenerator();
      generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password), salt);
      this.setCipher(algorithm.getCipherSpec().newInstance());
      this.setCipherParameters(new ParametersWithIV(generator.generateDerivedParameters(algorithm.getCipherSpec().getKeyLength()), iv));
   }
}
