package weblogic.deploy.service.datatransferhandlers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.mail.internet.MimeUtility;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DataTransferHandler;
import weblogic.deploy.service.DataTransferRequest;
import weblogic.deploy.service.MultiDataStream;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.transport.http.DeploymentServiceServlet;
import weblogic.deploy.utils.DeploymentServletConstants;
import weblogic.logging.Loggable;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.ConnectionSigner;
import weblogic.protocol.URLManager;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class HttpDataTransferHandler implements DataTransferHandler, DeploymentServletConstants {
   private static final AuthenticatedSubject KERNE_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean gotSecret = false;
   private String userName;
   private String password;
   private String idd;

   protected HttpDataTransferHandler() {
   }

   public final String getType() {
      return "HTTP";
   }

   public MultiDataStream getDataAsStream(DataTransferRequest request) throws IOException {
      DataHandlerManager.validateRequestType(request);
      HttpURLConnection connection = null;

      MultiDataStream var9;
      try {
         connection = this.createURLConnection();
         connection.setRequestProperty("deployment_request_id", Long.toString(request.getRequestId()));
         UnsyncByteArrayOutputStream bos = new UnsyncByteArrayOutputStream();
         WLObjectOutputStream oos = new WLObjectOutputStream(bos);
         oos.setReplacer(RemoteObjectReplacer.getReplacer());
         oos.writeObject(request);
         oos.flush();
         connection.setRequestProperty("Content-Length", "" + bos.size());
         connection.connect();
         OutputStream out = connection.getOutputStream();
         bos.writeTo(out);
         out.flush();
         int httpResponseCode = connection.getResponseCode();
         String type = connection.getContentType();
         if (httpResponseCode != 200 || type == null) {
            String errorMessage = connection.getHeaderField("ErrorMsg");
            if (errorMessage == null) {
               errorMessage = "HttpResponseCode: " + httpResponseCode + " type: " + type;
            }

            Loggable logger = DeploymentServiceLogger.logExceptionWhileGettingDataAsStreamLoggable(request.getRequestId(), errorMessage);
            throw new IOException(logger.getMessage());
         }

         MultipartParser multi = new MultipartParser(connection, (String)null);
         var9 = multi.getMultiDataStream();
      } catch (IOException var18) {
         if (Debug.isServiceTransportDebugEnabled()) {
            StringBuffer error = new StringBuffer();
            error.append("Error occurred while while downloading file for request '");
            error.append(request);
            error.append("'. ");
            error.append("Underlying error is: ");
            error.append(var18.toString());
            Debug.serviceHttpDebug(error.toString());
         }

         throw var18;
      } finally {
         if (connection != null) {
            try {
               connection.disconnect();
            } catch (Exception var17) {
            }
         }

      }

      return var9;
   }

   private HttpURLConnection createURLConnection() throws IOException {
      URL url = DeploymentServiceServlet.getURL();
      HttpURLConnection connection = URLManager.createAdminHttpConnection(url, true);
      connection.setRequestProperty("wl_request_type", mimeEncode("data_transfer_request"));
      if (ManagementService.isRuntimeAccessInitialized()) {
         ConnectionSigner.signConnection(connection, KERNE_ID);
      } else {
         connection.setRequestProperty("username", mimeEncode(this.getUserName()));
         connection.setRequestProperty("password", mimeEncode(this.getUserCredential()));
         String identityDomain = this.getIdentityDomain();
         if (identityDomain != null) {
            connection.setRequestProperty("idd", mimeEncode(identityDomain));
         }
      }

      connection.setRequestProperty("serverName", mimeEncode(ManagementService.getPropertyService(KERNE_ID).getServerName()));
      connection.setRequestProperty("server_version", PeerInfo.getPeerInfo().getVersionAsString());
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      return connection;
   }

   private String getUserName() {
      this.initSecret();
      return this.userName;
   }

   private String getUserCredential() {
      this.initSecret();
      return this.password;
   }

   private String getIdentityDomain() {
      this.initSecret();
      return this.idd;
   }

   private void initSecret() {
      if (!this.gotSecret) {
         synchronized(this) {
            if (this.gotSecret) {
               return;
            }

            PrivilegedAction theAction = new PrivilegedAction() {
               public Object run() {
                  HttpDataTransferHandler.this.userName = ManagementService.getPropertyService(HttpDataTransferHandler.KERNE_ID).getTimestamp1();
                  HttpDataTransferHandler.this.password = ManagementService.getPropertyService(HttpDataTransferHandler.KERNE_ID).getTimestamp2();
                  HttpDataTransferHandler.this.idd = ManagementService.getPropertyService(HttpDataTransferHandler.KERNE_ID).getIdentityDomain();
                  return null;
               }
            };
            SecurityServiceManager.runAs(KERNE_ID, KERNE_ID, theAction);
         }

         this.gotSecret = true;
      }

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
