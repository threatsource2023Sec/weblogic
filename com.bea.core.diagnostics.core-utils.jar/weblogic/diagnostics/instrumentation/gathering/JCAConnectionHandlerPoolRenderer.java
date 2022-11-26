package weblogic.diagnostics.instrumentation.gathering;

import weblogic.connector.outbound.ConnectionHandlerBaseImpl;
import weblogic.diagnostics.instrumentation.ValueRenderer;

public class JCAConnectionHandlerPoolRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      return inputObject != null && inputObject instanceof ConnectionHandlerBaseImpl ? ((ConnectionHandlerBaseImpl)inputObject).getPoolName() : null;
   }
}
