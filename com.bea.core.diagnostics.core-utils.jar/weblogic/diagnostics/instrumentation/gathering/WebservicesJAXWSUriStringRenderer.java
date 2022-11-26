package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;

public class WebservicesJAXWSUriStringRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      return inputObject == null ? null : new WebservicesJAXWSEventInfoImpl(inputObject.toString());
   }
}
