package org.python.google.common.io;

import java.io.Flushable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;

@Beta
@GwtIncompatible
public final class Flushables {
   private static final Logger logger = Logger.getLogger(Flushables.class.getName());

   private Flushables() {
   }

   public static void flush(Flushable flushable, boolean swallowIOException) throws IOException {
      try {
         flushable.flush();
      } catch (IOException var3) {
         if (!swallowIOException) {
            throw var3;
         }

         logger.log(Level.WARNING, "IOException thrown while flushing Flushable.", var3);
      }

   }

   public static void flushQuietly(Flushable flushable) {
      try {
         flush(flushable, true);
      } catch (IOException var2) {
         logger.log(Level.SEVERE, "IOException should not have been thrown.", var2);
      }

   }
}
