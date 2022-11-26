package weblogic.diagnostics.instrumentation.gathering;

import javax.servlet.http.HttpServletRequestWrapper;
import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletStub;

public final class ServletRequestRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject == null) {
         return null;
      } else if (inputObject instanceof ServletRequestImpl) {
         ServletRequestImpl request = (ServletRequestImpl)inputObject;
         ServletStub stub = request.getServletStub();
         return stub == null ? new ServletEventInfoImpl(request.getRequestURI()) : new ServletEventInfoImpl(request.getRequestURI(), stub.getServletName());
      } else if (inputObject instanceof HttpServletRequestWrapper) {
         HttpServletRequestWrapper request = (HttpServletRequestWrapper)inputObject;
         return new ServletEventInfoImpl(request.getRequestURI());
      } else {
         return null;
      }
   }
}
