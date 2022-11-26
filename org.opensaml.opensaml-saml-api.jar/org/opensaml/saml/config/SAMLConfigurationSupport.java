package org.opensaml.saml.config;

import java.util.List;
import org.joda.time.format.DateTimeFormatter;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactBuilderFactory;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilderFactory;

public final class SAMLConfigurationSupport {
   private SAMLConfigurationSupport() {
   }

   public static DateTimeFormatter getSAMLDateFormatter() {
      return ((SAMLConfiguration)ConfigurationService.get(SAMLConfiguration.class)).getSAMLDateFormatter();
   }

   public static void setSAMLDateFormat(String format) {
      ((SAMLConfiguration)ConfigurationService.get(SAMLConfiguration.class)).setSAMLDateFormat(format);
   }

   public static SAML1ArtifactBuilderFactory getSAML1ArtifactBuilderFactory() {
      return ((SAMLConfiguration)ConfigurationService.get(SAMLConfiguration.class)).getSAML1ArtifactBuilderFactory();
   }

   public static void setSAML1ArtifactBuilderFactory(SAML1ArtifactBuilderFactory factory) {
      ((SAMLConfiguration)ConfigurationService.get(SAMLConfiguration.class)).setSAML1ArtifactBuilderFactory(factory);
   }

   public static SAML2ArtifactBuilderFactory getSAML2ArtifactBuilderFactory() {
      return ((SAMLConfiguration)ConfigurationService.get(SAMLConfiguration.class)).getSAML2ArtifactBuilderFactory();
   }

   public static void setSAML2ArtifactBuilderFactory(SAML2ArtifactBuilderFactory factory) {
      ((SAMLConfiguration)ConfigurationService.get(SAMLConfiguration.class)).setSAML2ArtifactBuilderFactory(factory);
   }

   public static List getAllowedBindingURLSchemes() {
      return ((SAMLConfiguration)ConfigurationService.get(SAMLConfiguration.class)).getAllowedBindingURLSchemes();
   }

   public static void setAllowedBindingURLSchemes(List schemes) {
      ((SAMLConfiguration)ConfigurationService.get(SAMLConfiguration.class)).setAllowedBindingURLSchemes(schemes);
   }
}
