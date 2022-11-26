package javax.websocket;

import java.util.Iterator;
import java.util.ServiceLoader;

public abstract class ContainerProvider {
   public static WebSocketContainer getWebSocketContainer() {
      Iterator providers = ServiceLoader.load(ContainerProvider.class).iterator();
      if (!providers.hasNext()) {
         throw new RuntimeException("Could not find an implementation class.");
      } else {
         do {
            ContainerProvider impl = (ContainerProvider)providers.next();
            WebSocketContainer wsc = impl.getContainer();
            if (wsc != null) {
               return wsc;
            }
         } while(providers.hasNext());

         throw new RuntimeException("Could not find an implementation class with a non-null WebSocketContainer.");
      }
   }

   protected abstract WebSocketContainer getContainer();
}
