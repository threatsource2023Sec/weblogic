package org.python.bouncycastle.pkcs.bc;

import java.io.InputStream;
import org.python.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.crypto.digests.SHA1Digest;
import org.python.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import org.python.bouncycastle.crypto.io.CipherInputStream;
import org.python.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.InputDecryptor;
import org.python.bouncycastle.operator.InputDecryptorProvider;

public class BcPKCS12PBEInputDecryptorProviderBuilder {
   private ExtendedDigest digest;

   public BcPKCS12PBEInputDecryptorProviderBuilder() {
      this(new SHA1Digest());
   }

   public BcPKCS12PBEInputDecryptorProviderBuilder(ExtendedDigest var1) {
      this.digest = var1;
   }

   public InputDecryptorProvider build(final char[] var1) {
      return new InputDecryptorProvider() {
         public InputDecryptor get(final AlgorithmIdentifier var1x) {
            final PaddedBufferedBlockCipher var2 = PKCS12PBEUtils.getEngine(var1x.getAlgorithm());
            PKCS12PBEParams var3 = PKCS12PBEParams.getInstance(var1x.getParameters());
            CipherParameters var4 = PKCS12PBEUtils.createCipherParameters(var1x.getAlgorithm(), BcPKCS12PBEInputDecryptorProviderBuilder.this.digest, var2.getBlockSize(), var3, var1);
            var2.init(false, var4);
            return new InputDecryptor() {
               public AlgorithmIdentifier getAlgorithmIdentifier() {
                  return var1x;
               }

               public InputStream getInputStream(InputStream var1xx) {
                  return new CipherInputStream(var1xx, var2);
               }

               public GenericKey getKey() {
                  return new GenericKey(PKCS12ParametersGenerator.PKCS12PasswordToBytes(var1));
               }
            };
         }
      };
   }
}
