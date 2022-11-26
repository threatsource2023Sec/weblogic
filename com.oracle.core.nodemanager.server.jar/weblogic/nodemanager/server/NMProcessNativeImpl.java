package weblogic.nodemanager.server;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.util.ProcessControl;

public class NMProcessNativeImpl extends NMProcess {
   private ProcessControl pc;
   private String pid;
   private final boolean isAnExistingPID;
   private static final int PROCESS_CHECK_INTERVAL = 500;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   public NMProcessNativeImpl(ProcessControl pc, NMProcessInfo processInfo) {
      super(processInfo);
      this.pc = pc;
      this.isAnExistingPID = false;
      this.setLogger(NMServer.nmLog);
      this.setErrorLevel(Level.WARNING);
   }

   public NMProcessNativeImpl(ProcessControl pc, NMProcessInfo processInfo, String pid) {
      super(processInfo);
      this.pc = pc;
      this.pid = pid;
      if (pid != null) {
         this.isAnExistingPID = true;
      } else {
         this.isAnExistingPID = false;
      }

      this.setLogger(NMServer.nmLog);
      this.setErrorLevel(Level.WARNING);
   }

   public NMProcessNativeImpl(ProcessControl pc, List cmd, Map env, File dir, File outFile) {
      super(cmd, env, dir, outFile);
      this.pc = pc;
      this.isAnExistingPID = false;
      this.setLogger(NMServer.nmLog);
      this.setErrorLevel(Level.WARNING);
   }

   public NMProcessNativeImpl(ProcessControl pc, String pid) {
      super((List)null, (Map)null, (File)null, (File)null);
      this.pc = pc;
      this.pid = pid;
      if (pid != null) {
         this.isAnExistingPID = true;
         this.setLogger(NMServer.nmLog);
         this.setErrorLevel(Level.WARNING);
      } else {
         throw new IllegalStateException(nmText.msgIllegalNMProcessArguments(pid));
      }
   }

   protected final void startInternal(Properties prop) throws IOException {
      if (!this.isAnExistingPID) {
         synchronized(this.pc) {
            List cmd = this.getCommand();
            this.pid = this.pc.createProcess((String[])cmd.toArray(new String[cmd.size()]), this.getEnv(), this.getDir(), this.getOutFile());
         }
      }

   }

   public String getProcessId() {
      return this.pid;
   }

   public boolean isAlive() {
      return this.pid != null && !this.pid.isEmpty() ? this.pc.isProcessAlive(this.pid) : false;
   }

   public void destroy(Properties props) {
      if (this.pid == null) {
         this.notStartedYet("destroy");
      }

      if (!this.pc.killProcess(this.pid)) {
         NMServer.nmLog.warning("Native process management unable to kill " + this.pid);
      }

   }

   protected final void waitFor() throws InterruptedException {
      if (this.pid == null) {
         this.notStartedYet("waitFor");
      }

      while(this.pc.isProcessAlive(this.pid)) {
         Thread.sleep(500L);
      }

   }
}
