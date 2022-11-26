package weblogic.management.mbeans.custom;

import com.bea.common.security.saml.utils.SAMLUtil;
import weblogic.management.configuration.FederationServicesMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public final class FederationServices extends ConfigurationMBeanCustomizer {
   public FederationServices(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public String getSourceIdHex() {
      return SAMLUtil.calculateSourceIdHex(((FederationServicesMBean)this.getMbean()).getSourceSiteURL());
   }

   public String getSourceIdBase64() {
      return SAMLUtil.calculateSourceIdBase64(((FederationServicesMBean)this.getMbean()).getSourceSiteURL());
   }
}
