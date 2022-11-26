package weblogic.corba.iiop.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.common.internal.VersionInfo;
import weblogic.iiop.messages.MessageHeader;
import weblogic.protocol.AsyncOutgoingMessage;
import weblogic.protocol.LocalServerIdentity;
import weblogic.utils.io.Chunk;

final class Utils {
   private static final String CONTENT_LENGTH = "Content-Length";
   private static Cookie tunnelCookie = null;

   private Utils() {
   }

   static ServerConnection getConnectionFromID(HttpServletRequest req) throws IOException {
      String connectionID = getQueryStringParameter(req, "connectionID");
      return connectionID == null ? null : TunneledConnectionManager.findByID(connectionID);
   }

   static void sendDeadResponse(HttpServletResponse rsp) throws IOException {
      rsp.setHeader("WL-Result", "DEAD");
      rsp.getOutputStream().print("DEAD");
      rsp.getOutputStream().flush();
   }

   static void sendOKResponse(HttpServletResponse rsp) throws IOException {
      rsp.setHeader("WL-Result", "OK");
      rsp.setHeader("WL-Version", VersionInfo.theOne().getMajor() + "." + VersionInfo.theOne().getMinor() + "." + VersionInfo.theOne().getServicePack() + "." + VersionInfo.theOne().getRollingPatch() + "." + VersionInfo.theOne().hasTemporaryPatch());
      ServletOutputStream out = rsp.getOutputStream();
      out.print("OK\n");
      out.print("HL:12\n\n");
   }

   static void sendResponse(HttpServletResponse rsp, AsyncOutgoingMessage msg) throws IOException {
      int len = msg.getLength();
      rsp.setHeader("Content-Length", Integer.toString(len));
      rsp.setHeader("WL-Result", "OK");
      rsp.setHeader("WL-Type", Integer.toString(MessageHeader.getMsgType(msg.getChunks().buf)));
      ServletOutputStream sos = rsp.getOutputStream();
      msg.writeTo(sos);
      sos.flush();
   }

   static String getQueryStringParameter(HttpServletRequest req, String parameter) {
      String qs = req.getQueryString();
      Hashtable parameters = HttpUtils.parseQueryString(qs);
      String[] results = (String[])((String[])parameters.get(parameter));
      return results == null ? null : results[0];
   }

   static boolean requestIntended(HttpServletRequest req) {
      String dest = req.getHeader("WL-Dest");
      return dest == null || Integer.parseInt(dest) == LocalServerIdentity.getIdentity().hashCode();
   }

   static void addTunnelCookie(HttpServletRequest req, HttpServletResponse res) {
      if (tunnelCookie == null) {
         StringBuffer sb = new StringBuffer(52);

         for(int i = 0; i < 52; ++i) {
            sb.append("w");
         }

         sb.append("!").append(Integer.toString(LocalServerIdentity.getIdentity().hashCode()));
         tunnelCookie = new Cookie("JSESSIONID", sb.toString());
      }

      res.addCookie(tunnelCookie);
   }

   static void addClusterList(HttpServletRequest req, HttpServletResponse res) {
      if (ClusterTunnelingSupport.getClusterTunnelingSupport() != null && ClusterTunnelingSupport.getClusterTunnelingSupport().isClusterEnabled()) {
         res.addHeader("WL-Scheme", ServletTunnelingSupport.getServletTunnelingSupport().getProtocolName(req));
         res.addHeader("WL-List", createClusterListHeader(req));
      }
   }

   private static String createClusterListHeader(HttpServletRequest req) {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = ClusterTunnelingSupport.getClusterTunnelingSupport().getRemoteMemberInfos().iterator();

      while(var2.hasNext()) {
         ClusterMemberInfo memberInfo = (ClusterMemberInfo)var2.next();
         String s = ServletTunnelingSupport.getServletTunnelingSupport().getServerEntry(req, memberInfo);
         if (s != null) {
            sb.append(s).append('|');
         }
      }

      sb.append(ServletTunnelingSupport.getServletTunnelingSupport().getServerEntry(req, ClusterTunnelingSupport.getClusterTunnelingSupport().getLocalMemberInfo()));
      return sb.toString();
   }

   public static boolean isIntendedDestination(String dest) {
      return Integer.parseInt(dest) == LocalServerIdentity.getIdentity().hashCode();
   }

   static int copyFromStreamToChunk(InputStream is, Chunk head) throws IOException {
      int totalBytesCopied = 0;
      Chunk tail = head;
      head.end = 0;
      head.next = null;

      while(true) {
         if (tail.end == Chunk.CHUNK_SIZE) {
            tail.next = Chunk.getChunk();
            tail = tail.next;
            tail.end = 0;
            tail.next = null;
         }

         int maxBytesToRead = Chunk.CHUNK_SIZE - tail.end;
         int numRead = is.read(tail.buf, tail.end, maxBytesToRead);
         if (numRead == -1) {
            is.close();
            return totalBytesCopied;
         }

         totalBytesCopied += numRead;
         tail.end += numRead;
      }
   }
}
