package weblogic.nodemanager.server.provider;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.DefaultCacheServer;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.nodemanager.common.StateInfoWriter;
import weblogic.nodemanager.server.DomainDir;
import weblogic.nodemanager.server.ServerDir;
import weblogic.nodemanager.util.ConcurrentFile;
import weblogic.nodemanager.util.ProcessControl;
import weblogic.nodemanager.util.ProcessControlFactory;

public class WeblogicCacheServer extends DefaultCacheServer {
   private static final String SERVER_NAME_PROPERTY = "coherence.member";
   private static final String WEBLOGIC_DOMAIN_DIR_PROPERTY = "weblogic.RootDirectory";
   private final ConcurrentFile pidFile;
   private final ConcurrentFile stateFile;
   private boolean started = false;
   private boolean failed = false;

   public WeblogicCacheServer(ConfigurableCacheFactory cacheFactory) {
      super(cacheFactory);
      String domainDirPath = System.getProperty("weblogic.RootDirectory");
      if (domainDirPath == null) {
         throw new IllegalStateException("If using nodemanager you must supply a domain dir using property: weblogic.RootDirectory");
      } else {
         String serverName = System.getProperty("coherence.member");
         if (serverName == null) {
            throw new IllegalStateException("If using nodemanager you must supply a server name using property: coherence.member");
         } else {
            ServerDir serverDir = (new DomainDir(domainDirPath)).getServerDir(serverName, "Coherence");
            this.pidFile = serverDir.getPidFile();
            this.stateFile = serverDir.getStateFile();
         }
      }
   }

   public WeblogicCacheServer(ConfigurableCacheFactory cacheFactory, ServerDir serverDir) {
      super(cacheFactory);
      this.pidFile = serverDir.getPidFile();
      this.stateFile = serverDir.getStateFile();
   }

   public static void main(String[] asArg) {
      WeblogicCacheServer cacheServer = new WeblogicCacheServer(CacheFactory.getConfigurableCacheFactory(getContextClassLoader()));
      cacheServer.startAndMonitor(5000L);
   }

   public void startAndMonitor(long cWaitMillis) {
      this.writeProcessId(this.pidFile);
      this.writeState("STARTING");
      super.startAndMonitor(cWaitMillis);
      this.writeState("SHUTDOWN");
      this.pidFile.delete();
   }

   protected void initialStartServices(long cWaitMillis) {
      super.initialStartServices(cWaitMillis);
      this.started = true;
      this.writeState("RUNNING");
   }

   public void shutdownServer() {
      this.writeState("SHUTTING_DOWN");
      super.shutdownServer();
   }

   private void writeState(String state) {
      try {
         StateInfoWriter.writeStateInfo(this.stateFile, state, this.started, this.failed);
      } catch (IOException var4) {
         if (CacheFactory.isLogEnabled(1)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Exception while writing ").append(this.stateFile.getAbsoluteFile());
            sb.append(": ").append(var4.getMessage());
            CacheFactory.log(sb.toString(), 1);
         }
      }

   }

   private void writeProcessId(ConcurrentFile pidFile) {
      String pid;
      StringBuilder sb;
      try {
         pid = this.getProcessId();
      } catch (Throwable var5) {
         if (CacheFactory.isLogEnabled(1)) {
            sb = new StringBuilder();
            sb.append("Exception while writing ").append(pidFile.getAbsoluteFile());
            sb.append(": ").append(var5.getMessage());
            CacheFactory.log(sb.toString(), 1);
         }

         return;
      }

      if (pid != null) {
         try {
            pidFile.writeLine(pid);
         } catch (IOException var6) {
            if (CacheFactory.isLogEnabled(1)) {
               sb = new StringBuilder();
               sb.append("Exception while writing ").append(pidFile.getAbsoluteFile());
               sb.append(": ").append(var6.getMessage());
               CacheFactory.log(sb.toString(), 1);
            }
         }
      }

   }

   private String getProcessId() {
      RuntimeMXBean rtb = ManagementFactory.getRuntimeMXBean();
      String processName = rtb.getName();
      Pattern pattern = Pattern.compile("^([0-9]+)@.+$", 2);
      Matcher matcher = pattern.matcher(processName);
      return matcher.matches() ? matcher.group(1) : this.getProcessIdNative();
   }

   private String getProcessIdNative() {
      ProcessControl processControl = ProcessControlFactory.getProcessControl();
      return processControl.getProcessId();
   }

   private void logGetProcessIdException(Exception e) {
      if (CacheFactory.isLogEnabled(1)) {
         StringBuilder sb = new StringBuilder();
         sb.append("Exception while writing PID").append(this.stateFile.getAbsoluteFile());
         sb.append(": ").append(e.getMessage());
         CacheFactory.log(sb.toString(), 1);
      }

   }
}
