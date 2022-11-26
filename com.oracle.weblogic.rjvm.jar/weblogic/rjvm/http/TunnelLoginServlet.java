package weblogic.rjvm.http;

import java.io.IOException;
import java.net.ProtocolException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.common.internal.VersionInfo;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.socket.Login;
import weblogic.utils.StringUtils;

public final class TunnelLoginServlet extends HttpServlet {
   public void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      String firstLine = req.getParameter("wl-login");
      if (firstLine == null) {
         this.rejectConnection(1, "No version information", rsp);
      } else {
         String[] args = StringUtils.splitCompletely(firstLine, " \t");
         String version;
         if (args.length == 1) {
            version = args[0];
         } else {
            if (args.length <= 3) {
               this.rejectConnection(1, "Malformed first line - perhaps an attempt to connect to a plaintext port using SSL or vice versa?", rsp);
               return;
            }

            version = args[3];
         }

         VersionInfo peerVersion = new VersionInfo(version);
         if (!VersionInfo.compatibleWith(peerVersion)) {
            this.rejectConnection(6, VersionInfo.rejectionReasonFor(peerVersion), rsp);
         } else {
            int peerChannelMaxMessageSize = -1;

            int abbrevTableSize;
            int headerLength;
            try {
               abbrevTableSize = Integer.parseInt(req.getParameter("AS"));
               headerLength = Integer.parseInt(req.getParameter("HL"));
               if (req.getParameter("MS") != null) {
                  peerChannelMaxMessageSize = Integer.parseInt(req.getParameter("MS"));
               }
            } catch (NumberFormatException var18) {
               this.rejectConnection(1, "Malformed first line", rsp);
               return;
            }

            abbrevTableSize = Math.min(MsgAbbrevJVMConnection.ABBREV_TABLE_SIZE, abbrevTableSize);
            String partitionUrl = req.getParameter("PU");
            String remotePName = req.getParameter("LP");
            if (remotePName == null) {
               remotePName = "DOMAIN";
            }

            boolean upgradeRequested = this.getUpgradeRequest(req);
            req.getSession();

            HTTPServerJVMConnection conn;
            try {
               conn = HTTPServerJVMConnection.acceptJVMConnection(req, abbrevTableSize, headerLength, peerChannelMaxMessageSize, rsp, partitionUrl, remotePName);
            } catch (ProtocolException var17) {
               try {
                  rsp.sendError(404);
               } catch (IOException var16) {
               }

               return;
            }

            rsp.setHeader("Conn-Id", conn.sockID);
            String localPName = conn.getLocalPartitionName();
            Utils.sendOKResponseWithAdditionalParams(rsp, peerChannelMaxMessageSize, localPName, upgradeRequested);
         }
      }
   }

   private boolean getUpgradeRequest(HttpServletRequest req) {
      String upStr = req.getParameter("UP");
      return upStr != null ? Boolean.valueOf(upStr) : false;
   }

   private void rejectConnection(int code, String reason, HttpServletResponse rsp) throws IOException {
      ServletOutputStream out = rsp.getOutputStream();
      if (code >= Login.RET_CODES.length) {
         code = 7;
      }

      out.println(Login.RET_CODES[code] + ':' + reason);
      this.log("Login rejected with code: '" + Login.RET_TEXT[code] + "', reason: '" + reason + '\'');
   }
}
