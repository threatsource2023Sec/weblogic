package org.glassfish.grizzly;

import java.io.IOException;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.WritableMessage;

public interface Writer {
   GrizzlyFuture write(Connection var1, WritableMessage var2) throws IOException;

   void write(Connection var1, WritableMessage var2, CompletionHandler var3);

   GrizzlyFuture write(Connection var1, Object var2, WritableMessage var3);

   void write(Connection var1, Object var2, WritableMessage var3, CompletionHandler var4);

   /** @deprecated */
   void write(Connection var1, Object var2, WritableMessage var3, CompletionHandler var4, PushBackHandler var5);

   void write(Connection var1, Object var2, WritableMessage var3, CompletionHandler var4, MessageCloner var5);

   boolean canWrite(Connection var1);

   void notifyWritePossible(Connection var1, WriteHandler var2);

   public static final class Reentrant {
      private static final ThreadLocal REENTRANTS_COUNTER = new ThreadLocal() {
         protected Reentrant initialValue() {
            return new Reentrant();
         }
      };
      private static final int maxWriteReentrants = Integer.getInteger("org.glassfish.grizzly.Writer.max-write-reentrants", 10);
      private int counter;

      public static int getMaxReentrants() {
         return maxWriteReentrants;
      }

      public static Reentrant getWriteReentrant() {
         return (Reentrant)REENTRANTS_COUNTER.get();
      }

      public int get() {
         return this.counter;
      }

      public boolean inc() {
         return ++this.counter <= maxWriteReentrants;
      }

      public boolean dec() {
         return --this.counter <= maxWriteReentrants;
      }

      public boolean isMaxReentrantsReached() {
         return this.get() >= getMaxReentrants();
      }
   }
}
