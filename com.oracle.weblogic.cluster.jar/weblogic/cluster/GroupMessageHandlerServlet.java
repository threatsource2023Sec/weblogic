package weblogic.cluster;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AccessController;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Hex;
import weblogic.utils.io.DataIO;
import weblogic.utils.io.UnsyncByteArrayInputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class GroupMessageHandlerServlet extends HttpServlet {
   private static final boolean DEBUG = true;
   public static final String SERVER_NAME = "server-name";
   public static final String SERVER_HASH = "server-hash";
   private static final String SERVER_URI = "/bea_wls_cluster_internal/0056FABC093BDF49C8AE091F74400598";
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String CURRENT_SERVER_HASH = encodeServerHash();
   public static final String CURRENT_SERVER_NAME;

   private static String encodeServerHash() {
      byte[] hash = ClusterService.getClusterServiceInternal().getSecureHash();
      return Hex.asHex(hash, hash.length, false);
   }

   private static Object deserialize(InputStream is, int length) throws IOException, ClassNotFoundException {
      byte[] data = new byte[length];
      DataIO.readFully(is, data);
      WLObjectInputStream wlis = new WLObjectInputStream(new UnsyncByteArrayInputStream(data));
      Object obj = wlis.readObjectWL();
      wlis.close();
      return obj;
   }

   private static byte[] serialize(Object obj) throws IOException {
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
      WLObjectOutputStream wloos = new WLObjectOutputStream(baos);
      wloos.writeObject(obj);
      wloos.flush();
      byte[] b = baos.toByteArray();
      wloos.close();
      return b;
   }

   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String serverName = request.getParameter("server-name");
      String serverHash = request.getParameter("server-hash");
      if (request.getMethod().equals("POST") && request.getContentLength() > 0 && serverName != null && serverHash != null) {
         System.out.println("Servlet received state dump request from:" + serverName + " hash:" + serverHash);
         byte[] hashBytes = Hex.fromHexString(serverHash);
         if (!ClusterService.getClusterServiceInternal().checkRequest(serverName, hashBytes)) {
            response.sendError(403);
         } else {
            HostID senderId = null;
            ClusterServices cs = ClusterService.getServices();
            if (cs == null) {
               throw new ServletException("This server is not in a cluster.");
            } else {
               Collection collection = cs.getRemoteMembers();
               Iterator inboundMsg = collection.iterator();

               while(inboundMsg.hasNext()) {
                  ClusterMemberInfo memberInfo = (ClusterMemberInfo)inboundMsg.next();
                  if (memberInfo.serverName().equals(serverName)) {
                     senderId = memberInfo.identity();
                     break;
                  }
               }

               if (senderId == null) {
                  throw new ServletException("Sender is not in the cluster view of this server " + serverName);
               } else {
                  inboundMsg = null;

                  HttpGroupMessage inboundMsg;
                  try {
                     inboundMsg = (HttpGroupMessage)deserialize(request.getInputStream(), request.getContentLength());
                  } catch (ClassNotFoundException var13) {
                     throw new ServletException("Error deserializing stream.", var13);
                  }

                  GroupMessage outboundMsg = inboundMsg.executeAndGetResponse(senderId);
                  byte[] responseBytes = serialize(outboundMsg);
                  response.setContentLength(responseBytes.length);
                  OutputStream os = response.getOutputStream();
                  os.write(responseBytes);
                  os.close();
                  response.flushBuffer();
                  System.out.println("Writing response len:" + responseBytes.length);
               }
            }
         }
      } else {
         response.sendError(403);
      }
   }

   public static void executeMessageOnRemoteOverHttp(HostID targetId, HttpGroupMessage message) throws IOException, ClassNotFoundException {
      String uri = "/bea_wls_cluster_internal/0056FABC093BDF49C8AE091F74400598?server-name=" + CURRENT_SERVER_NAME + "&" + "server-hash" + "=" + CURRENT_SERVER_HASH;
      URL url = ClusterHelper.fabricateHTTPURL(uri, targetId);
      System.out.println("Sending statedump request:" + url);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("POST");
      con.setDoOutput(true);
      byte[] data = serialize(message);
      con.setRequestProperty("Content-Length", Integer.toString(data.length));
      OutputStream os = con.getOutputStream();
      os.write(data);
      os.close();
      if (con.getResponseCode() != 200) {
         throw new IOException("Unexpected error when requesting statedump  code:" + con.getResponseCode() + " url:" + url);
      } else {
         int payload = con.getContentLength();
         if (payload > 0) {
            GroupMessage m = (GroupMessage)deserialize(con.getInputStream(), con.getContentLength());
            m.execute(targetId);
         }

      }
   }

   static {
      CURRENT_SERVER_NAME = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
   }

   public interface HttpGroupMessage extends GroupMessage {
      GroupMessage executeAndGetResponse(HostID var1);
   }
}
