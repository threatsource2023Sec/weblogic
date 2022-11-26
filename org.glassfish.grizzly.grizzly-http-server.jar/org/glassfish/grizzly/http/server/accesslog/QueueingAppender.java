package org.glassfish.grizzly.http.server.accesslog;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;

public class QueueingAppender implements AccessLogAppender {
   private static final Logger LOGGER = Grizzly.logger(HttpServer.class);
   private final LinkedBlockingQueue queue = new LinkedBlockingQueue();
   private final AccessLogAppender appender;
   private final Thread thread;

   public QueueingAppender(AccessLogAppender appender) {
      if (appender == null) {
         throw new NullPointerException("Null appender");
      } else {
         this.appender = appender;
         this.thread = new Thread(new Dequeuer());
         this.thread.setName(this.toString());
         this.thread.setDaemon(true);
         this.thread.start();
      }
   }

   public void append(String accessLogEntry) throws IOException {
      if (this.thread.isAlive()) {
         try {
            this.queue.put(accessLogEntry);
         } catch (InterruptedException var3) {
            LOGGER.log(Level.FINE, "Interrupted adding log entry to the queue", var3);
         }
      }

   }

   public void close() throws IOException {
      this.thread.interrupt();

      try {
         this.thread.join();
      } catch (InterruptedException var5) {
         LOGGER.log(Level.FINE, "Interrupted stopping de-queuer", var5);
      } finally {
         this.appender.close();
      }

   }

   private final class Dequeuer implements Runnable {
      private Dequeuer() {
      }

      public void run() {
         while(true) {
            try {
               String accessLogEntry = (String)QueueingAppender.this.queue.take();
               if (accessLogEntry != null) {
                  QueueingAppender.this.appender.append(accessLogEntry);
               }
            } catch (InterruptedException var2) {
               QueueingAppender.LOGGER.log(Level.FINE, "Interrupted waiting for log entry to be queued, exiting!", var2);
               return;
            } catch (Throwable var3) {
               QueueingAppender.LOGGER.log(Level.WARNING, "Exception caught appending ququed log entry", var3);
            }
         }
      }

      // $FF: synthetic method
      Dequeuer(Object x1) {
         this();
      }
   }
}
