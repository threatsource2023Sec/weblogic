package weblogic.cluster;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.ConnectionSigner;
import weblogic.protocol.URLManager;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.io.DataIO;
import weblogic.work.WorkAdapter;

final class HTTPExecuteRequest extends WorkAdapter {
   private HttpURLConnection con;
   private DataInputStream in;
   private final String request;
   private final MulticastSessionId multicastSessionId;
   private final HostID memberID;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String SECRET_STRING;
   private static final byte[] SERVER_HASH_VALUE;
   private static final boolean DEBUG = false;

   HTTPExecuteRequest(long lastSeqNum, MulticastSessionId multicastSessionId, HostID memberID) {
      this.multicastSessionId = multicastSessionId;
      this.request = this.getHeader(lastSeqNum);
      this.memberID = memberID;
   }

   private void connect() throws ConnectException, IOException {
      URL url = this.getServerURL();
      this.con = URLManager.createAdminHttpConnection(url, true);
      ConnectionSigner.signConnection(this.con, kernelId, this.memberID.getServerName());
      this.con.setRequestMethod("POST");
      this.con.setDoInput(true);
      this.con.setDoOutput(true);
      OutputStream out = this.con.getOutputStream();
      this.con.connect();
      out.write(SERVER_HASH_VALUE);
      out.flush();
      out.close();
      this.in = new DataInputStream(this.con.getInputStream());
   }

   private URL getServerURL() throws MalformedURLException {
      return ClusterHelper.fabricateHTTPURL(this.request, this.memberID);
   }

   public void run() {
      ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
      if (cic.getApplicationName() == null) {
         cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "_HTTP_EXECUTE_REQUEST_", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
      }

      try {
         ManagedInvocationContext mic = mgr.setCurrentComponentInvocationContext(cic);
         Throwable var4 = null;

         try {
            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               ClusterLogger.logFetchServerStateDump(this.memberID.toString(), this.multicastSessionId.getName());
            }

            this.connect();
            if (this.con.getResponseCode() != 200) {
               throw new IOException("Failed to get OK response");
            }

            byte[] b = this.readHttpResponse(this.in, this.con.getContentLength());
            WLObjectInputStream ois = ClusterMessagesManager.getInputStream(b);
            MemberAttributes attributes = (MemberAttributes)ois.readObject();
            final GroupMessage finalmsg = (GroupMessage)ois.readObject();
            long currentSeqNum = ois.readLong();
            if (!(finalmsg instanceof MulticastSessionDataRecoveryServlet.MulticastSessionNotActiveMessage)) {
               this.processAttributes(attributes);
               final HostID finalid = this.memberID;
               SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
                  public Object run() {
                     finalmsg.execute(finalid);
                     if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
                        ClusterExtensionLogger.logFetchServerStateDumpComplete(HTTPExecuteRequest.this.memberID.toString(), HTTPExecuteRequest.this.multicastSessionId.getName());
                     }

                     return null;
                  }
               });
               return;
            }
         } catch (Throwable var43) {
            var4 = var43;
            throw var43;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var42) {
                     var4.addSuppressed(var42);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (ConnectException var45) {
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterLogger.logFailedWhileReceivingStateDump(this.memberID.toString(), var45);
         }

         return;
      } catch (IOException var46) {
         ClusterHelper.logStateDumpRequestRejection(this.con, var46, this.memberID.toString());
         return;
      } catch (ClassNotFoundException var47) {
         ClusterLogger.logFailedToDeserializeStateDump(this.memberID.toString(), var47);
         return;
      } finally {
         try {
            if (this.in != null) {
               this.in.close();
            }
         } catch (IOException var41) {
         }

         if (this.con != null) {
            this.con.disconnect();
         }

         this.resetHTTPRequestDispatchFlag();
      }

   }

   private void resetHTTPRequestDispatchFlag() {
      RemoteMemberInfo info = MemberManager.theOne().findOrCreate(this.memberID);

      try {
         HybridClusterMessageReceiver receiver = (HybridClusterMessageReceiver)info.findOrCreateReceiver(this.multicastSessionId, true);
         receiver.setHttpRequestDispatched(false);
      } finally {
         MemberManager.theOne().done(info);
      }

   }

   private String getHeader(long lastSeqNum) {
      StringBuilder sb = new StringBuilder("/bea_wls_cluster_internal/psquare/p2?");
      sb.append("&lastSeqNum=").append(lastSeqNum).append("&PeerInfo=").append(ClusterHelper.STRINGFIED_PEERINFO).append("&partitionId=").append(this.multicastSessionId.getPartitionID()).append("&resourceGroupName=").append(this.multicastSessionId.getResourceGroupName()).append("&sessionName=").append(this.multicastSessionId.getName()).append(SECRET_STRING);
      return sb.toString();
   }

   private byte[] readHttpResponse(DataInputStream is, int contentLength) throws IOException, ProtocolException {
      byte[] b = new byte[contentLength];
      DataIO.readFully(is, b);
      return b;
   }

   private void processAttributes(MemberAttributes attributes) {
      RemoteMemberInfo info = MemberManager.theOne().findOrCreate(attributes.identity());

      try {
         info.processAttributes(attributes);
      } finally {
         MemberManager.theOne().done(info);
      }

   }

   static {
      SECRET_STRING = "&ServerName=" + ManagementService.getRuntimeAccess(kernelId).getServer().getName();
      SERVER_HASH_VALUE = ClusterService.getClusterServiceInternal().getSecureHash();
   }
}
