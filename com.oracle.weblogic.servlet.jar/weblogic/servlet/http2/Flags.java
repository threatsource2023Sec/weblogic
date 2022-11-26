package weblogic.servlet.http2;

public class Flags {
   public static final int NONE = 0;
   public static final int END_STREAM = 1;
   public static final int ACK = 1;
   public static final int END_HEADERS = 4;
   public static final int PADDING = 8;
   public static final int PRIORITY = 32;

   private Flags() {
   }

   public static boolean isEndStream(int flags) {
      return (flags & 1) > 0;
   }

   public static boolean isAck(int flags) {
      return (flags & 1) > 0;
   }

   public static boolean isEndOfHeaders(int flags) {
      return (flags & 4) > 0;
   }

   public static boolean hasPadding(int flags) {
      return (flags & 8) > 0;
   }

   public static boolean hasPriority(int flags) {
      return (flags & 32) > 0;
   }
}
