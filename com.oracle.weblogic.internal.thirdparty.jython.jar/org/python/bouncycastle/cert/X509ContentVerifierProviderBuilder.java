package org.python.bouncycastle.cert;

import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.operator.ContentVerifierProvider;
import org.python.bouncycastle.operator.OperatorCreationException;

public interface X509ContentVerifierProviderBuilder {
   ContentVerifierProvider build(SubjectPublicKeyInfo var1) throws OperatorCreationException;

   ContentVerifierProvider build(X509CertificateHolder var1) throws OperatorCreationException;
}
