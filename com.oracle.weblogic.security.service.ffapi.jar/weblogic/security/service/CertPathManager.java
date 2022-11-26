package weblogic.security.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.X509Certificate;
import weblogic.security.pk.CertPathSelector;

public interface CertPathManager {
   CertPathBuilderResult build(CertPathSelector var1, X509Certificate[] var2, ContextHandler var3) throws CertPathBuilderException, InvalidAlgorithmParameterException;

   CertPathValidatorResult validate(CertPath var1, X509Certificate[] var2, ContextHandler var3) throws CertPathValidatorException, InvalidAlgorithmParameterException;
}
