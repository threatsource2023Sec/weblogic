package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.spi.HostID;
import weblogic.utils.Debug;
import weblogic.utils.io.DataIO;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class MulticastSessionDataRecoveryServlet extends HttpServlet {
   private static final boolean DEBUG = false;
   private static final int DEFAULT_BUF_SIZE = 10240;

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String serverName = req.getParameter("ServerName");
      InputStream in = req.getInputStream();
      int length = req.getContentLength();
      if (length <= 0) {
         res.setStatus(403);
      } else {
         byte[] originalHash = new byte[length];
         DataIO.readFully(req.getInputStream(), originalHash);
         if (!ClusterService.getClusterServiceInternal().checkRequest(serverName, originalHash)) {
            res.setStatus(403);
         } else if (ClusterService.getClusterServiceInternal().multicastDataEncryptionEnabled() && !req.isSecure()) {
            ClusterLogger.logEnforceSecureRequest();
            res.setStatus(403);
         } else {
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
            String lastSeqNumAsString = req.getParameter("lastSeqNum");
            String peerInfoAsString = req.getParameter("PeerInfo");
            PeerInfo info = ClusterHelper.getPeerInfo(peerInfoAsString);
            Debug.assertion(info != null, "Peer info cannot be null");
            int lastSeqNum = Integer.parseInt(lastSeqNumAsString);
            if (req.getParameter("PingOnly") != null) {
               this.executePingRequest(lastSeqNum);
            } else if (PartitionAwareSenderManager.theOne().getOnBulkUpdate()) {
               res.sendError(503, "unavailable due to bulk update");
            } else {
               UnsyncByteArrayOutputStream baos = null;
               WLObjectOutputStream oos = null;
               OutputStream out = null;

               try {
                  baos = new UnsyncByteArrayOutputStream(10240);
                  oos = UpgradeUtils.getInstance().getOutputStream(baos, (ServerChannel)null, info);
                  oos.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
                  ClusterMessageSender sender = ClusterMessagesManager.theOne().findSender(multicastSessionId);
                  GroupMessage msg = null;
                  long sendersCurrentSeqNum = -1L;
                  if (sender == null) {
                     msg = new MulticastSessionNotActiveMessage(multicastSessionId);
                  } else {
                     msg = sender.createRecoverMessage();
                     sendersCurrentSeqNum = sender.getCurrentSeqNum();
                  }

                  oos.writeObject(AttributeManager.theOne().getLocalAttributes());
                  oos.writeObject(msg);
                  oos.writeLong(sendersCurrentSeqNum);
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
                  } catch (IOException var33) {
                  }

                  try {
                     if (out != null) {
                        out.close();
                     }
                  } catch (IOException var32) {
                  }

                  try {
                     if (oos != null) {
                        oos.close();
                     }
                  } catch (IOException var31) {
                  }

               }

            }
         }
      }
   }

   private void executePingRequest(int lastSeqNum) throws ServletException {
      ClusterMessageSender sender = ClusterMessagesManager.theOne().findSender(ClusterMessagesManager.ANNOUNCEMENT_MANAGER_ID);
      if (sender.getCurrentSeqNum() != (long)lastSeqNum) {
         throw new ServletException("Incompatible sender sequence numbers. local value " + sender.getCurrentSeqNum() + " received value " + lastSeqNum);
      }
   }

   private void logMessage(String str) {
      Logger.getAnonymousLogger().log(Level.WARNING, "[MulitcastSessionDataRecoveryServlet]: " + str);
   }

   public static class MulticastSessionNotActiveMessage implements GroupMessage, Externalizable {
      private MulticastSessionId multicastSessionId;

      public MulticastSessionNotActiveMessage() {
      }

      public MulticastSessionNotActiveMessage(MulticastSessionId multicastSessionId) {
         this.multicastSessionId = multicastSessionId;
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeObject(this.multicastSessionId);
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         this.multicastSessionId = (MulticastSessionId)in.readObject();
      }

      public void execute(HostID sender) {
      }
   }
}
