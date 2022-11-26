package weblogic.messaging.saf;

import java.io.Externalizable;

public interface SAFErrorHandler extends Externalizable {
   short WS_ERROR_HANDLER = 1;
   short JMS_ERROR_HANDLER = 2;

   boolean isAlwaysForward();
}
