package weblogic.security.acl.internal;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.cert.Certificate;
import javax.security.auth.login.LoginException;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.KernelTypeService;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.protocol.Protocol;
import weblogic.rjvm.LocalRJVM;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.SecurityServiceGenerator;
import weblogic.security.acl.UserInfo;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.LocatorUtilities;

@Service
public final class Security implements FastThreadLocalMarker {
   private static final AuditableThreadLocal threadSSLClientInfo = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue(true));
   private static final String NON_PARTITION_ID = "DOMAIN";
   private static AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static boolean enableDefaultUserProperty = false;

   public static void init() {
   }

   private static AuthenticatedUser authenticateLocally(UserInfo user) throws SecurityException {
      AuthenticatedSubject as = null;
      PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelID, SecurityServiceManager.getContextSensitiveRealmName());

      try {
         if (user instanceof DefaultUserInfoImpl) {
            DefaultUserInfoImpl ui = (DefaultUserInfoImpl)user;
            as = pa.authenticate(new SimpleCallbackHandler(ui.getName(), ui.getPassword()), (ContextHandler)null);
         }

         return as;
      } catch (LoginException var4) {
         throw new SecurityException(var4.getMessage());
      }
   }

   public static AuthenticatedUser authenticate(UserInfo ui, RJVM rjvm, Protocol protocol, String channel) throws RemoteException, SecurityException {
      return authenticate(ui, rjvm, protocol, channel, 0L, false, (String)null);
   }

   public static AuthenticatedUser authenticate(UserInfo ui, RJVM rjvm, Protocol protocol, String channel, long timeout, boolean enableDefaultUser, String url) throws RemoteException, SecurityException {
      String partitionName = null;
      if (url == null) {
         partitionName = "DOMAIN";
      }

      return authenticate(ui, rjvm, protocol, channel, timeout, enableDefaultUser, partitionName, url);
   }

   public static AuthenticatedUser authenticate(final UserInfo ui, RJVM rjvm, Protocol protocol, String channel, long timeout, boolean enableDefaultUser, String partitionName, String url) throws RemoteException, SecurityException {
      if (rjvm.getID().isLocal()) {
         return authenticateLocally(ui);
      } else {
         rjvm = RJVMManager.getRJVMManager().findOrCreate(rjvm.getID());
         SecurityServiceGenerator generator = (SecurityServiceGenerator)LocatorUtilities.getService(SecurityServiceGenerator.class);
         AuthenticatedUser user;
         final SecurityService stub;
         if (isRMIBootstrapPossible(rjvm)) {
            stub = generator.createRMIBootService(rjvm, channel, (int)timeout, partitionName, url);

            try {
               AuthenticatedSubject anon = SubjectUtils.getAnonymousSubject();
               user = (AuthenticatedUser)SecurityManager.runAs(kernelID, anon, new PrivilegedExceptionAction() {
                  public Object run() throws RemoteException {
                     return stub.authenticate(ui);
                  }
               });
            } catch (PrivilegedActionException var14) {
               RemoteException re = (RemoteException)var14.getException();
               if (re.getCause() instanceof SecurityException) {
                  throw (SecurityException)re.getCause();
               }

               throw re;
            }
         } else {
            stub = generator.createBootService(rjvm, protocol, partitionName);
            user = stub.authenticate(ui);
         }

         if (!((KernelTypeService)LocatorUtilities.getService(KernelTypeService.class)).isServer()) {
            AuthenticatedSubject defaultUser = user == null ? null : SecurityServiceManager.getASFromAU(user);
            if (enableDefaultUser || enableDefaultUserProperty) {
               SecurityManager.setDefaultUser(defaultUser);
            }
         }

         return user;
      }
   }

   private static boolean isRMIBootstrapPossible(RJVM rjvm) {
      if (!(rjvm instanceof PeerInfoable)) {
         return false;
      } else {
         PeerInfo peerInfo = ((PeerInfoable)rjvm).getPeerInfo();
         if (LocalRJVM.getLocalRJVM().getPeerInfo().equals(peerInfo)) {
            return true;
         } else if (peerInfo == null) {
            return false;
         } else {
            int major = peerInfo.getMajor();
            int minor = peerInfo.getMinor();
            int sp = peerInfo.getServicePack();
            return major > 8 || major == 6 && minor == 1 && sp >= 5 || major == 7 && minor == 0 && sp >= 3 || major == 8 && minor == 1 && sp >= 1;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static SSLClientInfoService getThreadSSLClientInfo() {
      Object o = threadSSLClientInfo.get();
      SSLClientInfoService sslCI = null;
      if (o != null && o instanceof SSLClientInfoService) {
         sslCI = (SSLClientInfoService)o;
      } else {
         sslCI = (SSLClientInfoService)LocatorUtilities.getService(SSLClientInfoService.class);
         threadSSLClientInfo.set(sslCI);
      }

      return sslCI;
   }

   public static void setThreadSSLClientInfo(SSLClientInfoService sslci) {
      threadSSLClientInfo.set(sslci);
   }

   /** @deprecated */
   @Deprecated
   public static final void setSSLRootCAFingerprints(String fps) {
      getThreadSSLClientInfo().setRootCAfingerprints(fps);
   }

   /** @deprecated */
   @Deprecated
   public static final void setSSLRootCAFingerprints(byte[][] fps) {
      getThreadSSLClientInfo().setRootCAfingerprints(fps);
   }

   /** @deprecated */
   @Deprecated
   public static final byte[][] getSSLRootCAFingerprints() {
      return getThreadSSLClientInfo().getRootCAfingerprints();
   }

   /** @deprecated */
   @Deprecated
   public static final void setSSLServerName(String name) {
      getThreadSSLClientInfo().setExpectedName(name);
   }

   /** @deprecated */
   @Deprecated
   public static final String getSSLServerName() {
      return getThreadSSLClientInfo().getExpectedName();
   }

   /** @deprecated */
   @Deprecated
   public static final Object getSSLClientCertificate() throws IOException {
      return getThreadSSLClientInfo().getSSLClientCertificate();
   }

   /** @deprecated */
   @Deprecated
   public static final void setSSLClientCertificate(InputStream[] chain) {
      getThreadSSLClientInfo().setSSLClientCertificate(chain);
   }

   /** @deprecated */
   @Deprecated
   public static final void setSSLClientKeyPassword(String pass) {
      getThreadSSLClientInfo().setSSLClientKeyPassword(pass);
   }

   /** @deprecated */
   @Deprecated
   public static final String getSSLClientKeyPassword() {
      return getThreadSSLClientInfo().getSSLClientKeyPassword();
   }

   /** @deprecated */
   @Deprecated
   public static final void loadLocalIdentity(Certificate[] certs, PrivateKey privateKey) {
      getThreadSSLClientInfo().loadLocalIdentity(certs, privateKey);
   }

   public static final boolean isClientCertAvailable() {
      return getThreadSSLClientInfo().isClientCertAvailable();
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }

   static {
      if (!((KernelTypeService)LocatorUtilities.getService(KernelTypeService.class)).isServer() && !((KernelTypeService)LocatorUtilities.getService(KernelTypeService.class)).isApplet()) {
         String enableDefaultUser = System.getProperty("weblogic.jndi.enableDefaultUser");
         if (enableDefaultUser != null) {
            enableDefaultUserProperty = true;
         }
      }

   }
}
