package weblogic.security.auth;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.jndi.api.ThreadEnvironmentService;
import weblogic.kernel.KernelTypeService;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.rjvm.ClientServerURL;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.acl.internal.Security;
import weblogic.security.auth.login.PasswordCredential;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.AssertionError;
import weblogic.utils.LocatorUtilities;

public final class Authenticate {
   public static void authenticate(ServerEnvironment env, final Subject subject) throws LoginException, IOException, RemoteException {
      String url = env.getProviderUrl();
      ClientServerURL clntSrvrURL = null;
      RJVM hostVM = null;
      if (url != null && url.length() != 0 && url != "local://") {
         ThreadEnvironmentService threadEnvironment = (ThreadEnvironmentService)GlobalServiceLocator.getServiceLocator().getService(ThreadEnvironmentService.class, new Annotation[0]);
         threadEnvironment.push(env);

         try {
            clntSrvrURL = new ClientServerURL(url);
            hostVM = clntSrvrURL.findOrCreateRJVM(env.getProviderChannel());
         } finally {
            threadEnvironment.pop();
         }
      } else {
         if (!((KernelTypeService)LocatorUtilities.getService(KernelTypeService.class)).isServer()) {
            return;
         }

         hostVM = RJVMManager.getLocalRJVM();
         env.setProperty("java.naming.provider.url", (Object)null);
      }

      UserInfo user = env.getSecurityUser();
      AuthenticatedUser au = null;
      boolean isServer = hostVM.equals(RJVMManager.getLocalRJVM());
      if (user == null && env.isClientCertAvailable()) {
         user = new DefaultUserInfoImpl((String)null, (Object)null);
      }

      if (user != null) {
         ThreadEnvironmentService threadEnvironment = (ThreadEnvironmentService)GlobalServiceLocator.getServiceLocator().getService(ThreadEnvironmentService.class, new Annotation[0]);
         threadEnvironment.push(env);

         try {
            au = Security.authenticate((UserInfo)user, hostVM, getProtocol(env), env.getProviderChannel(), 0L, false, url);
         } catch (SecurityException var23) {
            String msg = var23.toString();
            int idx = msg.indexOf("Start server side stack trace:");
            if (idx > 0) {
               msg = msg.substring(0, idx - 1);
            }

            throw new LoginException(msg);
         } finally {
            threadEnvironment.pop();
         }

         AuthenticatedSubject auSubject = SecurityServiceManager.getASFromAU(au);
         env.setSecuritySubject(auSubject);

         try {
            if (Boolean.getBoolean("weblogic.security.authenticatePushSubject")) {
               AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
               SecurityServiceManager.pushSubject(kernelId, auSubject);
            }
         } catch (SecurityException var21) {
         }

         subject.getPrincipals().addAll(auSubject.getPrincipals());
         if (user instanceof DefaultUserInfoImpl) {
            DefaultUserInfoImpl defUser = (DefaultUserInfoImpl)user;
            if (defUser.getName() != null && defUser.getPassword() != null) {
               final PasswordCredential passwordCred = new PasswordCredential(defUser.getName(), defUser.getPassword());
               AccessController.doPrivileged(new PrivilegedAction() {
                  public Object run() {
                     subject.getPrivateCredentials().add(passwordCred);
                     return null;
                  }
               });
            }

            env.setSecurityUser(au);
            if (!((KernelTypeService)LocatorUtilities.getService(KernelTypeService.class)).isServer() && au != null) {
               hostVM.setUser(clntSrvrURL.getCurrentPartitionURL(), au);
            }
         }
      }

   }

   private static Protocol getProtocol(ServerEnvironment env) {
      try {
         String url = env.getProviderUrl();
         Protocol protocol = null;
         if (url == "local://") {
            T3ProtocolFetcherService fetcher = (T3ProtocolFetcherService)LocatorUtilities.getService(T3ProtocolFetcherService.class);
            protocol = fetcher.fetchT3Protocol();
         } else {
            protocol = ProtocolManager.getProtocolByName((new ClientServerURL(url)).getProtocol());
         }

         return protocol;
      } catch (MalformedURLException var4) {
         throw new AssertionError(var4);
      }
   }

   public static void logout(Subject subject) throws LoginException, IOException, RemoteException {
      Set s = subject.getPrincipals();
      s.clear();
      s = subject.getPrivateCredentials();
      s.clear();
      s = subject.getPublicCredentials();
      s.clear();
   }
}
