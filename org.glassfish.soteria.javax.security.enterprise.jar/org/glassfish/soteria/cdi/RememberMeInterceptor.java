package org.glassfish.soteria.cdi;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Priority;
import javax.el.ELProcessor;
import javax.enterprise.inject.Intercepted;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.soteria.Utils;
import org.glassfish.soteria.servlet.CookieHandler;

@Interceptor
@RememberMe
@Priority(210)
public class RememberMeInterceptor implements Serializable {
   private static final long serialVersionUID = 1L;
   @Inject
   private BeanManager beanManager;
   @Inject
   @Intercepted
   private Bean interceptedBean;

   @AroundInvoke
   public Object intercept(InvocationContext invocationContext) throws Exception {
      if (Utils.isImplementationOf(invocationContext.getMethod(), Utils.validateRequestMethod)) {
         return this.validateRequest(invocationContext, (HttpServletRequest)Utils.getParam(invocationContext, 0), (HttpServletResponse)Utils.getParam(invocationContext, 1), (HttpMessageContext)Utils.getParam(invocationContext, 2));
      } else {
         if (Utils.isImplementationOf(invocationContext.getMethod(), Utils.cleanSubjectMethod)) {
            this.cleanSubject(invocationContext, (HttpServletRequest)Utils.getParam(invocationContext, 0), (HttpServletResponse)Utils.getParam(invocationContext, 1), (HttpMessageContext)Utils.getParam(invocationContext, 2));
         }

         return invocationContext.proceed();
      }
   }

   private AuthenticationStatus validateRequest(InvocationContext invocationContext, HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws Exception {
      RememberMeIdentityStore rememberMeIdentityStore = (RememberMeIdentityStore)CDI.current().select(RememberMeIdentityStore.class, new Annotation[0]).get();
      RememberMe rememberMeAnnotation = this.getRememberMeFromIntercepted(this.getElProcessor(invocationContext, httpMessageContext), invocationContext);
      Cookie rememberMeCookie = CookieHandler.getCookie(request, rememberMeAnnotation.cookieName());
      if (rememberMeCookie != null) {
         CredentialValidationResult result = rememberMeIdentityStore.validate(new RememberMeCredential(rememberMeCookie.getValue()));
         if (result.getStatus() == Status.VALID) {
            return httpMessageContext.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
         }

         CookieHandler.removeCookie(request, response, rememberMeAnnotation.cookieName());
      }

      AuthenticationStatus authstatus = (AuthenticationStatus)invocationContext.proceed();
      if (authstatus == AuthenticationStatus.SUCCESS && httpMessageContext.getCallerPrincipal() != null) {
         Boolean isRememberMe = true;
         if (rememberMeAnnotation instanceof RememberMeAnnotationLiteral) {
            isRememberMe = ((RememberMeAnnotationLiteral)rememberMeAnnotation).isRememberMe();
         }

         if (isRememberMe) {
            String token = rememberMeIdentityStore.generateLoginToken(Utils.toCallerPrincipal(httpMessageContext.getCallerPrincipal()), httpMessageContext.getGroups());
            CookieHandler.saveCookie(request, response, rememberMeAnnotation.cookieName(), token, rememberMeAnnotation.cookieMaxAgeSeconds(), rememberMeAnnotation.cookieSecureOnly(), rememberMeAnnotation.cookieHttpOnly());
         }
      }

      return authstatus;
   }

   private void cleanSubject(InvocationContext invocationContext, HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws Exception {
      RememberMeIdentityStore rememberMeIdentityStore = (RememberMeIdentityStore)CDI.current().select(RememberMeIdentityStore.class, new Annotation[0]).get();
      RememberMe rememberMeAnnotation = this.getRememberMeFromIntercepted(this.getElProcessor(invocationContext, httpMessageContext), invocationContext);
      Cookie rememberMeCookie = CookieHandler.getCookie(request, rememberMeAnnotation.cookieName());
      if (rememberMeCookie != null) {
         CookieHandler.removeCookie(request, response, rememberMeAnnotation.cookieName());
         rememberMeIdentityStore.removeLoginToken(rememberMeCookie.getValue());
      }

      invocationContext.proceed();
   }

   private RememberMe getRememberMeFromIntercepted(ELProcessor elProcessor, InvocationContext invocationContext) {
      Optional optionalRememberMe = CdiUtils.getAnnotation(this.beanManager, this.interceptedBean.getBeanClass(), RememberMe.class);
      if (optionalRememberMe.isPresent()) {
         return RememberMeAnnotationLiteral.eval((RememberMe)optionalRememberMe.get(), elProcessor);
      } else {
         Set bindings = (Set)invocationContext.getContextData().get("org.jboss.weld.interceptor.bindings");
         if (bindings != null) {
            optionalRememberMe = bindings.stream().filter((annotation) -> {
               return annotation.annotationType().equals(RememberMe.class);
            }).findAny().map((annotation) -> {
               return (RememberMe)RememberMe.class.cast(annotation);
            });
            if (optionalRememberMe.isPresent()) {
               return RememberMeAnnotationLiteral.eval((RememberMe)optionalRememberMe.get(), elProcessor);
            }
         }

         throw new IllegalStateException("@RememberMe not present on " + this.interceptedBean.getBeanClass());
      }
   }

   private ELProcessor getElProcessor(InvocationContext invocationContext, HttpMessageContext httpMessageContext) {
      ELProcessor elProcessor = new ELProcessor();
      elProcessor.getELManager().addELResolver(this.beanManager.getELResolver());
      elProcessor.defineBean("self", invocationContext.getTarget());
      elProcessor.defineBean("httpMessageContext", httpMessageContext);
      return elProcessor;
   }
}
