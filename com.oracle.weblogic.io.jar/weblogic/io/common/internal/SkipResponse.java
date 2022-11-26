package weblogic.io.common.internal;

import java.io.IOException;

class SkipResponse {
   private Object result = null;
   private boolean cancelled = false;
   private String error = "";
   private static final int CLIENT_TIMEOUT_MILLSECS = 120000;

   public synchronized Object waitAround() throws IOException {
      if (this.cancelled) {
         throw new IOException(this.error);
      } else {
         if (this.result == null) {
            try {
               this.wait(120000L);
            } catch (InterruptedException var2) {
            }
         }

         if (this.result == null && !this.cancelled) {
            this.cancel("Timed out or interrupted waiting for skip response");
         }

         if (this.cancelled) {
            throw new IOException(this.error);
         } else {
            Object tresult = this.result;
            this.result = null;
            return tresult;
         }
      }
   }

   public synchronized void signal(Object rslt) {
      if (this.result != null) {
         throw new AssertionError("Result should have been null!");
      } else {
         this.result = rslt;
         this.notify();
      }
   }

   public synchronized void cancel(String error) {
      this.error = error;
      this.cancelled = true;
      this.notify();
   }
}
