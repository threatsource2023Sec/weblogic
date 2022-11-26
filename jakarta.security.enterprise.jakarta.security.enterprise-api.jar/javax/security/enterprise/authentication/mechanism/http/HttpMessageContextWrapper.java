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

public class HttpMessageContextWrapper implements HttpMessageContext {
   private final HttpMessageContext httpMessageContext;

   public HttpMessageContextWrapper(HttpMessageContext httpMessageContext) {
      this.httpMessageContext = httpMessageContext;
   }

   public HttpMessageContext getWrapped() {
      return this.httpMessageContext;
   }

   public boolean isProtected() {
      return this.getWrapped().isProtected();
   }

   public boolean isAuthenticationRequest() {
      return this.getWrapped().isAuthenticationRequest();
   }

   public boolean isRegisterSession() {
      return this.getWrapped().isRegisterSession();
   }

   public void setRegisterSession(String callerName, Set groups) {
      this.getWrapped().setRegisterSession(callerName, groups);
   }

   public void cleanClientSubject() {
      this.getWrapped().cleanClientSubject();
   }

   public AuthenticationParameters getAuthParameters() {
      return this.getWrapped().getAuthParameters();
   }

   public CallbackHandler getHandler() {
      return this.getWrapped().getHandler();
   }

   public MessageInfo getMessageInfo() {
      return this.getWrapped().getMessageInfo();
   }

   public Subject getClientSubject() {
      return this.getWrapped().getClientSubject();
   }

   public HttpServletRequest getRequest() {
      return this.getWrapped().getRequest();
   }

   public void setRequest(HttpServletRequest request) {
      this.getWrapped().setRequest(request);
   }

   public HttpMessageContext withRequest(HttpServletRequest request) {
      return this.getWrapped().withRequest(request);
   }

   public HttpServletResponse getResponse() {
      return this.getWrapped().getResponse();
   }

   public void setResponse(HttpServletResponse response) {
      this.getWrapped().setResponse(response);
   }

   public AuthenticationStatus redirect(String location) {
      return this.getWrapped().redirect(location);
   }

   public AuthenticationStatus forward(String path) {
      return this.getWrapped().forward(path);
   }

   public AuthenticationStatus responseUnauthorized() {
      return this.getWrapped().responseUnauthorized();
   }

   public AuthenticationStatus responseNotFound() {
      return this.getWrapped().responseNotFound();
   }

   public AuthenticationStatus notifyContainerAboutLogin(String username, Set roles) {
      return this.getWrapped().notifyContainerAboutLogin(username, roles);
   }

   public AuthenticationStatus notifyContainerAboutLogin(Principal principal, Set roles) {
      return this.getWrapped().notifyContainerAboutLogin(principal, roles);
   }

   public AuthenticationStatus notifyContainerAboutLogin(CredentialValidationResult result) {
      return this.getWrapped().notifyContainerAboutLogin(result);
   }

   public AuthenticationStatus doNothing() {
      return this.getWrapped().doNothing();
   }

   public Principal getCallerPrincipal() {
      return this.getWrapped().getCallerPrincipal();
   }

   public Set getGroups() {
      return this.getWrapped().getGroups();
   }
}
