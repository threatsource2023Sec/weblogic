package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.operator.ContentVerifier;
import org.python.bouncycastle.operator.ContentVerifierProvider;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.SignatureAlgorithmIdentifierFinder;

public class SignerInformationVerifier {
   private ContentVerifierProvider verifierProvider;
   private DigestCalculatorProvider digestProvider;
   private SignatureAlgorithmIdentifierFinder sigAlgorithmFinder;
   private CMSSignatureAlgorithmNameGenerator sigNameGenerator;

   public SignerInformationVerifier(CMSSignatureAlgorithmNameGenerator var1, SignatureAlgorithmIdentifierFinder var2, ContentVerifierProvider var3, DigestCalculatorProvider var4) {
      this.sigNameGenerator = var1;
      this.sigAlgorithmFinder = var2;
      this.verifierProvider = var3;
      this.digestProvider = var4;
   }

   public boolean hasAssociatedCertificate() {
      return this.verifierProvider.hasAssociatedCertificate();
   }

   public X509CertificateHolder getAssociatedCertificate() {
      return this.verifierProvider.getAssociatedCertificate();
   }

   public ContentVerifier getContentVerifier(AlgorithmIdentifier var1, AlgorithmIdentifier var2) throws OperatorCreationException {
      String var3 = this.sigNameGenerator.getSignatureName(var2, var1);
      AlgorithmIdentifier var4 = this.sigAlgorithmFinder.find(var3);
      return this.verifierProvider.get(new AlgorithmIdentifier(var4.getAlgorithm(), var1.getParameters()));
   }

   public DigestCalculator getDigestCalculator(AlgorithmIdentifier var1) throws OperatorCreationException {
      return this.digestProvider.get(var1);
   }
}
