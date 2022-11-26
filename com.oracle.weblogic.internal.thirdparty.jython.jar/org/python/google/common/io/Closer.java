package org.python.google.common.io;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Throwables;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public final class Closer implements Closeable {
   private static final Suppressor SUPPRESSOR;
   @VisibleForTesting
   final Suppressor suppressor;
   private final Deque stack = new ArrayDeque(4);
   private Throwable thrown;

   public static Closer create() {
      return new Closer(SUPPRESSOR);
   }

   @VisibleForTesting
   Closer(Suppressor suppressor) {
      this.suppressor = (Suppressor)Preconditions.checkNotNull(suppressor);
   }

   @CanIgnoreReturnValue
   public Closeable register(@Nullable Closeable closeable) {
      if (closeable != null) {
         this.stack.addFirst(closeable);
      }

      return closeable;
   }

   public RuntimeException rethrow(Throwable e) throws IOException {
      Preconditions.checkNotNull(e);
      this.thrown = e;
      Throwables.propagateIfPossible(e, IOException.class);
      throw new RuntimeException(e);
   }

   public RuntimeException rethrow(Throwable e, Class declaredType) throws IOException, Exception {
      Preconditions.checkNotNull(e);
      this.thrown = e;
      Throwables.propagateIfPossible(e, IOException.class);
      Throwables.propagateIfPossible(e, declaredType);
      throw new RuntimeException(e);
   }

   public RuntimeException rethrow(Throwable e, Class declaredType1, Class declaredType2) throws IOException, Exception, Exception {
      Preconditions.checkNotNull(e);
      this.thrown = e;
      Throwables.propagateIfPossible(e, IOException.class);
      Throwables.propagateIfPossible(e, declaredType1, declaredType2);
      throw new RuntimeException(e);
   }

   public void close() throws IOException {
      Throwable throwable = this.thrown;

      while(!this.stack.isEmpty()) {
         Closeable closeable = (Closeable)this.stack.removeFirst();

         try {
            closeable.close();
         } catch (Throwable var4) {
            if (throwable == null) {
               throwable = var4;
            } else {
               this.suppressor.suppress(closeable, throwable, var4);
            }
         }
      }

      if (this.thrown == null && throwable != null) {
         Throwables.propagateIfPossible(throwable, IOException.class);
         throw new AssertionError(throwable);
      }
   }

   static {
      SUPPRESSOR = (Suppressor)(Closer.SuppressingSuppressor.isAvailable() ? Closer.SuppressingSuppressor.INSTANCE : Closer.LoggingSuppressor.INSTANCE);
   }

   @VisibleForTesting
   static final class SuppressingSuppressor implements Suppressor {
      static final SuppressingSuppressor INSTANCE = new SuppressingSuppressor();
      static final Method addSuppressed = getAddSuppressed();

      static boolean isAvailable() {
         return addSuppressed != null;
      }

      private static Method getAddSuppressed() {
         try {
            return Throwable.class.getMethod("addSuppressed", Throwable.class);
         } catch (Throwable var1) {
            return null;
         }
      }

      public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed) {
         if (thrown != suppressed) {
            try {
               addSuppressed.invoke(thrown, suppressed);
            } catch (Throwable var5) {
               Closer.LoggingSuppressor.INSTANCE.suppress(closeable, thrown, suppressed);
            }

         }
      }
   }

   @VisibleForTesting
   static final class LoggingSuppressor implements Suppressor {
      static final LoggingSuppressor INSTANCE = new LoggingSuppressor();

      public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed) {
         Closeables.logger.log(Level.WARNING, "Suppressing exception thrown when closing " + closeable, suppressed);
      }
   }

   @VisibleForTesting
   interface Suppressor {
      void suppress(Closeable var1, Throwable var2, Throwable var3);
   }
}
