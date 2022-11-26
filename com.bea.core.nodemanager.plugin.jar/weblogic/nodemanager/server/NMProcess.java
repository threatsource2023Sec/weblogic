package weblogic.nodemanager.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.nodemanager.NMPluginTextFormatter;
import weblogic.nodemanager.plugin.AbstractProcess;
import weblogic.nodemanager.util.MultiException;

public abstract class NMProcess extends AbstractProcess {
   private static final NMPluginTextFormatter nmText = NMPluginTextFormatter.getInstance();
   private List cmd;
   private Map env;
   private File dir;
   private File outFile;
   private MultiExecuteCallbackHook preStartHook = new MultiExecuteCallbackHook("PreStart", false);
   private MultiExecuteCallbackHook postStopHook = new MultiExecuteCallbackHook("PostStop", true);
   private Logger logger;
   private Level errorLevel;
   private NMProcessInfo processInfo;
   private boolean started = false;

   protected NMProcess(NMProcessInfo processInfo) {
      this.processInfo = processInfo;
   }

   protected NMProcess(List cmd, Map env, File dir, File outFile) {
      this.cmd = cmd;
      this.env = env;
      this.dir = dir;
      this.outFile = outFile;
   }

   protected void setLogger(Logger l) {
      this.logger = l;
   }

   protected Logger getLogger() {
      if (this.logger == null) {
         throw new AssertionError("Logger not initialized");
      } else {
         return this.logger;
      }
   }

   protected void setErrorLevel(Level level) {
      this.errorLevel = level;
   }

   protected List getCommand() {
      return this.cmd;
   }

   protected Map getEnv() {
      return this.env;
   }

   protected File getDir() {
      return this.dir;
   }

   protected File getOutFile() {
      return this.outFile;
   }

   public final void addPreStartHook(ExecuteCallbackHook hook) {
      this.preStartHook.addHook(hook);
   }

   public final void removePreStartHook(ExecuteCallbackHook hook) {
      this.preStartHook.removeHook(hook);
   }

   public final void addPostStopHook(ExecuteCallbackHook hook) {
      this.postStopHook.addHook(hook);
   }

   public final void removePostStopHook(ExecuteCallbackHook hook) {
      this.postStopHook.removeHook(hook);
   }

   public final void start(Properties prop) throws IOException {
      if (this.started) {
         throw new IllegalStateException(nmText.msgProcessAlreadyStarted());
      } else {
         if (this.processInfo != null) {
            this.cmd = this.processInfo.specifyCmdLine(prop);
            this.env = this.processInfo.specifyEnvironmentValues(prop);
            this.outFile = this.processInfo.specifyOutputFile(prop);
            this.dir = this.processInfo.specifyWorkingDir(prop);
         }

         try {
            this.started = true;
            this.preStartHook.execute();
            this.startInternal(prop);
         } catch (IOException var3) {
            this.executePostStopHooks();
            throw var3;
         }
      }
   }

   public final void waitForProcessDeath() {
      try {
         this.waitFor();
      } catch (InterruptedException var5) {
      } finally {
         this.executePostStopHooks();
      }

   }

   protected abstract void startInternal(Properties var1) throws IOException;

   public abstract String getProcessId();

   public abstract boolean isAlive();

   public abstract void destroy(Properties var1);

   protected abstract void waitFor() throws InterruptedException;

   protected void notStartedYet(String methodCalled) {
      throw new IllegalStateException(nmText.msgProcessNotStarted(methodCalled));
   }

   private void executePostStopHooks() {
      try {
         this.postStopHook.execute();
      } catch (IOException var2) {
         this.logger.log(this.errorLevel, var2.getMessage(), var2);
      }

   }

   private class MultiExecuteCallbackHook implements ExecuteCallbackHook {
      private final String name;
      private final boolean continueOnFailure;
      private List allHooks = new ArrayList();
      private boolean alreadyExecuted = false;

      public MultiExecuteCallbackHook(String name, boolean continueOnFailure) {
         this.name = name;
         this.continueOnFailure = continueOnFailure;
      }

      private void addHook(ExecuteCallbackHook hook) {
         synchronized(this) {
            if (!this.allHooks.contains(hook)) {
               this.allHooks.add(hook);
            }

         }
      }

      private void removeHook(ExecuteCallbackHook hook) {
         synchronized(this) {
            this.allHooks.remove(hook);
         }
      }

      public void execute() throws IOException {
         if (!this.alreadyExecuted) {
            try {
               Iterator iter = this.allHooks.iterator();
               MultiException ne = null;

               while(iter.hasNext()) {
                  ExecuteCallbackHook each = (ExecuteCallbackHook)iter.next();
                  if (this.continueOnFailure) {
                     Exception e = this.executeWithContinueOnFailure(each);
                     if (e != null) {
                        if (ne == null) {
                           ne = new MultiException("Exception while executing '" + this.name + "' ExecutableCallbacks");
                        }

                        ne.add(e);
                     }
                  } else {
                     this.execute(each);
                  }
               }

               if (ne != null) {
                  IOException ioe = new IOException(ne.getBaseMessage());
                  ioe.initCause(ne);
                  throw ioe;
               }
            } finally {
               this.alreadyExecuted = true;
            }

         }
      }

      private Exception executeWithContinueOnFailure(ExecuteCallbackHook hook) {
         IOException ioe = null;

         try {
            this.execute(hook);
         } catch (IOException var4) {
            ioe = var4;
         }

         return ioe;
      }

      private void execute(ExecuteCallbackHook hook) throws IOException {
         hook.execute();
      }
   }

   public interface ExecuteCallbackHook {
      void execute() throws IOException;
   }
}
