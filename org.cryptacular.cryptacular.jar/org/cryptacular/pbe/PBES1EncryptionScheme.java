package org.cryptacular.pbe;

import org.bouncycastle.asn1.pkcs.PBEParameter;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator;

public class PBES1EncryptionScheme extends AbstractEncryptionScheme {
   public static final int KEY_LENGTH = 64;
   public static final int IV_LENGTH = 64;

   public PBES1EncryptionScheme(PBES1Algorithm alg, PBEParameter params, char[] password) {
      byte[] salt = params.getSalt();
      int iterations = params.getIterationCount().intValue();
      PKCS5S1ParametersGenerator generator = new PKCS5S1ParametersGenerator(alg.getDigestSpec().newInstance());
      generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password), salt, iterations);
      this.setCipher(alg.getCipherSpec().newInstance());
      this.setCipherParameters(generator.generateDerivedParameters(64, 64));
   }
}
