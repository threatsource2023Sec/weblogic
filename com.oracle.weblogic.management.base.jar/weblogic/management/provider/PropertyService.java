package weblogic.management.provider;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnknownHostException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.DomainDir;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.internal.SecurityHelper;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.URLManagerService;
import weblogic.protocol.configuration.ChannelHelperService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.ServerAuthenticate;
import weblogic.security.internal.ServerPropertyNameService;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.annotation.Secure;
import weblogic.utils.net.InetAddressHelper;

@Service
@Named
@Secure
@RunLevel(
   value = 5,
   mode = 0
)
public class PropertyService extends AbstractServerService implements ServerPropertyNameService {
   public static final String ADMIN_ANONYMOUSADMINLOOKUPENABLED_PROP = "weblogic.management.anonymousAdminLookupEnabled";
   public static final String ADMIN_CLEAR_TEXT_CREDENTIAL_ACCESS_ENABLED = "weblogic.management.clearTextCredentialAccessEnabled";
   public static final String ADMIN_IACACHETTL_PROP = "weblogic.security.identityAssertionTTL";
   public static final String ADMIN_SSLENFORCECONSTRAINT_PROP = "weblogic.security.SSL.enforceConstraints";
   public static final String ADMIN_SSLTRUSTCA_PROP = "weblogic.security.SSL.trustedCAKeyStore";
   public static final String ADMIN_SSL_MINIMUM_PROTOCOL_VERSION_PROP = "weblogic.security.SSL.minimumProtocolVersion";
   public static final String ADMIN_SSLVERSION_PROP = "weblogic.security.SSL.protocolVersion";
   public static final String JAVAEE_PERMISSIONS_DISABLED_PROP = "weblogic.security.dd.javaEESecurityPermissionsDisabled";
   public static final String SECURITY_FW_DELEGATE_CLASS_NAME_PROP = "weblogic.security.SecurityServiceManagerDelegate";
   public static final String SECURITY_FW_SUBJECT_MANAGER_CLASS_NAME_PROP = "weblogic.security.SubjectManager";
   public static final String ADMIN_HOST_PROP = "weblogic.management.server";
   public static final String ADMIN_USERNAME_PROP = "weblogic.management.username";
   public static final String ADMIN_PASSWORD_PROP = "weblogic.management.password";
   public static final String ADMIN_IDENTITYDOMAIN_PROP = "weblogic.management.IdentityDomain";
   public static final String LEGAL_BYPASS_ON_PARSING_PROP = "weblogic.mbeanLegalClause.ByPass";
   public static final String MBEAN_AUDITING_ENABLED_PROP = "weblogic.AdministrationMBeanAuditingEnabled";
   public static final String OLD_ADMIN_HOST_PROP = "weblogic.admin.host";
   public static final String ADMIN_PKPASSWORD_PROP = "weblogic.management.pkpassword";
   public static final String ADMIN_HIERARCHY_GROUP_PROP = "weblogic.security.hierarchyGroupMemberships";
   public static final String LDAP_DELEGATE_POOL_SIZE_PROP = "weblogic.security.providers.authentication.LDAPDelegatePoolSize";
   public static final String ADMIN_AUDITLOG_DIR = "weblogic.security.audit.auditLogDir";
   public static final String JMX_REMOTE_REQUEST_TIMEOUT = "weblogic.management.jmx.remote.requestTimeout";
   public static final String CONVERT_SECURITY_EXTENSION_SCHEMA = "weblogic.management.convertSecurityExtensionSchema";
   @Inject
   private Provider runtimeAccess;
   private String adminHost = null;
   private String serverName = "myserver";
   boolean serverNameIsSet = false;
   private boolean isChannelServiceReady = false;
   private String userName = null;
   private String password = null;
   private String idd = null;
   private String pkpassword = null;
   private URL adminURL;
   private String adminBinaryURL = null;
   private boolean isAdminServer = false;
   private boolean securityInitialized = false;

   private PropertyService() {
   }

   private void checkSecurityInitialized() {
      if (!this.securityInitialized) {
         if (!this.serverNameIsSet) {
            throw new AssertionError("Security required before it is initialized");
         }

         this.initializeSecurityProperties(false);
      }

   }

   public void stop() throws ServiceFailureException {
   }

   public void halt() throws ServiceFailureException {
   }

   public void start() throws ServiceFailureException {
      this.initializeServerName();
      this.initializeAdminHost();
      if (!this.serverNameIsSet && !this.isAdminServer()) {
         this.serverName = "myserver";
         this.serverNameIsSet = true;
      }

   }

   /** @deprecated */
   @Deprecated
   static PropertyService getPropertyService(AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      return PropertyService.PropertyServiceInitializer.singleton;
   }

   public final String getTimestamp1() {
      this.checkSecurityInitialized();
      return this.userName;
   }

   public final String getTimestamp2() {
      this.checkSecurityInitialized();
      return this.password;
   }

   public final String getTimestamp3() {
      this.checkSecurityInitialized();
      return this.pkpassword;
   }

   public void updateTimestamp5(String x) {
      this.password = x;
   }

   public void updateTimestamp6(String x) {
      this.userName = x;
   }

   public void updateTimestamp3() {
      this.pkpassword = null;
   }

   public final String getIdentityDomain() {
      this.checkSecurityInitialized();
      return this.idd;
   }

   public void initializeSecurityProperties(boolean domainCreate) {
      if (!this.securityInitialized) {
         String[] args = null;
         if (domainCreate) {
            args = new String[]{"domainCreation"};
         }

         ServerAuthenticate.main(args);
         this.userName = System.getProperty("weblogic.management.username", "guest");
         this.password = System.getProperty("weblogic.management.password", "guest");
         this.idd = System.getProperty("weblogic.management.IdentityDomain");
         this.pkpassword = System.getProperty("weblogic.management.pkpassword");
         Properties props = System.getProperties();
         props.remove("weblogic.management.username");
         props.remove("weblogic.management.password");
         props.remove("weblogic.management.IdentityDomain");
         props.remove("weblogic.management.pkpassword");
         this.securityInitialized = true;
      }
   }

   public void establishServerBootIdentity(StartupConfig.ValuesHolder startup) {
      this.checkSecurityInitialized();
      ClearOrEncryptedService ces = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());
      if (this.userName != null && this.userName.length() != 0) {
         startup.setUsername(ces.encrypt(this.userName));
      }

      if (this.password != null && this.password.length() != 0) {
         startup.setPassword(ces.encrypt(this.password));
      }

   }

   public synchronized void setAdminHost(String newHost) throws MalformedURLException {
      int idx = newHost.indexOf("://");
      if (idx != -1) {
         this.adminHost = newHost.substring(0, idx).toLowerCase(Locale.US) + newHost.substring(idx);
      } else {
         this.adminHost = newHost;
      }

      this.adminBinaryURL = null;
      this.adminURL = new URL(this.getAdminHttpUrl());
      this.isAdminServer = this.adminHost == null;
   }

   public synchronized String getAdminHost() {
      return this.adminHost;
   }

   public synchronized void setChannelServiceReady() {
      this.isChannelServiceReady = true;
      this.notifyAll();
   }

   public synchronized void waitForChannelServiceReady() {
      if (!this.isChannelServiceReady) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }

      }
   }

   private static ChannelHelperService getChannelHelperService() {
      return (ChannelHelperService)GlobalServiceLocator.getServiceLocator().getService(ChannelHelperService.class, new Annotation[0]);
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public final synchronized String getAdminHttpUrl() {
      String result = null;

      try {
         if (this.isChannelServiceReady) {
            String name = ((RuntimeAccess)this.runtimeAccess.get()).getAdminServerName();
            Protocol adminProtocol = null;
            Protocol httpProtocol = null;
            Protocol httpsProtocol = null;
            Iterator iter = ProtocolManager.iterator();

            while(iter.hasNext()) {
               Protocol protocol = (Protocol)iter.next();
               switch (protocol.toByte()) {
                  case 1:
                     httpProtocol = protocol;
                     break;
                  case 3:
                     httpsProtocol = protocol;
                     break;
                  case 6:
                     adminProtocol = protocol;
               }
            }

            String adminUrl = null;
            String httpUrl = null;
            String httpsUrl = null;
            if (adminProtocol != null) {
               adminUrl = getURLManagerService().findURL(name, adminProtocol);
            }

            if (httpProtocol != null) {
               httpUrl = getURLManagerService().findURL(name, httpProtocol);
            }

            if (httpsProtocol != null) {
               httpsUrl = getURLManagerService().findURL(name, httpsProtocol);
            }

            result = getURLManagerService().findAdministrationURL(name);
            if (adminUrl != null) {
               result = adminUrl;
            } else if (httpUrl != null) {
               result = httpUrl;
            } else if (httpsUrl != null) {
               result = httpsUrl;
            }
         }
      } catch (UnknownHostException var10) {
      }

      if (result == null) {
         result = this.getAdminHost();
      }

      if (result == null && this.isAdminServer()) {
         result = getChannelHelperService().getURL(ProtocolHandlerAdmin.PROTOCOL_ADMIN);
         if (result == null) {
            result = getChannelHelperService().getURL(((ProtocolHandler)GlobalServiceLocator.getServiceLocator().getService(ProtocolHandler.class, "http", new Annotation[0])).getProtocol());
         }

         if (result == null) {
            throw new AssertionError("Can not extract host name of the adminstration server from JVMID");
         }
      }

      if (result == null) {
         throw new AssertionError("Could not determine admin url");
      } else {
         result = InetAddressHelper.convertIfIPV6URL(result);
         return getURLManagerService().normalizeToHttpProtocol(result);
      }
   }

   public final synchronized String[] getAllAdminHttpUrls() {
      String httpurl = this.getAdminHttpUrl();

      try {
         URL u = new URL(httpurl);
         String protocol = u.getProtocol();
         String host = u.getHost();
         int port = u.getPort();
         InetAddress[] addr = InetAddress.getAllByName(host);
         if (addr != null && addr.length != 0) {
            String[] result = new String[addr.length];

            for(int i = 0; i < result.length; ++i) {
               result[i] = (new URL(protocol, addr[i].getHostAddress(), port, "")).toString();
            }

            return result;
         } else {
            return stringToArray(httpurl);
         }
      } catch (MalformedURLException var9) {
         return stringToArray(httpurl);
      } catch (java.net.UnknownHostException var10) {
         return stringToArray(httpurl);
      }
   }

   private static String[] stringToArray(String s) {
      if (s == null) {
         return null;
      } else {
         String[] result = new String[]{s};
         return result;
      }
   }

   public String getAdminBinaryURL() {
      if (this.adminBinaryURL != null) {
         return this.adminBinaryURL;
      } else {
         this.adminBinaryURL = this.adminHost;
         if (this.adminBinaryURL == null) {
            return null;
         } else {
            this.adminBinaryURL = getURLManagerService().normalizeToAdminProtocol(this.adminBinaryURL);
            return this.adminBinaryURL;
         }
      }
   }

   private String initializeAdminHost() {
      if (this.adminHost != null) {
         return this.adminHost;
      } else {
         String tempHost = System.getProperty("weblogic.management.server");
         if (tempHost == null) {
            tempHost = System.getProperty("weblogic.admin.host");
         }

         if (tempHost != null) {
            try {
               this.setAdminHost(tempHost);
            } catch (MalformedURLException var3) {
            }
         } else {
            this.isAdminServer = true;
         }

         return this.adminHost;
      }
   }

   private void initializeServerName() {
      String tmp = BootStrap.getServerName();
      if (tmp != null) {
         this.serverName = tmp;
         this.serverNameIsSet = true;
      } else {
         String configFileName = BootStrap.getConfigFileName();
         File newConfigFile = new File(DomainDir.getConfigDir(), BootStrap.getDefaultConfigFileName());
         File oldConfigFile = new File(DomainDir.getRootDir(), configFileName);
         if (!oldConfigFile.exists() && !newConfigFile.exists()) {
            this.serverName = "myserver";
            this.serverNameIsSet = true;
         }
      }

   }

   public URL getAdminURL() {
      return this.adminURL;
   }

   public final String getServerName() {
      if (!this.serverNameIsSet) {
         throw new AssertionError("Server has not yet been established.");
      } else {
         return this.serverName;
      }
   }

   public boolean isAdminServer() {
      return this.isAdminServer;
   }

   public void doPostParseInitialization(DomainMBean domain) {
      if (!this.serverNameIsSet && this.isAdminServer()) {
         ServerMBean[] server = domain.getServers();
         if (server != null && server.length == 1) {
            this.serverName = server[0].getName();
            this.serverNameIsSet = true;
         }

         if (!this.serverNameIsSet) {
            String adminServerName = domain.getAdminServerName();
            if (adminServerName != null && adminServerName.length() >= 0) {
               this.serverName = adminServerName;
               this.serverNameIsSet = true;
            }
         }
      }

      if (!this.serverNameIsSet) {
         this.serverName = "myserver";
         this.serverNameIsSet = true;
      }

   }

   public boolean serverNameIsSet() {
      return this.serverNameIsSet;
   }

   public void disableSecurityInitialization() {
      this.securityInitialized = true;
   }

   public static void main(String[] args) throws Exception {
      PropertyService p = new PropertyService();
      p.setAdminHost(args[0]);
      String[] urls = p.getAllAdminHttpUrls();

      for(int i = 0; i < urls.length; ++i) {
         System.out.println(urls[i]);
      }

   }

   private static class PropertyServiceInitializer {
      private static final PropertyService singleton = (PropertyService)LocatorUtilities.getService(PropertyService.class);
   }
}
