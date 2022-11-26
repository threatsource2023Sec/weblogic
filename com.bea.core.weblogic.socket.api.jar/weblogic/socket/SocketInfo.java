package weblogic.socket;

public class SocketInfo {
   static final int OKAY = 0;
   static final int PROTOCOL_ERROR = 1;
   static final int CLOSE_ONLY = 2;
   static final int IO_PENDING = 4;
   static final int EXCEPTION_PENDING = 8;
   static final int IDLE_TIMEOUT = 16;
   static final int MSG_TIMEOUT = 32;
   protected MuxableSocket ms;
   private long lastIoInitiatedTimeMillis = -1L;
   private long lastMessageReadingStartedTimeMillis = -1L;
   private boolean markedClose = false;
   private boolean exceptionHandlingCompleted = false;

   public SocketInfo(MuxableSocket ms) {
      this.setMuxableSocket(ms);
   }

   protected String fieldsToString() {
      StringBuilder sb = new StringBuilder(200);
      sb.append("ms = ").append(this.ms).append(", ").append("socket = ").append(this.ms.getSocket()).append(", ").append("lastIoInitiatedTimeMillis = ").append(this.lastIoInitiatedTimeMillis).append(", ").append("lastMessageReadingStartedTimeMillis = ").append(this.lastMessageReadingStartedTimeMillis).append(", ").append("markedClose = ").append(this.markedClose).append(", ").append("exceptionHandlingCompleted = ").append(this.exceptionHandlingCompleted);
      return sb.toString();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(200);
      sb.append(this.getClass().getName()).append("[").append(this.fieldsToString()).append("]");
      return sb.toString();
   }

   public int getFD() {
      return -1;
   }

   public boolean touch() {
      synchronized(this) {
         if (this.isCloseOnly()) {
            return false;
         } else {
            if (this.lastIoInitiatedTimeMillis != -1L) {
               this.lastIoInitiatedTimeMillis = System.currentTimeMillis();
            }

            return true;
         }
      }
   }

   final MuxableSocket getMuxableSocket() {
      return this.ms;
   }

   final void setMuxableSocket(MuxableSocket ms) {
      this.ms = ms;
   }

   final boolean ioInitiated() {
      synchronized(this) {
         if (!this.isCloseOnly() && !this.ioPending()) {
            this.lastIoInitiatedTimeMillis = System.currentTimeMillis();
            return true;
         } else {
            return false;
         }
      }
   }

   final int ioCompleted() {
      synchronized(this) {
         if (!this.ioPending()) {
            return 1;
         } else {
            this.lastIoInitiatedTimeMillis = -1L;
            if (this.isCloseOnly()) {
               return !this.exceptionHandlingCompleted ? 10 : 2;
            } else {
               return 0;
            }
         }
      }
   }

   final synchronized void messageInitiated() {
      if (this.lastMessageReadingStartedTimeMillis == -1L) {
         this.lastMessageReadingStartedTimeMillis = System.currentTimeMillis();
      }

   }

   final synchronized void messageCompleted() {
      this.lastMessageReadingStartedTimeMillis = -1L;
   }

   final int close() {
      synchronized(this) {
         if (this.isCloseOnly()) {
            return 1;
         } else {
            this.setCloseOnly();
            return this.ioPending() ? 4 : 0;
         }
      }
   }

   final int exceptionHandlingCompleted() {
      synchronized(this) {
         this.exceptionHandlingCompleted = true;
         return this.ioPending() ? 4 : 0;
      }
   }

   final int checkTimeout(long idleTimeout, long msgTimeout) {
      synchronized(this) {
         if (this.isCloseOnly()) {
            return 2;
         } else if (!this.ioPending()) {
            return 0;
         } else {
            byte status;
            long interval;
            if (this.messagePending()) {
               if (msgTimeout <= 0L) {
                  return 0;
               }

               interval = this.getMessageIntervalMillis(msgTimeout);
               if (interval <= msgTimeout) {
                  return 0;
               }

               status = 32;
            } else {
               if (idleTimeout <= 0L) {
                  return 0;
               }

               interval = this.getIdleIntervalMillis(idleTimeout);
               if (interval <= idleTimeout) {
                  return 0;
               }

               status = 16;
            }

            if (!this.ms.requestTimeout()) {
               return 0;
            } else {
               this.setCloseOnly();
               return status;
            }
         }
      }
   }

   synchronized boolean isCloseOnly() {
      return this.markedClose;
   }

   private void setCloseOnly() {
      this.markedClose = true;
   }

   private boolean ioPending() {
      return this.lastIoInitiatedTimeMillis != -1L;
   }

   private boolean messagePending() {
      return this.lastMessageReadingStartedTimeMillis != -1L;
   }

   private long getIdleIntervalMillis(long idleTimeout) {
      if (idleTimeout == 0L) {
         return 0L;
      } else {
         long now = System.currentTimeMillis();
         return now - this.lastIoInitiatedTimeMillis;
      }
   }

   private long getMessageIntervalMillis(long msgTimeout) {
      if (msgTimeout == 0L) {
         return 0L;
      } else {
         long now = System.currentTimeMillis();
         return now - this.lastMessageReadingStartedTimeMillis;
      }
   }

   protected void cleanup() {
   }
}
