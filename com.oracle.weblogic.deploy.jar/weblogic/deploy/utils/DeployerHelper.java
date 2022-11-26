package weblogic.deploy.utils;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import javax.mail.internet.MimeUtility;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.internal.utils.JMXDeployerHelper;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.deploy.internal.DeployerTextFormatter;
import weblogic.management.DeploymentNotification;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.utils.DeployerHelperTextFormatter;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.utils.Debug;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class DeployerHelper {
   public static final String HTTP_STRING = "http";
   public static final String HTTPS_STRING = "https";
   public static final String T3_STRING = "t3";
   public static final String T3S_STRING = "t3s";
   public static final String IIOP_STRING = "iiop";
   public static final String IIOPS_STRING = "iiops";
   private static final String DEPLOYER_MBEAN_TYPE = "DeployerRuntime";
   private static final String APPLICATION_MBEAN_TYPE = "Application";
   private static final String MIME_BOUNDARY = "---------------------------7d01b33320494";
   private static final String TEMP_FILE_PREFIX = "wl_comp";
   private static final String TEMP_FILE_EXT = ".jar";
   private MBeanServerConnection mbeanServerConnection;
   private JMXDeployerHelper jmxHelper;
   private DeployerRuntimeMBean deployer;
   private static final DeployerHelperTextFormatter textFormatter = new DeployerHelperTextFormatter();
   private static DeployerTextFormatter messageFormatter = new DeployerTextFormatter();
   private boolean formatted;
   private TaskCompletionNotificationListener taskCompletion;

   private DeployerHelper() {
      this.formatted = false;
      this.taskCompletion = null;
   }

   public DeployerHelper(MBeanServerConnection theMBeanServerConnection) {
      this();
      this.mbeanServerConnection = theMBeanServerConnection;

      try {
         this.jmxHelper = new JMXDeployerHelper(this.mbeanServerConnection);
      } catch (weblogic.deploy.api.internal.utils.DeployerHelperException var3) {
         throw new ServerConnectionException(var3.getMessage(), var3);
      }
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

   public MBeanServerConnection getMBeanServerConnection() {
      return this.mbeanServerConnection;
   }

   public DeployerRuntimeMBean getDeployer() {
      if (this.deployer != null) {
         return this.deployer;
      } else {
         try {
            this.deployer = this.jmxHelper.getDeployer();
         } catch (Throwable var2) {
            var2.printStackTrace();
         }

         return this.deployer;
      }
   }

   public DeploymentTaskRuntimeMBean getTaskByID(String id) {
      return this.getDeployer().query(id);
   }

   public DeploymentTaskRuntimeMBean[] getAllTasks() {
      return this.getDeployer().list();
   }

   public ApplicationMBean getApplication(String name) throws InstanceNotFoundException {
      DomainMBean root = this.jmxHelper.getDomain();
      return root.lookupApplication(name);
   }

   public List getAppDeployments() {
      List allAppsList = new ArrayList();
      DomainMBean root = this.jmxHelper.getDomain();
      AppDeploymentMBean[] allApps = root.getAppDeployments();

      for(int i = 0; i < allApps.length; ++i) {
         if (!allApps[i].isInternalApp()) {
            String appName = ApplicationVersionUtils.getDisplayName(allApps[i]);
            allAppsList.add(appName);
         }
      }

      return allAppsList;
   }

   public String getAdminServerName() {
      return this.jmxHelper.getDomain().getAdminServerName();
   }

   public boolean isSourceArchive(String fname) {
      File f = new File(fname);
      return f.isFile();
   }

   public void initiateListening(DeploymentTaskRuntimeMBean task) throws InstanceNotFoundException {
      ApplicationMBean application = task.getDeploymentObject();
      if (application != null) {
         try {
            this.mbeanServerConnection.addNotificationListener(application.getObjectName(), new DeployerHelperListener(task.getId(), this), new DeployerHelperFilter(), (Object)null);
         } catch (IOException var4) {
            var4.printStackTrace();
         }

      }
   }

   public String uploadSource(String adminUrlString, String user, String password, String source, String[] fileNames, String applicationName) throws DeployerHelperException {
      String sourceNameOnServer = null;
      String fdsUrlString = null;

      try {
         fdsUrlString = this.getDSSUrl(adminUrlString);
         HttpURLConnection connection = this.getDSSConnection(fdsUrlString);
         this.initConnection(user, password, applicationName, connection, "app_upload");
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
         UnsyncByteArrayOutputStream out = new UnsyncByteArrayOutputStream();
         TransferHelper transferHelper = this.getInputStreamForSource(source, fileNames);
         BufferedInputStream sourceStream = transferHelper.getStream();
         this.transferData(sourceStream, out, targetFileName);
         File fileToDelete = transferHelper.getSource();
         if (fileToDelete != null) {
            fileToDelete.delete();
         }

         connection.setRequestProperty("Content-Length", String.valueOf(out.size()));
         OutputStream httpOutputStream = connection.getOutputStream();
         out.writeTo(httpOutputStream);
         BufferedReader replyReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         sourceNameOnServer = replyReader.readLine();
         replyReader.close();
         return sourceNameOnServer;
      } catch (URISyntaxException var19) {
         throw new DeployerHelperException(var19.toString(), var19);
      } catch (IOException var20) {
         throw new DeployerHelperException(textFormatter.exceptionUploadingSource(fdsUrlString, applicationName, source), var20);
      }
   }

   public String uploadPlan(String adminUrlString, String user, String password, String fileName, String applicationName) throws DeployerHelperException {
      String sourceNameOnServer = null;
      String fdsUrlString = null;

      try {
         fdsUrlString = this.getDSSUrl(adminUrlString);
         HttpURLConnection connection = this.getDSSConnection(fdsUrlString);
         this.initConnection(user, password, applicationName, connection, "plan_upload");
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
      } catch (URISyntaxException var16) {
         throw new DeployerHelperException(var16.toString(), var16);
      } catch (IOException var17) {
         throw new DeployerHelperException(textFormatter.exceptionUploadingSource(fdsUrlString, applicationName, fileName), var17);
      }
   }

   public void setFormatted(boolean theFormatted) {
      this.formatted = theFormatted;
   }

   private void transferData(InputStream sourceStream, OutputStream out, String targetFileName) throws IOException {
      PrintStream outStream = new PrintStream(out);
      String streamSend = "-----------------------------7d01b33320494\r\n";
      streamSend = streamSend + "Content-Disposition: form-data; name=\"file\"; filename=\"" + targetFileName + "\"\r\n";
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

   private void initConnection(String user, String password, String applicationName, HttpURLConnection connection, String requestType) {
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setAllowUserInteraction(true);
      connection.setRequestProperty("wl_request_type", mimeEncode(requestType));
      connection.setRequestProperty("username", mimeEncode(user));
      connection.setRequestProperty("password", mimeEncode(password));
      if (applicationName != null) {
         connection.setRequestProperty("wl_upload_application_name", applicationName);
      }

      connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------7d01b33320494");
   }

   private String getDSSUrl(String adminUrlString) throws URISyntaxException {
      if (!adminUrlString.startsWith("http")) {
         URI url = new URI(adminUrlString);
         URI httpUrl = new URI(this.getHTTPProtocol(url.getScheme()), (String)null, url.getHost(), url.getPort(), (String)null, (String)null, (String)null);
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

   public void registerTaskCompletionNotificationListener(DeploymentTaskRuntimeMBean task) {
      try {
         Debug.assertion(task != null);
         this.taskCompletion = new TaskCompletionNotificationListener(task);
         this.mbeanServerConnection.addNotificationListener(task.getObjectName(), this.taskCompletion, new TaskCompletionNotificationFilter(), (Object)null);
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   public void waitForTaskCompletion(DeploymentTaskRuntimeMBean task, long timeout) {
      if (this.taskCompletion != null) {
         long t = timeout - System.currentTimeMillis();
         if (t > 0L) {
            this.taskCompletion.waitForTaskCompletion(t);
         }

      } else {
         task.waitForTaskCompletion(timeout);
      }
   }

   protected final void showDeploymentNotificationInformation(String taskId, DeploymentNotification n) {
      String event;
      if (this.formatted) {
         event = this.translateNotificationType(n.getPhase());
         if (n.isAppNotification()) {
            println(messageFormatter.showDeploymentNotification(taskId, event, n.getAppName(), n.getServerName()));
         }
      } else {
         event = n.getAppName();
         String server = n.getServerName();
         String msg = null;
         String moduleName = null;
         String currState;
         if (n.isModuleNotification()) {
            moduleName = n.getModuleName();
            currState = n.getCurrentState();
            String targetState = n.getTargetState();
            String trans = n.getTransition();
            if (trans.equals("end")) {
               msg = messageFormatter.successfulTransition(moduleName, currState, targetState, server);
            } else if (trans.equals("failed")) {
               msg = messageFormatter.failedTransition(moduleName, currState, targetState, server);
            }

            if (msg != null) {
               println(msg);
            }
         } else {
            currState = n.getPhase();
            println(messageFormatter.appNotification(event, server, currState));
         }
      }

   }

   private String translateNotificationType(String notification) {
      if ("activated".equals(notification)) {
         return messageFormatter.messageNotificationActivated();
      } else if ("activating".equals(notification)) {
         return messageFormatter.messageNotificationActivating();
      } else if ("deactivated".equals(notification)) {
         return messageFormatter.messageNotificationDeactivated();
      } else if ("deactivating".equals(notification)) {
         return messageFormatter.messageNotificationDeactivating();
      } else if ("prepared".equals(notification)) {
         return messageFormatter.messageNotificationPrepared();
      } else if ("preparing".equals(notification)) {
         return messageFormatter.messageNotificationPreparing();
      } else if ("unprepared".equals(notification)) {
         return messageFormatter.messageNotificationUnprepared();
      } else if ("unpreparing".equals(notification)) {
         return messageFormatter.messageNotificationUnpreparing();
      } else if ("distributing".equals(notification)) {
         return messageFormatter.messageNotificationDistributing();
      } else if ("distributed".equals(notification)) {
         return messageFormatter.messageNotificationDistributed();
      } else {
         return "failed".equals(notification) ? messageFormatter.messageNotificationFailed() : notification;
      }
   }

   private static void println(String msg) {
      System.out.println(msg);
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
