package org.python.google.common.cache;

import java.util.concurrent.Executor;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@GwtIncompatible
public final class RemovalListeners {
   private RemovalListeners() {
   }

   public static RemovalListener asynchronous(final RemovalListener listener, final Executor executor) {
      Preconditions.checkNotNull(listener);
      Preconditions.checkNotNull(executor);
      return new RemovalListener() {
         public void onRemoval(final RemovalNotification notification) {
            executor.execute(new Runnable() {
               public void run() {
                  listener.onRemoval(notification);
               }
            });
         }
      };
   }
}
