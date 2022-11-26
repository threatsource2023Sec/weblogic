package net.shibboleth.utilities.java.support.net;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class DynamicResponseHeaderFilter implements Filter {
   @Nonnull
   @NonnullElements
   private Map headers = Collections.emptyMap();
   @Nonnull
   @NonnullElements
   private Collection callbacks = Collections.emptyList();

   public void setHeaders(@Nullable @NonnullElements Map map) {
      if (map != null) {
         this.headers = new HashMap(map.size());
         Iterator i$ = map.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            String trimmedName = StringSupport.trimOrNull((String)entry.getKey());
            String trimmedValue = StringSupport.trimOrNull((String)entry.getValue());
            if (trimmedName != null && trimmedValue != null) {
               this.headers.put(trimmedName, trimmedValue);
            }
         }
      } else {
         this.headers = Collections.emptyMap();
      }

   }

   public void setCallbacks(@Nullable @NonnullElements Collection theCallbacks) {
      if (theCallbacks != null) {
         this.callbacks = new ArrayList(Collections2.filter(theCallbacks, Predicates.notNull()));
      } else {
         this.callbacks = Collections.emptyList();
      }

   }

   public void init(FilterConfig filterConfig) throws ServletException {
   }

   public void destroy() {
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      if (this.headers.isEmpty() && this.callbacks.isEmpty()) {
         chain.doFilter(request, response);
      } else if (!(request instanceof HttpServletRequest)) {
         throw new ServletException("Request is not an instance of HttpServletRequest");
      } else if (!(response instanceof HttpServletResponse)) {
         throw new ServletException("Response is not an instance of HttpServletResponse");
      } else {
         chain.doFilter(request, new ResponseProxy((HttpServletRequest)request, (HttpServletResponse)response));
      }
   }

   private class ResponseProxy extends HttpServletResponseWrapper {
      @Nonnull
      private final HttpServletRequest request;

      public ResponseProxy(@Nonnull HttpServletRequest req, @Nonnull HttpServletResponse response) {
         super(response);
         this.request = req;
      }

      public ServletOutputStream getOutputStream() throws IOException {
         this.addHeaders();
         return super.getOutputStream();
      }

      public PrintWriter getWriter() throws IOException {
         this.addHeaders();
         return super.getWriter();
      }

      public void sendError(int sc, String msg) throws IOException {
         this.addHeaders();
         super.sendError(sc, msg);
      }

      public void sendError(int sc) throws IOException {
         this.addHeaders();
         super.sendError(sc);
      }

      public void sendRedirect(String location) throws IOException {
         this.addHeaders();
         super.sendRedirect(location);
      }

      private void addHeaders() {
         Iterator i$ = DynamicResponseHeaderFilter.this.headers.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry header = (Map.Entry)i$.next();
            ((HttpServletResponse)this.getResponse()).addHeader((String)header.getKey(), (String)header.getValue());
         }

         if (!DynamicResponseHeaderFilter.this.callbacks.isEmpty()) {
            Pair p = new Pair(this.request, (HttpServletResponse)this.getResponse());
            Iterator i$x = DynamicResponseHeaderFilter.this.callbacks.iterator();

            while(i$x.hasNext()) {
               Function callback = (Function)i$x.next();
               callback.apply(p);
            }
         }

      }
   }
}
