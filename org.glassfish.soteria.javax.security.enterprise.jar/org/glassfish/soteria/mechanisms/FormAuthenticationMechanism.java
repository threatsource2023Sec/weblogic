package org.glassfish.soteria.mechanisms;

import java.lang.annotation.Annotation;
import javax.enterprise.inject.Typed;
import javax.enterprise.inject.spi.CDI;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.soteria.Utils;

@AutoApplySession
@LoginToContinue
@Typed({FormAuthenticationMechanism.class})
public class FormAuthenticationMechanism implements HttpAuthenticationMechanism, LoginToContinueHolder {
   private LoginToContinue loginToContinue;

   public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
      if (isValidFormPostback(request)) {
         IdentityStoreHandler identityStoreHandler = (IdentityStoreHandler)CDI.current().select(IdentityStoreHandler.class, new Annotation[0]).get();
         return httpMessageContext.notifyContainerAboutLogin(identityStoreHandler.validate(new UsernamePasswordCredential(request.getParameter("j_username"), new Password(request.getParameter("j_password")))));
      } else {
         return httpMessageContext.doNothing();
      }
   }

   private static boolean isValidFormPostback(HttpServletRequest request) {
      return "POST".equals(request.getMethod()) && request.getRequestURI().endsWith("/j_security_check") && Utils.notNull(request.getParameter("j_username"), request.getParameter("j_password"));
   }

   public LoginToContinue getLoginToContinue() {
      return this.loginToContinue;
   }

   public void setLoginToContinue(LoginToContinue loginToContinue) {
      this.loginToContinue = loginToContinue;
   }

   public FormAuthenticationMechanism loginToContinue(LoginToContinue loginToContinue) {
      this.setLoginToContinue(loginToContinue);
      return this;
   }
}
