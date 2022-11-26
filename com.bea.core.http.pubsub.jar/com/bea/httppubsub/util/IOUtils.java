package com.bea.httppubsub.util;

import java.io.IOException;
import java.io.InputStream;

public final class IOUtils {
   private IOUtils() {
   }

   public static void closeInputStreamIfNecessary(InputStream is) {
      if (is != null) {
         try {
            is.close();
         } catch (IOException var2) {
         }
      }

   }
}
