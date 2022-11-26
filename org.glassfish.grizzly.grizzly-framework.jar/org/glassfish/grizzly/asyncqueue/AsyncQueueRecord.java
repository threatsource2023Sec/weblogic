package org.glassfish.grizzly.asyncqueue;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.DebugPoint;

public abstract class AsyncQueueRecord implements Cacheable {
   private static final Logger LOGGER = Grizzly.logger(AsyncQueue.class);
   protected Connection connection;
   protected Object message;
   protected CompletionHandler completionHandler;
   protected boolean isRecycled = false;
   protected DebugPoint recycleTrack;

   protected AsyncQueueRecord() {
   }

   public AsyncQueueRecord(Connection connection, Object message, CompletionHandler completionHandler) {
      this.set(connection, message, completionHandler);
   }

   protected final void set(Connection connection, Object message, CompletionHandler completionHandler) {
      this.checkRecycled();
      this.connection = connection;
      this.message = message;
      this.completionHandler = completionHandler;
   }

   public Connection getConnection() {
      return this.connection;
   }

   public final Object getMessage() {
      this.checkRecycled();
      return this.message;
   }

   public final void setMessage(Object message) {
      this.checkRecycled();
      this.message = message;
   }

   public abstract Object getCurrentResult();

   public void notifyFailure(Throwable e) {
      if (this.completionHandler != null) {
         this.completionHandler.failed(e);
      } else if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, LogMessages.FINE_GRIZZLY_ASYNCQUEUE_ERROR_NOCALLBACK_ERROR(e));
      }

   }

   public final void notifyIncomplete() {
      if (this.completionHandler != null) {
         this.completionHandler.updated(this.getCurrentResult());
      }

   }

   protected final void checkRecycled() {
      if (Grizzly.isTrackingThreadCache() && this.isRecycled) {
         DebugPoint track = this.recycleTrack;
         if (track != null) {
            throw new IllegalStateException("AsyncReadQueueRecord has been recycled at: " + track);
         } else {
            throw new IllegalStateException("AsyncReadQueueRecord has been recycled");
         }
      }
   }
}
