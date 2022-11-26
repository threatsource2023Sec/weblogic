package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;

public class DefaultTlsSignerCredentials extends AbstractTlsSignerCredentials {
   protected TlsContext context;
   protected Certificate certificate;
   protected AsymmetricKeyParameter privateKey;
   protected SignatureAndHashAlgorithm signatureAndHashAlgorithm;
   protected TlsSigner signer;

   public DefaultTlsSignerCredentials(TlsContext var1, Certificate var2, AsymmetricKeyParameter var3) {
      this(var1, var2, var3, (SignatureAndHashAlgorithm)null);
   }

   public DefaultTlsSignerCredentials(TlsContext var1, Certificate var2, AsymmetricKeyParameter var3, SignatureAndHashAlgorithm var4) {
      if (var2 == null) {
         throw new IllegalArgumentException("'certificate' cannot be null");
      } else if (var2.isEmpty()) {
         throw new IllegalArgumentException("'certificate' cannot be empty");
      } else if (var3 == null) {
         throw new IllegalArgumentException("'privateKey' cannot be null");
      } else if (!var3.isPrivate()) {
         throw new IllegalArgumentException("'privateKey' must be private");
      } else if (TlsUtils.isTLSv12(var1) && var4 == null) {
         throw new IllegalArgumentException("'signatureAndHashAlgorithm' cannot be null for (D)TLS 1.2+");
      } else {
         if (var3 instanceof RSAKeyParameters) {
            this.signer = new TlsRSASigner();
         } else if (var3 instanceof DSAPrivateKeyParameters) {
            this.signer = new TlsDSSSigner();
         } else {
            if (!(var3 instanceof ECPrivateKeyParameters)) {
               throw new IllegalArgumentException("'privateKey' type not supported: " + var3.getClass().getName());
            }

            this.signer = new TlsECDSASigner();
         }

         this.signer.init(var1);
         this.context = var1;
         this.certificate = var2;
         this.privateKey = var3;
         this.signatureAndHashAlgorithm = var4;
      }
   }

   public Certificate getCertificate() {
      return this.certificate;
   }

   public byte[] generateCertificateSignature(byte[] var1) throws IOException {
      try {
         return TlsUtils.isTLSv12(this.context) ? this.signer.generateRawSignature(this.signatureAndHashAlgorithm, this.privateKey, var1) : this.signer.generateRawSignature(this.privateKey, var1);
      } catch (CryptoException var3) {
         throw new TlsFatalAlert((short)80, var3);
      }
   }

   public SignatureAndHashAlgorithm getSignatureAndHashAlgorithm() {
      return this.signatureAndHashAlgorithm;
   }
}
