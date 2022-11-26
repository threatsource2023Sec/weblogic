package net.shibboleth.utilities.java.support.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpCookie;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SameSiteCookieHeaderFilter implements Filter {
   @Nonnull
   @NotEmpty
   private static final String SAMESITE_ATTRIBITE_NAME = "SameSite";
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SameSiteCookieHeaderFilter.class);
   @Nullable
   private SameSiteValue defaultValue;
   @Nonnull
   @NonnullElements
   private Map sameSiteCookies = Collections.emptyMap();

   public void setDefaultValue(@Nullable SameSiteValue value) {
      this.defaultValue = value;
   }

   public void setSameSiteCookies(@Nullable @NonnullElements Map map) {
      if (map != null) {
         this.sameSiteCookies = new HashMap(4);
         Iterator i$ = map.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            Iterator i$ = ((List)entry.getValue()).iterator();

            while(i$.hasNext()) {
               String cookieName = (String)i$.next();
               if (this.sameSiteCookies.get(cookieName) != null) {
                  this.log.error("Duplicate cookie name '{}' found in SameSite cookie map, please check configuration.", cookieName);
                  throw new IllegalArgumentException("Duplicate cookie name found in SameSite cookie map");
               }

               String trimmedName = StringSupport.trimOrNull(cookieName);
               if (trimmedName != null) {
                  this.sameSiteCookies.put(cookieName, entry.getKey());
               }
            }
         }
      } else {
         this.sameSiteCookies = Collections.emptyMap();
      }

   }

   public void init(@Nonnull FilterConfig filterConfig) throws ServletException {
   }

   public void destroy() {
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      if (!(response instanceof HttpServletResponse)) {
         throw new ServletException("Response is not an instance of HttpServletResponse");
      } else {
         chain.doFilter(request, new SameSiteResponseProxy((HttpServletResponse)response));
      }
   }

   private class SameSiteResponseProxy extends HttpServletResponseWrapper {
      @Nonnull
      private final HttpServletResponse response;

      public SameSiteResponseProxy(@Nonnull HttpServletResponse resp) {
         super(resp);
         this.response = resp;
      }

      public void sendError(int sc) throws IOException {
         this.appendSameSite();
         super.sendError(sc);
      }

      public PrintWriter getWriter() throws IOException {
         this.appendSameSite();
         return super.getWriter();
      }

      public void sendError(int sc, String msg) throws IOException {
         this.appendSameSite();
         super.sendError(sc, msg);
      }

      public void sendRedirect(String location) throws IOException {
         this.appendSameSite();
         super.sendRedirect(location);
      }

      public ServletOutputStream getOutputStream() throws IOException {
         this.appendSameSite();
         return super.getOutputStream();
      }

      private void appendSameSite() {
         Collection cookieheaders = this.response.getHeaders("Set-Cookie");
         boolean firstHeader = true;
         Iterator i$ = cookieheaders.iterator();

         while(i$.hasNext()) {
            String cookieHeader = (String)i$.next();
            if (StringSupport.trimOrNull(cookieHeader) != null) {
               List parsedCookies = null;

               try {
                  parsedCookies = HttpCookie.parse(cookieHeader);
               } catch (IllegalArgumentException var7) {
                  SameSiteCookieHeaderFilter.this.log.trace("Cookie header '{}' violates the cookie specification and will be ignored", cookieHeader);
               }

               if (parsedCookies != null && parsedCookies.size() == 1) {
                  SameSiteValue sameSiteValue = (SameSiteValue)SameSiteCookieHeaderFilter.this.sameSiteCookies.get(((HttpCookie)parsedCookies.get(0)).getName());
                  if (sameSiteValue != null) {
                     this.appendSameSiteAttribute(cookieHeader, sameSiteValue.getValue(), firstHeader);
                  } else if (SameSiteCookieHeaderFilter.this.defaultValue != null) {
                     this.appendSameSiteAttribute(cookieHeader, SameSiteCookieHeaderFilter.this.defaultValue.getValue(), firstHeader);
                  } else if (firstHeader) {
                     this.response.setHeader("Set-Cookie", cookieHeader);
                  } else {
                     this.response.addHeader("Set-Cookie", cookieHeader);
                  }

                  firstHeader = false;
               }
            }
         }

      }

      private void appendSameSiteAttribute(@Nonnull @NotEmpty String cookieHeader, @Nonnull @NotEmpty String sameSiteValue, @Nonnull boolean first) {
         String sameSiteSetCookieValue = cookieHeader;
         if (!cookieHeader.contains("SameSite")) {
            sameSiteSetCookieValue = String.format("%s; %s", cookieHeader, "SameSite=" + sameSiteValue);
         }

         if (first) {
            this.response.setHeader("Set-Cookie", sameSiteSetCookieValue);
         } else {
            this.response.addHeader("Set-Cookie", sameSiteSetCookieValue);
         }

      }
   }

   public static enum SameSiteValue {
      Strict("Strict"),
      Lax("Lax"),
      None("None");

      @Nonnull
      @NotEmpty
      private String value;

      private SameSiteValue(@Nonnull @NotEmpty String attrValue) {
         this.value = Constraint.isNotEmpty(attrValue, "the same-site attribute value can not be empty");
      }

      public String getValue() {
         return this.value;
      }
   }
}
