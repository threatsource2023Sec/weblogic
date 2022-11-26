package org.opensaml.core.xml.config;

import javax.xml.namespace.QName;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;

public class XMLObjectProviderInitializer extends AbstractXMLObjectProviderInitializer {
   private static String[] configs = new String[]{"/default-config.xml", "/schema-config.xml"};

   protected String[] getConfigResources() {
      return configs;
   }

   public void init() throws InitializationException {
      super.init();
      XMLObjectProviderRegistry registry = (XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class);
      registry.registerIDAttribute(new QName("http://www.w3.org/XML/1998/namespace", "id"));
   }
}
