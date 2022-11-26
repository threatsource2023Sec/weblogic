package weblogic.management.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.security.AccessController;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.utils.RequestSigner;
import weblogic.security.utils.SignedRequestInfo;

public class ConnectionSigner {
   public static final String REQUEST_NONCE = "wls_nonce";
   public static final String REQUEST_TIMESTAMP = "wls_timestamp";
   public static final String REQUEST_CLIENT_SERVER_NAME = "wls_server_name";
   public static final String REQUEST_SIGNATURE = "wls_signature";
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void signConnection(URLConnection connection, AuthenticatedSubject requestor, String targetServer) {
      SecurityServiceManager.checkKernelIdentity(requestor);
      SignedRequestInfo info = RequestSigner.getInstance().signRequest(requestor, targetServer);
      connection.setRequestProperty("wls_nonce", info.getNonce());
      connection.setRequestProperty("wls_timestamp", info.getTimeStamp());
      connection.setRequestProperty("wls_server_name", mimeEncode(info.getClientServerName()));
      connection.setRequestProperty("wls_signature", info.getSignature());
   }

   public static void signConnection(URLConnection connection, AuthenticatedSubject requestor) {
      signConnection(connection, requestor, ManagementService.getRuntimeAccess(KERNEL_ID).getAdminServerName());
   }

   public static boolean authenticate(String nonce, String timeStamp, String clientServerName, String signature) {
      String targetServerName = ManagementService.getRuntimeAccess(KERNEL_ID).getServerName();
      SignedRequestInfo info = new SignedRequestInfo(signature, timeStamp, clientServerName, targetServerName, nonce);
      return RequestSigner.getInstance().verify(info, false);
   }

   public static boolean isConnectionSigned(HttpServletRequest request, boolean isRepeated) {
      try {
         String nonce = request.getHeader("wls_nonce");
         if (nonce != null) {
            String signature = request.getHeader("wls_signature");
            if (signature == null) {
               return false;
            }

            String timeStamp = request.getHeader("wls_timestamp");
            String clientServerName = mimeDecode(request.getHeader("wls_server_name"));
            String targetServerName = ManagementService.getRuntimeAccess(KERNEL_ID).getServerName();
            SignedRequestInfo info = new SignedRequestInfo(signature, timeStamp, clientServerName, targetServerName, nonce);
            return RequestSigner.getInstance().verify(info, isRepeated);
         }
      } catch (Exception var8) {
      }

      return false;
   }

   private static String mimeDecode(String val) {
      String result = null;

      try {
         result = MimeUtility.decodeText(val);
      } catch (UnsupportedEncodingException var3) {
         result = val;
      }

      return result;
   }

   private static String mimeEncode(String orig) {
      String result = null;

      try {
         result = MimeUtility.encodeText(orig, "UTF-8", (String)null);
      } catch (UnsupportedEncodingException var3) {
         result = orig;
      }

      return result;
   }
}
