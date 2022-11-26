package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cert.X509CertificateHolder;

public interface ContentVerifierProvider {
   boolean hasAssociatedCertificate();

   X509CertificateHolder getAssociatedCertificate();

   ContentVerifier get(AlgorithmIdentifier var1) throws OperatorCreationException;
}
