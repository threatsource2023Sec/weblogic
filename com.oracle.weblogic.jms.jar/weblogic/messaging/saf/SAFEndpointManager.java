package weblogic.messaging.saf;

import java.util.ArrayList;

public interface SAFEndpointManager {
   void addEndpoint(String var1, SAFEndpoint var2);

   void removeEndpoint(String var1);

   SAFEndpoint getEndpoint(String var1);

   void handleFailure(SAFErrorHandler var1, SAFRequest var2, ArrayList var3);

   SAFErrorHandler createErrorHandlerInstance();

   SAFErrorHandler getErrorHandler(String var1);
}
