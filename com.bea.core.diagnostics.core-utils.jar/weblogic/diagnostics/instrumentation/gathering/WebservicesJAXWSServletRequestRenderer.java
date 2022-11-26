package weblogic.diagnostics.instrumentation.gathering;

import javax.servlet.http.HttpServletRequest;
import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.servlet.http.RequestResponseKey;

public final class WebservicesJAXWSServletRequestRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject == null) {
         return null;
      } else {
         if (inputObject instanceof RequestResponseKey) {
            RequestResponseKey respK = (RequestResponseKey)inputObject;
            HttpServletRequest req = respK.getRequest();
            if (req != null) {
               return new WebservicesJAXWSEventInfoImpl(req.getRequestURI());
            }
         }

         return null;
      }
   }
}
