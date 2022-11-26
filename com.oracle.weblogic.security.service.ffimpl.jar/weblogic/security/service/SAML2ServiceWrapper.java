package weblogic.security.service;

import com.bea.common.security.service.SAML2PublishException;
import com.bea.common.security.service.SAML2Service;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.management.security.ProviderMBean;
import weblogic.security.shared.LoggerWrapper;

public class SAML2ServiceWrapper implements SAML2Service, SecurityService {
   private boolean initialized = false;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecuritySAML2Service");
   private SAML2Service saml2Service = null;

   private SAML2ServiceWrapper() {
   }

   public SAML2ServiceWrapper(SAML2Service saml2Service) {
      this.saml2Service = saml2Service;
      this.initialized = true;
   }

   public void initialize(String realmName, ProviderMBean[] configuration) throws InvalidParameterException, ProviderException {
      throw new IllegalStateException("Should not be getting called with CSS enabled");
   }

   public void shutdown() {
      if (this.saml2Service != null) {
         if (log.isDebugEnabled()) {
            log.debug("SAML2Service shutdown noop for common audit service");
         }

         this.saml2Service = null;
      }
   }

   public void start() {
      if (this.saml2Service != null) {
         if (log.isDebugEnabled()) {
            log.debug("SAML2Service start noop for common audit service");
         }

      }
   }

   public void suspend() {
      if (this.saml2Service != null) {
         if (log.isDebugEnabled()) {
            log.debug("SAML2Service suspend noop for common audit service");
         }

      }
   }

   public boolean process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      return this.saml2Service.process(request, response);
   }

   public void publish(String filename) throws SAML2PublishException {
      this.saml2Service.publish(filename);
   }

   public void publish(String filename, boolean prohibitOverwrite) throws SAML2PublishException {
      this.saml2Service.publish(filename, prohibitOverwrite);
   }

   public String exportMetadata() throws SAML2PublishException {
      return this.saml2Service.exportMetadata();
   }
}
