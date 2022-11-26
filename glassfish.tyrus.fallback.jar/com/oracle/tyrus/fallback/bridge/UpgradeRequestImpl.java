package com.oracle.tyrus.fallback.bridge;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.tyrus.spi.UpgradeRequest;

public class UpgradeRequestImpl extends UpgradeRequest {
   private HttpServletRequest req;
   private final Map headers;

   public UpgradeRequestImpl(HttpServletRequest req) {
      this.req = req;
      Map tmpHeaders = new TreeMap(new Comparator() {
         public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
         }
      });
      Enumeration headerNames = req.getHeaderNames();

      while(headerNames.hasMoreElements()) {
         String name = (String)headerNames.nextElement();
         tmpHeaders.put(name, Collections.unmodifiableList(Collections.list(req.getHeaders(name))));
      }

      tmpHeaders.put("Upgrade", Collections.singletonList("websocket"));
      tmpHeaders.put("Connection", Collections.singletonList("Upgrade"));
      tmpHeaders.put("Sec-WebSocket-Key", Collections.singletonList("dGhlIHNhbXBsZSBub25jZQ=="));
      tmpHeaders.put("Sec-WebSocket-Version", Collections.singletonList("13"));
      this.headers = Collections.unmodifiableMap(tmpHeaders);
   }

   public String getHeader(String name) {
      return this.headers.containsKey(name) ? (String)((List)this.headers.get(name)).get(0) : null;
   }

   public String getRequestUri() {
      this.validateRequest();
      return this.req.getRequestURI();
   }

   public boolean isSecure() {
      this.validateRequest();
      return this.req.isSecure();
   }

   public Map getHeaders() {
      return this.headers;
   }

   public Principal getUserPrincipal() {
      this.validateRequest();
      return this.req.getUserPrincipal();
   }

   public URI getRequestURI() {
      this.validateRequest();

      try {
         return new URI(this.getRequestUri());
      } catch (URISyntaxException var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   public boolean isUserInRole(String role) {
      this.validateRequest();
      return this.req.isUserInRole(role);
   }

   public Object getHttpSession() {
      this.validateRequest();
      return this.req.getSession(false);
   }

   public Map getParameterMap() {
      this.validateRequest();
      Map params = this.req.getParameterMap();
      Map paramMap = new HashMap();
      Iterator i$ = params.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry e = (Map.Entry)i$.next();
         paramMap.put(e.getKey(), Collections.unmodifiableList(Arrays.asList((Object[])e.getValue())));
      }

      return Collections.unmodifiableMap(paramMap);
   }

   public String getQueryString() {
      this.validateRequest();
      return this.req.getQueryString();
   }

   public void done() {
      this.req = null;
   }

   private void validateRequest() {
      if (this.req == null) {
         throw new IllegalStateException("HTTP request is used beyond the upgrade");
      }
   }
}
