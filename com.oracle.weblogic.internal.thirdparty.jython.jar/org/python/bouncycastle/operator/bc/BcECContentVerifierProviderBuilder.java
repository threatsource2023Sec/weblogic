package org.python.bouncycastle.operator.bc;

import java.io.IOException;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.signers.DSADigestSigner;
import org.python.bouncycastle.crypto.signers.ECDSASigner;
import org.python.bouncycastle.crypto.util.PublicKeyFactory;
import org.python.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import org.python.bouncycastle.operator.OperatorCreationException;

public class BcECContentVerifierProviderBuilder extends BcContentVerifierProviderBuilder {
   private DigestAlgorithmIdentifierFinder digestAlgorithmFinder;

   public BcECContentVerifierProviderBuilder(DigestAlgorithmIdentifierFinder var1) {
      this.digestAlgorithmFinder = var1;
   }

   protected Signer createSigner(AlgorithmIdentifier var1) throws OperatorCreationException {
      AlgorithmIdentifier var2 = this.digestAlgorithmFinder.find(var1);
      ExtendedDigest var3 = this.digestProvider.get(var2);
      return new DSADigestSigner(new ECDSASigner(), var3);
   }

   protected AsymmetricKeyParameter extractKeyParameters(SubjectPublicKeyInfo var1) throws IOException {
      return PublicKeyFactory.createKey(var1);
   }
}
