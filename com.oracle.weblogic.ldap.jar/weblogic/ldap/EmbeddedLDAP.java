package weblogic.ldap;

import com.octetstring.vde.EntryChangesListener;
import com.octetstring.vde.ExternalExecutor;
import com.octetstring.vde.LDAPServer;
import com.octetstring.vde.WorkQueueItem;
import com.octetstring.vde.acl.ACLChecker;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.backend.standard.BackendStandard;
import com.octetstring.vde.replication.Consumer;
import com.octetstring.vde.replication.Replication;
import com.octetstring.vde.schema.InitSchema;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.EncryptionHelper;
import com.octetstring.vde.util.LDIF;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import com.octetstring.vde.util.TimedActivityThread;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.AccessController;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.TimeZone;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.internet.MimeUtility;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.EmbeddedLDAPMBean;
import weblogic.management.configuration.ServerDebugMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.AdminServerIdentity;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.ServerIdentityManager;
import weblogic.protocol.URLManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.utils.EmbeddedLDAPService;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.annotation.Secure;
import weblogic.utils.net.InetAddressHelper;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(10)
@Secure
public final class EmbeddedLDAP extends AbstractServerService implements ExternalExecutor, EmbeddedLDAPService {
   @Inject
   @Named("PreEmbeddedLDAPService")
   private PreEmbeddedLDAPService preEmbeddedLDAPService;
   public static final String EMBEDDED_LDAP_DIR_NAME = "ldap";
   public static final String ROOT_USER_NAME = "cn=Admin";
   public static final String VDE_BACKUP_DIR = "backup";
   public static final String VDE_CONF_DIR = "conf";
   public static final String VDE_LOG_DIR = "log";
   public static final String VDE_DATA_DIR = "ldapfiles";
   public static final String VDE_REPLICADATA_DIR = "replicadata";
   public static final String VDE_PROP_NAME = "vde.prop";
   public static final String VDE_MAPPING_NAME = "mapping.cfg";
   public static final String VDE_REPLICAS_NAME = "replicas.prop";
   public static final String VDE_INVALID_REPLICA_NAME = "replica.invalid";
   public static final String DOMAIN_SCHEMA_NAME = "dc=";
   public static final String VDE_SCHEMA_FILENAME = "schema.core.xml";
   public static final String VDE_BACKENDTYPES_FILENAME = "adaptertypes.prop";
   public static final String VDE_BACKEND_FILENAME = "adapters.prop";
   public static final String VDE_ACL_FILENAME = "acls.prop";
   public static final String VDE_PROPS_REPLICA = "replica.";
   public static final String EMBEDDED_LDAP = "EmbeddedLDAP";
   private static final int timerInterval = 60000;
   private static EmbeddedLDAP singleton = null;
   private static final DebugLogger log = EmbeddedLDAPServiceLogger.getDebugLogger();
   private static boolean masterLDAPUseSSL = false;
   private static String masterLDAPHost = null;
   private static int masterLDAPPort = 0;
   private static String masterLDAPURL;
   private static boolean isDBMSOnly = false;
   private int state = 1;
   private LDAPServer ldapServer;
   private Logger logger;
   private ServerMBean serverMBean;
   private EmbeddedLDAPMBean embeddedLDAPMBean;
   private ServerDebugMBean debugMBean;
   private int numReplicas = 0;
   private Properties replicaProps = null;
   private Replication replication;
   private EmbeddedLDAPTimedActivity timedActivity = null;
   private boolean debugEnabled = false;
   private boolean masterFirst = false;
   private int timeout = 0;
   private boolean keepAliveEnabled = false;
   private BackendHandler handler;
   private BackendStandard backend = null;
   private String configDirPath = null;
   private Timer pollerTimer = null;
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static String[] confFiles = new String[]{"schema.core.xml", "adaptertypes.prop", "adapters.prop", "acls.prop"};
   private boolean isPerfDebug;

   public EmbeddedLDAP() {
      this.isPerfDebug = PreEmbeddedLDAPService.isPerfDebug;
      singleton = this;
   }

   public static EmbeddedLDAP getEmbeddedLDAP() {
      return singleton;
   }

   public void start() throws ServiceFailureException {
      long startTime = 0L;
      if (this.isPerfDebug) {
         startTime = System.currentTimeMillis();
      }

      if (this.preEmbeddedLDAPService == null) {
         this.preEmbeddedLDAPService = PreEmbeddedLDAPService.getSingleton();
      }

      if (this.isPerfDebug) {
         long currentTime = System.currentTimeMillis();
         long runTime = currentTime - startTime;
         PreEmbeddedLDAPService.dbgPsr("PreEmbeddedLDAPService started = " + runTime);
      }

      RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNELID);
      PreEmbeddedLDAPService.PreparedLdapData pdata = this.preEmbeddedLDAPService.getPreData(KERNELID);
      this.serverMBean = pdata.getServerMBean();
      isDBMSOnly = pdata.isDBOnly();
      this.debugMBean = this.serverMBean.getServerDebug();
      this.debugEnabled = this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAP();
      this.embeddedLDAPMBean = pdata.getEmbeddedLDAPMBean();
      if (this.embeddedLDAPMBean != null) {
         String vdeDir = getEmbeddedLDAPDir();
         boolean created = pdata.isVDEDirCreated();
         this.numReplicas = pdata.getNumReplicas();
         this.replicaProps = pdata.getReplicaProps();
         String initialReplicaFile = pdata.getInitReplicaFile();
         this.ldapServer = pdata.getLDAPServer();
         this.logger = pdata.getServiceLogger().getLogger();
         ACLChecker.getInstance().initialize();
         Properties backendProps = new Properties();
         String domainName = "dc=" + ra.getDomain().getName();
         backendProps.setProperty("backend.0.root", domainName);
         backendProps.setProperty("backend.0.config.backup-hour", Integer.toString(this.embeddedLDAPMBean.getBackupHour()));
         backendProps.setProperty("backend.0.config.backup-minute", Integer.toString(this.embeddedLDAPMBean.getBackupMinute()));
         backendProps.setProperty("backend.0.config.backup-max", Integer.toString(this.embeddedLDAPMBean.getBackupCopies()));
         if (this.debugEnabled) {
            debugLogProperties("VDE Backend properties: ", backendProps);
         }

         if (this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAPWriteOverrideProps()) {
            debugWriteProperties("./lib/adaptertypes.prop", backendProps);
         }

         this.handler = BackendHandler.getInstance(backendProps);

         try {
            this.backend = (BackendStandard)this.handler.getBackend(new DirectoryString(domainName));
         } catch (Exception var27) {
            if (!ra.isAdminServer()) {
               PreEmbeddedLDAPService.setReplicaInvalid();
               EmbeddedLDAPLogger.logErrorInitializingLDAPReplica(var27);
            } else {
               EmbeddedLDAPLogger.logErrorInitializingLDAPMaster(vdeDir + File.separator + "backup", var27);
            }

            throw new ServiceFailureException("Error initializing Embedded LDAP Server", var27);
         }

         try {
            this.timedActivity = new EmbeddedLDAPTimedActivity(this.backend);
            this.pollerTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this.timedActivity, 60000L);
         } catch (Exception var26) {
            EmbeddedLDAPLogger.logCouldNotScheduleTrigger(var26.toString());
         }

         if (initialReplicaFile != null) {
            this.loadInitialReplicaFile(initialReplicaFile);
         }

         URI uri = this.getMasterEmbeddedLDAPURI();
         if (uri != null) {
            masterLDAPUseSSL = uri.getScheme().equalsIgnoreCase("ldaps");
            masterLDAPHost = uri.getHost();
            masterLDAPPort = uri.getPort();
            masterLDAPURL = uri.toString();
         }

         this.masterFirst = this.embeddedLDAPMBean.isMasterFirst();
         this.timeout = this.embeddedLDAPMBean.getTimeout();
         this.keepAliveEnabled = this.embeddedLDAPMBean.isKeepAliveEnabled();
         this.updateReplicaProperties();
         if (!ra.isAdminServer() || this.numReplicas > 0) {
            this.initReplication();
         }

         if (this.debugEnabled) {
            log.debug("Start service for EmbeddedLDAP");
         }

         this.ldapServer.start();
         this.state = 2;
         if (isDBMSOnly && created && ra.isAdminServer()) {
            String domanin = domainName.substring(3);
            String ldif_min = "dn: " + domainName + "\nobjectclass: top\nobjectclass: domain\ndc: " + domanin + "\n\n";
            InputStream is = null;

            try {
               is = new ByteArrayInputStream(ldif_min.getBytes());
               (new LDIF()).importLDIF((String)null, is, true);
            } catch (Exception var24) {
               throw new ServiceFailureException("Error loading the min LDAP content forEmbeddedLDAP", var24);
            } finally {
               try {
                  is.close();
               } catch (Exception var23) {
               }

            }

            if (this.debugEnabled) {
               log.debug("Populated minimum domain content for EmbeddedLDAP");
            }
         }

         if (this.isPerfDebug) {
            long currentTime = System.currentTimeMillis();
            long runTime = currentTime - startTime;
            PreEmbeddedLDAPService.dbgPsr("EmbeddedLDAPService start = " + runTime);
         }

      }
   }

   public void stop() {
      LDAPExecuteRequest.waitForRequestsToComplete();
      this.shutdown();
   }

   public void halt() {
      this.shutdown();
   }

   private void shutdown() {
      this.state = 0;
      LDAPExecuteRequest.waitForRequestsToComplete();
      this.debugEnabled = this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAP();
      if (this.debugEnabled) {
         log.debug("Shutdown server for EmbeddedLDAP");
      }

      try {
         if (this.replication != null) {
            this.replication.shutdown();
         }

         if (this.backend != null) {
            this.backend.shutdown();
         }
      } catch (Exception var2) {
         if (this.debugEnabled) {
            log.debug("Exception shutting down " + var2);
         }
      }

   }

   public boolean isRunning() {
      return this.state == 2;
   }

   public synchronized String initReplicaForNewServer(String serverName, String url) {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNELID);
      if (!ra.isAdminServer()) {
         return null;
      } else {
         this.debugEnabled = this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAP();
         if (this.debugEnabled) {
            log.debug("init Replica for new server " + serverName + ", url " + url);
         }

         URI uri;
         try {
            uri = new URI(url);
         } catch (URISyntaxException var11) {
            throw new EmbeddedLDAPException("Invalid replica url: " + url);
         }

         ServerMBean replicaServerMBean = ra.getDomain().lookupServer(serverName);
         if (replicaServerMBean == null) {
            throw new EmbeddedLDAPException("Could not find server for Initial Replica: " + serverName);
         } else {
            int index = this.getReplicaIndex(serverName);
            if (index < 0) {
               index = this.numReplicas++;
            }

            boolean secure = uri.getScheme().equalsIgnoreCase("ldaps");
            String replica = "replica." + index;
            this.replicaProps.setProperty(replica + ".name", serverName);
            this.replicaProps.setProperty(replica + ".consumerid", serverName);
            this.replicaProps.setProperty(replica + ".base", "dc=" + ra.getDomainName());
            this.replicaProps.setProperty(replica + ".masterid", ra.getAdminServerName());
            this.replicaProps.setProperty(replica + ".masterurl", masterLDAPURL + "/");
            this.replicaProps.setProperty(replica + ".hostname", uri.getHost());
            this.replicaProps.setProperty(replica + ".port", Integer.toString(uri.getPort()));
            this.replicaProps.setProperty(replica + ".secure", secure ? "1" : "0");
            this.replicaProps.setProperty(replica + ".binddn", "cn=Admin");
            this.replicaProps.setProperty("replica.num", Integer.toString(this.numReplicas));
            this.writeReplicaProps();
            if (this.replication == null) {
               this.initReplication();
            } else {
               Consumer con = this.replication.getReplicaByName(serverName);
               if (con != null) {
                  con.setHostname(uri.getHost());
                  con.setPort(uri.getPort());
                  con.setSecure(secure);
               } else {
                  Hashtable raConfig = new Hashtable();
                  raConfig.put("name", serverName);
                  raConfig.put("consumerid", serverName);
                  raConfig.put("base", "dc=" + ra.getDomainName());
                  raConfig.put("masterid", ra.getAdminServerName());
                  raConfig.put("masterurl", masterLDAPURL + "/");
                  raConfig.put("hostname", uri.getHost());
                  raConfig.put("port", Integer.toString(uri.getPort()));
                  raConfig.put("secure", secure ? "1" : "0");
                  raConfig.put("binddn", "cn=Admin");
                  raConfig.put("bindpw", this.embeddedLDAPMBean.getCredential());
                  if (this.debugEnabled) {
                     log.debug("Adding replica for " + serverName);
                  }

                  this.replication.addReplica(raConfig);
               }
            }

            if (this.debugEnabled) {
               log.debug("Initializing VDE Replica for " + serverName);
            }

            String ldifFile = getEmbeddedLDAPDir() + File.separator + serverName + ".ldif";
            this.replication.setupAgreement(serverName, ldifFile);
            return ldifFile;
         }
      }
   }

   public synchronized boolean isValidReplica(String serverName, String url) {
      this.debugEnabled = this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAP();
      if (this.debugEnabled) {
         log.debug("validate replica status for server " + serverName + " url " + url);
      }

      if (this.replication != null && this.replication.getReplicaByName(serverName) != null) {
         int index = this.getReplicaIndex(serverName);
         if (index < 0) {
            return false;
         } else {
            String replicaProp = "replica." + index;
            String replicaHost = this.replicaProps.getProperty(replicaProp + ".hostname");
            String replicaPort = this.replicaProps.getProperty(replicaProp + ".port");

            try {
               URI uri = new URI(url);
               return uri.getHost().equals(replicaHost) && Integer.toString(uri.getPort()).equals(replicaPort);
            } catch (URISyntaxException var8) {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private int getReplicaIndex(String name) {
      for(int i = 0; i < this.numReplicas; ++i) {
         if (name.equals(this.replicaProps.getProperty("replica." + i + ".name"))) {
            return i;
         }
      }

      return -1;
   }

   public EmbeddedLDAPMBean getEmbeddedLDAPMBean() {
      return this.embeddedLDAPMBean;
   }

   public static boolean importLDIF(String vdeHome, String configDir, String domainName, String filename) {
      System.setProperty("vde.home", vdeHome);
      ServerConfig config = ServerConfig.getInstance();

      try {
         config.init();
      } catch (IOException var7) {
         return false;
      }

      config.setProperty("vde.server.name", "myserver");
      config.setProperty("vde.hostname", "localhost");
      config.setProperty("vde.server.port", "7003");
      config.setProperty("vde.rootuser", "cn=Admin");
      config.setProperty("vde.rootpw", "manager");
      config.setProperty("vde.logconsole", "0");
      config.setProperty("vde.tls", "0");
      config.setProperty("vde.changelog", "0");
      config.setProperty("vde.debug", "0");
      String configDirPath = BootStrap.getWebLogicHome() + "/lib/";
      config.setProperty("vde.schema.std", configDirPath + "schema.core.xml");
      config.setProperty("vde.backendtypes", configDirPath + "adaptertypes.prop");
      config.setProperty("vde.server.backends", configDirPath + "adapters.prop");
      config.setProperty("vde.aclfile", configDirPath + "acls.prop");
      (new InitSchema()).init();
      ACLChecker.getInstance().initialize();
      Properties backendProps = new Properties();
      backendProps.setProperty("backend.0.root", "dc=" + domainName);
      backendProps.setProperty("backend.0.config.backup-hour", "23");
      backendProps.setProperty("backend.0.config.backup-minute", "59");
      backendProps.setProperty("backend.0.config.backup-max", "7");
      BackendHandler.getInstance(backendProps);
      return (new LDIF()).importLDIF(filename, (InputStream)null, true);
   }

   public void execute(WorkQueueItem workQueueItem) {
      WorkManagerFactory.getInstance().getSystem().schedule(new LDAPExecuteRequest(workQueueItem));
   }

   public static String getEmbeddedLDAPDir() {
      return DomainDir.getLDAPDataDirForServer(ManagementService.getRuntimeAccess(KERNELID).getServerName());
   }

   public static String getEmbeddedLDAPDataDir() {
      return getEmbeddedLDAPDir();
   }

   public static String getEmbeddedLDAPHost() {
      return masterLDAPHost;
   }

   private URI getMasterEmbeddedLDAPURI() {
      String url = null;
      if (ManagementService.getRuntimeAccess(KERNELID).isAdminServerAvailable()) {
         url = findLdapURL(AdminServerIdentity.getBootstrapIdentity());
      } else {
         String adminHostName = ManagementService.getPropertyService(KERNELID).getAdminHost();
         adminHostName = InetAddressHelper.convertIfIPV6URL(adminHostName);
         url = URLManager.normalizeToLDAPProtocol(adminHostName);
      }

      if (url == null) {
         EmbeddedLDAPLogger.logCouldNotGetAdminListenAddress();
      } else {
         try {
            return new URI(url);
         } catch (URISyntaxException var3) {
            EmbeddedLDAPLogger.logInvalidAdminListenAddress(url);
         }
      }

      return null;
   }

   public static int getEmbeddedLDAPPort() {
      return masterLDAPPort;
   }

   public static String getEmbeddedLDAPDomain() {
      return ManagementService.getRuntimeAccess(KERNELID).getDomainName();
   }

   /** @deprecated */
   @Deprecated
   public static String getEmbeddedLDAPCredential() {
      SecurityServiceManager.checkKernelPermission();
      EmbeddedLDAPMBean masterEmbeddedLDAP = getEmbeddedLDAP().getEmbeddedLDAPMBean();
      return masterEmbeddedLDAP.getCredential();
   }

   public static String getEmbeddedLDAPCredential(AuthenticatedSubject as) {
      SecurityServiceManager.checkKernelIdentity(as);
      EmbeddedLDAPMBean masterEmbeddedLDAP = getEmbeddedLDAP().getEmbeddedLDAPMBean();
      return masterEmbeddedLDAP.getCredential();
   }

   public static boolean getEmbeddedLDAPUseSSL() {
      return masterLDAPUseSSL;
   }

   public void setReplicaInvalid() {
      PreEmbeddedLDAPService.setReplicaInvalid();
   }

   public void setPasswords2WayEncrypted() {
      this.backend.setPasswordExternalEncryptionHelper(new EncryptionHelperImpl(new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService())));
   }

   public boolean isDebugEnabled() {
      return this.debugEnabled;
   }

   public boolean isMasterFirst() {
      return this.masterFirst;
   }

   public int getTimeout() {
      return this.timeout;
   }

   public boolean isKeepAliveEnabled() {
      return this.keepAliveEnabled;
   }

   public void registerChangeListener(String changeBase, EmbeddedLDAPChangeListener listener) {
      if (this.handler == null) {
         throw new IllegalStateException("EmbeddedLDAP has not been initialized yet");
      } else {
         EntryChangesListener l = new EntryChangesListenerImpl(changeBase, listener);
         this.handler.registerEntryChangesListener(l);
      }
   }

   public void unregisterChangeListener(String changeBase, EmbeddedLDAPChangeListener listener) {
      if (this.handler == null) {
         throw new IllegalStateException("EmbeddedLDAP has not been initialized yet");
      } else {
         EntryChangesListener l = new EntryChangesListenerImpl(changeBase, listener);
         this.handler.unregisterEntryChangesListener(l);
      }
   }

   public static String getDateFormat(long time) {
      Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
      cal.setTime(new Date(time));
      int year = cal.get(1);
      int mo = cal.get(2);
      int day = cal.get(5);
      int hour = cal.get(11);
      int min = cal.get(12);
      String sMo = (mo < 10 ? "0" : "") + mo;
      String sDay = (day < 10 ? "0" : "") + day;
      String sHour = (hour < 10 ? "0" : "") + hour;
      String sMin = (min < 10 ? "0" : "") + min;
      return year + sMo + sDay + sHour + sMin + "Z";
   }

   private void updateReplicaProperties() {
      if (this.numReplicas != 0) {
         RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNELID);
         String masterId = ra.getAdminServerName();
         String masterURL = masterLDAPURL + "/";
         String base = "dc=" + ra.getDomainName();
         int j = 0;

         int i;
         String iReplica;
         for(i = 0; i < this.numReplicas; ++i) {
            iReplica = "replica." + i;
            String serverName = this.replicaProps.getProperty(iReplica + ".name");
            if (ra.getDomain().lookupServer(serverName) != null) {
               String jReplica = iReplica;
               if (i != j) {
                  jReplica = "replica." + j;
                  this.replicaProps.setProperty(jReplica + ".name", serverName);
                  this.replicaProps.setProperty(jReplica + ".consumerid", this.replicaProps.getProperty(iReplica + ".consumerid"));
                  this.replicaProps.setProperty(jReplica + ".hostname", this.replicaProps.getProperty(iReplica + ".hostname"));
                  this.replicaProps.setProperty(jReplica + ".port", this.replicaProps.getProperty(iReplica + ".port"));
                  this.replicaProps.setProperty(jReplica + ".secure", this.replicaProps.getProperty(iReplica + ".secure"));
               }

               this.replicaProps.setProperty(jReplica + ".masterid", masterId);
               this.replicaProps.setProperty(jReplica + ".masterurl", masterURL);
               this.replicaProps.setProperty(jReplica + ".base", base);
               this.replicaProps.setProperty(jReplica + ".binddn", "cn=Admin");
               ++j;
            }
         }

         for(i = j; i < this.numReplicas; ++i) {
            iReplica = "replica." + i;
            this.replicaProps.remove(iReplica + ".name");
            this.replicaProps.remove(iReplica + ".consumerid");
            this.replicaProps.remove(iReplica + ".hostname");
            this.replicaProps.remove(iReplica + ".port");
            this.replicaProps.remove(iReplica + ".secure");
            this.replicaProps.remove(iReplica + ".masterid");
            this.replicaProps.remove(iReplica + ".masterurl");
            this.replicaProps.remove(iReplica + ".base");
            this.replicaProps.remove(iReplica + ".binddn");
         }

         this.numReplicas = j;
         this.replicaProps.setProperty("replica.num", Integer.toString(this.numReplicas));
         this.writeReplicaProps();
      }
   }

   private void initReplication() {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNELID);
      Properties overrideProps = new Properties();
      String credential = this.embeddedLDAPMBean.getCredential();
      if (ra.isAdminServer()) {
         for(int i = 0; i < this.numReplicas; ++i) {
            overrideProps.setProperty("replica." + i + ".bindpw", credential);
         }
      } else {
         String replica = "replica.0";
         overrideProps.setProperty(replica + ".name", this.serverMBean.getName());
         overrideProps.setProperty(replica + ".consumerid", this.serverMBean.getName());
         overrideProps.setProperty(replica + ".base", "dc=" + ra.getDomainName());
         overrideProps.setProperty(replica + ".masterid", ra.getAdminServerName());
         overrideProps.setProperty(replica + ".masterurl", masterLDAPURL + "/");

         try {
            URI uri = this.findLdapURI(this.serverMBean);
            overrideProps.setProperty(replica + ".hostname", uri.getHost());
            overrideProps.setProperty(replica + ".port", Integer.toString(uri.getPort()));
            boolean secure = uri.getScheme().equalsIgnoreCase("ldaps");
            overrideProps.setProperty(replica + ".secure", secure ? "1" : "0");
         } catch (URISyntaxException var7) {
            throw new IllegalStateException("No master embedded LDAP server.");
         }

         overrideProps.setProperty(replica + ".binddn", "cn=Admin");
         overrideProps.setProperty(replica + ".bindpw", credential);
         overrideProps.setProperty("replica.num", "1");
      }

      if (this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAP()) {
         debugLogProperties("VDE Replica properties: ", this.replicaProps);
         debugLogProperties("VDE Override Replica properties: ", overrideProps);
      }

      if (this.debugMBean != null && this.debugMBean.getDebugEmbeddedLDAPWriteOverrideProps()) {
         debugWriteProperties(getEmbeddedLDAPDir() + File.separator + "conf" + File.separator + "replicas.prop", overrideProps);
      }

      this.replication = new Replication();
      this.replication.init(overrideProps, KERNELID.getSubject());
   }

   private void loadInitialReplicaFile(String replicaFile) {
      boolean success = false;

      try {
         if (this.debugEnabled) {
            log.debug("Loading initial replica file " + replicaFile);
         }

         PreEmbeddedLDAPService.setReplicaInvalid();
         boolean importSuccess = (new LDIF()).importLDIF(replicaFile, (InputStream)null, true);
         if (!importSuccess) {
            EmbeddedLDAPLogger.logReloadInitReplicaFile();
            boolean secondTry = (new LDIF()).importLDIF(replicaFile, (InputStream)null, true);
            if (!secondTry) {
               throw new EmbeddedLDAPException(EmbeddedLDAPLogger.getErrLoadInitReplicaFile());
            }

            EmbeddedLDAPLogger.logSuccessReloadInitReplicaFile();
         }

         success = true;
         PreEmbeddedLDAPService.cleanupInvalidReplica();
      } finally {
         if (!success) {
            this.cleanupDataDirectory(true);
         }

      }

   }

   private void cleanupDataDirectory(boolean deleteDirectory) {
      File dataDir = new File(getEmbeddedLDAPDir(), "ldapfiles");
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

   private URI findLdapURI(ServerMBean server) throws URISyntaxException {
      if (server.getName().equals(this.serverMBean.getName())) {
         return new URI(findLocalLdapURL());
      } else {
         ServerIdentity id = ServerIdentityManager.findServerIdentity(getEmbeddedLDAPDomain(), server.getName());
         if (id == null) {
            throw new URISyntaxException("<null>", "Unknown host");
         } else {
            String url = findLdapURL(id);
            if (url == null) {
               throw new URISyntaxException("<null>", "Null url");
            } else {
               return new URI(url);
            }
         }
      }
   }

   public static String findLocalLdapURL() {
      return findLdapURL(LocalServerIdentity.getIdentity());
   }

   public static String findLdapURL(ServerIdentity id) {
      ServerChannel sc = ServerChannelManager.findServerChannel(id, ProtocolHandlerAdmin.PROTOCOL_ADMIN);
      String url = null;
      if (sc == null || !sc.supportsTLS()) {
         url = URLManager.findURL(id, ProtocolHandlerLDAP.PROTOCOL_LDAP);
      }

      if (url == null) {
         url = URLManager.findURL(id, ProtocolHandlerLDAPS.PROTOCOL_LDAPS);
      }

      return url;
   }

   private void writeReplicaProps() {
      File propsFile = new File(getEmbeddedLDAPDir() + File.separator + "conf" + File.separator + "replicas.prop");

      try {
         this.replicaProps.save(new FileOutputStream(propsFile), "Generated property file");
      } catch (Exception var3) {
         EmbeddedLDAPLogger.logErrorWritingReplicasFile(propsFile.getAbsolutePath(), var3.toString());
      }

   }

   static void debugLogProperties(String hdrName, Properties props) {
      log.debug("Logging properties for " + hdrName);
      Iterator it = props.keySet().iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         log.debug("Property " + key + "=" + props.getProperty(key));
      }

   }

   static void debugWriteProperties(String propFileName, Properties props) {
      try {
         PrintWriter out = new PrintWriter(new FileWriter(propFileName, true));
         out.println("# Adding properties set at runtime");
         Iterator it = props.keySet().iterator();

         while(it.hasNext()) {
            String key = (String)it.next();
            out.println(key + "=" + props.getProperty(key));
         }

         out.close();
      } catch (IOException var5) {
         log.debug("Got I/O error writing override props", var5);
      }

   }

   static String mimeEncode(String orig) {
      String result = null;

      try {
         result = MimeUtility.encodeText(orig, "UTF-8", (String)null);
      } catch (UnsupportedEncodingException var3) {
         result = orig;
      }

      return result;
   }

   private static final class EncryptionHelperImpl implements EncryptionHelper {
      private ClearOrEncryptedService encrypter;

      public EncryptionHelperImpl(ClearOrEncryptedService encrypter) {
         this.encrypter = encrypter;
      }

      public String encrypt(String clearOrEncryptedString) {
         return this.encrypter.encrypt(clearOrEncryptedString);
      }

      public String decrypt(String clearOrEncryptedString) {
         return this.encrypter.decrypt(clearOrEncryptedString);
      }

      public boolean isEncrypted(String clearOrEncryptedString) {
         return this.encrypter.isEncrypted(clearOrEncryptedString);
      }
   }

   private static class EmbeddedLDAPTimedActivity implements TimerListener {
      BackendStandard backend;

      EmbeddedLDAPTimedActivity(BackendStandard backendStandard) {
         this.backend = backendStandard;
      }

      public void timerExpired(Timer timer) {
         TimedActivityThread.getInstance().runOnDemand();
         Logger.getInstance().flush();
         if (this.backend != null) {
            this.backend.cleanupPools();
         }

         TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, 60000L);
      }
   }
}
