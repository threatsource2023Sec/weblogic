package org.glassfish.grizzly.http.server;

public class HttpHandlerRegistration {
   public static final HttpHandlerRegistration ROOT = builder().contextPath("").urlPattern("/").build();
   private final String contextPath;
   private final String urlPattern;

   public static Builder builder() {
      return new Builder();
   }

   /** @deprecated */
   public static Builder bulder() {
      return builder();
   }

   public static HttpHandlerRegistration fromString(String mapping) {
      if (mapping == null) {
         return ROOT;
      } else {
         String contextPath = getContextPath(mapping);
         return new HttpHandlerRegistration(contextPath, getWrapperPath(contextPath, mapping));
      }
   }

   private HttpHandlerRegistration(String contextPath, String urlPattern) {
      this.contextPath = contextPath;
      this.urlPattern = urlPattern;
   }

   public String getContextPath() {
      return this.contextPath;
   }

   public String getUrlPattern() {
      return this.urlPattern;
   }

   public int hashCode() {
      int hash = 5;
      hash = 41 * hash + this.contextPath.hashCode();
      hash = 41 * hash + this.urlPattern.hashCode();
      return hash;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         HttpHandlerRegistration other = (HttpHandlerRegistration)obj;
         return this.contextPath.equals(other.contextPath) && this.urlPattern.equals(other.urlPattern);
      }
   }

   private static String getWrapperPath(String ctx, String mapping) {
      if (mapping.indexOf("*.") > 0) {
         return mapping.substring(mapping.lastIndexOf("/") + 1);
      } else if (ctx.length() != 0) {
         return mapping.substring(ctx.length());
      } else {
         return mapping.startsWith("//") ? mapping.substring(1) : mapping;
      }
   }

   private static String getContextPath(String mapping) {
      int slash = mapping.indexOf("/", 1);
      String ctx;
      if (slash != -1) {
         ctx = mapping.substring(0, slash);
      } else {
         ctx = mapping;
      }

      if (ctx.startsWith("/*") || ctx.startsWith("*")) {
         ctx = "";
      }

      if (ctx.equals("/")) {
         ctx = "";
      }

      return ctx;
   }

   // $FF: synthetic method
   HttpHandlerRegistration(String x0, String x1, Object x2) {
      this(x0, x1);
   }

   public static class Builder {
      private String contextPath;
      private String urlPattern;

      public Builder contextPath(String contextPath) {
         this.contextPath = contextPath;
         return this;
      }

      public Builder urlPattern(String urlPattern) {
         this.urlPattern = urlPattern;
         return this;
      }

      public HttpHandlerRegistration build() {
         if (this.contextPath == null) {
            this.contextPath = "";
         }

         if (this.urlPattern == null) {
            this.urlPattern = "/";
         }

         return new HttpHandlerRegistration(this.contextPath, this.urlPattern);
      }
   }
}
