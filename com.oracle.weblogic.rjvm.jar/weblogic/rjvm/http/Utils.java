package weblogic.rjvm.http;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.common.internal.VersionInfo;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMTextTextFormatter;

final class Utils extends UtilsBase {
   private static final String VERSION = (new StringBuilder(VersionInfo.theOne().getMajor())).append('.').append(VersionInfo.theOne().getMinor()).append('.').append(VersionInfo.theOne().getServicePack()).append('.').append(VersionInfo.theOne().getRollingPatch()).append('.').append(VersionInfo.theOne().hasTemporaryPatch()).toString();

   private Utils() {
   }

   static HTTPServerJVMConnection getConnectionFromID(HttpServletRequest req) throws IOException {
      String connectionID = req.getParameter("connectionID");
      return connectionID == null ? null : HTTPServerJVMConnection.findByID(connectionID);
   }

   static final long getRequestIdentifier(HttpServletRequest req) throws IOException {
      String str = req.getParameter("rand");
      if (str == null) {
         throw new IOException("random request identifier is not appeared");
      } else {
         try {
            return Long.parseLong(str);
         } catch (NumberFormatException var3) {
            throw new IOException(RJVMTextTextFormatter.getInstance().msgRandIncorrectNumber(), var3);
         }
      }
   }

   static void sendDeadResponse(HttpServletResponse rsp) throws IOException {
      rsp.setHeader("WL-Result", "DEAD");
      rsp.getOutputStream().print("DEAD");
      rsp.getOutputStream().flush();
   }

   static void sendOKResponse(HttpServletResponse rsp) throws IOException {
      rsp.setContentType("application/octet-stream");
      rsp.setHeader("WL-Result", "OK");
      rsp.setHeader("WL-Version", VERSION);
      ServletOutputStream out = rsp.getOutputStream();
      out.print("OK\n");
      out.print("AS:" + MsgAbbrevJVMConnection.ABBREV_TABLE_SIZE + '\n' + "HL" + ':' + 19 + "\n\n");
   }

   static void sendOKResponseWithAdditionalParams(HttpServletResponse rsp, int peerChannelMaxMessageSize, String partitionName, boolean upgraded) throws IOException {
      rsp.setContentType("application/octet-stream");
      rsp.setHeader("WL-Result", "OK");
      rsp.setHeader("WL-Version", VERSION);
      ServletOutputStream out = rsp.getOutputStream();
      out.print("OK\n");
      out.print("AS:" + MsgAbbrevJVMConnection.ABBREV_TABLE_SIZE + '\n' + "HL" + ':' + 19 + '\n' + "MS" + ':' + peerChannelMaxMessageSize + '\n' + "PN" + ':' + partitionName + '\n' + "UP" + ':' + upgraded + "\n\n");
   }
}
