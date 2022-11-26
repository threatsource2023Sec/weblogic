package weblogic.kernel;

public class QueueFullException extends RuntimeException {
   private static final long serialVersionUID = 8516665554844443414L;
   private int queueSize;

   public QueueFullException(int theQueueSize) {
      this.queueSize = theQueueSize;
   }

   public String getMessage() {
      return "Queue exceed maximum capacity of: '" + this.queueSize + "' elements";
   }
}
