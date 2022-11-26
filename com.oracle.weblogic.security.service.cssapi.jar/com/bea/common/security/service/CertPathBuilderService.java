package com.bea.common.security.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.X509Certificate;
import weblogic.security.pk.CertPathSelector;
import weblogic.security.service.ContextHandler;

public interface CertPathBuilderService {
   CertPathBuilderResult build(CertPathSelector var1, X509Certificate[] var2, ContextHandler var3) throws CertPathBuilderException, InvalidAlgorithmParameterException;
}
