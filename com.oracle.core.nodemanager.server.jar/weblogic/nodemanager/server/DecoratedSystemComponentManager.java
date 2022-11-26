package weblogic.nodemanager.server;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;

class DecoratedSystemComponentManager implements ProcessManagementPlugin.SystemComponentManager {
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   private static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");
   private final ProcessManagementPlugin.SystemComponentManager systemComponentManager;
   private final ServerManagerI serverMgr;

   public DecoratedSystemComponentManager(ServerManagerI serverMgr, ProcessManagementPlugin.SystemComponentManager systemComponentManager) {
      assert serverMgr != null : "ServerManagerI null";

      this.serverMgr = serverMgr;

      assert systemComponentManager != null : "SystemComponentManager null";

      this.systemComponentManager = systemComponentManager;
   }

   public ProcessManagementPlugin.SystemComponentState getState() {
      try {
         return this.systemComponentManager.getState();
      } catch (Throwable var2) {
         logUnExpectedPluginRuntimeError(this.systemComponentManager, "getState()", var2, (Class)null);
         return null;
      }
   }

   public ProcessManagementPlugin.Process createProcess() throws IOException {
      this.log("SystemComponentManager.createProcess()");

      ProcessManagementPlugin.Process process;
      try {
         process = this.systemComponentManager.createProcess();
      } catch (Throwable var4) {
         IOException ioEx = logAndWrapAsIOExceptionIfNecessary(this.systemComponentManager, "createProcess()", var4);
         throw ioEx;
      }

      return this.createDecoratedProcess(process);
   }

   public ProcessManagementPlugin.Process createProcess(String pid) throws IOException {
      assert pid != null : "PID null";

      this.log("SystemComponentManager.createProcess(" + pid + ")");

      ProcessManagementPlugin.Process process;
      try {
         process = this.systemComponentManager.createProcess(pid);
      } catch (Throwable var5) {
         IOException ioEx = logAndWrapAsIOExceptionIfNecessary(this.systemComponentManager, "createProcess(String pid)", var5);
         throw ioEx;
      }

      return this.createDecoratedProcess(process);
   }

   public void log(Level level, String message, Throwable thrown) {
      this.log("SystemComponentManager.log(" + level + ", '" + message + "', " + thrown + ")", thrown);

      try {
         this.systemComponentManager.log(level, message, thrown);
      } catch (Throwable var5) {
         logUnExpectedPluginRuntimeError(this.systemComponentManager, "log(Level level, String message, Throwable thrown)", var5, (Class)null);
      }

   }

   void log(String s) {
      this.log(s, (Throwable)null);
   }

   void log(String s, Throwable thrown) {
      this.serverMgr.log(Level.FINEST, "ProcessManagementPlugin: " + s, thrown);
   }

   private static void logUnExpectedPluginRuntimeError(Object pluginObj, String method, Throwable th, Class legalExceptionClass) {
      if (legalExceptionClass == null) {
         nmLog.log(Level.SEVERE, nmText.unexpectedExFromPluginNoLegalExThrown(pluginObj.getClass().getName() + "." + method, th.getClass().getName(), th.toString()), th);
      } else {
         nmLog.log(Level.SEVERE, nmText.unexpectedExFromPluginWithLegalExThrown(pluginObj.getClass().getName() + "." + method, th.getClass().getName(), th.toString(), legalExceptionClass.getName()), th);
      }

   }

   private static IOException logAndWrapAsIOExceptionIfNecessary(Object plugin, String method, Throwable th) {
      if (th instanceof IOException) {
         return (IOException)th;
      } else {
         logUnExpectedPluginRuntimeError(plugin, method, th, IOException.class);
         return new IOException("Unexpected exception from Plugin: " + th, th);
      }
   }

   protected DecoratedProcess createDecoratedProcess(ProcessManagementPlugin.Process process) {
      return new DecoratedProcess(this, process);
   }

   private static class DecoratedProcess implements ProcessManagementPlugin.Process {
      private final DecoratedSystemComponentManager parent;
      private final ProcessManagementPlugin.Process process;

      public DecoratedProcess(DecoratedSystemComponentManager decoratedSystemComponentManager, ProcessManagementPlugin.Process process) {
         assert process != null;

         this.parent = decoratedSystemComponentManager;
         this.process = process;
      }

      public void start(Properties props) throws IOException {
         this.parent.log("Process.start()");

         try {
            this.process.start(props);
         } catch (Throwable var4) {
            IOException ioEx = DecoratedSystemComponentManager.logAndWrapAsIOExceptionIfNecessary(this.process, "start(Properties props)", var4);
            throw ioEx;
         }
      }

      public void destroy(Properties props) throws IOException {
         this.parent.log("Process.destroy()");

         try {
            this.process.destroy(props);
         } catch (Throwable var4) {
            IOException ioEx = DecoratedSystemComponentManager.logAndWrapAsIOExceptionIfNecessary(this.process, "destroy(Properties props)", var4);
            throw ioEx;
         }
      }

      public boolean isAlive() {
         return this.process.isAlive();
      }

      public void softRestart(Properties props) throws UnsupportedOperationException, IOException {
         this.parent.log("Process.softRestart()");

         try {
            this.process.softRestart(props);
         } catch (UnsupportedOperationException var4) {
            throw var4;
         } catch (Throwable var5) {
            IOException ioex = DecoratedSystemComponentManager.logAndWrapAsIOExceptionIfNecessary(this.process, "softRestart(Properties props)", var5);
            throw ioex;
         }
      }

      public String getProcessId() {
         this.parent.log("Process.getProcessId()");
         return this.process.getProcessId();
      }

      public void waitForProcessDeath() {
         this.parent.log("Process.waitForProcessDeath()");
         this.process.waitForProcessDeath();
      }
   }
}
