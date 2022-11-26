package org.opensaml.saml.config;

import org.opensaml.core.xml.config.AbstractXMLObjectProviderInitializer;

public class XMLObjectProviderInitializer extends AbstractXMLObjectProviderInitializer {
   private static String[] configs = new String[]{"/saml1-assertion-config.xml", "/saml1-metadata-config.xml", "/saml1-protocol-config.xml", "/saml2-assertion-config.xml", "/saml2-assertion-delegation-restriction-config.xml", "/saml2-ecp-config.xml", "/saml2-metadata-algorithm-config.xml", "/saml2-metadata-attr-config.xml", "/saml2-metadata-config.xml", "/saml2-metadata-idp-discovery-config.xml", "/saml2-metadata-query-config.xml", "/saml2-metadata-reqinit-config.xml", "/saml2-metadata-ui-config.xml", "/saml2-metadata-rpi-config.xml", "/saml2-protocol-config.xml", "/saml2-protocol-thirdparty-config.xml", "/saml2-protocol-aslo-config.xml", "/saml2-channel-binding-config.xml", "/saml-ec-gss-config.xml"};

   protected String[] getConfigResources() {
      return configs;
   }
}
