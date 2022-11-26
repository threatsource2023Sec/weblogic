package com.bea.common.security.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.X509Certificate;
import weblogic.security.service.ContextHandler;

public interface CertPathValidatorService {
   CertPathValidatorResult validate(CertPath var1, X509Certificate[] var2, ContextHandler var3) throws CertPathValidatorException, InvalidAlgorithmParameterException;
}
