package weblogic.nodemanager.server;

import com.bea.logging.LogFileConfigBean;
import com.bea.logging.RotatingFileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import weblogic.nodemanager.util.Drainer;

public class NMProcessImpl extends NMProcess {
   private Process proc;
   private Drainer outDrainer;
   private LogFileConfigBean logFileRotationConfig;

   public NMProcessImpl(NMProcessInfo processInfo, LogFileConfigBean logFileRotationConfig) {
      super(processInfo);
      this.logFileRotationConfig = logFileRotationConfig;
      this.setLogger(NMServer.nmLog);
      this.setErrorLevel(Level.WARNING);
   }

   public NMProcessImpl(List cmd, Map env, File dir, File outFile, LogFileConfigBean logFileRotationConfig) {
      super(cmd, env, dir, outFile);
      this.logFileRotationConfig = logFileRotationConfig;
      this.setLogger(NMServer.nmLog);
      this.setErrorLevel(Level.WARNING);
   }

   protected final void startInternal(Properties props) throws IOException {
      this.logFileRotationConfig.setBaseLogFileName(this.getOutFile().getPath());
      this.logFileRotationConfig.setLogFileRotationDir(this.getOutFile().getParent());
      RotatingFileOutputStream out = new RotatingFileOutputStream(this.logFileRotationConfig);
      ProcessBuilder pb = this.createProcessObject();
      pb.redirectErrorStream(true);
      this.proc = pb.start();
      this.proc.getOutputStream().close();
      this.outDrainer = new Drainer(this.proc.getInputStream(), out);
      this.outDrainer.start();
   }

   ProcessBuilder createProcessObject() {
      ProcessBuilder pb = new ProcessBuilder(this.getCommand());
      Map env = pb.environment();
      Map additionalEnv;
      if ((additionalEnv = this.getEnv()) != null) {
         env.putAll(additionalEnv);
      }

      NMHelper.scrubEnvironmentValues(env);
      pb.directory(this.getDir());
      return pb;
   }

   public void destroy(Properties props) {
      if (this.proc == null) {
         this.notStartedYet("destroy");
      }

      this.proc.destroy();
   }

   protected final void waitFor() throws InterruptedException {
      if (this.proc == null) {
         this.notStartedYet("waitFor");
      }

      this.proc.waitFor();
      this.outDrainer.join();
   }

   public boolean isAlive() {
      if (this.proc != null) {
         try {
            this.proc.exitValue();
         } catch (IllegalThreadStateException var2) {
            return true;
         }
      }

      return false;
   }

   public String getProcessId() {
      return null;
   }
}
