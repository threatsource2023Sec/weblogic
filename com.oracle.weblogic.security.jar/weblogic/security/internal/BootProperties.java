package weblogic.security.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;
import weblogic.kernel.KernelTypeService;
import weblogic.management.DomainDir;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.internal.encryption.EncryptionServiceException;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.FileUtils;
import weblogic.utils.LocatorUtilities;

public final class BootProperties {
   private static final boolean DEBUG = false;
   private static final String USERNAME_PROP = "username";
   private static final String PASSWORD_PROP = "password";
   private static final String IDD_PROP = "IdentityDomain";
   private static final String FILE = "boot.properties";
   private static String DEFAULT_FILE = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static BootProperties theInstance = null;
   private ClearOrEncryptedService encryptionService = null;
   private String filename = null;
   private boolean needServerSecDir = false;
   private boolean useClear = false;
   private String username = null;
   private String password = null;
   private String idd = null;
   private String trustKeyStore = null;
   private String customTrustKeyStoreFileName = null;
   private String customTrustKeyStoreType = null;
   private String customTrustKeyStorePassPhrase = null;
   private String javaStandardTrustKeyStorePassPhrase = null;
   private String customIdentityKeyStoreFileName = null;
   private String customIdentityKeyStorePassPhrase = null;
   private String customIdentityAlias = null;
   private String customIdentityPrivateKeyPassPhrase = null;
   private String customIdentityKeyStoreType = null;

   static void upgradeBP(String specifiedBootPropertiesFile) {
      String serverName = getServerName();
      File newBP = new File(DomainDir.getPathRelativeServersSecurityDir(serverName, "boot.properties"));
      if (!newBP.exists()) {
         File oldBP = new File(DomainDir.getPathRelativeRootDir("boot.properties"));
         if (oldBP.exists()) {
            try {
               if (null != specifiedBootPropertiesFile) {
                  File propBP = new File(specifiedBootPropertiesFile);
                  if (propBP.getCanonicalPath().equals(oldBP.getCanonicalPath())) {
                     return;
                  }
               }

               if (theInstance != null) {
                  theInstance.updateDomainLevelBP(new File(theInstance.filename));
                  FileUtils.copy(oldBP, newBP);
               }
            } catch (IOException var5) {
            }

         }
      }
   }

   private void updateDomainLevelBP(File updatedFile) {
      File oldBP = new File(DomainDir.getPathRelativeRootDir("boot.properties"));
      if (oldBP.exists() && updatedFile.exists()) {
         try {
            if (updatedFile.getCanonicalPath().equals(oldBP.getCanonicalPath())) {
               return;
            }

            FileUtils.copy(updatedFile, oldBP);
         } catch (IOException var4) {
         }

      }
   }

   private void deleteDomainLevelBP() {
      File oldBP = new File(DomainDir.getPathRelativeRootDir("boot.properties"));
      if (oldBP.exists()) {
         oldBP.delete();
      }

   }

   public static String getServerName() {
      ServerPropertyNameService propertyNameService = (ServerPropertyNameService)AccessController.doPrivileged(new PrivilegedAction() {
         public ServerPropertyNameService run() {
            return (ServerPropertyNameService)LocatorUtilities.getService(ServerPropertyNameService.class);
         }
      });
      return propertyNameService == null ? null : propertyNameService.getServerName();
   }

   public static boolean exists(String filename) {
      String fname = getFileName(filename);
      File file = new File(fname);
      boolean rtn = file.exists();
      return rtn;
   }

   public static synchronized void load(String file, boolean clear) {
      if (theInstance == null) {
         try {
            BootProperties bootProps = new BootProperties(clear, file);
            if (bootProps.read()) {
               theInstance = bootProps;
            }
         } catch (Exception var3) {
            SecurityLogger.logBootPropertiesWarning(var3.toString());
         }

      }
   }

   public static synchronized void save() {
      try {
         if (theInstance != null && theInstance.haveUnencryptedValues()) {
            theInstance.write();
         }
      } catch (Exception var1) {
         SecurityLogger.logBootPropertiesWarning(var1.toString());
      }

   }

   public static synchronized void output(SecurityConfigurationMBean secMBean, String file, String username, String password, String trustKeyStore, String customTrustKeyStoreFileName, String customTrustKeyStoreType, String customTrustKeyStorePassPhrase, String javaStandardTrustKeyStorePassPhrase, String customIdentityKeyStoreFileName, String customIdentityKeyStoreType, String customIdentityKeyStorePassPhrase, String customIdentityAlias, String customIdentityPrivateKeyPassPhrase) {
      output(secMBean, file, username, password, (String)null, trustKeyStore, customTrustKeyStoreFileName, customTrustKeyStoreType, customTrustKeyStorePassPhrase, javaStandardTrustKeyStorePassPhrase, customIdentityKeyStoreFileName, customIdentityKeyStoreType, customIdentityKeyStorePassPhrase, customIdentityAlias, customIdentityPrivateKeyPassPhrase);
   }

   public static synchronized void output(SecurityConfigurationMBean secMBean, String file, String username, String password, String idd, String trustKeyStore, String customTrustKeyStoreFileName, String customTrustKeyStoreType, String customTrustKeyStorePassPhrase, String javaStandardTrustKeyStorePassPhrase, String customIdentityKeyStoreFileName, String customIdentityKeyStoreType, String customIdentityKeyStorePassPhrase, String customIdentityAlias, String customIdentityPrivateKeyPassPhrase) {
      try {
         boolean changed = false;
         BootProperties bootProps = theInstance;
         if (bootProps == null) {
            bootProps = new BootProperties(secMBean, file);
            if (!bootProps.read()) {
               changed = true;
            } else {
               theInstance = bootProps;
            }
         }

         if (valChanged(bootProps.getOne(kernelId), username)) {
            bootProps.username = username;
            changed = true;
         }

         if (valChanged(bootProps.getTwo(kernelId), password)) {
            bootProps.password = password;
            changed = true;
         }

         if (valChanged(bootProps.getIdentityDomain(kernelId), idd)) {
            bootProps.idd = idd;
            changed = true;
         }

         if (valChanged(bootProps.getTrustKeyStore(), trustKeyStore)) {
            bootProps.trustKeyStore = trustKeyStore;
            changed = true;
         }

         if (valChanged(bootProps.getCustomTrustKeyStoreFileName(), customTrustKeyStoreFileName)) {
            bootProps.customTrustKeyStoreFileName = customTrustKeyStoreFileName;
            changed = true;
         }

         if (valChanged(bootProps.getCustomTrustKeyStoreType(), customTrustKeyStoreType)) {
            bootProps.customTrustKeyStoreType = customTrustKeyStoreType;
            changed = true;
         }

         if (valChanged(bootProps.getCustomTrustKeyStorePassPhrase(), customTrustKeyStorePassPhrase)) {
            bootProps.customTrustKeyStorePassPhrase = customTrustKeyStorePassPhrase;
            changed = true;
         }

         if (valChanged(bootProps.getJavaStandardTrustKeyStorePassPhrase(), javaStandardTrustKeyStorePassPhrase)) {
            bootProps.javaStandardTrustKeyStorePassPhrase = javaStandardTrustKeyStorePassPhrase;
            changed = true;
         }

         if (valChanged(bootProps.getCustomIdentityKeyStoreFileName(), customIdentityKeyStoreFileName)) {
            bootProps.customIdentityKeyStoreFileName = customIdentityKeyStoreFileName;
            changed = true;
         }

         if (valChanged(bootProps.getCustomIdentityKeyStoreType(), customIdentityKeyStoreType)) {
            bootProps.customIdentityKeyStoreType = customIdentityKeyStoreType;
            changed = true;
         }

         if (valChanged(bootProps.getCustomIdentityKeyStorePassPhrase(), customIdentityKeyStorePassPhrase)) {
            bootProps.customIdentityKeyStorePassPhrase = customIdentityKeyStorePassPhrase;
            changed = true;
         }

         if (valChanged(bootProps.getCustomIdentityAlias(), customIdentityAlias)) {
            bootProps.customIdentityAlias = customIdentityAlias;
            changed = true;
         }

         if (valChanged(bootProps.getCustomIdentityPrivateKeyPassPhrase(), customIdentityPrivateKeyPassPhrase)) {
            bootProps.customIdentityPrivateKeyPassPhrase = customIdentityPrivateKeyPassPhrase;
            changed = true;
         }

         if (changed || bootProps.haveUnencryptedValues()) {
            bootProps.write();
         }
      } catch (Exception var17) {
         SecurityLogger.logBootPropertiesWarning(var17.toString());
      }

   }

   public static synchronized BootProperties getBootProperties() {
      return theInstance;
   }

   public static synchronized void unload(boolean deleteFile) {
      if (theInstance != null) {
         try {
            if (deleteFile) {
               theInstance.delete();
            }
         } catch (Exception var2) {
            SecurityLogger.logBootPropertiesWarning(var2.toString());
         }

         theInstance = null;
      }
   }

   String getOne(AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      return this.getEncryptedValue("username", this.username);
   }

   String getTwo(AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      return this.getEncryptedValue("password", this.password);
   }

   String getIdentityDomain(AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      return this.getEncryptedValue("IdentityDomain", this.idd);
   }

   public String getOneClient() {
      return !((KernelTypeService)LocatorUtilities.getService(KernelTypeService.class)).isServer() ? this.getOne(kernelId) : null;
   }

   public String getTwoClient() {
      return !((KernelTypeService)LocatorUtilities.getService(KernelTypeService.class)).isServer() ? this.getTwo(kernelId) : null;
   }

   public String getIdentityDomainClient() {
      return !((KernelTypeService)LocatorUtilities.getService(KernelTypeService.class)).isServer() ? this.getIdentityDomain(kernelId) : null;
   }

   public String getTrustKeyStore() {
      return this.trustKeyStore;
   }

   public String getCustomTrustKeyStoreFileName() {
      return this.customTrustKeyStoreFileName;
   }

   public String getCustomTrustKeyStoreType() {
      return this.customTrustKeyStoreType;
   }

   public String getCustomTrustKeyStorePassPhrase() {
      return this.encryptValue(this.customTrustKeyStorePassPhrase);
   }

   public String getJavaStandardTrustKeyStorePassPhrase() {
      return this.encryptValue(this.javaStandardTrustKeyStorePassPhrase);
   }

   public String getCustomIdentityKeyStoreFileName() {
      return this.customIdentityKeyStoreFileName;
   }

   public String getCustomIdentityKeyStoreType() {
      return this.customIdentityKeyStoreType;
   }

   public String getCustomIdentityKeyStorePassPhrase() {
      return this.encryptValue(this.customIdentityKeyStorePassPhrase);
   }

   public String getCustomIdentityPrivateKeyPassPhrase() {
      return this.encryptValue(this.customIdentityPrivateKeyPassPhrase);
   }

   public String getCustomIdentityAlias() {
      return this.customIdentityAlias;
   }

   private BootProperties(boolean clear, String file) {
      this.useClear = clear;
      this.initialize((SecurityConfigurationMBean)null, file);
   }

   private BootProperties(SecurityConfigurationMBean secMBean, String file) {
      this.initialize(secMBean, file);
   }

   private static boolean valChanged(String oldVal, String newVal) {
      if (!isSet(oldVal) && !isSet(newVal)) {
         return false;
      } else if (isSet(oldVal) && isSet(newVal)) {
         return !newVal.equals(oldVal);
      } else {
         return true;
      }
   }

   private static boolean isSet(String value) {
      return value != null && value.length() > 0;
   }

   private String getEncryptedValue(String property, String value) {
      if (this.useClear && this.encryptionService == null) {
         return value;
      } else if (value == null) {
         return value;
      } else {
         try {
            return this.encryptionService.decrypt(value);
         } catch (EncryptionServiceException var4) {
            SecurityLogger.logBootPropertiesDecryptionFailure(this.filename, property, value, var4.toString());
            return "";
         } catch (Exception var5) {
            SecurityLogger.logBootPropertiesWarning(var5.toString());
            return "";
         }
      }
   }

   private String encryptValue(String value) {
      return this.encryptionService != null && value != null ? this.encryptionService.encrypt(value) : value;
   }

   private void initialize(SecurityConfigurationMBean securityConfigurationMBean, String file) {
      this.filename = getFileName(file);
      this.needServerSecDir = file == null && getServerName() != null;
      if (!this.useClear) {
         EncryptionService encryptSvc = null;
         if (securityConfigurationMBean == null) {
            encryptSvc = SerializedSystemIni.getEncryptionService(DomainDir.getRootDir());
         } else {
            encryptSvc = SerializedSystemIni.getEncryptionService(securityConfigurationMBean.getSalt(), securityConfigurationMBean.getEncryptedSecretKey(), securityConfigurationMBean.getEncryptedAESSecretKey());
         }

         this.encryptionService = new ClearOrEncryptedService(encryptSvc);
      }
   }

   private boolean read() throws IOException {
      File file = new File(this.filename);
      if (!file.exists()) {
         return false;
      } else {
         Properties props = new Properties();
         FileInputStream is = new FileInputStream(file);

         try {
            props.load(is);
            this.username = props.getProperty("username");
            this.password = props.getProperty("password");
            this.idd = props.getProperty("IdentityDomain");
            this.trustKeyStore = props.getProperty("TrustKeyStore");
            this.customTrustKeyStoreFileName = props.getProperty("CustomTrustKeyStoreFileName");
            this.customTrustKeyStoreType = props.getProperty("CustomTrustKeyStoreType");
            this.customTrustKeyStorePassPhrase = props.getProperty("CustomTrustKeyStorePassPhrase");
            this.javaStandardTrustKeyStorePassPhrase = props.getProperty("JavaStandardTrustKeyStorePassPhrase");
            this.customIdentityKeyStoreFileName = props.getProperty("CustomIdentityKeyStoreFileName");
            this.customIdentityKeyStoreType = props.getProperty("CustomIdentityKeyStoreType");
            this.customIdentityKeyStorePassPhrase = props.getProperty("CustomIdentityKeyStorePassPhrase");
            this.customIdentityAlias = props.getProperty("CustomIdentityKeyStoreAlias");
            this.customIdentityPrivateKeyPassPhrase = props.getProperty("CustomIdentityPrivateKeyPassPhrase");
         } finally {
            is.close();
         }

         if (this.username != null && this.password != null) {
            if (this.useClear || !this.encryptionService.isEncrypted(this.username)) {
               this.username = this.username.trim();
               if (this.username.length() == 0) {
                  return false;
               }
            }

            if ((this.useClear || !this.encryptionService.isEncrypted(this.password)) && this.password.length() == 0) {
               return false;
            } else {
               if (this.idd != null) {
                  if (this.useClear || !this.encryptionService.isEncrypted(this.idd)) {
                     this.idd = this.idd.trim();
                  }

                  if (this.idd.length() == 0) {
                     this.idd = null;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   private boolean haveUnencryptedValues() {
      if (!this.encryptionService.isEncrypted(this.username)) {
         return true;
      } else if (!this.encryptionService.isEncrypted(this.password)) {
         return true;
      } else if (isSet(this.idd) && !this.encryptionService.isEncrypted(this.idd)) {
         return true;
      } else if (isSet(this.customTrustKeyStorePassPhrase) && !this.encryptionService.isEncrypted(this.customTrustKeyStorePassPhrase)) {
         return true;
      } else if (isSet(this.javaStandardTrustKeyStorePassPhrase) && !this.encryptionService.isEncrypted(this.javaStandardTrustKeyStorePassPhrase)) {
         return true;
      } else if (isSet(this.customIdentityKeyStorePassPhrase) && !this.encryptionService.isEncrypted(this.customIdentityKeyStorePassPhrase)) {
         return true;
      } else {
         return isSet(this.customIdentityPrivateKeyPassPhrase) && !this.encryptionService.isEncrypted(this.customIdentityPrivateKeyPassPhrase);
      }
   }

   private void write() throws IOException {
      if (!this.useClear) {
         File propsFile = new File(this.filename);

         try {
            SecurityLogger.logStoringBootIdentity(propsFile.getCanonicalPath());
         } catch (IOException var7) {
            SecurityLogger.logStoringBootIdentity(propsFile.getAbsolutePath());
         }

         this.ensureServerSecDirExists();
         Properties props = new Properties();
         FileOutputStream os = new FileOutputStream(propsFile);

         try {
            props.setProperty("username", this.encryptionService.encrypt(this.username));
            props.setProperty("password", this.encryptionService.encrypt(this.password));
            if (isSet(this.idd)) {
               props.setProperty("IdentityDomain", this.encryptionService.encrypt(this.idd));
            }

            if (isSet(this.trustKeyStore)) {
               props.setProperty("TrustKeyStore", this.trustKeyStore);
            }

            if (isSet(this.customTrustKeyStoreFileName)) {
               props.setProperty("CustomTrustKeyStoreFileName", this.customTrustKeyStoreFileName);
            }

            if (isSet(this.customTrustKeyStoreType)) {
               props.setProperty("CustomTrustKeyStoreType", this.customTrustKeyStoreType);
            }

            if (isSet(this.customTrustKeyStorePassPhrase)) {
               props.setProperty("CustomTrustKeyStorePassPhrase", this.encryptionService.encrypt(this.customTrustKeyStorePassPhrase));
            }

            if (isSet(this.javaStandardTrustKeyStorePassPhrase)) {
               props.setProperty("JavaStandardTrustKeyStorePassPhrase", this.encryptionService.encrypt(this.javaStandardTrustKeyStorePassPhrase));
            }

            if (isSet(this.customIdentityKeyStoreFileName)) {
               props.setProperty("CustomIdentityKeyStoreFileName", this.customIdentityKeyStoreFileName);
            }

            if (isSet(this.customIdentityKeyStoreType)) {
               props.setProperty("CustomIdentityKeyStoreType", this.customIdentityKeyStoreType);
            }

            if (isSet(this.customIdentityKeyStorePassPhrase)) {
               props.setProperty("CustomIdentityKeyStorePassPhrase", this.encryptionService.encrypt(this.customIdentityKeyStorePassPhrase));
            }

            if (isSet(this.customIdentityAlias)) {
               props.setProperty("CustomIdentityKeyStoreAlias", this.customIdentityAlias);
            }

            if (isSet(this.customIdentityPrivateKeyPassPhrase)) {
               props.setProperty("CustomIdentityPrivateKeyPassPhrase", this.encryptionService.encrypt(this.customIdentityPrivateKeyPassPhrase));
            }

            props.store(os, (String)null);
         } finally {
            os.close();
         }

         this.updateDomainLevelBP(propsFile);
      }
   }

   private void delete() throws IOException {
      this.deleteDomainLevelBP();
      File file = new File(this.filename);
      if (file.exists()) {
         if (!file.delete()) {
            throw new IOException(SecurityLogger.getUnableToDelete(this.filename));
         }
      }
   }

   private static String getFileName(String filename) {
      if (filename != null) {
         return filename;
      } else {
         if (DEFAULT_FILE == null) {
            String serverName = getServerName();
            if (serverName != null) {
               DEFAULT_FILE = DomainDir.getPathRelativeServersSecurityDir(serverName, "boot.properties");
            } else {
               DEFAULT_FILE = "boot.properties";
            }
         }

         return DEFAULT_FILE;
      }
   }

   private void ensureServerSecDirExists() {
      if (this.needServerSecDir && getServerName() != null) {
         File securityDir = new File(DomainDir.getSecurityDirForServer(getServerName()));
         if (!securityDir.exists() && securityDir.mkdir()) {
         }
      }

   }

   private static void debug(String msg) {
   }
}
