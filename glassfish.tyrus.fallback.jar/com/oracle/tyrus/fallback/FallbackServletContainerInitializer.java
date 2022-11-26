package com.oracle.tyrus.fallback;

import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpoint;

@HandlesTypes({ServerEndpoint.class, ServerApplicationConfig.class, Endpoint.class})
public class FallbackServletContainerInitializer implements ServletContainerInitializer {
   private static final Logger LOGGER = Logger.getLogger(FallbackServletContainerInitializer.class.getName());
   private static final String FALLBACK_PARAM = "com.oracle.tyrus.fallback.enabled";

   public void onStartup(Set classes, ServletContext ctx) throws ServletException {
      String paramValue = ctx.getInitParameter("com.oracle.tyrus.fallback.enabled");
      boolean enabled = paramValue != null && paramValue.equals("true");
      if (enabled && classes != null && !classes.isEmpty()) {
         FallbackInitFilter initFilter = (FallbackInitFilter)ctx.createFilter(FallbackInitFilter.class);
         FilterRegistration.Dynamic initReg = ctx.addFilter("WebSocket fallback init filter", initFilter);
         initReg.addMappingForUrlPatterns((EnumSet)null, true, new String[]{"/*"});
         initReg.setAsyncSupported(true);
         LOGGER.info("Registering WebSocket fallback init filter for context root " + ctx.getContextPath());
         LongPollingFilter pollingFilter = (LongPollingFilter)ctx.createFilter(LongPollingFilter.class);
         FilterRegistration.Dynamic pollingReg = ctx.addFilter("WebSocket fallback long-polling filter", pollingFilter);
         pollingReg.setAsyncSupported(true);
         pollingReg.addMappingForUrlPatterns((EnumSet)null, true, new String[]{"/*"});
         LOGGER.info("Registering WebSocket fallback long-polling filter for context root " + ctx.getContextPath());
      }
   }
}
