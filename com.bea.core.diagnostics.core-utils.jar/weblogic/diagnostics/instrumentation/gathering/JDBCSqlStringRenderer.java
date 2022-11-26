package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;

public class JDBCSqlStringRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      return inputObject == null ? null : new JDBCEventInfoImpl(inputObject.toString(), (String)null);
   }
}
