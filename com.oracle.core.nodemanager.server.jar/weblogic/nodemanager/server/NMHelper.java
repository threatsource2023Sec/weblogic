package weblogic.nodemanager.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import weblogic.nodemanager.NMException;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.util.Platform;
import weblogic.nodemanager.util.ScriptExecResult;

public final class NMHelper {
   public static final String WINDOWS_IFCONFIG_SCRIPT = "wlsifconfig.cmd";
   public static final String UNIX_IFCONFIG_SCRIPT = "wlsifconfig.sh";
   public static final String IFCONFIG_ADD = "-addif";
   public static final String IFCONFIG_REMOVE = "-removeif";
   public static final String SERVER_NAME_PROP = "ServerName";
   public static final String SERVER_DIR_PROP = "ServerDir";
   public static final String MAC_BROADCAST_PROP = "UseMACBroadcast";
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   private static final Object IP_BINDING_LOCK = new Object();
   private static final long IFCONFIG_TIMEOUT = 0L;

   public static ScriptExecResult callScript(String[] cmd, Map suppliedEnv, File workingDir, long timeout) throws IOException {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      OutputStreamWriter writer = new OutputStreamWriter(output) {
         public void write(String str) throws IOException {
            super.write(str);
            super.write("\n");
         }
      };
      ProcessRunner proc = new ProcessRunner(cmd, suppliedEnv, workingDir, writer);
      proc.start();
      long startTime = System.currentTimeMillis();
      boolean timeoutOccured = false;

      while(proc.isAlive() && waitingForTimeout(startTime, timeout)) {
         try {
            proc.join(timeout);
         } catch (InterruptedException var13) {
            timeout -= System.currentTimeMillis() - startTime;
         }

         if (!waitingForTimeout(startTime, timeout) && proc.isAlive()) {
            timeoutOccured = true;
            break;
         }
      }

      writer.flush();
      writer.close();
      byte[] data = output.toByteArray();
      proc.forceCleanup();
      int rc = timeoutOccured ? -101 : proc.getResponseCode();
      return new ScriptExecResult(rc, new ByteArrayInputStream(data));
   }

   public static int executeScript(String[] cmd, Properties env, File workingDir, long timeout) throws IOException {
      ProcessRunner proc = new ProcessRunner(cmd, env, workingDir);
      proc.start();
      long startTime = System.currentTimeMillis();

      while(proc.isAlive() && waitingForTimeout(startTime, timeout)) {
         try {
            proc.join(timeout);
         } catch (InterruptedException var13) {
         }

         if (!waitingForTimeout(startTime, timeout) && proc.isAlive()) {
            return -101;
         }
      }

      int var8;
      try {
         var8 = proc.getResponseCode();
      } finally {
         proc.cleanup();
      }

      return var8;
   }

   private static boolean waitingForTimeout(long startTime, long timeout) {
      if (timeout <= 0L) {
         return true;
      } else {
         return System.currentTimeMillis() < startTime + timeout;
      }
   }

   public static String getIFControlScriptName() {
      return Platform.isWindows() ? "wlsifconfig.cmd" : "wlsifconfig.sh";
   }

   public static String[] buildAddMigrationCommand(String serverIP, String interfaceName, String netMask, String scriptDir) {
      ArrayList tmpCommand = new ArrayList();
      File script = new File(scriptDir, getIFControlScriptName());
      tmpCommand.add(script.getPath());
      tmpCommand.add(getIFControlAddParam());
      tmpCommand.add(wrapSpacesForCmdLine(interfaceName));
      tmpCommand.add(serverIP);
      if (netMask != null) {
         tmpCommand.add(netMask);
      }

      return (String[])((String[])tmpCommand.toArray(new String[tmpCommand.size()]));
   }

   public static String[] buildRemoveMigrationCommand(String serverIP, String interfaceName, String netMask, String scriptDir) {
      ArrayList tmpCommand = new ArrayList();
      File script = new File(scriptDir, getIFControlScriptName());
      tmpCommand.add(script.getPath());
      tmpCommand.add(getIFControlRemoveParam());
      tmpCommand.add(wrapSpacesForCmdLine(interfaceName));
      tmpCommand.add(serverIP);
      if (netMask != null) {
         tmpCommand.add(netMask);
      }

      return (String[])((String[])tmpCommand.toArray(new String[tmpCommand.size()]));
   }

   public static Properties buildMigrationEnv(String serverName, String serverDir) {
      Properties env = new Properties();
      Iterator envEntries = System.getenv().entrySet().iterator();

      while(envEntries.hasNext()) {
         Map.Entry me = (Map.Entry)envEntries.next();
         env.put(me.getKey(), me.getValue());
      }

      env.put("ServerName", serverName);
      env.put("ServerDir", serverDir);
      return env;
   }

   public static Properties buildMigrationEnv(String serverName, String serverDir, boolean useMACBroadcast) {
      Properties env = buildMigrationEnv(serverName, serverDir);
      env.put("UseMACBroadcast", useMACBroadcast);
      return env;
   }

   private static String getIFControlAddParam() {
      return "-addif";
   }

   private static String getIFControlRemoveParam() {
      return "-removeif";
   }

   private static String wrapSpacesForCmdLine(String param) {
      if (param.indexOf(" ") > -1) {
         if (param.startsWith("\"") && param.endsWith("\"")) {
            return param;
         }

         param = "\"" + param + "\"";
      }

      return param;
   }

   private static boolean forceProcessCleanup() {
      return false;
   }

   public static String getIfConfigScriptDir(DomainManager domainMgr, NMServer nmServer) {
      String dir = nmServer.getConfig().getIfConfigDir();
      return dir != null ? dir : domainMgr.getDomainDir().getIfConfigDir();
   }

   public static NMProcess.ExecuteCallbackHook createBindIPHook(String serverName, String serverIP, String interfaceName, String netMask, String serverDir, String scriptDir, boolean useMACBroadcast) throws NMException {
      final File sDir = new File(scriptDir);
      if (!(new File(sDir, getIFControlScriptName())).exists()) {
         throw new NMException("wlsifconfig script does not exist at " + sDir.getPath());
      } else {
         final String[] cmd = buildAddMigrationCommand(serverIP, interfaceName, netMask, scriptDir);
         final Properties env = buildMigrationEnv(serverName, serverDir, useMACBroadcast);
         NMProcess.ExecuteCallbackHook bindIPHook = new NMProcess.ExecuteCallbackHook() {
            public void execute() throws IOException {
               int exitCode = true;
               int exitCodex;
               synchronized(NMHelper.IP_BINDING_LOCK) {
                  exitCodex = NMHelper.executeScript(cmd, env, sDir, 0L);
               }

               if (exitCodex != 0) {
                  StringBuffer sb = new StringBuffer();

                  for(int i = 0; i < cmd.length; ++i) {
                     sb.append(cmd[i]);
                     sb.append(" ");
                  }

                  throw new IOException("Command '" + sb + "' returned an unsuccessful exit code '" + exitCodex + "'. Check NM logs for script output.");
               }
            }
         };
         return bindIPHook;
      }
   }

   public static NMProcess.ExecuteCallbackHook createUnbindIPHook(String serverName, String serverIP, String interfaceName, String netMask, String serverDir, String scriptDir) throws NMException {
      final File sDir = new File(scriptDir);
      if (!(new File(sDir, getIFControlScriptName())).exists()) {
         throw new NMException("wlsifconfig script does not exist at " + sDir.getPath());
      } else {
         final String[] cmd = buildRemoveMigrationCommand(serverIP, interfaceName, netMask, scriptDir);
         final Properties env = buildMigrationEnv(serverName, serverDir);
         NMProcess.ExecuteCallbackHook removeIPHook = new NMProcess.ExecuteCallbackHook() {
            public void execute() throws IOException {
               int exitCode = true;
               int exitCodex;
               synchronized(NMHelper.IP_BINDING_LOCK) {
                  exitCodex = NMHelper.executeScript(cmd, env, sDir, 0L);
               }

               if (exitCodex != 0) {
                  StringBuffer sb = new StringBuffer();

                  for(int i = 0; i < cmd.length; ++i) {
                     sb.append(cmd[i]);
                     sb.append(" ");
                  }

                  throw new IOException("Command '" + sb + "' returned an unsuccessful exit code '" + exitCodex + "'. Check NM logs for script output.");
               }
            }
         };
         return removeIPHook;
      }
   }

   public static void scrubEnvironmentValues(Map env) {
      String wlHome = (String)env.remove("WL_HOME");
      String mwHome = (String)env.remove("MW_HOME");
      if (mwHome != null && wlHome != null) {
         stripAddedPath(env, "LD_LIBRARY_PATH", mwHome, wlHome);
         stripAddedPath(env, "PATH", mwHome, wlHome);
         stripAddedPath(env, "CLASSPATH", mwHome, wlHome);
      }

      env.remove("JAVA_HOME");
      env.remove("JAVA_VENDOR");
      env.remove("BEA_HOME");
      env.remove("COHERENCE_HOME");
      env.remove("FEATURES_DIR");
      env.remove("COMMON_JVM_ARGS");
      env.remove("FMWLAUNCH_CLASSPATH");
      env.remove("DERBY_HOME");
      env.remove("DERBY_CLASSPATH");
      env.remove("WEBLOGIC_CLASSPATH");
      env.remove("FMWCONFIG_CLASSPATH");
      env.remove("DERBY_OPTS");
      env.remove("DERBY_TOOLS");
      env.remove("MODULES_DIR");
      env.remove("ANT_HOME");
      env.remove("ANT_CONTRIB");
      env.remove("PRODUCTION_MODE");
      env.remove("JAVA_USE_64BIT");
      env.remove("VM_TYPE");
      env.remove("PROFILE_CLASSPATH");
   }

   private static void stripAddedPath(Map env, String varName, String mwHome, String wlHome) {
      String envValue = (String)env.remove(varName);
      if (envValue != null) {
         StringBuffer nuValue = new StringBuffer();
         StringTokenizer st = new StringTokenizer(envValue, File.pathSeparator);

         while(st.hasMoreTokens()) {
            String pathVal = st.nextToken();
            if (!pathVal.startsWith(mwHome) && !pathVal.startsWith(wlHome)) {
               nuValue.append(pathVal);
               if (st.hasMoreTokens()) {
                  nuValue.append(File.pathSeparator);
               }
            }
         }

         if (nuValue.length() > 0) {
            env.put(varName, nuValue.toString());
         }

      }
   }

   private static class NMLogWriter extends Writer {
      private Level level;

      NMLogWriter(Level level) {
         this.level = level;
      }

      public void write(int c) throws IOException {
         throw new UnsupportedOperationException();
      }

      public void write(char[] cbuf) throws IOException {
         throw new UnsupportedOperationException();
      }

      public void write(char[] cbuf, int off, int len) throws IOException {
         throw new UnsupportedOperationException();
      }

      public void write(String str) throws IOException {
         NMServer.nmLog.log(this.level, str);
      }

      public void write(String str, int off, int len) throws IOException {
         throw new UnsupportedOperationException();
      }

      public Writer append(CharSequence csq) throws IOException {
         throw new UnsupportedOperationException();
      }

      public Writer append(CharSequence csq, int start, int end) throws IOException {
         throw new UnsupportedOperationException();
      }

      public Writer append(char c) throws IOException {
         throw new UnsupportedOperationException();
      }

      public void flush() throws IOException {
      }

      public void close() throws IOException {
      }
   }

   private static class Drainer extends Thread {
      private BufferedReader in;
      Writer writer;

      Drainer(InputStream is, Writer writer) {
         this.in = new BufferedReader(new InputStreamReader(is));
         this.writer = writer;
      }

      public void run() {
         while(true) {
            try {
               String line;
               if ((line = this.in.readLine()) != null) {
                  this.writer.write(line);
                  continue;
               }
            } catch (IOException var3) {
               NMServer.nmLog.log(Level.FINEST, "problem logging script output due to exception", var3);
            }

            return;
         }
      }

      public void cleanup() {
         try {
            this.writer.flush();
         } catch (IOException var3) {
         }

         try {
            this.in.close();
         } catch (IOException var2) {
            NMServer.nmLog.log(Level.FINEST, "problem closing script output stream due to exception", var2);
         }

      }
   }

   private static class ProcessRunner extends Thread {
      private Process proc;
      private IOException caughtException;
      private String[] cmd;
      private File workingDir;
      private Collection drainers;
      private Writer writer;
      private Map env;

      public ProcessRunner(String[] cmd, Properties envProps, File workingDir) {
         this.cmd = cmd;
         this.workingDir = workingDir;
         this.env = new HashMap();
         Iterator var4 = envProps.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            this.env.put(entry.getKey().toString(), entry.getValue().toString());
         }

      }

      public ProcessRunner(String[] cmd, Map env, File workingDir, Writer writer) {
         this.cmd = cmd;
         this.workingDir = workingDir;
         this.env = env;
         this.writer = writer;
      }

      public void run() {
         try {
            ProcessBuilder pb = new ProcessBuilder(this.cmd);
            pb.directory(this.workingDir);
            Map currEnv = pb.environment();
            if (currEnv != null && this.env != null && !this.env.isEmpty()) {
               currEnv.putAll(this.env);
            }

            if (this.writer != null) {
               pb.redirectErrorStream(true);
            }

            this.proc = pb.start();
            this.proc.getOutputStream().close();
            this.drainers = new ArrayList();
            Drainer drainer;
            Drainer drainer;
            if (this.writer != null) {
               drainer = new Drainer(this.proc.getInputStream(), this.writer);
               this.drainers.add(drainer);
               drainer.start();
            } else {
               drainer = new Drainer(this.proc.getInputStream(), new NMLogWriter(Level.INFO));
               this.drainers.add(drainer);
               drainer = new Drainer(this.proc.getErrorStream(), new NMLogWriter(Level.WARNING));
               this.drainers.add(drainer);
               drainer.start();
               drainer.start();
            }

            try {
               this.proc.waitFor();
               Iterator var7 = this.drainers.iterator();

               while(var7.hasNext()) {
                  drainer = (Drainer)var7.next();
                  drainer.join();
               }
            } catch (InterruptedException var5) {
               var5.printStackTrace();
            }
         } catch (IOException var6) {
            this.caughtException = var6;
         }

      }

      public int getResponseCode() throws IOException {
         if (this.caughtException != null) {
            throw this.caughtException;
         } else if (this.proc == null) {
            throw new AssertionError("Process never got set!");
         } else {
            return this.proc.exitValue();
         }
      }

      public void cleanup() {
         if (NMHelper.forceProcessCleanup()) {
            this.proc.destroy();
         }

         if (this.drainers != null) {
            Iterator var1 = this.drainers.iterator();

            while(var1.hasNext()) {
               Drainer drainer = (Drainer)var1.next();
               drainer.cleanup();
            }
         }

      }

      public void forceCleanup() {
         if (this.proc != null) {
            Thread cleanup = new Thread(this.proc.toString() + " cleanup thread") {
               public void run() {
                  if (ProcessRunner.this.proc != null) {
                     ProcessRunner.this.proc.destroy();
                  }

                  if (ProcessRunner.this.drainers != null) {
                     Iterator var1 = ProcessRunner.this.drainers.iterator();

                     while(var1.hasNext()) {
                        Drainer drainer = (Drainer)var1.next();
                        drainer.cleanup();
                     }
                  }

               }
            };
            cleanup.start();
         }

      }
   }
}
