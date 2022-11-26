package org.glassfish.tyrus.core;

import java.net.URI;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.glassfish.tyrus.spi.UpgradeRequest;

public final class RequestContext extends UpgradeRequest {
   private final URI requestURI;
   private final String queryString;
   private final Object httpSession;
   private final boolean secure;
   private final Principal userPrincipal;
   private final Builder.IsUserInRoleDelegate isUserInRoleDelegate;
   private final String remoteAddr;
   private Map headers;
   private Map parameterMap;

   private RequestContext(URI requestURI, String queryString, Object httpSession, boolean secure, Principal userPrincipal, Builder.IsUserInRoleDelegate IsUserInRoleDelegate, String remoteAddr, Map parameterMap, Map headers) {
      this.requestURI = requestURI;
      this.queryString = queryString;
      this.httpSession = httpSession;
      this.secure = secure;
      this.userPrincipal = userPrincipal;
      this.isUserInRoleDelegate = IsUserInRoleDelegate;
      this.remoteAddr = remoteAddr;
      this.parameterMap = parameterMap;
      this.headers = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      if (headers != null) {
         this.headers.putAll(headers);
      }

   }

   public Map getHeaders() {
      return this.headers;
   }

   public String getHeader(String name) {
      List stringList = (List)this.headers.get(name);
      if (stringList == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();
         boolean first = true;

         String s;
         for(Iterator var5 = stringList.iterator(); var5.hasNext(); sb.append(s)) {
            s = (String)var5.next();
            if (first) {
               first = false;
            } else {
               sb.append(", ");
            }
         }

         return sb.toString();
      }
   }

   public void lock() {
      this.headers = Collections.unmodifiableMap(this.headers);
      this.parameterMap = Collections.unmodifiableMap(this.parameterMap);
   }

   public Principal getUserPrincipal() {
      return this.userPrincipal;
   }

   public URI getRequestURI() {
      return this.requestURI;
   }

   public boolean isUserInRole(String role) {
      return this.isUserInRoleDelegate != null ? this.isUserInRoleDelegate.isUserInRole(role) : false;
   }

   public Object getHttpSession() {
      return this.httpSession;
   }

   public Map getParameterMap() {
      return this.parameterMap;
   }

   public String getQueryString() {
      return this.queryString;
   }

   public String getRequestUri() {
      return this.requestURI.toString();
   }

   public boolean isSecure() {
      return this.secure;
   }

   public String getRemoteAddr() {
      return this.remoteAddr;
   }

   // $FF: synthetic method
   RequestContext(URI x0, String x1, Object x2, boolean x3, Principal x4, Builder.IsUserInRoleDelegate x5, String x6, Map x7, Map x8, Object x9) {
      this(x0, x1, x2, x3, x4, x5, x6, x7, x8);
   }

   public static final class Builder {
      private URI requestURI;
      private String queryString;
      private Object httpSession;
      private boolean secure;
      private Principal userPrincipal;
      private IsUserInRoleDelegate isUserInRoleDelegate;
      private Map parameterMap;
      private String remoteAddr;
      private Map headers;

      public static Builder create() {
         return new Builder();
      }

      public static Builder create(RequestContext requestContext) {
         Builder builder = new Builder();
         builder.requestURI = requestContext.requestURI;
         builder.queryString = requestContext.queryString;
         builder.httpSession = requestContext.httpSession;
         builder.secure = requestContext.secure;
         builder.userPrincipal = requestContext.userPrincipal;
         builder.isUserInRoleDelegate = requestContext.isUserInRoleDelegate;
         builder.parameterMap = requestContext.parameterMap;
         builder.remoteAddr = requestContext.remoteAddr;
         builder.headers = requestContext.headers;
         return builder;
      }

      public Builder requestURI(URI requestURI) {
         this.requestURI = requestURI;
         return this;
      }

      public Builder queryString(String queryString) {
         this.queryString = queryString;
         return this;
      }

      public Builder httpSession(Object httpSession) {
         this.httpSession = httpSession;
         return this;
      }

      public Builder secure(boolean secure) {
         this.secure = secure;
         return this;
      }

      public Builder userPrincipal(Principal principal) {
         this.userPrincipal = principal;
         return this;
      }

      public Builder isUserInRoleDelegate(IsUserInRoleDelegate isUserInRoleDelegate) {
         this.isUserInRoleDelegate = isUserInRoleDelegate;
         return this;
      }

      public Builder parameterMap(Map parameterMap) {
         if (parameterMap != null) {
            this.parameterMap = new HashMap();
            Iterator var2 = parameterMap.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               this.parameterMap.put(entry.getKey(), Arrays.asList((Object[])entry.getValue()));
            }
         } else {
            this.parameterMap = null;
         }

         return this;
      }

      public Builder remoteAddr(String remoteAddr) {
         this.remoteAddr = remoteAddr;
         return this;
      }

      public RequestContext build() {
         return new RequestContext(this.requestURI, this.queryString, this.httpSession, this.secure, this.userPrincipal, this.isUserInRoleDelegate, this.remoteAddr, (Map)(this.parameterMap != null ? this.parameterMap : new HashMap()), this.headers);
      }

      public interface IsUserInRoleDelegate {
         boolean isUserInRole(String var1);
      }
   }
}
