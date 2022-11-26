package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;

public interface SAML2SingleSignOnServicesConfigHelper {
   String getSingleSignOnServicesName();

   SAML2SingleSignOnServicesConfigCustomizer getSingleSignOnServicesCustomizer();

   void addToConfig(ServiceEngineConfig var1, String var2, LoginSessionServiceConfigHelper var3);

   public interface SAML2SingleSignOnServicesConfigCustomizer extends ServiceConfigCustomizer {
      void setSingleSignOnServicesConfig(SingleSignOnServicesConfigSpi var1);
   }
}
