package weblogic.security.spi;

import java.security.cert.CertPathParameters;
import java.security.cert.X509Certificate;
import weblogic.security.pk.CertPathSelector;
import weblogic.security.service.ContextHandler;

public interface CertPathBuilderParametersSpi extends CertPathParameters {
   CertPathProvider getCertPathProvider();

   CertPathSelector getCertPathSelector();

   X509Certificate[] getTrustedCAs();

   ContextHandler getContext();

   Object clone();
}
