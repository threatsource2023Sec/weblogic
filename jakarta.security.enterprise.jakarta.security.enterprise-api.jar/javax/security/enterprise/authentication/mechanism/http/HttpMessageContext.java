package javax.security.enterprise.authentication.mechanism.http;

import java.security.Principal;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.MessageInfo;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpMessageContext {
   boolean isProtected();

   boolean isAuthenticationRequest();

   boolean isRegisterSession();

   void setRegisterSession(String var1, Set var2);

   void cleanClientSubject();

   AuthenticationParameters getAuthParameters();

   CallbackHandler getHandler();

   MessageInfo getMessageInfo();

   Subject getClientSubject();

   HttpServletRequest getRequest();

   void setRequest(HttpServletRequest var1);

   HttpMessageContext withRequest(HttpServletRequest var1);

   HttpServletResponse getResponse();

   void setResponse(HttpServletResponse var1);

   AuthenticationStatus redirect(String var1);

   AuthenticationStatus forward(String var1);

   AuthenticationStatus responseUnauthorized();

   AuthenticationStatus responseNotFound();

   AuthenticationStatus notifyContainerAboutLogin(String var1, Set var2);

   AuthenticationStatus notifyContainerAboutLogin(Principal var1, Set var2);

   AuthenticationStatus notifyContainerAboutLogin(CredentialValidationResult var1);

   AuthenticationStatus doNothing();

   Principal getCallerPrincipal();

   Set getGroups();
}
