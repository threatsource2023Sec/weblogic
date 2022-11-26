package org.python.bouncycastle.cert.cmp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.cmp.CertConfirmContent;
import org.python.bouncycastle.asn1.cmp.CertStatus;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.python.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;

public class CertificateConfirmationContentBuilder {
   private DigestAlgorithmIdentifierFinder digestAlgFinder;
   private List acceptedCerts;
   private List acceptedReqIds;

   public CertificateConfirmationContentBuilder() {
      this(new DefaultDigestAlgorithmIdentifierFinder());
   }

   public CertificateConfirmationContentBuilder(DigestAlgorithmIdentifierFinder var1) {
      this.acceptedCerts = new ArrayList();
      this.acceptedReqIds = new ArrayList();
      this.digestAlgFinder = var1;
   }

   public CertificateConfirmationContentBuilder addAcceptedCertificate(X509CertificateHolder var1, BigInteger var2) {
      this.acceptedCerts.add(var1);
      this.acceptedReqIds.add(var2);
      return this;
   }

   public CertificateConfirmationContent build(DigestCalculatorProvider var1) throws CMPException {
      ASN1EncodableVector var2 = new ASN1EncodableVector();

      for(int var3 = 0; var3 != this.acceptedCerts.size(); ++var3) {
         X509CertificateHolder var4 = (X509CertificateHolder)this.acceptedCerts.get(var3);
         BigInteger var5 = (BigInteger)this.acceptedReqIds.get(var3);
         AlgorithmIdentifier var6 = this.digestAlgFinder.find(var4.toASN1Structure().getSignatureAlgorithm());
         if (var6 == null) {
            throw new CMPException("cannot find algorithm for digest from signature");
         }

         DigestCalculator var7;
         try {
            var7 = var1.get(var6);
         } catch (OperatorCreationException var9) {
            throw new CMPException("unable to create digest: " + var9.getMessage(), var9);
         }

         CMPUtil.derEncodeToStream(var4.toASN1Structure(), var7.getOutputStream());
         var2.add(new CertStatus(var7.getDigest(), var5));
      }

      return new CertificateConfirmationContent(CertConfirmContent.getInstance(new DERSequence(var2)), this.digestAlgFinder);
   }
}
