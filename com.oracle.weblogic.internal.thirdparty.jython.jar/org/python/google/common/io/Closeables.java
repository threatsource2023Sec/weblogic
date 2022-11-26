package org.python.google.common.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;

@Beta
@GwtIncompatible
public final class Closeables {
   @VisibleForTesting
   static final Logger logger = Logger.getLogger(Closeables.class.getName());

   private Closeables() {
   }

   public static void close(@Nullable Closeable closeable, boolean swallowIOException) throws IOException {
      if (closeable != null) {
         try {
            closeable.close();
         } catch (IOException var3) {
            if (!swallowIOException) {
               throw var3;
            }

            logger.log(Level.WARNING, "IOException thrown while closing Closeable.", var3);
         }

      }
   }

   public static void closeQuietly(@Nullable InputStream inputStream) {
      try {
         close(inputStream, true);
      } catch (IOException var2) {
         throw new AssertionError(var2);
      }
   }

   public static void closeQuietly(@Nullable Reader reader) {
      try {
         close(reader, true);
      } catch (IOException var2) {
         throw new AssertionError(var2);
      }
   }
}
