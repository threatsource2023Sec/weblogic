package org.python.bouncycastle.cert.path;

import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.util.Memoable;

public interface CertPathValidation extends Memoable {
   void validate(CertPathValidationContext var1, X509CertificateHolder var2) throws CertPathValidationException;
}
