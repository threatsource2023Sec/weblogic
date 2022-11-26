package weblogic.work.concurrent.spi;

public class ServerStatusChecker implements ThreadCreationChecker {
   private volatile int state = 0;

   public void acquire() throws RejectException {
      if (this.state == 0) {
         throw new ServiceShutdownException();
      }
   }

   public void undo() {
   }

   public boolean start() {
      if (this.state != 1) {
         this.state = 1;
         return true;
      } else {
         return false;
      }
   }

   public boolean stop() {
      if (this.state != 0) {
         this.state = 0;
         return true;
      } else {
         return false;
      }
   }

   public boolean isStarted() {
      return this.state == 1;
   }
}
