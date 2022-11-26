package weblogic.messaging.kernel;

import java.util.List;
import weblogic.messaging.Message;

public interface MultiListener {
   void multiDeliver(Message var1, List var2);

   public interface DeliveryInfo {
      MessageElement getMessageElement();

      Listener getListener();
   }
}
