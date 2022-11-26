package weblogic.jdbc.rmi.internal;

import java.rmi.server.ServerNotActiveException;
import java.security.AccessController;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jdbc.common.internal.JDBCServerHelperImpl;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.rmi.RMIWrapperImpl;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.EndPoint;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class RMISkelWrapperImpl extends RMIWrapperImpl {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final UnsupportedOperationException noRemoteJDBCException = new UnsupportedOperationException("Remote JDBC not supported");

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      super.preInvocationHandler(methodName, params);
      if (JDBCServerHelperImpl.rmiSecure()) {
         EndPoint endPoint = null;
         PeerInfo peerInfo = null;

         try {
            endPoint = ServerHelper.getClientEndPoint();
         } catch (ServerNotActiveException var7) {
         }

         if (endPoint != null && endPoint instanceof PeerInfoable) {
            peerInfo = ((PeerInfoable)endPoint).getPeerInfo();
         }

         if (peerInfo == null || !peerInfo.isServer()) {
            if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("rejecting: caller not a server. " + endPoint);
            }

            throw noRemoteJDBCException;
         }

         AuthenticatedSubject caller = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
         int acceptReceivedCall = true;
         int acceptReceivedCall = RemoteDomainSecurityHelper.acceptRemoteDomainCall(endPoint.getHostID(), caller);
         if (acceptReceivedCall == 1) {
            if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("rejecting: invalid cross-domain subject " + caller);
            }

            throw noRemoteJDBCException;
         }

         if (acceptReceivedCall == 0) {
            if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("accepting call from remote domain with subject " + caller);
            }

            return;
         }

         if (acceptReceivedCall == 2) {
            if (!SecurityServiceManager.isKernelIdentity(caller)) {
               if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
                  JdbcDebug.JDBCRMI.debug("rejecting: invalid subject for intra-domain call " + caller);
               }

               throw noRemoteJDBCException;
            }

            if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("accepting intra-domain call with subject " + caller);
            }
         }
      }

   }
}
