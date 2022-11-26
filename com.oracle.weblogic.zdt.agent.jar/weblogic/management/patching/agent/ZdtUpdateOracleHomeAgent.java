package weblogic.management.patching.agent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class ZdtUpdateOracleHomeAgent extends ZdtAgent {
   public static final String TEMPLATE_FILE_BASE_NAME = "BootstrapTemplate";
   public static final String PATCH_BACKUP_DIR = "patching_backup";
   public static final String DOMAIN_BACKUP_DIR = "domain";
   public static final String OHOME_BACKUP_DIR = "ohome";
   public static final String WL_SERVER_DIR = "wlserver";
   public static final String MODULES_DIR = "modules";
   private static final int BUF_SIZE = 4096;
   private static final String DEFAULT_REVERT_FROM_ERROR = "false";
   public static final String LOG_SCRIPT_OUTPUT = "oracleHomeUpdateScript.out";
   public static final String LOG_SCRIPT_ERRFILE = "oracleHomeUpdateScript.err";
   public static final String LOG_ERRFILE = "updateErrors.err";
   public static final String LOG_PASTE_BIN_OUT = "pasteBin.out";
   public static final String LOG_EXTRACT_PATCHED_OUT = "extractArchive.out";
   public static final String LOG_DETACH_HOME_OUT = "detachHome.out";
   public static final String LOG_ATTACH_HOME_OUT = "attachHOme.out";
   private String patchedLocation;
   private String backupLocation;
   private String newJavaHome;
   private String javaHomePath;
   private boolean revertFromError = false;
   private boolean verbose = false;
   private ZdtAgentRequest request;
   protected ZdtAgentScriptHelper scriptHelper;
   private JavaHomeUpdateUtils javaHomeUpdateUtils;
   protected File mwHomeDirectory;
   protected String mwHomePath;
   protected File bootstrapFile;
   protected String bootstrapFilePath;
   protected File bootstrapDirectory;
   protected File domainDirectory;
   protected File javaDomainFileBackupDirectory;
   protected File javaOHFileBackupDirectory;
   protected String workflowId;
   protected File zdtAgentJarFile;
   protected Map extScriptsEnv;
   public List filesToClean;
   public ArrayList extBeforeUpdateScripts;
   public ArrayList extAfterUpdateScripts;
   static String usageString = "Usage: java -jar agent.jar [check|prepare|update|validate] <variables>\n       variables: <domainDirectory> <patchedArchive> <backupDirectory> <newJavaHome> [revertFromError] [verbose] [workflowId]\n - make sure the following environment variables are set: JAVA_HOME, TMP_UPDATE_SCRIPT, MW_HOME\n - make sure that the optional parameters which are not required are passed as an empty quoted String\n";

   public ZdtUpdateOracleHomeAgent(ZdtAgentRequest request, ZdtAgentOutputHandler outputHandler) {
      super(request.getAction());
      outputHandler.info("ZdtUpdateOracleHomeAgent created");
      this.request = request;
      this.outputHandler = outputHandler;
      this.scriptHelper = new ZdtAgentScriptHelper(outputHandler);
      this.workflowId = request.get(ZdtAgentParam.WORKFLOW_ID_PARAM);
      if (request.get(ZdtAgentParam.EXTENSION_JARS_PARAM) != null) {
         this.extensionJars = request.get(ZdtAgentParam.EXTENSION_JARS_PARAM);
      }

      this.patchedLocation = this.normalizePath(request.get(ZdtAgentParam.PATCHED_PARAM));
      this.backupLocation = this.normalizePath(request.get(ZdtAgentParam.BACKUP_DIR_PARAM));
      this.newJavaHome = this.normalizePath(request.get(ZdtAgentParam.NEW_JAVA_HOME_PARAM));
      this.revertFromError = Boolean.parseBoolean(request.get(ZdtAgentParam.REVERT_PARAM));
      this.verbose = Boolean.parseBoolean(request.get(ZdtAgentParam.VERBOSE_PARAM));
      String mwHomeEnv = PlatformUtils.getMWHomePath();
      if (mwHomeEnv == null) {
         throw new IllegalArgumentException("Environment variable MW_HOME must be set");
      } else {
         this.mwHomePath = this.normalizePath(mwHomeEnv);
         this.mwHomeDirectory = new File(this.normalizePath(this.mwHomePath));
         if (!this.mwHomePath.endsWith(File.separator)) {
            this.mwHomePath = this.mwHomePath + File.separator;
         }

         String javaHomeEnv = PlatformUtils.getJavaHomePath();
         if (javaHomeEnv == null) {
            throw new IllegalArgumentException("Environment variable JAVA_HOME must be set");
         } else {
            this.javaHomePath = this.normalizePath(javaHomeEnv);
            String tmpUpdateScript = System.getenv("TMP_UPDATE_SCRIPT");
            if (tmpUpdateScript == null) {
               throw new IllegalArgumentException("Environment variable TMP_UPDATE_SCRIPT must be set");
            } else {
               this.bootstrapFilePath = this.normalizePath(tmpUpdateScript);
               this.bootstrapFile = new File(this.bootstrapFilePath);
               this.bootstrapDirectory = this.bootstrapFile.getParentFile();
               String domainDirectoryString = request.get(ZdtAgentParam.DOMAIN_DIR_PARAM);
               if (domainDirectoryString == null) {
                  throw new IllegalArgumentException("Domain Directory must be set");
               } else {
                  this.domainDirectoryPath = this.normalizePath(domainDirectoryString);
                  this.domainDirectory = new File(this.domainDirectoryPath);
                  this.javaHomeUpdateUtils = new JavaHomeUpdateUtils(outputHandler);
                  this.javaDomainFileBackupDirectory = Paths.get(this.mwHomePath, "patching_backup", "domain").toFile();
                  this.javaOHFileBackupDirectory = Paths.get(this.mwHomePath, "patching_backup", "ohome").toFile();
                  this.zdtAgentJarFile = new File(this.mwHomePath + "wlserver" + File.separator + "modules" + File.separator + "com.oracle.weblogic.zdt.agent.jar");
                  this.filesToClean = new ArrayList();
                  this.filesToClean.add(new File(this.bootstrapDirectory, "oracleHomeUpdateScript.out"));
                  this.filesToClean.add(new File(this.bootstrapDirectory, "oracleHomeUpdateScript.err"));
                  this.filesToClean.add(new File(this.bootstrapDirectory, "updateErrors.err"));
                  this.filesToClean.add(new File(this.bootstrapDirectory, "pasteBin.out"));
                  this.filesToClean.add(new File(this.bootstrapDirectory, "detachHome.out"));
                  this.filesToClean.add(new File(this.bootstrapDirectory, "attachHOme.out"));
                  this.filesToClean.add(new File(this.bootstrapDirectory, "com.oracle.weblogic.zdt.agent.jar"));
                  this.filesToClean.add(this.bootstrapFile);
                  if (request.get(ZdtAgentParam.BEFORE_UPDATE_EXTENSIONS_PARAM) != null) {
                     this.extBeforeUpdateScripts = new ArrayList(Arrays.asList(request.get(ZdtAgentParam.BEFORE_UPDATE_EXTENSIONS_PARAM).split("\\s*,\\s*")));
                  }

                  if (request.get(ZdtAgentParam.AFTER_UPDATE_EXTENSIONS_PARAM) != null) {
                     this.extAfterUpdateScripts = new ArrayList(Arrays.asList(request.get(ZdtAgentParam.AFTER_UPDATE_EXTENSIONS_PARAM).split("\\s*,\\s*")));
                  }

               }
            }
         }
      }
   }

   public void checkPrerequisites() throws Exception {
      File mw_home = new File(System.getenv("MW_HOME"));
      this.outputHandler.info("Will check all paths; MW_HOME: " + mw_home + " patchedLocation: " + this.patchedLocation + " backupLocation: " + this.backupLocation + " newJavaHome: " + this.newJavaHome + " revertFromError: " + this.revertFromError + " verbose: " + this.verbose);

      try {
         this.validateArgumentCombinations();
         this.validateArguments();
      } catch (IllegalArgumentException var3) {
         this.outputHandler.error("Invalid argument given to checkPrerequisites", var3);
         throw var3;
      }
   }

   public void prepare() throws Exception {
      this.outputHandler.info("Starting prepare");
      this.outputHandler.info("Prepare will write file: " + this.bootstrapFilePath + " and copy " + this.zdtAgentJarFile.getPath() + " to " + this.bootstrapDirectory);
      this.outputHandler.info("About to call checkPrerequisites");
      this.checkPrerequisites();
      this.outputHandler.info("About to call cleanUpFiles");
      this.cleanUpFiles();

      try {
         this.outputHandler.info("About to copy file: " + this.zdtAgentJarFile.getPath() + " to " + this.bootstrapDirectory);
         this.copyFileToDirectory(this.zdtAgentJarFile, this.bootstrapDirectory);
      } catch (IOException var16) {
         this.outputHandler.error("Unable to copy " + this.zdtAgentJarFile.getPath() + " to " + this.bootstrapDirectory, var16);
         throw var16;
      }

      this.outputHandler.info("About to write bootstrap script " + this.bootstrapFilePath + " from template to " + this.bootstrapDirectory);
      Map tokens = new HashMap();
      tokens.put(ZdtAgentParam.DOMAIN_DIR_PARAM.getDisplayString(), this.domainDirectoryPath);
      tokens.put(ZdtAgentParam.PATCHED_PARAM.getDisplayString(), this.patchedLocation);
      tokens.put(ZdtAgentParam.BACKUP_DIR_PARAM.getDisplayString(), this.backupLocation);
      tokens.put(ZdtAgentParam.NEW_JAVA_HOME_PARAM.getDisplayString(), this.newJavaHome);
      tokens.put(ZdtAgentParam.REVERT_PARAM.getDisplayString(), "" + this.revertFromError);
      tokens.put(ZdtAgentParam.VERBOSE_PARAM.getDisplayString(), "" + this.verbose);
      tokens.put(ZdtAgentParam.ZDT_AGENT_JAR_PATH.getDisplayString(), "");
      tokens.put(ZdtAgentParam.ZDT_AGENT_JAR_NAME.getDisplayString(), "com.oracle.weblogic.zdt.agent.jar");
      tokens.put(ZdtAgentParam.SCRIPT_OUTFILE_PARAM.getDisplayString(), "oracleHomeUpdateScript.out");
      tokens.put("SCRIPT_ERRFILE", "oracleHomeUpdateScript.err");
      tokens.put("LOG_ERRFILE", "updateErrors.err");
      tokens.put(ZdtAgentParam.JAVA_BACKUP_DIR_PARAM.getDisplayString(), this.normalizePath(this.javaDomainFileBackupDirectory));
      tokens.put(ZdtAgentParam.WORKFLOW_ID_PARAM.getDisplayString(), this.workflowId);
      tokens.put("TIMESTAMP", (new SimpleDateFormat()).format(new Date()));
      tokens.put(ZdtAgentParam.BEFORE_UPDATE_EXTENSIONS_PARAM.getDisplayString(), String.join(",", this.extBeforeUpdateScripts));
      tokens.put(ZdtAgentParam.AFTER_UPDATE_EXTENSIONS_PARAM.getDisplayString(), String.join(",", this.extAfterUpdateScripts));
      String templateFilename = "BootstrapTemplate" + PlatformUtils.getScriptExtension();
      InputStream is = this.getClass().getClassLoader().getResourceAsStream(templateFilename);
      if (is == null) {
         this.outputHandler.error("NULL IS for templateFile " + templateFilename);
         throw new Exception("Failed to find template file " + templateFilename);
      } else {
         this.outputHandler.info("Got valid IS for templateFile " + templateFilename);
         Reader sourceReader = new InputStreamReader(is);
         TemplateReader templateReader = new TemplateReader(sourceReader, tokens);
         Writer bootstrapWriter = new FileWriter(this.bootstrapFile);
         boolean writeSuccess = false;

         try {
            char[] cbuf = new char[4096];
            int numRead = templateReader.read(cbuf);
            if (numRead > 0) {
               bootstrapWriter.write(cbuf, 0, numRead);
               writeSuccess = true;
            } else {
               this.outputHandler.error("0 bytes read from template " + templateFilename);
            }
         } catch (IOException var14) {
            this.outputHandler.error("Error attempting to read template " + templateFilename, var14);
            throw var14;
         } finally {
            templateReader.close();
            bootstrapWriter.close();
         }

         if (writeSuccess) {
            this.outputHandler.info("Successfully wrote bootstrap script");
            boolean result = this.bootstrapFile.setExecutable(true);
            if (!result) {
               this.outputHandler.error("Unable to set bootstrap script TMP_UPDATE_SCRIPT executable " + this.bootstrapFilePath);
            }

            if (this.newJavaHome != null && !this.newJavaHome.isEmpty()) {
               this.outputHandler.info("backing up domain files that reference JAVA_HOME");
               this.javaHomeUpdateUtils.backupJavaHomeDomainFiles(this.domainDirectoryPath, this.javaDomainFileBackupDirectory.getPath());
            }
         }

         this.outputHandler.info("Successfully completed prepare");
      }
   }

   public void validateArgumentCombinations() throws Exception {
      if ((this.patchedLocation == null || this.patchedLocation.isEmpty()) && (this.newJavaHome == null || this.newJavaHome.isEmpty()) && !this.revertFromError) {
         throw new IllegalArgumentException("Must supply either PATCHED to update MW_HOME or NEW_JAVA_HOME to update JAVA_HOME");
      } else if (this.patchedLocation != null && !this.patchedLocation.isEmpty() && (this.backupLocation == null || this.backupLocation.isEmpty())) {
         throw new IllegalArgumentException("When specifying a PATCHED value to update MW_HOME, a backup directory must also be specified");
      }
   }

   public void validateArguments() throws Exception {
      this.outputHandler.info("Validating arguments - MW_HOME: " + this.mwHomePath + " patchedLocation: " + this.patchedLocation + " backupLocation: " + this.backupLocation + " newJavaHome: " + this.newJavaHome + " revertFromError: " + this.revertFromError + " verbose: " + this.verbose);
      File backupDirectory;
      File backupParent;
      if (this.patchedLocation != null && !this.patchedLocation.isEmpty()) {
         backupDirectory = new File(this.patchedLocation);
         if (!backupDirectory.exists()) {
            throw new IllegalArgumentException("PATCHED still does not exist: " + this.patchedLocation + ", it must be set to a valid archive or directory");
         }

         if (backupDirectory.isDirectory()) {
            if (this.newJavaHome != null && !this.newJavaHome.isEmpty()) {
               throw new IllegalArgumentException("PATCHED " + this.patchedLocation + ", is a directory, so NEW_JAVA_HOME is not allowed to be specified at the same time");
            }

            backupParent = new File(backupDirectory.getCanonicalPath() + File.separator + "wlserver" + File.separator + "server" + File.separator + "bin" + File.separator + "startNodeManager" + PlatformUtils.getScriptExtension());
            if (!backupParent.exists()) {
               throw new IllegalArgumentException("PATCHED " + this.patchedLocation + ", is a directory but does not seem to be a valid OracleHome, could not find startNodeManager at " + backupParent.getCanonicalPath());
            }
         }

         if (this.domainDirectoryPath.contains(this.mwHomePath)) {
            throw new IllegalArgumentException("The domain directory " + this.domainDirectoryPath + " is a directory under the OracleHome directory, " + this.mwHomePath + ", and this is an invalid topology for ZDT Control");
         }

         if (this.patchedLocation.contains(this.mwHomePath)) {
            throw new IllegalArgumentException("The UpdateOracleHome value " + this.patchedLocation + " is under the OracleHome directory, " + this.mwHomePath + ", and this should be moved to be accessible outside of the OracleHome");
         }
      }

      if (this.newJavaHome != null && !this.newJavaHome.isEmpty()) {
         backupDirectory = new File(this.newJavaHome);
         if (!backupDirectory.isDirectory()) {
            throw new IllegalArgumentException("NEW_JAVA_HOME should be a valid directory: " + this.newJavaHome);
         }

         if (backupDirectory.getCanonicalPath().equals(this.mwHomeDirectory.getCanonicalPath())) {
            throw new IllegalArgumentException("The RolloutJavaHome value " + this.newJavaHome + " is the same as the OracleHome directory. This is likley a typo and a valid Java directory should be given.");
         }
      }

      if (this.bootstrapFilePath != null && !this.bootstrapFilePath.isEmpty()) {
         if (this.bootstrapFilePath.contains(this.mwHomePath)) {
            throw new IllegalArgumentException("The bootstrap file TMP_UPDATE_SCRIPT value " + this.bootstrapFilePath + "is under the OracleHome directory, " + this.mwHomePath + ", and this should be moved to be accessible outside of the OracleHome.  This is likely due to an improper override of the TMP_UPDATE_SCRIPT environment value when starting the NodeManager.");
         } else {
            if (this.backupLocation != null && !this.backupLocation.isEmpty()) {
               if (this.backupLocation.equals(this.mwHomePath)) {
                  throw new IllegalArgumentException("The BackupOracleHome value " + this.backupLocation + " is the same as the OracleHome directory.  A unique BackupOracleHome dir should be given in order to move the MW_HOME aside for preservation.");
               }

               backupDirectory = new File(this.backupLocation);
               backupParent = backupDirectory.getParentFile();
               if (backupParent == null || !backupParent.isDirectory()) {
                  throw new IllegalArgumentException("Failed to get parent directory for BACKUP_DIR " + this.backupLocation);
               }

               if (backupDirectory.getCanonicalPath().contains(this.mwHomePath)) {
                  throw new IllegalArgumentException("The BackupOracleHome value " + this.backupLocation + " is under the OracleHome directory, " + this.mwHomePath + ", and should be moved to be stored outside of the OrcleHome.");
               }
            } else if (this.patchedLocation != null && !this.patchedLocation.isEmpty()) {
               throw new IllegalArgumentException("Must supply BACKUP_DIR");
            }

            if (this.outputHandler.isDebugEnabled()) {
               this.outputHandler.debug("Patched dir will be: " + this.patchedLocation + "\nBackup dir will be: " + this.backupLocation + "\nAction is " + this.action);
            }

         }
      } else {
         throw new IllegalArgumentException("The bootstrap file TMP_UPDATE_SCRIPT was not set by the startNodeManager script. This is likely due to an invalid ZDT topology with a custom NodeManager.  Please make sure the custom NodeManager delegates to the WL_HOME/server/bin/startNodeManager script.");
      }
   }

   public void update() throws Exception {
      this.outputHandler.info("Starting Update");
      this.outputHandler.info("Will do the update; MW_HOME: " + this.mwHomePath + " patchedLocation: " + this.patchedLocation + " backupLocation: " + this.backupLocation + " newJavaHome: " + this.newJavaHome + " revertFromError: " + this.revertFromError + " verbose: " + this.verbose);
      this.outputHandler.info("About to call checkPrerequisites");
      this.checkPrerequisites();
      this.outputHandler.info("Setting extension environment variables");
      this.setExtensionEnv();
      boolean isJavaRollout = this.newJavaHome != null && !this.newJavaHome.isEmpty();
      boolean isOHRollout = this.patchedLocation != null && !this.patchedLocation.isEmpty();
      boolean isPatchedJar = false;
      File patchedFile = null;
      if (isOHRollout) {
         patchedFile = new File(this.patchedLocation);
         isPatchedJar = !patchedFile.isDirectory();
      }

      boolean isPasteBinNeeded = isJavaRollout || isPatchedJar;
      String mwHomePathToUse = this.mwHomePath;
      String javaHomeToUse = isJavaRollout ? this.newJavaHome : this.javaHomePath;
      boolean detachPerformed = false;
      Iterator var9;
      String scr;
      String scriptOut;
      int rc1;
      if (this.extBeforeUpdateScripts.toString() != null && !this.extBeforeUpdateScripts.toString().equalsIgnoreCase("[]")) {
         this.outputHandler.info("BEFORE: About to call before update extension scripts " + this.extBeforeUpdateScripts.toString());
         var9 = this.extBeforeUpdateScripts.iterator();

         while(var9.hasNext()) {
            scr = (String)var9.next();
            scriptOut = scr + ".out";
            this.outputHandler.info("BEFORE: Domain dir path " + this.domainDirectoryPath + PATCHING_DIR);
            rc1 = this.scriptHelper.callExtensionScript(scriptOut, this.domainDirectoryPath + PATCHING_DIR, scr, this.extScriptsEnv);
            if (rc1 != 0) {
               this.outputHandler.error(this.readLogFile(scriptOut));
               throw new Exception("BEFORE: Failed to run extension script " + scr + ", return code was " + rc1 + ", check this log for details: " + scriptOut);
            }

            this.outputHandler.info("BEFORE: Successfully ran extension script " + scr + "\n" + this.readLogFile(scriptOut));
         }
      }

      if (isPasteBinNeeded) {
         this.outputHandler.info("About to call detach home on " + this.mwHomePath);
         int rc = this.scriptHelper.callDetachHome("detachHome.out");
         if (rc != 0) {
            this.outputHandler.error(this.readLogFile("detachHome.out"));
            throw new Exception("Failed to call detach home on " + this.mwHomePath + ", return code was " + rc + ", check this log for details: " + "detachHome.out");
         }

         detachPerformed = true;
         this.outputHandler.info("Successfully called detach home on " + this.mwHomePath);
      }

      if (!isOHRollout) {
         if (!this.revertFromError) {
            this.outputHandler.info("About to call backup JavaHome files at " + this.javaOHFileBackupDirectory.toString());
            this.javaHomeUpdateUtils.backupJavaHomeFiles(this.javaOHFileBackupDirectory.getPath());
            mwHomePathToUse = this.mwHomePath;
         }
      } else {
         try {
            this.backupMWHome();
         } catch (IOException var14) {
            if (detachPerformed) {
               this.outputHandler.info("Error perfoming backupMWHOME, calling re-attach OracleHome");
               this.scriptHelper.callAttachHome("attachHOme.out");
            }

            throw var14;
         }

         mwHomePathToUse = this.backupLocation;
         if (!isPatchedJar) {
            this.outputHandler.info("Attempting to move patched archive directory");

            try {
               this.moveDirectoryToDirectory(patchedFile, this.mwHomeDirectory);
            } catch (IOException var13) {
               this.outputHandler.error("Failed to move patched OracleHome directory " + this.patchedLocation + " to mwHome " + this.mwHomePath + ".  Attempting to restore original mwHome.");
               throw var13;
            }
         }
      }

      if (isPasteBinNeeded) {
         if (!isOHRollout) {
            this.performPasteBinary(mwHomePathToUse, javaHomeToUse);
         } else {
            this.performExtractionOfPatchedArchive(mwHomePathToUse, javaHomeToUse);
         }
      }

      if (this.revertFromError && !isOHRollout) {
         this.javaHomeUpdateUtils.restoreJavaHomeFiles(this.javaOHFileBackupDirectory.getPath());
      }

      this.javaHomeUpdateUtils.updateJavaHomeFiles(this.domainDirectoryPath, this.newJavaHome, this.javaDomainFileBackupDirectory.getPath());
      if (this.extAfterUpdateScripts.toString() != null && !this.extAfterUpdateScripts.toString().equalsIgnoreCase("[]")) {
         this.outputHandler.info("AFTER: About to call extension scripts " + this.extAfterUpdateScripts.toString());
         var9 = this.extAfterUpdateScripts.iterator();

         while(var9.hasNext()) {
            scr = (String)var9.next();
            scriptOut = scr + ".out";
            this.outputHandler.info("AFTER: Domain dir path " + this.domainDirectoryPath + PATCHING_DIR);
            rc1 = this.scriptHelper.callExtensionScript(scriptOut, this.domainDirectoryPath + PATCHING_DIR, scr, this.extScriptsEnv);
            if (rc1 != 0) {
               this.outputHandler.error(this.readLogFile(scriptOut));
               throw new Exception("AFTER: Failed to run extension script " + scr + ", return code was " + rc1 + ", check this log for details: " + scriptOut);
            }

            this.outputHandler.info("AFTER: Successfully ran extension script " + scr + "\n" + this.readLogFile(scriptOut));
         }
      }

      this.outputHandler.info("Successfully completed update");
   }

   private void performPasteBinary(String mwHomePathToUse, String javaHomeToUse) throws Exception {
      boolean success = false;

      try {
         this.outputHandler.info("About to call pasteBinary");
         OutputStream outputStream = new FileOutputStream("pasteBin.out");
         int rc = this.scriptHelper.callPasteBinary((OutputStream)outputStream, mwHomePathToUse, javaHomeToUse, this.mwHomePath, this.patchedLocation);
         if (rc == 0) {
            if (!this.mwHomeDirectory.isDirectory()) {
               throw new Exception("Cannot find MW_HOME after paste binary at " + this.mwHomePath + " after script returned no errors");
            }

            this.outputHandler.info("Successfully completed pasteBinary call");
            success = true;
         } else {
            this.outputHandler.error("Failed to run pasteBinary on " + this.mwHomePath + ", return code is " + rc);
         }
      } catch (IOException var13) {
         throw new Exception("Failed to run pasteBinary on " + this.mwHomePath + ", exception is " + var13.getMessage(), var13);
      } finally {
         if (!success) {
            try {
               this.outputHandler.error(this.readLogFile("pasteBin.out"));
            } catch (Exception var12) {
            }

            this.outputHandler.info("Attempting to restore MWHome");
            this.recoverFromPasteBinaryError();
         }

      }

   }

   private void performExtractionOfPatchedArchive(String mwHomePathToUse, String javaHomeToUse) throws Exception {
      boolean success = false;

      try {
         OutputStream outputStream = new FileOutputStream("extractArchive.out");
         Throwable var5 = null;

         try {
            this.outputHandler.info("About to call java -jar on " + this.patchedLocation);
            int rc = this.scriptHelper.callPatchedArchive(outputStream, mwHomePathToUse, javaHomeToUse, this.mwHomePath, this.patchedLocation);
            if (rc == 0) {
               if (!this.mwHomeDirectory.isDirectory()) {
                  throw new Exception("Cannot find MW_HOME after extraction of " + this.patchedLocation + " after command returned no errors");
               }

               this.outputHandler.info("Successfully completed java -jar call");
               success = true;
            } else {
               this.outputHandler.error("Failed to run java -jar on " + this.patchedLocation + ", return code is " + rc);
            }
         } catch (Throwable var28) {
            var5 = var28;
            throw var28;
         } finally {
            if (outputStream != null) {
               if (var5 != null) {
                  try {
                     outputStream.close();
                  } catch (Throwable var27) {
                     var5.addSuppressed(var27);
                  }
               } else {
                  outputStream.close();
               }
            }

         }
      } catch (IOException var30) {
         throw new Exception("Failed to run java -jar on " + this.patchedLocation + ", exception is " + var30.getMessage(), var30);
      } finally {
         if (!success) {
            try {
               this.outputHandler.error(this.readLogFile("extractArchive.out"));
            } catch (Exception var26) {
            }

            this.outputHandler.info("Attempting to restore MWHome");
            this.recoverFromPasteBinaryError();
         }

      }

   }

   public void setExtensionEnv() {
      this.extScriptsEnv = new HashMap();
      this.extScriptsEnv.put("javaHome", this.javaHomePath);
      this.extScriptsEnv.put("mwHome", this.mwHomePath);
      this.extScriptsEnv.put("domainDir", this.domainDirectory.toString());
      this.extScriptsEnv.put("domainTmp", this.domainDirectory.toString() + PlatformUtils.fileSeparator + "tmp");
      this.extScriptsEnv.put("patched", this.patchedLocation);
      this.extScriptsEnv.put("backupDir", this.backupLocation);
      this.extScriptsEnv.put("newJavaHome", this.newJavaHome);
   }

   public void recoverFromPasteBinaryError() throws IOException {
      Path mwHomePath = Paths.get(this.mwHomePath);
      Path mwHomePatchBackup = mwHomePath.resolve(Paths.get("patching_backup", "ohome"));
      if (this.backupLocation == null) {
         this.javaHomeUpdateUtils.restoreJavaHomeFiles(mwHomePatchBackup.toString());
      } else {
         Path backupPath = Paths.get(this.backupLocation);
         Path preservedErrorMwHome = mwHomePath.resolveSibling(this.mwHomeDirectory.getName() + ".err");
         this.outputHandler.info("recoverfromPasteBinaryError is using preservedErrorMwHome of " + preservedErrorMwHome.toString());
         if (preservedErrorMwHome.toFile().exists()) {
            try {
               this.javaHomeUpdateUtils.forceDelete(preservedErrorMwHome);
            } catch (IOException var7) {
               if (this.outputHandler.isDebugEnabled()) {
                  this.outputHandler.debug("Could not delete the location " + preservedErrorMwHome + " for preserving the partial MW_HOME produced by pasteBinary");
               }
            }
         }

         Files.move(mwHomePath, preservedErrorMwHome, StandardCopyOption.REPLACE_EXISTING);
         Files.move(backupPath, mwHomePath, StandardCopyOption.REPLACE_EXISTING);
      }

      try {
         this.scriptHelper.callAttachHome("attachHOme.out");
         if (this.outputHandler.isDebugEnabled()) {
            this.outputHandler.debug("Successfully reattached MW_HOME after error");
         }

      } catch (IOException var6) {
         this.outputHandler.error("attachHome.sh reported error, " + mwHomePath + " may not be registered", var6);
         byte[] bytes = Files.readAllBytes(Paths.get("attachHOme.out"));
         String content = new String(bytes);
         this.outputHandler.error(content);
         throw var6;
      }
   }

   public void backupMWHome() throws IOException {
      Path backupPath = Paths.get(this.backupLocation);
      Path mwHomePath = Paths.get(this.mwHomePath);
      if (backupPath.toFile().exists() && this.revertFromError) {
         this.deleteMW_HOMEForRevert(mwHomePath);
      } else {
         if (backupPath.toFile().exists()) {
            this.javaHomeUpdateUtils.forceDelete(backupPath);
            if (this.outputHandler.isDebugEnabled()) {
               this.outputHandler.debug("Successfully removed stale " + backupPath + " before backup");
            }
         }

         this.outputHandler.info("Inside backupMWHome, about to call Files.move with mwHomePath: " + mwHomePath.toString() + ", backupPath: " + backupPath.toString());

         try {
            Files.move(mwHomePath, backupPath);
         } catch (IOException var6) {
            this.outputHandler.error("Caught IOException when trying to backup MW Home from " + mwHomePath.toAbsolutePath().toString() + " to destination directory =" + backupPath.toAbsolutePath().toString() + " with exception: " + var6.getMessage());
            this.outputHandler.info("Trying the old way");
            File source = mwHomePath.toFile();
            File destination = mwHomePath.toFile();
            this.outputHandler.info("Renaming source: " + source.getCanonicalPath() + ", to destination: " + destination.getCanonicalPath());
            source.renameTo(destination);
         }

         if (this.outputHandler.isDebugEnabled()) {
            this.outputHandler.debug("Moved " + mwHomePath + " to BACKUP: " + backupPath);
         }
      }

   }

   private void deleteMW_HOMEForRevert(Path mwHome) throws IOException {
      if (this.outputHandler.isDebugEnabled()) {
         this.outputHandler.debug("BACkUP_DIR is an archive file - not a dir. So we simply remove MW_HOME");
      }

      try {
         this.javaHomeUpdateUtils.forceDelete(mwHome);
         if (this.outputHandler.isDebugEnabled()) {
            this.outputHandler.debug("Successfully removed " + mwHome + " for revert");
         }
      } catch (IOException var4) {
         Path tmpMW_Home = Paths.get(this.mwHomePath + "_tmp");
         this.outputHandler.info("Could not remove " + mwHome + " for revert - moving it to " + tmpMW_Home);
         Files.move(mwHome, tmpMW_Home, StandardCopyOption.REPLACE_EXISTING);
         if (this.outputHandler.isDebugEnabled()) {
            this.outputHandler.debug("Moved " + mwHome + " to " + tmpMW_Home + " after problem deleting it");
         }
      }

   }

   public void validate() throws Exception {
      if (this.outputHandler.isDebugEnabled()) {
         this.outputHandler.info("Will validate update; patchedLocation: " + this.patchedLocation + " backupLocation: " + this.backupLocation + " newJavaHome: " + this.newJavaHome + " revertFromError: " + this.revertFromError + " verbose: " + this.verbose);
      }

      String hostname = this.getHostName();
      String errContent;
      if ((new File(this.bootstrapDirectory, "oracleHomeUpdateScript.out")).exists()) {
         errContent = "Update OUTPUT.  Attempting to show details from " + hostname + ":" + this.bootstrapDirectory + File.separator + "oracleHomeUpdateScript.out";
         String content = new String(Files.readAllBytes(Paths.get(this.bootstrapDirectory.toString(), "oracleHomeUpdateScript.out")));
         if (!content.isEmpty() && this.outputHandler.isDebugEnabled()) {
            this.outputHandler.debug(errContent + "\n" + content);
         }
      }

      String javaHomePath;
      if ((new File(this.bootstrapDirectory, "updateErrors.err")).exists()) {
         errContent = new String(Files.readAllBytes(Paths.get(this.bootstrapDirectory.toString(), "updateErrors.err")));
         if (!errContent.isEmpty()) {
            File destination = this.moveTmpDirFiles();
            Objects.requireNonNull(destination);
            File errorFileDir = null;
            if (Files.exists(Paths.get(destination.toString(), "updateErrors.err"), new LinkOption[0])) {
               errorFileDir = destination;
            } else {
               errorFileDir = this.bootstrapDirectory;
            }

            javaHomePath = "Update FAILURE.  Attempting to show details from " + hostname + ":" + errorFileDir + File.separator + "updateErrors.err";
            this.outputHandler.error(javaHomePath + "\n" + errContent);
            String severeMsg = this.getSevereMsg(errContent);
            if (severeMsg == null || severeMsg.isEmpty()) {
               severeMsg = "Update FAILURE. Error details can be obtained from " + hostname + ":" + errorFileDir + File.separator + "updateErrors.err";
            }

            throw new IOException(severeMsg);
         }

         this.cleanUpFiles();
      } else {
         boolean isJavaRollout = this.newJavaHome != null && !this.newJavaHome.isEmpty();
         boolean isOHRollout = this.patchedLocation != null && !this.patchedLocation.isEmpty();
         if (isOHRollout) {
            Path backupPath = Paths.get(this.backupLocation);
            if (!this.revertFromError) {
               if (!backupPath.toFile().exists()) {
                  throw new Exception("Backup directory " + this.backupLocation + " does not exist");
               }

               if (!backupPath.toFile().isDirectory()) {
                  throw new Exception("Backup location" + this.backupLocation + " should be a directory");
               }

               if (backupPath.toFile().list().length == 0) {
                  throw new Exception("Backup directory " + this.backupLocation + " is empty");
               }
            }
         }

         if (isJavaRollout) {
            String javaHomeEnv = PlatformUtils.getJavaHomePath();
            if (javaHomeEnv == null) {
               throw new IllegalArgumentException("Environment variable JAVA_HOME must be set");
            }

            javaHomePath = this.normalizePath(javaHomeEnv);
            if (!javaHomePath.equals(this.newJavaHome)) {
               throw new Exception("JAVA_HOME not updated. New JAVA_HOME should be " + this.newJavaHome + " instead of " + javaHomePath);
            }
         }

         this.outputHandler.info("Update Successful");
         this.cleanUpFiles();
      }

      this.outputHandler.info("Successfully completed validate");
   }

   private String getSevereMsg(String input) {
      String[] lines = input.split("\\r?\\n");
      String[] var3 = lines;
      int var4 = lines.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String line = var3[var5];
         if (line.trim().startsWith("SEVERE")) {
            return line;
         }
      }

      return null;
   }

   public boolean cleanUpFiles() {
      boolean success = true;

      File f;
      for(Iterator var2 = this.filesToClean.iterator(); var2.hasNext(); success &= this.cleanUpFile(f)) {
         f = (File)var2.next();
      }

      return success;
   }

   public File moveTmpDirFiles() {
      File dstDir = new File(this.bootstrapDirectory, "workflow_" + (new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss")).format(new Date()));
      if (!dstDir.exists()) {
         dstDir.mkdir();
      }

      File f;
      boolean isErr;
      for(Iterator var2 = this.filesToClean.iterator(); var2.hasNext(); this.moveFile(f, dstDir, isErr)) {
         f = (File)var2.next();
         isErr = false;
         if (f.getAbsolutePath().endsWith(".err")) {
            isErr = true;
         }
      }

      String errFileLocation = "Update FAILURE.  All relevant files related to this failure are stored in directory " + dstDir.getAbsolutePath();
      this.outputHandler.error(errFileLocation);
      return dstDir;
   }

   public void moveFile(File f, File dst, boolean isErr) {
      Path source = f.toPath();
      Path newdir = dst.toPath();

      try {
         if (f.exists()) {
            if (!isErr) {
               Files.move(source, newdir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
               if (this.outputHandler.isDebugEnabled()) {
                  this.outputHandler.debug("Moved " + f.getAbsolutePath() + " to dir " + dst.getAbsolutePath());
               }
            } else {
               Files.copy(source, newdir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
               if (this.outputHandler.isDebugEnabled()) {
                  this.outputHandler.debug("Copied " + f.getAbsolutePath() + " to dir " + dst.getAbsolutePath());
               }
            }
         }
      } catch (IOException var7) {
         this.outputHandler.error("Caught IOException moving/copying src file=" + f.getAbsolutePath() + " to destination directory =" + dst.getAbsolutePath() + " with exception: " + var7.getMessage());
      }

   }

   public String readLogFile(String fileName) throws IOException {
      return new String(Files.readAllBytes(Paths.get(fileName)));
   }

   public boolean cleanUpFile(File file) {
      boolean result = true;
      if (file.exists()) {
         result = file.delete();
      }

      if (!result) {
         this.outputHandler.info("Failed to delete file " + file.getPath());

         try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss");
            File backFile = new File(file.getCanonicalPath() + "." + sdf.format(new Date()));
            if (file.renameTo(backFile)) {
               this.outputHandler.info("Renamed " + file.getPath() + " to " + backFile.getPath());
            } else {
               this.outputHandler.info("Could not rename " + file.getPath() + " to " + backFile.getPath());
            }
         } catch (Exception var5) {
         }
      }

      return result;
   }

   public void copyFileToDirectory(File sourceFile, File targetDirectory) throws IOException {
      File targetFile = new File(targetDirectory.getCanonicalPath() + File.separator + sourceFile.getName());
      this.copyFile(sourceFile, targetFile);
   }

   public void copyFile(File sourceFile, File target) throws IOException {
      this.cleanUpFile(target);
      CopyOption[] options = new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING};
      if (!sourceFile.exists()) {
         throw new IllegalArgumentException("Failed to copy, source file missing " + sourceFile.getCanonicalPath());
      } else {
         this.outputHandler.info("Attempting to copy " + sourceFile.toPath() + " to " + target.toPath());
         Files.copy(sourceFile.toPath(), target.toPath(), options);
      }
   }

   public void moveDirectoryToDirectory(File sourceDirectory, File targetDirectory) throws IOException {
      Path source = sourceDirectory.toPath();
      Path newdir = targetDirectory.toPath();

      try {
         this.outputHandler.info("Testing single move command method.. " + source + " to " + newdir);
         Files.move(source, newdir, StandardCopyOption.REPLACE_EXISTING);
         this.outputHandler.info("Single move success!");
      } catch (Exception var7) {
         this.outputHandler.error("Single move command did not work - " + var7.getMessage());
         String newDest = targetDirectory.getPath() + "." + sourceDirectory.getName();
         this.outputHandler.info("Try rename of " + sourceDirectory + " to " + newDest);
         if (!sourceDirectory.renameTo(new File(newDest))) {
            this.outputHandler.error("Failed to do rename as well " + sourceDirectory + " to " + newDest);
            throw var7;
         }
      }

   }

   public String normalizePath(String locationStr) {
      String normalPath = locationStr;

      try {
         if (locationStr != null && !locationStr.isEmpty()) {
            normalPath = (new File(locationStr)).getCanonicalPath();
         }

         return normalPath;
      } catch (IOException var4) {
         throw new IllegalArgumentException("Invalid value " + locationStr, var4);
      }
   }

   public String normalizePath(File file) {
      String normalPath = null;

      try {
         if (file != null) {
            normalPath = file.getCanonicalPath();
         }

         return normalPath;
      } catch (IOException var4) {
         throw new IllegalArgumentException("Invalid value file " + file.getPath(), var4);
      }
   }

   public static void main(String[] args) {
      ZdtAgentBufferedOutputHandler outputHandler = new ZdtAgentBufferedOutputHandler(true);
      if (args == null || args.length == 0) {
         outputHandler.error(usageString);
         dumpMessages(outputHandler);
         System.exit(1);
      }

      ZdtAgentRequest request = null;
      String action = args[0];
      String revertFromErrorString;
      if (ZdtAgentAction.PREPARE_FOR_UPDATE.displayString.equalsIgnoreCase(action)) {
         if (args.length < 5) {
            outputHandler.error(usageString);
            dumpMessages(outputHandler);
            System.exit(2);
         }

         request = createRequest(ZdtAgentAction.PREPARE_FOR_UPDATE.displayString, args[1], args[2], args[3], args[4], args[7], args[8], args[9]);
      } else if (ZdtAgentAction.UPDATE.displayString.equalsIgnoreCase(action)) {
         if (args.length < 5) {
            outputHandler.error(usageString);
            dumpMessages(outputHandler);
            System.exit(2);
         }

         revertFromErrorString = args.length > 5 ? args[5] : "false";
         request = createRequest(ZdtAgentAction.UPDATE.displayString, args[1], args[2], args[3], args[4], revertFromErrorString, args[7], args[8], args[9]);
      } else if (ZdtAgentAction.CHECK_PREREQUISITES.displayString.equalsIgnoreCase(action)) {
         if (args.length < 5) {
            outputHandler.error(usageString);
            dumpMessages(outputHandler);
            System.exit(2);
         }

         request = createRequest(ZdtAgentAction.CHECK_PREREQUISITES.displayString, args[1], args[2], args[3], args[4], args[7], args[8], args[9]);
      } else if (ZdtAgentAction.VALIDATE_UPDATE.displayString.equalsIgnoreCase(action)) {
         if (args.length < 5) {
            outputHandler.error(usageString);
            dumpMessages(outputHandler);
            System.exit(2);
         }

         request = createRequest(ZdtAgentAction.VALIDATE_UPDATE.displayString, args[1], args[2], args[3], args[4], args[7], args[8], args[9]);
      } else if (ZdtAgentAction.EXTRACT.displayString.equalsIgnoreCase(action)) {
         if (args.length < 5) {
            outputHandler.error(usageString);
            dumpMessages(outputHandler);
            System.exit(2);
         }

         request = createRequest(ZdtAgentAction.EXTRACT.displayString, args[1], args[2], args[3], args[4], args[7], args[8], args[9]);
      } else {
         outputHandler.error("Error: Action \"" + action + "\" not supported.  Please refer to the command usage and try again");
         outputHandler.error(usageString);
         dumpMessages(outputHandler);
         System.exit(3);
      }

      outputHandler.info("Starting Update with action " + action);
      revertFromErrorString = "  action: " + action + "\n  domainDir: " + args[1] + "\n  patched:   " + args[2] + "\n  backup:    " + args[3] + "\n  javaHome:  " + args[4] + "\n  workflowId: " + args[7] + "\n  MW_HOME:   " + System.getenv("MW_HOME") + "\n  TMP_UPDATE_SCRIPT: " + System.getenv("TMP_UPDATE_SCRIPT");
      outputHandler.info("About to create ZdtUpdateOracleHomeAgent\n" + revertFromErrorString);

      try {
         ZdtUpdateOracleHomeAgent agent = new ZdtUpdateOracleHomeAgent(request, outputHandler);
         agent.execRequest();
      } catch (Exception var6) {
         outputHandler.error("Failed to prepare", var6);
         dumpMessages(outputHandler);
         System.exit(4);
      }

      dumpMessages(outputHandler);
   }

   public static ZdtAgentRequest createRequest(String action, String domainDir, String patched, String backup, String javaHome, String workflowId, String beforeUpdateExtensions, String afterUpdateExtensions) {
      return createRequest(action, domainDir, patched, backup, javaHome, "false", workflowId, beforeUpdateExtensions, afterUpdateExtensions);
   }

   public static ZdtAgentRequest createRequest(String action, String domainDir, String patched, String backup, String javaHome, String revertFromError, String workflowId, String beforeUpdateExtensions, String afterUpdateExtensions) {
      String[] command = new String[]{ZdtAgentParam.ACTION_PARAM.getDisplayString(), action, ZdtAgentParam.DOMAIN_DIR_PARAM.getDisplayString(), domainDir, ZdtAgentParam.PATCHED_PARAM.getDisplayString(), patched, ZdtAgentParam.BACKUP_DIR_PARAM.getDisplayString(), backup, ZdtAgentParam.NEW_JAVA_HOME_PARAM.getDisplayString(), javaHome, ZdtAgentParam.REVERT_PARAM.getDisplayString(), revertFromError, ZdtAgentParam.VERBOSE_PARAM.getDisplayString(), "true", ZdtAgentParam.WORKFLOW_ID_PARAM.getDisplayString(), workflowId, ZdtAgentParam.BEFORE_UPDATE_EXTENSIONS_PARAM.getDisplayString(), beforeUpdateExtensions, ZdtAgentParam.AFTER_UPDATE_EXTENSIONS_PARAM.getDisplayString(), afterUpdateExtensions};
      ZdtAgentRequest zdtAgentRequest = new ZdtAgentRequest(command);
      return zdtAgentRequest;
   }

   protected static void dumpMessages(ZdtAgentBufferedOutputHandler zdtAgentBufferedOutputHandler) {
      BufferedWriter errWriter = null;

      try {
         errWriter = new BufferedWriter(new FileWriter("updateErrors.err", true));
         Iterator var2 = zdtAgentBufferedOutputHandler.getMessages().iterator();

         while(var2.hasNext()) {
            ZdtAgentLogMessage m = (ZdtAgentLogMessage)var2.next();
            String mCause = m.getCause();
            if (mCause == null || mCause.isEmpty() || "null".equals(mCause)) {
               mCause = null;
            }

            String formattedMsg = m.getLevel() + " - " + m.getMsgContents().trim() + (mCause == null ? "" : " exception: " + mCause);
            if (m.getLevel() != Level.SEVERE) {
               System.out.println(formattedMsg);
            } else {
               System.err.println(formattedMsg);
               errWriter.write(formattedMsg);
               errWriter.write("\n");
            }
         }
      } catch (IOException var14) {
         System.err.println("Error writing log messages to file updateErrors.err, " + var14.getMessage());
      } finally {
         if (errWriter != null) {
            try {
               errWriter.flush();
               errWriter.close();
            } catch (IOException var13) {
            }
         }

      }

   }

   public String getHostName() {
      String canonicalHostName = "";

      try {
         canonicalHostName = InetAddress.getLocalHost().getCanonicalHostName();
      } catch (UnknownHostException var3) {
         this.outputHandler.error("Failed to get Hostname ", var3);
      }

      this.outputHandler.info("ZDTUpdateOracleHomeAgent running on hostname " + canonicalHostName);
      return canonicalHostName;
   }
}
