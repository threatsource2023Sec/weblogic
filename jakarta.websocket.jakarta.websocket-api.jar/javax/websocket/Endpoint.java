package javax.websocket;

public abstract class Endpoint {
   public abstract void onOpen(Session var1, EndpointConfig var2);

   public void onClose(Session session, CloseReason closeReason) {
   }

   public void onError(Session session, Throwable thr) {
   }
}
