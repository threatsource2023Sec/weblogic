package weblogic.corba.iiop.http;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.Enumeration;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.common.internal.VersionInfo;
import weblogic.protocol.LocalServerIdentity;
import weblogic.socket.Login;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.StringUtils;

public final class TunnelLoginServlet extends HttpServlet {
   private static final DebugCategory debug = Debug.getCategory("weblogic.iiop.http.tunnelLogin");

   public void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      String firstLine = Utils.getQueryStringParameter(req, "wl-login");
      if (firstLine == null) {
         this.rejectConnection(1, "No version information", rsp);
      } else {
         if (debug.isEnabled()) {
            Enumeration e = req.getHeaderNames();

            while(e.hasMoreElements()) {
               System.out.println("<TunnelLogin>: " + req.getHeader((String)e.nextElement()));
            }
         }

         String[] args = StringUtils.splitCompletely(firstLine, " \t");
         String version = null;
         if (args.length == 1) {
            version = args[0];
         } else {
            if (args.length <= 3) {
               this.rejectConnection(1, "Malformed first line - perhaps an attempt to onnect to a plaintext port using SSL or vice versa?", rsp);
               return;
            }

            version = args[3];
         }

         VersionInfo peerVersion = new VersionInfo(version);
         if (!VersionInfo.compatibleWith(peerVersion)) {
            this.rejectConnection(6, VersionInfo.rejectionReasonFor(peerVersion), rsp);
         } else {
            int headerLength = false;

            try {
               int headerLength = Integer.parseInt(Utils.getQueryStringParameter(req, "HL"));
            } catch (NumberFormatException var11) {
               this.rejectConnection(1, "Malformed first line", rsp);
            }

            String newID = null;

            try {
               newID = ServerConnection.acceptConnection(req);
            } catch (ProtocolException var10) {
               this.rejectConnection(1, StackTraceUtils.throwable2StackTrace(var10), rsp);
               return;
            }

            Utils.addTunnelCookie(req, rsp);
            rsp.setHeader("Conn-Id", newID);
            rsp.setHeader("WL-Dest", Integer.toString(LocalServerIdentity.getIdentity().hashCode()));
            Utils.addClusterList(req, rsp);
            Utils.sendOKResponse(rsp);
         }
      }
   }

   private void rejectConnection(int code, String reason, HttpServletResponse rsp) throws IOException {
      ServletOutputStream out = rsp.getOutputStream();
      if (code >= Login.RET_CODES.length) {
         code = 7;
      }

      out.println(Login.RET_CODES[code] + ":" + reason);
      this.log("Login rejected with code: '" + Login.RET_TEXT[code] + "', reason: " + reason + "'");
   }
}
