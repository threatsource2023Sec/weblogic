package javax.security.enterprise.authentication.mechanism.http;

import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpAuthenticationMechanism {
   AuthenticationStatus validateRequest(HttpServletRequest var1, HttpServletResponse var2, HttpMessageContext var3) throws AuthenticationException;

   default AuthenticationStatus secureResponse(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
      return AuthenticationStatus.SUCCESS;
   }

   default void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
      httpMessageContext.cleanClientSubject();
   }
}
