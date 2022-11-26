package org.opensaml.security.config;

import java.util.Arrays;
import java.util.HashSet;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.opensaml.security.x509.X509Support;
import org.opensaml.security.x509.tls.CertificateNameOptions;
import org.opensaml.security.x509.tls.ClientTLSValidationConfiguration;
import org.opensaml.security.x509.tls.impl.BasicClientTLSValidationConfiguration;

public class ClientTLSValidationConfiguratonInitializer implements Initializer {
   public void init() throws InitializationException {
      CertificateNameOptions nameOptions = new CertificateNameOptions();
      nameOptions.setEvaluateSubjectCommonName(true);
      nameOptions.setSubjectAltNames(new HashSet(Arrays.asList(X509Support.DNS_ALT_NAME, X509Support.URI_ALT_NAME)));
      BasicClientTLSValidationConfiguration config = new BasicClientTLSValidationConfiguration();
      config.setCertificateNameOptions(nameOptions);
      ConfigurationService.register(ClientTLSValidationConfiguration.class, config);
   }
}
