package weblogic.work.concurrent;

import java.util.Map;
import javax.enterprise.concurrent.ManagedTask;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.work.concurrent.context.CICContextProvider;
import weblogic.work.concurrent.context.ContextSetupProcessor;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.work.concurrent.spi.DaemonThreadManagerImpl;

public class ManagedThread extends AbstractManagedThread {
   private final ContextHandle contextHandleForSetup;
   private final ContextHandle contextHandleForSubmittingCompState;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentMTF");
   private final ContextProvider contextSetupProcessor;
   private final ContextProvider submittingCompStateChecker;
   private final DaemonThreadManagerImpl daemonThreadManager;
   private final ComponentInvocationContext submittingCICInSharing;

   public ManagedThread(Runnable target, ContextProvider contextSetupProcessor, DaemonThreadManagerImpl daemonThreadManager) {
      super(target);
      this.contextSetupProcessor = contextSetupProcessor;
      this.submittingCompStateChecker = contextSetupProcessor instanceof ContextSetupProcessor ? ((ContextSetupProcessor)contextSetupProcessor).getSubmittingCompStateChecker() : null;
      Map props = null;
      if (target instanceof ManagedTask) {
         props = ((ManagedTask)target).getExecutionProperties();
      }

      this.contextHandleForSetup = contextSetupProcessor.save(props);
      this.contextHandleForSubmittingCompState = this.submittingCompStateChecker != null ? this.submittingCompStateChecker.save(props) : null;
      this.daemonThreadManager = daemonThreadManager;
      this.submittingCICInSharing = CICContextProvider.InvocationContextHandle.extractCIC(this.contextHandleForSubmittingCompState);
   }

   public void shutdown(String reason) {
      super.shutdown(reason);

      try {
         this.interrupt();
      } catch (SecurityException var3) {
      }

   }

   public void run() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this + "@" + Integer.toHexString(this.hashCode()) + " enters");
      }

      if (this.shutdown) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this + "@" + Integer.toHexString(this.hashCode()) + " starts after the MTF or the submitting component is shut down, so interrupt itself and continue");
         }

         this.interrupt();
      } else if (this.contextHandleForSubmittingCompState != null) {
         try {
            this.submittingCompStateChecker.setup(this.contextHandleForSubmittingCompState);
         } catch (IllegalStateException var12) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this + "@" + Integer.toHexString(this.hashCode()) + " starts after the submitting component is shut down, so interrupt itself and continue", var12);
            }

            this.shutdown(var12.getMessage());
         }
      }

      if (Thread.currentThread() != this) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("The run() method of " + this + "@" + Integer.toHexString(this.hashCode()) + " is called by thread " + Thread.currentThread() + " directly");
         }

         try {
            super.run();
         } finally {
            this.daemonThreadManager.threadTerminate(this);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this + "@" + Integer.toHexString(this.hashCode()) + " leaves directly");
         }

      } else {
         ContextHandle handle = null;

         try {
            this.readyToRun();
            handle = this.contextSetupProcessor.setup(this.contextHandleForSetup);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("The target of " + this + "@" + Integer.toHexString(this.hashCode()) + " is called");
            }

            super.run();
         } finally {
            this.daemonThreadManager.threadTerminate(this);
            this.contextSetupProcessor.reset(handle);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this + "@" + Integer.toHexString(this.hashCode()) + " leaves");
            }

         }

      }
   }

   public ComponentInvocationContext getSubmittingCICInSharing() {
      return this.submittingCICInSharing;
   }

   protected void finalize() throws Throwable {
      if (this.daemonThreadManager.threadTerminate(this)) {
         debugLogger.debug(String.format("%s was never run, now it is being Garbage Collected.", this.getName()));
      }

   }
}
