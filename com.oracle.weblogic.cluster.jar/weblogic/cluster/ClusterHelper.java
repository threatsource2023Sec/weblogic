package weblogic.cluster;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import weblogic.common.internal.PeerInfo;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.StringUtils;
import weblogic.utils.XSSUtils;
import weblogic.utils.io.DataIO;
import weblogic.utils.net.AddressUtils;
import weblogic.utils.net.SocketResetException;

public final class ClusterHelper {
   public static final String STRINGFIED_PEERINFO = PeerInfo.getPeerInfo().getMajor() + "," + PeerInfo.getPeerInfo().getMinor() + "," + PeerInfo.getPeerInfo().getServicePack() + "," + PeerInfo.getPeerInfo().getRollingPatch() + "," + PeerInfo.getPeerInfo().getPatchUpdate();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DomainMBean domain;

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public static URL fabricateHTTPURL(String uri, HostID target) throws MalformedURLException {
      String url = getURLManagerService().findAdministrationURL((ServerIdentity)target);
      if (url == null) {
         throw new MalformedURLException("Could not construct URL for: " + target);
      } else {
         url = getURLManagerService().normalizeToHttpProtocol(url);
         return new URL(new URL(url), uri);
      }
   }

   public static String getMachineName() {
      return AddressUtils.getLocalHost().getHostName();
   }

   public static PeerInfo getPeerInfo(String peerInfo) {
      return PeerInfo.getPeerInfo(peerInfo);
   }

   static void logStateDumpRequestRejection(HttpURLConnection con, IOException ioe, String memberID) {
      String remoteErrorMessage = null;
      if (con != null) {
         int contentLength = con.getContentLength();
         InputStream errorStream = con.getErrorStream();
         if (contentLength > 0 && errorStream != null) {
            DataInputStream dis = new DataInputStream(errorStream);
            byte[] b = new byte[contentLength];

            try {
               DataIO.readFully(dis, b);
               remoteErrorMessage = StringUtils.getString(b);
            } catch (IOException var17) {
            } finally {
               try {
                  if (dis != null) {
                     dis.close();
                  }
               } catch (IOException var16) {
               }

            }
         }
      }

      if (remoteErrorMessage != null) {
         ClusterLogger.logFailedWhileReceivingStateDumpWithMessage(memberID, ioe, remoteErrorMessage, ClusterService.getClusterServiceInternal().getLocalServerDetails());
      } else if (ClusterAnnouncementsDebugLogger.isDebugEnabled() || !SocketResetException.isResetException(ioe)) {
         ClusterLogger.logFailedWhileReceivingStateDump(memberID, ioe);
      }

   }

   public static String encodeXSS(String text) {
      return XSSUtils.encodeXSS(text);
   }

   public static String getPartitionNameFromPartitionId(String partitionId) {
      PartitionMBean partitionMBean = getDomain().findPartitionByID(partitionId);
      if (partitionMBean == null) {
         throw new RuntimeException("No Partition Found by Identifier '" + partitionId + "'");
      } else {
         return partitionMBean.getName();
      }
   }

   public static String getPartitionIdFromName(String partitionName) {
      PartitionMBean partitionMBean = getDomain().lookupPartition(partitionName);
      if (partitionMBean == null) {
         throw new RuntimeException("No Partition Found by name '" + partitionName + "'");
      } else {
         return partitionMBean.getPartitionID();
      }
   }

   public static String extractPartitionName(String serviceName) {
      int idx = serviceName.indexOf(".");
      return idx >= 0 ? serviceName.substring(0, idx) : null;
   }

   public static boolean isServerShuttingDown() {
      ServerRuntimeMBean runtime = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime();
      return runtime.isShuttingDown();
   }

   public static String getServerState() {
      ServerRuntimeMBean runtime = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime();
      return runtime.getState();
   }

   public static String getPartitionState(String partitionName) {
      PartitionRuntimeMBean partitionRuntimeMBean = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().lookupPartitionRuntime(partitionName);
      if (partitionRuntimeMBean != null) {
         PartitionRuntimeMBean.State st = partitionRuntimeMBean.getInternalState();
         if (st != null) {
            return st.toString();
         }
      }

      return null;
   }

   public static boolean isPartitionShuttingDown(String partitionName) {
      if (partitionName != null && !partitionName.isEmpty() && !partitionName.equals("DOMAIN")) {
         PartitionRuntimeMBean partitionRuntimeMBean = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().lookupPartitionRuntime(partitionName);
         if (partitionRuntimeMBean != null) {
            PartitionRuntimeMBean.State internalState = partitionRuntimeMBean.getInternalState();
            if (State.FORCE_SHUTTING_DOWN.equals(internalState) || State.SHUTTING_DOWN.equals(internalState) || State.HALTING.equals(internalState)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static DomainMBean getDomain() {
      return domain;
   }

   static {
      domain = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
   }
}
