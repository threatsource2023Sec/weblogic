package weblogic.security.spi;

import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;

public interface CertPathProvider extends SecurityProvider {
   CertPathBuilder getCertPathBuilder();

   CertPathValidator getCertPathValidator();
}
