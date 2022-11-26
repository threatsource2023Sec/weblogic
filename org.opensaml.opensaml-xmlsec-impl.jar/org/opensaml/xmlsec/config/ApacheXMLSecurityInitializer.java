package org.opensaml.xmlsec.config;

import org.apache.xml.security.Init;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApacheXMLSecurityInitializer implements Initializer {
   private Logger log = LoggerFactory.getLogger(ApacheXMLSecurityInitializer.class);

   public void init() throws InitializationException {
      String lineBreakPropName = "org.apache.xml.security.ignoreLineBreaks";
      if (System.getProperty(lineBreakPropName) == null) {
         System.setProperty(lineBreakPropName, "true");
      }

      if (!Init.isInitialized()) {
         this.log.debug("Initializing Apache XMLSecurity library");
         Init.init();
      } else {
         this.log.debug("Apache XMLSecurity library was already initialized, skipping...");
      }

   }
}
