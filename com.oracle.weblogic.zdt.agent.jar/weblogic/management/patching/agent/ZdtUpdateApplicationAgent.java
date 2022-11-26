package weblogic.management.patching.agent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class ZdtUpdateApplicationAgent extends ZdtAgent {
   private Path currentApplicationLocation;
   private Path patchedLocation;
   private Path backupLocation;
   private Path domainDir;
   private boolean resume = false;
   private boolean verbose = false;
   private ZdtAgentOutputHandler outputHandler;
   private String workflowId;
   private Map extScriptsEnv;
   private ZdtAgentScriptHelper scriptHelper;
   private ArrayList extBeforeUpdateScripts;
   private ArrayList extAfterUpdateScripts;
   protected File domainDirectory;

   public ZdtUpdateApplicationAgent(ZdtAgentRequest request, ZdtAgentOutputHandler outputHandler, File domainDir) {
      super(request.getAction());
      this.outputHandler = outputHandler;
      this.domainDir = domainDir.toPath();
      this.currentApplicationLocation = Paths.get(request.get(ZdtAgentParam.CURRENT_PARAM));
      this.patchedLocation = Paths.get(request.get(ZdtAgentParam.PATCHED_PARAM));
      this.backupLocation = Paths.get(request.get(ZdtAgentParam.BACKUP_DIR_PARAM));
      this.resume = Boolean.parseBoolean(request.get(ZdtAgentParam.RESUME_PARAM));
      this.verbose = Boolean.parseBoolean(request.get(ZdtAgentParam.VERBOSE_PARAM));
      this.workflowId = request.get(ZdtAgentParam.WORKFLOW_ID_PARAM);
      this.scriptHelper = new ZdtAgentScriptHelper(outputHandler);
      String domainDirectoryString = request.get(ZdtAgentParam.DOMAIN_DIR_PARAM);
      if (domainDirectoryString == null) {
         throw new IllegalArgumentException("Domain Directory must be set");
      } else {
         this.domainDirectoryPath = this.normalizePath(domainDirectoryString);
         this.domainDirectory = new File(this.domainDirectoryPath);
         if (request.get(ZdtAgentParam.BEFORE_UPDATE_EXTENSIONS_PARAM) != null) {
            this.extBeforeUpdateScripts = new ArrayList(Arrays.asList(request.get(ZdtAgentParam.BEFORE_UPDATE_EXTENSIONS_PARAM).split("\\s*,\\s*")));
         }

         if (request.get(ZdtAgentParam.AFTER_UPDATE_EXTENSIONS_PARAM) != null) {
            this.extAfterUpdateScripts = new ArrayList(Arrays.asList(request.get(ZdtAgentParam.AFTER_UPDATE_EXTENSIONS_PARAM).split("\\s*,\\s*")));
         }

      }
   }

   public boolean isPathRelativeToLocation(Path path, Path location) {
      Path relPath = Paths.get(location.toString(), path.toString());
      if (this.verbose) {
         this.outputHandler.debug("Checking Path " + relPath);
      }

      Path fp;
      try {
         fp = relPath.toRealPath();
      } catch (NoSuchFileException var6) {
         if (this.verbose) {
            this.outputHandler.debug("Path Does not Exists: " + relPath);
         }

         return false;
      } catch (IOException var7) {
         throw new InvalidPathException(path.toString(), "Path I/O Error");
      }

      if (this.verbose) {
         this.outputHandler.debug("Path Exists: " + fp);
      }

      return true;
   }

   public void copyFileOrDirectory(Path src, Path dest) throws Exception {
      if (src.toFile().isDirectory()) {
         FileUtils.copyDirectory(src.toFile(), dest.toFile(), false);
      } else {
         FileUtils.copyFile(src.toFile(), dest.toFile(), false);
      }

   }

   public void moveFileorDirectory(Path src, Path dest) throws Exception {
      if (src.toFile().isDirectory()) {
         FileUtils.moveDirectory(src.toFile(), dest.toFile());
      } else {
         FileUtils.moveFile(src.toFile(), dest.toFile());
      }

   }

   public void deleteFileorDirectory(Path fileOrDirectory) throws Exception {
      if (fileOrDirectory.toFile().isDirectory()) {
         FileUtils.deleteDirectory(fileOrDirectory.toFile());
      } else {
         Files.delete(fileOrDirectory);
      }

   }

   public void validateArgs() throws Exception {
      Path oldScriptPath = Paths.get(this.domainDir.toString(), "bin", "patching");
      if (this.verbose) {
         this.outputHandler.debug("In validateArgs : Will check all paths; currentLocation: " + this.currentApplicationLocation + " patchedLocation: " + this.patchedLocation + " backupLocation: " + this.backupLocation + " resume: " + this.resume + " verbose: " + this.verbose);
      }

      if (this.currentApplicationLocation == null) {
         throw new InvalidPathException("NULL", "Must supply CURRENT to update the current location");
      } else if (this.patchedLocation == null) {
         throw new InvalidPathException("NULL", "Must supply PATCHED to update CURRENT");
      } else {
         if (!this.currentApplicationLocation.isAbsolute()) {
            Path relCurrent;
            if (this.isPathRelativeToLocation(this.currentApplicationLocation, this.domainDir)) {
               relCurrent = Paths.get(this.domainDir.toString(), this.currentApplicationLocation.toString());
               this.currentApplicationLocation = relCurrent.toRealPath();
            } else {
               if (!this.isPathRelativeToLocation(this.currentApplicationLocation, oldScriptPath)) {
                  throw new InvalidPathException(this.currentApplicationLocation.toString(), "Could not find the source relative to the domain or the domain/bin/patching dir: " + this.currentApplicationLocation + ".It must be set to a valid archive or directory");
               }

               relCurrent = Paths.get(oldScriptPath.toString(), this.currentApplicationLocation.toString());
               this.currentApplicationLocation = relCurrent.toRealPath();
            }

            if (this.verbose) {
               this.outputHandler.debug("The resolved relative Current path :" + this.currentApplicationLocation);
            }
         }

         if (!this.currentApplicationLocation.toFile().exists()) {
            throw new InvalidPathException(this.currentApplicationLocation.toString(), "CURRENT does not exist: " + this.currentApplicationLocation + ", it must be set to a valid archive or directory");
         } else {
            if (!this.patchedLocation.isAbsolute()) {
               Path relPatched;
               if (this.isPathRelativeToLocation(this.patchedLocation, this.domainDir)) {
                  relPatched = Paths.get(this.domainDir.toString(), this.patchedLocation.toString());
                  this.patchedLocation = relPatched.toRealPath();
               } else {
                  if (!this.isPathRelativeToLocation(this.patchedLocation, oldScriptPath)) {
                     throw new InvalidPathException(this.patchedLocation.toString(), "Could not find the patched source relative to the domain or the domain/bin/patching dir: " + this.patchedLocation + ".It must be set to a valid archive or directory");
                  }

                  relPatched = Paths.get(oldScriptPath.toString(), this.patchedLocation.toString());
                  this.patchedLocation = relPatched.toRealPath();
               }

               if (this.verbose) {
                  this.outputHandler.debug("The resolved relative Patched path :" + this.patchedLocation);
               }
            }

            if (!this.patchedLocation.toFile().exists()) {
               if (this.resume && Files.exists(this.backupLocation, new LinkOption[0]) && Files.size(this.patchedLocation) != 0L) {
                  throw new InvalidPathException(this.patchedLocation.toString(), "PATCHED does not exist after resume: " + this.patchedLocation + ".It most likely has already been applied");
               } else {
                  throw new InvalidPathException(this.patchedLocation.toString(), "PATCHED does not exist: " + this.patchedLocation + ", it must be set to a valid archive or directory");
               }
            } else {
               Path checkPatched = this.patchedLocation.toRealPath();
               Path checkCurrent = this.currentApplicationLocation.toRealPath();
               if (checkPatched.equals(checkCurrent)) {
                  throw new InvalidPathException(checkPatched.toString(), "Application patchedLocation " + checkPatched + " is the same as the current location, " + checkCurrent + ".A unique patchedLocation path should be given in order to move the current location aside for preservation.");
               } else if (this.backupLocation != null && !this.backupLocation.toString().equals("")) {
                  if (!this.backupLocation.isAbsolute()) {
                     Path relBackup;
                     if (this.backupLocation.getParent() != null && !this.isPathRelativeToLocation(this.backupLocation.getParent(), this.domainDir)) {
                        if (!this.isPathRelativeToLocation(this.backupLocation.getParent(), oldScriptPath)) {
                           throw new InvalidPathException(this.backupLocation.toString(), "Could not find the backup dir relative to the domain or the current working dir.The given value " + this.backupLocation + " must be set to a valid archive or directory");
                        }

                        relBackup = Paths.get(oldScriptPath.toString(), this.backupLocation.toString());
                        this.backupLocation = relBackup.normalize().toAbsolutePath();
                     } else {
                        relBackup = Paths.get(this.domainDir.toString(), this.backupLocation.toString());
                        this.backupLocation = relBackup.normalize().toAbsolutePath();
                     }

                     if (this.verbose) {
                        this.outputHandler.debug("The resolved relative backup path :" + this.backupLocation);
                     }
                  }

                  Path checkBackup = this.backupLocation;
                  if (checkPatched.equals(checkBackup)) {
                     throw new InvalidPathException(checkBackup.toString(), "Application BackupLocation " + checkBackup.toString() + " is the same as the patched location, " + checkPatched.toString() + ".A unique BackupLocation path should be given in order to move the current location aside for preservation.");
                  } else if (checkCurrent.equals(checkBackup)) {
                     throw new InvalidPathException(checkBackup.toString(), "Application BackupLocation " + checkBackup.toString() + " is the same as the current location, " + checkCurrent.toString() + ".A unique BackupLocation path should be given in order to move the current location aside for preservation.");
                  } else {
                     if (this.verbose) {
                        this.outputHandler.debug("Current is : " + this.currentApplicationLocation.toString());
                        this.outputHandler.debug("Patched is: " + this.patchedLocation.toString());
                        this.outputHandler.debug("Backup dir will be: " + this.backupLocation.toString());
                        this.outputHandler.debug("Action is: " + this.action.displayString);
                     }

                  }
               } else {
                  throw new InvalidPathException(this.backupLocation.toString(), "Must supply Backup Directory");
               }
            }
         }
      }
   }

   public void checkPrerequisites() throws Exception {
      if (this.verbose) {
         this.outputHandler.debug("Checking prereq arguments");
      }

      this.validateArgs();
      this.outputHandler.info("checkreq Successful");
   }

   public void prepare() throws Exception {
      throw new UnsupportedOperationException();
   }

   public void backupCurrent() throws Exception {
      if (this.verbose) {
         this.outputHandler.debug("backUpCurrent");
      }

      if (this.backupLocation.toFile().exists()) {
         this.deleteFileorDirectory(this.backupLocation);
         if (this.verbose) {
            this.outputHandler.debug("successfuly removed stale " + this.backupLocation + " before backup");
         }
      } else if (this.verbose) {
         this.outputHandler.debug("Backup Dir does not exist :" + this.backupLocation + " before backup");
      }

      this.moveFileorDirectory(this.currentApplicationLocation, this.backupLocation);
      if (this.verbose) {
         this.outputHandler.debug("Moved " + this.currentApplicationLocation + " to BACKUP: " + this.backupLocation);
      }

   }

   public void update() throws Exception {
      this.outputHandler.info("Setting extension environment variables");
      this.setExtensionEnv();
      Iterator var1;
      String scr;
      String scriptOut;
      int rc1;
      if (this.extBeforeUpdateScripts.toString() != null && !this.extBeforeUpdateScripts.toString().equalsIgnoreCase("[]")) {
         this.outputHandler.info("BEFORE: About to call before update extension scripts " + this.extBeforeUpdateScripts.toString());
         var1 = this.extBeforeUpdateScripts.iterator();

         while(var1.hasNext()) {
            scr = (String)var1.next();
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

      if (this.verbose) {
         this.outputHandler.debug("Will do the update; currentLocation: " + this.currentApplicationLocation + " patchedLocation: " + this.patchedLocation + " backupLocation: " + this.backupLocation + " resume: " + this.resume + " verbose: " + this.verbose);
      }

      this.validateArgs();
      this.backupCurrent();
      if (this.verbose) {
         this.outputHandler.debug("Now will move " + this.patchedLocation + " dir to " + this.currentApplicationLocation);
      }

      try {
         this.copyFileOrDirectory(this.patchedLocation, this.currentApplicationLocation);
      } catch (Exception var5) {
         this.outputHandler.error("Could not move " + this.patchedLocation + "to " + this.currentApplicationLocation + " , restoring " + this.currentApplicationLocation, var5);
         this.moveFileorDirectory(this.backupLocation, this.currentApplicationLocation);
         throw var5;
      }

      if (this.verbose) {
         this.outputHandler.debug("Successfully moved the PATCHED_DIR into place, " + this.patchedLocation + "->" + this.currentApplicationLocation);
      }

      if (this.extAfterUpdateScripts.toString() != null && !this.extAfterUpdateScripts.toString().equalsIgnoreCase("[]")) {
         this.outputHandler.info("AFTER: About to call extension scripts " + this.extAfterUpdateScripts.toString());
         var1 = this.extAfterUpdateScripts.iterator();

         while(var1.hasNext()) {
            scr = (String)var1.next();
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

      this.outputHandler.info("Successfully updated " + this.currentApplicationLocation);
   }

   public void validate() throws Exception {
      throw new UnsupportedOperationException();
   }

   public String readLogFile(String fileName) throws IOException {
      return new String(Files.readAllBytes(Paths.get(fileName)));
   }

   public void setExtensionEnv() {
      this.extScriptsEnv = new HashMap();
      this.extScriptsEnv.put("javaHome", PlatformUtils.getJavaHomePath());
      this.extScriptsEnv.put("mwHome", PlatformUtils.getMWHomePath());
      this.extScriptsEnv.put("domainDir", this.domainDir.toString());
      this.extScriptsEnv.put("domainTmp", this.domainDir.toString() + PlatformUtils.fileSeparator + "tmp");
      this.extScriptsEnv.put("patched", this.patchedLocation.toString());
      this.extScriptsEnv.put("backupDir", this.backupLocation.toString());
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
}
