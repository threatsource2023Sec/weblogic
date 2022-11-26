package weblogic.socket;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import weblogic.common.internal.VersionInfo;
import weblogic.utils.StringUtils;

public final class Login {
   public static final int RET_OK = 0;
   public static final int RET_LOGIN_FAIL = 1;
   public static final int RET_NO_SERVICE = 2;
   public static final int RET_SERVICE_UNAVAILABLE = 3;
   public static final int RET_LICENSE_UNAVAILABLE = 4;
   public static final int RET_NO_RESOURCE = 5;
   public static final int RET_WRONG_VERSION = 6;
   public static final int RET_CATASTROPHE = 7;
   public static final int RET_NO_COMMAND = 8;
   public static final byte[] UNAVAIL_RESPONSE = "HTTP/1.0 403 Forbidden\r\nWL-Result: UNAVAIL\r\nContent-Type: text/html\r\n\r\n<TITLE>403 Forbidden</TITLE>The Server is not able to service this request: <b>".getBytes();
   public static final byte[] UNAVAIL_END = "</b>".getBytes();
   public static final String[] RET_CODES = new String[]{"HELO", "LGIN", "SERV", "UNAV", "LICN", "RESC", "VERS", "CATA", "CMND"};
   public static final String[] RET_TEXT = new String[]{"Success", "Failed", "No such service", "Service unavailable", "No license", "No resource", "Incompatible version", "Other problem -- see log file", "No such command"};

   public static int getMajorVersion(String reply) {
      String[] replies = StringUtils.split(reply, ':');
      return replies.length > 1 ? Integer.parseInt(replies[1].substring(0, 0)) : 6;
   }

   public static int getMinorVersion(String reply) {
      String[] replies = StringUtils.split(reply, ':');
      return replies.length > 1 ? Integer.parseInt(replies[1].substring(2, 2)) : 1;
   }

   public static String getVersionString(String reply) {
      if (reply == null) {
         return null;
      } else {
         int startIdx = reply.indexOf(58);
         return startIdx >= 0 && startIdx != reply.length() - 1 ? reply.substring(startIdx + 1) : null;
      }
   }

   public static String checkLoginSuccess(String reply) throws UnrecoverableConnectException {
      if (reply == null) {
         return "Empty server reply";
      } else {
         String[] replies = StringUtils.split(reply, ':');
         if (replies[0].equalsIgnoreCase(RET_CODES[0])) {
            return null;
         } else {
            for(int i = 0; i < RET_CODES.length; ++i) {
               if (RET_CODES[i].equalsIgnoreCase(replies[0])) {
                  if (i != 2 && i != 4 && i != 6 && i != 7) {
                     return "Login failed: " + RET_TEXT[i] + ": " + replies[1];
                  }

                  throw new UnrecoverableConnectException("Login failed: '" + RET_TEXT[i] + ": " + replies[1]);
               }
            }

            throw new UnrecoverableConnectException("Login failed for an unknown reason: " + reply);
         }
      }
   }

   public static void connectReply(Socket sock, int reason, String reasonString) throws IOException {
      DataOutputStream dOut = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
      if (reason >= RET_CODES.length) {
         reason = 7;
      }

      dOut.writeBytes(RET_CODES[reason] + ":" + reasonString + "\n");
      dOut.flush();
   }

   public static void connectReplyOK(Socket sock, byte[] connectParams, VersionInfo versionInfo, boolean needToTalkFive) throws IOException {
      writeConnectReplyOK(sock.getOutputStream(), connectParams, versionInfo, needToTalkFive);
   }

   private static void writeConnectReplyOK(OutputStream os, byte[] connectParams, VersionInfo versionInfo, boolean needToTalkFive) throws IOException {
      DataOutputStream dOut = new DataOutputStream(new BufferedOutputStream(os));
      dOut.writeBytes(RET_CODES[0]);
      dOut.writeBytes(":");
      dOut.writeBytes(Integer.toString(versionInfo.getMajor()));
      dOut.writeBytes(".");
      dOut.writeBytes(Integer.toString(versionInfo.getMinor()));
      dOut.writeBytes(".");
      dOut.writeBytes(Integer.toString(versionInfo.getServicePack()));
      dOut.writeBytes(".");
      dOut.writeBytes(Integer.toString(versionInfo.getRollingPatch()));
      dOut.writeBytes(".");
      if (needToTalkFive) {
         dOut.writeBytes(Integer.toString(versionInfo.getPatchUpdate()));
         dOut.writeBytes(".");
      }

      dOut.writeBytes(versionInfo.hasTemporaryPatch() ? "true" : "false");
      dOut.writeBytes("\n");
      dOut.write(connectParams);
      dOut.flush();
   }

   public static final void rejectConnection(Socket socket, int err, String msg) {
      try {
         boolean isHttpClient = false;
         InputStream i = socket.getInputStream();
         int a = i.read();
         int b = i.read();
         int c = i.read();
         int d = i.read();
         if (a == 71 && b == 69 && c == 84 || a == 80 && b == 79 && c == 83 && d == 84) {
            isHttpClient = true;
         }

         rejectConnection(socket, err, msg, isHttpClient);
      } catch (IOException var9) {
      }

   }

   public static final void rejectConnection(Socket socket, int err, String msg, boolean isHttpClient) {
      try {
         if (isHttpClient) {
            writeRejectConnection(socket.getOutputStream(), msg);
         } else {
            connectReply(socket, err, msg);
         }
      } catch (IOException var5) {
      }

   }

   private static void writeRejectConnection(OutputStream os, String msg) throws IOException {
      DataOutputStream dOut = new DataOutputStream(new BufferedOutputStream(os));
      dOut.write(UNAVAIL_RESPONSE);
      dOut.writeBytes(msg);
      dOut.write(UNAVAIL_END);
      dOut.flush();
   }
}
