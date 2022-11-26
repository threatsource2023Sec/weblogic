package weblogic.deploy.service.internal.transport.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileLock;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.List;
import javax.mail.internet.MimeUtility;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DataTransferRequest;
import weblogic.deploy.service.MultiDataStream;
import weblogic.deploy.service.datatransferhandlers.DataHandlerManager;
import weblogic.deploy.service.datatransferhandlers.MultipartResponse;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.transport.DeploymentServiceMessage;
import weblogic.deploy.service.internal.transport.MessageReceiver;
import weblogic.deploy.utils.DeploymentServletConstants;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.utils.ConnectionSigner;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AdminResource;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.ServerResource;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.utils.FileUtils;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.jars.JarFileUtils;

public final class DeploymentServiceServlet extends HttpServlet implements PrivilegedExceptionAction, DeploymentServiceConstants, DeploymentServletConstants {
   private static final int MAXIMUM_UPLOAD_SIZE = Integer.MAX_VALUE;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static AdminResource FILEUPLOAD_RESOURCE = new AdminResource("FileUpload", (String)null, (String)null);
   private static final int CACHE_SIZE_LIMIT = getIntProperty("weblogic.deploy.DeploymentServiceServletCacheSize", 1048576);
   private AuthorizationManager authorizer;
   private PrincipalAuthenticator authenticator;
   private ServletConfig config = null;
   private String serverName = null;
   private RuntimeAccess serverConfig = null;
   private MessageReceiver loopbackReceiver = null;

   private final boolean isDebugEnabled() {
      return Debug.isServiceHttpDebugEnabled();
   }

   private final void debug(String message) {
      Debug.serviceHttpDebug("DeploymentServiceServlet:" + message);
   }

   public String getServletInfo() {
      return "DeploymentService transport servlet";
   }

   public void init(ServletConfig config) throws ServletException {
      this.config = config;
      if (kernelId == null) {
         Loggable l = DeploymentServiceLogger.logServletFailedToInitLoggable();
         l.log();
         if (this.isDebugEnabled()) {
            this.debug("DeploymentServiceServlet: init: Security Services unavailable");
         }

         throw new ServletException(l.getMessage());
      } else {
         String realmName = "weblogicDEFAULT";
         this.authenticator = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelId, "weblogicDEFAULT", ServiceType.AUTHENTICATION);
         this.authorizer = (AuthorizationManager)SecurityServiceManager.getSecurityService(kernelId, SecurityServiceManager.getAdministrativeRealmName(), ServiceType.AUTHORIZE);
         if (this.authenticator != null && this.authorizer != null) {
            try {
               SecurityServiceManager.runAs(kernelId, kernelId, this);
            } catch (PrivilegedActionException var5) {
               Loggable l = DeploymentServiceLogger.logServletInitFailedDueToPrivilegedActionViolationLoggable(var5.getException().getMessage());
               l.log();
               if (this.isDebugEnabled()) {
                  this.debug("DeploymentServiceServlet: init: Privileged action violation: " + l.getMessage());
               }

               throw (ServletException)var5.getException();
            }

            this.serverConfig = ManagementService.getRuntimeAccess(kernelId);
            this.serverName = this.serverConfig.getServerName();
            DeploymentService deploymentService = DeploymentService.getDeploymentService();
            this.loopbackReceiver = deploymentService.getMessageReceiver().getDelegate();
            if (this.isDebugEnabled()) {
               this.debug("DeploymentServiceServlet initialized");
            }

         } else {
            Loggable l = DeploymentServiceLogger.logServletFailedToInitLoggable();
            l.log();
            if (this.isDebugEnabled()) {
               this.debug("DeploymentServiceServlet: init: Security Services unavailable " + (this.authenticator == null ? "null authenticator" : "null authorizer"));
            }

            throw new ServletException(l.getMessage());
         }
      }
   }

   public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      this.setFrameOptionsDeny(res);
   }

   public void doPost(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
      this.setFrameOptionsDeny(res);
      final AuthenticatedSubject user = this.authenticateRequest(req, res);
      if (user != null) {
         String requestType = mimeDecode(req.getHeader("wl_request_type"));
         if (this.isDebugEnabled()) {
            this.debug("DeploymentServiceServlet: doPost: requestType: " + requestType);
         }

         try {
            Object var5 = SecurityServiceManager.runAs(kernelId, user, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  DeploymentServiceServlet.this.internalDoPost(req, res, user);
                  return null;
               }
            });
         } catch (PrivilegedActionException var7) {
            Loggable l = DeploymentServiceLogger.logUnautherizedRequestLoggable(requestType, user.getName());
            if (this.isDebugEnabled()) {
               this.debug("DeploymentServiceServlet: doPost: privileged action error - " + l.getMessage());
            }

            logAndSendError(res, 401, l);
         }

      }
   }

   private final void internalDoPost(HttpServletRequest req, HttpServletResponse res, AuthenticatedSubject user) throws IOException {
      String requestType = mimeDecode(req.getHeader("wl_request_type"));

      try {
         if (requestType.equals("deployment_svc_msg")) {
            this.handleDeploymentServiceMessage(req, res, user);
            return;
         }

         if (requestType.equals("data_transfer_request")) {
            this.handleDataTransferRequest(req, res, user);
            return;
         }

         if (requestType.equals("plan_upload") || requestType.equals("app_upload")) {
            this.handlePlanOrApplicationUpload(req, res, user);
            return;
         }

         if (requestType.equals("user_file_upload")) {
            this.handlePartitionUserFileUpload(req, res, user);
            return;
         }
      } catch (IOException var7) {
         Loggable l = DeploymentServiceLogger.logExceptionInServletRequestLoggable(requestType, var7.getMessage());
         logAndSendError(res, 500, l);
      }

   }

   private final void handlePlanOrApplicationUpload(HttpServletRequest req, HttpServletResponse res, AuthenticatedSubject user) throws IOException {
      String applicationId = mimeDecode(req.getHeader("wl_upload_application_name"));
      String requestType = mimeDecode(req.getHeader("wl_request_type"));
      String rgtName = mimeDecode(req.getHeader("wl_resource_group_template_name"));
      String partitionName = this.getPartitionName(req);
      String rgName = mimeDecode(req.getHeader("wl_resource_group_name"));
      if (this.isDebugEnabled()) {
         this.debug("handlePlanOrApplicationUpload");
         this.debug("rgtName:  " + rgtName);
         this.debug("partitionName:  " + partitionName);
         this.debug("rgName:  " + rgName);
      }

      if (applicationId != null && applicationId.contains("..")) {
         Loggable l = DeploymentServiceLogger.logRequestWithInvalidAppNameLoggable(requestType, applicationId);
         logAndSendError(res, 403, l);
      } else {
         String applicationName = ApplicationVersionUtils.getApplicationName(applicationId);
         String versionId = ApplicationVersionUtils.getVersionId(applicationId);
         AppDeploymentMBean appDeployment = ApplicationVersionUtils.getAppDeployment(applicationName, versionId);
         if (applicationName == null || appDeployment != null && appDeployment.isInternalApp()) {
            Loggable l = DeploymentServiceLogger.logRequestWithNoAppNameLoggable(requestType);
            logAndSendError(res, 403, l);
         } else {
            String contentType = req.getContentType();
            if (contentType != null && contentType.startsWith("multipart")) {
               boolean isDelta = false;
               String isDeltaParameter = req.getHeader("wl_upload_delta");
               if (isDeltaParameter != null && isDeltaParameter.equalsIgnoreCase("true")) {
                  isDelta = true;
               }

               boolean isPlan = requestType.equals("plan_upload");
               boolean unpackArchive = "false".equals(req.getHeader("archive"));
               if (this.isDebugEnabled()) {
                  this.debug(requestType + " request for application " + applicationName + " with archive: " + unpackArchive);
               }

               String uploadingDirName = null;
               if (versionId == null || versionId.length() == 0) {
                  uploadingDirName = this.getUploadDirName(applicationName, versionId, isDelta, isPlan, unpackArchive, rgtName, rgName, partitionName);
               }

               if (uploadingDirName == null) {
                  uploadingDirName = this.getDefaultUploadDirName(rgtName, partitionName, rgName);
                  if (uploadingDirName == null) {
                     Loggable l = DeploymentServiceLogger.logNoUploadDirectoryLoggable(requestType, applicationName);
                     logAndSendError(res, 500, l);
                     return;
                  }

                  uploadingDirName = uploadingDirName + applicationName + File.separator;
                  if (versionId != null) {
                     uploadingDirName = uploadingDirName + versionId + File.separator;
                  }
               }

               if (this.isDebugEnabled()) {
                  this.debug(" +++ Final uploadingDirName : " + uploadingDirName);
               }

               boolean error = true;
               String uploadedFileName = null;
               boolean var29 = false;

               PrintStream out;
               File absoluteFile;
               label257: {
                  label258: {
                     try {
                        var29 = true;
                        res.setContentType("text/plain");
                        File uploadedFile = null;
                        Loggable l;
                        if (req.getHeader("jspRefresh") != null && req.getHeader("jspRefresh").equals("true")) {
                           l = DeploymentServiceLogger.logNoUploadFileRequestLoggable();
                           logAndSendError(res, 403, l);
                           var29 = false;
                           break label257;
                        }

                        uploadedFile = this.doUploadFile(req, uploadingDirName);
                        if (uploadedFile == null) {
                           l = DeploymentServiceLogger.logFailedOnUploadingFileLoggable();
                           logAndSendError(res, 400, l);
                           var29 = false;
                           break label258;
                        }

                        uploadedFileName = uploadedFile.getPath();
                        error = false;
                        if (unpackArchive) {
                           if (this.isDebugEnabled()) {
                              this.debug("extracting " + uploadedFileName + " to " + uploadingDirName);
                           }

                           error = !this.extractArchive(uploadedFileName, uploadingDirName);
                           uploadedFileName = uploadingDirName;
                           var29 = false;
                        } else {
                           var29 = false;
                        }
                     } catch (IllegalArgumentException var30) {
                        sendError(res, 400, var30.getMessage());
                        throw var30;
                     } finally {
                        if (var29) {
                           if (!error) {
                              PrintStream out = new PrintStream(res.getOutputStream());
                              File absoluteFile = new File(uploadedFileName);
                              out.println(mimeEncode(absoluteFile.getAbsolutePath()));
                              out.close();
                           }

                        }
                     }

                     if (!error) {
                        PrintStream out = new PrintStream(res.getOutputStream());
                        File absoluteFile = new File(uploadedFileName);
                        out.println(mimeEncode(absoluteFile.getAbsolutePath()));
                        out.close();
                     }

                     return;
                  }

                  if (!error) {
                     out = new PrintStream(res.getOutputStream());
                     absoluteFile = new File(uploadedFileName);
                     out.println(mimeEncode(absoluteFile.getAbsolutePath()));
                     out.close();
                  }

                  return;
               }

               if (!error) {
                  out = new PrintStream(res.getOutputStream());
                  absoluteFile = new File(uploadedFileName);
                  out.println(mimeEncode(absoluteFile.getAbsolutePath()));
                  out.close();
               }

            } else {
               Loggable l = DeploymentServiceLogger.logBadContentTypeServletRequestLoggable(requestType, contentType);
               logAndSendError(res, 400, l);
            }
         }
      }
   }

   private final void handlePartitionUserFileUpload(HttpServletRequest req, HttpServletResponse res, AuthenticatedSubject user) throws IOException {
      String requestType = mimeDecode(req.getHeader("wl_request_type"));
      String uploadPath = mimeDecode(req.getHeader("wl_upload_path"));
      String overwriteFile = mimeDecode(req.getHeader("wl_overwrite_file"));
      String uploadFileName = mimeDecode(req.getHeader("wl_upload_file_name"));
      String partitionName = this.getPartitionName(req);
      if (this.isDebugEnabled()) {
         this.debug("handlePartitionUserFileUpload");
         this.debug("partitionName:  " + partitionName);
         this.debug("uploadPath:  " + uploadPath);
         this.debug("overwriteFile:  " + overwriteFile);
         this.debug("uploadFileName:  " + uploadFileName);
      }

      Loggable l;
      if (!this.validatePartitionName(req)) {
         l = DeploymentServiceLogger.logRequestWithInvalidPartitionNameLoggable(requestType, partitionName);
         logAndSendError(res, 403, l);
      } else if (uploadPath == null || !uploadPath.contains("..") && !uploadPath.startsWith("/") && !(new File(uploadPath)).isAbsolute()) {
         if (uploadPath == null) {
            l = DeploymentServiceLogger.logRequestWithNoUploadPathLoggable(requestType);
            logAndSendError(res, 403, l);
         } else {
            String contentType = req.getContentType();
            Loggable l;
            if (contentType != null && contentType.startsWith("multipart")) {
               if (uploadFileName == null || !uploadFileName.contains("..") && !uploadFileName.startsWith("/") && !(new File(uploadFileName)).isAbsolute()) {
                  l = null;

                  PartitionTableEntry pte;
                  try {
                     pte = PartitionTable.getInstance().lookupByName(partitionName);
                  } catch (IllegalArgumentException var24) {
                     Loggable l = DeploymentServiceLogger.logRequestWithInvalidPartitionNameLoggable(requestType, partitionName);
                     logAndSendError(res, 403, l);
                     return;
                  }

                  String uploadingDirName = pte.getPartitionUserRoot() + "/" + uploadPath;
                  if (!uploadingDirName.endsWith("/")) {
                     uploadingDirName = uploadingDirName + "/";
                  }

                  boolean overwrite = Boolean.parseBoolean(overwriteFile);
                  File target = new File(uploadingDirName + uploadFileName);
                  if (target.exists()) {
                     Loggable l;
                     if (!overwrite) {
                        l = DeploymentServiceLogger.logUserFileAlreadyExistsLoggable(requestType, uploadFileName);
                        logAndSendError(res, 403, l);
                        return;
                     }

                     if (!target.delete()) {
                        l = DeploymentServiceLogger.logUnableToOverwriteUserFileLoggable(requestType, uploadFileName);
                        logAndSendError(res, 403, l);
                        return;
                     }
                  }

                  boolean error = true;
                  String uploadedFileName = null;
                  boolean var23 = false;

                  try {
                     var23 = true;
                     res.setContentType("text/plain");
                     File uploadedFile = null;
                     uploadedFile = this.doUploadFile(req, uploadingDirName);
                     uploadedFileName = uploadedFile.getPath();
                     error = false;
                     var23 = false;
                  } finally {
                     if (var23) {
                        if (!error) {
                           PrintStream out = new PrintStream(res.getOutputStream());
                           File absoluteFile = new File(uploadedFileName);
                           out.println(mimeEncode(absoluteFile.getAbsolutePath()));
                           out.close();
                        }

                     }
                  }

                  if (!error) {
                     PrintStream out = new PrintStream(res.getOutputStream());
                     File absoluteFile = new File(uploadedFileName);
                     out.println(mimeEncode(absoluteFile.getAbsolutePath()));
                     out.close();
                  }

               } else {
                  l = DeploymentServiceLogger.logRequestWithInvalidUploadPathLoggable(requestType, uploadFileName);
                  logAndSendError(res, 403, l);
               }
            } else {
               l = DeploymentServiceLogger.logBadContentTypeServletRequestLoggable(requestType, contentType);
               logAndSendError(res, 400, l);
            }
         }
      } else {
         l = DeploymentServiceLogger.logRequestWithInvalidUploadPathLoggable(requestType, uploadPath);
         logAndSendError(res, 403, l);
      }
   }

   private String getPartitionName(HttpServletRequest req) {
      String partitionName = mimeDecode(req.getHeader("wl_partition_name"));
      if (partitionName == null) {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (cic != null && !cic.isGlobalRuntime()) {
            partitionName = cic.getPartitionName();
         }
      }

      return partitionName;
   }

   private boolean validatePartitionName(HttpServletRequest req) {
      String incomingPartitionName = mimeDecode(req.getHeader("wl_partition_name"));
      String cicPartitionName = null;
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (cic != null && !cic.isGlobalRuntime()) {
         cicPartitionName = cic.getPartitionName();
      }

      if (cicPartitionName == null && incomingPartitionName == null) {
         return false;
      } else {
         return cicPartitionName == null || incomingPartitionName == null || cicPartitionName.equals(incomingPartitionName);
      }
   }

   private File doUploadFile(HttpServletRequest req, String saveDirectory) throws IOException {
      AuthenticatedSubject user = SecurityServiceManager.getCurrentSubject(kernelId);
      if (!this.authorizer.isAccessAllowed(user, FILEUPLOAD_RESOURCE, new ResourceIDDContextWrapper())) {
         throw new RuntimeException("User has no access to upload files");
      } else {
         File tmpDir;
         if (saveDirectory != null) {
            tmpDir = new File(saveDirectory);
            if (!tmpDir.exists()) {
               boolean md = tmpDir.mkdirs();
               if (!md) {
                  Loggable l = DeploymentServiceLogger.logCannotCreateUploadDirectoryLoggable(tmpDir.getCanonicalPath());
                  throw new IllegalArgumentException(l.getMessage());
               }
            }

            Loggable l;
            if (!tmpDir.isDirectory()) {
               l = DeploymentServiceLogger.logUploadDirectoryIsNotDirectoryLoggable(tmpDir.getCanonicalPath());
               throw new IllegalArgumentException(l.getMessage());
            }

            if (!tmpDir.canWrite()) {
               l = DeploymentServiceLogger.logUploadDirectoryIsNotWritableLoggable(tmpDir.getCanonicalPath());
               throw new IllegalArgumentException(l.getMessage());
            }
         }

         try {
            tmpDir = File.createTempFile("wls_upload", "");
         } catch (Exception var20) {
            throw new IllegalArgumentException(var20.getMessage());
         }

         if (tmpDir.exists()) {
            tmpDir.delete();
         }

         tmpDir.mkdirs();
         if (!tmpDir.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + tmpDir);
         } else if (!tmpDir.canWrite()) {
            throw new IllegalArgumentException("Not writable: " + tmpDir);
         } else {
            File saveTo = null;

            Object var14;
            try {
               String requestMethod;
               String fileName;
               String request;
               try {
                  DiskFileItemFactory factory = new DiskFileItemFactory();
                  factory.setSizeThreshold(1048576);
                  factory.setRepository(tmpDir);
                  ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
                  servletFileUpload.setSizeMax(2147483647L);
                  List fileItemsList = servletFileUpload.parseRequest(req);
                  FileItem fileItem = (FileItem)fileItemsList.iterator().next();
                  if (!fileItem.isFormField()) {
                     fileName = mimeDecode(fileItem.getName());
                     saveTo = new File(saveDirectory + fileName);
                     File saveDir = new File(saveDirectory);
                     if (!saveTo.getCanonicalPath().contains(saveDir.getCanonicalPath())) {
                        throw new IllegalArgumentException("File path " + saveTo + " is not contained within " + saveDirectory);
                     }

                     fileItem.write(saveTo);
                  }

                  return saveTo;
               } catch (FileUploadException var21) {
                  requestMethod = req.getMethod();
                  String applicationId = mimeDecode(req.getHeader("wl_upload_application_name"));
                  String applicationName = ApplicationVersionUtils.getApplicationName(applicationId);
                  fileName = ApplicationVersionUtils.getVersionId(applicationId);
                  String requestType = mimeDecode(req.getHeader("wl_request_type"));
                  String contentType = req.getContentType();
                  request = "\n" + requestMethod + ",\n" + "wl_request_type" + ":" + requestType + ",\n" + "wl_upload_application_name" + ": " + applicationName + ",\nContent-Type: " + contentType + ",\n";
                  if (this.isDebugEnabled()) {
                     this.debug(" Exception while parsing multipart/form-data request " + request + "reason: " + StackTraceUtils.throwable2StackTrace(var21));
                  }
               } catch (Exception var22) {
                  if (this.isDebugEnabled()) {
                     this.debug(" Exception while uploading file to directory : " + saveDirectory + "reason: " + StackTraceUtils.throwable2StackTrace(var22));
                  }

                  DeploymentServiceLogger.logExceptionOnUpload(saveDirectory.toString(), var22.getMessage());
                  if (saveTo.exists()) {
                     saveTo.delete();
                  }

                  requestMethod = null;
                  return requestMethod;
               }

               DeploymentServiceLogger.logExceptionOnUpload(request, var21.getMessage());
               if (saveTo != null && saveTo.exists()) {
                  saveTo.delete();
               }

               var14 = null;
            } finally {
               FileUtils.remove(tmpDir);
               if (!tmpDir.delete()) {
                  tmpDir.deleteOnExit();
               }

            }

            return (File)var14;
         }
      }
   }

   private final String getUploadDirName(String appName, String versionId, boolean isDelta, boolean isPlan, boolean isArchive, String rgt, String rg, String partition) {
      AppDeploymentMBean application = null;
      String uploadingDirName = null;
      if (appName != null) {
         application = ApplicationVersionUtils.getAppDeployment(ManagementService.getRuntimeAccess(kernelId).getDomain(), appName, versionId, rgt, rg, partition);
         if (application != null) {
            if (isDelta) {
               uploadingDirName = application.getAbsoluteSourcePath();
            } else if (!isPlan) {
               File appFile = new File(application.getAbsoluteSourcePath());
               uploadingDirName = appFile.getParentFile().getParent();
            } else if (isArchive) {
               uploadingDirName = this.getUploadDirForPlanDir(application);
            } else {
               uploadingDirName = this.getUploadDirForPlan(application);
            }

            if (uploadingDirName != null) {
               uploadingDirName = uploadingDirName + File.separator;
            }
         }
      }

      return uploadingDirName;
   }

   private String getUploadDirForPlan(AppDeploymentMBean application) {
      String uploadingDirName = application.getAbsolutePlanPath();
      if (uploadingDirName == null) {
         uploadingDirName = this.getOrCreatePlanDir(application);
      } else {
         uploadingDirName = (new File(uploadingDirName)).getParent();
      }

      return uploadingDirName;
   }

   private String getOrCreatePlanDir(AppDeploymentMBean application) {
      if (application.getPlanDir() != null) {
         return application.getAbsolutePlanDir();
      } else {
         File dd = null;
         String path = null;
         if (application.getAbsoluteInstallDir() != null) {
            dd = new File(application.getAbsoluteInstallDir());
            dd = new File(dd, "plan");
            dd.mkdirs();
            path = dd.getPath();
         }

         return path;
      }
   }

   private String getUploadDirForPlanDir(AppDeploymentMBean application) {
      return this.getOrCreatePlanDir(application);
   }

   private final String getDefaultUploadDirName(String rgtName, String partitionName, String rgName) {
      String uploadingDirName = null;
      if (rgtName != null) {
         uploadingDirName = this.getUploadDirFromResourceGroupTemplate(rgtName);
      } else if (partitionName == null && rgName == null) {
         uploadingDirName = ManagementService.getRuntimeAccess(kernelId).getServer().getUploadDirectoryName() + File.separator;
      } else {
         uploadingDirName = this.getUploadDirFromResourceGroup(partitionName, rgName);
      }

      if (this.isDebugEnabled()) {
         this.debug("uploadingDirName is " + uploadingDirName);
      }

      return uploadingDirName;
   }

   private final boolean extractArchive(String fileName, String toDirName) {
      File toDir = new File(toDirName);
      File jar = new File(fileName);
      if (this.isDebugEnabled()) {
         this.debug(" +++ toDir : " + toDir.getAbsolutePath());
         this.debug(" +++ jar : " + jar.getAbsolutePath());
      }

      try {
         JarFileUtils.extract(jar, toDir);
      } catch (IOException var6) {
         if (this.isDebugEnabled()) {
            this.debug(" Exception while extracting jar file to directory : " + toDir + "reason: " + StackTraceUtils.throwable2StackTrace(var6));
         }

         DeploymentServiceLogger.logExceptionOnExtract(toDir.toString(), var6.getMessage());
         return false;
      }

      jar.delete();
      return true;
   }

   private AuthenticatedSubject authenticateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
      long beginTime = System.currentTimeMillis();
      boolean var20 = false;

      Object var12;
      label221: {
         Loggable l;
         long timeTook;
         label222: {
            AuthenticatedSubject var24;
            long timeTook;
            label223: {
               label224: {
                  try {
                     var20 = true;
                     String nonce = request.getHeader("wls_nonce");
                     String userName;
                     String password;
                     String idd;
                     Loggable l;
                     if (nonce != null) {
                        userName = request.getHeader("wls_signature");
                        if (userName == null) {
                           Loggable l = DeploymentServiceLogger.logUnautherizedRequestLoggable(mimeDecode(request.getHeader("wl_request_type")), "No Signature");
                           l.log();
                           if (this.isDebugEnabled()) {
                              this.debug("DeploymentServiceServlet: authenticateRequest: " + l.getMessage());
                           }
                        }

                        password = request.getHeader("wls_timestamp");
                        idd = mimeDecode(request.getHeader("wls_server_name"));
                        if (!ConnectionSigner.authenticate(nonce, password, idd, userName)) {
                           l = DeploymentServiceLogger.logDomainWideSecretMismatchLoggable();
                           if (this.isDebugEnabled()) {
                              this.debug("DeploymentServiceServlet: authenticateRequest: Domain wide secret mismatch between nonce: " + nonce + " and signature: " + userName);
                           }

                           logAndSendError(response, 401, l);
                           l = null;
                           var20 = false;
                           break label222;
                        }

                        var24 = kernelId;
                        var20 = false;
                        break label223;
                     }

                     userName = mimeDecode(request.getHeader("username"));
                     if (this.isDebugEnabled()) {
                        this.debug("DeploymentServiceServlet: authenticateRequest: Received req.header username: " + userName);
                     }

                     password = mimeDecode(request.getHeader("password"));
                     idd = mimeDecode(request.getHeader("idd"));
                     if (userName != null && password != null) {
                        AuthenticatedSubject result;
                        try {
                           if (idd == null) {
                              result = this.authenticator.authenticate(new SimpleCallbackHandler(userName, password.toCharArray(), DeploymentServiceLogger.unrecognizedCallback()));
                           } else {
                              result = this.authenticator.authenticate(new SimpleCallbackHandler(userName, idd, password.toCharArray()));
                           }
                        } catch (LoginException var21) {
                           l = DeploymentServiceLogger.logInvalidUserNameOrPasswordLoggable();
                           if (this.isDebugEnabled()) {
                              this.debug("DeploymentServiceServlet: authenticateRequest: error - User name not authorized");
                           }

                           logAndSendError(response, 401, l);
                           var12 = null;
                           var20 = false;
                           break label221;
                        }

                        var24 = result;
                        var20 = false;
                        break label224;
                     }

                     if (this.isDebugEnabled()) {
                        if (userName == null) {
                           this.debug("DeploymentServiceServlet: authenticateRequest: error - User name not provided");
                        } else {
                           this.debug("DeploymentServiceServlet: authenticateRequest: error - User password not provided");
                        }
                     }

                     l = DeploymentServiceLogger.logNoUserNameOrPasswordLoggable();
                     logAndSendError(response, 401, l);
                     l = null;
                     var20 = false;
                  } finally {
                     if (var20) {
                        long timeTook = System.currentTimeMillis() - beginTime;
                        if (this.isDebugEnabled()) {
                           this.debug("DeploymentServiceServlet: authenticateRequest: TIME TOOK: " + timeTook);
                        }

                     }
                  }

                  timeTook = System.currentTimeMillis() - beginTime;
                  if (this.isDebugEnabled()) {
                     this.debug("DeploymentServiceServlet: authenticateRequest: TIME TOOK: " + timeTook);
                  }

                  return l;
               }

               timeTook = System.currentTimeMillis() - beginTime;
               if (this.isDebugEnabled()) {
                  this.debug("DeploymentServiceServlet: authenticateRequest: TIME TOOK: " + timeTook);
               }

               return var24;
            }

            timeTook = System.currentTimeMillis() - beginTime;
            if (this.isDebugEnabled()) {
               this.debug("DeploymentServiceServlet: authenticateRequest: TIME TOOK: " + timeTook);
            }

            return var24;
         }

         timeTook = System.currentTimeMillis() - beginTime;
         if (this.isDebugEnabled()) {
            this.debug("DeploymentServiceServlet: authenticateRequest: TIME TOOK: " + timeTook);
         }

         return l;
      }

      long timeTook = System.currentTimeMillis() - beginTime;
      if (this.isDebugEnabled()) {
         this.debug("DeploymentServiceServlet: authenticateRequest: TIME TOOK: " + timeTook);
      }

      return (AuthenticatedSubject)var12;
   }

   private void handleDeploymentServiceMessage(HttpServletRequest req, HttpServletResponse res, AuthenticatedSubject user) throws IOException {
      String messageSrc = mimeDecode(req.getHeader("serverName"));
      if (this.isDebugEnabled()) {
         this.debug("Received req.header serverName: " + messageSrc);
      }

      String reqIdString = req.getHeader("deployment_request_id");
      long requestId = reqIdString != null ? Long.parseLong(reqIdString) : -1L;
      String peerVersion = this.readOrConstructPeerVersion(req);
      if (this.isDebugEnabled()) {
         this.debug("Peer Version : " + peerVersion);
      }

      boolean isSynchronous = Boolean.valueOf(mimeDecode(req.getHeader("isSynchronous")));
      if (this.isDebugEnabled()) {
         this.debug("Received req.header isSynch: " + isSynchronous);
      }

      res.addHeader("serverName", mimeEncode(this.serverName));
      ObjectInputStream in = null;
      WLObjectOutputStream out = null;
      OutputStream ros = null;

      try {
         ServerResource resource = new ServerResource((String)null, messageSrc, "boot");
         if (this.authorizer.isAccessAllowed(user, resource, new ResourceIDDContextWrapper(true))) {
            in = new DeploymentObjectInputStream(req.getInputStream(), peerVersion);
            DeploymentServiceMessage message = (DeploymentServiceMessage)in.readObject();
            if (this.isDebugEnabled()) {
               this.debug("Received DeploymentService message '" + message);
            }

            res.setStatus(200);
            if (isSynchronous) {
               if (this.isDebugEnabled()) {
                  this.debug("Handling synchronous deployment service message");
               }

               DeploymentServiceMessage drsResponse = this.loopbackReceiver.receiveSynchronousMessage(message);
               if (drsResponse != null && this.isDebugEnabled()) {
                  this.debug("Sending out synchronous response " + drsResponse);
               }

               res.addHeader("server_version", PeerInfo.getPeerInfo().getVersionAsString());
               CacheByteOutputStream byteLenOut = new CacheByteOutputStream(CACHE_SIZE_LIMIT);
               out = new DeploymentObjectOutputStream(byteLenOut, peerVersion);
               out.setReplacer(RemoteObjectReplacer.getReplacer());
               out.writeObject(drsResponse);
               out.flush();
               res.setContentLength(byteLenOut.size());
               ros = res.getOutputStream();
               if (byteLenOut.isCached()) {
                  byteLenOut.writeTo(ros);
                  ros.flush();
               } else {
                  out = new DeploymentObjectOutputStream(ros, peerVersion);
                  out.setReplacer(RemoteObjectReplacer.getReplacer());
                  out.writeObject(drsResponse);
                  out.flush();
               }

               return;
            } else {
               if (this.isDebugEnabled()) {
                  this.debug("Handling asynchronous deployment service message");
               }

               this.loopbackReceiver.receiveMessage(message);
               return;
            }
         }

         Loggable l = DeploymentServiceLogger.logAccessNotAllowedLoggable(messageSrc);
         if (this.isDebugEnabled()) {
            this.debug(l.getMessage());
            this.debug("DeploymentServiceServlet error - access not allowed");
         }

         logAndSendError(res, 401, l);
      } catch (Throwable var20) {
         res.addHeader("serverName", mimeEncode(this.serverName));
         String errorStack = StackTraceUtils.throwable2StackTrace(var20);
         if (this.isDebugEnabled()) {
            this.debug("DeploymentServiceServlet error - " + var20.getMessage() + " " + errorStack);
         }

         Loggable l = DeploymentServiceLogger.logExceptionInServletRequestForDeploymentMsgLoggable(requestId, messageSrc, errorStack);
         l.log();
         l = DeploymentServiceLogger.logExceptionInServletRequestForDeploymentMsgLoggable(requestId, messageSrc, var20.getMessage());
         sendError(res, 500, l.getMessage());
         return;
      } finally {
         if (in != null) {
            in.close();
         }

         if (out != null) {
            out.close();
         }

         if (ros != null) {
            ros.close();
         }

      }

   }

   private void handleDataTransferRequest(HttpServletRequest req, HttpServletResponse res, AuthenticatedSubject user) throws IOException {
      if (this.isDebugEnabled()) {
         this.debug("Received DataTransferRequest : ");
      }

      String peerVersion = this.readOrConstructPeerVersion(req);
      if (this.isDebugEnabled()) {
         this.debug("Peer Version : " + peerVersion);
      }

      String reqIdString = req.getHeader("deployment_request_id");
      long requestId = reqIdString != null ? Long.parseLong(reqIdString) : -1L;
      String srcServer = mimeDecode(req.getHeader("serverName"));
      ObjectInputStream in = null;

      try {
         Loggable l;
         try {
            String messageSrc = mimeDecode(req.getHeader("serverName"));
            ServerResource resource = new ServerResource((String)null, messageSrc, "boot");
            if (this.authorizer.isAccessAllowed(user, resource, new ResourceIDDContextWrapper(true))) {
               in = new DeploymentObjectInputStream(req.getInputStream(), peerVersion);
               DataTransferRequest request = (DataTransferRequest)in.readObject();
               MultiDataStream stream = DataHandlerManager.getInstance().getHttpDataTransferHandler().getDataAsStream(request);
               String lockPath = request.getLockPath();
               FileLock lock = null;

               try {
                  if (lockPath != null && lockPath.length() > 0) {
                     lock = this.lockFile(lockPath);
                  }

                  MultipartResponse multi = new MultipartResponse(res, stream);
                  multi.write();
                  return;
               } finally {
                  this.unlockFile(lock);
               }
            }

            l = DeploymentServiceLogger.logAccessNotAllowedLoggable(messageSrc);
            if (this.isDebugEnabled()) {
               this.debug(l.getMessage());
               this.debug("DeploymentServiceServlet error - access not allowed");
            }

            logAndSendError(res, 401, l);
         } catch (Throwable var26) {
            String errorMsg = StackTraceUtils.throwable2StackTrace(var26);
            if (this.isDebugEnabled()) {
               this.debug("DeploymentServiceServlet error - " + var26.getMessage() + " " + errorMsg);
            }

            l = DeploymentServiceLogger.logExceptionInServletRequestForDatatransferMsgLoggable(requestId, srcServer, errorMsg);
            l.log();
            l = DeploymentServiceLogger.logExceptionInServletRequestForDatatransferMsgLoggable(requestId, srcServer, var26.getMessage());
            sendError(res, 500, l.getMessage());
            return;
         }
      } finally {
         if (in != null) {
            in.close();
         }

      }

   }

   public Object run() throws ServletException {
      super.init(this.config);
      return null;
   }

   public static URL getURL() throws MalformedURLException {
      String httpURL = ManagementService.getPropertyService(kernelId).getAdminHttpUrl();
      if (!httpURL.endsWith("/")) {
         httpURL = httpURL + "/";
      }

      return new URL(httpURL + "bea_wls_deployment_internal/DeploymentService");
   }

   private static void sendError(HttpServletResponse res, int code, String msg) throws IOException {
      if (!res.isCommitted()) {
         res.addHeader("ErrorMsg", msg);
         res.sendError(code, msg);
      }

   }

   private static void logAndSendError(HttpServletResponse res, int code, Loggable l) throws IOException {
      l.log();
      sendError(res, code, l.getMessage());
   }

   private FileLock lockFile(String lockFileName) {
      if (lockFileName == null) {
         return null;
      } else {
         FileLock fileLock = null;

         try {
            String prefix = DomainDir.getRootDir() + File.separator;
            String lockFilePath = prefix + lockFileName;
            FileOutputStream os = new FileOutputStream(lockFilePath);
            fileLock = FileUtils.getFileLock(os.getChannel(), 30000L);
         } catch (IOException var6) {
         }

         return fileLock;
      }
   }

   private void unlockFile(FileLock lockedFile) {
      if (lockedFile != null) {
         try {
            lockedFile.release();
            lockedFile.channel().close();
         } catch (IOException var3) {
         }
      }

   }

   private static String mimeDecode(String val) {
      String result = null;

      try {
         if (val != null) {
            result = MimeUtility.decodeText(val);
         }
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

   private static int getIntProperty(String key, int defaultValue) {
      try {
         String prop = System.getProperty(key);
         if (prop != null) {
            int value = Integer.parseInt(prop);
            if (value >= 0) {
               return value;
            }
         }
      } catch (NumberFormatException var4) {
      }

      return defaultValue;
   }

   private String readOrConstructPeerVersion(HttpServletRequest req) {
      String invokingVersion = req.getHeader("server_version");
      if (this.isDebugEnabled()) {
         this.debug("Received req.header server_version: " + invokingVersion);
      }

      String peerVersion = invokingVersion != null && invokingVersion.length() != 0 ? invokingVersion : PeerInfo.getPeerInfo().getVersionAsString();
      if (this.isDebugEnabled()) {
         this.debug("decided peerVersion: " + peerVersion);
      }

      return peerVersion;
   }

   private String getUploadDirFromResourceGroupTemplate(String rgtName) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ResourceGroupTemplateMBean rgtm = domain.lookupResourceGroupTemplate(rgtName);
      return rgtm != null ? rgtm.getUploadDirectoryName() : null;
   }

   private String getUploadDirFromResourceGroup(String partitionName, String rgName) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (partitionName != null) {
         PartitionMBean partitionMBean = domain.lookupPartition(partitionName);
         return partitionMBean == null ? null : partitionMBean.getUploadDirectoryName();
      } else if (rgName != null) {
         ResourceGroupMBean rgMBean = domain.lookupResourceGroup(rgName);
         return rgMBean == null ? null : rgMBean.getUploadDirectoryName();
      } else {
         return null;
      }
   }

   private void setFrameOptionsDeny(HttpServletResponse res) {
      res.setHeader("X-Frame-Options", "DENY");
   }
}
