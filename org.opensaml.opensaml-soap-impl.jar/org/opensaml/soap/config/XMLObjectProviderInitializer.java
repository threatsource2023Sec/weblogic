package org.opensaml.soap.config;

import org.opensaml.core.xml.config.AbstractXMLObjectProviderInitializer;

public class XMLObjectProviderInitializer extends AbstractXMLObjectProviderInitializer {
   private static String[] configs = new String[]{"/soap11-config.xml", "/wsaddressing-config.xml", "/wsfed11-protocol-config.xml", "/wspolicy-config.xml", "/wssecurity-config.xml", "/wstrust-config.xml"};

   protected String[] getConfigResources() {
      return configs;
   }
}
