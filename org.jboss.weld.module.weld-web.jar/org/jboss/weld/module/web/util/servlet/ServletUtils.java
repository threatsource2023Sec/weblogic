package org.jboss.weld.module.web.util.servlet;

import javax.servlet.ServletContext;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.web.logging.ServletLogger;
import org.jboss.weld.servlet.spi.HttpContextActivationFilter;
import org.jboss.weld.servlet.spi.helpers.AcceptingHttpContextActivationFilter;
import org.jboss.weld.servlet.spi.helpers.RegexHttpContextActivationFilter;

public class ServletUtils {
   private ServletUtils() {
   }

   public static HttpContextActivationFilter getContextActivationFilter(BeanManagerImpl manager, ServletContext context) {
      HttpContextActivationFilter filter = (HttpContextActivationFilter)manager.getServices().get(HttpContextActivationFilter.class);
      String pattern = context.getInitParameter("org.jboss.weld.context.mapping");
      if (filter == AcceptingHttpContextActivationFilter.INSTANCE) {
         if (pattern != null) {
            return new RegexHttpContextActivationFilter(pattern);
         }
      } else if (pattern != null) {
         ServletLogger.LOG.webXmlMappingPatternIgnored(pattern);
      }

      return filter;
   }
}
