package weblogic.management.patching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.management.ManagementException;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.patching.commands.WaitForCoherenceServicesHAStatusBaseCommand;

public class RolloutUpdateSettings {
   public static final String ROLLOUT_OPTION_DRY_RUN = "isdryrun";
   public static final String ROLLOUT_OPTION_IS_SESSION_COMPATIBILE = "issessioncompatible";
   public static final String ROLLOUT_OPTION_AUTO_REVERT = "isautorevertonfailure";
   public static final String ROLLOUT_OPTION_SHUTDOWN_TIMEOUT = "shutdowntimeout";
   public static final String ROLLOUT_OPTION_MIGRATION_PROPERTIES = "migrationproperties";
   public static final String ROLLOUT_OPTION_EXTENSION_PROPERTIES = "extensionproperties";
   public static final String ROLLOUT_OPTION_DELAY_BETWEEN_NODES = "delaybetweennodes";
   public static final String ROLLOUT_OPTION_COHERENCE_SERVICE_HA_TARGET = "coherenceservicehatarget";
   public static final String ROLLOUT_OPTION_COHERENCE_SERVICE_HA_CHECK_TIMEOUT = "coherenceservicehawaittimeout";
   public static final String ROLLOUT_OPTION_EXTENSIONS = "extensions";
   String target;
   String patchedOracleHome;
   String backupOracleHome;
   String rollbackString;
   String javaHome;
   String applicationPropertiesLocation;
   String migrationPropertiesLocation;
   String extensionPropertiesLocation;
   String extensionParam;
   String options;
   boolean isMTRollout;
   RolloutTargetType rolloutTargetType;
   ExtensionManager extensionManager;
   boolean rollback;
   boolean dryRun;
   boolean sessionCompatible;
   boolean autoRevert;
   int shutdownTimeoutSeconds;
   private static final int DEFAULT_DELAY_SECONDS = 60;
   long delayBetweenNodesMillis;
   List applicationPropertyList;
   List migrationPropertyList;
   List extensionPropertyList;
   private static final int DEFAULT_COHERENCE_HA_WAIT_TIMEOUT = 60;
   private static final String DELIMITER = ",";
   private String coherenceServicestatusHATarget;
   private int coherenceServiceStatusHAWaitTimeout;
   private List rolloutOptionsList;

   public boolean isMTRollout() {
      return this.isMTRollout;
   }

   public void setMTRollout(boolean isMTRollout) {
      this.isMTRollout = isMTRollout;
   }

   public RolloutUpdateSettings() {
      this((String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
   }

   public RolloutUpdateSettings(String target, String patchedOracleHome, String backupOracleHome, String rollbackString, String javaHome, String applicationPropertiesLocation, String options) {
      this.rollback = false;
      this.dryRun = false;
      this.sessionCompatible = true;
      this.autoRevert = false;
      this.shutdownTimeoutSeconds = 0;
      this.delayBetweenNodesMillis = TimeUnit.MILLISECONDS.convert(60L, TimeUnit.SECONDS);
      this.rolloutOptionsList = new ArrayList(Arrays.asList("isdryrun", "issessioncompatible", "isautorevertonfailure", "shutdowntimeout", "migrationproperties", "extensionproperties", "extensions", "delaybetweennodes", "coherenceservicehatarget", "coherenceservicehawaittimeout"));
      if (target != null && target.length() > 0) {
         this.target = target;
      }

      if (patchedOracleHome != null && patchedOracleHome.length() > 0) {
         this.patchedOracleHome = InputUtils.sanitizePath(patchedOracleHome);
      }

      if (backupOracleHome != null && backupOracleHome.length() > 0) {
         this.backupOracleHome = InputUtils.sanitizePath(backupOracleHome);
      }

      if (rollbackString != null && rollbackString.length() > 0) {
         this.rollbackString = rollbackString;
         this.rollback = Boolean.valueOf(rollbackString);
      }

      if (javaHome != null && javaHome.length() > 0) {
         this.javaHome = InputUtils.sanitizePath(javaHome);
      }

      if (applicationPropertiesLocation != null && applicationPropertiesLocation.length() > 0) {
         this.applicationPropertiesLocation = InputUtils.sanitizePath(applicationPropertiesLocation);
      }

      if (options != null && options.length() > 0) {
         this.options = options;
         this.parseOptions();
      }

      this.rolloutTargetType = null;
   }

   protected void parseOptions() throws IllegalArgumentException {
      Boolean isDryRun = this.dryRun;
      Boolean isSessionCompatible = this.sessionCompatible;
      Boolean isAutoRevert = this.autoRevert;
      int shutdownTimeoutSeconds = this.shutdownTimeoutSeconds;
      boolean shutdownTimeoutSecondsSpecified = false;
      int delayTimeSeconds = 60;
      String coherenceHaStatusTarget = null;
      int coherenceHaStatusWaitTimeout = 60;
      boolean foundExtensionProperties = false;
      boolean foundExtensionParam = false;
      String optionsWithoutExtensionParam = this.extractExtensionParam(this.options);
      if (!optionsWithoutExtensionParam.equals(this.options)) {
         foundExtensionParam = true;
      }

      StringTokenizer extensionPropertiesList;
      String key;
      if (!optionsWithoutExtensionParam.isEmpty()) {
         extensionPropertiesList = new StringTokenizer(optionsWithoutExtensionParam, ",");

         while(extensionPropertiesList.hasMoreTokens()) {
            String optionPair = extensionPropertiesList.nextToken();
            key = optionPair.toLowerCase();
            int i;
            if (key.startsWith("isdryrun")) {
               i = optionPair.indexOf(61);
               if (i == -1) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("isdryrun"));
               }

               isDryRun = Boolean.valueOf(optionPair.substring(i + 1));
            } else if (key.startsWith("issessioncompatible")) {
               i = optionPair.indexOf(61);
               if (i == -1) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("issessioncompatible"));
               }

               isSessionCompatible = Boolean.valueOf(optionPair.substring(i + 1));
            } else if (key.startsWith("isautorevertonfailure")) {
               i = optionPair.indexOf(61);
               if (i == -1) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("isautorevertonfailure"));
               }

               isAutoRevert = Boolean.valueOf(optionPair.substring(i + 1).trim());
            } else if (key.startsWith("shutdowntimeout")) {
               shutdownTimeoutSecondsSpecified = true;
               i = optionPair.indexOf(61);
               if (i == -1) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("shutdowntimeout"));
               }

               shutdownTimeoutSeconds = Integer.parseInt(optionPair.substring(i + 1).trim());
            } else if (key.startsWith("delaybetweennodes")) {
               i = optionPair.indexOf(61);
               if (i == -1) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("delaybetweennodes"));
               }

               delayTimeSeconds = Integer.parseInt(optionPair.substring(i + 1).trim());
            } else if (key.startsWith("coherenceservicehatarget")) {
               i = optionPair.indexOf(61);
               if (i == -1) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("coherenceservicehatarget"));
               }

               coherenceHaStatusTarget = optionPair.substring(i + 1).trim();
               if (coherenceHaStatusTarget != null && !WaitForCoherenceServicesHAStatusBaseCommand.HA_STATUS_VALID_LIST.contains(coherenceHaStatusTarget)) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidCoherenceServiceHAStatus());
               }
            } else if (key.startsWith("coherenceservicehawaittimeout")) {
               i = optionPair.indexOf(61);
               if (i == -1) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("coherenceservicehawaittimeout"));
               }

               coherenceHaStatusWaitTimeout = Integer.parseInt(optionPair.substring(i + 1).trim());
               if (coherenceHaStatusWaitTimeout < 0) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidCoherenceServiceHAStatusWaitTimeout());
               }
            } else if (key.startsWith("migrationproperties")) {
               i = optionPair.indexOf(61);
               if (i == -1) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("migrationproperties"));
               }

               this.migrationPropertiesLocation = optionPair.substring(i + 1).trim();
               List migrationPropertiesList = null;

               try {
                  migrationPropertiesList = (new MigrationPropertiesReader()).readMigrationProperties(this.migrationPropertiesLocation);
                  this.setMigrationPropertyList(migrationPropertiesList);
               } catch (Exception var20) {
                  String propType = PatchingMessageTextFormatter.getInstance().getMigrationString();
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotLoadProps(propType, this.migrationPropertiesLocation, var20));
               }
            } else {
               if (!key.startsWith("extensionproperties")) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().unknownOption(optionPair));
               }

               foundExtensionProperties = true;
               i = optionPair.indexOf(61);
               if (i == -1) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("extensionproperties"));
               }

               this.extensionPropertiesLocation = optionPair.substring(i + 1).trim();
            }
         }
      }

      if (foundExtensionProperties || foundExtensionParam) {
         extensionPropertiesList = null;

         try {
            ExtensionPropertiesReader extensionPropsReader = new ExtensionPropertiesReader();
            List extensionPropertiesList = extensionPropsReader.readExtensionProperties(this.extensionPropertiesLocation, this.extensionParam);
            this.setExtensionPropertyList(extensionPropertiesList);
         } catch (Exception var19) {
            key = PatchingMessageTextFormatter.getInstance().getExtensionString();
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotLoadProps(key, this.extensionPropertiesLocation, var19));
         }
      }

      if (!isSessionCompatible && !shutdownTimeoutSecondsSpecified) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().incompatibleSessionsRequireShutdownTimeout());
      } else {
         this.setDryRun(isDryRun);
         this.setSessionCompatible(isSessionCompatible);
         this.setAutoRevert(isAutoRevert);
         this.setShutdownTimeoutSeconds(shutdownTimeoutSeconds);
         this.setDelayBetweenNodesMillis(TimeUnit.MILLISECONDS.convert((long)delayTimeSeconds, TimeUnit.SECONDS));
         this.setCoherenceServiceStatusHATarget(coherenceHaStatusTarget);
         this.setCoherenceServiceStatusHAWaitTimeout(coherenceHaStatusWaitTimeout);
      }
   }

   public ExtensionManager getExtensionManager() {
      return this.extensionManager;
   }

   public void validateExtensions() throws IllegalArgumentException {
      if (this.extensionManager != null) {
         this.extensionManager.validateExtensions();
      }

   }

   public void setExtensionManager(String extensionBaseDir) throws ManagementException {
      this.extensionManager = new ExtensionManager(this.extensionPropertyList, extensionBaseDir);
   }

   public boolean hasExtensions() {
      return this.extensionPropertyList != null;
   }

   public String extractExtensionParam(String options) {
      String regEx = "((,*";
      Iterator var3 = this.rolloutOptionsList.iterator();

      while(var3.hasNext()) {
         String opt = (String)var3.next();
         if (!opt.equalsIgnoreCase("extensions")) {
            regEx = regEx + opt;
            if (!opt.equalsIgnoreCase((String)this.rolloutOptionsList.get(this.rolloutOptionsList.size() - 1))) {
               regEx = regEx + "|,*";
            } else {
               regEx = regEx + ")=[\\w\\-\\./:\\\\]*)";
            }
         }
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Match regex: " + regEx + "\nfor the options: " + options);
      }

      Pattern p = Pattern.compile(regEx, 2);
      Matcher m = p.matcher(options);
      String optionsWithoutExtensionParam = "";

      String extensions;
      String noExtension;
      for(extensions = options; m.find(); extensions = extensions.replaceAll(noExtension, "")) {
         noExtension = m.group();
         optionsWithoutExtensionParam = optionsWithoutExtensionParam + noExtension;
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Found non-extensions part: " + noExtension);
         }

         if (noExtension.contains("\\")) {
            noExtension = noExtension.replace("\\", "\\\\");
         }
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Found extensions part: " + extensions);
      }

      if (!extensions.isEmpty()) {
         if (extensions.startsWith(",")) {
            this.extensionParam = this.parseExtensions(extensions.substring(1));
         } else {
            this.extensionParam = this.parseExtensions(extensions);
         }
      }

      return optionsWithoutExtensionParam;
   }

   public String parseExtensions(String key) {
      int i = key.indexOf(61);
      if (i == -1) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Parsing error extensions param: " + key);
         }

         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("extensions"));
      } else {
         return key.substring(i + 1).trim();
      }
   }

   public void validateSettings() throws IllegalArgumentException {
      boolean valid = true;
      valid = this.target != null && this.target.length() > 0;
      if (!valid) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().missingTarget());
      } else {
         valid &= this.patchedOracleHome != null && this.backupOracleHome != null && this.rollbackString != null || this.patchedOracleHome == null && this.backupOracleHome == null;
         if (!valid) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getMissingOracleHomeArg(this.patchedOracleHome, this.backupOracleHome, this.rollbackString));
         } else if (this.rollbackString != null && !this.rollbackString.equalsIgnoreCase("TRUE") && !this.rollbackString.equalsIgnoreCase("FALSE")) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidRollbackValue(this.rollbackString));
         } else if (this.isRollback() && this.isUpdateApplications()) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidApplicationsRollback());
         } else {
            this.checkWhiteSpace("OracleHome", this.patchedOracleHome);
            this.checkWhiteSpace("JavaHome", this.javaHome);
         }
      }
   }

   public String getTarget() {
      return this.target;
   }

   public void setTarget(String target) {
      this.target = target;
   }

   public boolean isUpdateOracleHome() {
      return this.patchedOracleHome != null && !this.patchedOracleHome.isEmpty() && this.backupOracleHome != null && !this.backupOracleHome.isEmpty();
   }

   public boolean isUpdateJavaHome() {
      return this.javaHome != null && !this.javaHome.isEmpty();
   }

   public boolean isUpdateApplications() {
      return this.applicationPropertiesLocation != null && !this.applicationPropertiesLocation.isEmpty();
   }

   public boolean isRollingRestart() {
      return !this.isUpdateOracleHome() && !this.isUpdateJavaHome() && !this.isUpdateApplications();
   }

   public boolean isRollback() {
      return this.rollback;
   }

   public void setRollback(boolean rollback) {
      this.rollback = rollback;
   }

   public boolean isDryRun() {
      return this.dryRun;
   }

   public void setDryRun(boolean dryRun) {
      this.dryRun = dryRun;
   }

   public boolean isSessionCompatible() {
      return this.sessionCompatible;
   }

   public void setSessionCompatible(boolean sessionCompatible) {
      this.sessionCompatible = sessionCompatible;
   }

   public void setAutoRevert(Boolean isAutoRevert) {
      this.autoRevert = isAutoRevert;
   }

   public boolean isAutoRevert() {
      return this.autoRevert;
   }

   public void setShutdownTimeoutSeconds(int shutdownTimeoutSeconds) {
      this.shutdownTimeoutSeconds = shutdownTimeoutSeconds;
   }

   public int getShutdownTimeoutSeconds() {
      return this.shutdownTimeoutSeconds;
   }

   public List getApplicationPropertyList() {
      return this.applicationPropertyList;
   }

   public void setApplicationPropertyList(List applicationPropertyList) {
      this.applicationPropertyList = applicationPropertyList;
   }

   public List getMigrationPropertyList() {
      return this.migrationPropertyList;
   }

   public void setMigrationPropertyList(List migrationPropertyList) {
      this.migrationPropertyList = migrationPropertyList;
   }

   public List getExtensionPropertyList() {
      return this.extensionPropertyList;
   }

   public void setExtensionPropertyList(List extensionPropertyList) {
      this.extensionPropertyList = extensionPropertyList;
   }

   public RolloutTargetType getRolloutTargetType() {
      return this.rolloutTargetType;
   }

   public void setRolloutTargetType(RolloutTargetType rolloutTargetType) {
      this.rolloutTargetType = rolloutTargetType;
   }

   public void setRolloutTargetType(String rolloutTargetTypeStr) {
      if (RolloutUpdateSettings.RolloutTargetType.DOMAIN.toString().equalsIgnoreCase(rolloutTargetTypeStr)) {
         this.setRolloutTargetType(RolloutUpdateSettings.RolloutTargetType.DOMAIN);
      } else if (RolloutUpdateSettings.RolloutTargetType.DOMAIN_ROLLBACK.toString().equalsIgnoreCase(rolloutTargetTypeStr)) {
         this.setRolloutTargetType(RolloutUpdateSettings.RolloutTargetType.DOMAIN_ROLLBACK);
      } else if (RolloutUpdateSettings.RolloutTargetType.CLUSTER.toString().equalsIgnoreCase(rolloutTargetTypeStr)) {
         this.setRolloutTargetType(RolloutUpdateSettings.RolloutTargetType.CLUSTER);
      } else if (RolloutUpdateSettings.RolloutTargetType.SERVER.toString().equalsIgnoreCase(rolloutTargetTypeStr)) {
         this.setRolloutTargetType(RolloutUpdateSettings.RolloutTargetType.SERVER);
      } else {
         if (!RolloutUpdateSettings.RolloutTargetType.PARTITION.toString().equalsIgnoreCase(rolloutTargetTypeStr)) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Unrecognized rolloutTypeStr: " + rolloutTargetTypeStr);
            }

            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidRolloutTargetType(rolloutTargetTypeStr));
         }

         this.setRolloutTargetType(RolloutUpdateSettings.RolloutTargetType.PARTITION);
      }

   }

   public String getPatchedOracleHome() {
      return this.patchedOracleHome;
   }

   public String getBackupOracleHome() {
      return this.backupOracleHome;
   }

   public String getJavaHome() {
      return this.javaHome;
   }

   public long getDelayBetweenNodesMillis() {
      return this.delayBetweenNodesMillis;
   }

   public void setDelayBetweenNodesMillis(long delayBetweenNodesMillis) {
      this.delayBetweenNodesMillis = delayBetweenNodesMillis;
   }

   public void setCoherenceServiceStatusHATarget(String coherenceServicestatusHATarget) {
      this.coherenceServicestatusHATarget = coherenceServicestatusHATarget;
   }

   public String getCoherenceServiceStatusHATarget() {
      return this.coherenceServicestatusHATarget;
   }

   public void setCoherenceServiceStatusHAWaitTimeout(int coherenceServiceStatusHAWaitTimeout) {
      this.coherenceServiceStatusHAWaitTimeout = coherenceServiceStatusHAWaitTimeout;
   }

   public int getCoherenceServiceStatusHAWaitTimeout() {
      return this.coherenceServiceStatusHAWaitTimeout;
   }

   private void checkWhiteSpace(String argName, String argValue) throws IllegalArgumentException {
      if (argValue != null && argValue.trim().indexOf(" ") > -1) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getInvalidArgWithSpace(argName, argValue));
      }
   }

   public static enum RolloutTargetType {
      DOMAIN,
      DOMAIN_ROLLBACK,
      CLUSTER,
      SERVER,
      PARTITION;
   }
}
