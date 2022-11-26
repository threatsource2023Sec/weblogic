package weblogic.security.acl.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.common.internal.BootServicesStub;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.common.internal.RMIBootServiceStub;
import weblogic.protocol.Protocol;
import weblogic.rjvm.LocalRJVM;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.UserInfo;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;

public final class WlT3ClientSecurity {
   private static AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void init() {
   }

   public static AuthenticatedUser authenticate(final UserInfo ui, RJVM rjvm, Protocol protocol, String channel, String url) throws RemoteException, SecurityException {
      long timeout = 0L;
      rjvm = RJVMManager.getRJVMManager().findOrCreate(rjvm.getID());
      AuthenticatedUser user = null;
      if (isRMIBootstrapPossible(rjvm)) {
         final SecurityService stub = RMIBootServiceStub.getStub(rjvm, channel, url == null ? "DOMAIN" : null, url);

         try {
            AuthenticatedSubject anon = SubjectUtils.getAnonymousSubject();
            user = (AuthenticatedUser)SecurityManager.runAs(kernelID, anon, new PrivilegedExceptionAction() {
               public Object run() throws RemoteException {
                  return stub.authenticate(ui);
               }
            });
         } catch (PrivilegedActionException var11) {
            RemoteException re = (RemoteException)var11.getException();
            if (re.getCause() instanceof SecurityException) {
               throw (SecurityException)re.getCause();
            }

            throw re;
         }
      } else {
         BootServicesStub stub = new BootServicesStub(rjvm, protocol);
         user = stub.authenticate(ui);
      }

      if (user == null) {
         Object var10000 = null;
      } else {
         SecurityServiceManager.getASFromAU(user);
      }

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
}
