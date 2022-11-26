package weblogic.jms.dispatcher;

import java.io.IOException;
import weblogic.jms.common.DDMembershipCancelRequest;
import weblogic.jms.common.DDMembershipPushRequest;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSPushRequest;

public class ClientDispatcherObjectHandler extends weblogic.messaging.dispatcher.DispatcherObjectHandler {
   public ClientDispatcherObjectHandler() {
      super(16776960);
   }

   protected weblogic.messaging.dispatcher.Request instantiate(int tc) throws IOException {
      switch (tc) {
         case 15360:
            return new JMSPushExceptionRequest();
         case 15616:
            return new JMSPushRequest();
         case 18688:
            return new DDMembershipPushRequest();
         case 18944:
            return new DDMembershipCancelRequest();
         default:
            return super.instantiate(tc);
      }
   }
}
