package org.glassfish.soteria.cdi.spi.impl;

import javax.el.ELProcessor;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.soteria.cdi.spi.CDIPerRequestInitializer;

public class LibertyCDIPerRequestInitializer implements CDIPerRequestInitializer {
   public void init(HttpServletRequest request) {
      Object weldInitialListener = request.getServletContext().getAttribute("org.jboss.weld.servlet.WeldInitialListener");
      ServletRequestEvent event = new ServletRequestEvent(request.getServletContext(), request);
      ELProcessor elProcessor = new ELProcessor();
      elProcessor.defineBean("weldInitialListener", weldInitialListener);
      elProcessor.defineBean("event", event);
      elProcessor.eval("weldInitialListener.requestInitialized(event)");
   }

   public void destroy(HttpServletRequest request) {
      Object weldInitialListener = request.getServletContext().getAttribute("org.jboss.weld.servlet.WeldInitialListener");
      ServletRequestEvent event = new ServletRequestEvent(request.getServletContext(), request);
      ELProcessor elProcessor = new ELProcessor();
      elProcessor.defineBean("weldInitialListener", weldInitialListener);
      elProcessor.defineBean("event", event);
      elProcessor.eval("weldInitialListener.requestDestroyed(event)");
      if (request.getServletContext().getAttribute("com.ibm.ws.security.jaspi.servlet.request.wrapper") != null) {
         request.getServletContext().removeAttribute("com.ibm.ws.security.jaspi.servlet.request.wrapper");
      }

   }
}
