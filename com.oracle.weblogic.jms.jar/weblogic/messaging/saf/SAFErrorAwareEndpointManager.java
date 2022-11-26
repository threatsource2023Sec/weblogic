package weblogic.messaging.saf;

import java.util.ArrayList;

public interface SAFErrorAwareEndpointManager extends SAFEndpointManager {
   void handleFailure(SAFErrorHandler var1, SAFRequest var2, ArrayList var3, ArrayList var4);
}
