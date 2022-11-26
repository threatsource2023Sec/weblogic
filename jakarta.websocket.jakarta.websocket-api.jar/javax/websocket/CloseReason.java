package javax.websocket;

import java.io.UnsupportedEncodingException;

public class CloseReason {
   private final CloseCode closeCode;
   private final String reasonPhrase;

   public CloseReason(CloseCode closeCode, String reasonPhrase) {
      if (closeCode == null) {
         throw new IllegalArgumentException("closeCode cannot be null");
      } else {
         try {
            if (reasonPhrase != null && reasonPhrase.getBytes("UTF-8").length > 123) {
               throw new IllegalArgumentException("Reason Phrase cannot exceed 123 UTF-8 encoded bytes: " + reasonPhrase);
            }
         } catch (UnsupportedEncodingException var4) {
            throw new IllegalStateException(var4);
         }

         this.closeCode = closeCode;
         this.reasonPhrase = "".equals(reasonPhrase) ? null : reasonPhrase;
      }
   }

   public CloseCode getCloseCode() {
      return this.closeCode;
   }

   public String getReasonPhrase() {
      return this.reasonPhrase == null ? "" : this.reasonPhrase;
   }

   public String toString() {
      return this.reasonPhrase == null ? "CloseReason[" + this.closeCode.getCode() + "]" : "CloseReason[" + this.closeCode.getCode() + "," + this.reasonPhrase + "]";
   }

   public static enum CloseCodes implements CloseCode {
      NORMAL_CLOSURE(1000),
      GOING_AWAY(1001),
      PROTOCOL_ERROR(1002),
      CANNOT_ACCEPT(1003),
      RESERVED(1004),
      NO_STATUS_CODE(1005),
      CLOSED_ABNORMALLY(1006),
      NOT_CONSISTENT(1007),
      VIOLATED_POLICY(1008),
      TOO_BIG(1009),
      NO_EXTENSION(1010),
      UNEXPECTED_CONDITION(1011),
      SERVICE_RESTART(1012),
      TRY_AGAIN_LATER(1013),
      TLS_HANDSHAKE_FAILURE(1015);

      private int code;

      public static CloseCode getCloseCode(final int code) {
         if (code >= 1000 && code <= 4999) {
            switch (code) {
               case 1000:
                  return NORMAL_CLOSURE;
               case 1001:
                  return GOING_AWAY;
               case 1002:
                  return PROTOCOL_ERROR;
               case 1003:
                  return CANNOT_ACCEPT;
               case 1004:
                  return RESERVED;
               case 1005:
                  return NO_STATUS_CODE;
               case 1006:
                  return CLOSED_ABNORMALLY;
               case 1007:
                  return NOT_CONSISTENT;
               case 1008:
                  return VIOLATED_POLICY;
               case 1009:
                  return TOO_BIG;
               case 1010:
                  return NO_EXTENSION;
               case 1011:
                  return UNEXPECTED_CONDITION;
               case 1012:
                  return SERVICE_RESTART;
               case 1013:
                  return TRY_AGAIN_LATER;
               case 1014:
               default:
                  return new CloseCode() {
                     public int getCode() {
                        return code;
                     }
                  };
               case 1015:
                  return TLS_HANDSHAKE_FAILURE;
            }
         } else {
            throw new IllegalArgumentException("Invalid code: " + code);
         }
      }

      private CloseCodes(int code) {
         this.code = code;
      }

      public int getCode() {
         return this.code;
      }
   }

   public interface CloseCode {
      int getCode();
   }
}
