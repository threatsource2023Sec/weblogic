package weblogic.transaction.internal;

import java.rmi.server.ServerNotActiveException;
import java.security.AccessController;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

final class ReceiveSecureAction {
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   static final boolean stranger(HostID hostID, String actionNm) {
      AuthenticatedSubject caller = SecurityServiceManager.getCurrentSubject(kernelID);
      int acceptReceivedCall = RemoteDomainSecurityHelper.acceptRemoteDomainCall(hostID, caller);
      if (acceptReceivedCall == 1) {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("SecureAction.stranger  Subject used on received call: " + SubjectUtils.getUsername(caller) + " for action :" + actionNm + " AUTHENTICATION FAILED ");
         }

         return true;
      } else if (acceptReceivedCall == 0) {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("SecureAction.stranger  Subject used on received call: " + SubjectUtils.getUsername(caller) + " for action: " + actionNm + " AUTHENTICATION SUCCESSFUL ");
         }

         return false;
      } else {
         if (acceptReceivedCall == 2) {
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("SecureAction.stranger  Subject used on received call: " + SubjectUtils.getUsername(caller) + " for action: " + actionNm + " AUTHENTICATION UNDETERMINABLE  use SecurityInteropMode");
            }

            int mode = ServerTransactionManagerImpl.getInteropMode();
            if (caller == null || RmiSecurityFacade.isSubjectAnonymous(caller)) {
               return mode != 1 && kernelID.getQOS() == 103;
            }

            if (SecurityServiceManager.isKernelIdentity(caller)) {
               return false;
            }

            PeerInfo peerInfo = null;

            try {
               EndPoint endPoint = ServerHelper.getClientEndPoint();
               if (endPoint != null && endPoint instanceof PeerInfoable) {
                  peerInfo = ((PeerInfoable)endPoint).getPeerInfo();
               }
            } catch (ServerNotActiveException var7) {
               return true;
            }

            if (peerInfo == null) {
               return true;
            }

            if (peerInfo.getMajor() == 6 && "system".equals(SubjectUtils.getUsername(caller))) {
               return false;
            }
         }

         return true;
      }
   }
}
