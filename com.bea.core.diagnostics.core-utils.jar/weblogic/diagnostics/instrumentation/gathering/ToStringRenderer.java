package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;

public class ToStringRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      return inputObject == null ? null : inputObject.toString();
   }
}
