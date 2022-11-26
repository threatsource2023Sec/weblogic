package org.glassfish.soteria.mechanisms.jaspic;

import java.io.IOException;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.callback.GroupPrincipalCallback;
import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.module.ServerAuthModule;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.soteria.Utils;

public final class Jaspic {
   public static final String IS_AUTHENTICATION = "org.glassfish.soteria.security.message.request.authentication";
   public static final String IS_AUTHENTICATION_FROM_FILTER = "org.glassfish.soteria.security.message.request.authenticationFromFilter";
   public static final String IS_SECURE_RESPONSE = "org.glassfish.soteria.security.message.request.secureResponse";
   public static final String IS_REFRESH = "org.glassfish.soteria.security.message.request.isRefresh";
   public static final String DID_AUTHENTICATION = "org.glassfish.soteria.security.message.request.didAuthentication";
   public static final String AUTH_PARAMS = "org.glassfish.soteria.security.message.request.authParams";
   public static final String LOGGEDIN_USERNAME = "org.glassfish.soteria.security.message.loggedin.username";
   public static final String LOGGEDIN_ROLES = "org.glassfish.soteria.security.message.loggedin.roles";
   public static final String LAST_AUTH_STATUS = "org.glassfish.soteria.security.message.authStatus";
   public static final String CONTEXT_REGISTRATION_ID = "org.glassfish.soteria.security.message.registrationId";
   private static final String IS_MANDATORY = "javax.security.auth.message.MessagePolicy.isMandatory";
   private static final String REGISTER_SESSION = "javax.servlet.http.registerSession";

   private Jaspic() {
   }

   public static boolean authenticate(HttpServletRequest request, HttpServletResponse response, AuthenticationParameters authParameters) {
      boolean var3;
      try {
         request.setAttribute("org.glassfish.soteria.security.message.request.authentication", true);
         if (authParameters != null) {
            request.setAttribute("org.glassfish.soteria.security.message.request.authParams", authParameters);
         }

         var3 = request.authenticate(response);
      } catch (IOException | ServletException var7) {
         throw new IllegalArgumentException(var7);
      } finally {
         request.removeAttribute("org.glassfish.soteria.security.message.request.authentication");
         if (authParameters != null) {
            request.removeAttribute("org.glassfish.soteria.security.message.request.authParams");
         }

      }

      return var3;
   }

   public static AuthenticationParameters getAuthParameters(HttpServletRequest request) {
      AuthenticationParameters authParameters = (AuthenticationParameters)request.getAttribute("org.glassfish.soteria.security.message.request.authParams");
      if (authParameters == null) {
         authParameters = new AuthenticationParameters();
      }

      return authParameters;
   }

   public static void logout(HttpServletRequest request, HttpServletResponse response) {
      try {
         request.logout();
         request.getSession().invalidate();
      } catch (ServletException var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   public static void cleanSubject(final Subject subject) {
      if (subject != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Void run() {
               subject.getPrincipals().clear();
               return null;
            }
         });
      }

   }

   public static boolean isRegisterSession(MessageInfo messageInfo) {
      return Boolean.valueOf((String)messageInfo.getMap().get("javax.servlet.http.registerSession"));
   }

   public static boolean isProtectedResource(MessageInfo messageInfo) {
      return Boolean.valueOf((String)messageInfo.getMap().get("javax.security.auth.message.MessagePolicy.isMandatory"));
   }

   public static void setRegisterSession(MessageInfo messageInfo, String username, Set roles) {
      messageInfo.getMap().put("javax.servlet.http.registerSession", Boolean.TRUE.toString());
      HttpServletRequest request = (HttpServletRequest)messageInfo.getRequestMessage();
      request.setAttribute("org.glassfish.soteria.security.message.loggedin.username", username);
      request.setAttribute("org.glassfish.soteria.security.message.loggedin.roles", roles);
   }

   public static boolean isAuthenticationRequest(HttpServletRequest request) {
      return Boolean.TRUE.equals(request.getAttribute("org.glassfish.soteria.security.message.request.authentication"));
   }

   public static void notifyContainerAboutLogin(Subject clientSubject, CallbackHandler handler, Principal callerPrincipal, Set groups) {
      handleCallbacks(clientSubject, handler, new CallerPrincipalCallback(clientSubject, callerPrincipal), groups);
   }

   public static void notifyContainerAboutLogin(Subject clientSubject, CallbackHandler handler, String callerPrincipalName, Set groups) {
      handleCallbacks(clientSubject, handler, new CallerPrincipalCallback(clientSubject, callerPrincipalName), groups);
   }

   private static void handleCallbacks(Subject clientSubject, CallbackHandler handler, CallerPrincipalCallback callerPrincipalCallback, Set groups) {
      if (clientSubject == null) {
         throw new IllegalArgumentException("Null clientSubject!");
      } else if (handler == null) {
         throw new IllegalArgumentException("Null callback handler!");
      } else {
         try {
            if (groups != null && !Utils.isEmpty((Collection)groups) && (callerPrincipalCallback.getPrincipal() != null || callerPrincipalCallback.getName() != null)) {
               handler.handle(new Callback[]{callerPrincipalCallback, new GroupPrincipalCallback(clientSubject, (String[])groups.toArray(new String[groups.size()]))});
            } else {
               handler.handle(new Callback[]{callerPrincipalCallback});
            }

         } catch (UnsupportedCallbackException | IOException var5) {
            throw new IllegalStateException(var5);
         }
      }
   }

   public static void setLastAuthenticationStatus(HttpServletRequest request, AuthenticationStatus status) {
      request.setAttribute("org.glassfish.soteria.security.message.authStatus", status);
   }

   public static AuthenticationStatus getLastAuthenticationStatus(HttpServletRequest request) {
      return (AuthenticationStatus)request.getAttribute("org.glassfish.soteria.security.message.authStatus");
   }

   public static AuthStatus fromAuthenticationStatus(AuthenticationStatus authenticationStatus) {
      switch (authenticationStatus) {
         case NOT_DONE:
         case SUCCESS:
            return AuthStatus.SUCCESS;
         case SEND_FAILURE:
            return AuthStatus.SEND_FAILURE;
         case SEND_CONTINUE:
            return AuthStatus.SEND_CONTINUE;
         default:
            throw new IllegalStateException("Unhandled status:" + authenticationStatus.name());
      }
   }

   public static void setDidAuthentication(HttpServletRequest request) {
      request.setAttribute("org.glassfish.soteria.security.message.request.didAuthentication", Boolean.TRUE);
   }

   public static String getAppContextID(ServletContext context) {
      return context.getVirtualServerName() + " " + context.getContextPath();
   }

   public static String registerServerAuthModule(final ServerAuthModule serverAuthModule, final ServletContext servletContext) {
      String registrationId = (String)AccessController.doPrivileged(new PrivilegedAction() {
         public String run() {
            return AuthConfigFactory.getFactory().registerConfigProvider(new DefaultAuthConfigProvider(serverAuthModule), "HttpServlet", Jaspic.getAppContextID(servletContext), "Default single SAM authentication config provider");
         }
      });
      servletContext.setAttribute("org.glassfish.soteria.security.message.registrationId", registrationId);
      return registrationId;
   }

   public static void deregisterServerAuthModule(ServletContext servletContext) {
      final String registrationId = (String)servletContext.getAttribute("org.glassfish.soteria.security.message.registrationId");
      if (!Utils.isEmpty(registrationId)) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Boolean run() {
               return AuthConfigFactory.getFactory().removeRegistration(registrationId);
            }
         });
      }

   }
}
