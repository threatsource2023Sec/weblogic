package com.rsa.certj.spi.path;

import com.rsa.certj.cert.CertificateException;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Vector;

/** @deprecated */
public interface CertPathResult {
   boolean getValidationResult();

   void setValidationResult(boolean var1);

   String getMessage();

   JSAFE_PublicKey getSubjectPublicKey(String var1) throws CertificateException;

   Vector getValidPolicies();
}
