package org.jboss.weld.servlet.spi.helpers;

import javax.servlet.http.HttpServletRequest;
import org.jboss.weld.servlet.spi.HttpContextActivationFilter;

public class AcceptingHttpContextActivationFilter implements HttpContextActivationFilter {
   public static final AcceptingHttpContextActivationFilter INSTANCE = new AcceptingHttpContextActivationFilter();

   private AcceptingHttpContextActivationFilter() {
   }

   public void cleanup() {
   }

   public boolean accepts(HttpServletRequest request) {
      return true;
   }
}
