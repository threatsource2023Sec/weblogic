package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Supplier;

@GwtCompatible(
   emulated = true
)
public final class Callables {
   private Callables() {
   }

   public static Callable returning(@Nullable final Object value) {
      return new Callable() {
         public Object call() {
            return value;
         }
      };
   }

   @Beta
   @GwtIncompatible
   public static AsyncCallable asAsyncCallable(final Callable callable, final ListeningExecutorService listeningExecutorService) {
      Preconditions.checkNotNull(callable);
      Preconditions.checkNotNull(listeningExecutorService);
      return new AsyncCallable() {
         public ListenableFuture call() throws Exception {
            return listeningExecutorService.submit(callable);
         }
      };
   }

   @GwtIncompatible
   static Callable threadRenaming(final Callable callable, final Supplier nameSupplier) {
      Preconditions.checkNotNull(nameSupplier);
      Preconditions.checkNotNull(callable);
      return new Callable() {
         public Object call() throws Exception {
            Thread currentThread = Thread.currentThread();
            String oldName = currentThread.getName();
            boolean restoreName = Callables.trySetName((String)nameSupplier.get(), currentThread);
            boolean var9 = false;

            Object var4;
            try {
               var9 = true;
               var4 = callable.call();
               var9 = false;
            } finally {
               if (var9) {
                  if (restoreName) {
                     Callables.trySetName(oldName, currentThread);
                  }

               }
            }

            if (restoreName) {
               Callables.trySetName(oldName, currentThread);
            }

            return var4;
         }
      };
   }

   @GwtIncompatible
   static Runnable threadRenaming(final Runnable task, final Supplier nameSupplier) {
      Preconditions.checkNotNull(nameSupplier);
      Preconditions.checkNotNull(task);
      return new Runnable() {
         public void run() {
            Thread currentThread = Thread.currentThread();
            String oldName = currentThread.getName();
            boolean restoreName = Callables.trySetName((String)nameSupplier.get(), currentThread);
            boolean var8 = false;

            try {
               var8 = true;
               task.run();
               var8 = false;
            } finally {
               if (var8) {
                  if (restoreName) {
                     Callables.trySetName(oldName, currentThread);
                  }

               }
            }

            if (restoreName) {
               Callables.trySetName(oldName, currentThread);
            }

         }
      };
   }

   @GwtIncompatible
   private static boolean trySetName(String threadName, Thread currentThread) {
      try {
         currentThread.setName(threadName);
         return true;
      } catch (SecurityException var3) {
         return false;
      }
   }
}
