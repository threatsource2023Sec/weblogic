package org.glassfish.soteria.mechanisms.jaspic;

import java.lang.annotation.Annotation;
import java.util.Map;
import javax.enterprise.inject.spi.CDI;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.module.ServerAuthModule;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.soteria.cdi.spi.CDIPerRequestInitializer;
import org.glassfish.soteria.mechanisms.HttpMessageContextImpl;

public class HttpBridgeServerAuthModule implements ServerAuthModule {
   private CallbackHandler handler;
   private final Class[] supportedMessageTypes = new Class[]{HttpServletRequest.class, HttpServletResponse.class};
   private final CDIPerRequestInitializer cdiPerRequestInitializer;

   public HttpBridgeServerAuthModule(CDIPerRequestInitializer cdiPerRequestInitializer) {
      this.cdiPerRequestInitializer = cdiPerRequestInitializer;
   }

   public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler handler, Map options) throws AuthException {
      this.handler = handler;
   }

   public Class[] getSupportedMessageTypes() {
      return this.supportedMessageTypes;
   }

   public AuthStatus validateRequest(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
      HttpMessageContext msgContext = new HttpMessageContextImpl(this.handler, messageInfo, clientSubject);
      if (this.cdiPerRequestInitializer != null) {
         this.cdiPerRequestInitializer.init(msgContext.getRequest());
      }

      AuthenticationStatus status = AuthenticationStatus.NOT_DONE;
      Jaspic.setLastAuthenticationStatus(msgContext.getRequest(), status);

      try {
         status = ((HttpAuthenticationMechanism)CDI.current().select(HttpAuthenticationMechanism.class, new Annotation[0]).get()).validateRequest(msgContext.getRequest(), msgContext.getResponse(), msgContext);
      } catch (AuthenticationException var7) {
         Jaspic.setLastAuthenticationStatus(msgContext.getRequest(), AuthenticationStatus.SEND_FAILURE);
         throw (AuthException)(new AuthException("Authentication failure in HttpAuthenticationMechanism")).initCause(var7);
      }

      Jaspic.setLastAuthenticationStatus(msgContext.getRequest(), status);
      return Jaspic.fromAuthenticationStatus(status);
   }

   public AuthStatus secureResponse(MessageInfo messageInfo, Subject serviceSubject) throws AuthException {
      HttpMessageContext msgContext = new HttpMessageContextImpl(this.handler, messageInfo, (Subject)null);

      AuthStatus var6;
      try {
         AuthenticationStatus status = ((HttpAuthenticationMechanism)CDI.current().select(HttpAuthenticationMechanism.class, new Annotation[0]).get()).secureResponse(msgContext.getRequest(), msgContext.getResponse(), msgContext);
         AuthStatus authStatus = Jaspic.fromAuthenticationStatus(status);
         if (authStatus == AuthStatus.SUCCESS) {
            var6 = AuthStatus.SEND_SUCCESS;
            return var6;
         }

         var6 = authStatus;
      } catch (AuthenticationException var10) {
         throw (AuthException)(new AuthException("Secure response failure in HttpAuthenticationMechanism")).initCause(var10);
      } finally {
         if (this.cdiPerRequestInitializer != null) {
            this.cdiPerRequestInitializer.destroy(msgContext.getRequest());
         }

      }

      return var6;
   }

   public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
      HttpMessageContext msgContext = new HttpMessageContextImpl(this.handler, messageInfo, subject);
      ((HttpAuthenticationMechanism)CDI.current().select(HttpAuthenticationMechanism.class, new Annotation[0]).get()).cleanSubject(msgContext.getRequest(), msgContext.getResponse(), msgContext);
   }
}
