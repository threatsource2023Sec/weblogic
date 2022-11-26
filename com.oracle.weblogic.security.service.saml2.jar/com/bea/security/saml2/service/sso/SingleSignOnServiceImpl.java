package com.bea.security.saml2.service.sso;

import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.service.AbstractService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SingleSignOnServiceImpl extends AbstractService {
   public SingleSignOnServiceImpl(SAML2ConfigSpi config) {
      super(config);
   }

   public boolean process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SingleSignOnServicesConfigSpi local = this.config.getLocalConfiguration();
      if (!local.isIdentityProviderEnabled()) {
         if (this.log.isDebugEnabled()) {
            this.log.info(Saml2Logger.getIdpDisabled());
         }

         response.sendError(404);
         return true;
      } else {
         this.logRequest(request);
         return (new SSOServiceProcessor(this.config)).process(request, response);
      }
   }
}
