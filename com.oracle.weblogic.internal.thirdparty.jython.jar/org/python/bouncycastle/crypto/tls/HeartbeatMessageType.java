package org.python.bouncycastle.crypto.tls;

public class HeartbeatMessageType {
   public static final short heartbeat_request = 1;
   public static final short heartbeat_response = 2;

   public static boolean isValid(short var0) {
      return var0 >= 1 && var0 <= 2;
   }
}
