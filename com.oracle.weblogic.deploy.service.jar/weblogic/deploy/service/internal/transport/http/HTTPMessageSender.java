package weblogic.deploy.service.internal.transport.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.MimeUtility;
import weblogic.common.internal.PeerInfo;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.internal.transport.DeploymentServiceMessage;
import weblogic.deploy.service.internal.transport.MessageReceiver;
import weblogic.deploy.service.internal.transport.MessageSender;
import weblogic.deploy.service.internal.transport.UnreachableHostException;
import weblogic.deploy.utils.DeploymentServletConstants;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.PropertyService;
import weblogic.management.utils.ConnectionSigner;
import weblogic.protocol.URLManager;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.StringUtils;

public final class HTTPMessageSender implements MessageSender, DeploymentServletConstants, DeploymentServiceConstants {
   private static final String DEPLOYMENT_APPNAME = "bea_wls_deployment_internal";
   private static final HTTPMessageSender SINGLETON = new HTTPMessageSender();
   private String userName;
   private String password;
   private String idd;
   private MessageReceiver loopbackReceiver;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private HTTPMessageSender() {
      if (isDebugEnabled()) {
         debug("Created HTTPMessageSender");
      }

   }

   private static final boolean isDebugEnabled() {
      return Debug.isServiceHttpDebugEnabled();
   }

   private static final void debug(String message) {
      Debug.serviceHttpDebug(message);
   }

   private String getUserName() {
      if (this.userName == null) {
         this.userName = ManagementService.getPropertyService(kernelId).getTimestamp1();
      }

      return this.userName;
   }

   private String getUserCredential() {
      if (this.password == null) {
         this.password = ManagementService.getPropertyService(kernelId).getTimestamp2();
      }

      return this.password;
   }

   private String getIdentityDomain() {
      if (this.idd == null) {
         this.idd = ManagementService.getPropertyService(kernelId).getIdentityDomain();
      }

      return this.idd;
   }

   private static String getAdminUrl() {
      return ManagementService.getPropertyService(kernelId).getAdminHttpUrl();
   }

   private static String[] getAllAdminUrls() {
      return ((PropertyService)LocatorUtilities.getService(PropertyService.class)).getAllAdminHttpUrls();
   }

   public static HTTPMessageSender getMessageSender() {
      return SINGLETON;
   }

   public void sendHeartbeatMessage(DeploymentServiceMessage message, String server) throws Exception {
      try {
         String serverURL = getURL(server);
         this.sendMessageToServerURL(message, serverURL, server, false, 60000);
      } catch (UnreachableHostException var4) {
         if (isDebugEnabled()) {
            debug(" UnreachableHost : " + StackTraceUtils.throwable2StackTrace(var4));
         }
      }

   }

   public void sendMessageToTargetServer(DeploymentServiceMessage message, String server) throws RemoteException {
      String serverURL = getURL(server);
      this.sendMessageToServerURL(message, serverURL, server, true, 0);
   }

   public void sendMessageToAdminServer(DeploymentServiceMessage message) throws RemoteException {
      boolean isSynchronous = true;
      String server = null;
      String serverURL = null;
      if (!isAdminServer()) {
         serverURL = getAdminUrl();
      }

      try {
         this.sendMessageToServerURL(message, serverURL, (String)server, true, 0);
      } catch (UnreachableHostException var6) {
         this.sendMessageToAdminServerViaAlternateUrls(serverURL, message, true, var6);
      }

   }

   private DeploymentServiceMessage sendMessageToAdminServerViaAlternateUrls(String serverURL, DeploymentServiceMessage message, boolean synchronous, UnreachableHostException original) throws RemoteException {
      List exceptions = new ArrayList();
      String[] serverURLs = getAllAdminUrls();
      int i = 0;
      String server = null;

      while(true) {
         String nextAdminURL;
         do {
            if (i >= serverURLs.length) {
               if (original == null) {
                  nextAdminURL = DeployerRuntimeLogger.adminUnreachable(StringUtils.join(serverURLs, ","));
                  throw new RemoteException(nextAdminURL);
               }

               if (exceptions.size() == 0) {
                  throw original;
               }

               StringBuilder sb = new StringBuilder();
               sb.append("\n [from AdminURL 0] " + original.toString());

               for(int j = 0; j < exceptions.size(); ++j) {
                  sb.append(" [from AdminURL " + (j + 1) + "] " + ((UnreachableHostException)exceptions.get(j)).toString());
               }

               throw new RemoteException(sb.toString());
            }

            nextAdminURL = serverURLs[i];
            ++i;
         } while(serverURL != null && serverURL.equals(nextAdminURL));

         try {
            if (isDebugEnabled()) {
               debug("Retrying attempt to send to admin server via alternate url '" + nextAdminURL);
            }

            return this.sendMessageToServerURL(message, nextAdminURL, (String)server, synchronous, 0);
         } catch (UnreachableHostException var12) {
            exceptions.add(var12);
         } catch (RemoteException var13) {
            String msg = DeployerRuntimeLogger.altURLFailed(nextAdminURL);
            throw new RemoteException(msg);
         }
      }
   }

   public DeploymentServiceMessage sendBlockingMessageToAdminServer(DeploymentServiceMessage message) throws RemoteException {
      boolean isSynchronous = true;
      String server = null;
      String serverURL = null;
      if (!isAdminServer()) {
         serverURL = getAdminUrl();
      }

      try {
         return this.sendMessageToServerURL(message, serverURL, (String)server, true, 0);
      } catch (UnreachableHostException var6) {
         return this.sendMessageToAdminServerViaAlternateUrls(serverURL, message, true, var6);
      }
   }

   public void setLoopbackReceiver(MessageReceiver receiver) {
      if (this.loopbackReceiver == null) {
         this.loopbackReceiver = receiver;
      }

      if (isDebugEnabled()) {
         debug("setting loopback messageReceiver to '" + receiver + "'");
      }

   }

   private DeploymentServiceMessage sendMessageToServerURL(DeploymentServiceMessage message, String serverURL, String serverName, boolean isSynchronous, int timeout) throws RemoteException {
      boolean loopback = false;
      if (isAdminServer() && (serverName == null || serverName.equals(ManagementService.getRuntimeAccess(kernelId).getServerName()))) {
         loopback = true;
         serverName = "loopback admin";
      }

      if (isDebugEnabled()) {
         debug("sending message for id '" + message.getDeploymentId() + "' to '" + serverName + "' using URL '" + serverURL + "' via http");
      }

      if (loopback) {
         try {
            if (isSynchronous) {
               return this.loopbackReceiver.receiveSynchronousMessage(message);
            } else {
               this.loopbackReceiver.receiveMessage(message);
               return null;
            }
         } catch (Exception var26) {
            String msg = DeployerRuntimeLogger.errorReceivingMessage();
            throw new RemoteException(msg, var26);
         }
      } else {
         URL url;
         try {
            url = new URL(serverURL + "/" + "bea_wls_deployment_internal" + "/DeploymentService");
         } catch (MalformedURLException var27) {
            String msg = DeployerRuntimeLogger.malformedURL(serverURL);
            throw new RemoteException(msg, var27);
         }

         int responseCode = -1;
         HttpURLConnection con = null;

         try {
            con = URLManager.createAdminHttpConnection(url, true);
            con.setRequestProperty("wl_request_type", mimeEncode("deployment_svc_msg"));
            if (ManagementService.isRuntimeAccessInitialized()) {
               if (serverName != null) {
                  ConnectionSigner.signConnection(con, kernelId, serverName);
               } else {
                  ConnectionSigner.signConnection(con, kernelId);
               }
            } else {
               con.setRequestProperty("username", mimeEncode(this.getUserName()));
               con.setRequestProperty("password", mimeEncode(this.getUserCredential()));
               String identityDomain = this.getIdentityDomain();
               if (identityDomain != null) {
                  con.setRequestProperty("idd", mimeEncode(identityDomain));
               }
            }

            con.setRequestProperty("serverName", mimeEncode(ManagementService.getPropertyService(kernelId).getServerName()));
            con.setRequestProperty("server_version", PeerInfo.getPeerInfo().getVersionAsString());
            con.setRequestProperty("isSynchronous", mimeEncode(Boolean.valueOf(isSynchronous).toString()));
            con.setRequestProperty("deployment_request_id", Long.toString(message.getDeploymentId()));
            con.setRequestProperty("Content-Type", "application/x-java-serialized-object");
            con.setDoInput(true);
            con.setDoOutput(true);
            if (timeout > 0) {
               con.setConnectTimeout(timeout);
               con.setReadTimeout(timeout);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DeploymentObjectOutputStream out = new DeploymentObjectOutputStream(bos, PeerInfo.getPeerInfo().getVersionAsString());
            out.setReplacer(RemoteObjectReplacer.getReplacer());
            out.writeObject(message);
            out.flush();
            out.close();
            con.setRequestProperty("Content-Length", "" + bos.size());
            OutputStream cos = con.getOutputStream();
            bos.writeTo(cos);
            cos.flush();
            cos.close();
            if (isSynchronous) {
               responseCode = con.getResponseCode();
               if (responseCode != 200) {
                  StringBuffer sb = new StringBuffer();
                  sb.append(DeployerRuntimeLogger.errorReadingInput());
                  sb.append(" : with response code '").append(responseCode).append("'");
                  sb.append(" : with response message '");
                  sb.append(con.getResponseMessage()).append("'");
                  if (isServerStandby(serverName)) {
                     throw new ConnectException(sb.toString());
                  }

                  if (responseCode == 503) {
                     Debug.serviceHttpDebug("HTTPMessageSender: HTTP_UNAVAILABLE error when making a DeploymentServiceMsg request to URL, sever might be in the process of shutting down : " + url.toString());
                     throw new ConnectException(sb.toString());
                  }

                  throw new RemoteException(sb.toString());
               }

               String responseServerVersion = con.getHeaderField("server_version");
               String peerVersion = responseServerVersion != null && responseServerVersion.length() != 0 ? responseServerVersion : PeerInfo.getPeerInfo().getVersionAsString();
               if (isDebugEnabled()) {
                  debug("Response Peer Version: " + peerVersion);
               }

               ObjectInputStream inputStream = new DeploymentObjectInputStream(con.getInputStream(), peerVersion);
               DeploymentServiceMessage var16 = (DeploymentServiceMessage)inputStream.readObject();
               return var16;
            }
         } catch (ConnectException var28) {
            throw new UnreachableHostException(serverName, var28);
         } catch (UnknownHostException var29) {
            throw new UnreachableHostException(serverName, var29);
         } catch (IOException var30) {
            if (var30 instanceof RemoteException) {
               throw (RemoteException)var30;
            }

            int responseCode = true;
            if (isDebugEnabled()) {
               debug("HTTPMessageSender: IOException: " + var30.toString() + " when making a DeploymentServiceMsg request to URL: " + url.toString());
               debug("Exception StackTrace: " + StackTraceUtils.throwable2StackTrace(var30));
            }

            String msg = DeployerRuntimeLogger.errorReadingInput();
            throw new RemoteException(msg, var30);
         } catch (ClassNotFoundException var31) {
            if (isDebugEnabled()) {
               debug("HTTPMessageSender: ClassNotFoundException: " + var31.toString() + " when making a DeploymentServiceMsg request to URL: " + url.toString());
            }

            throw new RemoteException(var31.toString(), var31);
         } catch (Throwable var32) {
            if (isDebugEnabled()) {
               debug("HTTPMessageSender: Throwable: " + StackTraceUtils.throwable2StackTrace(var32) + " when making a DeploymentServiceMsg request to URL: " + url.toString());
            }

            throw new RemoteException(var32.toString(), var32);
         } finally {
            if (con != null) {
               con.disconnect();
            }

         }

         if (responseCode == 404) {
            Debug.serviceHttpDebug("HTTPMessageSender: HTTP_NOT_FOUND error when making a DeploymentServiceMsg request to URL: " + url.toString());
         } else if (responseCode == 401) {
            Debug.serviceHttpDebug("HTTPMessageSender: HTTP_UNAUTHORIZED error when making a DeploymentServiceMsg request to URL: " + url.toString());
         } else if (responseCode == 500 || responseCode == 409) {
            Debug.serviceHttpDebug("HTTPMessageSender: " + (responseCode == 500 ? "HTTP_INTERNAL_ERROR" : "HTTP_CONFLICT") + "error when making a DeploymentServiceMsg request to URL: " + url.toString());
         }

         return null;
      }
   }

   private static boolean isAdminServer() {
      return ManagementService.getPropertyService(kernelId).isAdminServer();
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private static String getURL(String server) throws UnreachableHostException {
      try {
         String result = getURLManagerService().findAdministrationURL(server);
         if (result == null) {
            throw new UnreachableHostException(server, (Exception)null);
         } else {
            result = getURLManagerService().normalizeToHttpProtocol(result);
            return result;
         }
      } catch (UnknownHostException var2) {
         throw new UnreachableHostException(server, var2);
      }
   }

   private static boolean isServerStandby(String serverName) {
      if (!ManagementService.isRuntimeAccessInitialized()) {
         return false;
      } else {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         ServerMBean scfg = domain.lookupServer(serverName);
         return scfg != null && "STANDBY".equals(scfg.getStartupMode());
      }
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
