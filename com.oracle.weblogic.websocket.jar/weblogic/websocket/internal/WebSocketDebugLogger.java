package weblogic.websocket.internal;

import java.util.Formatter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.io.Chunk;

public final class WebSocketDebugLogger {
   public static final DebugLogger DEBUG_WEBSOCKET = DebugLogger.getDebugLogger("DebugWebSocket");

   public static final boolean isEnabled() {
      return DEBUG_WEBSOCKET.isDebugEnabled();
   }

   public static final void debug(String msg) {
      DEBUG_WEBSOCKET.debug(msg);
   }

   public static final void debug(String msg, Throwable t) {
      DEBUG_WEBSOCKET.debug(msg, t);
   }

   public static final void debug(String msg, byte b, boolean toHex) {
      debug(msg, new byte[]{b}, toHex);
   }

   public static final void debug(String msg, byte[] data, boolean toHex) {
      DEBUG_WEBSOCKET.debug((msg == null ? "" : msg) + (toHex ? bytesToHexString(data) : bytesToBinaryString(data)));
   }

   public static final void debug(String msg, Chunk chunk, int len, boolean toHex) {
      while(true) {
         if (chunk != null) {
            if (len > Chunk.CHUNK_SIZE) {
               debug(msg, chunk.buf, toHex);
               len -= Chunk.CHUNK_SIZE;
               chunk = chunk.next;
               continue;
            }

            byte[] data = new byte[len];
            System.arraycopy(chunk.buf, 0, data, 0, len);
            debug(msg, data, toHex);
         }

         return;
      }
   }

   private static String bytesToHexString(byte[] data) {
      if (data == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();
         Formatter formatter = new Formatter(sb);
         byte[] var3 = data;
         int var4 = data.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            formatter.format("%02x", b);
         }

         return sb.toString();
      }
   }

   private static String bytesToBinaryString(byte[] data) {
      if (data == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();
         byte[] var2 = data;
         int var3 = data.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            sb.append(byteToBinaryString(b, 8));
         }

         return sb.toString();
      }
   }

   private static String byteToBinaryString(byte b, int width) {
      String s = Integer.toBinaryString(b);
      int paddingWidth = width - s.length();
      if (paddingWidth == 0) {
         return s;
      } else if (paddingWidth <= 0) {
         return s.substring(0 - paddingWidth);
      } else {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < paddingWidth; ++i) {
            sb.append("0");
         }

         sb.append(s);
         return sb.toString();
      }
   }
}
