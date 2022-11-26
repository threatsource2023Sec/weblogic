package org.glassfish.soteria.mechanisms;

import java.lang.annotation.Annotation;
import javax.enterprise.inject.spi.CDI;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.glassfish.soteria.Utils;

public class BasicAuthenticationMechanism implements HttpAuthenticationMechanism {
   private final BasicAuthenticationMechanismDefinition basicAuthenticationMechanismDefinition;

   public BasicAuthenticationMechanism(BasicAuthenticationMechanismDefinition basicAuthenticationMechanismDefinition) {
      this.basicAuthenticationMechanismDefinition = basicAuthenticationMechanismDefinition;
   }

   public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMsgContext) throws AuthenticationException {
      String[] credentials = this.getCredentials(request);
      if (!Utils.isEmpty((Object[])credentials)) {
         IdentityStoreHandler identityStoreHandler = (IdentityStoreHandler)CDI.current().select(IdentityStoreHandler.class, new Annotation[0]).get();
         CredentialValidationResult result = identityStoreHandler.validate(new UsernamePasswordCredential(credentials[0], new Password(credentials[1])));
         if (result.getStatus() == Status.VALID) {
            return httpMsgContext.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
         }
      }

      if (httpMsgContext.isProtected()) {
         response.setHeader("WWW-Authenticate", String.format("Basic realm=\"%s\"", this.basicAuthenticationMechanismDefinition.realmName()));
         return httpMsgContext.responseUnauthorized();
      } else {
         return httpMsgContext.doNothing();
      }
   }

   private String[] getCredentials(HttpServletRequest request) {
      String authorizationHeader = request.getHeader("Authorization");
      return !Utils.isEmpty(authorizationHeader) && authorizationHeader.startsWith("Basic ") ? (new String(DatatypeConverter.parseBase64Binary(authorizationHeader.substring(6)))).split(":") : null;
   }
}
