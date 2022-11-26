package javax.websocket;

public interface MessageHandler {
   public interface Partial extends MessageHandler {
      void onMessage(Object var1, boolean var2);
   }

   public interface Whole extends MessageHandler {
      void onMessage(Object var1);
   }
}
