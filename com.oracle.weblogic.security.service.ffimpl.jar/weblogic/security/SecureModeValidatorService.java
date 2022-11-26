package weblogic.security;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.SecureModeMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.UnixMachineMBean;
import weblogic.management.internal.DefaultJMXPolicyManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.audit.AuditorMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.GroupReaderMBean;
import weblogic.management.security.authentication.PasswordValidatorMBean;
import weblogic.management.security.authentication.UserLockoutManagerMBean;
import weblogic.management.security.authentication.UserReaderMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.providers.audit.DefaultAuditorMBean;
import weblogic.security.service.ConsumptionException;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SSLConfigChecker;
import weblogic.security.shared.LoggerWrapper;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.annotation.Secure;

@Service
@Named
@Rank(-100)
@RunLevel(20)
@Secure
public final class SecureModeValidatorService extends AbstractServerService implements BeanUpdateListener {
   @Inject
   @Named("EnableListenersService")
   private ServerService enableListenersServerService;
   private SecurityConfigurationMBean secMbean = null;
   private static final long DEFAULT_LOCKOUT_TH = 5L;
   private static final long DEFAULT_LOCKOUT_DURATION_TH = 30L;
   private static final String SECURE_AUDITING_LEVEL_ERROR = "ERROR";
   private static final String SECURE_AUDITING_LEVEL_SUCCESS = "SUCCESS";
   private static final String SECURE_AUDITING_LEVEL_CUSTOM = "CUSTOM";
   private static final String SECURE_AUDITING_LEVEL_FAILURE = "FAILURE";
   private static final String CONFIG_FILE = "config.xml";
   private static final String NM_DIR = "nodemanager";
   private static final String NM_PASSWORD_FILE = "nm_password.properties";
   private static final String CONFIG_ARCHIVE_DIR = "configArchive";
   private static final String SAMPLES_DIR = "samples";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityService");
   private static final RuntimeAccess runtimeAccess = (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);

   public SecureModeValidatorService() {
      if (this.isDebugEnabled()) {
         log.debug("SecureModeValidatorService init");
      }

      if (runtimeAccess != null) {
         runtimeAccess.getDomain().getSecurityConfiguration().getSecureMode().addBeanUpdateListener(this);
      }

   }

   public void start() throws ServiceFailureException {
      this.secMbean = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration();
      this.validate();
      if (this.isDebugEnabled()) {
         log.debug("finished starting SecureModeValidatorService");
      }

   }

   public void stop() throws ServiceFailureException {
      if (this.isDebugEnabled()) {
         log.debug("SecureModeValidatorService stop");
      }

   }

   public void halt() throws ServiceFailureException {
      if (this.isDebugEnabled()) {
         log.debug("SecureModeValidatorService halt");
      }

   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

         for(int i = 0; i < updates.length; ++i) {
            String propertyName = updates[i].getPropertyName();
            if (propertyName.equals("RestrictiveJMXPolicies")) {
               try {
                  DefaultJMXPolicyManager.reset();
               } catch (ConsumptionException var6) {
                  throw new RuntimeException(var6);
               }
            }
         }

      }
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   private boolean isDebugEnabled() {
      return log != null ? log.isDebugEnabled() : false;
   }

   private void validate() {
      SecureModeMBean secureMode = this.secMbean.getSecureMode();
      if (secureMode.isSecureModeEnabled()) {
         RealmMBean[] realms = this.secMbean.getRealms();
         RealmMBean[] var3 = realms;
         int var4 = realms.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            RealmMBean realm = var3[var5];
            if (secureMode.isWarnOnAuditing()) {
               this.validateAuditConfig(realm);
            }

            this.validateUserPasswordConfig(realm);
            this.validateUserLockoutConfig(realm);
         }

         if (secureMode.isWarnOnJavaSecurityManager()) {
            this.checkJavaSecurityManager();
         }

         if (secureMode.isWarnOnInsecureFileSystem()) {
            this.validateFileSystem();
         }

         this.validatePortsConfig();
         if (secureMode.isWarnOnInsecureSSL()) {
            SSLMBean sslmBean = runtimeAccess.getServer().getSSL();
            NetworkAccessPointMBean[] networkAccessPointMBeans = runtimeAccess.getServer().getNetworkAccessPoints();
            SSLConfigChecker sslConfigChecker = new SSLConfigChecker(sslmBean, networkAccessPointMBeans);
            sslConfigChecker.checkAndLog();
         }

         this.validateUsernames();
         this.validateSamples();
      }

   }

   private boolean validateAuditConfig(RealmMBean realm) {
      String realmName = realm.getName();
      AuditorMBean[] auditors = realm.getAuditors();
      if (auditors != null && auditors.length != 0) {
         boolean severityQualified = true;
         AuditorMBean[] var5 = auditors;
         int var6 = auditors.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            AuditorMBean auditor = var5[var7];
            if (auditor instanceof DefaultAuditorMBean) {
               DefaultAuditorMBean defAud = (DefaultAuditorMBean)auditor;
               String severity = defAud.getSeverity();
               if (severity.equals("ERROR") || severity.equals("SUCCESS") || severity.equals("FAILURE") || severity.equals("CUSTOM") && !defAud.getWarningAuditSeverityEnabled() || !defAud.getErrorAuditSeverityEnabled() || !defAud.getFailureAuditSeverityEnabled()) {
                  severityQualified = false;
                  SecurityLogger.logAuditingLevelInappropriateInSecureMode(realmName);
               }
               break;
            }
         }

         return severityQualified;
      } else {
         SecurityLogger.logAuditingNotEnabledInSecureMode(realmName);
         return false;
      }
   }

   private boolean validateUserPasswordConfig(RealmMBean realm) {
      boolean valid = true;
      String cmdPasswd = System.getProperty("weblogic.management.password");
      if (cmdPasswd != null && !cmdPasswd.trim().isEmpty()) {
         valid = false;
         SecurityLogger.logUnEncryptedPasswdInCommandLine();
      }

      PasswordValidatorMBean[] passwdValidators = realm.getPasswordValidators();
      if (passwdValidators == null || passwdValidators.length == 0) {
         String realmName = realm.getName();
         valid = false;
         SecurityLogger.logNoPasswordValidatorInSecureMode(realmName);
      }

      return valid;
   }

   private boolean validateUserLockoutConfig(RealmMBean realm) {
      UserLockoutManagerMBean lockoutMgr = realm.getUserLockoutManager();
      boolean lockoutEnabled = lockoutMgr.isLockoutEnabled();
      long lockoutTH = lockoutMgr.getLockoutThreshold();
      long durationTH = lockoutMgr.getLockoutDuration();
      if (lockoutEnabled && lockoutTH <= 5L && durationTH >= 30L) {
         return true;
      } else {
         String realmName = realm.getName();
         SecurityLogger.logLockoutSettingNotSecureInSecureMode(realmName);
         return false;
      }
   }

   private boolean checkJavaSecurityManager() {
      if (System.getSecurityManager() == null) {
         SecurityLogger.logSecurityManagerNotEnabledInSecureMode();
         return false;
      } else {
         return true;
      }
   }

   private boolean validatePortsConfig() {
      boolean portsValid = true;
      DomainMBean domain = (DomainMBean)((DomainMBean)this.secMbean.getParent());
      ServerMBean[] servers = domain.getServers();
      ServerMBean[] var4 = servers;
      int var5 = servers.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ServerMBean server = var4[var6];
         MachineMBean machine = server.getMachine();
         if (machine instanceof UnixMachineMBean) {
            UnixMachineMBean uMachine = (UnixMachineMBean)machine;
            if (!uMachine.isPostBindUIDEnabled() || !uMachine.isPostBindGIDEnabled()) {
               portsValid = this.validateServerPorts(uMachine.getName(), server);
            }
         }
      }

      if (!domain.isAdministrationPortEnabled()) {
         portsValid = false;
         SecurityLogger.logAdministrationPortNotEnabledInSecureMode();
      }

      return portsValid;
   }

   private boolean validateServerPorts(String machineName, ServerMBean server) {
      boolean portsValid = true;
      if (server.isListenPortEnabled() && server.getListenPort() < 1024) {
         portsValid = false;
         SecurityLogger.logUnixMachinePostBindNotEnabled(machineName, server.getListenPort());
      }

      if (server.isAdministrationPortEnabled() && server.getAdministrationPort() < 1024) {
         portsValid = false;
         SecurityLogger.logUnixMachinePostBindNotEnabled(machineName, server.getAdministrationPort());
      }

      SSLMBean ssl = server.getSSL();
      if (ssl.isEnabled() && ssl.getListenPort() < 1024) {
         portsValid = false;
         SecurityLogger.logUnixMachinePostBindNotEnabled(machineName, ssl.getListenPort());
      }

      NetworkAccessPointMBean[] naps = server.getNetworkAccessPoints();
      NetworkAccessPointMBean[] var6 = naps;
      int var7 = naps.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         NetworkAccessPointMBean nap = var6[var8];
         if (nap.isEnabled()) {
            int listenPort = nap.getListenPort();
            int publicPort = nap.getPublicPort();
            if (listenPort != -1 && listenPort < 1024) {
               portsValid = false;
               SecurityLogger.logUnixMachinePostBindNotEnabled(machineName, listenPort);
            }

            if (publicPort != -1 && publicPort < 1024) {
               portsValid = false;
               SecurityLogger.logUnixMachinePostBindNotEnabled(machineName, publicPort);
            }
         }
      }

      return portsValid;
   }

   private void validateFileSystem() {
      if (FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
         try {
            String rootDir = DomainDir.getRootDir();
            String[] filesToCheck = new String[]{DomainDir.getConfigDir() + File.separator + "config.xml", DomainDir.getConfigDir() + File.separator + "nodemanager" + File.separator + "nm_password.properties"};
            List dirsToCheck = new ArrayList(Arrays.asList(DomainDir.getBinDir(), DomainDir.getDiagnosticsDir(), DomainDir.getJDBCDir(), DomainDir.getJMSDir(), rootDir + File.separator + "configArchive", rootDir + File.separator + "nodemanager", DomainDir.getSecurityDir(), DomainDir.getPartitionsDir()));
            DomainMBean domain = (DomainMBean)((DomainMBean)this.secMbean.getParent());
            ServerMBean[] servers = domain.getServers();
            ServerMBean[] var6 = servers;
            int var7 = servers.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               ServerMBean server = var6[var8];
               String serverName = server.getName();
               dirsToCheck.add(DomainDir.getBinDirForServer(serverName));
               dirsToCheck.add(DomainDir.getLDAPDataDirForServer(serverName));
               dirsToCheck.add(DomainDir.getStoreDataDirForServer(serverName));
               dirsToCheck.add(DomainDir.getSecurityDirForServer(serverName));
               dirsToCheck.add(DomainDir.getLogsDirForServer(serverName));
            }

            Path rootPath = Paths.get(rootDir);
            UserPrincipal owner = Files.getOwner(rootPath, LinkOption.NOFOLLOW_LINKS);
            String[] var15 = filesToCheck;
            int var17 = filesToCheck.length;

            for(int var19 = 0; var19 < var17; ++var19) {
               String file = var15[var19];
               if (Files.exists(Paths.get(file), new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                  this.validateFileSecurity(file, owner);
               }
            }

            Iterator var16 = dirsToCheck.iterator();

            while(var16.hasNext()) {
               String dir = (String)var16.next();
               this.validateDirectorySecurity(dir, owner);
            }
         } catch (Exception var12) {
            if (this.isDebugEnabled()) {
               log.debug("SecureModeValidatorService exception validating file system " + var12.getClass() + " cause " + var12.getCause());
            }
         }
      }

   }

   private void validateFileSecurity(String file, UserPrincipal owner) throws Exception {
      Path fileValidate = Paths.get(file);

      try {
         UserPrincipal fOwner = Files.getOwner(fileValidate, LinkOption.NOFOLLOW_LINKS);
         if (!fOwner.equals(owner)) {
            SecurityLogger.logFileOwnerInsecureSecureMode(file, fOwner.getName(), owner.getName());
         }

         Set sp = Files.getPosixFilePermissions(fileValidate, LinkOption.NOFOLLOW_LINKS);
         if (sp != null && (sp.contains(PosixFilePermission.GROUP_WRITE) || sp.contains(PosixFilePermission.OTHERS_READ) || sp.contains(PosixFilePermission.OTHERS_WRITE) || sp.contains(PosixFilePermission.OTHERS_EXECUTE))) {
            SecurityLogger.logFilePermissionInsecureSecureMode(file);
         }
      } catch (Exception var6) {
         if (this.isDebugEnabled()) {
            log.debug("SecureModeValidatorService exception validating file security " + file + " exception " + var6.getClass() + " cause " + var6.getCause());
         }
      }

   }

   private void validateDirectorySecurity(String dirToValidate, final UserPrincipal owner) throws Exception {
      Path vDir = Paths.get(dirToValidate);
      if (Files.exists(vDir, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
         try {
            Files.walkFileTree(vDir, new SimpleFileVisitor() {
               public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                  try {
                     SecureModeValidatorService.this.validateFileSecurity(file.toString(), owner);
                  } catch (Exception var4) {
                     if (SecureModeValidatorService.this.isDebugEnabled()) {
                        SecureModeValidatorService.log.debug("SecureModeValidatorService exception validating file security " + file.toString() + " exception " + var4.getClass() + " cause " + var4.getCause());
                     }
                  }

                  return FileVisitResult.CONTINUE;
               }

               public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                  try {
                     SecureModeValidatorService.this.validateFileSecurity(dir.toString(), owner);
                  } catch (Exception var4) {
                     if (SecureModeValidatorService.this.isDebugEnabled()) {
                        SecureModeValidatorService.log.debug("SecureModeValidatorService exception validating directory security " + dir.toString() + " exception " + var4.getClass() + " cause " + var4.getCause());
                     }
                  }

                  return FileVisitResult.CONTINUE;
               }
            });
         } catch (Exception var5) {
            throw var5;
         }
      }

   }

   private void validateUsernames() {
      try {
         String[] usernamesToCheck = new String[]{"weblogic", "admin", "administrator", "system"};
         RealmMBean[] var2 = this.secMbean.getRealms();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            RealmMBean realm = var2[var4];
            AuthenticationProviderMBean[] var6 = realm.getAuthenticationProviders();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               AuthenticationProviderMBean atn = var6[var8];
               if (atn instanceof UserReaderMBean && atn instanceof GroupReaderMBean) {
                  UserReaderMBean userReader = (UserReaderMBean)atn;
                  GroupReaderMBean groupReader = (GroupReaderMBean)atn;
                  String[] var12 = usernamesToCheck;
                  int var13 = usernamesToCheck.length;

                  for(int var14 = 0; var14 < var13; ++var14) {
                     String user = var12[var14];
                     if (userReader.userExists(user) && groupReader.isMember("Administrators", user, true)) {
                        SecurityLogger.logAdminUserInsecureName(user);
                     }
                  }
               }
            }
         }
      } catch (Exception var16) {
         if (this.isDebugEnabled()) {
            log.debug("SecureModeValidatorService exception validating user names ", var16);
         }
      }

   }

   private void validateSamples() {
      try {
         String samplesDirStr = runtimeAccess.getServerRuntime().getWeblogicHome() + File.separator + "samples";
         File samplesDir = new File(samplesDirStr);
         if (samplesDir.exists() && samplesDir.isDirectory()) {
            SecurityLogger.logSamplesInstalledInSecureMode();
         }
      } catch (Exception var3) {
         if (this.isDebugEnabled()) {
            log.debug("SecureModeValidatorService exception validating samples directory ", var3);
         }
      }

   }
}
