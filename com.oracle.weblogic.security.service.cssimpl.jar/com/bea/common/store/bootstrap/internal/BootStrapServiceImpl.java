package com.bea.common.store.bootstrap.internal;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.store.bootstrap.BootStrapPersistence;
import com.bea.common.store.bootstrap.BootStrapService;
import com.bea.common.store.bootstrap.Entry;
import com.bea.common.store.bootstrap.EntryConverter;
import com.bea.common.store.bootstrap.GlobalPolicyUpdate;
import com.bea.common.store.bootstrap.LDIFTParser;
import com.bea.common.store.bootstrap.LDIFTUtils;
import com.bea.common.store.service.StoreService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.management.security.RealmMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.spi.ProviderInitializationException;

public class BootStrapServiceImpl implements BootStrapService {
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
   private static final int STRING_BUF_LEN = 160;
   private static final int DBVERAUTHORIZER = 90;
   private static final int DBVERROLEMAPPER = 50;
   private static final int DBVERCREDENTIALMAPPER = 0;
   private static final int DBVERPKICREDENTIALMAPPER = 0;
   private static final int DBVERSAML2IDENTITYASSERTER = 101;
   private static final int DBVERSAML2CREDENTIALMAPPER = 81;
   private static final String domainToken = "@domain@";
   private static final String realmToken = "@realm@";
   private static final String XACML_BASENAME = "XACML";
   private static HashMap parentEntriesMap = new HashMap();
   private String envRootDir;
   private String securityRootDir;
   private String bootStrapVersioningDir;
   private String tempFileRootDir;

   public BootStrapServiceImpl(String envRootDir, String securityRootDir, String bootStrapVersioningDir, String tempFileRootDir) {
      this.envRootDir = envRootDir;
      this.securityRootDir = securityRootDir;
      this.bootStrapVersioningDir = bootStrapVersioningDir;
      this.tempFileRootDir = tempFileRootDir;
   }

   public void loadLDIFCredentialMapperTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, RealmMBean realmMBean) {
      this.loadLDIFCredentialMapperTemplate(log, (BootStrapPersistence)(new DefaultBootStrapPersistenceImpl(storeService)), entryConverter, domainName, realmMBean);
   }

   public void loadLDIFCredentialMapperTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, RealmMBean realmMBean) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      String realmName;
      if (realmMBean != null) {
         realmName = realmMBean.getName();
      } else {
         realmName = "myrealm";
      }

      this.loadLDIFTemplate(log, bootStrapPersistence, entryConverter, "CredentialMapper", 0, domainName, realmName);
   }

   public void loadLDIFXACMLAuthorizerTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      this.loadLDIFXACMLAuthorizerTemplate(log, (BootStrapPersistence)(new DefaultBootStrapPersistenceImpl(storeService)), entryConverter, domainName, realmName);
   }

   public void loadLDIFXACMLAuthorizerTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, String realmName) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      if (realmName == null) {
         realmName = "myrealm";
      }

      this.loadLDIFTemplate(log, bootStrapPersistence, entryConverter, "Authorizer", 90, domainName, realmName, "XACML");
   }

   public void loadLDIFXACMLRoleMapperTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      String initializedFilePath = this.getInitializedFilePath(storeService.getStoreId(), "RoleMapper", realmName, "XACML");
      boolean existsInit = (new File(initializedFilePath)).exists();
      this.loadLDIFXACMLRoleMapperTemplate(log, (BootStrapPersistence)(new DefaultBootStrapPersistenceImpl(storeService)), entryConverter, domainName, realmName);
   }

   public void loadLDIFXACMLRoleMapperTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, String realmName) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      if (realmName == null) {
         realmName = "myrealm";
      }

      this.loadLDIFTemplate(log, bootStrapPersistence, entryConverter, "RoleMapper", 50, domainName, realmName, "XACML");
   }

   public void loadLDIFPKICredentialMapperTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      if (realmName == null) {
         realmName = "myrealm";
      }

      this.loadLDIFTemplate(log, new DefaultBootStrapPersistenceImpl(storeService), entryConverter, "CredentialMapper", 0, domainName, realmName, "PKI");
   }

   public void loadLDIFSAML2CredentialMapperTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      this.loadLDIFSAML2CredentialMapperTemplate(log, (BootStrapPersistence)(new DefaultBootStrapPersistenceImpl(storeService)), entryConverter, domainName, realmName);
   }

   public void loadLDIFSAML2CredentialMapperTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, String realmName) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      if (realmName == null) {
         realmName = "myrealm";
      }

      this.loadLDIFTemplate(log, bootStrapPersistence, entryConverter, "CredentialMapper", 81, domainName, realmName, "SAML2");
   }

   public void loadLDIFSAML2IdentityAsserterTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      this.loadLDIFSAML2IdentityAsserterTemplate(log, (BootStrapPersistence)(new DefaultBootStrapPersistenceImpl(storeService)), entryConverter, domainName, realmName);
   }

   public void loadLDIFSAML2IdentityAsserterTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, String realmName) {
      if (domainName == null) {
         domainName = "mydomain";
      }

      if (realmName == null) {
         realmName = "myrealm";
      }

      this.loadLDIFTemplate(log, bootStrapPersistence, entryConverter, "IdentityAsserter", 101, domainName, realmName, "SAML2");
   }

   public void importSAMLDataLDIFT(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String providerType, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      boolean debugEnabled = log != null && log.isDebugEnabled();
      String fileName = this.securityRootDir + File.separator + providerType + "Init.ldift";
      File defaultDataFile = new File(fileName);
      if (!defaultDataFile.exists()) {
         if (debugEnabled) {
            log.debug("SAML Import Data file not specified");
         }

      } else {
         this.importDataFromLDIFT(log, new DefaultBootStrapPersistenceImpl(storeService), entryConverter, providerType, fileName, domainName, realmName);
      }
   }

   public void importSAML2DataLDIFT(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String providerType, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      boolean debugEnabled = log != null && log.isDebugEnabled();
      String fileName = this.securityRootDir + File.separator + providerType + "Init.ldift";
      File defaultDataFile = new File(fileName);
      if (!defaultDataFile.exists()) {
         if (debugEnabled) {
            log.debug("SAML2 Import Data file not specified");
         }

      } else {
         this.importDataFromLDIFT(log, new DefaultBootStrapPersistenceImpl(storeService), entryConverter, providerType, fileName, domainName, realmName);
      }
   }

   public void importPKICredentialMapperLDIFT(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String fileName, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      this.importDataFromLDIFT(log, new DefaultBootStrapPersistenceImpl(storeService), entryConverter, "PKICredentialMapper", fileName, domainName, realmName);
   }

   public void importCredentialMapperLDIFT(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String fileName, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      this.importDataFromLDIFT(log, new DefaultBootStrapPersistenceImpl(storeService), entryConverter, "CredentialMapper", fileName, domainName, realmName);
   }

   public void importCredentialMapperLDIFT(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String fileName, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      this.importDataFromLDIFT(log, bootStrapPersistence, entryConverter, "CredentialMapper", fileName, domainName, realmName);
   }

   public void exportSAMLDataToLDIFT(LoggerSpi log, String providerType, String fileName, String domainName, String realmName, List entries) throws InvalidParameterException, ErrorCollectionException {
      this.exportDataConvertToLDIFT(log, providerType, fileName, domainName, realmName, entries);
   }

   public void exportSAML2DataToLDIFT(LoggerSpi log, String providerType, String fileName, String domainName, String realmName, List entries) throws InvalidParameterException, ErrorCollectionException {
      this.exportDataConvertToLDIFT(log, providerType, fileName, domainName, realmName, entries);
   }

   public void exportPKICredentialMapperLDIFT(LoggerSpi log, String fileName, String domainName, String realmName, List entries) throws InvalidParameterException, ErrorCollectionException {
      this.exportDataConvertToLDIFT(log, "PKICredentialMapper", fileName, domainName, realmName, entries);
   }

   public void exportCredentialMapperLDIFT(LoggerSpi log, String fileName, String domainName, String realmName, List entries) throws InvalidParameterException, ErrorCollectionException {
      this.exportDataConvertToLDIFT(log, "CredentialMapper", fileName, domainName, realmName, entries);
   }

   public void exportDataConvertToLDIFT(LoggerSpi log, String providerType, String fileName, String domainName, String realmName, List entries) throws InvalidParameterException, ErrorCollectionException {
      boolean debugEnabled = log != null && log.isDebugEnabled();
      List combinedEntries = this.combineRootEntries(entries, providerType);
      if (debugEnabled) {
         log.debug(providerType + " Exporting Data...");
      }

      if (fileName != null && domainName != null && realmName != null) {
         String fullFileName = null;
         File exportFile = new File(fileName);

         try {
            fullFileName = exportFile.getCanonicalPath();
         } catch (IOException var43) {
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
               } catch (IOException var42) {
                  throw new IOException(SecurityLogger.getCreateTempFileFailed(var42.getMessage()));
               }

               if (debugEnabled) {
                  log.debug("Export file: " + fullFileName);
                  log.debug("Temporary file: " + tmpFile.getAbsolutePath());
               }
            } catch (IOException var47) {
               SecurityLogger.logFailureSavingLDIFForProvider(log, providerType, fullFileName, var47);
               throw new InvalidParameterException(SecurityLogger.getExportFileError(), var47);
            }

            HashMap tokenMap = new HashMap();
            tokenMap.put("dc=" + domainName, "dc=@domain@");
            tokenMap.put("ou=" + realmName, "ou=@realm@");

            try {
               LDIFTUtils.writeLDIFFile(tmpFile, combinedEntries);
            } catch (Exception var44) {
               if (debugEnabled) {
                  log.debug("Export error: " + var44.toString(), var44);
               }

               SecurityLogger.logFailureSavingLDIFForProvider(log, providerType, fullFileName, var44);
               errors.add(var44);
               throw errors;
            }

            String line = null;
            BufferedReader in = null;

            try {
               in = new BufferedReader(new FileReader(tmpFile));

               while((line = in.readLine()) != null) {
                  pw.println(convertTokens(line, tokenMap));
               }
            } catch (IOException var45) {
               if (debugEnabled) {
                  log.debug("Export error: " + var45.toString());
               }

               errors.add(var45);
            } finally {
               try {
                  if (in != null) {
                     in.close();
                     in = null;
                  }
               } catch (IOException var41) {
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
            SecurityLogger.logFailureSavingLDIFForProvider(log, providerType, fullFileName, errors);
            throw errors;
         } else {
            SecurityLogger.logSavedLDIFForProvider(log, providerType, fullFileName);
         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getInvalidNameSupplied());
      }
   }

   public void updateXACMLAuthorizerPolicies(LoggerSpi log, GlobalPolicyUpdate globalPolicyUpdate, StoreService storeService, String domainName, String realmName) {
      this.updateXACMLAuthorizerPolicies(log, globalPolicyUpdate, (BootStrapPersistence)(new DefaultBootStrapPersistenceImpl(storeService)), domainName, realmName);
   }

   public void updateXACMLRoleMapperPolicies(LoggerSpi log, GlobalPolicyUpdate globalPolicyUpdate, StoreService storeService, String domainName, String realmName) {
      this.updateXACMLRoleMapperPolicies(log, globalPolicyUpdate, (BootStrapPersistence)(new DefaultBootStrapPersistenceImpl(storeService)), domainName, realmName);
   }

   public void updateXACMLAuthorizerPolicies(LoggerSpi log, GlobalPolicyUpdate globalPolicyUpdate, BootStrapPersistence bootStrapPersistence, String domainName, String realmName) {
      this.UpdateGlobalPolicies(log, globalPolicyUpdate, bootStrapPersistence, domainName, realmName, "Authorizer");
   }

   public void updateXACMLRoleMapperPolicies(LoggerSpi log, GlobalPolicyUpdate globalPolicyUpdate, BootStrapPersistence bootStrapPersistence, String domainName, String realmName) {
      this.UpdateGlobalPolicies(log, globalPolicyUpdate, bootStrapPersistence, domainName, realmName, "RoleMapper");
   }

   private void UpdateGlobalPolicies(LoggerSpi log, GlobalPolicyUpdate globalPolicyUpdate, BootStrapPersistence bootStrapPersistence, String domainName, String realmName, String providerType) {
      String logPrefix = "BootStrapServiceImpl.UpdateGlobalPolicies - ";
      String storeIdCurrent = bootStrapPersistence.getStoreId();
      if (storeIdCurrent == null) {
         if (log != null) {
            log.error(logPrefix + "storeIdCurrent == null");
         }

      } else {
         String initializedFilePath = this.getInitializedFilePath(storeIdCurrent, providerType, realmName, "XACML");
         InitializedFileInfo fileInfo = this.getInitializedFileInfo(log, initializedFilePath, providerType);
         if (this.checkPolicyUpdate(storeIdCurrent, fileInfo)) {
            try {
               globalPolicyUpdate.updateGlobalPolicies();
            } catch (Exception var12) {
               SecurityLogger.logFailedUpdateGlobalPolicies(log, providerType, var12);
               throw new ProviderInitializationException("Update XACML policies error", var12);
            }

            InitializedFileHandler fileHandler = new InitializedFileHandler(initializedFilePath, providerType, log);
            if (fileInfo == null) {
               fileInfo = new InitializedFileInfo(storeIdCurrent, false);
               fileHandler.updateFile(true, fileInfo);
            } else {
               fileInfo.updatePolicy = false;
               fileHandler.updateFile(false, fileInfo);
            }

         }
      }
   }

   private boolean checkPolicyUpdate(String storeIdCurrent, InitializedFileInfo fileInfo) {
      if (fileInfo == null) {
         return true;
      } else {
         String storeIdInFile = fileInfo.storeId;
         boolean updatePolicy = fileInfo.updatePolicy;
         return !storeIdCurrent.equals(storeIdInFile) ? true : updatePolicy;
      }
   }

   private void loadLDIFTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String providerType, int providerDbVer, String domainName, String realmName) {
      this.loadLDIFTemplate(log, bootStrapPersistence, entryConverter, providerType, providerDbVer, domainName, realmName, "Default");
   }

   private void loadLDIFTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String providerType, int providerStoreVer, String domainName, String realmName, String baseName) {
      String logPrefix = "BootStrapServiceImpl.loadLDIFTemplate - ";
      boolean debugEnabled = log != null && log.isDebugEnabled();
      String storeIdCurrent = bootStrapPersistence.getStoreId();
      if (storeIdCurrent == null) {
         if (log != null) {
            log.error(logPrefix + "storeIdCurrent == null");
         }

      } else {
         String initializedFilePath = this.getInitializedFilePath(storeIdCurrent, providerType, realmName, baseName);
         InitializedFileHandler fileHandler = new InitializedFileHandler(initializedFilePath, providerType, log);
         InitializedFileInfo fileInfo = this.getInitializedFileInfo(log, initializedFilePath, providerType);
         boolean loaded = false;
         if (fileInfo != null) {
            int storeVerInFile = fileInfo.storeVersion;
            String storeIdInFile = fileInfo.storeId;
            if (storeVerInFile < 0 || storeIdInFile == null) {
               if (log != null) {
                  log.error(logPrefix + "failed to read file info");
               }

               return;
            }

            if (!storeIdCurrent.equals(storeIdInFile)) {
               if (log != null) {
                  log.info(logPrefix + "We switched to a different store, will load full LDIFT.");
               }
            } else if (storeVerInFile < providerStoreVer) {
               if (log != null) {
                  log.info(logPrefix + providerType + " is not up to date. Updating version " + storeVerInFile + " to " + providerStoreVer + ".");
               }

               for(int updateFileNo = storeVerInFile + 1; updateFileNo <= providerStoreVer; ++updateFileNo) {
                  if (this.loadUpdateLDIFTemplate(log, bootStrapPersistence, entryConverter, providerType, updateFileNo, domainName, realmName, baseName)) {
                     fileInfo.storeVersion = updateFileNo;
                     fileHandler.updateFile(false, fileInfo);
                  }
               }

               loaded = true;
            } else {
               if (debugEnabled) {
                  log.debug(logPrefix + providerType + " is up to date at version " + providerStoreVer + " in the same store.");
               }

               SecurityLogger.logLDAPPreviouslyInitialized(log, providerType);
               loaded = true;
            }
         } else if (log != null) {
            log.info(logPrefix + "Did not find " + initializedFilePath + ", will load full LDIFT.");
         }

         if (!loaded) {
            if (fileInfo == null) {
               fileInfo = new InitializedFileInfo(providerStoreVer, storeIdCurrent);
            } else {
               fileInfo.storeId = storeIdCurrent;
               fileInfo.storeVersion = providerStoreVer;
            }

            this.loadFullLDIFTemplate(log, bootStrapPersistence, entryConverter, providerType, domainName, realmName, baseName);
            fileHandler.updateFile(true, fileInfo);
         }

      }
   }

   private String getInitializedFilePath(String storeIdCurrent, String providerType, String realmName, String baseName) {
      int index = storeIdCurrent.indexOf("_");
      String storeType;
      if (index < 0) {
         storeType = "";
      } else {
         storeType = storeIdCurrent.substring(0, index);
      }

      String baseNameInFile = storeType.equals("file") ? "FileBased" + baseName : baseName;
      return this.bootStrapVersioningDir + File.separator + baseNameInFile + providerType + realmName + "Init.initialized";
   }

   private InitializedFileInfo getInitializedFileInfo(LoggerSpi log, String initializedFilePath, String providerType) {
      String logPrefix = "BootStrapServiceImpl.getInitializedFileInfo - ";
      boolean debugEnabled = log != null && log.isDebugEnabled();
      File initializedFile = new File(initializedFilePath);
      InitializedFileHandler fileHandler = new InitializedFileHandler(initializedFilePath, providerType, log);
      if (debugEnabled) {
         log.debug(logPrefix + "Looking for " + initializedFilePath);
      }

      InitializedFileInfo fileInfo = null;
      boolean loaded = false;
      if (initializedFile.exists()) {
         if (debugEnabled) {
            log.debug(logPrefix + "Found " + initializedFilePath);
         }

         return fileHandler.readFile();
      } else {
         if (debugEnabled) {
            log.debug(logPrefix + "The file " + initializedFilePath + "was not found");
         }

         return null;
      }
   }

   private void loadFullLDIFTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String providerType, String domainName, String realmName, String baseName) {
      boolean debugEnabled = log != null && log.isDebugEnabled();
      String templateFileName = null;
      File file3 = new File(this.securityRootDir + File.separator + baseName + providerType + realmName + "Init.ldift");
      File file2 = new File(this.securityRootDir + File.separator + baseName + providerType + "Init.ldift");
      File file1 = new File(this.envRootDir + File.separator + "lib" + File.separator + baseName + providerType + "Init.ldift");
      String file3Name = file3.getAbsolutePath();
      String file2Name = file2.getAbsolutePath();
      String file1Name = file1.getAbsolutePath();
      if (debugEnabled) {
         log.debug("looking for " + file3Name);
         log.debug("looking for " + file2Name);
         log.debug("looking for " + file1Name);
      }

      if (file3.exists()) {
         templateFileName = file3Name;
         if (file3.length() == 0L) {
            if (debugEnabled) {
               log.debug(file3Name + " found,  but is empty. Will not load");
            }

            if ("CredentialMapper".equals(providerType)) {
               SecurityLogger.logLDIFEmptyForCredentialProvider(log, providerType, file3Name);
            } else {
               SecurityLogger.logLDIFEmptyForProvider(log, providerType, file3Name);
            }

            return;
         }

         if (debugEnabled) {
            log.debug(file3Name + " exists");
         }
      } else if (file2.exists()) {
         templateFileName = file2Name;
         if (file2.length() == 0L) {
            if (debugEnabled) {
               log.debug(file2Name + " found,  but is empty. Will not load");
            }

            if ("CredentialMapper".equals(providerType)) {
               SecurityLogger.logLDIFEmptyForCredentialProvider(log, providerType, file2Name);
            } else {
               SecurityLogger.logLDIFEmptyForProvider(log, providerType, file2Name);
            }

            return;
         }

         if (debugEnabled) {
            log.debug(file2Name + " exists");
         }
      } else {
         if (!file1.exists()) {
            if (debugEnabled) {
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
            SecurityLogger.logLDIFNotFoundForProvider(log, providerType, filesLookedFor.toString());
            return;
         }

         templateFileName = file1Name;
         if (file1.length() == 0L) {
            if (debugEnabled) {
               log.debug(file1Name + " found,  but is empty. Will not load");
            }

            if ("CredentialMapper".equals(providerType)) {
               SecurityLogger.logLDIFEmptyForCredentialProvider(log, providerType, file1Name);
            } else {
               SecurityLogger.logLDIFEmptyForProvider(log, providerType, file1Name);
            }

            return;
         }

         if (debugEnabled) {
            log.debug(file1Name + " exists");
         }
      }

      SecurityLogger.logInitializingLDIFForProvider(log, providerType, templateFileName);
      String tmpFileName = null;

      LoggableMessageSpi loggableMessageSpi;
      try {
         if (debugEnabled) {
            log.debug("calling convertLDIFTemplate " + templateFileName);
         }

         tmpFileName = this.convertLDIFTemplate(log, templateFileName, domainName, realmName);
      } catch (IOException var25) {
         SecurityLogger.logFailureLoadingLDIFForProvider(log, providerType, templateFileName, var25);
         loggableMessageSpi = SecurityLogger.logFailureLoadingLDIFForProviderLoggable(providerType, templateFileName, var25);
         throw new ProviderInitializationException(loggableMessageSpi.getFormattedMessageBody(), var25);
      }

      if (tmpFileName != null) {
         try {
            if (debugEnabled) {
               log.debug("calling importLDIF " + tmpFileName);
            }

            this.importLDIF(log, bootStrapPersistence, entryConverter, tmpFileName);
         } catch (Exception var23) {
            SecurityLogger.logFailureLoadingLDIFForProvider(log, providerType, templateFileName, var23);
            loggableMessageSpi = SecurityLogger.logFailureLoadingLDIFForProviderLoggable(providerType, templateFileName, var23);
            throw new ProviderInitializationException(loggableMessageSpi.getFormattedMessageBody(), var23);
         } finally {
            (new File(tmpFileName)).delete();
         }
      }

      SecurityLogger.logLoadedLDIFForProvider(log, providerType, templateFileName);
   }

   private boolean loadUpdateLDIFTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String providerType, int updateFileNo, String domainName, String realmName, String baseName) {
      boolean debugEnabled = log != null && log.isDebugEnabled();
      File updateFile = new File(this.envRootDir + File.separator + "lib" + File.separator + baseName + providerType + "Update_" + updateFileNo + ".ldift");
      String updateFileName = updateFile.getAbsolutePath();
      if (debugEnabled) {
         log.debug("looking for " + updateFileName);
      }

      if (updateFile.exists()) {
         if (updateFile.length() == 0L) {
            if (debugEnabled) {
               log.debug(updateFileName + " found,  but is empty. Will not load");
            }

            if ("CredentialMapper".equals(providerType)) {
               SecurityLogger.logLDIFEmptyForCredentialProvider(log, providerType, updateFileName);
            } else {
               SecurityLogger.logLDIFEmptyForProvider(log, providerType, updateFileName);
            }

            return true;
         } else {
            if (debugEnabled) {
               log.debug(updateFileName + " exists");
            }

            SecurityLogger.logUpdatingLDIFForProvider(log, providerType, updateFileName, updateFileNo);
            String tmpFileName = null;

            LoggableMessageSpi loggableMessageSpi;
            try {
               if (debugEnabled) {
                  log.debug("calling convertLDIFTemplate " + updateFileName);
               }

               tmpFileName = this.convertLDIFTemplate(log, updateFileName, domainName, realmName);
            } catch (IOException var21) {
               SecurityLogger.logFailureLoadingLDIFForProvider(log, providerType, updateFileName, var21);
               loggableMessageSpi = SecurityLogger.logFailureLoadingLDIFForProviderLoggable(providerType, updateFileName, var21);
               throw new ProviderInitializationException(loggableMessageSpi.getFormattedMessageBody(), var21);
            }

            if (tmpFileName != null) {
               try {
                  if (debugEnabled) {
                     log.debug("calling importLDIF " + tmpFileName);
                  }

                  this.importLDIF(log, bootStrapPersistence, entryConverter, tmpFileName);
               } catch (Exception var19) {
                  SecurityLogger.logFailureLoadingLDIFForProvider(log, providerType, updateFileName, var19);
                  loggableMessageSpi = SecurityLogger.logFailureLoadingLDIFForProviderLoggable(providerType, updateFileName, var19);
                  throw new ProviderInitializationException(loggableMessageSpi.getFormattedMessageBody(), var19);
               } finally {
                  (new File(tmpFileName)).delete();
               }
            }

            SecurityLogger.logLoadedLDIFForProvider(log, providerType, updateFileName);
            return true;
         }
      } else {
         if (debugEnabled) {
            log.debug("The " + updateFileName + " ldift update file can not be found, skipping");
         }

         return true;
      }
   }

   private String convertLDIFTemplate(LoggerSpi log, String fileName, String domainName, String realmName) throws IOException {
      return this.convertLDIFTemplate(log, fileName, domainName, realmName, true);
   }

   private String convertLDIFTemplate(LoggerSpi log, String fileName, String domainName, String realmName, boolean fallback) throws IOException {
      boolean lineConverted = false;
      File tmpFile = null;

      try {
         tmpFile = File.createTempFile("impwls", ".dat", (File)null);
      } catch (IOException var15) {
         boolean failure = true;
         if (fallback) {
            try {
               tmpFile = File.createTempFile("impwls", ".dat", new File(this.tempFileRootDir));
               failure = false;
            } catch (IOException var13) {
            }
         }

         if (failure) {
            throw new IOException(SecurityLogger.getCreateTempFileFailed(var15.getMessage()));
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
      } catch (IOException var14) {
         bw.close();
         tmpFile.delete();
         in.close();
         throw var14;
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

   private void importDataFromLDIFT(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String providerType, String fileName, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      boolean debugEnabled = log != null && log.isDebugEnabled();
      if (fileName != null && domainName != null && realmName != null) {
         String fullFileName = null;
         File importFile = new File(fileName);

         try {
            fullFileName = importFile.getCanonicalPath();
         } catch (IOException var20) {
            fullFileName = importFile.getAbsolutePath();
         }

         if (!importFile.isDirectory() && importFile.canRead() && importFile.length() != 0L) {
            if (debugEnabled) {
               log.debug(providerType + " Importing Data from '" + fullFileName + "'...");
            }

            String tmpFileName = null;

            try {
               tmpFileName = this.convertLDIFTemplate(log, fileName, domainName, realmName, false);
            } catch (IOException var19) {
               SecurityLogger.logFailureLoadingLDIFForProvider(log, providerType, fullFileName, var19);
               throw new InvalidParameterException(SecurityLogger.getImportFileError(), var19);
            }

            ErrorCollectionException errors = new ErrorCollectionException("Import Errors: ");

            try {
               if (debugEnabled) {
                  log.debug("Import LDIF file: " + tmpFileName);
               }

               this.importLDIF(log, bootStrapPersistence, entryConverter, tmpFileName);
            } catch (Exception var21) {
               if (debugEnabled) {
                  log.debug("Import error: " + var21.toString(), var21);
               }

               SecurityLogger.logFailureLoadingLDIFForProvider(log, providerType, fullFileName, var21);
               errors.add(var21);
               throw errors;
            } finally {
               (new File(tmpFileName)).delete();
            }

            if (!errors.isEmpty()) {
               SecurityLogger.logFailureLoadingLDIFForProvider(log, providerType, fullFileName, errors);
               throw errors;
            } else {
               SecurityLogger.logLoadedLDIFForProvider(log, providerType, fullFileName);
            }
         } else {
            throw new InvalidParameterException(SecurityLogger.getUnableToReadFile(fullFileName));
         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getInvalidNameSupplied());
      }
   }

   private static String getDn(Entry e) {
      StringBuffer sb = new StringBuffer();
      if (e != null) {
         Map attributes = e.getAttributes();
         Collection dn = (Collection)attributes.get("dn");
         Iterator i = dn.iterator();

         while(i.hasNext()) {
            String value = (String)i.next();
            sb.append(value);
            sb.append(' ');
         }
      }

      return sb.toString();
   }

   private void importLDIF(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String filePath) throws Exception {
      boolean debugEnabled = log != null && log.isDebugEnabled();
      Set alreadySeen = new HashSet();
      Collection importObjects = new ArrayList();
      List entries = LDIFTParser.parse(new File(filePath));

      for(int i = 0; i < entries.size(); ++i) {
         Entry entry = (Entry)entries.get(i);
         String dn = getDn(entry);
         if (!alreadySeen.contains(dn)) {
            alreadySeen.add(dn);
            Object businessObject = entryConverter.convert(entry);
            if (businessObject != null) {
               if (debugEnabled) {
                  log.debug("converted business object: " + businessObject.toString());
               }

               importObjects.add(businessObject);
            } else if (debugEnabled) {
               log.debug("converted business object is null");
            }
         } else if (debugEnabled) {
            log.debug("ignoring duplicate entry: " + dn);
         }
      }

      if (importObjects.size() == 0) {
         if (debugEnabled) {
            log.debug("No business objects to be imported");
         }

      } else {
         if (debugEnabled) {
            log.debug("Total: " + importObjects.size() + " business objects to be imported");
         }

         Collection importObjects = bootStrapPersistence.postProcessObjects(importObjects);

         try {
            Iterator it = importObjects.iterator();

            while(it.hasNext()) {
               Object next = it.next();
               if (bootStrapPersistence.has(next)) {
                  it.remove();
               }
            }

            bootStrapPersistence.makePersistentAll(importObjects);
            if (debugEnabled) {
               log.debug("Total: " + importObjects.size() + " business objects imported");
            }
         } catch (Exception var17) {
            if (debugEnabled) {
               log.error("import ldift error", var17);
            }

            throw var17;
         } catch (Throwable var18) {
            if (log != null) {
               log.error("Caught Throwable " + var18.getClass().getName());
               log.error("Throwable message: " + var18.getMessage());
               log.error("Throwable", var18);
            }

            throw new Exception(var18);
         } finally {
            bootStrapPersistence.close();
         }

      }
   }

   private List combineRootEntries(List entries, String providerType) {
      ArrayList list = new ArrayList();
      List rootEntries = (List)parentEntriesMap.get(providerType);
      if (rootEntries != null) {
         list.addAll(rootEntries);
      }

      list.addAll(entries);
      return list;
   }

   private static void buildSAMLCredMapperRootEntries(Map rootEntriesMap) {
      List rootEntries = buildRegistryRootEntries("SAMLRelyingPartyRegistry");
      rootEntriesMap.put("SAMLCredentialMapper", rootEntries);
   }

   private static void buildSAMLIdentityAsserterRootEntries(Map rootEntriesMap) {
      List rootEntries = buildRegistryRootEntries("SAMLAssertingPartyRegistry");
      rootEntriesMap.put("SAMLIdentityAssertor", rootEntries);
   }

   private static List buildRegistryRootEntries(String registryName) {
      ArrayList rootEntries = new ArrayList();
      buildBaseRootEntries(rootEntries);
      HashMap attributes = new HashMap();
      LDIFTUtils.buildBaseAttributes(attributes, "ou=" + registryName + ", ou=" + "@realm@" + ", dc=" + "@domain@", registryName, new String[]{"top", "organizationalUnit"});
      LDIFTParser.EntryImpl entry = new LDIFTParser.EntryImpl(attributes, (Map)null);
      rootEntries.add(entry);
      attributes = new HashMap();
      LDIFTUtils.buildBaseAttributes(attributes, "ou=SAMLCertificateRegistry, ou=@realm@, dc=@domain@", "SAMLCertificateRegistry", new String[]{"top", "organizationalUnit"});
      entry = new LDIFTParser.EntryImpl(attributes, (Map)null);
      rootEntries.add(entry);
      return rootEntries;
   }

   private static void buildDefaultCredentialMapperRootEntries(Map rootEntriesMap) {
      List rootEntries = new ArrayList();
      buildBaseRootEntries(rootEntries);
      HashMap attributes = new HashMap();
      LDIFTUtils.buildBaseAttributes(attributes, "ou=CredentialMaps, ou=@realm@, dc=@domain@", "CredentialMaps", new String[]{"top", "organizationalUnit"});
      LDIFTParser.EntryImpl entry = new LDIFTParser.EntryImpl(attributes, (Map)null);
      rootEntries.add(entry);
      attributes = new HashMap();
      LDIFTUtils.buildBaseAttributes(attributes, "ou=ResourceMaps, ou=@realm@, dc=@domain@", "ResourceMaps", new String[]{"top", "organizationalUnit"});
      entry = new LDIFTParser.EntryImpl(attributes, (Map)null);
      rootEntries.add(entry);
      rootEntriesMap.put("CredentialMapper", rootEntries);
   }

   private static void buildBaseRootEntries(List baseEntries) {
      Map attributes = new HashMap();
      String topDn = "dc=@domain@";
      LDIFTUtils.addAttribute(attributes, "dn", topDn);
      ArrayList list = new ArrayList();
      list.add("top");
      list.add("domain");
      attributes.put("objectClass", list);
      LDIFTParser.EntryImpl entry = new LDIFTParser.EntryImpl(attributes, (Map)null);
      baseEntries.add(entry);
      attributes = new HashMap();
      LDIFTUtils.buildBaseAttributes(attributes, "ou=@realm@, dc=@domain@", "@realm@", new String[]{"top", "organizationalUnit"});
      entry = new LDIFTParser.EntryImpl(attributes, (Map)null);
      baseEntries.add(entry);
   }

   static {
      buildSAMLCredMapperRootEntries(parentEntriesMap);
      buildSAMLIdentityAsserterRootEntries(parentEntriesMap);
      buildDefaultCredentialMapperRootEntries(parentEntriesMap);
   }

   private static class InitializedFileInfo {
      int storeVersion = -1;
      String storeId = null;
      boolean updatePolicy = true;

      InitializedFileInfo() {
      }

      InitializedFileInfo(int dbVer, String storeId) {
         this.storeVersion = dbVer;
         this.storeId = storeId;
      }

      InitializedFileInfo(String storeId, boolean updatePolicy) {
         this.storeId = storeId;
         this.updatePolicy = updatePolicy;
      }

      public String toString() {
         return "store version( " + this.storeVersion + " ), Id ( " + this.storeId + " )";
      }
   }

   private static class InitializedFileHandler {
      private static String STORE_ID_TAG = "StoreId";
      private static String POLICY_UPDATE_TAG = "PolicyUpdate";
      private final String initializedFilePath;
      private final String providerType;
      private final LoggerSpi logger;

      InitializedFileHandler(String initializedFilePath, String providerType, LoggerSpi logger) {
         this.initializedFilePath = initializedFilePath;
         this.providerType = providerType;
         this.logger = logger;
      }

      InitializedFileInfo readFile() {
         String logPrefix = "InitializedFileHandler.readFile - ";
         boolean debugEnabled = this.logger != null && this.logger.isDebugEnabled();
         FileInputStream is = null;
         InitializedFileInfo fileInfo = new InitializedFileInfo();

         try {
            LoggableMessageSpi loggableMessageSpi;
            try {
               is = new FileInputStream(this.initializedFilePath);
               Properties props = new Properties();
               props.load(is);
               String value = props.getProperty(this.providerType);
               fileInfo.storeVersion = value == null ? 0 : Integer.parseInt(value);
               value = props.getProperty(STORE_ID_TAG);
               if (value == null) {
                  if (debugEnabled) {
                     this.logger.debug(logPrefix + this.initializedFilePath + " does not contain " + STORE_ID_TAG);
                  }

                  fileInfo.storeId = "";
               } else {
                  fileInfo.storeId = value;
               }

               if ("Authorizer".equals(this.providerType) || "RoleMapper".equals(this.providerType)) {
                  value = props.getProperty(POLICY_UPDATE_TAG);
                  fileInfo.updatePolicy = value == null ? true : Boolean.parseBoolean(value);
               }

               if (debugEnabled) {
                  this.logger.debug(logPrefix + this.providerType + " " + fileInfo.toString());
               }
            } catch (IOException var15) {
               if (debugEnabled) {
                  this.logger.debug(logPrefix + "While opening " + this.initializedFilePath + " got an exception: " + var15);
               }

               SecurityLogger.logUnreadableWLSProviderUpdateFile(this.logger, this.providerType, this.initializedFilePath, var15);
               loggableMessageSpi = SecurityLogger.logUnreadableWLSProviderUpdateFileLoggable(this.providerType, this.initializedFilePath, var15);
               throw new ProviderInitializationException(loggableMessageSpi.getFormattedMessageBody(), var15);
            } catch (NumberFormatException var16) {
               if (debugEnabled) {
                  this.logger.debug(logPrefix + "While attempting to parse a version number in file " + this.initializedFilePath + " got an exception: " + var16);
               }

               SecurityLogger.logMisconfiguredWLSProviderUpdateFile(this.logger, this.providerType, this.initializedFilePath, var16);
               loggableMessageSpi = SecurityLogger.logMisconfiguredWLSProviderUpdateFileLoggable(this.providerType, this.initializedFilePath, var16);
               throw new ProviderInitializationException(loggableMessageSpi.getFormattedMessageBody(), var16);
            }
         } finally {
            try {
               if (is != null) {
                  is.close();
               }
            } catch (IOException var14) {
            }

         }

         return fileInfo;
      }

      boolean updateFile(boolean freshUpdate, InitializedFileInfo fileInfo) {
         String logPrefix = "InitializedFileHandler.updateFile - ";
         boolean debugEnabled = this.logger != null && this.logger.isDebugEnabled();
         String outputPath = this.initializedFilePath;
         int providerStoreVer = fileInfo.storeVersion;
         ProviderInitializationException initExp = null;
         Properties props = new Properties();
         LoggableMessageSpi loggableMessageSpi;
         if (!freshUpdate) {
            FileInputStream is = null;

            try {
               is = new FileInputStream(this.initializedFilePath);
               props.load(is);
            } catch (IOException var32) {
               if (debugEnabled) {
                  this.logger.debug(logPrefix + "Unable to load properties from file: " + this.initializedFilePath + " exception: " + var32);
               }

               SecurityLogger.logFailureUpdatingLDIFVersion(this.logger, this.providerType, this.initializedFilePath, providerStoreVer, var32);
               loggableMessageSpi = SecurityLogger.logFailureUpdatingLDIFVersionLoggable(this.providerType, this.initializedFilePath, providerStoreVer, var32);
               throw new ProviderInitializationException(loggableMessageSpi.getFormattedMessageBody(), var32);
            } finally {
               try {
                  if (is != null) {
                     is.close();
                     is = null;
                  }
               } catch (IOException var31) {
                  if (debugEnabled) {
                     this.logger.debug(logPrefix + "Unable to close file : " + this.initializedFilePath + " exception: " + var31);
                  }

                  initExp = new ProviderInitializationException(var31.getMessage(), var31);
               }

            }
         }

         if (initExp != null) {
            throw initExp;
         } else {
            props.setProperty(this.providerType, String.valueOf(providerStoreVer));
            props.setProperty(STORE_ID_TAG, fileInfo.storeId);
            props.setProperty(POLICY_UPDATE_TAG, Boolean.toString(fileInfo.updatePolicy));
            if (debugEnabled) {
               this.logger.debug("Set " + this.providerType + " to version " + providerStoreVer + " in file: " + this.initializedFilePath);
            }

            FileOutputStream os = null;

            try {
               os = new FileOutputStream(outputPath);
               props.store(os, "The version of LDIFT loaded for the WLS default " + this.providerType + " provider. Last update was to version " + providerStoreVer);
            } catch (IOException var35) {
               if (debugEnabled) {
                  this.logger.debug(logPrefix + "Exception while trying to update: " + outputPath + " " + var35);
               }

               SecurityLogger.logFailureUpdatingLDIFVersion(this.logger, this.providerType, outputPath, providerStoreVer, var35);
               loggableMessageSpi = SecurityLogger.logFailureUpdatingLDIFVersionLoggable(this.providerType, outputPath, providerStoreVer, var35);
               throw new ProviderInitializationException(loggableMessageSpi.getFormattedMessageBody(), var35);
            } finally {
               try {
                  if (os != null) {
                     os.close();
                  }
               } catch (IOException var34) {
                  if (debugEnabled) {
                     this.logger.debug(logPrefix + "Exception while trying to close: " + outputPath + " " + var34);
                  }
               }

            }

            return true;
         }
      }
   }
}
