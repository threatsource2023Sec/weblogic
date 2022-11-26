package org.opensaml.xacml.config;

import org.opensaml.core.xml.config.AbstractXMLObjectProviderInitializer;

public class XMLObjectProviderInitializer extends AbstractXMLObjectProviderInitializer {
   private static String[] configs = new String[]{"/xacml20-context-config.xml", "/xacml20-policy-config.xml"};

   protected String[] getConfigResources() {
      return configs;
   }
}
