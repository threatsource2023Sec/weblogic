package weblogic.ldap;

import com.octetstring.vde.LDAPServer;
import com.octetstring.vde.schema.InitSchema;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Properties;
import javax.inject.Named;
import javax.mail.internet.MimeUtility;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.EmbeddedLDAPMBean;
import weblogic.management.configuration.ServerDebugMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.utils.ConnectionSigner;
import weblogic.management.utils.ManagementHelper;
import weblogic.protocol.URLManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.AbstractServerService;
import weblogic.server.RemoteLifeCycleOperations;
import weblogic.server.ServerLifeCycleRuntime;
import weblogic.server.ServiceFailureException;
import weblogic.utils.annotation.Secure;

@Service
@Named
@RunLevel(10)
@Secure
public final class PreEmbeddedLDAPService extends AbstractServerService {
   private static final DebugLogger log = EmbeddedLDAPServiceLogger.getDebugLogger();
   private static int MAX_ATTEMPTS = 3;
   private static boolean isDBMSOnly = false;
   private EmbeddedLDAPServiceLogger srvLog;
   private LDAPServer ldapServer;
   private Logger logger;
   private ServerMBean serverMBean;
   private EmbeddedLDAPMBean embeddedLDAPMBean;
   private ServerDebugMBean debugMBean;
   private int numReplicas = 0;
   private Properties replicaProps = null;
   private static boolean replEnabled = false;
   private boolean invalidReplica = false;
   private boolean debugEnabled = false;
   private PreparedLdapData pData;
   private String configDirPath = null;
   private boolean isVDEDircreated = false;
   private String initialReplicaFile = null;
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static String[] confFiles = new String[]{"schema.core.xml", "adaptertypes.prop", "adapters.prop", "acls.prop"};
   public static boolean isPerfDebug = false;
   private static PreEmbeddedLDAPService singleton;

   public PreEmbeddedLDAPService() {
      setSingleton(this);
   }

   private static void setSingleton(PreEmbeddedLDAPService one) {
      singleton = one;
   }

   static PreEmbeddedLDAPService getSingleton() {
      return singleton;
   }

   public void start() throws ServiceFailureException {
      long startTime = 0L;
      if (isPerfDebug) {
         startTime = System.currentTimeMillis();
      }

      RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNELID);
      this.serverMBean = ra.getServer();
      isDBMSOnly = !SecurityServiceManager.isEmbeddedLdapNeeded(KERNELID);
      this.debugMBean = this.serverMBean.getServerDebug();
      this.debugEnabled = this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAP();
      if (this.debugEnabled) {
         log.debug("Initialize service for EmbeddedLDAP");
      }

      this.embeddedLDAPMBean = ra.getDomain().getEmbeddedLDAP();
      if (this.embeddedLDAPMBean != null) {
         String cred = this.embeddedLDAPMBean.getCredential();
         if (cred != null && cred.length() != 0) {
            String vdeDir = EmbeddedLDAP.getEmbeddedLDAPDir();
            this.isVDEDircreated = this.validateVDEDirectories(vdeDir);
            if (!ra.isAdminServer() && ra.isAdminServerAvailable()) {
               boolean refresh = this.isVDEDircreated || this.invalidReplica || this.embeddedLDAPMBean.isRefreshReplicaAtStartup();
               this.initialReplicaFile = this.getInitialReplicaFromAdminServer(refresh, !this.isVDEDircreated);
            }

            this.validateVDEConfigFiles(vdeDir);
            this.ensureExclusiveAccess(vdeDir);
            this.initServerConfig(vdeDir);
            this.srvLog = new EmbeddedLDAPServiceLogger();
            this.logger = this.srvLog.getLogger();
            this.ldapServer = new LDAPServer();
            MuxableSocketLDAP.initialize(this.ldapServer);
            MuxableSocketLDAPS.initialize(this.ldapServer);
            this.logger.log(5, this.ldapServer, "VDE Engine Starting");
            long runTime;
            long currentTime;
            if (isPerfDebug) {
               currentTime = System.currentTimeMillis();
               runTime = currentTime - startTime;
               dbgPsr("1st stage start = " + runTime);
            }

            (new InitSchema()).init();
            this.pData = new PreparedLdapData();
            if (isPerfDebug) {
               currentTime = System.currentTimeMillis();
               runTime = currentTime - startTime;
               dbgPsr("PreEmbeddedLDAPService start = " + runTime);
            }

         } else {
            throw new ServiceFailureException(EmbeddedLDAPLogger.getCredUnavailable());
         }
      }
   }

   public void stop() {
   }

   public void halt() {
   }

   public static boolean isAdmin() {
      return replEnabled;
   }

   public static void setReplicaInvalid() {
      try {
         PrintWriter out = new PrintWriter(new FileWriter(EmbeddedLDAP.getEmbeddedLDAPDir() + File.separator + "ldapfiles" + File.separator + "replica.invalid", false));
         out.println("# Replica set invalid");
         out.close();
      } catch (IOException var1) {
         log.debug("Got I/O error writing invalid replica file", var1);
      }

   }

   public static void cleanupInvalidReplica() {
      File invalidReplica = new File(EmbeddedLDAP.getEmbeddedLDAPDir() + File.separator + "ldapfiles" + File.separator + "replica.invalid");
      invalidReplica.delete();
   }

   public boolean isDebugEnabled() {
      return this.debugEnabled;
   }

   private void validateVDEConfigFiles(String vdeDir) {
      String home = BootStrap.getWebLogicHome();
      if (home == null) {
         throw new EmbeddedLDAPException("weblogic.home must be set");
      } else {
         File configDirFile = new File(home, "lib");
         this.configDirPath = configDirFile.getAbsolutePath() + File.separator;
         if (this.debugEnabled) {
            log.debug("Using configuration directory of " + this.configDirPath);
         }

         boolean fnd = true;

         for(int i = 0; i < confFiles.length; ++i) {
            File confFile = new File(configDirFile, confFiles[i]);
            if (!confFile.exists()) {
               fnd = false;

               String fileName;
               try {
                  fileName = confFile.getCanonicalPath();
               } catch (IOException var9) {
                  if (this.debugEnabled) {
                     log.debug("Error checking file " + confFiles[i], var9);
                  }

                  fileName = confFile.getAbsolutePath();
               }

               EmbeddedLDAPLogger.logConfigFileNotFound(fileName);
            }
         }

         if (!fnd) {
            throw new EmbeddedLDAPException("Could not find configuration files - see log file for more information");
         }
      }
   }

   private void ensureExclusiveAccess(String vdeDir) throws ServiceFailureException {
      if (!Boolean.getBoolean("weblogic.ldap.skipExclusiveAccessCheck")) {
         ;
      }
   }

   private boolean validateVDEDirectories(String vdeDir) throws ServiceFailureException {
      boolean created = false;
      File dataDir = new File(vdeDir, "ldapfiles");
      if (!dataDir.exists()) {
         dataDir.mkdirs();
         created = true;
      }

      File replicaDir = new File(vdeDir, "replicadata");
      if (!replicaDir.exists()) {
         replicaDir.mkdirs();
      }

      File logDir = new File(vdeDir, "log");
      if (!logDir.exists()) {
         logDir.mkdirs();
      }

      File backupDir = new File(vdeDir, "backup");
      if (!backupDir.exists()) {
         backupDir.mkdirs();
      }

      File confDir = new File(vdeDir, "conf");
      if (!confDir.exists()) {
         confDir.mkdirs();
      }

      if (this.debugEnabled) {
         log.debug("Creating directories and initial files");
      }

      try {
         File vdeProp = new File(confDir, "vde.prop");
         if (!vdeProp.exists()) {
            PrintWriter out = new PrintWriter(new FileWriter(vdeProp));
            out.println("vde.server.threads=5");
            out.println("vde.schemacheck=1");
            out.println("vde.aclcheck=1");
            out.println("vde.logfile=log/EmbeddedLDAP.log");
            out.println("vde.logrotate.hour=0");
            out.println("vde.logrotate.minute=10");
            out.println("vde.logrotate.maxlogs=7");
            out.println("vde.accesslogfile=log/EmbeddedLDAPAccess.log");
            out.println("vde.logconsole=0");
            out.println("vde.changelog.suffix=cn=changelog");
            out.println("vde.changelog.file=ldapfiles/changelog");
            out.println("vde.replicas=conf/replicas.prop");
            out.println("vde.tls.keystore=notused");
            out.println("vde.tls.pass=notused");
            out.println("vde.quota.max.connections=1800");
            out.println("vde.quota.max.opspercon=0");
            int maxConPerSubject = 100;

            try {
               String poolSizeProp = System.getProperty("weblogic.security.providers.authentication.LDAPDelegatePoolSize");
               if (poolSizeProp != null && poolSizeProp.length() > 0) {
                  maxConPerSubject = Integer.parseInt(poolSizeProp);
               }
            } catch (Exception var14) {
            }

            out.println("vde.quota.max.conpersubject=" + maxConPerSubject);
            out.println("vde.quota.max.conperip=0");
            out.println("vde.quota.period=30000");
            out.println("vde.quota.exemptips=");
            out.println("vde.quota.exemptusers=");
            out.println("vde.quota.check=1");
            out.close();
         }

         File mappingCfg = new File(confDir, "mapping.cfg");
         if (!mappingCfg.exists()) {
            mappingCfg.createNewFile();
         }

         this.replicaProps = new Properties();
         File replicasProp = new File(confDir, "replicas.prop");
         if (!replicasProp.exists()) {
            PrintWriter out = new PrintWriter(new FileWriter(replicasProp));
            out.println("replica.num=0");
            out.close();
            this.replicaProps.setProperty("replica.num", Integer.toString(this.numReplicas));
         } else if (ManagementService.getRuntimeAccess(KERNELID).isAdminServer()) {
            try {
               FileInputStream is = new FileInputStream(replicasProp);
               this.replicaProps.load(is);
               is.close();
               String numReplicasStr = this.replicaProps.getProperty("replica.num");
               this.numReplicas = Integer.parseInt(numReplicasStr);
            } catch (Exception var13) {
               ServiceFailureException sfe = new ServiceFailureException("Error reading replicas property file, the file may be corrupted - original Exception: " + var13.getClass().getName() + " with message: " + var13.getMessage());
               sfe.setStackTrace(var13.getStackTrace());
               throw sfe;
            }
         }

         File invalidRepFile = new File(dataDir, "replica.invalid");
         if (invalidRepFile.exists()) {
            this.invalidReplica = true;
         }

         return created;
      } catch (IOException var15) {
         throw new ServiceFailureException("Error creating configuration files", var15);
      }
   }

   private ServerConfig initServerConfig(String vdeDir) throws ServiceFailureException {
      System.setProperty("vde.home", vdeDir);
      if (this.debugEnabled) {
         log.debug("Setting vde.home to " + vdeDir);
      }

      ServerConfig config = ServerConfig.getInstance();

      try {
         config.init();
      } catch (IOException var6) {
         throw new ServiceFailureException("Error initializing VDE ", var6);
      }

      config.setProperty("vde.server.name", this.serverMBean.getName());

      try {
         String url = EmbeddedLDAP.findLocalLdapURL();
         if (url == null) {
            throw new ServiceFailureException("Null VDE URL");
         }

         URI uri = new URI(url);
         if (uri == null || uri.getHost() == null) {
            throw new ServiceFailureException("INVALID VDE URL");
         }

         config.setProperty("vde.server.listenaddr", uri.getHost());
         config.setProperty("vde.server.port", Integer.toString(uri.getPort()));
      } catch (URISyntaxException var7) {
         throw new ServiceFailureException(var7);
      }

      config.setProperty("vde.rootuser", "cn=Admin");
      config.setProperty("vde.allow.anonymous", this.embeddedLDAPMBean.isAnonymousBindAllowed() ? "true" : "false");
      boolean logToConsole = this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAPLogToConsole();
      config.setProperty("vde.logconsole", logToConsole ? "1" : "0");
      int logLevel = this.debugMBean != null ? this.debugMBean.getDebugEmbeddedLDAPLogLevel() : 0;
      config.setProperty("vde.debug", Integer.toString(logLevel));
      config.setProperty("vde.tls", "0");
      replEnabled = ManagementService.getRuntimeAccess(KERNELID).isAdminServer();
      config.setProperty("vde.changelog", replEnabled ? "1" : "0");
      config.setProperty("vde.schema.std", this.configDirPath + "schema.core.xml");
      config.setProperty("vde.backendtypes", this.configDirPath + "adaptertypes.prop");
      config.setProperty("vde.server.backends", this.configDirPath + "adapters.prop");
      config.setProperty("vde.aclfile", this.configDirPath + "acls.prop");
      if (this.debugEnabled) {
         EmbeddedLDAP.debugLogProperties("VDE configuration properties", config);
      }

      String credential = this.embeddedLDAPMBean.getCredential();
      config.setProperty("vde.rootpw", credential);
      if (this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAPWriteOverrideProps()) {
         EmbeddedLDAP.debugWriteProperties(EmbeddedLDAP.getEmbeddedLDAPDir() + File.separator + "conf" + File.separator + "vde.prop", config);
      }

      return config;
   }

   private String getInitialReplicaFromAdminServer(boolean alwaysRefresh, boolean deleteData) {
      URL url = null;
      InputStream is = null;
      OutputStream os = null;
      String ldifFileName = null;

      try {
         url = ManagementHelper.getURL();
      } catch (MalformedURLException var28) {
         setReplicaInvalid();
         throw new EmbeddedLDAPException("Unable to build initial replica url", var28);
      }

      String localurl = EmbeddedLDAP.findLocalLdapURL();
      if (localurl == null) {
         setReplicaInvalid();
         throw new EmbeddedLDAPException("Unable to get local addressing information");
      } else {
         HttpURLConnection connection = null;
         int tryAttempts = 1;
         boolean finished = false;

         try {
            Object var13;
            try {
               while(true) {
                  if (finished || tryAttempts > MAX_ATTEMPTS) {
                     if (!finished && tryAttempts >= MAX_ATTEMPTS) {
                        throw new IOException("Multiple failures to read VDE replica, network issues suspected");
                     }
                     break;
                  }

                  try {
                     connection = URLManager.createAdminHttpConnection(url, true);
                     ConnectionSigner.signConnection(connection, KERNELID);
                     connection.setRequestProperty("wl_request_type", "wl_init_replica_request");
                     connection.setRequestProperty("init-replica_server-name", EmbeddedLDAP.mimeEncode(ManagementService.getRuntimeAccess(KERNELID).getServerName()));
                     connection.setRequestProperty("init-replica_server-url", localurl);
                     if (!alwaysRefresh) {
                        connection.setRequestProperty("init-replica-validate", String.valueOf(alwaysRefresh));
                     }

                     connection.setRequestProperty("Connection", "Close");
                     is = connection.getInputStream();
                     byte[] buf = new byte[4096];
                     int read = is.read(buf, 0, 4096);
                     if (read == -1) {
                        if (!alwaysRefresh) {
                           var13 = null;
                           return (String)var13;
                        }

                        if (!isDBMSOnly) {
                           throw new EmbeddedLDAPException("Empty initial replica");
                        }

                        if (this.debugEnabled) {
                           this.logger.log(7, this.ldapServer, "Received empty replica file for EmbeddedLDAP");
                        }

                        finished = true;
                        var13 = null;
                        return (String)var13;
                     }

                     ldifFileName = EmbeddedLDAP.getEmbeddedLDAPDir() + File.separator + ManagementService.getRuntimeAccess(KERNELID).getServerName() + ".ldif";
                     os = new FileOutputStream(ldifFileName);

                     do {
                        os.write(buf, 0, read);
                     } while((read = is.read(buf, 0, 4096)) != -1);

                     finished = true;
                  } catch (SocketTimeoutException var29) {
                     if (this.debugEnabled) {
                        log.debug("PAR: PreEmbeddedLDAPService.getInitialReplicaFromAdminServer(); SocketTimeoutException on attempt " + tryAttempts);
                     }

                     ++tryAttempts;
                  } catch (IOException var30) {
                     if (this.debugEnabled) {
                        log.debug("PAR: PreEmbeddedLDAPService.getInitialReplicaFromAdminServer(); IOException on attempt " + tryAttempts);
                     }

                     ++tryAttempts;
                  }
               }
            } catch (IOException var31) {
               String ignoreReplicaError = System.getProperty("weblogic.ldap.ignoreInitialReplicaDownloadError");
               if (ignoreReplicaError != null && ignoreReplicaError.equalsIgnoreCase("false")) {
                  setReplicaInvalid();
                  throw new EmbeddedLDAPException("Unable to open initial replica url: " + url, var31);
               }

               if (this.debugEnabled) {
                  log.debug("Unable to open initial replica url: " + url, var31);
               }

               EmbeddedLDAPLogger.logUnableToDownloadReplica(var31.toString());
               var13 = null;
               return (String)var13;
            }
         } finally {
            try {
               if (is != null) {
                  is.close();
               }

               if (os != null) {
                  os.close();
               }

               if (connection != null) {
                  connection.disconnect();
               }
            } catch (Exception var27) {
            }

         }

         if (deleteData) {
            cleanupDataDirectory(false);
         }

         return ldifFileName;
      }
   }

   public static void sendInvalidToManagedServer(String ms) {
      URL url = null;
      final RemoteLifeCycleOperations rlc = ServerLifeCycleRuntime.getLifeCycleOperationsRemote(ms);
      final String serverName = ms;

      try {
         SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedExceptionAction() {
            public Object run() {
               try {
                  rlc.setInvalid(serverName);
                  return null;
               } catch (RemoteException var2) {
                  throw new EmbeddedLDAPException(EmbeddedLDAPLogger.logCannotMarkReplicaInvalid(serverName), var2);
               }
            }
         });
      } catch (PrivilegedActionException var5) {
         throw new EmbeddedLDAPException(EmbeddedLDAPLogger.logCannotMarkReplicaInvalid(ms), var5);
      }
   }

   private static void cleanupDataDirectory(boolean deleteDirectory) {
      File dataDir = new File(EmbeddedLDAP.getEmbeddedLDAPDir(), "ldapfiles");
      if (dataDir.exists()) {
         File[] files = dataDir.listFiles();

         for(int i = 0; i < files.length; ++i) {
            File dataFile = files[i];
            if (!dataFile.delete()) {
               dataFile.deleteOnExit();
               EmbeddedLDAPLogger.logCouldNotDeleteOnCleanup(dataFile.getAbsolutePath(), dataDir.getAbsolutePath());
            }
         }

         if (deleteDirectory && !dataDir.delete()) {
            dataDir.deleteOnExit();
            EmbeddedLDAPLogger.logCouldNotDeleteOnCleanup(dataDir.getAbsolutePath(), dataDir.getAbsolutePath());
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

   public PreparedLdapData getPreData(AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      return this.pData;
   }

   public static final void dbgPsr(String msg) {
      System.out.println(msg);
   }

   class PreparedLdapData {
      boolean isVDEDirCreated() {
         return PreEmbeddedLDAPService.this.isVDEDircreated;
      }

      String getInitReplicaFile() {
         return PreEmbeddedLDAPService.this.initialReplicaFile;
      }

      LDAPServer getLDAPServer() {
         return PreEmbeddedLDAPService.this.ldapServer;
      }

      EmbeddedLDAPServiceLogger getServiceLogger() {
         return PreEmbeddedLDAPService.this.srvLog;
      }

      ServerMBean getServerMBean() {
         return PreEmbeddedLDAPService.this.serverMBean;
      }

      EmbeddedLDAPMBean getEmbeddedLDAPMBean() {
         return PreEmbeddedLDAPService.this.embeddedLDAPMBean;
      }

      boolean isDBOnly() {
         return PreEmbeddedLDAPService.isDBMSOnly;
      }

      int getNumReplicas() {
         return PreEmbeddedLDAPService.this.numReplicas;
      }

      Properties getReplicaProps() {
         return PreEmbeddedLDAPService.this.replicaProps;
      }
   }
}
