package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.CompressionConfig.CompressionMode;

/** @deprecated */
public enum CompressionLevel implements CompressionConfig.CompressionModeI {
   OFF(CompressionMode.OFF),
   ON(CompressionMode.ON),
   FORCE(CompressionMode.FORCE);

   private final CompressionConfig.CompressionMode normalizedLevel;

   private CompressionLevel(CompressionConfig.CompressionMode normalizedLevel) {
      this.normalizedLevel = normalizedLevel;
   }

   public CompressionConfig.CompressionMode normalize() {
      return this.normalizedLevel;
   }

   public static CompressionLevel getCompressionLevel(String compression) {
      if ("on".equalsIgnoreCase(compression)) {
         return ON;
      } else if ("force".equalsIgnoreCase(compression)) {
         return FORCE;
      } else if ("off".equalsIgnoreCase(compression)) {
         return OFF;
      } else {
         throw new IllegalArgumentException();
      }
   }
}
