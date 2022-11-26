package org.glassfish.soteria.mechanisms;

import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.MessageInfo;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.soteria.Utils;
import org.glassfish.soteria.mechanisms.jaspic.Jaspic;

public class HttpMessageContextImpl implements HttpMessageContext {
   private CallbackHandler handler;
   private MessageInfo messageInfo;
   private Subject clientSubject;
   private AuthenticationParameters authParameters;
   private Principal callerPrincipal;
   private Set groups;

   public HttpMessageContextImpl(CallbackHandler handler, MessageInfo messageInfo, Subject clientSubject) {
      this.handler = handler;
      this.messageInfo = messageInfo;
      this.clientSubject = clientSubject;
      if (messageInfo != null) {
         this.authParameters = Jaspic.getAuthParameters(this.getRequest());
      }

   }

   public boolean isProtected() {
      return Jaspic.isProtectedResource(this.messageInfo);
   }

   public boolean isAuthenticationRequest() {
      return Jaspic.isAuthenticationRequest(this.getRequest());
   }

   public boolean isRegisterSession() {
      return Jaspic.isRegisterSession(this.messageInfo);
   }

   public void setRegisterSession(String username, Set groups) {
      Jaspic.setRegisterSession(this.messageInfo, username, groups);
   }

   public void cleanClientSubject() {
      Jaspic.cleanSubject(this.clientSubject);
   }

   public AuthenticationParameters getAuthParameters() {
      return this.authParameters;
   }

   public CallbackHandler getHandler() {
      return this.handler;
   }

   public MessageInfo getMessageInfo() {
      return this.messageInfo;
   }

   public Subject getClientSubject() {
      return this.clientSubject;
   }

   public HttpServletRequest getRequest() {
      return (HttpServletRequest)this.messageInfo.getRequestMessage();
   }

   public void setRequest(HttpServletRequest request) {
      this.messageInfo.setRequestMessage(request);
   }

   public HttpMessageContext withRequest(HttpServletRequest request) {
      this.setRequest(request);
      return this;
   }

   public HttpServletResponse getResponse() {
      return (HttpServletResponse)this.messageInfo.getResponseMessage();
   }

   public void setResponse(HttpServletResponse response) {
      this.messageInfo.setResponseMessage(response);
   }

   public AuthenticationStatus redirect(String location) {
      Utils.redirect(this.getResponse(), location);
      return AuthenticationStatus.SEND_CONTINUE;
   }

   public AuthenticationStatus forward(String path) {
      try {
         this.getRequest().getRequestDispatcher(path).forward(this.getRequest(), this.getResponse());
      } catch (ServletException | IOException var3) {
         throw new IllegalStateException(var3);
      }

      return AuthenticationStatus.SEND_CONTINUE;
   }

   public AuthenticationStatus responseUnauthorized() {
      try {
         this.getResponse().sendError(401);
      } catch (IOException var2) {
         throw new IllegalStateException(var2);
      }

      return AuthenticationStatus.SEND_FAILURE;
   }

   public AuthenticationStatus responseNotFound() {
      try {
         this.getResponse().sendError(404);
      } catch (IOException var2) {
         throw new IllegalStateException(var2);
      }

      return AuthenticationStatus.SEND_FAILURE;
   }

   public AuthenticationStatus notifyContainerAboutLogin(String callerName, Set groups) {
      NameHolderPrincipal nameHolder = null;
      if (callerName != null) {
         nameHolder = new NameHolderPrincipal(callerName);
      }

      return this.notifyContainerAboutLogin((Principal)nameHolder, groups);
   }

   public AuthenticationStatus notifyContainerAboutLogin(CredentialValidationResult result) {
      return result.getStatus() == Status.VALID ? this.notifyContainerAboutLogin((Principal)result.getCallerPrincipal(), result.getCallerGroups()) : AuthenticationStatus.SEND_FAILURE;
   }

   public AuthenticationStatus notifyContainerAboutLogin(Principal callerPrincipal, Set groups) {
      this.callerPrincipal = callerPrincipal;
      if (callerPrincipal != null) {
         this.groups = groups;
      } else {
         this.groups = null;
      }

      if (this.callerPrincipal instanceof NameHolderPrincipal) {
         Jaspic.notifyContainerAboutLogin(this.clientSubject, this.handler, this.callerPrincipal.getName(), this.groups);
      } else {
         Jaspic.notifyContainerAboutLogin(this.clientSubject, this.handler, this.callerPrincipal, this.groups);
      }

      Jaspic.setDidAuthentication((HttpServletRequest)this.messageInfo.getRequestMessage());
      return AuthenticationStatus.SUCCESS;
   }

   public AuthenticationStatus doNothing() {
      this.callerPrincipal = null;
      this.groups = null;
      Jaspic.notifyContainerAboutLogin(this.clientSubject, this.handler, (String)((String)null), (Set)null);
      return AuthenticationStatus.NOT_DONE;
   }

   public Principal getCallerPrincipal() {
      return this.callerPrincipal;
   }

   public Set getGroups() {
      return this.groups;
   }

   private static class NameHolderPrincipal extends CallerPrincipal {
      NameHolderPrincipal(String name) {
         super(name);
      }
   }
}
