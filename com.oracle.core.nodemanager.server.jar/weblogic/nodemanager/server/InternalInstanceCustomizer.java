package weblogic.nodemanager.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.StartupConfig;

abstract class InternalInstanceCustomizer implements Customizer.InstanceCustomizer {
   protected static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   private static final String EOL = System.getProperty("line.separator");
   private final ServerManagerI serverMgr;
   private final StartupConfig conf;
   private final WLSProcessBuilder processBuilder;
   private final LogFormatter formatter = new LogFormatter();

   public InternalInstanceCustomizer(ServerManagerI serverMgr, StartupConfig conf, WLSProcessBuilder processBuilder) {
      assert serverMgr != null : "ServerManager null";

      assert conf != null : "StartupConfig null";

      assert processBuilder != null : "WLSProcessBuilder null";

      this.serverMgr = serverMgr;
      this.conf = conf;
      this.processBuilder = processBuilder;
   }

   abstract String getStartString(StringBuilder var1);

   public NMProcess createProcess() throws IOException {
      this.logStartInfo();
      return this.processBuilder.createProcess();
   }

   public NMProcess createProcess(String pid) throws IOException {
      return this.processBuilder.createProcess(pid);
   }

   public void log(Level level, String msg, Throwable thrown) {
      LogRecord lr = new LogRecord(level, msg);
      lr.setParameters(new String[]{"NodeManager"});
      if (thrown != null) {
         lr.setThrown(thrown);
      }

      String line;
      synchronized(this.formatter) {
         line = this.formatter.format(lr);
      }

      if (line.endsWith(EOL)) {
         line = line.substring(0, line.length() - EOL.length());
      }

      File outFile = this.serverMgr.getServerDir().getOutFile();

      try {
         FileWriter out = new FileWriter(outFile, true);
         out.write(line);
         out.write(EOL);
         out.close();
      } catch (IOException var8) {
         this.serverMgr.log(Level.WARNING, nmText.msgErrorFileWrite(outFile.toString()), var8);
      }

   }

   private void logStartInfo() throws IOException {
      LogFileRotationUtil.changeDirOwnerships(this.serverMgr, this.conf);
      if (this.conf.getLogFileRotationConfig().isRotateLogOnStartupEnabled()) {
         LogFileRotationUtil.rotateServerFiles(this.serverMgr, this.conf);
      }

      WLSProcessBuilder builder = this.processBuilder;
      StringBuilder text = new StringBuilder(1024);
      List cmd = builder.getCommandLine();
      Iterator var4 = cmd.iterator();

      while(var4.hasNext()) {
         String t = (String)var4.next();
         if (t.matches("[ \t\n]")) {
            text.append("\"").append(t).append("\" ");
         } else {
            text.append(t).append(' ');
         }
      }

      this.logStartInfo(Level.INFO, this.getStartString(text));
      Map env = builder.getEnvironment();
      if (env != null) {
         Iterator var8 = env.entrySet().iterator();

         while(var8.hasNext()) {
            Map.Entry e = (Map.Entry)var8.next();
            this.logStartInfo(Level.FINEST, "Environment: " + e.getKey() + "=" + e.getValue());
         }
      }

      this.logStartInfo(Level.INFO, nmText.getWorkingDirectory(builder.getDirectory().toString()));
      this.logStartInfo(Level.INFO, nmText.getOutFile(this.serverMgr.getServerDir().getOutFile().getCanonicalPath()));
   }

   private void logStartInfo(Level level, String msg) {
      this.serverMgr.log(level, msg, (Throwable)null);
      this.log(level, msg, (Throwable)null);
   }
}
