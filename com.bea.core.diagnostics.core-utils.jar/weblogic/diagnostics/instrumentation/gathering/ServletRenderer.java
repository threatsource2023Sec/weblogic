package weblogic.diagnostics.instrumentation.gathering;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import weblogic.diagnostics.instrumentation.ValueRenderer;

public class ServletRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject instanceof Servlet) {
         ServletConfig servletConfig = ((Servlet)inputObject).getServletConfig();
         if (servletConfig != null) {
            return servletConfig.getServletName();
         }
      }

      return null;
   }
}
