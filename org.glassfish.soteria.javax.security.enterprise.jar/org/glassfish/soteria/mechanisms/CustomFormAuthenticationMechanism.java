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
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AutoApplySession
@LoginToContinue
@Typed({CustomFormAuthenticationMechanism.class})
public class CustomFormAuthenticationMechanism implements HttpAuthenticationMechanism, LoginToContinueHolder {
   private LoginToContinue loginToContinue;

   public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
      if (hasCredential(httpMessageContext)) {
         IdentityStoreHandler identityStoreHandler = (IdentityStoreHandler)CDI.current().select(IdentityStoreHandler.class, new Annotation[0]).get();
         return httpMessageContext.notifyContainerAboutLogin(identityStoreHandler.validate(httpMessageContext.getAuthParameters().getCredential()));
      } else {
         return httpMessageContext.doNothing();
      }
   }

   private static boolean hasCredential(HttpMessageContext httpMessageContext) {
      return httpMessageContext.getAuthParameters().getCredential() != null;
   }

   public LoginToContinue getLoginToContinue() {
      return this.loginToContinue;
   }

   public void setLoginToContinue(LoginToContinue loginToContinue) {
      this.loginToContinue = loginToContinue;
   }

   public CustomFormAuthenticationMechanism loginToContinue(LoginToContinue loginToContinue) {
      this.setLoginToContinue(loginToContinue);
      return this;
   }
}
