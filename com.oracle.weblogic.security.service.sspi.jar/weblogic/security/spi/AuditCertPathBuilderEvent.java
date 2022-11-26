package weblogic.security.spi;

import java.security.cert.X509Certificate;
import weblogic.security.pk.CertPathSelector;
import weblogic.security.service.ContextHandler;

public interface AuditCertPathBuilderEvent extends AuditEvent {
   CertPathSelector getCertPathSelector();

   ContextHandler getContext();

   X509Certificate[] getTrustedCAs();
}
