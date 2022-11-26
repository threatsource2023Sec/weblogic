package weblogic.io.common.internal;

import java.io.IOException;

class WriteResponse {
   private int bufferWritten = -1;
   private boolean cancelled = false;
   private String error = "";
   private int writeBehind;
   private static final int CLIENT_TIMEOUT_MILLSECS = 120000;

   public WriteResponse(int writeBehind) {
      this.writeBehind = writeBehind;
   }

   public synchronized void waitAroundExactly(int bufferNum) throws IOException {
      this.waitAround(bufferNum + this.writeBehind + 1);
   }

   public synchronized void waitAround(int bufferNum) throws IOException {
      if (this.cancelled) {
         throw new IOException(this.error);
      } else {
         while(bufferNum - this.writeBehind - 1 > this.bufferWritten && !this.cancelled) {
            int oldBufferWritten = this.bufferWritten;

            try {
               this.wait(120000L);
            } catch (InterruptedException var4) {
            }

            if (oldBufferWritten == this.bufferWritten && !this.cancelled) {
               this.cancel("Timed out or interrupted waiting for write response");
            }
         }

         if (this.cancelled) {
            throw new IOException(this.error);
         }
      }
   }

   public synchronized void signal(int bufferNum) {
      if (bufferNum > this.bufferWritten) {
         this.bufferWritten = bufferNum;
         this.notify();
      }

   }

   public synchronized void cancel(String error) {
      this.error = error;
      this.cancelled = true;
      this.notify();
   }
}
