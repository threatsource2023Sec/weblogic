package weblogic.messaging.kernel;

import java.util.List;

public interface Listener {
   Runnable deliver(ListenRequest var1, List var2);

   Runnable deliver(ListenRequest var1, MessageElement var2);
}
