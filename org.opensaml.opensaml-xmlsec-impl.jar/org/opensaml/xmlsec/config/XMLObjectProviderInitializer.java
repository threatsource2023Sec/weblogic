package org.opensaml.xmlsec.config;

import org.opensaml.core.xml.config.AbstractXMLObjectProviderInitializer;

public class XMLObjectProviderInitializer extends AbstractXMLObjectProviderInitializer {
   private static String[] configs = new String[]{"/signature-config.xml", "/encryption-config.xml"};

   protected String[] getConfigResources() {
      return configs;
   }
}
