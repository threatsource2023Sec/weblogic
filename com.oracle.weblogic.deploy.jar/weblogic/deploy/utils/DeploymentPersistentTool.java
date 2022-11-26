package weblogic.deploy.utils;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import weblogic.deploy.internal.DeploymentVersion;
import weblogic.deploy.internal.targetserver.state.TargetModuleState;
import weblogic.management.deploy.internal.AppTargetState;
import weblogic.management.deploy.internal.ApplicationRuntimeState;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreWritePolicy;
import weblogic.store.admintool.StoreAdminIF;
import weblogic.store.admintool.StoreAdminIFImpl;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.utils.Getopt2;
import weblogic.utils.PlatformConstants;

public class DeploymentPersistentTool {
   private static final String EOL;
   private static final String LIST_OPTION = "list";
   private static final String REMOVE_OPTION = "remove";
   private static final String DIR_OPTION = "dir";
   private static final String STORE_OPTION = "store";
   private static final String APPEXP_OPTION = "appexp";
   private static final String NO_FILE_CHECK_FLAG = "nofilecheck";
   private static final String DEFAULT_REGEXP = ".*";
   private static final String FILE_APPENDIX = "000000.DAT";
   private static final boolean oldStyle;
   private final String directory;
   private final String storeName;
   private final String appExp;
   private final boolean noFileCheck;
   private final StoreAdminIF storeAdmin = new StoreAdminIFImpl();

   public DeploymentPersistentTool(String directory, String storeName, String appExp, boolean noFileCheck) {
      this.directory = directory;
      this.storeName = storeName;
      this.appExp = appExp;
      this.noFileCheck = noFileCheck;
   }

   private void checkFile() {
      File file = new File(this.directory, this.storeName + "000000.DAT");
      if (!file.exists()) {
         throw new IllegalStateException("The file " + file.getAbsolutePath() + " does not exist, check the store name or directory");
      } else if (!file.canRead()) {
         throw new IllegalStateException("The file " + file.getAbsolutePath() + " can not be read");
      }
   }

   public Map getListOfAppRuntimeState() throws PersistentStoreException, IOException {
      if (!this.noFileCheck) {
         this.checkFile();
      }

      Pattern regPattern = Pattern.compile(this.appExp);
      PersistentStoreXA store = this.storeAdmin.openFileStore(this.storeName, this.directory, StoreWritePolicy.CACHE_FLUSH, false);

      LinkedHashMap var13;
      try {
         PersistentMap pMap = store.createPersistentMap("weblogic.deploy.store");
         LinkedHashMap retVal = new LinkedHashMap();
         Iterator var5 = pMap.keySet().iterator();

         while(var5.hasNext()) {
            Object oKey = var5.next();
            Object oValue = pMap.get(oKey);
            String key = (String)oKey;
            if (regPattern.matcher(key).matches()) {
               ApplicationRuntimeState ars = (ApplicationRuntimeState)oValue;
               retVal.put(key, ars);
            }
         }

         var13 = retVal;
      } finally {
         this.storeAdmin.closeStore(store);
      }

      return var13;
   }

   public void removeListOfAppRuntimeState() throws PersistentStoreException, IOException {
      if (!this.noFileCheck) {
         this.checkFile();
      }

      Pattern regPattern = Pattern.compile(this.appExp);
      PersistentStoreXA store = this.storeAdmin.openFileStore(this.storeName, this.directory, StoreWritePolicy.DIRECT_WRITE, false);

      try {
         PersistentMap pMap = store.createPersistentMap("weblogic.deploy.store");
         List removeList = new ArrayList();
         Iterator var5 = pMap.keySet().iterator();

         while(var5.hasNext()) {
            Object oKey = var5.next();
            String key = (String)oKey;
            if (regPattern.matcher(key).matches()) {
               removeList.add(key);
            }
         }

         var5 = removeList.iterator();

         while(var5.hasNext()) {
            String appId = (String)var5.next();
            System.out.println("Removing application: " + appId + " from persistence store");
            pMap.remove(appId);
         }
      } finally {
         this.storeAdmin.closeStore(store);
      }

   }

   public static int embeddedMain(String[] argv) {
      Getopt2 getOpt = new Getopt2();
      getOpt.setFailOnUnrecognizedOpts(true);
      getOpt.addFlag("list", "List operation to list the application runtime states");
      getOpt.addFlag("remove", "Remove operation to remove the application runtime states");
      getOpt.addOption("dir", "directory", "The name of the directory containing the store.  May be relative");
      getOpt.addOption("store", "store-name", "The name of the store to open");
      getOpt.addOption("appexp", "application-id-regular-expression", "A regular expression for applications names to print. Defaults to *");
      getOpt.addFlag("nofilecheck", "Will not check existence of file prior to running");

      try {
         getOpt = getOpt.grok(argv);
      } catch (IllegalArgumentException var15) {
         getOpt.usageError("DeploymentPersistentTool");
         return 1;
      }

      boolean list = getOpt.hasOption("list");
      boolean remove = getOpt.hasOption("remove");
      if (list && remove) {
         getOpt.usageError("DeploymentPersistentTool");
         return 3;
      } else {
         String directory = getOpt.getOption("dir");
         String storeName = getOpt.getOption("store");
         if (directory != null && storeName != null) {
            String appExp = getOpt.getOption("appexp");
            if (appExp == null) {
               appExp = ".*";
            }

            boolean noFileCheck = getOpt.getBooleanOption("nofilecheck");
            DeploymentPersistentTool dpt = new DeploymentPersistentTool(directory, storeName, appExp, noFileCheck);
            if (remove) {
               try {
                  dpt.removeListOfAppRuntimeState();
                  return 0;
               } catch (Throwable var13) {
                  var13.printStackTrace();
                  return 2;
               }
            } else {
               Map data = null;

               try {
                  data = dpt.getListOfAppRuntimeState();
               } catch (Throwable var14) {
                  var14.printStackTrace();
                  return 2;
               }

               int lcv = 0;

               for(Iterator var11 = data.entrySet().iterator(); var11.hasNext(); ++lcv) {
                  Map.Entry entry = (Map.Entry)var11.next();
                  System.out.println("### Application \"" + (String)entry.getKey() + "\" " + (lcv + 1) + " of " + data.size() + " ###");
                  if (oldStyle) {
                     System.out.println(oldStylePretty((ApplicationRuntimeState)entry.getValue()));
                  } else {
                     System.out.println(((ApplicationRuntimeState)entry.getValue()).pretty());
                  }

                  System.out.println("### Application \"" + (String)entry.getKey() + "\" complete ###" + EOL);
               }

               if (lcv == 0) {
                  System.out.println("The deployment Persistent application state map is empty for application name pattern " + appExp);
               }

               return 0;
            }
         } else {
            getOpt.usageError("DeploymentPersistentTool");
            return 3;
         }
      }
   }

   private static String oldStylePretty(ApplicationRuntimeState appRuntimeState) {
      StringBuffer sb = new StringBuffer("ApplicationRuntimeState: " + appRuntimeState.getAppId() + " (" + System.identityHashCode(appRuntimeState) + ")" + EOL);
      sb.append("Retire timeout seconds: " + appRuntimeState.getRetireTimeoutSeconds() + " sec" + EOL);
      sb.append("Retire time milliseconds: " + appRuntimeState.getRetireTimeMillis() + " ms" + EOL);
      sb.append("=== Start Runtime State ===" + EOL);
      Map modules = appRuntimeState.getModules();
      int lcv = 0;
      Iterator var4 = modules.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         String moduleName = (String)entry.getKey();
         Map mInfo = (Map)entry.getValue();
         ++lcv;
         sb.append("--- Module \"" + moduleName + "\", " + lcv + " of " + modules.size() + " ---" + EOL);
         prettyPrintModuleInfo(sb, mInfo, 1);
      }

      sb.append("=== End Runtime State, start Intended States ===" + EOL);
      Map appTargetState = appRuntimeState.getAppTargetState();
      lcv = 0;

      for(Iterator var10 = appTargetState.entrySet().iterator(); var10.hasNext(); sb.append(EOL)) {
         Map.Entry entry = (Map.Entry)var10.next();
         String targetName = (String)entry.getKey();
         AppTargetState ats = (AppTargetState)entry.getValue();
         ++lcv;
         if (ats == null) {
            sb.append("--- Target \"" + targetName + "\", " + lcv + " of " + appTargetState.size() + " has no AppTargetState ---" + EOL);
         } else {
            sb.append("--- Target \"" + targetName + "\", " + lcv + " of " + appTargetState.size() + " ---" + EOL);
            sb.append(ats.toString());
         }
      }

      sb.append("=== End Intended States, start Deployment Version ===" + EOL);
      DeploymentVersion deploymentVersion = appRuntimeState.getDeploymentVersion();
      if (deploymentVersion == null) {
         sb.append("There is no deployment version available" + EOL);
      } else {
         deploymentVersionPretty(sb, deploymentVersion);
      }

      return sb.toString();
   }

   public static void deploymentVersionPretty(StringBuffer sb, DeploymentVersion deploymentVersion) {
      sb.append("Deployment version for \"" + deploymentVersion.getIdentity() + "\"" + EOL);
      Map versionMap = deploymentVersion.getVersionComponents();
      synchronized(versionMap) {
         int lcv = 0;
         Iterator var5 = versionMap.entrySet().iterator();

         while(true) {
            if (!var5.hasNext()) {
               break;
            }

            Map.Entry entry = (Map.Entry)var5.next();
            sb.append(lcv + 1 + ". " + (String)entry.getKey() + "=" + entry.getValue() + EOL);
            ++lcv;
         }
      }

      sb.append(EOL);
   }

   private static void prettyPrintModuleInfo(StringBuffer sb, Map cascadedMap, int tabLevel) {
      printTabs(sb, tabLevel);
      Iterator var3 = cascadedMap.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         String moduleName = (String)entry.getKey();
         Object oValue = entry.getValue();
         if (oValue == null) {
            sb.append("--- Target \"" + moduleName + "\" has no associated target state ---");
         } else if (oValue instanceof TargetModuleState) {
            TargetModuleState targetModuleState = (TargetModuleState)oValue;
            sb.append("--- Target state for \"" + moduleName + "\" (" + System.identityHashCode(targetModuleState) + ") ---" + EOL);
            targetModuleStatePretty(sb, tabLevel, targetModuleState);
            sb.append(EOL);
         } else {
            Map subModules = (Map)oValue;
            sb.append("--- Target \"" + moduleName + "\" has " + subModules.size() + " sub-modules ---" + EOL);
            prettyPrintModuleInfo(sb, subModules, tabLevel + 1);
         }
      }

   }

   private static void targetModuleStatePretty(StringBuffer sb, int tabLevel, TargetModuleState targetModuleState) {
      printTabs(sb, tabLevel);
      sb.append("Module Id: " + targetModuleState.getModuleId() + EOL);
      String submoduleId = targetModuleState.getSubmoduleId();
      if (submoduleId != null) {
         printTabs(sb, tabLevel);
         sb.append("Sub-Module Id: " + submoduleId + EOL);
      }

      String type = targetModuleState.getType();
      if (type != null) {
         printTabs(sb, tabLevel);
         sb.append("Module type: " + type + EOL);
      }

      printTabs(sb, tabLevel);
      sb.append("Module target: " + targetModuleState.getTargetName() + EOL);
      printTabs(sb, tabLevel);
      sb.append("Target type: " + targetModuleState.getTargetType() + EOL);
      String serverName = targetModuleState.getServerName();
      if (serverName != null && !serverName.equals(targetModuleState.getTargetName())) {
         printTabs(sb, tabLevel);
         sb.append("Server name: " + serverName + EOL);
      }

      printTabs(sb, tabLevel);
      sb.append("Current state: " + targetModuleState.getCurrentState() + EOL);
   }

   private static void printTabs(StringBuffer sb, int tabLevel) {
      for(int lcv = 0; lcv < tabLevel; ++lcv) {
         sb.append("  ");
      }

   }

   public static void main(String[] argv) {
      try {
         int retVal = embeddedMain(argv);
         System.exit(retVal);
      } catch (Throwable var2) {
         var2.printStackTrace();
         System.exit(255);
      }

   }

   static {
      EOL = PlatformConstants.EOL;
      oldStyle = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
         public Boolean run() {
            try {
               ApplicationRuntimeState.class.getMethod("pretty");
               return false;
            } catch (Throwable var2) {
               return true;
            }
         }
      });
   }
}
