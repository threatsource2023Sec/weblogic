package weblogic.cluster;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.io.DataIO;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class StateDumpServlet extends HttpServlet implements MulticastSessionIDConstants {
   private static final int DEFAULT_BUF_SIZE = 10240;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String serverName = req.getParameter("ServerName");
      InputStream in = req.getInputStream();
      int length = req.getContentLength();
      if (length <= 0) {
         res.sendError(403, "Invalid content-length: " + length);
      } else {
         byte[] originalHash = new byte[length];
         DataIO.readFully(req.getInputStream(), originalHash);
         String peerInfoAsString;
         if (!ClusterService.getClusterServiceInternal().checkRequest(serverName, originalHash)) {
            peerInfoAsString = ClusterHelper.encodeXSS(serverName);
            res.sendError(403, this.getServerHashInvalidMessage(peerInfoAsString));
         } else if (ClusterService.getClusterServiceInternal().multicastDataEncryptionEnabled() && !req.isSecure()) {
            ClusterLogger.logEnforceSecureRequest();
            peerInfoAsString = ClusterHelper.encodeXSS(serverName);
            res.sendError(403, this.getUnsecureMulticastMessage(peerInfoAsString));
         } else {
            peerInfoAsString = req.getParameter("PeerInfo");
            PeerInfo info = ClusterHelper.getPeerInfo(peerInfoAsString);
            Debug.assertion(info != null, "Peer info cannot be null");
            String partitionId = req.getParameter("partitionId");
            if (partitionId == null) {
               partitionId = "0";
            }

            String resourceGroupName = req.getParameter("resourceGroupName");
            if (resourceGroupName == null) {
               resourceGroupName = "NO_RESOURCE_GROUP";
            }

            String sessionName = req.getParameter("sessionName");
            if (sessionName == null) {
               sessionName = MulticastSessionIDConstants.ANNOUNCEMENT_MANAGER_ID.getName();
            }

            MulticastSessionId multicastSessionId = new MulticastSessionId(partitionId, resourceGroupName, sessionName);
            UnsyncByteArrayOutputStream baos = null;
            WLObjectOutputStream oos = null;
            OutputStream out = null;

            try {
               baos = new UnsyncByteArrayOutputStream(10240);
               oos = UpgradeUtils.getInstance().getOutputStream(baos, ServerChannelManager.findDefaultLocalServerChannel(), info);
               ArrayList list = (ArrayList)MemberManager.theOne().getRemoteMembers();
               if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
                  ClusterAnnouncementsDebugLogger.debug("Sending Clustered StateDump for " + (list.size() + 1) + " servers for senderID: " + multicastSessionId);
               }

               oos.writeInt(list.size());

               for(int i = 0; i < list.size(); ++i) {
                  MemberAttributes attr = (MemberAttributes)list.get(i);
                  RemoteMemberInfo memInfo = MemberManager.theOne().findOrCreate(attr.identity());

                  try {
                     HostID hostID = memInfo.getAttributes().identity();
                     oos.setReplacer(new MulticastReplacer(hostID));
                     MemberAttributes remoteMemberAttributes = memInfo.getAttributes();
                     oos.writeObjectWL(remoteMemberAttributes);
                     ArrayList remoteOffers = memInfo.getMemberServices(multicastSessionId).getAllOffers();
                     long remoteServerSeqNum = memInfo.findOrCreateReceiver(multicastSessionId, true).getCurrentSeqNum();
                     StateDumpMessage message = new StateDumpMessage(remoteOffers, multicastSessionId, remoteServerSeqNum);
                     long startupTime = 3L * ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getServerStartupTime();
                     if (startupTime == 0L) {
                        startupTime = 3600000L;
                     }

                     if (AttributeManager.theOne().getLocalAttributes().joinTime() - remoteMemberAttributes.joinTime() < startupTime || (long)remoteOffers.size() != remoteServerSeqNum) {
                        message = null;
                     }

                     if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
                        if (message != null) {
                           ClusterAnnouncementsDebugLogger.debug("Sending offers of size " + remoteOffers.size() + " of " + hostID + " and seq num " + message.currentSeqNum + " for '" + multicastSessionId + "'");
                        } else {
                           ClusterAnnouncementsDebugLogger.debug("Not sending statedump on behalf  of other server because other server has not been up long enough  for data to be guaranteed consistent.");
                        }
                     }

                     oos.writeObject(message);
                  } finally {
                     MemberManager.theOne().done(memInfo);
                  }
               }

               oos.setReplacer(new MulticastReplacer(LocalServerIdentity.getIdentity()));
               oos.writeObject(AttributeManager.theOne().getLocalAttributes());
               oos.writeObject(PartitionAwareSenderManager.theOne().findOrCreateAnnouncementManager(multicastSessionId).createRecoverMessage());
               oos.flush();
               res.setContentType("application/unknown");
               out = res.getOutputStream();
               res.setContentLength(baos.size());
               baos.writeTo(out);
               out.flush();
            } finally {
               try {
                  if (baos != null) {
                     baos.close();
                  }
               } catch (IOException var46) {
               }

               try {
                  if (oos != null) {
                     oos.close();
                  }
               } catch (IOException var45) {
               }

               try {
                  if (out != null) {
                     out.close();
                  }
               } catch (IOException var44) {
               }

            }

         }
      }
   }

   private String getServerHashInvalidMessage(String serverName) {
      return "The server hash received from '" + serverName + "' is invalid. The request was rejected by " + ClusterService.getClusterServiceInternal().getLocalServerDetails();
   }

   private String getUnsecureMulticastMessage(String serverName) {
      return "An unsecure statedump request was sent by '" + serverName + "'. Statedump requests must be encrypted when encrypted multicast option is turned on. The request was rejected by " + ClusterService.getClusterServiceInternal().getLocalServerDetails();
   }
}
