package com.oracle.tyrus.fallback;

import com.oracle.tyrus.fallback.spi.StickyNode;
import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.UUID;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FallbackInitFilter implements Filter {
   private static final String FALLBACK_INIT_HDR = "tyrus-ws-attempt";
   private static final String FALLBACK_TRANSPORTS_HDR = "tyrus-fallback-transports";
   private static final String FALLBACK_TRANSPORTS_VALUE = "WebSocket,XMLHttpRequest";
   private static final String CLUSTER = "weblogic.websocket.tyrus.cluster";
   private static final Logger LOGGER = Logger.getLogger(FallbackInitFilter.class.getName());
   private final String stickyInfo;

   public FallbackInitFilter() {
      ServiceLoader sl = ServiceLoader.load(StickyNode.class);
      Iterator it = sl.iterator();
      if (it.hasNext()) {
         StickyNode stickyNode = (StickyNode)it.next();
         this.stickyInfo = stickyNode.getStickinessInfo();
      } else {
         this.stickyInfo = "";
      }

   }

   public void init(FilterConfig filterConfig) throws ServletException {
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest)request;
      String hdr = FallbackUtil.getHeaderValue(req, "tyrus-ws-attempt");
      if (hdr != null) {
         LOGGER.fine("Received a fallback init request");
         HttpServletResponse res = (HttpServletResponse)response;
         res.setStatus(200);
         res.addHeader("tyrus-fallback-transports", "WebSocket,XMLHttpRequest");
         String conId = UUID.randomUUID().toString();
         conId = conId + this.stickyInfo;
         res.addHeader("tyrus-connection-id", conId);
         String clusterFlag = req.getServletContext().getInitParameter("weblogic.websocket.tyrus.cluster");
         boolean cluster = clusterFlag != null && clusterFlag.equalsIgnoreCase("true");
         if (cluster) {
            String clusterConId = req.getHeader("tyrus-cluster-connection-id");
            if (clusterConId == null) {
               clusterConId = UUID.randomUUID().toString();
            }

            res.addHeader("tyrus-cluster-connection-id", clusterConId);
         }
      } else {
         filterChain.doFilter(request, response);
      }

   }

   public void destroy() {
   }
}
