package weblogic.diagnostics.instrumentation.gathering;

import weblogic.connector.outbound.ConnectionPool;
import weblogic.diagnostics.instrumentation.ValueRenderer;

public class JCAConnectionPoolRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      return inputObject != null && inputObject instanceof ConnectionPool ? ((ConnectionPool)inputObject).getName() : null;
   }
}
