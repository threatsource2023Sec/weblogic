package org.opensaml.saml.config;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactBuilderFactory;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLConfigurationInitializer implements Initializer {
   private Logger log = LoggerFactory.getLogger(SAMLConfigurationInitializer.class);

   public void init() throws InitializationException {
      this.log.debug("Initializing SAML Artifact builder factories");
      SAMLConfiguration config = null;
      Class var2 = ConfigurationService.class;
      synchronized(ConfigurationService.class) {
         config = (SAMLConfiguration)ConfigurationService.get(SAMLConfiguration.class);
         if (config == null) {
            config = new SAMLConfiguration();
            ConfigurationService.register(SAMLConfiguration.class, config);
         }
      }

      config.setSAML1ArtifactBuilderFactory(new SAML1ArtifactBuilderFactory());
      config.setSAML2ArtifactBuilderFactory(new SAML2ArtifactBuilderFactory());
   }
}
