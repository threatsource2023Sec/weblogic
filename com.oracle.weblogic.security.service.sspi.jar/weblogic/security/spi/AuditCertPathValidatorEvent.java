package weblogic.security.spi;

import java.security.cert.CertPath;
import java.security.cert.X509Certificate;
import weblogic.security.service.ContextHandler;

public interface AuditCertPathValidatorEvent extends AuditEvent {
   CertPath getCertPath();

   ContextHandler getContext();

   X509Certificate[] getTrustedCAs();
}
