package org.glassfish.soteria;

import java.io.Serializable;
import java.security.Principal;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.soteria.authorization.spi.CallerDetailsResolver;
import org.glassfish.soteria.authorization.spi.ResourceAccessResolver;
import org.glassfish.soteria.authorization.spi.impl.JaccResourceAccessResolver;
import org.glassfish.soteria.authorization.spi.impl.ReflectionAndJaccCallerDetailsResolver;
import org.glassfish.soteria.mechanisms.jaspic.Jaspic;

public class SecurityContextImpl implements SecurityContext, Serializable {
   private static final long serialVersionUID = 1L;
   private CallerDetailsResolver callerDetailsResolver;
   private ResourceAccessResolver resourceAccessResolver;

   @PostConstruct
   public void init() {
      this.callerDetailsResolver = new ReflectionAndJaccCallerDetailsResolver();
      this.resourceAccessResolver = new JaccResourceAccessResolver();
   }

   public Principal getCallerPrincipal() {
      return this.callerDetailsResolver.getCallerPrincipal();
   }

   public Set getPrincipalsByType(Class pType) {
      return this.callerDetailsResolver.getPrincipalsByType(pType);
   }

   public boolean isCallerInRole(String role) {
      return this.callerDetailsResolver.isCallerInRole(role);
   }

   public Set getAllDeclaredCallerRoles() {
      return this.callerDetailsResolver.getAllDeclaredCallerRoles();
   }

   public boolean hasAccessToWebResource(String resource, String... methods) {
      return this.resourceAccessResolver.hasAccessToWebResource(resource, methods);
   }

   public AuthenticationStatus authenticate(HttpServletRequest request, HttpServletResponse response, AuthenticationParameters parameters) {
      try {
         return Jaspic.authenticate(request, response, parameters) ? AuthenticationStatus.SUCCESS : Jaspic.getLastAuthenticationStatus(request);
      } catch (IllegalArgumentException var5) {
         return AuthenticationStatus.SEND_FAILURE;
      }
   }
}
