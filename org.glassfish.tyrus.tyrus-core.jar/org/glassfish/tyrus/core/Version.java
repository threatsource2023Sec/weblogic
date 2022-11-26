package org.glassfish.tyrus.core;

import org.glassfish.tyrus.spi.UpgradeRequest;

public enum Version {
   DRAFT17("13") {
      public ProtocolHandler createHandler(boolean mask, MaskingKeyGenerator maskingKeyGenerator) {
         return new ProtocolHandler(mask, maskingKeyGenerator);
      }

      public boolean validate(UpgradeRequest request) {
         return this.wireProtocolVersion.equals(request.getHeader("Sec-WebSocket-Version"));
      }
   };

   final String wireProtocolVersion;

   public abstract ProtocolHandler createHandler(boolean var1, MaskingKeyGenerator var2);

   public abstract boolean validate(UpgradeRequest var1);

   private Version(String wireProtocolVersion) {
      this.wireProtocolVersion = wireProtocolVersion;
   }

   public String toString() {
      return this.name();
   }

   public static String getSupportedWireProtocolVersions() {
      StringBuilder sb = new StringBuilder();
      Version[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Version v = var1[var3];
         if (v.wireProtocolVersion.length() > 0) {
            sb.append(v.wireProtocolVersion).append(", ");
         }
      }

      return sb.substring(0, sb.length() - 2);
   }

   // $FF: synthetic method
   Version(String x2, Object x3) {
      this(x2);
   }
}
