package weblogic.security.acl.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Protocol;
import weblogic.rjvm.LocalRJVM;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.SecurityServiceGenerator;
import weblogic.security.acl.UserInfo;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.LocatorUtilities;

public final class RemoteAuthenticate {
   private static AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static boolean enableDefaultUserProperty = false;

   public static AuthenticatedUser authenticate(final UserInfo ui, RJVM rjvm, Protocol protocol, String channel, boolean enableDefaultUser, int timeout, String partitionURL) throws RemoteException, SecurityException {
      if (rjvm.isDead()) {
         rjvm = RJVMManager.getRJVMManager().findOrCreate(rjvm.getID());
      }

      SecurityServiceGenerator generator = (SecurityServiceGenerator)LocatorUtilities.getService(SecurityServiceGenerator.class);
      AuthenticatedUser user;
      final SecurityService stub;
      if (isRMIBootstrapPossible(rjvm)) {
         stub = generator.createRMIBootService(rjvm, channel, timeout, (String)null, partitionURL);

         try {
            AuthenticatedSubject anon = SubjectUtils.getAnonymousSubject();
            user = (AuthenticatedUser)SecurityManager.runAs(kernelID, anon, new PrivilegedExceptionAction() {
               public Object run() throws RemoteException {
                  return stub.authenticate(ui);
               }
            });
         } catch (PrivilegedActionException var12) {
            RemoteException re = (RemoteException)var12.getException();
            if (re.getCause() instanceof SecurityException) {
               throw (SecurityException)re.getCause();
            }

            throw re;
         }
      } else {
         stub = generator.createBootService(rjvm, protocol, (String)null, (String)null, partitionURL);
         user = stub.authenticate(ui);
      }

      if (!KernelStatus.isServer()) {
         AuthenticatedSubject defaultUser = user == null ? null : SecurityServiceManager.getASFromAU(user);
         if (enableDefaultUser || enableDefaultUserProperty) {
            SecurityManager.setDefaultUser(defaultUser);
         }
      }

      rjvm.setUser(partitionURL, user);
      return user;
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

   static {
      if (!KernelStatus.isServer() && !KernelStatus.isApplet()) {
         String enableDefaultUser = System.getProperty("weblogic.jndi.enableDefaultUser");
         if (enableDefaultUser != null) {
            enableDefaultUserProperty = true;
         }
      }

   }
}
