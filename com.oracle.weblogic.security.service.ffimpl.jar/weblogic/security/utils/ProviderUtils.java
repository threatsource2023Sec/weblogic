package weblogic.security.utils;

import com.octetstring.vde.util.DuplicateEntryCollection;
import com.octetstring.vde.util.EncryptionHelper;
import com.octetstring.vde.util.ErrorCollection;
import com.octetstring.vde.util.LDIF;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSearchResults;
import org.jvnet.hk2.annotations.Service;
import weblogic.entitlement.util.Escaping;
import weblogic.entitlement.util.TextFilter;
import weblogic.ldap.EmbeddedLDAP;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.security.RealmMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.URLResource;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.ProviderInitializationException;
import weblogic.utils.FileUtils;

public final class ProviderUtils {
   public static final String AUTHENTICATOR = "Authenticator";
   public static final String AUTHORIZER = "Authorizer";
   public static final String ROLEMAPPER = "RoleMapper";
   public static final String CREDENTIALMAPPER = "CredentialMapper";
   public static final String PKICREDENTIALMAPPER = "PKICredentialMapper";
   public static final String IDENTITYASSERTER = "IdentityAsserter";
   public static final String SAMLCREDENTIALMAPPER = "SAMLCredentialMapper";
   public static final String SAMLIDENITYASSERTOR = "SAMLIdentityAssertor";
   static final String LDIFTEXT = ".ldift";
   static final String LDIFTEMPLATEEXT = "Init.ldift";
   private static final String LDIFTUPDATE = "Update_";
   static final String BASELDIFTEMPLATEEXT = "Base.ldift";
   private static final String LDIFINITIALIZEDEXT = "Init.initialized";
   static final String DEFAULT = "Default";
   static final String DEFAULTREALM = "myrealm";
   static final String DEFAULTDOMAIN = "mydomain";
   private static final String DATAEXT = ".dat";
   private static final String EXPORT_PREFIX = "expwls";
   private static final String IMPORT_PREFIX = "impwls";
   public static final String FROMMBEAN = "mbean";
   public static final String FROMDEPLOY = "deploy";
   public static final String MODIFY_TIMESTAMP = "modifyTimestamp";
   public static final String CREATE_TIMESTAMP = "createTimestamp";
   public static final String CREATEORS_NAME = "creatorsName";
   public static final String WLSCREATORINFO = "wlsCreatorInfo";
   public static final String WLSCOLLECTIONNAME = "wlsCollectionName";
   public static final String GUID = "orclguid";
   public static final String[] EXCLUDED_ON_COPY_ATTRS;
   private static final String[] EXPORT_HEADER;
   private static final String[] LDAP_BASE_HEADER;
   private static final String ATZ_BASE = "EResource";
   private static final String ATZ_COLLECTION_BASE = "EPolicyCollectionInfo";
   private static final String ROLE_BASE = "ERole";
   private static final String ROLE_COLLECTION_BASE = "ERoleCollectionInfo";
   private static final String PREDICATE_BASE = "EPredicate";
   private static final String[] ATZ_LDAP_BASE;
   private static final String[] ROLE_LDAP_BASE;
   private static final String[] ALL_ATN_LDIF;
   private static final String[] ALL_ATZ_LDIF;
   private static final String[] ALL_ROLE_LDIF;
   private static final String[] ALL_CRED_LDIF;
   private static final String[] ALL_PKI_CRED_LDIF;
   public static final String PWCREDMAP_PRINCIPALPASSWORD = "principalPassword";
   public static final String PKI_CREDMAP_KEYSTORE_PASSWORD = "keystoreAliasPassword";
   private static final String[] CRED_MAPPER_ENCRYPTED_ATTRIBS;
   private static final String[] PKI_CRED_MAPPER_ENCRYPTED_ATTRIBS;
   public static final String PWATN_USERPASSWORD = "userpassword";
   private static final String[] ATN_ENCRYPTED_ATTRIBS;
   private static HashMap AtnConstraintsMap;
   private static final String SAML_AUTH_PWD = "beaSAMLAuthPassword";
   private static final String[] SAML_ENCRYPTED_ATTRIBS;
   private static final AuthenticatedSubject kernelId;
   private static final String PASSWORDS_CONSTRAINT = "passwords";
   private static final String CLEAR_PASSWORDS = "cleartext";
   private static final int STRING_BUF_LEN = 160;
   private static final int DBVERAUTHENTICATOR = 41;
   private static final int DBVERAUTHORIZER = 90;
   private static final int DBVERROLEMAPPER = 50;
   private static final int DBVERCREDENTIALMAPPER = 0;
   private static final String NAME_DELIMITER = "::";
   private static final Escaping escaper;
   private static final String escapedComma;
   private static final String escapedModule;
   private static final String escapedContextPath;
   private static final String escapedApplication;
   private static final String escapedTypeEJB;
   private static final String escapedTypeURL;
   private static final String escapedTypeAPP;
   private static final String escapedTypeEIS;
   private static final String escapedTypeWSS;
   private static final String[] noAttrs;
   private static final String escapedAppKey;
   private static final String escapedAppSearch;
   private static final String domainToken = "@domain@";
   private static final String realmToken = "@realm@";
   private static final String userToken = "@changemeuser@";
   private static final String passToken = "@changemepass@";
   private static final String ldapBaseToken = "@LDAPbase@";

   private ProviderUtils() {
   }

   public static Escaping getLDAPSpecialCharsEncoder() {
      return escaper;
   }

   public static void loadLDIFAuthenticatorTemplate(String domainName, RealmMBean realmMBean) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      String realmName;
      if (realmMBean != null) {
         realmName = realmMBean.getName();
      } else {
         realmName = "myrealm";
      }

      LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtn");
      boolean skipUsers = realmMBean != null ? !realmMBean.isDefaultRealm() : false;
      if (System.getProperty("weblogic.security.bootstrapLoadBootUser") != null) {
         skipUsers = false;
      }

      boolean loadedLDIFT = loadLDIFTemplate("Authenticator", 41, domainName, realmName, log, "Default", skipUsers);
      if (loadedLDIFT) {
         String ldifTemplateFilePath = DomainDir.getSecurityDir() + File.separator + "Default" + "Authenticator" + "Init.ldift";
         File ldifTemplateFile = new File(ldifTemplateFilePath);
         String strippedLDIFTFilename = null;
         if (ldifTemplateFile.exists() && !Boolean.getBoolean("weblogic.security.doNotRemoveUsersFromLDIFT")) {
            try {
               strippedLDIFTFilename = stripUsersFromLDIFTemplate(ldifTemplateFilePath);
            } catch (IOException var18) {
               if (log.isDebugEnabled()) {
                  log.debug("Error string users out of LDIFT file", var18);
               }

               return;
            }

            try {
               File backupFile = new File(ldifTemplateFilePath + ".old");
               boolean backedUp = ldifTemplateFile.renameTo(backupFile);
               if (!backedUp) {
                  if (log.isDebugEnabled()) {
                     log.debug("Could not backup LDIFT template file. Leave as is.");
                  }

                  return;
               }

               boolean copied = true;

               try {
                  File strippedLDIFTFile = new File(strippedLDIFTFilename);
                  FileUtils.copy(strippedLDIFTFile, ldifTemplateFile);
               } catch (IOException var19) {
                  if (log.isDebugEnabled()) {
                     log.debug("Error copying LDIFT template file.", var19);
                  }

                  copied = false;
               }

               if (!copied) {
                  backupFile.renameTo(ldifTemplateFile);
               } else {
                  backupFile.delete();
               }
            } catch (Exception var20) {
               if (log.isDebugEnabled()) {
                  log.debug("Error renaming or backing up LDIFT file", var20);
               }
            } finally {
               (new File(strippedLDIFTFilename)).delete();
            }
         }

      }
   }

   public static void loadLDIFAuthorizerTemplate(String domainName, RealmMBean realmMBean) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      String realmName;
      if (realmMBean != null) {
         realmName = realmMBean.getName();
      } else {
         realmName = "myrealm";
      }

      LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtz");
      loadLDIFTemplate("Authorizer", 90, domainName, realmName, log);
   }

   public static void loadLDIFXACMLAuthorizerTemplate(String domainName, String realmName) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      if (realmName == null) {
         realmName = "myrealm";
      }

      LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtz");
      loadLDIFTemplate("Authorizer", 90, domainName, realmName, log, "XACML", false);
   }

   public static void loadLDIFRoleMapperTemplate(String domainName, RealmMBean realmMBean) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      String realmName;
      if (realmMBean != null) {
         realmName = realmMBean.getName();
      } else {
         realmName = "myrealm";
      }

      LoggerWrapper log = LoggerWrapper.getInstance("SecurityRoleMap");
      loadLDIFTemplate("RoleMapper", 50, domainName, realmName, log);
   }

   public static void loadLDIFXACMLRoleMapperTemplate(String domainName, String realmName) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      LoggerWrapper log = LoggerWrapper.getInstance("SecurityRoleMap");
      loadLDIFTemplate("RoleMapper", 50, domainName, realmName, log, "XACML", false);
   }

   public static void loadLDIFCredentialMapperTemplate(String domainName, RealmMBean realmMBean) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      String realmName;
      if (realmMBean != null) {
         realmName = realmMBean.getName();
      } else {
         realmName = "myrealm";
      }

      LoggerWrapper log = LoggerWrapper.getInstance("SecurityCredMap");
      loadLDIFTemplate("CredentialMapper", 0, domainName, realmName, log);
   }

   private static void loadLDIFTemplate(String providerType, int providerDbVer, String domainName, String realmName, LoggerWrapper log) {
      loadLDIFTemplate(providerType, providerDbVer, domainName, realmName, log, "Default", false);
   }

   private static boolean loadLDIFTemplate(String providerType, int providerDbVer, String domainName, String realmName, LoggerWrapper log, String baseName, boolean skipUsers) {
      String templateFileName = null;
      RuntimeAccess c = ManagementService.getRuntimeAccess(kernelId);
      if (!c.isAdminServer()) {
         if (log.isDebugEnabled()) {
            log.debug("No need to load, this is not an admin server");
         }

         return false;
      } else {
         File initializedFile = getInitializedFile(baseName, providerType, realmName);
         String initializedFileName = initializedFile.getAbsolutePath();
         if (log.isDebugEnabled()) {
            log.debug("Looking for " + initializedFileName);
         }

         if (initializedFile.exists()) {
            if (log.isDebugEnabled()) {
               log.debug("Found " + initializedFileName);
            }

            int dbVer = getLDIFTProviderVersion(providerType, realmName, log, baseName);
            if (dbVer < 0) {
               return false;
            }

            if (dbVer >= providerDbVer) {
               if (log.isDebugEnabled()) {
                  log.debug(providerType + " is up to date at version " + providerDbVer + ".");
               }

               SecurityLogger.logLDAPPreviouslyInitialized(providerType);
               return false;
            }

            if (log.isDebugEnabled()) {
               log.debug(providerType + " is not up to date. Updating version " + dbVer + " to " + providerDbVer + ".");
            }

            for(int updateFileNo = dbVer + 1; updateFileNo <= providerDbVer; ++updateFileNo) {
               if (loadUpdateLDIFTemplate(providerType, updateFileNo, domainName, realmName, log, baseName)) {
                  UpdateProviderLDIFTVersion(providerType, updateFileNo, realmName, log, baseName);
               }
            }
         } else {
            if (log.isDebugEnabled()) {
               log.debug("Did not find " + initializedFileName + " will load full LDIFT.");
            }

            loadFullLDIFTemplate(providerType, domainName, realmName, log, baseName, skipUsers);

            try {
               initializedFile.createNewFile();
               UpdateProviderLDIFTVersion(providerType, providerDbVer, realmName, log, baseName);
            } catch (IOException var13) {
               if (log.isDebugEnabled()) {
                  log.debug("Updated LDIFT but unable to create " + initializedFileName);
               }

               logMessageAndThrowProviderInitializationException(SecurityLogger.logFailureCreatingProviderInitFileLoggable(providerType, initializedFileName, var13), var13);
            }
         }

         return true;
      }
   }

   private static void loadFullLDIFTemplate(String providerType, String domainName, String realmName, LoggerWrapper log, String baseName, boolean skipUsers) {
      String templateFileName = null;
      File file3 = new File(DomainDir.getSecurityDir() + File.separator + baseName + providerType + realmName + "Init.ldift");
      File file2 = new File(DomainDir.getSecurityDir() + File.separator + baseName + providerType + "Init.ldift");
      File file1 = new File(BootStrap.getWebLogicHome() + File.separator + "lib" + File.separator + baseName + providerType + "Init.ldift");
      String file3Name = file3.getAbsolutePath();
      String file2Name = file2.getAbsolutePath();
      String file1Name = file1.getAbsolutePath();
      if (log.isDebugEnabled()) {
         log.debug("looking for " + file3Name);
         log.debug("looking for " + file2Name);
         log.debug("looking for " + file1Name);
      }

      if (file3.exists()) {
         templateFileName = file3Name;
         if (file3.length() == 0L) {
            if (log.isDebugEnabled()) {
               log.debug(file3Name + " found,  but is empty. Will not load");
            }

            if ("CredentialMapper".equals(providerType)) {
               SecurityLogger.logLDIFEmptyForCredentialProvider(providerType, file3Name);
            } else {
               SecurityLogger.logLDIFEmptyForProvider(providerType, file3Name);
            }

            return;
         }

         if (log.isDebugEnabled()) {
            log.debug(file3Name + " exists");
         }
      } else if (file2.exists()) {
         templateFileName = file2Name;
         if (file2.length() == 0L) {
            if (log.isDebugEnabled()) {
               log.debug(file2Name + " found,  but is empty. Will not load");
            }

            if ("CredentialMapper".equals(providerType)) {
               SecurityLogger.logLDIFEmptyForCredentialProvider(providerType, file2Name);
            } else {
               SecurityLogger.logLDIFEmptyForProvider(providerType, file2Name);
            }

            return;
         }

         if (log.isDebugEnabled()) {
            log.debug(file2Name + " exists");
         }
      } else {
         if (!file1.exists()) {
            if (log.isDebugEnabled()) {
               log.debug("no ldift file can be found ");
            }

            StringBuffer filesLookedFor = new StringBuffer();
            filesLookedFor.append("(");
            filesLookedFor.append(file3Name);
            filesLookedFor.append(", ");
            filesLookedFor.append(file2Name);
            filesLookedFor.append(", ");
            filesLookedFor.append(file1Name);
            filesLookedFor.append(")");
            SecurityLogger.logLDIFNotFoundForProvider(providerType, filesLookedFor.toString());
            return;
         }

         templateFileName = file1Name;
         if (file1.length() == 0L) {
            if (log.isDebugEnabled()) {
               log.debug(file1Name + " found,  but is empty. Will not load");
            }

            if ("CredentialMapper".equals(providerType)) {
               SecurityLogger.logLDIFEmptyForCredentialProvider(providerType, file1Name);
            } else {
               SecurityLogger.logLDIFEmptyForProvider(providerType, file1Name);
            }

            return;
         }

         if (log.isDebugEnabled()) {
            log.debug(file1Name + " exists");
         }
      }

      SecurityLogger.logInitializingLDIFForProvider(providerType, templateFileName);
      String tmpFileName = null;

      try {
         if (log.isDebugEnabled()) {
            log.debug("calling convertLDIFTemplate " + templateFileName);
         }

         tmpFileName = convertLDIFTemplate(templateFileName, domainName, realmName);
         if (tmpFileName != null && skipUsers) {
            tmpFileName = stripUsersFromLDIFTemplate(tmpFileName);
         }
      } catch (IOException var21) {
         logMessageAndThrowProviderInitializationException(SecurityLogger.logFailureLoadingLDIFForProviderLoggable(providerType, templateFileName, var21), var21);
      }

      if (tmpFileName != null) {
         try {
            if (log.isDebugEnabled()) {
               log.debug("calling importLDIF " + tmpFileName);
            }

            (new LDIF()).importLDIF(tmpFileName, (InputStream)null, true);
         } catch (Exception var19) {
            logMessageAndThrowProviderInitializationException(SecurityLogger.logFailureLoadingLDIFForProviderLoggable(providerType, templateFileName, var19), var19);
         } finally {
            (new File(tmpFileName)).delete();
         }
      }

      SecurityLogger.logLoadedLDIFForProvider(providerType, templateFileName);
   }

   private static boolean loadUpdateLDIFTemplate(String providerType, int updateFileNo, String domainName, String realmName, LoggerWrapper log) {
      return loadUpdateLDIFTemplate(providerType, updateFileNo, domainName, realmName, log, "Default");
   }

   private static boolean loadUpdateLDIFTemplate(String providerType, int updateFileNo, String domainName, String realmName, LoggerWrapper log, String baseName) {
      File updateFile = new File(BootStrap.getWebLogicHome() + File.separator + "lib" + File.separator + baseName + providerType + "Update_" + updateFileNo + ".ldift");
      String updateFileName = updateFile.getAbsolutePath();
      if (log.isDebugEnabled()) {
         log.debug("looking for " + updateFileName);
      }

      if (updateFile.exists()) {
         if (updateFile.length() == 0L) {
            if (log.isDebugEnabled()) {
               log.debug(updateFileName + " found,  but is empty. Will not load");
            }

            if ("CredentialMapper".equals(providerType)) {
               SecurityLogger.logLDIFEmptyForCredentialProvider(providerType, updateFileName);
            } else {
               SecurityLogger.logLDIFEmptyForProvider(providerType, updateFileName);
            }

            return true;
         } else {
            if (log.isDebugEnabled()) {
               log.debug(updateFileName + " exists");
            }

            SecurityLogger.logUpdatingLDIFForProvider(providerType, updateFileName, updateFileNo);
            String tmpFileName = null;

            try {
               if (log.isDebugEnabled()) {
                  log.debug("calling convertLDIFTemplate " + updateFileName);
               }

               tmpFileName = convertLDIFTemplate(updateFileName, domainName, realmName);
            } catch (IOException var16) {
               logMessageAndThrowProviderInitializationException(SecurityLogger.logFailureLoadingLDIFForProviderLoggable(providerType, updateFileName, var16), var16);
            }

            if (tmpFileName != null) {
               try {
                  if (log.isDebugEnabled()) {
                     log.debug("calling importLDIF " + tmpFileName);
                  }

                  (new LDIF()).importLDIF(tmpFileName, (InputStream)null, true);
               } catch (Exception var14) {
                  logMessageAndThrowProviderInitializationException(SecurityLogger.logFailureLoadingLDIFForProviderLoggable(providerType, updateFileName, var14), var14);
               } finally {
                  (new File(tmpFileName)).delete();
               }
            }

            SecurityLogger.logLoadedLDIFForProvider(providerType, updateFileName);
            return true;
         }
      } else {
         if (log.isDebugEnabled()) {
            log.debug("The " + updateFileName + " ldift update file can not be found, skipping");
         }

         return true;
      }
   }

   private static int getLDIFTProviderVersion(String providerType, String realmName, LoggerWrapper log) {
      return getLDIFTProviderVersion(providerType, realmName, log, "Default");
   }

   private static int getLDIFTProviderVersion(String providerType, String realmName, LoggerWrapper log, String baseName) {
      int dbVer = -1;
      String inputPath = EmbeddedLDAP.getEmbeddedLDAPDataDir() + File.separator + baseName + providerType + realmName + "Init.initialized";
      FileInputStream is = null;

      byte var9;
      try {
         is = new FileInputStream(inputPath);
         Properties props = new Properties();
         props.load(is);
         is.close();
         is = null;
         String value = props.getProperty(providerType);
         if (value != null) {
            if (log.isDebugEnabled()) {
               log.debug(providerType + " version " + value);
            }

            dbVer = Integer.parseInt(value);
            return dbVer;
         }

         var9 = 0;
      } catch (IOException var22) {
         if (log.isDebugEnabled()) {
            log.debug("While opening " + inputPath + " got an exception: " + var22);
         }

         logMessageAndThrowProviderInitializationException(SecurityLogger.logUnreadableWLSProviderUpdateFileLoggable(providerType, inputPath, var22), var22);
         return dbVer;
      } catch (NumberFormatException var23) {
         if (log.isDebugEnabled()) {
            log.debug("While attempting to parse a version number in file " + inputPath + " got an exception: " + var23);
         }

         logMessageAndThrowProviderInitializationException(SecurityLogger.logMisconfiguredWLSProviderUpdateFileLoggable(providerType, inputPath, var23), var23);
         return dbVer;
      } finally {
         try {
            if (is != null) {
               is.close();
            }
         } catch (IOException var21) {
            return dbVer;
         }

      }

      return var9;
   }

   private static boolean UpdateProviderLDIFTVersion(String providerType, int providerDbVer, String realmName, LoggerWrapper log) {
      return UpdateProviderLDIFTVersion(providerType, providerDbVer, realmName, log, "Default");
   }

   private static boolean UpdateProviderLDIFTVersion(String providerType, int providerDbVer, String realmName, LoggerWrapper log, String baseName) {
      String inputPath = EmbeddedLDAP.getEmbeddedLDAPDataDir() + File.separator + baseName + providerType + realmName + "Init.initialized";
      String outputPath = inputPath;
      FileInputStream is = null;

      try {
         is = new FileInputStream(inputPath);
      } catch (FileNotFoundException var43) {
         if (log.isDebugEnabled()) {
            log.debug("Unable to find file : " + inputPath + " exception: " + var43);
         }

         logMessageAndThrowProviderInitializationException(SecurityLogger.logFailureUpdatingLDIFVersionLoggable(providerType, inputPath, providerDbVer, var43), var43);
      }

      Properties props = new Properties();

      try {
         props.load(is);
      } catch (IOException var41) {
         if (log.isDebugEnabled()) {
            log.debug("Unable to load properties from file : " + inputPath + " exception: " + var41);
         }

         logMessageAndThrowProviderInitializationException(SecurityLogger.logFailureUpdatingLDIFVersionLoggable(providerType, inputPath, providerDbVer, var41), var41);
      } finally {
         try {
            if (is != null) {
               is.close();
            }

            is = null;
         } catch (IOException var37) {
            if (log.isDebugEnabled()) {
               log.debug("Unable to close file : " + inputPath + " exception: " + var37);
            }

            throw new ProviderInitializationException(var37.getMessage());
         }
      }

      props.setProperty(providerType, String.valueOf(providerDbVer));
      if (log.isDebugEnabled()) {
         log.debug("Set " + providerType + " to version " + providerDbVer + " in file: " + inputPath);
      }

      FileOutputStream os = null;

      try {
         os = new FileOutputStream(outputPath);
      } catch (FileNotFoundException var40) {
         if (log.isDebugEnabled()) {
            log.debug("Unable to find file : " + inputPath + " exception: " + var40);
         }

         logMessageAndThrowProviderInitializationException(SecurityLogger.logFailureUpdatingLDIFVersionLoggable(providerType, inputPath, providerDbVer, var40), var40);
      }

      try {
         props.store(os, "The version of LDIFT loaded for the WLS default " + providerType + " provider. Last update was to version " + providerDbVer);
      } catch (IOException var38) {
         if (log.isDebugEnabled()) {
            log.debug("Exception while trying to update: " + outputPath + " " + var38);
         }

         logMessageAndThrowProviderInitializationException(SecurityLogger.logFailureUpdatingLDIFVersionLoggable(providerType, outputPath, providerDbVer, var38), var38);
      } finally {
         try {
            os.close();
         } catch (IOException var36) {
            if (log.isDebugEnabled()) {
               log.debug("Exception while trying to close: " + inputPath + " " + var36);
            }
         }

      }

      return true;
   }

   private static String convertLDIFTemplate(String fileName, String domainName, String realmName) throws IOException {
      return convertLDIFTemplate(fileName, domainName, realmName, true);
   }

   private static String convertLDIFTemplate(String fileName, String domainName, String realmName, boolean fallback) throws IOException {
      boolean lineConverted = false;
      File tmpFile = null;

      try {
         tmpFile = File.createTempFile("impwls", ".dat", (File)null);
      } catch (IOException var13) {
         boolean failure = true;
         if (fallback) {
            try {
               tmpFile = File.createTempFile("impwls", ".dat", new File(DomainDir.getRootDir()));
               failure = false;
            } catch (IOException var11) {
            }
         }

         if (failure) {
            throw new IOException(SecurityLogger.getCreateTempFileFailed(var13.getMessage()));
         }
      }

      BufferedReader in = new BufferedReader(new FileReader(fileName));
      BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
      HashMap tokenMap = new HashMap();
      tokenMap.put("@domain@", domainName);
      tokenMap.put("@realm@", realmName);
      String line = null;

      try {
         while((line = in.readLine()) != null) {
            String retLine = convertTokens(line, tokenMap);
            if (!lineConverted && retLine != line) {
               lineConverted = true;
            }

            bw.write(retLine, 0, retLine.length());
            bw.newLine();
         }
      } catch (IOException var12) {
         bw.close();
         tmpFile.delete();
         in.close();
         throw var12;
      }

      in.close();
      bw.close();
      if (!lineConverted) {
         tmpFile.delete();
         throw new IOException(SecurityLogger.getInvalidFileFormat());
      } else {
         return tmpFile.getAbsolutePath();
      }
   }

   public static void convertBaseLDIFTemplate(String userName, String encyptPass, String templateName, String baseTemplateName) throws IOException {
      File templateFile = new File(templateName);
      templateFile.createNewFile();
      BufferedWriter bw = new BufferedWriter(new FileWriter(templateFile));
      BufferedReader in = new BufferedReader(new FileReader(baseTemplateName));
      boolean reaplacedUser = false;
      boolean reaplacedPass = false;
      HashMap tokenMap = new HashMap();
      tokenMap.put("@changemeuser@", userName);
      tokenMap.put("@changemepass@", encyptPass);
      String line = null;

      while((line = in.readLine()) != null) {
         int index;
         if (!reaplacedUser) {
            index = line.indexOf("@changemeuser@");
            if (index >= 0) {
               reaplacedUser = true;
            }
         }

         if (!reaplacedPass) {
            index = line.indexOf("@changemepass@");
            if (index >= 0) {
               reaplacedPass = true;
            }
         }

         String retLine = line;
         if (reaplacedUser || reaplacedPass) {
            retLine = convertTokens(line, tokenMap);
         }

         bw.write(retLine, 0, retLine.length());
         bw.newLine();
      }

      in.close();
      bw.close();
      if (!reaplacedUser || !reaplacedPass) {
         templateFile.delete();
         throw new IOException(SecurityLogger.getInvalidBaseTemplate());
      }
   }

   private static String stripUsersFromLDIFTemplate(String fileName) throws IOException {
      File tmpFile = File.createTempFile("impwls", ".dat", (File)null);
      BufferedReader in = new BufferedReader(new FileReader(fileName));
      BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
      String line = null;

      try {
         boolean writeEntry = true;

         while((line = in.readLine()) != null) {
            if (writeEntry && line.startsWith("dn: uid=")) {
               writeEntry = false;
            }

            if (!writeEntry && line.trim().isEmpty()) {
               writeEntry = true;
            }

            if (writeEntry) {
               bw.write(line, 0, line.length());
               bw.newLine();
            }
         }
      } catch (IOException var6) {
         bw.close();
         tmpFile.delete();
         in.close();
         throw var6;
      }

      in.close();
      bw.close();
      return tmpFile.getAbsolutePath();
   }

   public static AppConfigurationEntry.LoginModuleControlFlag getLoginModuleControlFlag(String flag) {
      if (flag != null && !flag.equalsIgnoreCase("REQUIRED")) {
         if (flag.equalsIgnoreCase("OPTIONAL")) {
            return LoginModuleControlFlag.OPTIONAL;
         } else if (flag.equalsIgnoreCase("REQUISITE")) {
            return LoginModuleControlFlag.REQUISITE;
         } else if (flag.equalsIgnoreCase("SUFFICIENT")) {
            return LoginModuleControlFlag.SUFFICIENT;
         } else {
            throw new IllegalArgumentException(SecurityLogger.getInvalidFlagValue(flag));
         }
      } else {
         return LoginModuleControlFlag.REQUIRED;
      }
   }

   public static void applicationDeleted(LDAPConnection conn, String baseDN, String applicationName, int compType, String compName, LoggerWrapper theLog) throws LDAPException {
      if (applicationName != null && applicationName.length() != 0) {
         LoggerWrapper log = theLog;
         if (theLog == null) {
            log = LoggerWrapper.getInstance("DebugEmbeddedLDAP");
         }

         String escapedAppName = escaper.escapeString(applicationName);
         String filter = escapedAppSearch + escapedAppName;
         switch (compType) {
            case 0:
               if (log.isDebugEnabled()) {
                  log.debug("applicationDeleted(): ALL");
               }

               compName = null;
               break;
            case 1:
            case 3:
               if (log.isDebugEnabled()) {
                  log.debug("applicationDeleted(): EJB or CONNECTOR");
               }

               filter = filter + escaper.escapeString(",") + escaper.escapeString(" module=");
               break;
            case 2:
            case 4:
               if (log.isDebugEnabled()) {
                  log.debug("applicationDeleted(): WEBAPP or WEBSERVICE");
               }

               filter = filter + escaper.escapeString(",") + escaper.escapeString(" contextPath=");
               break;
            case 100:
               if (log.isDebugEnabled()) {
                  log.debug("applicationDeleted(): NONE");
               }

               return;
            default:
               if (log.isDebugEnabled()) {
                  log.debug("applicationDeleted(): default");
               }

               compName = null;
         }

         if (compName != null) {
            filter = filter + escaper.escapeString(compName);
         }

         filter = filter + "*";
         if (log.isDebugEnabled()) {
            log.debug("applicationDeleted() search: " + filter);
         }

         LDAPSearchResults results = conn.search(baseDN, 1, filter, noAttrs, false);
         String appName = escapedAppKey + escapedAppName;
         if (log.isDebugEnabled()) {
            log.debug("applicationDeleted() appName: " + appName);
         }

         while(true) {
            while(results.hasMoreElements()) {
               LDAPEntry entry = results.next();
               String dn = entry.getDN();
               if (log.isDebugEnabled()) {
                  log.debug("applicationDeleted() found: " + dn);
               }

               if (dn.indexOf(appName + escapedComma) == -1 && dn.indexOf(appName + "::") == -1 && dn.indexOf(appName + ",") == -1) {
                  if (log.isDebugEnabled()) {
                     log.debug("applicationDeleted() DN skipped");
                  }
               } else {
                  if (log.isDebugEnabled()) {
                     log.debug("applicationDeleted() DN deleted");
                  }

                  conn.delete(dn);
               }
            }

            return;
         }
      }
   }

   public static void cleanupAfterCollection(LDAPConnection conn, String baseDN, String collectionName, long time, List removed, LoggerWrapper theLog) throws LDAPException {
      if (collectionName != null && collectionName.length() != 0) {
         LoggerWrapper log = theLog;
         if (theLog == null) {
            log = LoggerWrapper.getInstance("DebugEmbeddedLDAP");
         }

         String[] attrs = new String[]{"modifyTimestamp", "createTimestamp"};
         String beforeTime = EmbeddedLDAP.getDateFormat(time);
         String colName = escaper.escapeString(collectionName);
         String filter = "(&(wlsCollectionName=" + colName + ")(" + "wlsCreatorInfo" + "=" + "deploy" + "))";
         if (log.isDebugEnabled()) {
            log.debug("cleanupAfterCollection() filter: " + filter);
         }

         LDAPSearchResults results = conn.search(baseDN, 1, filter, attrs, false);

         while(results.hasMoreElements()) {
            LDAPEntry entry = results.next();
            String entryDate = getStringAttr(entry, "modifyTimestamp");
            if (entryDate == null) {
               entryDate = getStringAttr(entry, "createTimestamp");
            }

            if (entryDate != null && entryDate.compareTo(beforeTime) < 0) {
               String dn = entry.getDN();
               if (log.isDebugEnabled()) {
                  log.debug("cleanupAfterCollection() found: " + dn);
               }

               String resId = escaper.unescapeString(dn.substring(dn.indexOf(61) + 1, dn.indexOf(44)));
               removed.add(resId);
               conn.delete(dn);
            }
         }

      }
   }

   public static void cleanupAfterAppDeploy(LDAPConnection conn, String baseDN, String applicationName, int compType, String compName, long time, LoggerWrapper theLog) throws LDAPException {
      String[] attrs = new String[]{"modifyTimestamp", "createTimestamp"};
      String beforeTime = EmbeddedLDAP.getDateFormat(time);
      if (applicationName != null && applicationName.length() != 0) {
         LoggerWrapper log = theLog;
         if (theLog == null) {
            log = LoggerWrapper.getInstance("DebugEmbeddedLDAP");
         }

         String compRes = "*";
         String compFilter = null;
         String resourcePrefix = null;
         switch (compType) {
            case 0:
            default:
               if (log.isDebugEnabled()) {
                  log.debug("cleanupAfterAppDeploy(): ALL");
               }

               compName = null;
               break;
            case 1:
               if (log.isDebugEnabled()) {
                  log.debug("cleanupAfterAppDeploy(): EJB");
               }

               resourcePrefix = escapedTypeEJB + escaper.escapeString(applicationName) + escapedComma;
               compFilter = escapedModule;
               break;
            case 2:
               if (log.isDebugEnabled()) {
                  log.debug("cleanupAfterAppDeploy(): WEBAPP");
               }

               resourcePrefix = escapedTypeURL + escaper.escapeString(applicationName) + escapedComma;
               compFilter = escapedContextPath;
               break;
            case 3:
               if (log.isDebugEnabled()) {
                  log.debug("cleanupAfterAppDeploy(): CONNECTOR");
               }

               resourcePrefix = escapedTypeEIS + escaper.escapeString(applicationName) + escapedComma;
               compFilter = escapedModule;
               break;
            case 4:
               if (log.isDebugEnabled()) {
                  log.debug("cleanupAfterAppDeploy(): WEBSERVICE");
               }

               resourcePrefix = escapedTypeWSS + escaper.escapeString(applicationName) + escapedComma;
               compFilter = escapedContextPath;
               break;
            case 100:
               if (log.isDebugEnabled()) {
                  log.debug("cleanupAfterAppDeploy(): NONE");
               }

               return;
            case 101:
               if (log.isDebugEnabled()) {
                  log.debug("cleanupAfterAppDeploy(): APPLICATION");
               }

               compName = null;
               resourcePrefix = escapedTypeAPP;
               compFilter = escapedApplication + escaper.escapeString(applicationName);
         }

         if (resourcePrefix == null) {
            resourcePrefix = escaper.escapeString("type=<") + compRes + escaper.escapeString(">, application=") + escaper.escapeString(applicationName) + escaper.escapeString(",");
         }

         if (compFilter != null) {
            resourcePrefix = resourcePrefix + compFilter;
         }

         String theCompName;
         if (compName != null) {
            theCompName = compName;
            if (compType == 2) {
               boolean doMappingToLowerCaseURL = (new URLResource((String)null, (String)null, (String)null, (String)null, (String)null)).mappingToLowerCase();
               if (doMappingToLowerCaseURL) {
                  theCompName = compName.toLowerCase();
               }
            }

            compFilter = compFilter + escaper.escapeString(theCompName);
            resourcePrefix = resourcePrefix + escaper.escapeString(theCompName);
         }

         resourcePrefix = resourcePrefix + "*";
         if (log.isDebugEnabled()) {
            log.debug("cleanupAfterAppDeploy() search: " + resourcePrefix);
            log.debug("cleanupAfterAppDeploy() compFilter: " + compFilter);
         }

         theCompName = "(&(&(cn=" + resourcePrefix + ")(" + "wlsCreatorInfo" + "=" + "deploy" + "))";
         LDAPSearchResults results = conn.search(baseDN, 1, theCompName, attrs, false);

         while(true) {
            while(true) {
               LDAPEntry entry;
               String entryDate;
               do {
                  do {
                     if (!results.hasMoreElements()) {
                        return;
                     }

                     entry = results.next();
                     entryDate = getStringAttr(entry, "modifyTimestamp");
                     if (entryDate == null) {
                        entryDate = getStringAttr(entry, "createTimestamp");
                     }
                  } while(entryDate == null);
               } while(entryDate.compareTo(beforeTime) >= 0);

               String dn = entry.getDN();
               if (log.isDebugEnabled()) {
                  log.debug("cleanupAfterAppDeploy() found: " + dn);
               }

               if (compFilter != null && dn.indexOf(compFilter + escapedComma) == -1 && dn.indexOf(compFilter + "::") == -1 && dn.indexOf(compFilter + ",") == -1) {
                  if (log.isDebugEnabled()) {
                     log.debug("cleanupAfterAppDeploy() DN skipped");
                  }
               } else {
                  if (log.isDebugEnabled()) {
                     log.debug("cleanupAfterAppDeploy() DN deleted");
                  }

                  conn.delete(dn);
               }
            }
         }
      }
   }

   public static void applicationCopy(LDAPConnection conn, String baseDN, String sourceAppName, String destAppName, String[] modifyAttributes, String[] excludedAttributes, LoggerWrapper theLog) throws LDAPException {
      LoggerWrapper log = theLog;
      if (theLog == null) {
         log = LoggerWrapper.getInstance("DebugEmbeddedLDAP");
      }

      if (sourceAppName != null && sourceAppName.length() != 0 && destAppName != null && destAppName.length() != 0) {
         String escapedAppName = escaper.escapeString(sourceAppName);
         String filter = escapedAppSearch + escapedAppName + "*";
         if (log.isDebugEnabled()) {
            log.debug("applicationCopy(" + baseDN + ")");
            log.debug("applicationCopy() search: " + filter);
         }

         LDAPSearchResults results = conn.search(baseDN, 1, filter, noAttrs, false);
         String appName = escapedAppKey + escapedAppName;
         String destName = escapedAppKey + escaper.escapeString(destAppName);
         if (log.isDebugEnabled()) {
            log.debug("applicationCopy() source: " + appName);
            log.debug("applicationCopy() dest: " + destName);
         }

         while(true) {
            while(results.hasMoreElements()) {
               LDAPEntry entry = results.next();
               String dn = entry.getDN();
               if (log.isDebugEnabled()) {
                  log.debug("applicationCopy() found: " + dn);
               }

               if (dn.indexOf(appName + escapedComma) == -1 && dn.indexOf(appName + "::") == -1 && dn.indexOf(appName + ",") == -1) {
                  if (log.isDebugEnabled()) {
                     log.debug("applicationCopy() DN skipped");
                  }
               } else {
                  entry = conn.read(dn);
                  LDAPAttribute wlsCreatorInfo = entry.getAttribute("wlsCreatorInfo");
                  if (wlsCreatorInfo != null) {
                     String[] values = wlsCreatorInfo.getStringValueArray();
                     if (values != null && values.length > 0 && "deploy".equals(values[0])) {
                        if (log.isDebugEnabled()) {
                           log.debug("applicationCopy() deployed DN skipped");
                        }
                        continue;
                     }
                  }

                  LDAPAttributeSet attrSet = entry.getAttributeSet();

                  int i;
                  for(i = 0; i < excludedAttributes.length; ++i) {
                     attrSet.remove(excludedAttributes[i]);
                  }

                  for(i = 0; i < modifyAttributes.length; ++i) {
                     LDAPAttribute attr = attrSet.getAttribute(modifyAttributes[i]);
                     attrSet.remove(modifyAttributes[i]);
                     String modified = getUpdatedAttrValue(attr, appName, destName);
                     attrSet.add(new LDAPAttribute(modifyAttributes[i], modified));
                  }

                  entry = new LDAPEntry(getUpdatedValue(dn, appName, destName), attrSet);
                  if (log.isDebugEnabled()) {
                     log.debug("applicationCopy() write: " + entry.getDN());
                  }

                  try {
                     conn.add(entry);
                  } catch (LDAPException var20) {
                     if (var20.getLDAPResultCode() != 68) {
                        throw var20;
                     }

                     if (log.isDebugEnabled()) {
                        log.debug("applicationCopy() retry DN");
                     }

                     conn.delete(entry.getDN());
                     conn.add(entry);
                  }
               }
            }

            return;
         }
      }
   }

   private static String getUpdatedAttrValue(LDAPAttribute attr, String source, String dest) {
      if (attr == null) {
         return null;
      } else {
         String[] values = attr.getStringValueArray();
         return values != null && values.length != 0 ? getUpdatedValue(values[0], source, dest) : "";
      }
   }

   private static String getUpdatedValue(String value, String source, String dest) {
      int start = value.indexOf(source);
      if (start == -1) {
         return value;
      } else {
         StringBuffer sb = new StringBuffer(value);
         int end = start + source.length();
         sb.replace(start, end, dest);
         return sb.toString();
      }
   }

   private static String getStringAttr(LDAPEntry entry, String AttrName) {
      LDAPAttribute attr = entry.getAttribute(AttrName);
      if (attr == null) {
         return null;
      } else {
         Enumeration enumVals = attr.getStringValues();
         return enumVals != null && enumVals.hasMoreElements() ? (String)enumVals.nextElement() : null;
      }
   }

   public static void checkImportExportDataFormat(String providerType, String format, String[] supportedFormats) throws InvalidParameterException {
      if (format == null || format.length() == 0 || supportedFormats == null || supportedFormats.length == 0 || !format.equals(supportedFormats[0])) {
         throw new InvalidParameterException(SecurityLogger.getInvalidFormat(format));
      }
   }

   public static void importAuthenticatorLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtn");
      importDataFromLDIFT("Authenticator", ALL_ATN_LDIF, fileName, domainName, realmName, (String[])null, (EncryptionHelper)null, log, constraints);
   }

   public static void exportAuthenticatorLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtn");
      String[] encryptedAttribs = null;
      EncryptionHelper encryptionHelper = null;
      String[] LDAPBase = ALL_ATN_LDIF;
      if (constraints != null && !constraints.isEmpty()) {
         ArrayList reqConstraints = new ArrayList();
         Iterator allConstraints = constraints.keySet().iterator();

         while(allConstraints.hasNext()) {
            String constraint = (String)allConstraints.next();
            String mapping = (String)AtnConstraintsMap.get(constraint);
            if (mapping != null) {
               reqConstraints.add(mapping);
            } else if ("passwords".equals(constraint)) {
               String value = (String)constraints.get(constraint);
               if (!"cleartext".equalsIgnoreCase(value)) {
                  throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
               }

               encryptedAttribs = ATN_ENCRYPTED_ATTRIBS;
               encryptionHelper = new EncryptionHelperImpl(escaper, new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
            }
         }

         if (reqConstraints.isEmpty() && encryptionHelper == null) {
            throw new InvalidParameterException(SecurityLogger.getInvalidConstraintsNone());
         }

         if (!reqConstraints.isEmpty()) {
            int arraySize = reqConstraints.size();
            String[] tmpLDAPBase = new String[arraySize];

            for(int i = 0; i < arraySize; ++i) {
               tmpLDAPBase[i] = (String)reqConstraints.get(i);
            }

            LDAPBase = tmpLDAPBase;
         }
      }

      exportDataConvertToLDIFT("Authenticator", LDAPBase, (String)null, fileName, domainName, realmName, encryptedAttribs, encryptionHelper, log);
   }

   public static void importAuthorizerLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtz");
      if (constraints != null && !constraints.isEmpty()) {
         throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
      } else {
         importDataFromLDIFT("Authorizer", ALL_ATZ_LDIF, fileName, domainName, realmName, (String[])null, (EncryptionHelper)null, log);
      }
   }

   public static void exportAuthorizerLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtz");
      if (constraints != null && !constraints.isEmpty() && constraints.size() != 1 && constraints.get("") != null) {
         throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
      } else {
         exportDataConvertToLDIFT("Authorizer", ALL_ATZ_LDIF, (String)null, fileName, domainName, realmName, (String[])null, (EncryptionHelper)null, log);
      }
   }

   public static void exportAuthorizerLDIFT(String fileName, String cnFilter, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtz");
      if (cnFilter != null && cnFilter.length() != 0) {
         String cnFilterPolicy = escapedFilter(cnFilter, (String)null);
         exportDataConvertToLDIFT("Authorizer", ATZ_LDAP_BASE, cnFilterPolicy, fileName, domainName, realmName, (String[])null, (EncryptionHelper)null, log);
      } else {
         throw new InvalidParameterException(SecurityLogger.getNoSearchFilterSupplied());
      }
   }

   public static void importRoleMapperLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityRoleMap");
      if (constraints != null && !constraints.isEmpty()) {
         throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
      } else {
         importDataFromLDIFT("RoleMapper", ALL_ROLE_LDIF, fileName, domainName, realmName, (String[])null, (EncryptionHelper)null, log);
      }
   }

   public static void exportRoleMapperLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityRoleMap");
      if (constraints != null && !constraints.isEmpty()) {
         throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
      } else {
         exportDataConvertToLDIFT("RoleMapper", ALL_ROLE_LDIF, (String)null, fileName, domainName, realmName, (String[])null, (EncryptionHelper)null, log);
      }
   }

   public static void exportSAMLDataToLDIFT(String providerType, String fileName, String domainName, String realmName, String[] baseDN, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecuritySAMLLib");
      String[] encryptedAttribs = null;
      EncryptionHelper encryptionHelper = null;
      if (constraints != null && !constraints.isEmpty()) {
         ArrayList reqConstraints = new ArrayList();
         Iterator allConstraints = constraints.keySet().iterator();

         while(allConstraints.hasNext()) {
            String constraint = (String)allConstraints.next();
            String mapping = (String)AtnConstraintsMap.get(constraint);
            if (mapping != null) {
               reqConstraints.add(mapping);
            } else if ("passwords".equals(constraint)) {
               String value = (String)constraints.get(constraint);
               if (!"cleartext".equalsIgnoreCase(value)) {
                  throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
               }

               encryptedAttribs = SAML_ENCRYPTED_ATTRIBS;
               encryptionHelper = new EncryptionHelperImpl(escaper, new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
            }
         }
      }

      exportDataConvertToLDIFT(providerType, baseDN, (String)null, fileName, domainName, realmName, encryptedAttribs, encryptionHelper, log);
   }

   public static void importSAMLDataLDIFT(String providerType, String[] ldapBase, String domainName, String realmName, LoggerWrapper log) throws InvalidParameterException, ErrorCollectionException {
      String fileName = DomainDir.getSecurityDir() + File.separator + providerType + "Init.ldift";
      File defaultDataFile = new File(fileName);
      if (!defaultDataFile.exists()) {
         if (log.isDebugEnabled()) {
            log.debug("SAML Import Data file not specified");
         }

      } else {
         String[] encryptedAttribs = SAML_ENCRYPTED_ATTRIBS;
         EncryptionHelper encryptionHelper = new EncryptionHelperImpl(escaper, new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
         importDataFromLDIFT(providerType, ldapBase, fileName, domainName, realmName, encryptedAttribs, encryptionHelper, log);
      }
   }

   public static void exportRoleMapperLDIFT(String fileName, String cnFilter, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityRoleMap");
      if (cnFilter != null && cnFilter.length() != 0) {
         String cnFilterRole = escapedFilter(cnFilter, "*");
         exportDataConvertToLDIFT("RoleMapper", ROLE_LDAP_BASE, cnFilterRole, fileName, domainName, realmName, (String[])null, (EncryptionHelper)null, log);
      } else {
         throw new InvalidParameterException(SecurityLogger.getNoSearchFilterSupplied());
      }
   }

   public static void importCredentialMapperLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityCredMap");
      if (constraints != null && !constraints.isEmpty()) {
         throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
      } else {
         String[] encryptedAttribs = CRED_MAPPER_ENCRYPTED_ATTRIBS;
         EncryptionHelper encryptionHelper = new EncryptionHelperImpl(escaper, new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
         importDataFromLDIFT("CredentialMapper", ALL_CRED_LDIF, fileName, domainName, realmName, encryptedAttribs, encryptionHelper, log);
      }
   }

   public static void importPKICredentialMapperLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityCredMap");
      if (constraints != null && !constraints.isEmpty()) {
         throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
      } else {
         String[] encryptedAttribs = PKI_CRED_MAPPER_ENCRYPTED_ATTRIBS;
         EncryptionHelper encryptionHelper = new EncryptionHelperImpl(escaper, new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
         importDataFromLDIFT("PKICredentialMapper", ALL_PKI_CRED_LDIF, fileName, domainName, realmName, encryptedAttribs, encryptionHelper, log);
      }
   }

   public static void exportCredentialMapperLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityCredMap");
      String[] encryptedAttribs = null;
      EncryptionHelper encryptionHelper = null;
      if (constraints != null && !constraints.isEmpty()) {
         boolean found = false;
         Iterator allConstraints = constraints.keySet().iterator();

         while(allConstraints.hasNext()) {
            String constraint = (String)allConstraints.next();
            if ("passwords".equals(constraint)) {
               String value = (String)constraints.get(constraint);
               if ("cleartext".equalsIgnoreCase(value)) {
                  found = true;
               }
            }
         }

         if (!found) {
            throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
         }

         encryptedAttribs = CRED_MAPPER_ENCRYPTED_ATTRIBS;
         encryptionHelper = new EncryptionHelperImpl(escaper, new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
      }

      exportDataConvertToLDIFT("CredentialMapper", ALL_CRED_LDIF, (String)null, fileName, domainName, realmName, encryptedAttribs, encryptionHelper, log);
   }

   public static void exportPKICredentialMapperLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      LoggerWrapper log = LoggerWrapper.getInstance("SecurityCredMap");
      String[] encryptedAttribs = null;
      EncryptionHelper encryptionHelper = null;
      if (constraints != null && !constraints.isEmpty()) {
         boolean found = false;
         Iterator allConstraints = constraints.keySet().iterator();

         while(allConstraints.hasNext()) {
            String constraint = (String)allConstraints.next();
            if ("passwords".equals(constraint)) {
               String value = (String)constraints.get(constraint);
               if ("cleartext".equalsIgnoreCase(value)) {
                  found = true;
               }
            }
         }

         if (!found) {
            throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
         }

         encryptedAttribs = PKI_CRED_MAPPER_ENCRYPTED_ATTRIBS;
         encryptionHelper = new EncryptionHelperImpl(escaper, new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
      }

      exportDataConvertToLDIFT("PKICredentialMapper", ALL_PKI_CRED_LDIF, (String)null, fileName, domainName, realmName, encryptedAttribs, encryptionHelper, log);
   }

   private static void importDataFromLDIFT(String providerType, String[] LDAPBase, String fileName, String domainName, String realmName, String[] encryptedAttribs, EncryptionHelper encryptionHelper, LoggerWrapper log) throws InvalidParameterException, ErrorCollectionException {
      importDataFromLDIFT(providerType, LDAPBase, fileName, domainName, realmName, encryptedAttribs, encryptionHelper, log, (Properties)null);
   }

   private static void importDataFromLDIFT(String providerType, String[] LDAPBase, String fileName, String domainName, String realmName, String[] encryptedAttribs, EncryptionHelper encryptionHelper, LoggerWrapper log, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      importDataFromLDIFT(providerType, LDAPBase, fileName, domainName, realmName, encryptedAttribs, encryptionHelper, log, constraints, false);
   }

   private static void importDataFromLDIFT(String providerType, String[] LDAPBase, String fileName, String domainName, String realmName, String[] encryptedAttribs, EncryptionHelper encryptionHelper, LoggerWrapper log, Properties constraints, boolean skipUsers) throws InvalidParameterException, ErrorCollectionException {
      if (fileName != null && domainName != null && realmName != null) {
         String fullFileName = null;
         File importFile = new File(fileName);

         try {
            fullFileName = importFile.getCanonicalPath();
         } catch (IOException var23) {
            fullFileName = importFile.getAbsolutePath();
         }

         if (!importFile.isDirectory() && importFile.canRead() && importFile.length() != 0L) {
            RuntimeAccess c = ManagementService.getRuntimeAccess(kernelId);
            if (!c.isAdminServer()) {
               if (log.isDebugEnabled()) {
                  log.debug("Unable to import data on managed server");
               }

               throw new InvalidParameterException(SecurityLogger.getImportOnlyAvailableOnAdminServer());
            } else {
               if (log.isDebugEnabled()) {
                  log.debug(providerType + " Importing Data from '" + fullFileName + "'...");
               }

               String tmpFileName = null;

               try {
                  tmpFileName = convertLDIFTemplate(fileName, domainName, realmName, false);
               } catch (IOException var22) {
                  SecurityLogger.logFailureLoadingLDIFForProvider(providerType, fullFileName, var22);
                  throw new InvalidParameterException(SecurityLogger.getImportFileError(), var22);
               }

               ErrorCollectionException errors = new ErrorCollectionException("Import Errors: ");

               try {
                  if (log.isDebugEnabled()) {
                     log.debug("Import LDIF file: " + tmpFileName);
                  }

                  LDIFErrors VDEerrors = new LDIFErrors(errors);
                  DuplicateLDAPEntries duplicates = new DuplicateLDAPEntries();
                  (new LDIF()).importLDIF(tmpFileName, (InputStream)null, false, encryptedAttribs, encryptionHelper, VDEerrors, duplicates, constraints);
                  if (log.isDebugEnabled()) {
                     log.debug(duplicates.toString());
                  }

                  if (!duplicates.isEmpty()) {
                     duplicates.log(providerType, LDAPBase, domainName, realmName);
                  }
               } catch (Exception var24) {
                  if (log.isDebugEnabled()) {
                     log.debug("Import error: " + var24.toString(), var24);
                  }

                  SecurityLogger.logFailureLoadingLDIFForProvider(providerType, fullFileName, var24);
                  errors.add(var24);
                  throw errors;
               } finally {
                  (new File(tmpFileName)).delete();
               }

               if (!errors.isEmpty()) {
                  SecurityLogger.logFailureLoadingLDIFForProvider(providerType, fullFileName, errors);
                  throw errors;
               } else {
                  SecurityLogger.logLoadedLDIFForProvider(providerType, fullFileName);
               }
            }
         } else {
            throw new InvalidParameterException(SecurityLogger.getUnableToReadFile(fullFileName));
         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getInvalidNameSupplied());
      }
   }

   private static void exportDataConvertToLDIFT(String providerType, String[] LDAPBase, String filter, String fileName, String domainName, String realmName, String[] encryptedAttribs, EncryptionHelper encryptionHelper, LoggerWrapper log) throws InvalidParameterException, ErrorCollectionException {
      if (log.isDebugEnabled()) {
         log.debug(providerType + " Exporting Data...");
      }

      if (LDAPBase != null && LDAPBase.length != 0) {
         if (fileName != null && domainName != null && realmName != null) {
            String fullFileName = null;
            File exportFile = new File(fileName);

            try {
               fullFileName = exportFile.getCanonicalPath();
            } catch (IOException var45) {
               fullFileName = exportFile.getAbsolutePath();
            }

            File tmpFile = null;
            PrintWriter pw = null;
            ErrorCollectionException errors = new ErrorCollectionException("Export Errors: ");

            try {
               try {
                  pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));

                  try {
                     tmpFile = File.createTempFile("expwls", ".dat", (File)null);
                  } catch (IOException var44) {
                     throw new IOException(SecurityLogger.getCreateTempFileFailed(var44.getMessage()));
                  }

                  if (log.isDebugEnabled()) {
                     log.debug("Export file: " + fullFileName);
                     log.debug("Temporary file: " + tmpFile.getAbsolutePath());
                  }
               } catch (IOException var46) {
                  SecurityLogger.logFailureSavingLDIFForProvider(providerType, fullFileName, var46);
                  throw new InvalidParameterException(SecurityLogger.getExportFileError(), var46);
               }

               HashMap tokenMap = new HashMap();
               tokenMap.put("dc=" + domainName, "dc=@domain@");
               tokenMap.put("ou=" + realmName, "ou=@realm@");

               for(int i = 0; i < EXPORT_HEADER.length; ++i) {
                  pw.println(EXPORT_HEADER[i]);
               }

               LDIFErrors VDEerrors = new LDIFErrors(errors);

               for(int i = 0; i < LDAPBase.length; ++i) {
                  String line;
                  String in;
                  try {
                     line = "ou=" + LDAPBase[i] + ",ou=" + realmName + ",dc=" + domainName;
                     if (log.isDebugEnabled()) {
                        log.debug("Export LDIF with base: " + line);
                     }

                     in = null;
                     if (filter != null) {
                        if (log.isDebugEnabled()) {
                           log.debug("Export search filter: " + filter);
                        }

                        in = "(" + filter + ")";
                     }

                     boolean success = (new LDIF()).exportLDIF(line, in, tmpFile.getAbsolutePath(), encryptedAttribs, encryptionHelper, VDEerrors, true);
                     if (!success) {
                        continue;
                     }
                  } catch (Exception var49) {
                     if (log.isDebugEnabled()) {
                        log.debug("Export error: " + var49.toString(), var49);
                     }

                     SecurityLogger.logFailureSavingLDIFForProvider(providerType, fullFileName, var49);
                     errors.add(var49);
                     throw errors;
                  }

                  if (filter != null) {
                     HashMap baseTokenMap = new HashMap();
                     baseTokenMap.put("@LDAPbase@", LDAPBase[i]);

                     for(int j = 0; j < LDAP_BASE_HEADER.length; ++j) {
                        pw.println(convertTokens(LDAP_BASE_HEADER[j], baseTokenMap));
                     }
                  }

                  line = null;
                  BufferedReader in = null;

                  try {
                     in = new BufferedReader(new FileReader(tmpFile));

                     while((line = in.readLine()) != null) {
                        pw.println(convertTokens(line, tokenMap));
                     }
                  } catch (IOException var47) {
                     if (log.isDebugEnabled()) {
                        log.debug("Export error: " + var47.toString());
                     }

                     errors.add(var47);
                  } finally {
                     try {
                        if (in != null) {
                           in.close();
                           in = null;
                        }
                     } catch (IOException var43) {
                     }

                  }
               }
            } finally {
               if (pw != null) {
                  pw.close();
               }

               if (tmpFile != null) {
                  tmpFile.delete();
               }

            }

            if (!errors.isEmpty()) {
               SecurityLogger.logFailureSavingLDIFForProvider(providerType, fullFileName, errors);
               throw errors;
            } else {
               SecurityLogger.logSavedLDIFForProvider(providerType, fullFileName);
            }
         } else {
            throw new InvalidParameterException(SecurityLogger.getInvalidNameSupplied());
         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getNoBaseDataToExport());
      }
   }

   private static String escapedFilter(String resourceFilter, String roleFilter) {
      String filter = null;
      if (resourceFilter != null) {
         TextFilter res = new TextFilter(resourceFilter);
         filter = "cn=" + res.toString(escaper, "*");
         if (roleFilter != null) {
            TextFilter role = new TextFilter(roleFilter);
            filter = filter + "::" + role.toString(escaper, "*");
         }
      }

      return filter;
   }

   private static String convertTokens(String line, Map tokenMap) {
      if (tokenMap != null && line != null && line.length() != 0) {
         String source = line;
         StringBuffer destination = new StringBuffer(160);
         Iterator tokens = tokenMap.keySet().iterator();

         while(true) {
            int previous;
            String token;
            int index;
            do {
               if (!tokens.hasNext()) {
                  return source;
               }

               int index = false;
               previous = 0;
               token = (String)tokens.next();
               index = source.indexOf(token, previous);
            } while(index < 0);

            int tokenLen = token.length();
            String subToken = (String)tokenMap.get(token);

            do {
               destination.append(source.substring(previous, index));
               destination.append(subToken);
               previous = index + tokenLen;
               index = source.indexOf(token, previous);
            } while(index >= 0);

            destination.append(source.substring(previous));
            source = destination.toString();
            destination.setLength(0);
            destination.ensureCapacity(160);
         }
      } else {
         return line;
      }
   }

   private static void logMessageAndThrowProviderInitializationException(Loggable loggable, Exception exc) {
      loggable.log();
      throw new ProviderInitializationException(loggable.getMessage(), exc);
   }

   private static File getInitializedFile(String baseName, String providerType, String realmName) {
      File initializedFile = new File(EmbeddedLDAP.getEmbeddedLDAPDataDir() + File.separator + baseName + providerType + realmName + "Init.initialized");
      return initializedFile;
   }

   static {
      EXCLUDED_ON_COPY_ATTRS = ProviderUtilsService.EXCLUDED_ON_COPY_ATTRS;
      EXPORT_HEADER = new String[]{"dn: dc=@domain@", "dc: @domain@", "objectclass: top", "objectclass: domain", "", "dn: ou=@realm@,dc=@domain@", "ou: @realm@", "objectclass: top", "objectclass: organizationalUnit", ""};
      LDAP_BASE_HEADER = new String[]{"dn: ou=@LDAPbase@,ou=@realm@,dc=@domain@", "ou: @LDAPbase@", "objectclass: top", "objectclass: organizationalUnit", ""};
      ATZ_LDAP_BASE = new String[]{"EResource"};
      ROLE_LDAP_BASE = new String[]{"ERole"};
      ALL_ATN_LDIF = new String[]{"groups", "people"};
      ALL_ATZ_LDIF = new String[]{"EResource", "EPolicyCollectionInfo", "EPredicate"};
      ALL_ROLE_LDIF = new String[]{"ERole", "ERoleCollectionInfo", "EPredicate"};
      ALL_CRED_LDIF = new String[]{"CredentialMaps", "ResourceMaps"};
      ALL_PKI_CRED_LDIF = new String[]{"PublicCertificate,ou=ResourceMapping,ou=PKICredentialMap", "KeyPair,ou=ResourceMapping,ou=PKICredentialMap"};
      CRED_MAPPER_ENCRYPTED_ATTRIBS = new String[]{"principalPassword"};
      PKI_CRED_MAPPER_ENCRYPTED_ATTRIBS = new String[]{"keystoreAliasPassword"};
      ATN_ENCRYPTED_ATTRIBS = new String[]{"userpassword"};
      AtnConstraintsMap = new HashMap();
      AtnConstraintsMap.put("users", "people");
      AtnConstraintsMap.put("groups", "groups");
      SAML_ENCRYPTED_ATTRIBS = new String[]{"beaSAMLAuthPassword"};
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      escaper = new Escaping(new char[]{'@', '|', '&', '!', '=', '<', '>', '~', '(', ')', '*', ':', ',', ';', ' ', '"', '\'', '\t', '\\', '+', '/'});
      escapedComma = escaper.escapeString(",");
      escapedModule = escaper.escapeString(" module=");
      escapedContextPath = escaper.escapeString(" contextPath=");
      escapedApplication = escaper.escapeString(" application=");
      escapedTypeEJB = escaper.escapeString("type=<ejb>, application=");
      escapedTypeURL = escaper.escapeString("type=<url>, application=");
      escapedTypeAPP = escaper.escapeString("type=<app>,");
      escapedTypeEIS = escaper.escapeString("type=<eis>, application=");
      escapedTypeWSS = escaper.escapeString("type=<webservices>, application=");
      noAttrs = new String[]{"1.1"};
      escapedAppKey = escaper.escapeString("application=");
      escapedAppSearch = "cn=" + escaper.escapeString("type=") + "*" + escaper.escapeString(", application=");
   }

   @Service
   private static class ProviderUtilsServiceImpl implements ProviderUtilsService {
      public void convertBaseLDIFTemplate(String userName, String encyptPass, String templateName, String baseTemplateName) throws IOException {
         ProviderUtils.convertBaseLDIFTemplate(userName, encyptPass, templateName, baseTemplateName);
      }

      public void applicationDeleted(LDAPConnection conn, String baseDN, String applicationName, int compType, String compName, LoggerWrapper theLog) throws LDAPException {
         ProviderUtils.applicationDeleted(conn, baseDN, applicationName, compType, compName, theLog);
      }

      public void cleanupAfterCollection(LDAPConnection conn, String baseDN, String collectionName, long time, List removed, LoggerWrapper theLog) throws LDAPException {
         ProviderUtils.cleanupAfterCollection(conn, baseDN, collectionName, time, removed, theLog);
      }

      public void cleanupAfterAppDeploy(LDAPConnection conn, String baseDN, String applicationName, int compType, String compName, long time, LoggerWrapper theLog) throws LDAPException {
         ProviderUtils.cleanupAfterAppDeploy(conn, baseDN, applicationName, compType, compName, time, theLog);
      }

      public void applicationCopy(LDAPConnection conn, String baseDN, String sourceAppName, String destAppName, String[] modifyAttributes, String[] excludedAttributes, LoggerWrapper theLog) throws LDAPException {
         ProviderUtils.applicationCopy(conn, baseDN, sourceAppName, destAppName, modifyAttributes, excludedAttributes, theLog);
      }

      public void loadLDIFAuthenticatorTemplate(String domainName, RealmMBean realmMBean) {
         ProviderUtils.loadLDIFAuthenticatorTemplate(domainName, realmMBean);
      }

      public void checkImportExportDataFormat(String providerType, String format, String[] supportedFormats) throws InvalidParameterException {
         ProviderUtils.checkImportExportDataFormat(providerType, format, supportedFormats);
      }

      public void importAuthenticatorLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
         ProviderUtils.importAuthenticatorLDIFT(fileName, constraints, domainName, realmName);
      }

      public void exportAuthenticatorLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
         ProviderUtils.exportAuthenticatorLDIFT(fileName, constraints, domainName, realmName);
      }

      public void loadLDIFAuthorizerTemplate(String domainName, RealmMBean realmMBean) {
         ProviderUtils.loadLDIFAuthorizerTemplate(domainName, realmMBean);
      }

      public void loadLDIFRoleMapperTemplate(String domainName, RealmMBean realmMBean) {
         ProviderUtils.loadLDIFRoleMapperTemplate(domainName, realmMBean);
      }

      public void importRoleMapperLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
         ProviderUtils.importRoleMapperLDIFT(fileName, constraints, domainName, realmName);
      }

      public void exportRoleMapperLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
         ProviderUtils.exportRoleMapperLDIFT(fileName, constraints, domainName, realmName);
      }

      public void exportRoleMapperLDIFT(String fileName, String cnFilter, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
         ProviderUtils.exportRoleMapperLDIFT(fileName, cnFilter, domainName, realmName);
      }

      public AppConfigurationEntry.LoginModuleControlFlag getLoginModuleControlFlag(String flag) {
         return ProviderUtils.getLoginModuleControlFlag(flag);
      }

      public void importAuthorizerLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
         ProviderUtils.importAuthorizerLDIFT(fileName, constraints, domainName, realmName);
      }

      public void exportAuthorizerLDIFT(String fileName, Properties constraints, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
         ProviderUtils.exportAuthorizerLDIFT(fileName, constraints, domainName, realmName);
      }

      public void exportAuthorizerLDIFT(String fileName, String cnFilter, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
         ProviderUtils.exportAuthorizerLDIFT(fileName, cnFilter, domainName, realmName);
      }
   }

   private static final class EncryptionHelperImpl implements EncryptionHelper {
      private Escaping escaper = null;
      private ClearOrEncryptedService encrypter;

      public EncryptionHelperImpl(ClearOrEncryptedService encrypter) {
         this.encrypter = encrypter;
      }

      public EncryptionHelperImpl(Escaping escaper, ClearOrEncryptedService encrypter) {
         this.escaper = escaper;
         this.encrypter = encrypter;
      }

      public String encrypt(String clearOrEncryptedString) {
         String s = clearOrEncryptedString;
         if (this.escaper != null) {
            s = this.escaper.unescapeString(clearOrEncryptedString);
         }

         String ret = this.encrypter.encrypt(s);
         if (this.escaper != null) {
            ret = this.escaper.escapeString(ret);
         }

         return ret;
      }

      public String decrypt(String clearOrEncryptedString) {
         String s = clearOrEncryptedString;
         if (this.escaper != null) {
            s = this.escaper.unescapeString(clearOrEncryptedString);
         }

         String ret = this.encrypter.decrypt(s);
         if (this.escaper != null) {
            ret = this.escaper.escapeString(ret);
         }

         return ret;
      }

      public boolean isEncrypted(String clearOrEncryptedString) {
         String s = clearOrEncryptedString;
         if (this.escaper != null) {
            s = this.escaper.unescapeString(clearOrEncryptedString);
         }

         return this.encrypter.isEncrypted(s);
      }
   }

   private static final class DuplicateLDAPEntries implements DuplicateEntryCollection {
      private LinkedList duplicateEntryList = new LinkedList();

      public DuplicateLDAPEntries() {
      }

      public void add(String duplicateEntry) {
         this.duplicateEntryList.add(duplicateEntry);
      }

      public Collection getDuplicateEntries() {
         return this.duplicateEntryList;
      }

      public boolean isEmpty() {
         return this.duplicateEntryList.isEmpty();
      }

      public void log(String providerType, String[] LDAPBase, String domainName, String realmName) {
         Iterator iter = this.duplicateEntryList.iterator();

         while(true) {
            String dup;
            int index;
            do {
               boolean found;
               do {
                  do {
                     do {
                        if (!iter.hasNext()) {
                           return;
                        }

                        dup = (String)iter.next();
                     } while(dup.startsWith("dc=" + domainName));
                  } while(dup.startsWith("ou=" + realmName));

                  found = false;

                  for(index = 0; index < LDAPBase.length; ++index) {
                     if (dup.startsWith("ou=" + LDAPBase[index])) {
                        found = true;
                        break;
                     }
                  }
               } while(found);

               if (!"Authorizer".equals(providerType) && !"RoleMapper".equals(providerType)) {
                  break;
               }

               index = dup.indexOf("ou=EPredicate");
            } while(index >= 0);

            SecurityLogger.logDuplicateLDAPEntryForProvider(providerType, dup);
         }
      }

      public String toString() {
         if (this.duplicateEntryList.isEmpty()) {
            return "No Duplicate LDAP Entries Found";
         } else {
            StringBuffer sb = new StringBuffer("Duplicate LDAP Entries Found:");
            Iterator iter = this.duplicateEntryList.iterator();

            while(iter.hasNext()) {
               String dup = (String)iter.next();
               sb.append("\n\t\t " + dup);
            }

            return sb.toString();
         }
      }
   }

   private static final class LDIFErrors implements ErrorCollection {
      private ErrorCollectionException errors;

      public LDIFErrors(ErrorCollectionException errors) {
         this.errors = errors;
      }

      public void add(Throwable exception) {
         this.errors.add(exception);
      }

      public Collection getExceptions() {
         return this.errors.getExceptions();
      }

      public boolean isEmpty() {
         return this.errors.isEmpty();
      }
   }
}
