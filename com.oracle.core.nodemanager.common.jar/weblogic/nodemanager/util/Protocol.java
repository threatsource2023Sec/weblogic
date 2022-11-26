package weblogic.nodemanager.util;

import java.io.IOException;

public class Protocol {
   public static void assertNotTLSAlert(byte[] protocolMsgBytes) throws ProtocolException {
      if (isTLSAlert(protocolMsgBytes)) {
         throw new TLSAlertException("TLS/SSL Alert", protocolMsgBytes);
      }
   }

   public static void protocolException(byte[] protocolMsgBytes) throws ProtocolException {
      assertNotTLSAlert(protocolMsgBytes);
      throw new ProtocolException("Unknown protocol exception", protocolMsgBytes);
   }

   public static boolean isTLSAlert(byte[] bytes) {
      if (bytes != null && bytes.length >= 7) {
         byte TLS_ALERT_FLAG = 21;
         byte TLS_ALERT_MAJOR_VERSION = 3;
         byte[] TLS_ALERT_MINOR_VERSION = new byte[]{0, 1, 2, 3};
         byte TLS_ALERT_LENGTH_3 = 0;
         byte TLS_ALERT_LENGTH_4 = 2;
         byte[] TLS_ALERT_LEVEL = new byte[]{1, 2};
         byte[] TLS_ALERT_DESCRIPTION = new byte[]{0, 10, 20, 21, 22, 30, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 60, 70, 71, 80, 90, 100, 110, 111, 112, 113, 114, 115};
         return TLS_ALERT_FLAG == bytes[0] && TLS_ALERT_MAJOR_VERSION == bytes[1] && support(TLS_ALERT_MINOR_VERSION, bytes[2]) && TLS_ALERT_LENGTH_3 == bytes[3] && TLS_ALERT_LENGTH_4 == bytes[4] && support(TLS_ALERT_LEVEL, bytes[5]) && support(TLS_ALERT_DESCRIPTION, bytes[6]);
      } else {
         return false;
      }
   }

   private static boolean support(byte[] scope, byte value) {
      for(int i = 0; i < scope.length; ++i) {
         if (scope[i] == value) {
            return true;
         }
      }

      return false;
   }

   public static class TLSAlertException extends ProtocolException {
      public TLSAlertException(String msg) {
         super(msg);
      }

      public TLSAlertException(String msg, byte[] protocolMsgBytes) {
         super(msg, protocolMsgBytes);
      }
   }

   public static class ProtocolException extends IOException {
      private byte[] protocolMsgBytes;

      public ProtocolException(String msg) {
         this(msg, (byte[])null);
      }

      public ProtocolException(String msg, byte[] protocolMsgBytes) {
         super(msg);
         this.protocolMsgBytes = protocolMsgBytes;
      }
   }
}
