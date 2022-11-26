package weblogic.management.servlet;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.Kernel;
import weblogic.ldap.EmbeddedLDAP;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.internal.DefaultJMXPolicyManager;
import weblogic.management.utils.ConnectionSigner;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AdminResource;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.servlet.security.Utils;
import weblogic.utils.io.StreamUtils;
import weblogic.xml.registry.XMLRegistryDir;
import weblogic.xml.registry.XMLRegistryException;

public class FileDistributionServlet extends HttpServlet {
   private static final String NMSERVERPING = "NMSERVERPING";
   public static final String SERVER_NAME = "server_name";
   private static final long serialVersionUID = -8473442547994280015L;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugFileDistributionServlet");
   private AuthorizationManager am = null;
   private PrincipalAuthenticator pa = null;
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private AuthenticatedSubject authenticateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
      AuthenticatedSubject result = null;
      String nonceHeader = request.getHeader("wls_nonce");
      String userName;
      String password;
      if (nonceHeader != null) {
         userName = request.getHeader("wls_signature");
         if (userName == null) {
            ManagementLogger.logErrorFDSMissingCredentials();
         }

         password = request.getHeader("wls_timestamp");
         String clientServerName = mimeDecode(request.getHeader("wls_server_name"));
         if (!ConnectionSigner.authenticate(nonceHeader, password, clientServerName, userName)) {
            ManagementLogger.logErrorFDSAuthenticationFailedDueToDomainWideSecretMismatch(nonceHeader, userName);
            response.sendError(401);
            return null;
         } else {
            return KERNEL_ID;
         }
      } else {
         userName = request.getHeader("wl_request_type");
         if (!"wl_init_replica_request".equals(userName) && !"wl_xml_entity_request".equals(userName)) {
            userName = request.getHeader("username");
            password = request.getHeader("password");
            if (userName != null && password != null) {
               try {
                  result = this.pa.authenticate(new SimpleCallbackHandler(userName, password.toCharArray()));
                  return result;
               } catch (LoginException var8) {
                  ManagementLogger.logErrorFDSAuthenticationFailed(userName);
                  response.sendError(401);
                  return null;
               }
            } else {
               if (request.getHeader("NMSERVERPING") == null) {
                  ManagementLogger.logErrorFDSMissingCredentials();
               }

               response.sendError(401);
               return null;
            }
         } else {
            ManagementLogger.logLDAPAndXMLRequireSignedConnection(userName);
            response.sendError(401);
            return null;
         }
      }
   }

   public String getServletInfo() {
      return "Management files distribution servlet";
   }

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      if (KERNEL_ID == null) {
         throw new ServletException("Security Services Unavailable");
      } else {
         String realmName = "weblogicDEFAULT";
         this.pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(KERNEL_ID, realmName, ServiceType.AUTHENTICATION);
         this.am = (AuthorizationManager)SecurityServiceManager.getSecurityService(KERNEL_ID, SecurityServiceManager.getAdministrativeRealmName(), ServiceType.AUTHORIZE);
         if (this.pa != null && this.am != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("FileDistributionServlet initialized");
            }

         } else {
            throw new ServletException("Security Services Unavailable");
         }
      }
   }

   public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      AuthenticatedSubject user = this.authenticateRequest(req, res);
      if (user != null) {
         final HttpServletRequest thereq = req;
         final HttpServletResponse theres = res;

         try {
            SecurityServiceManager.runAs(KERNEL_ID, user, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  FileDistributionServlet.this.internalDoPost(thereq, theres);
                  return null;
               }
            });
         } catch (PrivilegedActionException var7) {
            ManagementLogger.logErrorFDSUnauthorizedUploadAttempt(user.getName());
            res.sendError(401);
         }

      }
   }

   private void internalDoPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
   }

   public void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("---- >doGet top of method: incoming request: " + req.getHeader("wl_request_type"));
      }

      AuthenticatedSubject user = this.authenticateRequest(req, res);
      if (user != null) {
         final String requestType = req.getHeader("wl_request_type");
         if (user != KERNEL_ID) {
            AdminResource fileDownloadResource = new AdminResource("FileDownload", (String)null, requestType);
            if (!this.am.isAccessAllowed(user, fileDownloadResource, new ResourceIDDContextWrapper(true))) {
               ManagementLogger.logErrorFDSUnauthorizedDownloadAttempt(user.getName(), requestType);
               res.sendError(401);
               return;
            }
         }

         try {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("---- >doGet incoming request: " + requestType);
            }

            if (requestType.equals("wl_xml_entity_request")) {
               if (user != KERNEL_ID) {
                  ManagementLogger.logErrorFDSUnauthorizedDownloadAttempt(user.getName(), requestType);
                  res.sendError(401);
                  return;
               }

               this.doGetXMLEntityRequest(req, res);
            } else if (!requestType.equals("wl_init_replica_request") && !requestType.equals("wl_managed_server_independence_request")) {
               res.addHeader("ErrorMsg", "Bad request type");
               String htmlEncodedRequestType = Utils.encodeXSS(requestType);
               res.sendError(400, "Bad request type: " + htmlEncodedRequestType);
               ManagementLogger.logBadRequestInFileDistributionServlet(requestType);
            } else {
               try {
                  SecurityServiceManager.runAs(KERNEL_ID, user, new PrivilegedExceptionAction() {
                     public Object run() throws IOException {
                        if (requestType.equals("wl_init_replica_request")) {
                           FileDistributionServlet.this.doGetInitReplicaRequest(req, res);
                        } else if (requestType.equals("wl_managed_server_independence_request")) {
                           FileDistributionServlet.this.doGetMSIRequest(req, res);
                        }

                        return null;
                     }
                  });
               } catch (PrivilegedActionException var8) {
                  Exception e = var8.getException();
                  throw e;
               }
            }
         } catch (Exception var9) {
            if (!Kernel.isInitialized()) {
               throw new AssertionError("kernel not initialized");
            }

            ManagementLogger.logErrorInFileDistributionServlet(requestType, var9);
         }

      }
   }

   private void returnInputStream(InputStream in, OutputStream out) throws IOException {
      StreamUtils.writeTo(in, out);
   }

   private void returnFile(String path, HttpServletResponse res, boolean includePath, FileNotFoundHandler fileNotFoundHandler) throws IOException {
      File file = new File(path);
      if (!file.exists()) {
         String msg = fileNotFoundHandler.purpose() + " file not found at configured location";
         res.addHeader("ErrorMsg", msg);
         res.sendError(500, msg + ": " + Utils.encodeXSS(file.toString()));
         if (Kernel.isInitialized()) {
            fileNotFoundHandler.log(file.toString());
         }

      } else {
         DataOutputStream out = new DataOutputStream(res.getOutputStream());

         try {
            if (includePath) {
               out.writeUTF(Utils.encodeXSS(path));
            }

            InputStream in = new FileInputStream(file);

            try {
               this.returnInputStream(in, out);
            } finally {
               in.close();
            }
         } finally {
            out.close();
         }

      }
   }

   private void doGetXMLEntityRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
      String registryName = req.getHeader("xml-registry-name");
      String entityPath = req.getHeader("xml-entity-path");
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("wl_xml_entity_request: registryName = " + registryName + ", entityPath = " + entityPath);
      }

      String msg;
      if (registryName != null && registryName.length() != 0) {
         if (entityPath != null && entityPath.length() != 0) {
            XMLRegistryDir entityDir = new XMLRegistryDir(registryName);
            InputStream in = null;
            BufferedOutputStream out = new BufferedOutputStream(res.getOutputStream());

            label103: {
               try {
                  try {
                     in = entityDir.getEntity(entityPath);
                     StreamUtils.writeTo(in, out);
                     break label103;
                  } catch (XMLRegistryException var13) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Exception in FileDistributionServlet", var13);
                     }
                  }

                  String msg = var13.getMessage();
                  res.addHeader("ErrorMsg", msg);
                  res.sendError(500, msg);
               } finally {
                  if (in != null) {
                     in.close();
                  }

                  out.close();
               }

               return;
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("entity written to servlet output stream");
            }

         } else {
            msg = "Entity path not specified";
            res.addHeader("ErrorMsg", msg);
            res.sendError(500, msg);
         }
      } else {
         msg = "Registry Name not specified";
         res.addHeader("ErrorMsg", msg);
         res.sendError(500, msg);
      }
   }

   private void doGetInitReplicaRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
      String serverName = mimeDecode(req.getHeader("init-replica_server-name"));
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("wl_init_replica_request: serverName = " + serverName);
      }

      String url;
      if (serverName == null) {
         url = "Server Name not specified";
         ManagementLogger.logErrorProcessingInitReplicaRequest(url);
         res.addHeader("ErrorMsg", url);
         res.sendError(500, url);
      } else {
         url = req.getHeader("init-replica_server-url");
         String validate = req.getHeader("init-replica-validate");
         EmbeddedLDAP embeddedLDAP = EmbeddedLDAP.getEmbeddedLDAP();
         String replicaFileName;
         if (embeddedLDAP == null) {
            replicaFileName = "Embedded LDAP not available";
            ManagementLogger.logErrorProcessingInitReplicaRequest(replicaFileName);
            res.addHeader("ErrorMsg", replicaFileName);
            res.sendError(500, replicaFileName);
         } else {
            String msg;
            try {
               if (validate != null && embeddedLDAP.isValidReplica(serverName, url)) {
                  BufferedOutputStream out = new BufferedOutputStream(res.getOutputStream());
                  out.close();
                  return;
               }
            } catch (Exception var12) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Exception in FileDistributionServlet", var12);
               }

               msg = "" + var12.getMessage();
               ManagementLogger.logErrorProcessingInitReplicaRequest(msg);
               res.addHeader("ErrorMsg", msg);
               res.sendError(500, msg);
               return;
            }

            try {
               DefaultJMXPolicyManager.init();
            } catch (Exception var11) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Exception in FileDistributionServlet while trying to initialize default JMX policies", var11);
               }

               msg = "" + var11.getMessage();
               ManagementLogger.logFailedToInitJMXPolicies(msg);
            }

            replicaFileName = null;

            try {
               replicaFileName = embeddedLDAP.initReplicaForNewServer(serverName, url);
               if (replicaFileName == null) {
                  msg = "Initial replica not available";
                  ManagementLogger.logErrorProcessingInitReplicaRequest(msg);
                  res.addHeader("ErrorMsg", msg);
                  res.sendError(500, msg);
                  return;
               }
            } catch (Exception var10) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Exception in FileDistributionServlet", var10);
               }

               String msg = "" + var10.getMessage();
               ManagementLogger.logErrorProcessingInitReplicaRequest(msg);
               res.addHeader("ErrorMsg", msg);
               res.sendError(500, msg);
               return;
            }

            this.returnFile(replicaFileName, res, false, new FileNotFoundHandler() {
               public void log(String path) {
                  ManagementLogger.logFileNotFoundProcessingInitReplicaRequest(path);
               }

               public String purpose() {
                  return "Embedded LDAP initial replica";
               }
            });
         }
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

   private void doGetMSIRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
      String fileName = req.getHeader("wl_managed_server_independence_request_filename");
      String sep = File.separator;
      String pathName = DomainDir.getRootDir() + sep + fileName;
      File file = new File(pathName);
      if (!file.exists()) {
         String msg = file.getAbsolutePath() + " does not exist in AdminServer";
         res.addHeader("ErrorMsg", msg);
         res.sendError(404, msg);
      } else {
         File binDirFile = new File(DomainDir.getBinDir());
         if (!file.getCanonicalPath().contains(binDirFile.getCanonicalPath())) {
            String msg = file.getAbsolutePath() + " is not contained in domain bin directory on AdminServer";
            res.addHeader("ErrorMsg", msg);
            res.sendError(401, msg);
         } else {
            try {
               InputStream in = null;

               try {
                  res.setContentType("text/plain");
                  res.setStatus(200);
                  if (file.isDirectory()) {
                     File[] fs = file.listFiles();
                     ObjectOutputStream os = new ObjectOutputStream(res.getOutputStream());
                     os.writeObject(fs);
                  } else {
                     in = new FileInputStream(file);
                     this.returnInputStream(in, res.getOutputStream());
                  }
               } finally {
                  if (in != null) {
                     in.close();
                  }

               }
            } catch (IOException var15) {
               this.log("Interal I/0 Exception on AdminServer getting resource " + (fileName == null ? "null" : fileName), var15);
               String msg = "I/O Exception getting resource: " + var15.getMessage();
               res.addHeader("ErrorMsg", msg);
               res.sendError(500, msg);
            }

         }
      }
   }

   private interface FileNotFoundHandler {
      void log(String var1);

      String purpose();
   }
}
