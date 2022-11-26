package weblogic.management.upgrade;

import com.bea.xml.XmlValidationError;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.AccessController;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.VersionConstants;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ConfigFileHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static int configurationVersion;
   private static boolean productionModeEnabled;
   private static byte[] old_format_version = "ConfigurationVersion=\"".getBytes();
   private static byte[] new_format_version = "configuration-version>".getBytes();
   private static byte[] old_format_mode = "ProductionModeEnabled=\"".getBytes();
   private static byte[] new_format_mode = "production-mode-enabled>".getBytes();
   private static int UTF8_ZERO = 48;
   private static int UTF8_NINE = 57;

   public static boolean isUpgradeNeeded() throws ConfigFileException {
      File domainDir = new File(DomainDir.getRootDir());
      if (!ManagementService.getPropertyService(kernelId).isAdminServer()) {
         File configDir = new File(domainDir, "config");
         File configCfg = new File(configDir, "config.xml");
         BufferedReader reader = getBufferedReader(configCfg);
         if (reader != null) {
            productionModeEnabled = getProductionMode(reader);
            ensureClosed(reader);
         }

         return false;
      } else {
         boolean upgradeNeeded = isUpgradeNeeded(domainDir);
         if (!upgradeNeeded) {
            return false;
         } else {
            boolean forceImplicitUpgrade = false;
            if (System.getProperty("weblogic.ForceImplicitUpgradeIfNeeded") != null) {
               forceImplicitUpgrade = Boolean.getBoolean("weblogic.ForceImplicitUpgradeIfNeeded");
            }

            return forceImplicitUpgrade ? true : true;
         }
      }
   }

   public static boolean isUpgradeNeeded(File rootDir) throws ConfigFileException {
      boolean result = false;
      ensureOneOrFewerConfigFiles(rootDir);
      File configDir = new File(rootDir, "config");
      File configCfg = new File(configDir, "config.xml");
      File rootCfg = new File(rootDir, BootStrap.getConfigFileName());
      BufferedReader reader;
      int version;
      String absPath;
      if (configCfg.exists()) {
         reader = getBufferedReader(configCfg);
         if (reader != null) {
            version = getConfigVersion(reader);
            configurationVersion = version;
            if (version < 9) {
               ensureClosed(reader);
               reader = getBufferedReader(configCfg);
               if (isOldFormat(reader)) {
                  absPath = ManagementLogger.logExpectedVersion9Loggable(version).getMessage();
                  throw new ConfigFileException(absPath);
               }
            }

            ensureClosed(reader);
         }

         reader = getBufferedReader(configCfg);
         if (reader != null) {
            productionModeEnabled = getProductionMode(reader);
            ensureClosed(reader);
         }

         return Boolean.getBoolean("weblogic.upgradeRequiredForNewConfig");
      } else if (rootCfg.exists()) {
         reader = getBufferedReader(rootCfg);
         if (reader != null) {
            productionModeEnabled = getProductionMode(reader);
            ensureClosed(reader);
         }

         reader = getBufferedReader(rootCfg);
         version = -1;
         if (reader != null) {
            version = getConfigVersion(reader);
            configurationVersion = version;
            ensureClosed(reader);
            if (version == 9) {
               absPath = ManagementLogger.logExpectedPreVersion9Loggable(version).getMessage();
               throw new ConfigFileException(absPath);
            }
         }

         if (version >= 6 && version <= 8) {
            absPath = ManagementLogger.logConfigVersionNotSupportedLoggable(version, rootCfg.getAbsolutePath()).getMessage();
            throw new ConfigFileException(absPath);
         } else {
            absPath = rootCfg.getAbsolutePath();
            String msg = ManagementLogger.logUnexpectedConfigVersionLoggable(version, absPath).getMessage();
            throw new ConfigFileException(msg);
         }
      } else if (!ManagementService.getPropertyService(kernelId).isAdminServer()) {
         return false;
      } else {
         throw new ConfigFileException("Unable to locate config.xml.");
      }
   }

   public static int getConfigurationVersion() {
      return configurationVersion;
   }

   public static boolean getProductionModeEnabled() {
      return productionModeEnabled;
   }

   public static int getConfigurationVersionFromNewFormat(File configFile) throws ConfigFileException {
      BufferedReader reader = getBufferedReader(configFile);
      int version = true;
      if (reader != null) {
         int version = getConfigVersion(reader);
         ensureClosed(reader);
         return version;
      } else {
         throw new ConfigFileException("Unable to locate config.xml.");
      }
   }

   private static BufferedReader getBufferedReader(File f) {
      if (!f.exists()) {
         return null;
      } else {
         BufferedReader result = null;
         InputStreamReader reader = null;
         FileInputStream fis = null;

         try {
            fis = new FileInputStream(f);
            reader = new InputStreamReader(fis);
            result = new BufferedReader(reader);
            return result;
         } catch (FileNotFoundException var9) {
            if (result != null) {
               try {
                  result.close();
               } catch (IOException var8) {
               }
            }

            if (reader != null) {
               try {
                  reader.close();
               } catch (IOException var7) {
               }
            }

            if (fis != null) {
               try {
                  fis.close();
               } catch (IOException var6) {
               }
            }

            return null;
         }
      }
   }

   private static int getConfigVersion(BufferedReader reader) throws ConfigFileException {
      boolean is_new_format = isNewFormat(reader);

      try {
         int result = -1;
         int curr = reader.read();
         int offsetOld = 0;

         for(int offsetNew = 0; curr != -1; curr = reader.read()) {
            if (curr == old_format_version[offsetOld]) {
               ++offsetOld;
            } else {
               offsetOld = 0;
            }

            if (offsetOld >= old_format_version.length) {
               result = readInt(reader);
               break;
            }

            if (curr == new_format_version[offsetNew]) {
               ++offsetNew;
            } else {
               offsetNew = 0;
            }

            if (offsetNew >= new_format_version.length) {
               result = readInt(reader);
               break;
            }
         }

         if (result == -1) {
            return is_new_format ? 9 : 8;
         } else if (result >= 9 && is_new_format) {
            return result;
         } else if (result < 9 && !is_new_format) {
            return result;
         } else if (result == 9 && !is_new_format) {
            return 8;
         } else {
            String format = is_new_format ? "new format" : "old format";
            String msg = "Found " + format + " config with invalid version: " + result;
            throw new ConfigFileException(msg);
         }
      } catch (IOException var8) {
         throw new ConfigFileException("Error searching for config version", var8);
      }
   }

   private static boolean getProductionMode(BufferedReader reader) throws ConfigFileException {
      boolean is_new_format = isNewFormat(reader);

      try {
         boolean result = false;
         int curr = reader.read();
         int offsetOld = 0;

         for(int offsetNew = 0; curr != -1; curr = reader.read()) {
            if (curr == old_format_mode[offsetOld]) {
               ++offsetOld;
            } else {
               offsetOld = 0;
            }

            if (offsetOld >= old_format_mode.length) {
               result = readBoolean(reader);
               break;
            }

            if (curr == new_format_mode[offsetNew]) {
               ++offsetNew;
            } else {
               offsetNew = 0;
            }

            if (offsetNew >= new_format_mode.length) {
               result = readBoolean(reader);
               break;
            }
         }

         return result;
      } catch (IOException var6) {
         throw new ConfigFileException("Error searching for config version", var6);
      }
   }

   private static boolean isNewFormat(BufferedReader reader) throws ConfigFileException {
      try {
         String line = null;
         boolean isNewFormat = false;

         while((line = reader.readLine()) != null) {
            int i;
            for(i = 0; i < VersionConstants.KNOWN_NAMESPACE_PREFIXES.length; ++i) {
               if (line.contains(VersionConstants.KNOWN_NAMESPACE_PREFIXES[i])) {
                  isNewFormat = true;
                  break;
               }
            }

            for(i = 0; i < VersionConstants.NAMESPACE_DOMAIN_SUPPORTED_VERSIONS.length; ++i) {
               if (line.contains(VersionConstants.NAMESPACE_DOMAIN_SUPPORTED_VERSIONS[i])) {
                  isNewFormat = true;
                  break;
               }
            }

            if (isNewFormat) {
               break;
            }
         }

         return isNewFormat;
      } catch (IOException var4) {
         throw new ConfigFileException("Unable to determine config file format", var4);
      }
   }

   private static int readInt(BufferedReader reader) throws ConfigFileException {
      int result = -1;

      try {
         for(int curr = reader.read(); curr != -1 && curr >= UTF8_ZERO && curr <= UTF8_NINE; curr = reader.read()) {
            if (result < 0) {
               result = curr - UTF8_ZERO;
            } else {
               result *= 10;
               result += curr - UTF8_ZERO;
            }
         }
      } catch (IOException var3) {
         throw new ConfigFileException("Error reading config file version", var3);
      }

      if (result == -1) {
         throw new ConfigFileException("Unable to read config file version number.");
      } else {
         return result;
      }
   }

   private static boolean readBoolean(BufferedReader reader) throws ConfigFileException {
      boolean result = false;

      try {
         int ch = reader.read();
         if (ch != 34 && ch != 70 && ch != 102) {
            if (ch == 84 || ch == 116) {
               result = true;
            }
         } else {
            result = false;
         }

         return result;
      } catch (IOException var3) {
         throw new ConfigFileException("Error reading config file version", var3);
      }
   }

   private static void ensureClosed(BufferedReader reader) {
      if (reader != null) {
         try {
            reader.close();
         } catch (IOException var2) {
         }

      }
   }

   private static void ensureOneOrFewerConfigFiles(File rootDir) throws ConfigFileException {
      File rootCfg = new File(rootDir, BootStrap.getConfigFileName());
      if (rootCfg.exists()) {
      }

   }

   private static File findParentConfig(File dir) {
      File result = null;
      String parentDir = dir.getParent();
      if (parentDir != null) {
         result = new File(parentDir, BootStrap.getConfigFileName());
      }

      return result;
   }

   private static boolean isOldFormat(BufferedReader reader) throws ConfigFileException {
      try {
         String line = null;
         boolean isOldFormat = false;

         while((line = reader.readLine()) != null) {
            if (line.contains("<Domain")) {
               isOldFormat = true;
               break;
            }

            if (isOldFormat) {
               break;
            }
         }

         return isOldFormat;
      } catch (IOException var3) {
         throw new ConfigFileException("Unable to determine config file format", var3);
      }
   }

   public static boolean isAcceptableXmlValidationError(XmlValidationError err) {
      String msg = err.getMessage();
      if (msg != null && msg.contains("is not derived from")) {
         String[] msgPieces = msg.split("is not derived from");

         for(int n = 0; n < VersionConstants.NAMESPACE_MAPPING.length; ++n) {
            if (msgPieces.length > 1 && msgPieces[1].contains(VersionConstants.NAMESPACE_MAPPING[n][1])) {
               return true;
            }
         }
      }

      return false;
   }

   static class ConfigFileException extends ManagementException {
      public ConfigFileException(String msg) {
         super(msg);
      }

      public ConfigFileException(String msg, Throwable cause) {
         super(msg, cause);
      }
   }
}
