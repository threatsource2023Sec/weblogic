package com.rsa.certj.spi.revocation;

import com.rsa.certj.NotSupportedException;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.spi.path.CertPathCtx;

/** @deprecated */
public interface CertStatusInterface {
   CertRevocationInfo checkCertRevocation(CertPathCtx var1, Certificate var2) throws NotSupportedException, CertStatusException;
}
