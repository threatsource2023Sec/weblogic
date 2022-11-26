package weblogic.security.spi;

import java.security.cert.CertPathParameters;
import java.security.cert.X509Certificate;
import weblogic.security.service.ContextHandler;

public interface CertPathValidatorParametersSpi extends CertPathParameters {
   CertPathProvider getCertPathProvider();

   X509Certificate[] getTrustedCAs();

   ContextHandler getContext();

   Object clone();
}
