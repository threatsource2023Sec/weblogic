package weblogic.nodemanager.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import weblogic.nodemanager.server.LogFormatter;
import weblogic.nodemanager.server.NMProcessInfo;

public abstract class DefaultSystemComponentManagerImpl implements ProcessManagementPlugin.SystemComponentManager, NMProcessInfo {
   private static final String EOL = System.getProperty("line.separator");
   private final String instanceName;
   private final NMProcessFactory processFactory;
   private final File outFile;
   private final LogFormatter formatter = new LogFormatter();
   private final File domainDir;
   private final File logsDir;

   public DefaultSystemComponentManagerImpl(String name, Provider provider) {
      this.instanceName = name;
      this.processFactory = provider.getNMProcessFactory();
      this.domainDir = provider.getDomainDirectory();
      File serversDir = new File(this.domainDir, "servers");
      this.makeDir(serversDir);
      File instanceDir = new File(serversDir, this.instanceName);
      this.makeDir(instanceDir);
      this.logsDir = new File(instanceDir, "logs");
      this.makeDir(this.logsDir);
      this.outFile = new File(this.logsDir, this.instanceName + ".out");
   }

   private void makeDir(File dir) {
      Class var2 = DefaultSystemComponentManagerImpl.class;
      synchronized(DefaultSystemComponentManagerImpl.class) {
         if (!dir.isDirectory()) {
            dir.mkdirs();
         }

      }
   }

   public abstract List specifyCmdLine(Properties var1);

   public Map specifyEnvironmentValues(Properties startProps) {
      Map env = new HashMap();
      env.putAll(System.getenv());
      return env;
   }

   public File specifyWorkingDir(Properties startProps) {
      return this.getDomainDir();
   }

   public File specifyOutputFile(Properties startProps) {
      return this.getOutFile();
   }

   public File getDomainDir() {
      return this.domainDir;
   }

   public NMProcessFactory getProcessFactory() {
      return this.processFactory;
   }

   public abstract ProcessManagementPlugin.SystemComponentState getState();

   public String getInstanceName() {
      return this.instanceName;
   }

   public File getOutFile() {
      return this.outFile;
   }

   public ProcessManagementPlugin.Process createProcess() throws IOException {
      return this.getProcessFactory().createProcess(this);
   }

   public ProcessManagementPlugin.Process createProcess(String pid) throws IOException {
      return this.getProcessFactory().createProcess(this, pid);
   }

   public void log(Level level, String message, Throwable thrown) {
      LogRecord lr = new LogRecord(level, message);
      lr.setParameters(new String[]{"NodeManager ComponentManager"});
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

      try {
         FileWriter out = new FileWriter(this.getOutFile(), true);
         out.write(line);
         out.write(EOL);
         out.close();
      } catch (IOException var8) {
      }

   }
}
