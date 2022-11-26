package weblogic.management.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.mail.internet.MimeUtility;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.management.internal.BootStrapStruct;
import weblogic.management.internal.ConfigLogger;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerChannelManager;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.ServerResource;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.utils.ResourceIDDContextWrapper;

public final class BootstrapServlet extends HttpServlet implements PrivilegedExceptionAction {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugBootstrapServlet");
   private AuthorizationManager authorizer;
   private PrincipalAuthenticator authenticator;
   private AuthenticatedSubject kernelId;
   private ServletConfig config = null;

   public String getServletInfo() {
      return "Managed server bootstrap servlet";
   }

   public void init(ServletConfig config) throws ServletException {
      this.config = config;
      this.kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      if (this.kernelId == null) {
         throw new ServletException("Security Services Unavailable");
      } else {
         String realmName = "weblogicDEFAULT";
         this.authenticator = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(this.kernelId, realmName, ServiceType.AUTHENTICATION);
         this.authorizer = (AuthorizationManager)SecurityServiceManager.getSecurityService(this.kernelId, SecurityServiceManager.getAdministrativeRealmName(), ServiceType.AUTHORIZE);
         if (this.authenticator != null && this.authorizer != null) {
            try {
               SecurityServiceManager.runAs(this.kernelId, this.kernelId, this);
            } catch (PrivilegedActionException var4) {
               throw (ServletException)var4.getException();
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("BootstrapServlet initialized");
            }

         } else {
            throw new ServletException("Security Services Unavailable");
         }
      }
   }

   public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      this.doGet(req, res);
   }

   public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("BootstrapServlet invoked");
      }

      this.processGet(req, res);
   }

   public void processGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
      try {
         SecurityServiceManager.runAs(this.kernelId, this.kernelId, new PrivilegedExceptionAction() {
            public Object run() throws IOException {
               WLObjectOutputStream out = null;
               OutputStream ros = null;
               PeerInfo thePeersVersionInfo = BootstrapServlet.this.checkClientVersion(req, res);
               if (thePeersVersionInfo == null) {
                  return null;
               } else {
                  String userName = BootstrapServlet.mimeDecode(req.getHeader("username"));
                  String password = BootstrapServlet.mimeDecode(req.getHeader("password"));
                  String iddHdr = req.getHeader("idd");
                  String idd = iddHdr == null ? null : BootstrapServlet.mimeDecode(iddHdr);
                  String serverName = BootstrapServlet.mimeDecode(req.getHeader("servername"));
                  Loggable lx;
                  if (serverName.equals(ManagementService.getRuntimeAccess(BootstrapServlet.this.kernelId).getServerName())) {
                     lx = ConfigLogger.logServerNameSameAsAdminLoggable(serverName);
                     lx.log();
                     res.addHeader("MatchMsg", lx.getMessageText());
                     res.sendError(404);
                     return null;
                  } else if (ManagementService.getRuntimeAccess(BootstrapServlet.this.kernelId).getDomain().lookupServer(serverName) == null) {
                     lx = ConfigLogger.logServerNameDoesNotExistLoggable(serverName);
                     lx.log();
                     res.addHeader("UnkSvrMsg", lx.getMessageText());
                     res.sendError(404);
                     return null;
                  } else {
                     ByteArrayOutputStream bos = new ByteArrayOutputStream();
                     out = new WLObjectOutputStream(bos);
                     out.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());

                     try {
                        String dv;
                        try {
                           AuthenticatedSubject user;
                           if (userName == null || password == null) {
                              ConfigLogger.logBootstrapMissingCredentials(serverName);
                              res.sendError(401);
                              user = null;
                              return user;
                           }

                           user = null;

                           try {
                              if (idd == null) {
                                 user = BootstrapServlet.this.authenticator.authenticate(new SimpleCallbackHandler(userName, password.toCharArray()));
                              } else {
                                 user = BootstrapServlet.this.authenticator.authenticate(new SimpleCallbackHandler(userName, idd, password.toCharArray()));
                              }
                           } catch (LoginException var17) {
                              ConfigLogger.logBootstrapInvalidCredentials(serverName, userName);
                              res.sendError(401);
                              dv = null;
                              return dv;
                           }

                           ServerResource resource = new ServerResource((String)null, serverName, "boot");
                           if (!BootstrapServlet.this.authorizer.isAccessAllowed(user, resource, new ResourceIDDContextWrapper(true))) {
                              ConfigLogger.logBootstrapUnauthorizedUser(serverName, userName);
                              res.sendError(401);
                              dv = null;
                              return dv;
                           }

                           dv = ManagementService.getRuntimeAccess(BootstrapServlet.this.kernelId).getDomain().getDomainVersion();
                           if (dv != null) {
                              res.addHeader("DomainVersion", dv);
                           }

                           BootstrapServlet.this.writeStructToStream(out);
                           out.flush();
                           res.setContentLength(bos.size());
                           ros = res.getOutputStream();
                           bos.writeTo(ros);
                           ros.flush();
                           ConfigLogger.logManagedServerConfigWritten(serverName);
                        } catch (Exception var18) {
                           Loggable l = ConfigLogger.logBootStrapExceptionLoggable(var18);
                           l.log();
                           res.addHeader("ErrorMsg", l.getMessageText());
                           res.sendError(500, l.getMessage());
                           dv = null;
                           return dv;
                        }
                     } finally {
                        if (out != null) {
                           out.close();
                        }

                        if (ros != null) {
                           ros.close();
                        }

                     }

                     return null;
                  }
               }
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e = var5.getException();
         if (e == null) {
            e = var5;
         }

         ConfigLogger.logBootStrapException((Exception)e);
         if (e instanceof IOException) {
            throw (IOException)e;
         } else {
            throw new ServletException((Throwable)e);
         }
      }
   }

   public Object run() throws ServletException {
      super.init(this.config);
      return null;
   }

   private void writeStructToStream(ObjectOutputStream oout) throws RemoteException, IOException {
      String adminServerName = ManagementService.getRuntimeAccess(this.kernelId).getAdminServerName();
      BootStrapStruct bootStrap = new BootStrapStruct(adminServerName);
      oout.writeObject(bootStrap);
   }

   private PeerInfo checkClientVersion(HttpServletRequest req, HttpServletResponse res) throws IOException {
      String strVersion = req.getHeader("Version");
      String serverName = req.getHeader("servername");
      if (strVersion != null && strVersion.length() != 0) {
         PeerInfo clientPeerVersionInfo = PeerInfo.getPeerInfo(strVersion);
         PeerInfo adminPeerVersionInfo = PeerInfo.getPeerInfo();
         if (clientPeerVersionInfo != null && adminPeerVersionInfo != null && adminPeerVersionInfo.getMajor() <= clientPeerVersionInfo.getMajor()) {
            return clientPeerVersionInfo;
         } else {
            String adminVersion = adminPeerVersionInfo == null ? "null" : adminPeerVersionInfo.getVersionAsString();
            Loggable l = ConfigLogger.logInvalidReleaseLevelLoggable(serverName, strVersion, adminVersion);
            l.log();
            res.addHeader("ErrorMsg", l.getMessageText());
            res.sendError(409);
            return null;
         }
      } else {
         Loggable l = ConfigLogger.logUnknownReleaseLevelLoggable();
         l.log();
         res.addHeader("ErrorMsg", l.getMessageText());
         res.sendError(409);
         return null;
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
}
