package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;

public class DefaultTlsEncryptionCredentials extends AbstractTlsEncryptionCredentials {
   protected TlsContext context;
   protected Certificate certificate;
   protected AsymmetricKeyParameter privateKey;

   public DefaultTlsEncryptionCredentials(TlsContext var1, Certificate var2, AsymmetricKeyParameter var3) {
      if (var2 == null) {
         throw new IllegalArgumentException("'certificate' cannot be null");
      } else if (var2.isEmpty()) {
         throw new IllegalArgumentException("'certificate' cannot be empty");
      } else if (var3 == null) {
         throw new IllegalArgumentException("'privateKey' cannot be null");
      } else if (!var3.isPrivate()) {
         throw new IllegalArgumentException("'privateKey' must be private");
      } else if (var3 instanceof RSAKeyParameters) {
         this.context = var1;
         this.certificate = var2;
         this.privateKey = var3;
      } else {
         throw new IllegalArgumentException("'privateKey' type not supported: " + var3.getClass().getName());
      }
   }

   public Certificate getCertificate() {
      return this.certificate;
   }

   public byte[] decryptPreMasterSecret(byte[] var1) throws IOException {
      return TlsRSAUtils.safeDecryptPreMasterSecret(this.context, (RSAKeyParameters)this.privateKey, var1);
   }
}
