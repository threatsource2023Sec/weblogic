package org.glassfish.soteria.cdi;

import java.io.Serializable;
import java.security.Principal;
import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.auth.callback.Callback;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.soteria.Utils;

@Interceptor
@AutoApplySession
@Priority(200)
public class AutoApplySessionInterceptor implements Serializable {
   private static final long serialVersionUID = 1L;

   @AroundInvoke
   public Object intercept(InvocationContext invocationContext) throws Exception {
      if (Utils.isImplementationOf(invocationContext.getMethod(), Utils.validateRequestMethod)) {
         HttpMessageContext httpMessageContext = (HttpMessageContext)invocationContext.getParameters()[2];
         Principal userPrincipal = this.getPrincipal(httpMessageContext.getRequest());
         if (userPrincipal != null) {
            httpMessageContext.getHandler().handle(new Callback[]{new CallerPrincipalCallback(httpMessageContext.getClientSubject(), userPrincipal)});
            return AuthenticationStatus.SUCCESS;
         } else {
            Object outcome = invocationContext.proceed();
            if (AuthenticationStatus.SUCCESS.equals(outcome)) {
               httpMessageContext.getMessageInfo().getMap().put("javax.servlet.http.registerSession", Boolean.TRUE.toString());
            }

            return outcome;
         }
      } else {
         return invocationContext.proceed();
      }
   }

   private Principal getPrincipal(HttpServletRequest request) {
      return request.getUserPrincipal();
   }
}
