package weblogic.management.patching.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ZdtAgentScriptHelper {
   public static final String DETACH_HOME_SCRIPT_NAME = "detachHome";
   public static final String ATTACH_HOME_SCRIPT_NAME = "attachHome";
   public static final String PASTE_BINARY_SCRIPT_NAME = "pasteBinary";
   public static final String MW_HOME_ENV_NAME = "MW_HOME";
   public static final String OUI_DIR_NAME = "oui";
   public static final String BIN_DIR_NAME = "bin";
   public static final String ORACLE_COMMON_DIR_NAME = "oracle_common";
   public static final long INVENTORY_SCRIPT_TIMEOUT;
   public static final long PASTE_SCRIPT_TIMEOUT;
   public static final long EXT_SCRIPT_TIMEOUT;
   public static final int SCRIPT_TIMEOUT_EXIT_CODE = -101;
   public static final String JAR_PARAM = "-jar";
   public static final String NO_WAIT_PARAM = "-nowait";
   public static final String ALREADY_CLONED_PARAM = "-ohAlreadyCloned";
   public static final String EXECUTE_PREREQS_PARAM = "-executeSysPrereqs";
   public static final String TARGET_ORACLE_HOME_PARAM = "-targetOracleHomeLoc";
   public static final String JAVA_HOME_PARAM = "-javaHome";
   public static final String INV_POINTER_PARAM = "-invPtrLoc";
   public static final String ARCHIVE_LOC_PARAM = "-archiveLoc";
   public static final String NO_CONSOLE_PARAM = "-noconsole";
   public static final String ORA_INST_LOC = "oraInst.loc";
   private ZdtAgentOutputHandler outputHandler;

   public ZdtAgentScriptHelper(ZdtAgentOutputHandler outputHandler) {
      this.outputHandler = outputHandler;
   }

   public int callDetachHome(String outputPath) throws IOException {
      FileOutputStream fos = new FileOutputStream(outputPath);
      return this.callDetachHome((OutputStream)fos);
   }

   public int callPasteBinary(String outputPath, String knownMWHomeLocation, String javaHomeToUse, String targetOracleHome, String patchedLoc) throws IOException {
      FileOutputStream fos = new FileOutputStream(outputPath);
      return this.callPasteBinary((OutputStream)fos, knownMWHomeLocation, javaHomeToUse, targetOracleHome, patchedLoc);
   }

   public int callPasteBinary(String outputPath, String javaHomeToUse) throws IOException {
      FileOutputStream fos = new FileOutputStream(outputPath);
      return this.callPasteBinary((OutputStream)fos, javaHomeToUse);
   }

   public int callAttachHome(String outputPath) throws IOException {
      FileOutputStream fos = new FileOutputStream(outputPath);
      return this.callAttachHome((OutputStream)fos);
   }

   public int callPasteBinary(OutputStream out, String javaHomeToUse) throws IOException {
      String mwHome = PlatformUtils.getMWHomePath();
      return this.callPasteBinary((OutputStream)out, mwHome, javaHomeToUse, mwHome, (String)null);
   }

   public int callDetachHome(OutputStream out) throws IOException {
      File detachHomeScript = new File(this.getOuiBinDir(), this.getDetachHomeScriptName());
      List commandList = new ArrayList();
      commandList.add(detachHomeScript.getCanonicalPath());
      if (PlatformUtils.isWindows) {
         commandList.add("-noconsole");
      }

      this.outputHandler.debug("ZdtAgentScriptHandler calling:\n" + commandList.toString());
      String[] cmd = new String[commandList.size()];
      cmd = (String[])commandList.toArray(cmd);
      return this.callScript(cmd, detachHomeScript.getParentFile(), out, INVENTORY_SCRIPT_TIMEOUT);
   }

   public int callAttachHome(OutputStream out) throws IOException {
      File attachHomeScript = new File(this.getOuiBinDir(), this.getAttachHomeScriptName());
      List commandList = new ArrayList();
      commandList.add(attachHomeScript.getCanonicalPath());
      if (PlatformUtils.isUnix) {
         commandList.add("-invPtrLoc");
         commandList.add((new File(System.getenv("MW_HOME"), "oraInst.loc")).getCanonicalPath());
      } else {
         commandList.add("-noconsole");
      }

      this.outputHandler.debug("ZdtAgentScriptHandler calling:\n" + commandList.toString());
      String[] cmd = new String[commandList.size()];
      cmd = (String[])commandList.toArray(cmd);
      return this.callScript(cmd, attachHomeScript.getParentFile(), out, INVENTORY_SCRIPT_TIMEOUT);
   }

   public int callPasteBinary(OutputStream out, String knownMWHomeLocation, String javaHomeToUse, String targetOracleHome, String patchedLoc) throws IOException {
      File pasteBinaryScript = new File(this.getOracleCommonBinDir(knownMWHomeLocation), this.getPasteBinaryScriptName());
      List commandList = new ArrayList();
      commandList.add(pasteBinaryScript.getCanonicalPath());
      commandList.add("-javaHome");
      commandList.add((new File(javaHomeToUse)).getCanonicalPath());
      commandList.add("-targetOracleHomeLoc");
      commandList.add((new File(targetOracleHome)).getCanonicalPath());
      commandList.add("-executeSysPrereqs");
      commandList.add("false");
      if (patchedLoc != null && !patchedLoc.isEmpty()) {
         commandList.add("-archiveLoc");
         commandList.add((new File(patchedLoc)).getCanonicalPath());
      } else {
         commandList.add("-ohAlreadyCloned");
         commandList.add("true");
      }

      if (PlatformUtils.isUnix) {
         commandList.add("-invPtrLoc");
         commandList.add((new File(knownMWHomeLocation, "oraInst.loc")).getCanonicalPath());
      }

      this.outputHandler.debug("ZdtAgentScriptHandler calling:\n" + commandList.toString());
      String[] cmd = new String[commandList.size()];
      cmd = (String[])commandList.toArray(cmd);
      return this.callScript(cmd, pasteBinaryScript.getParentFile(), out, PASTE_SCRIPT_TIMEOUT);
   }

   public int callPatchedArchive(OutputStream out, String knownMWHomeLocation, String javaHomeToUse, String targetOracleHome, String patchedLoc) throws IOException {
      File javaBinary = new File(PlatformUtils.getJavaBinaryPath());
      List commandList = new ArrayList();
      commandList.add(javaBinary.getCanonicalPath());
      commandList.add("-jar");
      commandList.add((new File(patchedLoc)).getCanonicalPath());
      commandList.add("-javaHome");
      commandList.add((new File(javaHomeToUse)).getCanonicalPath());
      commandList.add("-targetOracleHomeLoc");
      commandList.add((new File(targetOracleHome)).getCanonicalPath());
      if (PlatformUtils.isUnix) {
         commandList.add("-invPtrLoc");
         commandList.add((new File(knownMWHomeLocation, "oraInst.loc")).getCanonicalPath());
      }

      commandList.add("-nowait");
      this.outputHandler.debug("ZdtAgentScriptHandler calling:\n" + commandList.toString());
      String[] cmd = new String[commandList.size()];
      cmd = (String[])commandList.toArray(cmd);
      return this.callScript(cmd, this.getOracleCommonBinDir(knownMWHomeLocation), out, PASTE_SCRIPT_TIMEOUT);
   }

   public int callExtensionScript(OutputStream out, String scriptDir, String scriptName, Map extScriptsEnv) throws IOException {
      File extScript = new File(scriptDir, scriptName);
      List commandList = new ArrayList();
      commandList.add(extScript.getCanonicalPath());
      this.outputHandler.debug("ZdtAgentScriptHandler calling:\n" + commandList.toString());
      String[] cmd = new String[commandList.size()];
      cmd = (String[])commandList.toArray(cmd);
      return this.callScript(cmd, extScript.getParentFile(), out, EXT_SCRIPT_TIMEOUT, extScriptsEnv);
   }

   public int callExtensionScript(String outFile, String scriptDir, String scriptName, Map extScriptsEnv) throws IOException {
      File extScript = new File(scriptDir, scriptName);
      List commandList = new ArrayList();
      commandList.add(extScript.getCanonicalPath());
      this.outputHandler.debug("ZdtAgentScriptHandler calling:\n" + commandList.toString());
      String[] cmd = new String[commandList.size()];
      cmd = (String[])commandList.toArray(cmd);
      return this.callScript(cmd, extScript.getParentFile(), outFile, EXT_SCRIPT_TIMEOUT, extScriptsEnv);
   }

   private String getDetachHomeScriptName() {
      return "detachHome" + PlatformUtils.getScriptExtension();
   }

   private String getAttachHomeScriptName() {
      return "attachHome" + PlatformUtils.getScriptExtension();
   }

   private String getPasteBinaryScriptName() {
      return "pasteBinary" + PlatformUtils.getScriptExtension();
   }

   public File getOuiBinDir() {
      return new File(new File(System.getenv("MW_HOME"), "oui"), "bin");
   }

   public File getOracleCommonBinDir(String knownMWHomeLocation) {
      return new File(new File(knownMWHomeLocation, "oracle_common"), "bin");
   }

   public int callScript(String[] cmd, File workingDir, OutputStream out, long timeout) throws IOException {
      OutputStreamWriter writer = new OutputStreamWriter(out);
      ProcessBuilder pb = new ProcessBuilder(cmd);
      pb.directory(workingDir);
      return this.runProc(pb, writer, timeout);
   }

   public int callScript(String[] cmd, File workingDir, OutputStream out, long timeout, Map extScriptsEnv) throws IOException {
      OutputStreamWriter writer = new OutputStreamWriter(out);
      ProcessBuilder pb = new ProcessBuilder(cmd);
      pb.directory(workingDir);
      Map currEnv = pb.environment();
      if (extScriptsEnv != null) {
         currEnv.putAll(extScriptsEnv);
      }

      return this.runProc(pb, writer, timeout);
   }

   public int callScript(String[] cmd, File workingDir, String outFilePath, long timeout, Map extScriptsEnv) throws IOException {
      File outFile = new File(outFilePath);
      ProcessBuilder pb = new ProcessBuilder(cmd);
      pb.directory(workingDir);
      Map currEnv = pb.environment();
      if (extScriptsEnv != null) {
         currEnv.putAll(extScriptsEnv);
      }

      return this.runProc(pb, outFile, timeout);
   }

   public int runProc(ProcessBuilder pb, OutputStreamWriter writer, long timeout) throws IOException {
      pb.redirectErrorStream(true);
      Process proc = pb.start();
      proc.getOutputStream().close();
      Drainer outDrainer = new Drainer(proc.getInputStream(), writer, this.outputHandler);
      outDrainer.start();
      long endTime = System.currentTimeMillis() + timeout;
      boolean timedOut = false;

      while(proc.isAlive()) {
         try {
            if (!proc.waitFor(timeout, TimeUnit.MILLISECONDS)) {
               timedOut = true;
            }

            outDrainer.join();
         } catch (InterruptedException var11) {
            timeout = endTime - System.currentTimeMillis();
         }
      }

      writer.flush();
      writer.close();
      proc.destroyForcibly();
      int rc = timedOut ? -101 : proc.exitValue();
      return rc;
   }

   public int runProc(ProcessBuilder pb, File outFile, long timeout) throws IOException {
      pb.redirectErrorStream(true);
      pb.redirectOutput(outFile);
      Process proc = pb.start();
      proc.getOutputStream().close();
      long endTime = System.currentTimeMillis() + timeout;
      boolean timedOut = false;

      while(proc.isAlive()) {
         try {
            if (!proc.waitFor(timeout, TimeUnit.MILLISECONDS)) {
               timedOut = true;
            }
         } catch (InterruptedException var10) {
            timeout = endTime - System.currentTimeMillis();
         }
      }

      proc.destroyForcibly();
      int rc = timedOut ? -101 : proc.exitValue();
      return rc;
   }

   static {
      INVENTORY_SCRIPT_TIMEOUT = TimeUnit.MILLISECONDS.convert(10L, TimeUnit.MINUTES);
      PASTE_SCRIPT_TIMEOUT = TimeUnit.MILLISECONDS.convert(1L, TimeUnit.HOURS);
      EXT_SCRIPT_TIMEOUT = TimeUnit.MILLISECONDS.convert(20L, TimeUnit.MINUTES);
   }
}
