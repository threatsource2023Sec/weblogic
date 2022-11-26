package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.servlet.internal.ServletStub;

public class ServletStubRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject instanceof ServletStub) {
         ServletStub stub = (ServletStub)inputObject;
         return stub.getServletName();
      } else {
         return null;
      }
   }
}
