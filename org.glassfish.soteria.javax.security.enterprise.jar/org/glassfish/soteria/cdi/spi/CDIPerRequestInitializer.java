package org.glassfish.soteria.cdi.spi;

import javax.servlet.http.HttpServletRequest;

public interface CDIPerRequestInitializer {
   void init(HttpServletRequest var1);

   void destroy(HttpServletRequest var1);
}
