package weblogic.management.scripting;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import weblogic.management.configuration.CoherenceServerMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.scripting.core.NodeManagerCoreService;
import weblogic.management.scripting.utils.ErrorInformation;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.nodemanager.server.NMEncryptionHelper;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.utils.FileUtils;

public class NodeManagerService extends NodeManagerCoreService implements NMConstants {
   WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;
   private static final String SALT_FILE = "security/SerializedSystemIni.dat";
   private static final String SERVICEMIGRATION = "bin/service_migration";
   private static final String SERVERMIGRATION = "bin/server_migration";
   private static final String NM_USER_FILE_NAME = "nm_password.properties";
   private static final String HTTP_STRING = "http";
   private static final String HTTPS_STRING = "https";
   private static final String T3S_STRING = "t3s";
   private static final String IIOPS_STRING = "iiops";

   public NodeManagerService(WLScriptContext ctx) {
      super(ctx);
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   String decrypt(Object obj, String domainDir) throws ScriptException {
      try {
         EncryptionService es = null;
         ClearOrEncryptedService ces = null;
         if (domainDir != null) {
            File f = new File(domainDir);
            this.ctx.printDebug("Setting the root directory to " + f.getAbsolutePath());
            es = SerializedSystemIni.getEncryptionService(f.getAbsolutePath());
         } else {
            es = SerializedSystemIni.getExistingEncryptionService();
         }

         if (es == null) {
            this.ctx.errorMsg = this.txtFmt.getErrorInitializingEncryptionService();
            this.ctx.errorInfo = new ErrorInformation(this.ctx.errorMsg);
            this.ctx.exceptionHandler.handleException(this.ctx.errorInfo);
         }

         ces = new ClearOrEncryptedService(es);
         return obj instanceof String ? ces.decrypt((String)obj) : new String(ces.decryptBytes((byte[])((byte[])obj)));
      } catch (Throwable var6) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorEncryptingValue(), var6);
         return null;
      }
   }

   public void nmEnrollMachine(String domainDir, String nmHome) throws ScriptException {
      Class var3 = NodeManagerService.class;
      synchronized(NodeManagerService.class) {
         try {
            this.ctx.commandType = "nmEnroll";
            if (domainDir == null) {
               domainDir = ".";
            }

            File domainDirFile = new File(domainDir);
            this.ctx.println(this.txtFmt.getEnrollingMachineInDomain(domainDir));
            File saltFile = new File(domainDir + "/" + "security/SerializedSystemIni.dat");
            if (!saltFile.exists()) {
               this.ctx.println(this.txtFmt.getEnrollSaltDownloadNotSupported("security/SerializedSystemIni.dat"));
               this.ctx.throwWLSTException(this.txtFmt.getFailedToEnrolMachineInDomain());
            }

            this.downloadRequiredFiles(domainDirFile.getAbsolutePath());
            String username = this.ctx.runtimeDomainMBean.getSecurityConfiguration().getNodeManagerUsername();
            byte[] encryptedPassword = this.ctx.runtimeDomainMBean.getSecurityConfiguration().getNodeManagerPasswordEncrypted();
            String password = this.decrypt(encryptedPassword, domainDir);
            if (password == null) {
               password = "";
            }

            this.ctx.printDebug("The username and pwd are " + username + "   ****");
            Properties props = new Properties();
            String hash = NMEncryptionHelper.getNMSecretHash(domainDir, username, password);
            if (hash == null) {
               hash = "";
            }

            props.setProperty("hashed", hash);
            File propsFile = new File(domainDirFile.getAbsolutePath() + "/config/nodemanager/" + "nm_password.properties");
            propsFile.getParentFile().mkdirs();
            if (propsFile.exists()) {
               this.ctx.printDebug("Found an existing properties file, will delete it");
               FileUtils.remove(propsFile);
            }

            FileOutputStream os = new FileOutputStream(propsFile);
            props.store(os, "");
            os.close();
            File home = null;
            if (nmHome == null) {
               nmHome = domainDirFile.getAbsolutePath() + "/nodemanager";
            }

            this.ctx.printDebug("NMHome is " + nmHome);
            File nmDomains = new File(nmHome + "/nodemanager.domains");
            Properties nmDomainProps = new Properties();
            String domainName = this.ctx.runtimeDomainMBean.getName();
            if (nmDomains.exists()) {
               this.ctx.printDebug("nodemanager.domains file exists, the new domain will be added");
               FileInputStream fis = new FileInputStream(nmDomains);
               nmDomainProps.load(fis);
               nmDomainProps.put(domainName, domainDirFile.getAbsolutePath());
               FileOutputStream fos = new FileOutputStream(nmDomains);
               nmDomainProps.store(fos, "");
               fos.close();
            } else {
               this.ctx.printDebug("creating a new nodemanager.domains, the new domain will be added");
               nmDomainProps.put(domainName, domainDirFile.getAbsolutePath());
               nmDomains.getParentFile().mkdirs();
               FileOutputStream fos = new FileOutputStream(nmDomains);
               this.ctx.printDebug("The file will be written to " + nmDomains.getAbsolutePath());
               nmDomainProps.store(fos, "");
               fos.close();
            }

            this.ctx.println(this.txtFmt.getEnrolledMachineInDomain(domainDirFile.getAbsolutePath()));
         } catch (IOException var20) {
            this.ctx.throwWLSTException(this.txtFmt.getFailedToEnrolMachineInDomain(), var20);
         }

      }
   }

   private void downloadRequiredFiles(String domainDir) throws ScriptException {
      this.downloadFile(domainDir, "bin/service_migration");
      this.downloadFile(domainDir, "bin/server_migration");
   }

   private void initFDSConnection(String user, String password, String filePath, HttpURLConnection connection) {
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setAllowUserInteraction(true);
      connection.setRequestProperty("wl_request_type", "wl_managed_server_independence_request");
      connection.setRequestProperty("username", user);
      connection.setRequestProperty("password", password);
      if (filePath != null) {
         connection.setRequestProperty("wl_managed_server_independence_request_filename", filePath);
      }

   }

   private String getFDSUrl(String adminUrlString) throws URISyntaxException {
      if (!adminUrlString.startsWith("http")) {
         URI url = new URI(adminUrlString);
         URI httpUrl = new URI(this.getHTTPProtocol(url.getScheme()), (String)null, url.getHost(), url.getPort(), (String)null, (String)null, (String)null);
         adminUrlString = httpUrl.toString();
      }

      if (!adminUrlString.endsWith("/")) {
         adminUrlString = adminUrlString + "/";
      }

      return adminUrlString + "bea_wls_management_internal2/wl_management";
   }

   private String getHTTPProtocol(String scheme) {
      return !scheme.equals("https") && !scheme.equals("t3s") && !scheme.equals("iiops") ? "http" : "https";
   }

   private HttpURLConnection getFDSConnection(String fdsUrlString) throws IOException {
      URL fdsUrl = new URL(fdsUrlString);
      return (HttpURLConnection)fdsUrl.openConnection();
   }

   private void downloadFile(String domainDir, String fileToDownload) throws ScriptException {
      String fdsUrlString = null;

      try {
         fdsUrlString = this.getFDSUrl(this.ctx.url);
         HttpURLConnection connection = this.getFDSConnection(fdsUrlString);
         this.initFDSConnection(new String(this.ctx.encodeUserAndIDDBytes(this.ctx.username_bytes, this.ctx.idd_bytes)), new String(this.ctx.password_bytes), fileToDownload, connection);
         connection.connect();
         this.ctx.printDebug("Downloading the file " + fileToDownload);
         InputStream fileObject = null;

         try {
            fileObject = connection.getInputStream();
         } catch (FileNotFoundException var12) {
            this.ctx.printDebug("Error downloading the file " + fileToDownload);
            return;
         }

         File file = new File(domainDir + "/" + fileToDownload);
         if (!"bin/service_migration".equalsIgnoreCase(fileToDownload) && !"bin/server_migration".equalsIgnoreCase(fileToDownload)) {
            if (!file.exists()) {
               FileUtils.writeToFile(fileObject, file);
               this.ctx.printDebug("downloaded the file " + fileToDownload + " successfully");
            } else if (file.isFile()) {
               File tmpFile = File.createTempFile("nmservice", "tmp");
               tmpFile.deleteOnExit();
               FileUtils.writeToFile(fileObject, tmpFile);
               long oldFileCRC = FileUtils.computeCRC(file);
               long newFileCRC = FileUtils.computeCRC(tmpFile);
               if (oldFileCRC != newFileCRC) {
                  FileUtils.copy(tmpFile, file);
                  this.ctx.printDebug("downloaded the file " + fileToDownload + " successfully");
               }
            }
         } else {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(fileObject));
            Object retObject = in.readObject();
            if (retObject instanceof File[]) {
               file.mkdir();
               File[] fis = (File[])((File[])retObject);

               for(int i = 0; i < fis.length; ++i) {
                  this.downloadFile(domainDir, fileToDownload + "/" + fis[i].getName());
               }
            }
         }

         return;
      } catch (StreamCorruptedException var13) {
      } catch (Throwable var14) {
         this.ctx.throwWLSTException(this.txtFmt.getProblemEnrollingMachine(), var14);
      }

   }

   public void nmGenBootStartupProps(String serverName) throws ScriptException {
      this.ctx.commandType = "nmGenBootStartupProps";
      if (serverName == null || serverName.equals("")) {
         this.ctx.throwWLSTException(this.txtFmt.getNullOrEmptyServerName());
      }

      ServerMBean smb = this.ctx.runtimeServiceMBean.getDomainConfiguration().lookupServer(serverName);
      CoherenceServerMBean csmb = null;
      if (smb == null) {
         csmb = this.ctx.runtimeServiceMBean.getDomainConfiguration().lookupCoherenceServer(serverName);
         if (csmb == null) {
            this.ctx.throwWLSTException(this.txtFmt.getNoServerOrCoherenceMBean(serverName));
         }
      }

      Properties bootProps = new Properties();
      Properties startupProps = new Properties();
      File nmDir = null;
      String rootDir;
      File serversDir;
      File dataDir;
      if (smb != null) {
         rootDir = smb.getServerStart().getRootDirectory();
         if (rootDir == null) {
            rootDir = this.ctx.runtimeServiceMBean.getDomainConfiguration().getRootDirectory();
         }

         bootProps = smb.getServerStart().getBootProperties();
         startupProps = smb.getServerStart().getStartupProperties();
         serversDir = new File(rootDir, "servers");
         dataDir = new File(serversDir, serverName + "/" + "data");
         nmDir = new File(dataDir, "nodemanager");
      } else if (csmb != null) {
         rootDir = csmb.getCoherenceServerStart().getRootDirectory();
         if (rootDir == null) {
            rootDir = this.ctx.runtimeServiceMBean.getDomainConfiguration().getRootDirectory();
         }

         bootProps = csmb.getCoherenceServerStart().getBootProperties();
         startupProps = csmb.getCoherenceServerStart().getStartupProperties();
         serversDir = new File(rootDir, "servers_coherence");
         dataDir = new File(serversDir, serverName + "/" + "data");
         nmDir = new File(dataDir, "nodemanager");
      }

      nmDir.mkdirs();

      FileOutputStream fos;
      File f;
      try {
         f = new File(nmDir, "boot.properties");
         fos = new FileOutputStream(f);
         bootProps.save(fos, (String)null);
         fos.flush();
         fos.close();
         this.ctx.println(this.txtFmt.getGeneratedBootProperties(f.getAbsolutePath()));
      } catch (IOException var11) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorSavingBootProperties(), var11);
      }

      try {
         f = new File(nmDir, "startup.properties");
         fos = new FileOutputStream(f);
         startupProps.save(fos, (String)null);
         fos.flush();
         fos.close();
         this.ctx.println(this.txtFmt.getGeneratedStartupProperties(f.getAbsolutePath()));
      } catch (IOException var10) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorSavingStartupProperties(), var10);
      }

   }

   byte[] getNMUser() {
      return this.nmCredential.isUsernameSet() ? this.nmCredential.getUsername().getBytes() : null;
   }

   byte[] getNMPwd() {
      return this.nmCredential.isPasswordSet() ? (new String(this.nmCredential.getPassword())).getBytes() : null;
   }
}
