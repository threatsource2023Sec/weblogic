package weblogic.application;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.work.ShutdownCallback;

public final class AdminModeCompletionBarrier {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private static final int STATE_REGISTERING = 1;
   private static final int STATE_REGISTRATION_COMPLETE = 2;
   private final AdminModeCallback callback;
   private int state = 1;
   private int pendingCallbacks = 0;

   public AdminModeCompletionBarrier(AdminModeCallback callback) {
      this.callback = callback;
   }

   private String state2String(int s) {
      switch (s) {
         case 1:
            return "STATE_REGISTERING";
         case 2:
            return "STATE_REGISTRATION_COMPLETE";
         default:
            throw new AssertionError("unexpected state " + s);
      }
   }

   private void callbackCompleted() {
      boolean makeAdminCallback;
      synchronized(this) {
         --this.pendingCallbacks;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("callbackCompleted " + this + ", pendingCallbacks=" + this.pendingCallbacks, new Exception("Debug exception to capture stack trace"));
         }

         makeAdminCallback = this.pendingCallbacks == 0 && this.state == 2;
      }

      if (makeAdminCallback) {
         this.callback.completed();
      }

   }

   private synchronized void registerCallback() {
      if (this.state != 1) {
         throw new AssertionError("Unexpected state in registerCallback " + this.state2String(this.state));
      } else {
         ++this.pendingCallbacks;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("registerCallback " + this + ", pendingCallbacks=" + this.pendingCallbacks, new Exception("Debug exception to capture stack trace"));
         }

      }
   }

   public ShutdownCallback registerWMShutdown() {
      this.registerCallback();
      return new ShutdownCallback() {
         public void completed() {
            AdminModeCompletionBarrier.this.callbackCompleted();
         }
      };
   }

   public void registrationComplete() {
      boolean makeAdminCallback;
      synchronized(this) {
         if (this.state != 1) {
            throw new AssertionError("Unexpected state in registrationComplete " + this.state2String(this.state));
         }

         this.state = 2;
         makeAdminCallback = this.pendingCallbacks == 0;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("registrationComplete " + this + ", pendingCallbacks=" + this.pendingCallbacks);
         }
      }

      if (makeAdminCallback) {
         this.callback.completed();
      }

   }
}
