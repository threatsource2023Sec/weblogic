package weblogic.cluster;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class AlternateLivelinessChecker {
   private static final boolean DEBUG = false;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String SECRET_STRING;
   private static final byte[] SERVER_HASH_VALUE;
   private HashMap unreachableMap = new HashMap();
   private Set underExecutionSet = new HashSet();

   public static AlternateLivelinessChecker getInstance() {
      return AlternateLivelinessChecker.Factory.THE_ONE;
   }

   public synchronized void reachable(HostID memberID) {
      this.unreachableMap.remove(memberID);
   }

   private int getRetryCount() {
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      return clusterMBean.getHTTPPingRetryCount();
   }

   private int getCurrentCount(HostID memberID) {
      Integer retry = (Integer)this.unreachableMap.get(memberID);
      return retry != null ? retry : 0;
   }

   private int getActiveServerRetryCount() {
      Iterator memberIDs = this.unreachableMap.keySet().iterator();
      int activeCount = 0;

      while(memberIDs.hasNext()) {
         if (this.getCurrentCount((HostID)memberIDs.next()) < this.getRetryCount()) {
            ++activeCount;
         }
      }

      return activeCount;
   }

   synchronized void addToUnreachableSet(HostID memberID) {
      int count = this.getCurrentCount(memberID);
      ++count;
      this.unreachableMap.put(memberID, count);
      this.underExecutionSet.remove(memberID);
   }

   synchronized void addToReachableSet(HostID memberID) {
      this.unreachableMap.put(memberID, 0);
      this.underExecutionSet.remove(memberID);
   }

   public synchronized boolean isUnreachable(long lastSeqNum, HostID memberID) {
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      if (clusterMBean.getMaxServerCountForHttpPing() <= 0) {
         return true;
      } else if (this.getCurrentCount(memberID) >= this.getRetryCount()) {
         return true;
      } else if (this.underExecutionSet.contains(memberID)) {
         return false;
      } else if (this.getActiveServerRetryCount() >= clusterMBean.getMaxServerCountForHttpPing()) {
         return true;
      } else {
         if (this.unreachableMap.get(memberID) == null) {
            this.unreachableMap.put(memberID, 0);
         }

         HTTPPingRequest request = new HTTPPingRequest(lastSeqNum, memberID);
         WorkManagerFactory.getInstance().getSystem().schedule(request);
         this.underExecutionSet.add(memberID);
         return false;
      }
   }

   private static void debug(String s) {
      Debug.say("[AlternateLivenessChecker] " + s);
   }

   static {
      SECRET_STRING = "&ServerName=" + ManagementService.getRuntimeAccess(kernelId).getServer().getName() + "&PingOnly=true";
      SERVER_HASH_VALUE = ClusterService.getClusterServiceInternal().getSecureHash();
   }

   private final class HTTPPingRequest extends WorkAdapter {
      private static final boolean DEBUG = false;
      private HttpURLConnection con;
      private DataInputStream in;
      private final String request;
      private final HostID memberID;
      private final long lastSeqNum;

      HTTPPingRequest(long lastSeqNum, HostID memberID) {
         this.lastSeqNum = lastSeqNum;
         this.request = this.getHeader();
         this.memberID = memberID;
      }

      private void connect() throws ConnectException, IOException {
         URL url = this.getServerURL();
         this.con = (HttpURLConnection)url.openConnection();
         this.con.setRequestMethod("POST");
         this.con.setDoInput(true);
         this.con.setDoOutput(true);
         OutputStream out = this.con.getOutputStream();
         this.con.connect();
         out.write(AlternateLivelinessChecker.SERVER_HASH_VALUE);
         out.flush();
         out.close();
         this.in = new DataInputStream(this.con.getInputStream());
      }

      private URL getServerURL() throws MalformedURLException {
         return ClusterHelper.fabricateHTTPURL(this.request, this.memberID);
      }

      public void run() {
         try {
            this.connect();
            if (this.con.getResponseCode() == 200) {
               AlternateLivelinessChecker.this.addToReachableSet(this.memberID);
            } else {
               AlternateLivelinessChecker.this.addToUnreachableSet(this.memberID);
            }
         } catch (ConnectException var12) {
            AlternateLivelinessChecker.this.addToUnreachableSet(this.memberID);
         } catch (IOException var13) {
            AlternateLivelinessChecker.this.addToUnreachableSet(this.memberID);
         } finally {
            try {
               if (this.in != null) {
                  this.in.close();
               }
            } catch (IOException var11) {
            }

            if (this.con != null) {
               this.con.disconnect();
            }

         }

      }

      private String getHeader() {
         StringBuilder sb = new StringBuilder("/bea_wls_cluster_internal/psquare/p2?");
         sb.append("partitionId=").append(MulticastSessionIDConstants.HEARTBEAT_SENDER_ID.getPartitionID()).append("&resourceGroupName=").append(MulticastSessionIDConstants.HEARTBEAT_SENDER_ID.getResourceGroupName()).append("&sessionName=").append(MulticastSessionIDConstants.HEARTBEAT_SENDER_ID.getName()).append("&lastSeqNum=").append(this.lastSeqNum).append("&PeerInfo=").append(ClusterHelper.STRINGFIED_PEERINFO).append(AlternateLivelinessChecker.SECRET_STRING);
         return sb.toString();
      }
   }

   private static final class Factory {
      static final AlternateLivelinessChecker THE_ONE = new AlternateLivelinessChecker();
   }
}
