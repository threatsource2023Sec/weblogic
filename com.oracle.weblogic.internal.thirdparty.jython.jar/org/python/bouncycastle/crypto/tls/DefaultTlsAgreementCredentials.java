package org.python.bouncycastle.crypto.tls;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.BasicAgreement;
import org.python.bouncycastle.crypto.agreement.DHBasicAgreement;
import org.python.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.util.BigIntegers;

public class DefaultTlsAgreementCredentials extends AbstractTlsAgreementCredentials {
   protected Certificate certificate;
   protected AsymmetricKeyParameter privateKey;
   protected BasicAgreement basicAgreement;
   protected boolean truncateAgreement;

   public DefaultTlsAgreementCredentials(Certificate var1, AsymmetricKeyParameter var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("'certificate' cannot be null");
      } else if (var1.isEmpty()) {
         throw new IllegalArgumentException("'certificate' cannot be empty");
      } else if (var2 == null) {
         throw new IllegalArgumentException("'privateKey' cannot be null");
      } else if (!var2.isPrivate()) {
         throw new IllegalArgumentException("'privateKey' must be private");
      } else {
         if (var2 instanceof DHPrivateKeyParameters) {
            this.basicAgreement = new DHBasicAgreement();
            this.truncateAgreement = true;
         } else {
            if (!(var2 instanceof ECPrivateKeyParameters)) {
               throw new IllegalArgumentException("'privateKey' type not supported: " + var2.getClass().getName());
            }

            this.basicAgreement = new ECDHBasicAgreement();
            this.truncateAgreement = false;
         }

         this.certificate = var1;
         this.privateKey = var2;
      }
   }

   public Certificate getCertificate() {
      return this.certificate;
   }

   public byte[] generateAgreement(AsymmetricKeyParameter var1) {
      this.basicAgreement.init(this.privateKey);
      BigInteger var2 = this.basicAgreement.calculateAgreement(var1);
      return this.truncateAgreement ? BigIntegers.asUnsignedByteArray(var2) : BigIntegers.asUnsignedByteArray(this.basicAgreement.getFieldSize(), var2);
   }
}
