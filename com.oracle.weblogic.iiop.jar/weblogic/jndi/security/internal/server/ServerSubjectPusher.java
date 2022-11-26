package weblogic.jndi.security.internal.server;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import weblogic.corba.j2ee.naming.ContextImpl;
import weblogic.corba.j2ee.naming.NameParser;
import weblogic.iiop.IIOPClient;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.IiopConfigurationFacade;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORDelegate;
import weblogic.iiop.spi.ServerReferenceDelegate;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jndi.Environment;
import weblogic.jndi.internal.ExceptionTranslator;
import weblogic.jndi.internal.NamingDebugLogger;
import weblogic.jndi.security.SubjectPusher;
import weblogic.kernel.Kernel;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.internal.InitialReferenceConstants;
import weblogic.rmi.internal.ServerReference;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.auth.login.PasswordCredential;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SubjectManagerImpl;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class ServerSubjectPusher implements SubjectPusher, InitialReferenceConstants {
   private static AuthenticatedSubject kernelId;
   private static final DebugCategory debugSecurity = Debug.getCategory("weblogic.jndi.security");

   public ServerSubjectPusher() {
      SubjectManagerImpl.ensureInitialized();
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   public final void pushSubject(Hashtable env, Context context) throws NamingException {
      Environment environment = new Environment(env);
      if (Kernel.isServer()) {
         serverPushSubject(environment, context);
      } else {
         clientPushSubject(environment, context);
      }

   }

   public final void popSubject() {
      SecurityServiceManager.popSubject(kernelId);
   }

   public void clearSubjectStack() {
      while(SecurityServiceManager.getSubjectStackSize() > 0) {
         this.popSubject();
      }

   }

   private static void clientPushSubject(Environment env, Context jndi) throws NamingException {
      UserInfo user = env.getSecurityUser();
      if (user == null && env.isClientCertAvailable()) {
         user = new DefaultUserInfoImpl((String)null, (Object)null);
      }

      if (user != null && user instanceof DefaultUserInfoImpl) {
         AuthenticatedSubject s = env.getSecuritySubject();
         if (s == null) {
            ContextImpl ctx = (ContextImpl)jndi;
            if (IIOPClient.isEnabled() && ctx.getContext() != null && getIor(ctx).getProfile().useSAS()) {
               s = new AuthenticatedSubject();
               s.getPrincipals().add(user);
               if (NamingDebugLogger.isDebugEnabled() || debugSecurity.isEnabled()) {
                  IIOPLogger.logDebugSecurity("pushing user " + user + " using CSIv2");
               }

               addPasswordCreds((DefaultUserInfoImpl)user, s);
            } else {
               if (NamingDebugLogger.isDebugEnabled() || debugSecurity.isEnabled()) {
                  IIOPLogger.logDebugSecurity("authenticating " + user);
               }

               Object o = jndi.lookup("weblogic/security/SecurityManager");
               SecurityService ss = (SecurityService)PortableRemoteObject.narrow(o, SecurityService.class);

               try {
                  AuthenticatedUser au = ss.authenticate((UserInfo)user);
                  s = SecurityServiceManager.getASFromAU(au);
               } catch (RemoteException var8) {
                  throw ExceptionTranslator.toNamingException(var8);
               }
            }

            env.setSecuritySubject(s);
         }

         SecurityServiceManager.pushSubject(kernelId, s);
         if (jndi instanceof ContextImpl) {
            ((ContextImpl)jndi).enableLogoutOnClose();
         }
      }

   }

   private static void serverPushSubject(Environment env, Context context) throws NamingException {
      UserInfo user = env.getSecurityUser();
      if (!isNotDefined(user)) {
         AuthenticatedSubject subject = null;
         if ((subject = env.getSecuritySubject()) != null) {
            subject = env.getSecuritySubject();
         } else {
            try {
               if (user instanceof DefaultUserInfoImpl) {
                  DefaultUserInfoImpl defUser = (DefaultUserInfoImpl)user;
                  if (isWtcUrl(env.getProviderUrl())) {
                     subject = authenticateLocally(defUser);
                  }

                  if (defUser.getName() != null && defUser.getPassword() != null) {
                     if (isLocalServer(context)) {
                        subject = authenticateLocally(context, defUser);
                     } else {
                        subject = authenticateRemotely(user, subject, defUser);
                     }
                  } else if (subject == null) {
                     subject = authenticateLocally(defUser);
                  }
               }
            } catch (NoSuchObjectException | LoginException var6) {
               NamingException ne = new AuthenticationException();
               ne.setRootCause(var6);
               throw ne;
            }
         }

         if (subject != null) {
            env.setSecuritySubject(subject);
            SecurityServiceManager.pushSubject(kernelId, subject);
            if (context instanceof ContextImpl) {
               ((ContextImpl)context).enableLogoutOnClose();
            }
         }

      }
   }

   private static boolean isWtcUrl(String providerUrl) {
      return "local://".equals(providerUrl) || "tgiop".equals(NameParser.getProtocol(providerUrl));
   }

   private static boolean isNotDefined(UserInfo user) {
      return user == null || user.getName() == null || user.getName().length() == 0;
   }

   private static AuthenticatedSubject authenticateRemotely(UserInfo user, AuthenticatedSubject subject, DefaultUserInfoImpl defUser) {
      if (subject == null) {
         subject = new AuthenticatedSubject();
         subject.getPrincipals().add(user);
      }

      addPasswordCreds(defUser, subject);
      return subject;
   }

   private static boolean isLocalServer(Context ctx) {
      if (!(ctx instanceof ContextImpl)) {
         return false;
      } else {
         ContextImpl ci = (ContextImpl)ctx;

         try {
            return ci.getContext() != null && IiopConfigurationFacade.isLocal(getIor(ci));
         } catch (Throwable var3) {
            if (NamingDebugLogger.isDebugEnabled() || debugSecurity.isEnabled()) {
               IIOPLogger.logDebugSecurity("isLocalServer: false; Unable to determine whether is local or not; will treat it as remote. Exception: " + var3);
            }

            return false;
         }
      }
   }

   private static IOR getIor(ContextImpl ci) {
      return ((IORDelegate)ci.getDelegate()).getIOR();
   }

   public static AuthenticatedSubject authenticateLocally(DefaultUserInfoImpl user) throws LoginException {
      PrincipalAuthenticator pa = RmiSecurityFacade.getPrincipalAuthenticator(kernelId, "weblogicDEFAULT");
      SimpleCallbackHandler handler = new SimpleCallbackHandler(user.getName(), user.getPassword());
      return pa.authenticate(handler);
   }

   public static AuthenticatedSubject authenticateLocally(Context context, DefaultUserInfoImpl user) throws LoginException, NoSuchObjectException {
      ManagedInvocationContext ignored = RmiInvocationFacade.setInvocationContext(kernelId, getInvocationContext((ContextImpl)context));
      Throwable var3 = null;

      AuthenticatedSubject var6;
      try {
         PrincipalAuthenticator pa = RmiSecurityFacade.getPrincipalAuthenticator(kernelId, "weblogicDEFAULT");
         SimpleCallbackHandler handler = new SimpleCallbackHandler(user.getName(), user.getPassword());
         var6 = pa.authenticate(handler);
      } catch (Throwable var15) {
         var3 = var15;
         throw var15;
      } finally {
         if (ignored != null) {
            if (var3 != null) {
               try {
                  ignored.close();
               } catch (Throwable var14) {
                  var3.addSuppressed(var14);
               }
            } else {
               ignored.close();
            }
         }

      }

      return var6;
   }

   private static ComponentInvocationContext getInvocationContext(ContextImpl context) throws NoSuchObjectException {
      return getServerReference(context).getInvocationContext();
   }

   private static ServerReference getServerReference(ContextImpl context) throws NoSuchObjectException {
      return ((ServerReferenceDelegate)context.getDelegate()).getServerReference();
   }

   private static void addPasswordCreds(DefaultUserInfoImpl user, final AuthenticatedSubject subject) {
      if (user.getName() != null && user.getPassword() != null) {
         final PasswordCredential passwordCred = new PasswordCredential(user.getName(), user.getPassword());
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               subject.getPrivateCredentials(ServerSubjectPusher.kernelId).add(passwordCred);
               return null;
            }
         });
      }

   }

   static void p(String s) {
      NamingDebugLogger.debug("<ServerSecurityManager> " + s);
   }
}
