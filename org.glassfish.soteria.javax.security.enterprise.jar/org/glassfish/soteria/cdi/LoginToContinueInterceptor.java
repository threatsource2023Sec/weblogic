package org.glassfish.soteria.cdi;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Priority;
import javax.enterprise.inject.Intercepted;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.auth.message.AuthException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.glassfish.soteria.Utils;
import org.glassfish.soteria.mechanisms.LoginToContinueHolder;
import org.glassfish.soteria.servlet.AuthenticationData;
import org.glassfish.soteria.servlet.HttpServletRequestDelegator;
import org.glassfish.soteria.servlet.RequestCopier;
import org.glassfish.soteria.servlet.RequestData;

@Interceptor
@LoginToContinue
@Priority(220)
public class LoginToContinueInterceptor implements Serializable {
   private static final long serialVersionUID = 1L;
   @Inject
   private BeanManager beanManager;
   @Inject
   @Intercepted
   private Bean interceptedBean;
   private static final String ORIGINAL_REQUEST_DATA_SESSION_NAME = "org.glassfish.soteria.original.request";
   private static final String AUTHENTICATION_DATA_SESSION_NAME = "org.glassfish.soteria.authentication";
   private static final String CALLER_INITIATED_AUTHENTICATION_SESSION_NAME = "org.glassfish.soteria.caller_initiated_authentication";

   @AroundInvoke
   public Object intercept(InvocationContext invocationContext) throws Exception {
      return Utils.isImplementationOf(invocationContext.getMethod(), Utils.validateRequestMethod) ? this.validateRequest(invocationContext, (HttpServletRequest)Utils.getParam(invocationContext, 0), (HttpServletResponse)Utils.getParam(invocationContext, 1), (HttpMessageContext)Utils.getParam(invocationContext, 2)) : invocationContext.proceed();
   }

   private AuthenticationStatus validateRequest(InvocationContext invocationContext, HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws Exception {
      this.tryClean(httpMessageContext);
      return this.isCallerInitiatedAuthentication(request) ? this.processCallerInitiatedAuthentication(invocationContext, request, response, httpMessageContext) : this.processContainerInitiatedAuthentication(invocationContext, request, response, httpMessageContext);
   }

   private void tryClean(HttpMessageContext httpMessageContext) {
      if (this.isOnProtectedURLWithStaleData(httpMessageContext)) {
         this.removeSavedRequest(httpMessageContext.getRequest());
         this.removeCallerInitiatedAuthentication(httpMessageContext.getRequest());
      }

      if (httpMessageContext.getAuthParameters().isNewAuthentication()) {
         this.saveCallerInitiatedAuthentication(httpMessageContext.getRequest());
         this.removeSavedRequest(httpMessageContext.getRequest());
         this.removeSavedAuthentication(httpMessageContext.getRequest());
      }

   }

   private AuthenticationStatus processCallerInitiatedAuthentication(InvocationContext invocationContext, HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws Exception {
      AuthenticationStatus authstatus;
      try {
         authstatus = (AuthenticationStatus)invocationContext.proceed();
      } catch (AuthException var7) {
         authstatus = AuthenticationStatus.SEND_FAILURE;
      }

      if (authstatus == AuthenticationStatus.SUCCESS) {
         if (httpMessageContext.getCallerPrincipal() == null) {
            return AuthenticationStatus.SUCCESS;
         }

         this.removeCallerInitiatedAuthentication(httpMessageContext.getRequest());
      }

      return authstatus;
   }

   private AuthenticationStatus processContainerInitiatedAuthentication(InvocationContext invocationContext, HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws Exception {
      if (this.isOnInitialProtectedURL(httpMessageContext)) {
         this.saveRequest(request);
         LoginToContinue loginToContinueAnnotation = this.getLoginToContinueAnnotation(invocationContext);
         return loginToContinueAnnotation.useForwardToLogin() ? httpMessageContext.forward(loginToContinueAnnotation.loginPage()) : httpMessageContext.redirect(Utils.getBaseURL(request) + loginToContinueAnnotation.loginPage());
      } else {
         if (this.isOnLoginPostback(request)) {
            AuthenticationStatus authstatus;
            try {
               authstatus = (AuthenticationStatus)invocationContext.proceed();
            } catch (AuthException var7) {
               authstatus = AuthenticationStatus.SEND_FAILURE;
            }

            if (authstatus != AuthenticationStatus.SUCCESS) {
               if (authstatus == AuthenticationStatus.SEND_FAILURE) {
                  String errorPage = this.getLoginToContinueAnnotation(invocationContext).errorPage();
                  if (Utils.isEmpty(errorPage)) {
                     return authstatus;
                  }

                  return httpMessageContext.redirect(Utils.getBaseURL(request) + errorPage);
               }

               return authstatus;
            }

            if (httpMessageContext.getCallerPrincipal() == null) {
               return AuthenticationStatus.SUCCESS;
            }

            RequestData savedRequest = this.getSavedRequest(request);
            if (!savedRequest.matchesRequest(request)) {
               this.saveAuthentication(request, new AuthenticationData(httpMessageContext.getCallerPrincipal(), httpMessageContext.getGroups()));
               return httpMessageContext.redirect(savedRequest.getFullRequestURL());
            }
         }

         if (this.isOnOriginalURLAfterAuthenticate(request)) {
            RequestData requestData = this.removeSavedRequest(request);
            AuthenticationData authenticationData = this.removeSavedAuthentication(request);
            return httpMessageContext.withRequest(new HttpServletRequestDelegator(request, requestData)).notifyContainerAboutLogin(authenticationData.getPrincipal(), authenticationData.getGroups());
         } else {
            return (AuthenticationStatus)invocationContext.proceed();
         }
      }
   }

   private boolean isCallerInitiatedAuthentication(HttpServletRequest request) {
      return Boolean.TRUE.equals(this.getCallerInitiatedAuthentication(request));
   }

   private boolean isOnProtectedURLWithStaleData(HttpMessageContext httpMessageContext) {
      return httpMessageContext.isProtected() && !httpMessageContext.isAuthenticationRequest() && this.getSavedRequest(httpMessageContext.getRequest()) != null && this.getSavedAuthentication(httpMessageContext.getRequest()) == null && !httpMessageContext.getRequest().getRequestURI().endsWith("j_security_check");
   }

   private boolean isOnInitialProtectedURL(HttpMessageContext httpMessageContext) {
      return httpMessageContext.isProtected() && !httpMessageContext.isAuthenticationRequest() && this.getSavedRequest(httpMessageContext.getRequest()) == null && this.getSavedAuthentication(httpMessageContext.getRequest()) == null && !httpMessageContext.getRequest().getRequestURI().endsWith("j_security_check");
   }

   private boolean isOnLoginPostback(HttpServletRequest request) {
      return this.getSavedRequest(request) != null && this.getSavedAuthentication(request) == null;
   }

   private boolean isOnOriginalURLAfterAuthenticate(HttpServletRequest request) {
      RequestData savedRequest = this.getSavedRequest(request);
      AuthenticationData authenticationData = this.getSavedAuthentication(request);
      return Utils.notNull(savedRequest, authenticationData) && savedRequest.matchesRequest(request);
   }

   private LoginToContinue getLoginToContinueAnnotation(InvocationContext invocationContext) {
      if (invocationContext.getTarget() instanceof LoginToContinueHolder) {
         return ((LoginToContinueHolder)invocationContext.getTarget()).getLoginToContinue();
      } else {
         Optional optionalLoginToContinue = CdiUtils.getAnnotation(this.beanManager, this.interceptedBean.getBeanClass(), LoginToContinue.class);
         if (optionalLoginToContinue.isPresent()) {
            return (LoginToContinue)optionalLoginToContinue.get();
         } else {
            Set bindings = (Set)invocationContext.getContextData().get("org.jboss.weld.interceptor.bindings");
            if (bindings != null) {
               optionalLoginToContinue = bindings.stream().filter((annotation) -> {
                  return annotation.annotationType().equals(LoginToContinue.class);
               }).findAny().map((annotation) -> {
                  return (LoginToContinue)LoginToContinue.class.cast(annotation);
               });
               if (optionalLoginToContinue.isPresent()) {
                  return (LoginToContinue)optionalLoginToContinue.get();
               }
            }

            throw new IllegalStateException("@LoginToContinue not present on " + this.interceptedBean.getBeanClass());
         }
      }
   }

   private void saveCallerInitiatedAuthentication(HttpServletRequest request) {
      request.getSession().setAttribute("org.glassfish.soteria.caller_initiated_authentication", Boolean.TRUE);
   }

   private Boolean getCallerInitiatedAuthentication(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      return session == null ? null : (Boolean)session.getAttribute("org.glassfish.soteria.caller_initiated_authentication");
   }

   private void removeCallerInitiatedAuthentication(HttpServletRequest request) {
      request.getSession().removeAttribute("org.glassfish.soteria.caller_initiated_authentication");
   }

   private void saveRequest(HttpServletRequest request) {
      request.getSession().setAttribute("org.glassfish.soteria.original.request", RequestCopier.copy(request));
   }

   private RequestData getSavedRequest(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      return session == null ? null : (RequestData)session.getAttribute("org.glassfish.soteria.original.request");
   }

   private RequestData removeSavedRequest(HttpServletRequest request) {
      RequestData requestData = this.getSavedRequest(request);
      request.getSession().removeAttribute("org.glassfish.soteria.original.request");
      return requestData;
   }

   private void saveAuthentication(HttpServletRequest request, AuthenticationData authenticationData) {
      request.getSession().setAttribute("org.glassfish.soteria.authentication", authenticationData);
   }

   private AuthenticationData getSavedAuthentication(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      return session == null ? null : (AuthenticationData)session.getAttribute("org.glassfish.soteria.authentication");
   }

   private AuthenticationData removeSavedAuthentication(HttpServletRequest request) {
      AuthenticationData authenticationData = this.getSavedAuthentication(request);
      request.getSession().removeAttribute("org.glassfish.soteria.authentication");
      return authenticationData;
   }
}
