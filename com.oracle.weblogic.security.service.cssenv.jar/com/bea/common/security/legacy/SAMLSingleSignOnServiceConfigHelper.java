package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.spi.SAMLSingleSignOnServiceConfigInfoSpi;

public interface SAMLSingleSignOnServiceConfigHelper {
   String getSAMLSingleSignOnServiceName();

   SAMLSingleSignOnServiceConfigCustomizer getSAMLSingleSignOnServiceCustomizer();

   void addToConfig(ServiceEngineConfig var1, String var2);

   public interface SAMLSingleSignOnServiceConfigCustomizer extends ServiceConfigCustomizer {
      void setSAMLSingleSignOnServiceConfigInfo(SAMLSingleSignOnServiceConfigInfoSpi var1);
   }
}
