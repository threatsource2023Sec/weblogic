package weblogic.deploy.api.internal.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import javax.mail.internet.MimeUtility;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentNotification;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.utils.DeployerHelperTextFormatter;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.ConnectionSigner;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class JMXDeployerHelper {
   public static final String HTTP_STRING = "http";
   public static final String HTTPS_STRING = "https";
   public static final String T3_STRING = "t3";
   public static final String T3S_STRING = "t3s";
   public static final String IIOP_STRING = "iiop";
   public static final String IIOPS_STRING = "iiops";
   private static final String MIME_BOUNDARY = "---------------------------7d01b33320494";
   private static final String TEMP_FILE_PREFIX = "wl_comp";
   private static final String TEMP_FILE_EXT = ".jar";
   private static final boolean UPLOAD_LARGEFILE = Boolean.getBoolean("weblogic.deploy.UploadLargeFile");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final MBeanServerConnection mBeanServer;
   private final Vector notifications = new Vector();
   private DeployerRuntimeMBean deployer;
   private String partitionNameFromCurrentConnection = null;
   private static final DeployerHelperTextFormatter textFormatter = new DeployerHelperTextFormatter();
   private static final boolean debug = false;
   private DomainRuntimeMBean domainRuntime = null;
   private AppRuntimeStateRuntimeMBean appRT = null;
   private DomainRuntimeServiceMBean drsb;
   private DomainMBean domain;
   private JMXConnector connector = null;
   private boolean supportsMT = false;
   private ConfigurationManagerMBean configMgr = null;

   public JMXDeployerHelper(JMXConnector conn) throws DeployerHelperException {
      if (conn != null) {
         this.connector = conn;
         MBeanServerConnection mbs = null;

         try {
            mbs = conn.getMBeanServerConnection();
         } catch (IOException var5) {
            var5.printStackTrace();
         }

         this.mBeanServer = mbs;
      } else {
         this.mBeanServer = null;
      }

      try {
         this.init();
      } catch (InstanceNotFoundException var4) {
         throw new DeployerHelperException(var4.toString(), var4);
      }
   }

   public JMXDeployerHelper(MBeanServerConnection mbs) throws DeployerHelperException {
      this.mBeanServer = mbs;

      try {
         this.init();
      } catch (InstanceNotFoundException var3) {
         throw new DeployerHelperException(var3.toString(), var3);
      }
   }

   private void init() throws InstanceNotFoundException {
      try {
         if (this.mBeanServer == null) {
            DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
            this.domainRuntime = domainAccess.getDomainRuntime();
         } else {
            ObjectName drs = new ObjectName(DomainRuntimeServiceMBean.OBJECT_NAME);
            if (this.connector == null) {
               this.drsb = (DomainRuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.mBeanServer, drs);
            } else {
               this.drsb = (DomainRuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.connector, drs);
            }

            this.domainRuntime = this.drsb.getDomainRuntime();
         }

         DomainPartitionRuntimeMBean domainPartitionRuntime = this.getCurrentDomainPartitionRuntime();
         if (domainPartitionRuntime == null) {
            this.deployer = this.domainRuntime.getDeployerRuntime();
            this.appRT = this.domainRuntime.getAppRuntimeStateRuntime();
         } else {
            this.deployer = domainPartitionRuntime.getDeployerRuntime();
            this.appRT = domainPartitionRuntime.getAppRuntimeStateRuntime();
            this.partitionNameFromCurrentConnection = domainPartitionRuntime.getName();
         }

      } catch (Throwable var3) {
         InstanceNotFoundException infe = new InstanceNotFoundException(var3.toString());
         infe.initCause(var3);
         infe.setStackTrace(var3.getStackTrace());
         throw infe;
      }
   }

   private DomainPartitionRuntimeMBean getCurrentDomainPartitionRuntime() {
      try {
         DomainPartitionRuntimeMBean dprmb = this.domainRuntime.getCurrentDomainPartitionRuntime();
         this.supportsMT = true;
         return dprmb;
      } catch (RuntimeException var3) {
         Throwable cause = var3.getCause();
         if (cause instanceof AttributeNotFoundException) {
            this.supportsMT = false;
            return null;
         } else {
            throw var3;
         }
      }
   }

   public boolean supportsMT() {
      return this.supportsMT;
   }

   public ConfigurationManagerMBean getConfigMgr() {
      return this.configMgr;
   }

   public boolean needsNonExclusiveLock() {
      if (this.getConfigMgr() == null) {
         return this.needsNonExclusiveLock((String)null);
      } else {
         return this.getConfigMgr().isEditor() && !this.getConfigMgr().isCurrentEditorExclusive();
      }
   }

   public boolean needsNonExclusiveLock(String editSessionName) {
      if (this.getConfigMgr() != null) {
         return this.getConfigMgr().isEditor() && !this.getConfigMgr().isCurrentEditorExclusive();
      } else {
         EditAccess access = this.getEditAccess(editSessionName);
         return access != null && access.isEditor() && !access.isEditorExclusive();
      }
   }

   public boolean isEditing() {
      if (this.getConfigMgr() != null) {
         return this.getConfigMgr().getCurrentEditor() != null;
      } else {
         EditAccess access = ManagementServiceRestricted.getEditAccess(kernelId);
         return access != null && access.getEditor() != null;
      }
   }

   public void setConfigMgr(ConfigurationManagerMBean configMgr) {
      this.configMgr = configMgr;
   }

   public DomainMBean getDomain() {
      if (this.mBeanServer != null) {
         if (this.partitionNameFromCurrentConnection == null) {
            this.domain = this.drsb.getDomainPending();
         }

         if (this.domain == null) {
            this.domain = this.drsb.getDomainConfiguration();
         }
      } else {
         try {
            this.domain = ManagementServiceRestricted.getEditAccess(kernelId).getDomainBeanWithoutLock();
         } catch (Throwable var3) {
            RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
            this.domain = runtimeAccess.getDomain();
         }
      }

      return this.domain;
   }

   public DomainMBean getRuntimeDomain() {
      if (this.mBeanServer != null) {
         String adminServer = this.drsb.getServerName();
         return this.drsb.findDomainConfiguration(adminServer);
      } else {
         return ManagementService.getRuntimeAccess(kernelId).getDomain();
      }
   }

   private EditAccess getEditAccess(String editSessionName) {
      EditAccess access = null;
      if (editSessionName != null) {
         access = ManagementServiceRestricted.getEditSession(editSessionName);
      }

      if (access == null) {
         access = ManagementServiceRestricted.getEditAccess(kernelId);
      }

      return access;
   }

   private boolean isAliveState(String state) {
      return state.equals("ADMIN") || state.equals("RUNNING") || state.equals("RESUMING");
   }

   public boolean isServerAlive(String name) {
      try {
         ServerLifeCycleRuntimeMBean slrt = this.domainRuntime.lookupServerLifeCycleRuntime(name);
         return this.isAliveState(slrt.getState());
      } catch (Throwable var3) {
         return false;
      }
   }

   public AppRuntimeStateRuntimeMBean getAppRuntimeStateMBean() throws Exception {
      return this.appRT;
   }

   public static File createJarFromDirectory(File root, File directory, File tempDir, Set fileSet) throws IOException {
      if (directory == null) {
         directory = root;
      }

      File result = null;
      JarOutputStream out = null;

      File var7;
      try {
         result = File.createTempFile("wl_comp", ".jar", tempDir);
         result.deleteOnExit();
         out = new JarOutputStream(new FileOutputStream(result));
         String rootString = root.toString().replace(File.separatorChar, '/');
         addFileOrDirectoryToJar(rootString.length(), directory, out, fileSet);
         var7 = result;
      } finally {
         if (out != null) {
            out.close();
         }

      }

      return var7;
   }

   private static void addFileOrDirectoryToJar(int rootNameLen, File fileOrDirectory, JarOutputStream out, Set fileSet) throws IOException {
      String entryName = fileOrDirectory.toString().replace(File.separatorChar, '/');
      if (entryName.length() > rootNameLen) {
         entryName = entryName.substring(rootNameLen + 1);
      } else if (entryName.length() == rootNameLen && fileOrDirectory.isFile()) {
         entryName = fileOrDirectory.getName();
      } else {
         entryName = "";
      }

      if (fileOrDirectory.isDirectory()) {
         if (fileSet != null && fileSet.contains(entryName)) {
            fileSet.remove(entryName);
            fileSet = null;
         }

         String[] files = fileOrDirectory.list();

         for(int i = 0; i < files.length; ++i) {
            File f = new File(fileOrDirectory, files[i]);
            addFileOrDirectoryToJar(rootNameLen, f, out, fileSet);
         }
      } else {
         InputStream inStream = null;

         try {
            if (fileSet == null || fileSet.contains(entryName)) {
               if (fileSet != null) {
                  fileSet.remove(entryName);
               }

               JarEntry entry = new JarEntry(entryName);
               entry.setTime(fileOrDirectory.lastModified());
               out.putNextEntry(entry);
               byte[] buf = new byte[4096];
               int read = false;
               inStream = new BufferedInputStream(new FileInputStream(fileOrDirectory));

               int read;
               while((read = inStream.read(buf)) != -1) {
                  out.write(buf, 0, read);
               }
            }
         } catch (Exception var13) {
            String message = DeployerHelperTextFormatter.getInstance().exceptionArchivingFile(fileOrDirectory.toString(), var13.toString());
            if (var13 instanceof IOException) {
               throw (IOException)var13;
            }

            throw new IOException(message);
         } finally {
            if (inStream != null) {
               inStream.close();
            }

         }
      }

   }

   public DeployerRuntimeMBean getDeployer() throws Throwable {
      return this.deployer;
   }

   public String getPartitionNameFromCurrentConnection() {
      return this.partitionNameFromCurrentConnection;
   }

   public DeployerRuntimeMBean getDeployer(String partitionName) throws Throwable {
      if (partitionName == null) {
         return this.deployer;
      } else {
         DomainPartitionRuntimeMBean domainPartitionRuntime = null;
         if (this.mBeanServer == null) {
            DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
            domainPartitionRuntime = domainAccess.lookupDomainPartitionRuntime(partitionName);
         } else {
            DomainRuntimeMBean domainRuntime = this.drsb.getDomainRuntime();
            domainPartitionRuntime = domainRuntime.lookupDomainPartitionRuntime(partitionName);
         }

         if (domainPartitionRuntime == null) {
            Loggable l = SPIDeployerLogger.logNonExistPartitionLoggable(partitionName);
            l.log();
            throw new IllegalArgumentException(l.getMessage());
         } else {
            return domainPartitionRuntime.getDeployerRuntime();
         }
      }
   }

   public DeploymentTaskRuntimeMBean getTaskMBean(String name) {
      try {
         return this.deployer.query(name);
      } catch (Throwable var3) {
         return null;
      }
   }

   public void removeTask(DeploymentTaskRuntimeMBean task) {
      if (task != null) {
         this.deployer.removeTask(task.getId());
      }
   }

   public DeploymentTaskRuntimeMBean getTaskByID(String id) {
      try {
         return this.deployer.query(id);
      } catch (Throwable var3) {
         throw new RuntimeException(var3);
      }
   }

   public DeploymentTaskRuntimeMBean[] getAllTasks() {
      try {
         return this.deployer.list();
      } catch (Throwable var2) {
         throw new RuntimeException(var2);
      }
   }

   public ApplicationMBean getApplication(String name) throws InstanceNotFoundException {
      return this.getAppDeployment(name).getAppMBean();
   }

   public AppDeploymentMBean getAppDeployment(String name) throws InstanceNotFoundException {
      AppDeploymentMBean app = AppDeploymentHelper.lookupAppOrLib(name, this.getDomain());
      if (app == null) {
         throw new InstanceNotFoundException(name);
      } else {
         return app;
      }
   }

   public String getAdminServerName() {
      if (this.domain == null) {
         this.getDomain();
      }

      return this.domain.getAdminServerName();
   }

   public DeploymentNotification getNextNotification(long timeout) {
      DeploymentNotification notification = null;
      long endTime = System.currentTimeMillis() + timeout;

      do {
         if (this.notifications != null && !this.notifications.isEmpty()) {
            notification = (DeploymentNotification)this.notifications.remove(0);
         } else {
            long waitTime = endTime - System.currentTimeMillis();
            if (waitTime <= 0L) {
               return null;
            }

            try {
               this.wait(waitTime);
            } catch (InterruptedException var9) {
            }
         }
      } while(notification == null);

      return notification;
   }

   public boolean isSourceArchive(String fname) {
      File f = new File(fname);
      return f.isFile();
   }

   public synchronized void queueNotification(Notification notification) {
      this.notifications.add(notification);
      this.notify();
   }

   public String uploadSource(String adminUrlString, String user, String password, String source, String[] fileNames, String appId, DeploymentOptions options) throws DeployerHelperException {
      String sourceNameOnServer = null;
      String fdsUrlString = null;

      try {
         fdsUrlString = this.getDSSUrl(adminUrlString);
         HttpURLConnection connection = this.getDSSConnection(fdsUrlString);
         this.initConnection(user, password, appId, connection, "app_upload", options);
         File sourceFile = new File(source);
         String targetFileName = sourceFile.getName();
         String isArchive;
         if (this.isSourceArchive(source)) {
            isArchive = "true";
         } else {
            isArchive = "false";
            targetFileName = targetFileName.concat(".jar");
         }

         connection.setRequestProperty("archive", isArchive);
         connection.setRequestProperty("wl_upload_delta", Boolean.toString(fileNames != null));
         TransferHelper transferHelper = this.getInputStreamForSource(source, fileNames);
         BufferedInputStream sourceStream = transferHelper.getStream();
         OutputStream httpOutputStream;
         if (UPLOAD_LARGEFILE) {
            connection.setChunkedStreamingMode(8192);
            httpOutputStream = connection.getOutputStream();
            this.transferData(sourceStream, httpOutputStream, targetFileName);
         } else {
            UnsyncByteArrayOutputStream out = new UnsyncByteArrayOutputStream();
            this.transferData(sourceStream, out, targetFileName);
            connection.setRequestProperty("Content-Length", String.valueOf(out.size()));
            httpOutputStream = connection.getOutputStream();
            out.writeTo(httpOutputStream);
         }

         File fileToDelete = transferHelper.getSource();
         if (fileToDelete != null) {
            fileToDelete.delete();
         }

         int httpResponseCode = connection.getResponseCode();
         String type = connection.getContentType();
         if (httpResponseCode == 200 && type != null) {
            BufferedReader replyReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sourceNameOnServer = replyReader.readLine();
            sourceNameOnServer = this.mimeDecode(sourceNameOnServer);
            replyReader.close();
            return sourceNameOnServer;
         } else {
            String errorMessage = connection.getHeaderField("ErrorMsg");
            if (errorMessage == null) {
               errorMessage = "HttpResponseCode: " + httpResponseCode + " type: " + type;
            }

            throw new DeployerHelperException(textFormatter.errorUploading(errorMessage));
         }
      } catch (URISyntaxException var21) {
         throw new DeployerHelperException(var21.toString(), var21);
      } catch (IOException var22) {
         throw new DeployerHelperException(textFormatter.exceptionUploadingSource(fdsUrlString, appId, source), var22);
      }
   }

   public String uploadPlan(String adminUrlString, String user, String password, String fileName, String appId, DeploymentOptions options) throws DeployerHelperException {
      String sourceNameOnServer = null;
      String fdsUrlString = null;

      try {
         fdsUrlString = this.getDSSUrl(adminUrlString);
         HttpURLConnection connection = this.getDSSConnection(fdsUrlString);
         this.initConnection(user, password, appId, connection, "plan_upload", options);
         File sourceFile = new File(fileName);
         String targetFileName = sourceFile.getName();
         if (sourceFile.isDirectory()) {
            connection.setRequestProperty("archive", "false");
            targetFileName = targetFileName.concat(".jar");
         }

         OutputStream out = connection.getOutputStream();
         TransferHelper transferHelper = this.getInputStreamForSource(fileName, (String[])null);
         BufferedInputStream sourceStream = transferHelper.getStream();
         this.transferData(sourceStream, out, targetFileName);
         File fileToDelete = transferHelper.getSource();
         if (fileToDelete != null) {
            fileToDelete.delete();
         }

         BufferedReader replyReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         sourceNameOnServer = replyReader.readLine();
         replyReader.close();
         return sourceNameOnServer;
      } catch (URISyntaxException var17) {
         throw new DeployerHelperException(var17.toString(), var17);
      } catch (IOException var18) {
         throw new DeployerHelperException(textFormatter.exceptionUploadingSource(fdsUrlString, appId, fileName), var18);
      }
   }

   public String uploadPartitionUserFile(String adminUrlString, String user, String password, String fileName, String appId, DeploymentOptions options) throws DeployerHelperException {
      String sourceNameOnServer = null;
      String fdsUrlString = null;

      try {
         fdsUrlString = this.getDSSUrl(adminUrlString);
         HttpURLConnection connection = this.getDSSConnection(fdsUrlString);
         this.initConnection(user, password, appId, connection, "user_file_upload", options);
         File sourceFile = new File(fileName);
         String targetFileName = sourceFile.getName();
         connection.setRequestProperty("wl_upload_file_name", mimeEncode(targetFileName));
         OutputStream out = connection.getOutputStream();
         TransferHelper transferHelper = this.getInputStreamForSource(fileName, (String[])null);
         BufferedInputStream sourceStream = transferHelper.getStream();
         this.transferData(sourceStream, out, targetFileName);
         File fileToDelete = transferHelper.getSource();
         if (fileToDelete != null) {
            fileToDelete.delete();
         }

         BufferedReader replyReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         sourceNameOnServer = replyReader.readLine();
         replyReader.close();
         return sourceNameOnServer;
      } catch (URISyntaxException var17) {
         throw new DeployerHelperException(var17.toString(), var17);
      } catch (IOException var18) {
         throw new DeployerHelperException(var18.toString(), var18);
      }
   }

   private void transferData(InputStream sourceStream, OutputStream out, String targetFileName) throws IOException {
      PrintStream outStream = new PrintStream(out);
      String streamSend = "-----------------------------7d01b33320494\r\n";
      streamSend = streamSend + "Content-Disposition: form-data; name=\"file\"; filename=\"" + mimeEncode(targetFileName) + "\"\r\n";
      streamSend = streamSend + "Content-Type: application/x-zip-compressed\r\n";
      streamSend = streamSend + "\r\n";
      outStream.print(streamSend);
      DataOutputStream dataOutputStream = new DataOutputStream(out);
      byte[] b = new byte[1024];

      while(true) {
         int read = sourceStream.read(b);
         if (read == -1) {
            outStream.print("\r\n-----------------------------7d01b33320494--");
            outStream.flush();
            outStream.close();
            dataOutputStream.close();
            sourceStream.close();
            return;
         }

         dataOutputStream.write(b, 0, read);
      }
   }

   private void initConnection(String user, String password, String appId, HttpURLConnection connection, String requestType, DeploymentOptions options) {
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setAllowUserInteraction(true);
      connection.setRequestProperty("wl_request_type", mimeEncode(requestType));
      if (user != null) {
         connection.setRequestProperty("username", mimeEncode(user));
         connection.setRequestProperty("password", mimeEncode(password));
      } else {
         ConnectionSigner.signConnection(connection, (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction()));
      }

      if (appId != null) {
         connection.setRequestProperty("wl_upload_application_name", mimeEncode(appId));
      }

      connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------7d01b33320494");
      String rgtName = options.getResourceGroupTemplate();
      if (rgtName != null) {
         connection.setRequestProperty("wl_resource_group_template_name", mimeEncode(rgtName));
      }

      String partitionName = options.getPartition();
      if (partitionName != null) {
         connection.setRequestProperty("wl_partition_name", mimeEncode(partitionName));
      }

      String rgName = options.getResourceGroup();
      if (rgName != null) {
         connection.setRequestProperty("wl_resource_group_name", mimeEncode(rgName));
      }

      String uploadPath = options.getUploadPath();
      if (uploadPath != null) {
         connection.setRequestProperty("wl_upload_path", mimeEncode(uploadPath));
      }

      boolean overwrite = options.getOverwriteFile();
      if (overwrite) {
         connection.setRequestProperty("wl_overwrite_file", mimeEncode("true"));
      } else {
         connection.setRequestProperty("wl_overwrite_file", mimeEncode("false"));
      }

   }

   private String getDSSUrl(String adminUrlString) throws URISyntaxException {
      if (!adminUrlString.startsWith("http")) {
         URI url = new URI(adminUrlString);
         URI httpUrl = new URI(this.getHTTPProtocol(url.getScheme()), (String)null, url.getHost(), url.getPort(), url.getPath(), (String)null, (String)null);
         adminUrlString = httpUrl.toString();
      }

      if (!adminUrlString.endsWith("/")) {
         adminUrlString = adminUrlString + "/";
      }

      return adminUrlString + "bea_wls_deployment_internal/DeploymentService";
   }

   private HttpURLConnection getDSSConnection(String fdsUrlString) throws IOException {
      URL fdsUrl = new URL(fdsUrlString);
      return (HttpURLConnection)fdsUrl.openConnection();
   }

   private String getHTTPProtocol(String scheme) {
      return !scheme.equals("https") && !scheme.equals("t3s") && !scheme.equals("iiops") ? "http" : "https";
   }

   TransferHelper getInputStreamForSource(String source, String[] fileNames) throws IOException, DeployerHelperException {
      try {
         URL url = new URL(source);
         TransferHelper transferHelper = new TransferHelper(new BufferedInputStream(url.openStream()), (File)null);
         return transferHelper;
      } catch (MalformedURLException var8) {
         File sourceFile = new File(source);
         if (!sourceFile.exists()) {
            throw new DeployerHelperException(textFormatter.exceptionNoSuchSource(source));
         } else {
            Set fileSet = null;
            if (fileNames != null) {
               fileSet = new TreeSet();

               for(int i = 0; i < fileNames.length; ++i) {
                  String fileNameFormatted = fileNames[i].replace(File.separatorChar, '/');
                  fileSet.add(fileNameFormatted);
               }
            }

            File fileToDelete = null;
            if (sourceFile.isDirectory()) {
               sourceFile = createJarFromDirectory(sourceFile, (File)null, (File)null, fileSet);
               fileToDelete = sourceFile;
            }

            if (fileNames != null && fileSet != null && fileSet.size() > 0) {
               throw new DeployerHelperException(textFormatter.exceptionUploadingFiles(fileSet.toString(), source));
            } else {
               TransferHelper transferHelper = new TransferHelper(new BufferedInputStream(new FileInputStream(sourceFile)), fileToDelete);
               return transferHelper;
            }
         }
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

   private String mimeDecode(String val) {
      try {
         if (val != null) {
            return MimeUtility.decodeText(val);
         }
      } catch (UnsupportedEncodingException var3) {
      }

      return val;
   }
}
