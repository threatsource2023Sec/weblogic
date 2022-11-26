package weblogic.io.common.internal;

import java.io.IOException;

class CommandResponse {
   private boolean signaled = false;
   private boolean cancelled = false;
   private String error = "";
   private static final int CLIENT_TIMEOUT_MILLSECS = 120000;

   public synchronized void waitAround() throws IOException {
      if (this.cancelled) {
         throw new IOException(this.error);
      } else {
         if (!this.signaled) {
            try {
               this.wait(120000L);
            } catch (InterruptedException var2) {
            }
         }

         if (!this.signaled && !this.cancelled) {
            this.cancel("Timed out or interrupted waiting for command response");
         }

         if (this.cancelled) {
            throw new IOException(this.error);
         } else {
            this.signaled = false;
         }
      }
   }

   public synchronized void signal() {
      if (this.signaled) {
         throw new AssertionError("Signaled should be false!");
      } else {
         this.signaled = true;
         this.notify();
      }
   }

   public synchronized void cancel(String error) {
      this.error = error;
      this.cancelled = true;
      this.notify();
   }
}
