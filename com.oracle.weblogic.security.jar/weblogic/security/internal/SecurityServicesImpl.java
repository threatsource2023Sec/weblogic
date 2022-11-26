package weblogic.security.internal;

import weblogic.security.spi.AuditorService;
import weblogic.security.spi.SecurityServices;

public class SecurityServicesImpl implements SecurityServices {
   private AuditorService auditorService = null;

   public SecurityServicesImpl(AuditorService service, String realm) {
      this.auditorService = service;
      SecurityServicesManagerHelper.registerSecurityServices(this, realm);
   }

   public AuditorService getAuditorService() {
      return this.auditorService;
   }
}
