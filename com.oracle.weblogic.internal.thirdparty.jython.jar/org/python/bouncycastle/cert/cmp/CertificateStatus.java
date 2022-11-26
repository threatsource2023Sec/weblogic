package org.python.bouncycastle.cert.cmp;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.cmp.CertStatus;
import org.python.bouncycastle.asn1.cmp.PKIStatusInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.util.Arrays;

public class CertificateStatus {
   private DigestAlgorithmIdentifierFinder digestAlgFinder;
   private CertStatus certStatus;

   CertificateStatus(DigestAlgorithmIdentifierFinder var1, CertStatus var2) {
      this.digestAlgFinder = var1;
      this.certStatus = var2;
   }

   public PKIStatusInfo getStatusInfo() {
      return this.certStatus.getStatusInfo();
   }

   public BigInteger getCertRequestID() {
      return this.certStatus.getCertReqId().getValue();
   }

   public boolean isVerified(X509CertificateHolder var1, DigestCalculatorProvider var2) throws CMPException {
      AlgorithmIdentifier var3 = this.digestAlgFinder.find(var1.toASN1Structure().getSignatureAlgorithm());
      if (var3 == null) {
         throw new CMPException("cannot find algorithm for digest from signature");
      } else {
         DigestCalculator var4;
         try {
            var4 = var2.get(var3);
         } catch (OperatorCreationException var6) {
            throw new CMPException("unable to create digester: " + var6.getMessage(), var6);
         }

         CMPUtil.derEncodeToStream(var1.toASN1Structure(), var4.getOutputStream());
         return Arrays.areEqual(this.certStatus.getCertHash().getOctets(), var4.getDigest());
      }
   }
}
