package weblogic.iiop.utils;

import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class TimeLimitedLogger {
   private static final Clock clock = new Clock() {
      public long currentTimeMillis() {
         return System.currentTimeMillis();
      }
   };
   private PrintWriter writer;
   private long intervalSize;
   private Queue messageTimes;

   public TimeLimitedLogger(int maxMessagesPerInterval, long timeInterval, PrintWriter writer) {
      this.intervalSize = timeInterval * 1000L;
      this.writer = writer;
      this.messageTimes = new ArrayBlockingQueue(maxMessagesPerInterval);
   }

   public void log(String s) {
      long now = clock.currentTimeMillis();
      this.removeMessageTimesBefore(now - this.intervalSize);
      if (this.messageTimes.offer(now)) {
         this.writer.println(s);
      }

   }

   private void removeMessageTimesBefore(long startOfInterval) {
      while(this.messageTimes.size() > 0 && (Long)this.messageTimes.peek() <= startOfInterval) {
         this.messageTimes.poll();
      }

   }
}
