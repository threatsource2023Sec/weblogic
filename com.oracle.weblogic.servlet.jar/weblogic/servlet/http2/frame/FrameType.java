package weblogic.servlet.http2.frame;

public enum FrameType {
   DATA(0),
   HEADERS(1),
   PRIORITY(2),
   RST_STREAM(3),
   SETTINGS(4),
   PUSH_PROMISE(5),
   PING(6),
   GOAWAY(7),
   WINDOW_UPDATE(8),
   CONTINUATION(9),
   UNKNOWN(256);

   private final int type;

   private FrameType(int type) {
      this.type = type;
   }

   public int getType() {
      return this.type;
   }

   public static FrameType valueOf(int i) {
      switch (i) {
         case 0:
            return DATA;
         case 1:
            return HEADERS;
         case 2:
            return PRIORITY;
         case 3:
            return RST_STREAM;
         case 4:
            return SETTINGS;
         case 5:
            return PUSH_PROMISE;
         case 6:
            return PING;
         case 7:
            return GOAWAY;
         case 8:
            return WINDOW_UPDATE;
         case 9:
            return CONTINUATION;
         default:
            return UNKNOWN;
      }
   }
}
