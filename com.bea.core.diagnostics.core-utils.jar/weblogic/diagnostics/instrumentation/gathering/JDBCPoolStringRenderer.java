package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;

public class JDBCPoolStringRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      return inputObject == null ? null : new JDBCEventInfoImpl((String)null, inputObject.toString());
   }
}
