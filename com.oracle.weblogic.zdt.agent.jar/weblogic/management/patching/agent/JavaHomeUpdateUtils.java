package weblogic.management.patching.agent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JavaHomeUpdateUtils {
   public static final String DOMAIN_ENV_SCRIPT_NAME = "setDomainEnv";
   public static final String BIN_DIR = "bin";
   public static final String INIT_INFO_DIR = "init-info";
   public static final String NM_PROPS_NAME = "nodemanager.properties";
   public static final String TOKEN_VALUES_NAME = "tokenValue.properties";
   public static final String DOMAIN_INFO_NAME = "domain-info.xml";
   public static final String NM_PROPS_XML_NAME = "nodemanager-properties.xml";
   public static final String START_SCRIPT_XML_NAME = "startscript.xml";
   public static final String NM_JAVA_HOME_NAME = "setNMJavaHome";
   public static final String ORACLE_COMMON_DIR = "oracle_common";
   public static final String COMMON_DIR = "common";
   public static final String NODEMANAGER_DIR = "nodemanager";
   public static final String NM_PROPS_FILE_NAME = "nodemanager.properties";
   public static final String FULL_JAVA_HOME_KEY = "FULL_JAVA_HOME";
   public static final String UPPERCASE_DRIVE_LETTER_FULL_JAVA_HOME_KEY = "UPPERCASE_DRIVE_LETTER_FULL_JAVA_HOME";
   public static final String LOWERCASE_DRIVE_LETTER_FULL_JAVA_HOME_KEY = "LOWERCASE_DRIVE_LETTER_FULL_JAVA_HOME";
   public static final String UPPERCASE_DRIVE_LETTER_ESC_FULL_JAVA_KEY = "UPPERCASE_DRIVE_LETTER_ESC_FULL_JAVA_HOME";
   public static final String LOWERCASE_DRIVE_LETTER_ESC_FULL_JAVA_KEY = "LOWERCASE_DRIVE_LETTER_ESC_FULL_JAVA_HOME";
   public static final String UPPERCASE_DRIVE_LETTER_SHORT_JAVA_HOME_KEY = "UPPERCASE_DRIVE_LETTER_SHORT_JAVA_HOME";
   public static final String LOWERCASE_DRIVE_LETTER_SHORT_JAVA_HOME_KEY = "LOWERCASE_DRIVE_LETTER_SHORT_JAVA_HOME";
   public static final String UPPERCASE_DRIVE_LETTER_ESC_SHORT_JAVA_HOME_KEY = "UPPERCASE_DRIVE_LETTER_ESC_SHORT_JAVA_HOME";
   public static final String LOWERCASE_DRIVE_LETTER_ESC_SHORT_JAVA_HOME_KEY = "LOWERCASE_DRIVE_LETTER_ESC_SHORT_JAVA_HOME";
   private ZdtAgentOutputHandler outputHandler;
   private Map javaHomeFormats;

   public JavaHomeUpdateUtils(ZdtAgentOutputHandler outputHandler) {
      this.outputHandler = outputHandler;
      this.createJavaHomeFormats();
   }

   private void createJavaHomeFormats() {
      this.javaHomeFormats = new HashMap();
      String envJavaPath = PlatformUtils.getJavaHomePath();
      String fullJavaPath = envJavaPath;

      try {
         fullJavaPath = (new File(envJavaPath)).getCanonicalPath();
      } catch (IOException var6) {
         if (this.outputHandler.isDebugEnabled()) {
            this.outputHandler.debug("Could not make sure we have fullJavaPath from " + envJavaPath + " due to: " + var6);
         }
      }

      this.javaHomeFormats.put("FULL_JAVA_HOME", fullJavaPath);
      if (PlatformUtils.isWindows) {
         this.javaHomeFormats.put("UPPERCASE_DRIVE_LETTER_FULL_JAVA_HOME", this.capitalizeFirstLetter(fullJavaPath));
         this.javaHomeFormats.put("LOWERCASE_DRIVE_LETTER_FULL_JAVA_HOME", this.decapitalizeFirstLetter(fullJavaPath));
         this.javaHomeFormats.put("UPPERCASE_DRIVE_LETTER_ESC_FULL_JAVA_HOME", this.capitalizeFirstLetter(this.escapePathForWindows(fullJavaPath)));
         this.javaHomeFormats.put("LOWERCASE_DRIVE_LETTER_ESC_FULL_JAVA_HOME", this.decapitalizeFirstLetter(this.escapePathForWindows(fullJavaPath)));
         String short_java_home = envJavaPath;
         if (envJavaPath.equalsIgnoreCase(fullJavaPath)) {
            try {
               short_java_home = this.getShortJavaHomeWindows(fullJavaPath);
            } catch (IOException var5) {
               this.outputHandler.warning("Could not convert " + fullJavaPath + " to the MSDOS shortened name for string replacement");
            }
         }

         this.javaHomeFormats.put("UPPERCASE_DRIVE_LETTER_SHORT_JAVA_HOME", this.capitalizeFirstLetter(short_java_home));
         this.javaHomeFormats.put("LOWERCASE_DRIVE_LETTER_SHORT_JAVA_HOME", this.decapitalizeFirstLetter(short_java_home));
         this.javaHomeFormats.put("UPPERCASE_DRIVE_LETTER_ESC_SHORT_JAVA_HOME", this.capitalizeFirstLetter(this.escapePathForWindows(short_java_home)));
         this.javaHomeFormats.put("LOWERCASE_DRIVE_LETTER_ESC_SHORT_JAVA_HOME", this.decapitalizeFirstLetter(this.escapePathForWindows(short_java_home)));
      }

   }

   public String decapitalizeFirstLetter(String string) {
      if (string != null && string.length() != 0) {
         char[] c = string.toCharArray();
         c[0] = Character.toLowerCase(c[0]);
         return new String(c);
      } else {
         return string;
      }
   }

   public String capitalizeFirstLetter(String string) {
      if (string != null && string.length() != 0) {
         char[] c = string.toCharArray();
         c[0] = Character.toUpperCase(c[0]);
         return new String(c);
      } else {
         return string;
      }
   }

   private String escapePathForWindows(String javaPath) {
      String esc_java_home = javaPath.replace("\\", "\\\\");
      return esc_java_home.replace(":", "\\:");
   }

   private String getShortJavaHomeWindows(String fullJavaPath) throws IOException {
      assert PlatformUtils.isWindows;

      ArrayList command = new ArrayList();
      command.add("cmd");
      command.add("/c");
      command.add("for");
      command.add("%A");
      command.add("in");
      command.add("(\"" + fullJavaPath + "\")");
      command.add("do");
      command.add("@echo");
      command.add("%~fsA");
      String[] cmd = new String[command.size()];
      command.toArray(cmd);
      if (this.outputHandler.isDebugEnabled()) {
         this.outputHandler.debug("Will now execute " + command);
      }

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      (new ZdtAgentScriptHelper(this.outputHandler)).callScript(cmd, new File(fullJavaPath), out, TimeUnit.MILLISECONDS.convert(10L, TimeUnit.SECONDS));
      String cmdOut = new String(out.toByteArray());
      if (this.outputHandler.isDebugEnabled()) {
         this.outputHandler.debug("After executing " + command + "\n\t the output is:\n\n" + cmdOut + " and our fullJavaPath is: " + fullJavaPath);
      }

      return (new File(cmdOut)).getPath();
   }

   public List getJavaHomeDomainFiles(String domainDir) throws IOException {
      File domain = new File(domainDir);
      return this.getJavaHomeDomainFiles(domain);
   }

   public static Predicate isNotArchive() {
      return (f) -> {
         return f.toString().endsWith(".sh") || f.toString().endsWith(".cmd") || f.toString().endsWith(".bat") || f.toString().endsWith(".xml") || f.toString().endsWith(".properties");
      };
   }

   public static Predicate isNotBackup() {
      Path mwHome = Paths.get(PlatformUtils.getMWHomePath());
      Path backupLocation = Paths.get(mwHome.toString(), "patching_backup");
      return (f) -> {
         return !f.startsWith(backupLocation);
      };
   }

   public void backupJavaHomeFiles(String backupLocation) throws IOException {
      if (this.outputHandler.isDebugEnabled()) {
         this.outputHandler.debug("backupJavaHomeFiles to " + backupLocation);
      }

      Path mwHome = Paths.get(PlatformUtils.getMWHomePath());
      Path backupLoc = Paths.get(backupLocation);
      List files = new ArrayList();
      List allFiles = (List)Files.walk(mwHome).filter((x$0) -> {
         return Files.isRegularFile(x$0, new LinkOption[0]);
      }).filter(isNotArchive()).filter(isNotBackup()).collect(Collectors.toList());
      Iterator var6 = allFiles.iterator();

      while(var6.hasNext()) {
         Path filePath = (Path)var6.next();
         if (this.doesFileContainString(filePath, PlatformUtils.getJavaHomePath())) {
            files.add(filePath);
         }
      }

      this.backupFiles(backupLoc, mwHome, files);
   }

   private void backupFiles(Path backupLocation, Path sourceLocation, List files) throws IOException {
      if (!backupLocation.toFile().exists()) {
         Files.createDirectories(backupLocation);
         if (this.outputHandler.isDebugEnabled()) {
            this.outputHandler.debug("created " + backupLocation.toString() + " dir");
         }
      }

      Iterator var4 = files.iterator();

      while(var4.hasNext()) {
         Path filePath = (Path)var4.next();
         Path backupPath = backupLocation.resolve(sourceLocation.relativize(filePath));
         Files.createDirectories(backupPath.getParent());
         Files.copy(filePath, backupPath, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
         if (this.outputHandler.isDebugEnabled()) {
            this.outputHandler.debug("copied " + filePath + " to " + backupPath);
         }
      }

   }

   private void restoreFiles(Path backupLocation, Path sourceLocation) throws IOException {
      List filePaths = (List)Files.walk(backupLocation).filter((x$0) -> {
         return Files.isRegularFile(x$0, new LinkOption[0]);
      }).collect(Collectors.toList());
      Iterator var4 = filePaths.iterator();

      while(var4.hasNext()) {
         Path filePath = (Path)var4.next();
         Path restorePath = sourceLocation.resolve(backupLocation.relativize(filePath));
         Files.move(filePath, restorePath, StandardCopyOption.REPLACE_EXISTING);
         if (this.outputHandler.isDebugEnabled()) {
            this.outputHandler.debug("Successfully restored " + restorePath);
         }
      }

   }

   public void restoreJavaHomeFiles(String backupLocation) throws IOException {
      if (this.outputHandler.isDebugEnabled()) {
         this.outputHandler.debug("restoreJavaHomeFile from " + backupLocation);
      }

      Path mwHome = Paths.get(System.getenv("MW_HOME"));
      Path backupLoc = Paths.get(backupLocation);
      this.restoreFiles(backupLoc, mwHome);
      this.forceDelete(backupLoc);
   }

   public void forceDelete(Path dirToDelete) throws IOException {
      Files.walkFileTree(dirToDelete, new SimpleFileVisitor() {
         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (!file.toFile().delete()) {
               JavaHomeUpdateUtils.this.outputHandler.error("Unable to delete file " + file.toString());
            }

            return FileVisitResult.CONTINUE;
         }

         public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            if (!dir.toFile().delete()) {
               JavaHomeUpdateUtils.this.outputHandler.error("Unable to delete dir " + dir.toString());
            }

            return FileVisitResult.CONTINUE;
         }
      });
   }

   public void restoreFilesFromPatchBackup(String domainDir, String backupLocation) throws IOException {
      if (this.outputHandler.isDebugEnabled()) {
         this.outputHandler.debug("restoreJavaHomeDomainFiles from " + backupLocation + " to " + domainDir);
      }

      Path domainDirPath = Paths.get(domainDir);
      Path backupLoc = Paths.get(backupLocation);
      this.restoreFiles(backupLoc, domainDirPath);
   }

   public void backupJavaHomeDomainFiles(String domainDir, String backupLocation) throws IOException {
      if (this.outputHandler.isDebugEnabled()) {
         this.outputHandler.debug("backupJavaHomeDomainFiles from " + domainDir + " to " + backupLocation);
      }

      Path domainDirPath = Paths.get(domainDir);
      Path backupLoc = Paths.get(backupLocation);
      List domainFiles = this.getJavaHomeDomainFiles(domainDir);
      this.backupFiles(backupLoc, domainDirPath, domainFiles);
   }

   private boolean doesFileContainString(Path filePath, String find) throws IOException {
      byte[] bytes = Files.readAllBytes(filePath);
      String content = new String(bytes);
      return content.contains(find) || content.contains(find.replace("\\", "\\\\"));
   }

   private List getJavaHomeDomainFiles(File location) throws IOException {
      ArrayList javaHomeDomainFiles = new ArrayList();
      javaHomeDomainFiles.add((new File(new File(location, "bin"), "setDomainEnv" + PlatformUtils.getScriptExtension())).toPath());
      javaHomeDomainFiles.add((new File(new File(location, "nodemanager"), "nodemanager.properties")).toPath());
      javaHomeDomainFiles.add((new File(new File(location, "init-info"), "tokenValue.properties")).toPath());
      javaHomeDomainFiles.add((new File(new File(location, "init-info"), "domain-info.xml")).toPath());
      javaHomeDomainFiles.add((new File(new File(location, "init-info"), "nodemanager-properties.xml")).toPath());
      javaHomeDomainFiles.add((new File(new File(location, "init-info"), "startscript.xml")).toPath());
      javaHomeDomainFiles.add((new File(new File(location, "bin"), "setNMJavaHome" + PlatformUtils.getScriptExtension())).toPath());
      return javaHomeDomainFiles;
   }

   private void replaceJavaHomeInFile(File file, String newJavaHomeToUse) throws IOException {
      if (file.exists()) {
         Path filePath = file.toPath();
         byte[] bytes = Files.readAllBytes(filePath);
         String content = new String(bytes);
         boolean foundJavaHome = false;
         Iterator var7 = this.javaHomeFormats.entrySet().iterator();

         while(var7.hasNext()) {
            Map.Entry javaHomeFormatEntry = (Map.Entry)var7.next();
            if (content.contains((CharSequence)javaHomeFormatEntry.getValue())) {
               foundJavaHome = true;
               String javaHomeFormat = (String)javaHomeFormatEntry.getValue();
               String key = (String)javaHomeFormatEntry.getKey();
               if (key.equalsIgnoreCase("UPPERCASE_DRIVE_LETTER_ESC_FULL_JAVA_HOME") || key.equalsIgnoreCase("LOWERCASE_DRIVE_LETTER_ESC_FULL_JAVA_HOME") || key.equalsIgnoreCase("UPPERCASE_DRIVE_LETTER_ESC_SHORT_JAVA_HOME") || key.equalsIgnoreCase("LOWERCASE_DRIVE_LETTER_ESC_SHORT_JAVA_HOME")) {
                  if (this.outputHandler.isDebugEnabled()) {
                     this.outputHandler.debug("We have found " + javaHomeFormat + " in " + filePath);
                  }

                  newJavaHomeToUse = this.escapePathForWindows(newJavaHomeToUse);
                  if (this.outputHandler.isDebugEnabled()) {
                     this.outputHandler.debug("altered the USE_NEW_JAVA_HOME to be " + newJavaHomeToUse);
                  }
               }

               content = content.replace(javaHomeFormat, newJavaHomeToUse);
               break;
            }
         }

         if (!foundJavaHome) {
            if (file.toString().contains("setDomainEnv")) {
               throw new IOException("Unable to find JAVA_HOME, " + PlatformUtils.getJavaHomePath() + ", in any form in " + file + " and will not be able to rollout a Java update.  This is likely due to the ZdtAgent instance (started by NodeManager or AdminServer) running with a different JAVA_HOME than the domain");
            }

            this.outputHandler.warning("Could not find JAVA_HOME, " + PlatformUtils.getJavaHomePath() + " in any form in " + file);
         }

         Files.write(filePath, content.getBytes(), new OpenOption[0]);
      }
   }

   public void updateJavaHomeFiles(String domainDir, String newJavaHome, String patchBackupDomainDir) throws IOException {
      File newJavaHomeDir = null;
      if (newJavaHome != null && !newJavaHome.isEmpty()) {
         newJavaHomeDir = new File(newJavaHome);
      }

      if (newJavaHomeDir != null && newJavaHomeDir.isDirectory() && !(new File(PlatformUtils.getJavaHomePath())).equals(new File(newJavaHome))) {
         Iterator var5 = this.getJavaHomeDomainFiles(domainDir).iterator();

         while(var5.hasNext()) {
            Path filePath = (Path)var5.next();
            this.replaceJavaHomeInFile(filePath.toFile(), newJavaHome);
         }

         File perMachineNMProps = new File(new File(new File(new File(PlatformUtils.getMWHomePath(), "oracle_common"), "common"), "nodemanager"), "nodemanager.properties");
         this.replaceJavaHomeInFile(perMachineNMProps, newJavaHome);
      } else if (Paths.get(patchBackupDomainDir, "bin", "setDomainEnv" + PlatformUtils.getScriptExtension()).toFile().exists()) {
         this.restoreFilesFromPatchBackup(domainDir, patchBackupDomainDir);
      }

   }

   public static void main(String[] args) throws Exception {
      ZdtAgentOutputHandler test = new ZdtAgentOutputHandler() {
         public void error(String message) {
            System.out.println("ERROR: " + message);
         }

         public void error(String message, Exception cause) {
            System.out.println("ERROR: " + message);
            cause.printStackTrace();
         }

         public void info(String message) {
            System.out.println("INFO: " + message);
         }

         public void warning(String message) {
            System.out.println("WARNING: " + message);
         }

         public boolean isDebugEnabled() {
            return true;
         }

         public void debug(String message) {
            System.out.println("DEBUG: " + message);
         }
      };
      String domainDir = System.getenv("DOM_DIR");
      if (domainDir == null) {
         throw new Exception("Need DOM_DIR");
      } else {
         File patchingBackupDir = new File(PlatformUtils.getMWHomePath(), "patching_backup");
         String newJavaHome = System.getenv("NEW_JAVA_HOME");
         if (newJavaHome == null) {
            throw new Exception("Need NEW_JAVA_HOME");
         } else {
            File patchingBackupDom = new File(patchingBackupDir, "domain1");
            JavaHomeUpdateUtils utils = new JavaHomeUpdateUtils(test);
            utils.backupJavaHomeFiles((new File(patchingBackupDir, "ohome1")).getCanonicalPath());
            utils.backupJavaHomeDomainFiles(domainDir, patchingBackupDom.getCanonicalPath());
            utils.updateJavaHomeFiles(domainDir, newJavaHome, patchingBackupDom.getCanonicalPath());
            utils.backupJavaHomeDomainFiles(domainDir, (new File(patchingBackupDir, "domain2")).getCanonicalPath());
            utils.restoreFilesFromPatchBackup(domainDir, patchingBackupDom.getCanonicalPath());
            utils.restoreJavaHomeFiles((new File(patchingBackupDir, "ohome1")).getCanonicalPath());
         }
      }
   }
}
